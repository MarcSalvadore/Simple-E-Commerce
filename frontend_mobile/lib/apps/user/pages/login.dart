import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/catalog/pages/catalog.dart';
import 'package:frontend_mobile/apps/home/home.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Login extends StatefulWidget{
  const Login({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/login';
  static String id = 'login';

  @override
  State<Login> createState() => _LoginState();

}

class _LoginState extends State<Login>{
    final _formKey = GlobalKey<FormState>();
    final Future<SharedPreferences> _prefs = SharedPreferences.getInstance();

    showLoading(BuildContext context) {
      return showDialog(
          context: context,
          barrierDismissible: false,
          builder: (BuildContext context) {
            return const Center(
              child: CircularProgressIndicator(),
            );
          });
    }
    TextEditingController usernameController = TextEditingController();
    TextEditingController passwordController = TextEditingController();

    @override
    void dispose() {
      usernameController.dispose();
      passwordController.dispose();
      super.dispose();
    }


    @override
    Widget build(BuildContext context){
      return PopScope(
        canPop: false,
          child: Scaffold(
            body: Center(
              child : SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    const Text("APAPEDIA",
                        style: TextStyle(
                          fontSize: 28,
                          fontWeight: FontWeight.w700)),
                    const SizedBox(
                      height: 10,
                    ),
                    const Icon(
                      Icons.shopping_cart,
                      size: 100,
                    ),
                    Container(
                      padding: const EdgeInsets.symmetric(horizontal: 8, vertical: 20),
                        margin: const EdgeInsets.all(16),
                        decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(8),
                          color: Colors.white,
                        ),
                      child: Column(
                        mainAxisAlignment: MainAxisAlignment.center,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          const Text("Hello, Login to Explore!",
                              style: TextStyle(
                                fontSize: 24,
                              )),
                          Form(
                              key: _formKey,
                              child: SingleChildScrollView(
                                child: Column(
                                  children: [
                                    const SizedBox(
                                      height: 20,
                                    ),
                                    const SizedBox(
                                      height: 20,
                                    ),
                                    SizedBox(
                                      width:
                                      MediaQuery.of(context).size.width * 0.8,
                                      child: TextFormField(
                                        controller: usernameController,
                                        decoration: const InputDecoration(
                                          border: OutlineInputBorder(),
                                          labelText: "Username",
                                        ),
                                        validator: (String? value) {
                                          if (value == null || value.isEmpty) {
                                            return 'Masukkan username anda';
                                          }
                                          return null;
                                        },
                                      ),
                                    ),
                                    const SizedBox(
                                      height: 20,
                                    ),
                                    SizedBox(
                                        width:
                                        MediaQuery.of(context).size.width * 0.8,
                                        child: TextFormField(
                                          controller: passwordController,
                                          obscureText: true,
                                          decoration: const InputDecoration(
                                            border: OutlineInputBorder(),
                                            labelText: 'Password',
                                          ),
                                          validator: (String? value) {
                                            if (value == null || value.isEmpty) {
                                              return 'Masukkan password anda';
                                            }
                                            return null;
                                          },
                                        )),
                                    const SizedBox(
                                      height: 20,
                                    ),
                                    ElevatedButton(
                                      style: ElevatedButton.styleFrom(
                                        fixedSize: Size(MediaQuery.of(context).size.width * 0.8, 50),
                                        // backgroundColor: ColorPallete.primary,
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                      onPressed: () async {
                                        if(_formKey.currentState!.validate()){
                                          _formKey.currentState!.save();
                                          final response = await http.post(Uri.parse(
                                              "http://10.0.2.2:8081/api/auth/login-customer"
                                          ),
                                              headers: <String, String> {
                                                'Content-Type' : 'application/json;charset=UTF-8'
                                              },
                                              body: jsonEncode(<String,String>{
                                                'username' : usernameController.text,
                                                'password' : passwordController.text,
                                              }));
                                          print("STATUS");
                                          print(response.statusCode);
                                          if(response.statusCode == 200){
                                              print("BERHASIL LOGIN");
                                              final Map parsed = json.decode(response.body);
                                              final String jwtToken = parsed['token'];
                                              final String userId = parsed['id'];

                                              final SharedPreferences prefs = await _prefs;
                                              prefs.setString('jwtToken', jwtToken);
                                              prefs.setString('username', usernameController.text);
                                              prefs.setString('userId', userId);

                                              Navigator.push(
                                                context, MaterialPageRoute(builder: (context) => const Catalog(title: 'Catalog',)),
                                              );
                                          }
                                          else{
                                            ScaffoldMessenger.of(context).showSnackBar(
                                              SnackBar(
                                                content: Text('Invalid username or password. Please try again.'),
                                                duration: const Duration(seconds: 3),
                                              ),
                                            );
                                          }
                                        }
                                      },
                                      child: const Text(
                                        'Login',
                                        style: TextStyle(
                                            fontSize: 20,
                                            color: Colors.black
                                        ),
                                      ),
                                    ),
                                    const SizedBox(
                                      height: 10,
                                    ),
                                    TextButton(
                                      onPressed: () {
                                        Navigator.push(
                                          context, MaterialPageRoute(builder: (context) => const Register(title: 'Register',)),
                                        ); // Use the route name for navigation
                                      },
                                      child: const Text(
                                        "Don't have an account yet? Register here",
                                        // style: TextStyle(
                                        //   fontSize:
                                        // ),
                                      ),
                                    ),
                                  ],
                                ),
                              )
                          )
                        ],
                      ),
                    )
                  ],
                ),
              )
            ),
          )
      );
    }
  }