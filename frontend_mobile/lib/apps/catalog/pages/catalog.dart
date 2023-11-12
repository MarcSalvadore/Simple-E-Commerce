import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Catalog extends StatefulWidget{
  const Catalog({Key? key, required String title}) : super(key: key);
  // static const ROUTE_NAME = '/';

  @override
  State<Catalog> createState() => _CatalogState();

}

class _CatalogState extends State<Catalog>{
    @override
    Widget build(BuildContext context){
      return Scaffold( //bebas
        appBar: const CustomAppBar(title: "Catalog"),
        drawer: buildDrawer(context),

      );
    }
  }
import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Catalog extends StatefulWidget{
  const Catalog({Key? key, required String title}) : super(key: key);
  // static const ROUTE_NAME = '/';

  @override
  State<Catalog> createState() => _CatalogState();

}

class _CatalogState extends State<Catalog>{
    @override
    Widget build(BuildContext context){
      return Scaffold( //bebas
        appBar: CustomAppBar(title: "Catalog"),
        drawer: buildDrawer(context),

      );
    }
  }