import 'package:flutter/material.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Customer Cart',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: CartPage(),
    );
  }
}

class CartPage extends StatefulWidget {
  @override
  _CartPageState createState() => _CartPageState();
}

class _CartPageState extends State<CartPage> {
  int itemCount = 0;

  void _incrementItemCount() {
    setState(() {
      itemCount++;
    });
  }

  void _decrementItemCount() {
    if (itemCount > 0) {
      setState(() {
        itemCount--;
      });
    }
  }

  void _placeOrder() {
    // Implement order placement logic here
    // This is where you would navigate to the order history page
    // For simplicity, we'll print a message to the console
    print('Placing order for $itemCount items');
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cart'), // Use const here
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Items in Cart:',
              style: TextStyle(fontSize: 20),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                ElevatedButton(
                  onPressed: _decrementItemCount,
                  child: const Text('-'), // Use const here
                ),
                const SizedBox(width: 20),
                Text(
                  '$itemCount',
                  style: TextStyle(fontSize: 18),
                ),
                const SizedBox(width: 20),
                ElevatedButton(
                  onPressed: _incrementItemCount,
                  child: const Text('+'), // Use const here
                ),
              ],
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _placeOrder,
              child: const Text('Order'), // Use const here
            ),
          ],
        ),
      ),
    );
  }
}
