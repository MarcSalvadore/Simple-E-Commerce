import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/user/pages/login.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';

Drawer buildDrawer(BuildContext context){
  return Drawer(
    child: Container(
      child: ListView(
        padding: const EdgeInsets.only(top: 60.0, left: 30.0),
        children: [
          ListTile(
            title: const Text('Register'),
            leading: const Icon(Icons.person),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Register(
                  title: "Sign Up"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Log in'),
            leading: const Icon(Icons.person),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Login(
                  title: "Log in"
                )),
              );
            },
          ),
        ],
      ),
    ),
  );
}