import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/catalog/pages/catalog.dart';
import 'package:frontend_mobile/apps/order/pages/cart.dart';
import 'package:frontend_mobile/apps/order/pages/cart_page.dart';
import 'package:frontend_mobile/apps/order/pages/order_history.dart';
import 'package:frontend_mobile/apps/user/pages/login.dart';
import 'package:frontend_mobile/apps/user/pages/profile.dart';
import 'package:frontend_mobile/apps/user/pages/register.dart';

Drawer buildDrawer(BuildContext context){
  return Drawer(
    child: Container(
      child: ListView(
        padding: const EdgeInsets.only(top: 60.0, left: 30.0),
        children: [
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Log in'),
            leading: const Icon(Icons.login),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Login(
                  title: "Log in"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Catalog'),
            leading: const Icon(Icons.library_books),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Catalog(
                  title: "Catalog"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Cart'),
            leading: const Icon(Icons.shopping_cart),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Cart(
                  title: "Cart"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Order History'),
            leading: const Icon(Icons.shopping_bag),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Order_History(
                  title: "Order History"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Cart Page'),
            leading: const Icon(Icons.shopping_bag),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Cart_Page(
                  title: "Cart Page"
                )),
              );
            },
          ),
          Divider(color: Colors.white),
          ListTile(
            title: const Text('Profile'),
            leading: const Icon(Icons.person),
            onTap: (){
              Navigator.pushReplacement(
                context, 
                MaterialPageRoute(builder: (context) => const Profile(
                  title: "Profile"
                )),
              );
            },
          ),
          
        ],
      ),
    ),
  );
}