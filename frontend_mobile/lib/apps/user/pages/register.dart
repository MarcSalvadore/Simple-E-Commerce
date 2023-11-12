import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/home/home.dart';

class Register extends StatefulWidget{
  const Register({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/register';
  static String id = 'register';

  @override
  State<Register> createState() => _RegisterState();

}

class _RegisterState extends State<Register>{
    final _formKey = GlobalKey<FormState>();

    @override
    Widget build(BuildContext context){
      Size size = MediaQuery.of(context).size;
      return WillPopScope(
        onWillPop: () async {
        Navigator.popAndPushNamed(context, Home.id);
        return true;
        },
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
                            child: Text('Sign Up',
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
                                hintText: "Enter your name",
                                labelText: "Name",
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
                                hintText: "Enter your email",
                                labelText: "Email",
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
                          Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 25.0, vertical: 10.0
                            ),
                            child: TextFormField(
                              decoration: InputDecoration(
                                hintText: "Enter your address",
                                labelText: "Address",
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
                                'Submit',
                                style: TextStyle(
                                  fontSize: 22,
                                  height: 1.5,
                                  fontWeight: FontWeight.bold
                                ),
                              ),
                            )
                          )
                        ],
                      )
                    ],
                  ),
                ),
              )
            ],
          ),
        ),
      );
    }
  }