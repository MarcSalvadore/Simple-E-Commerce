import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/home/home.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';

class Login extends StatefulWidget{
  const Login({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/login';
  static String id = 'login';

  @override
  State<Login> createState() => _LoginState();

}

class _LoginState extends State<Login>{
    final _formKey = GlobalKey<FormState>();

    @override
    Widget build(BuildContext context){
      Size size = MediaQuery.of(context).size;
      return WillPopScope(
        onWillPop: () async {
        Navigator.popAndPushNamed(context, Home.id);
        return true;
        },
      child : Container(
        child: Form(
          key: _formKey,
          child: Stack(
            children: [
              Scaffold(
                body: SingleChildScrollView(
                  child: Column(
                    children: [
                      // SizedBox(
                      //   height: size.width * 0.1,
                      // ),
                      const Stack(
                        children: [
                          Center(
                            child: Text('Login',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 55,
                              fontWeight: FontWeight.bold
                            ),
                            ),
                          )
                        ],
                      ),
                      SizedBox(
                        height: size.width * 0.1,
                      ),
                      Column(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 25.0, vertical: 10.0
                            ),
                            child: TextFormField(
                              decoration: const InputDecoration(
                                hintText: "Enter your username",
                                labelText: "Username",
                              ),
                              // onchanged, onsaved, autovalidatemode belum
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 25.0, vertical: 10.0
                            ),
                            child: TextFormField(
                              decoration: const InputDecoration(
                                hintText: "Enter your password",
                                labelText: "Password",
                              ),
                              // onchanged, onsaved, autovalidatemode belum
                            ),
                          ),
                          const SizedBox(
                            height: 25,
                          ),
                          Container(
                            height: size.height * 0.08,
                            width:  size.width * 0.8,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(16),
                            ),
                            child : TextButton(
                              onPressed: () {

                              },
                              child: const Text(
                                'Log in',
                                style: TextStyle(
                                  fontSize: 22,
                                  height: 1.5,
                                  fontWeight: FontWeight.bold
                                ),
                              ),
                            )
                          ),
                          const SizedBox(
                            height: 15,
                          ),
                          TextButton(
                            onPressed: () {
                              Navigator.pushNamed(context, Register.id);
                            },
                            child: const Text(
                              "Don't have an account? Sign up",
                              style: TextStyle(
                                fontSize: 16,
                                color: Colors.blue, // You can customize the color
                              ),
                            )
                          ),
                        ],
                      )
                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      )
      );
    }
  }
import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/home/home.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';

class Login extends StatefulWidget{
  const Login({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/login';
  static String id = 'login';

  @override
  State<Login> createState() => _LoginState();

}

class _LoginState extends State<Login>{
    final _formKey = GlobalKey<FormState>();

    @override
    Widget build(BuildContext context){
      Size size = MediaQuery.of(context).size;
      return WillPopScope(
        onWillPop: () async {
        Navigator.popAndPushNamed(context, Home.id);
        return true;
        },
      child : Container(
        child: Form(
          key: _formKey,
          child: Stack(
            children: [
              Scaffold(
                body: SingleChildScrollView(
                  child: Column(
                    children: [
                      // SizedBox(
                      //   height: size.width * 0.1,
                      // ),
                      Stack(
                        children: const [
                          Center(
                            child: Text('Login',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 55,
                              fontWeight: FontWeight.bold
                            ),
                            ),
                          )
                        ],
                      ),
                      SizedBox(
                        height: size.width * 0.1,
                      ),
                      Column(
                        children: [
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 25.0, vertical: 10.0
                            ),
                            child: TextFormField(
                              decoration: InputDecoration(
                                hintText: "Enter your username",
                                labelText: "Username",
                              ),
                              // onchanged, onsaved, autovalidatemode belum
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 25.0, vertical: 10.0
                            ),
                            child: TextFormField(
                              decoration: InputDecoration(
                                hintText: "Enter your password",
                                labelText: "Password",
                              ),
                              // onchanged, onsaved, autovalidatemode belum
                            ),
                          ),
                          const SizedBox(
                            height: 25,
                          ),
                          Container(
                            height: size.height * 0.08,
                            width:  size.width * 0.8,
                            decoration: BoxDecoration(
                              borderRadius: BorderRadius.circular(16),
                            ),
                            child : TextButton(
                              onPressed: () {

                              },
                              child: const Text(
                                'Log in',
                                style: TextStyle(
                                  fontSize: 22,
                                  height: 1.5,
                                  fontWeight: FontWeight.bold
                                ),
                              ),
                            )
                          ),
                          SizedBox(
                            height: 15,
                          ),
                          TextButton(
                            onPressed: () {
                              Navigator.pushNamed(context, Register.id);
                            },
                            child: const Text(
                              "Don't have an account? Sign up",
                              style: TextStyle(
                                fontSize: 16,
                                color: Colors.blue, // You can customize the color
                              ),
                            )
                          ),
                        ],
                      )
                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      )
      );
    }
  }