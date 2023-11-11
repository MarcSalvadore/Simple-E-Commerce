import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Profile extends StatefulWidget{
  const Profile({Key? key, required String title}) : super(key: key);
  // static const ROUTE_NAME = '/';

  @override
  State<Profile> createState() => _ProfileState();

}

class _ProfileState extends State<Profile>{
    @override
    Widget build(BuildContext context){
      return Scaffold( //bebas
        appBar: CustomAppBar(title: "Profile"),
        drawer: buildDrawer(context), 

      );
    }
  }