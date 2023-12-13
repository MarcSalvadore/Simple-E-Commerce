import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../../home/home.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';
import 'package:http/http.dart' as http;

import '../models/customer.dart';

class UpdateProfilePage extends StatefulWidget {
  const UpdateProfilePage({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/update';
  static String id = 'update-profile';

  @override
  State<UpdateProfilePage> createState() => _UpdateProfilePageState();
}

class _UpdateProfilePageState extends State<UpdateProfilePage> {
  final _formKey = GlobalKey<FormState>();
  final Future<SharedPreferences> _prefs = SharedPreferences.getInstance();
  late Future<Customer> futureCustomer;

  TextEditingController usernameController = TextEditingController();
  TextEditingController nameController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController addressController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  TextEditingController idController = TextEditingController();

  Map<String, dynamic>? decodeJwtToken(String jwtToken) {
    try {
      Map<String, dynamic> decodedToken = JwtDecoder.decode(jwtToken);
      return decodedToken;
    } catch (error) {
      print('Error decoding JWT: $error');
      return null;
    }
  }

  Future<Customer> getUserData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? _jwtToken = prefs.getString('jwtToken');

    Map<String, dynamic>? decodedToken = decodeJwtToken(_jwtToken!);
    String? _userId = decodedToken?['userId'];

    var res = await http.get(
      Uri.parse("http://10.0.2.2:8081/api/user/$_userId"),
      headers: <String, String>{
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
    );
    return Customer.fromJson(jsonDecode(res.body));
  }
  Future<void> updateUserProfile() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? _jwtToken = prefs.getString('jwtToken');

    Map<String, dynamic>? decodedToken = decodeJwtToken(_jwtToken!);
    String? _userId = decodedToken?['userId'];
    Map<String, String> headers = {
      'Content-Type': 'application/json;charset=UTF-8',
      'Authorization': 'Bearer $_jwtToken',
    };

    Map<String, dynamic> requestBody = {
      'name': nameController.text,
      'username': usernameController.text,
      'email': emailController.text,
      'address': addressController.text,
      'password' : passwordController.text,
      'id' : idController.text
    };
    try {
      http.Response response = await http.put(
        Uri.parse("http://10.0.2.2:8081/api/user/$_userId/update"),
        headers: headers,
        body: jsonEncode(requestBody),
      );
      if (response.statusCode == 200) {
        print("User profile updated successfully");
        Navigator.pop(context);
      } else {
        print("Error updating user profile. Status code: ${response.statusCode}");
        print("Response body: ${response.body}");
      }
    } catch (e) {
      print("Error updating user profile: $e");
    }
  }

  @override
  void initState() {
    super.initState();
    futureCustomer = getUserData();
    futureCustomer.then((customer) {
      usernameController.text = customer.username;
      nameController.text = customer.name;
      emailController.text = customer.email;
      addressController.text = customer.address;
      idController.text = customer.id;
      passwordController.text = customer.password;
    });
  }
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Customer>(
      future: futureCustomer,
      builder: (context, AsyncSnapshot<Customer> snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (snapshot.hasError) {
          return Text('Error: ${snapshot.error}');
        } else if (snapshot.hasData) {
          Customer customer = snapshot.data!;
          return Scaffold(
            appBar: const CustomAppBar(title: "APAPEDIA"),
            drawer: buildDrawer(context),
            body: Padding(
              padding: EdgeInsets.all(16.0),
              child: Form(
                key: _formKey,
                child: Column(
                  children: [
                    Visibility(
                        visible : false,
                      child: TextFormField(
                        controller: idController,
                      ),
                    ),
                    Visibility(
                      visible : false,
                      child: TextFormField(
                        controller: passwordController,
                      ),
                    ),
                    ListTile(
                      title: Text("Update Profile", style: TextStyle(fontWeight: FontWeight.bold)),
                    ),
                    SingleChildScrollView(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 25.0, vertical: 10.0
                      ),
                      child: TextFormField(
                        decoration: const InputDecoration(
                          hintText: "Enter your name",
                          labelText: "Name",
                        ),
                        controller: nameController,
                      ),
                    ),
                    SingleChildScrollView(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 25.0, vertical: 10.0
                      ),
                      child: TextFormField(
                        decoration: const InputDecoration(
                          hintText: "Enter your username",
                          labelText: "Username",
                        ),
                        controller: usernameController,
                      ),
                    ),
                    SingleChildScrollView(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 25.0, vertical: 10.0
                      ),
                      child: TextFormField(
                        decoration: const InputDecoration(
                          hintText: "Enter your email",
                          labelText: "Email",
                        ),
                        controller: emailController,
                        // onchanged, onsaved, autovalidatemode belum
                      ),
                    ),
                    SingleChildScrollView(
                      padding: const EdgeInsets.symmetric(
                          horizontal: 25.0, vertical: 10.0
                      ),
                      child: TextFormField(
                        decoration: const InputDecoration(
                          hintText: "Enter your address",
                          labelText: "Address",
                        ),
                        controller: addressController,
                        // onchanged, onsaved, autovalidatemode belum
                      ),
                    ),
                    TextButton(
                      onPressed: () {
                        updateUserProfile();
                      },
                      child: const Text(
                        'Save',
                        style: TextStyle(
                            fontSize: 22,
                            height: 1.5,
                            fontWeight: FontWeight.bold
                        ),
                      ),
                    ),
                    ElevatedButton(
                      onPressed: () {
                        // Untuk berpindah ke Home page
                        Navigator.push(
                          context,
                          MaterialPageRoute(builder: (context) => Home(title: 'home')),
                        );
                      },
                      style: ElevatedButton.styleFrom(
                        primary: Colors.red, // Atur warna latar belakang menjadi merah
                      ),
                      child: Text("Cancel"),
                    ),
                  ],
                ),
              ),
            ),
          );
        }
        else {
          return Center(
            child: Text('Data not available'),
          );
        }
      },
    );
  }
}
