import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Home extends StatelessWidget{
  const Home({super.key, required String title});
  static String id = "home";

  @override
  Widget build(BuildContext context){
    return Scaffold(
      appBar: CustomAppBar(title: "APAPEDIA"),
      drawer: buildDrawer(context),
    );
  }
}