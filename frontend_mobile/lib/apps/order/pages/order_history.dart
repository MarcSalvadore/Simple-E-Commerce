import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Order extends StatefulWidget{
  const Order({Key? key, required String title}) : super(key: key);
  // static const ROUTE_NAME = '/';

  @override
  State<Order> createState() => _OrderState();

}

class _OrderState extends State<Order>{
    @override
    Widget build(BuildContext context){
      return Scaffold( //bebas
        appBar: CustomAppBar(title: "Order History"),
        drawer: buildDrawer(context), 

      );
    }
  }