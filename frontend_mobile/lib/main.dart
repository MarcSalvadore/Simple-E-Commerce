import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/home/home.dart';
import 'package:frontend_mobile/apps/user/pages/login.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';
void main() {
  runApp(const Apapedia());
}

class Apapedia extends StatelessWidget {
  const Apapedia({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Apapedia',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
        textTheme: const TextTheme(
        bodyMedium: TextStyle(
        ),
        ),
      ),
      initialRoute: Home.id,
      routes: {
        Home.id: (context) => Home(title: 'Home'),
        Login.id: (context) => Login(title: 'Login'),
        Register.id: (context) => Register(title: 'Register'),
      },
    );
  }
}
