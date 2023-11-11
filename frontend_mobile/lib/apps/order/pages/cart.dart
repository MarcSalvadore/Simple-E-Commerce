import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Cart extends StatefulWidget{
  const Cart({Key? key, required String title}) : super(key: key);
  // static const ROUTE_NAME = '/';

  @override
  State<Cart> createState() => _CartState();

}

class _CartState extends State<Cart>{
    @override
    Widget build(BuildContext context){
      return Scaffold( //bebas
        appBar: const CustomAppBar(title: "Cart"),
        drawer: buildDrawer(context), 

      );
    }
  }