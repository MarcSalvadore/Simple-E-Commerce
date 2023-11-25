import 'package:flutter/material.dart';
import '../../home/home.dart';

class UpdateProfilePage extends StatefulWidget {
  const UpdateProfilePage({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/update';
  static String id = 'update-profile';

  @override
  State<UpdateProfilePage> createState() => _UpdateProfilePageState();
}

class _UpdateProfilePageState extends State<UpdateProfilePage> {
  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return Scaffold(
      appBar: AppBar(
        title: Text('Update Profile'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: Column(
                children: [
                  SingleChildScrollView(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 25.0, vertical: 10.0
                    ),
                    child: TextFormField(
                      decoration: const InputDecoration(
                        hintText: "Enter your name",
                        labelText: "Name",
                        
                      ),
                      initialValue: "ujang",
                      // onchanged, onsaved, autovalidatemode belum
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
                      initialValue: "ujang.ganteng123",
                      // onchanged, onsaved, autovalidatemode belum
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
                      initialValue: "ujang.ganteng123@gmail.com",
                      // onchanged, onsaved, autovalidatemode belum
                    ),
                  ),
                  SingleChildScrollView(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 25.0, vertical: 10.0
                    ),
                    child: TextFormField(
                      obscureText: true,
                      decoration: const InputDecoration(
                        hintText: "Enter your password",
                        labelText: "Password",
                      ),
                      initialValue: "ujang12345",
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
                      initialValue: "Jl. bareng yuk",
                      // onchanged, onsaved, autovalidatemode belum
                    ),
                  ),
                  const SizedBox(
                    height: 25,
                  ),
                  TextButton(
                      onPressed: () {

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
}
