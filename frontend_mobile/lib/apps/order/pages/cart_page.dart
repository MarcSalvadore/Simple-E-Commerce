import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/order/pages/order_history.dart';
import 'package:frontend_mobile/components/drawer.dart';
class Cart_Page extends StatefulWidget {
  const Cart_Page({Key? key, required String title}) : super(key: key);

  @override
  _CartPageState createState() => _CartPageState();
}

class _CartPageState extends State<Cart_Page> {
  List<CartItem> cartItems = [
    CartItem(id: 1, productName: 'Product A', quantity: 2, price: 20.0),
    CartItem(id: 2, productName: 'Product B', quantity: 1, price: 15.0),
    // Add more items as needed
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Cart'),
      ),
      body: ListView.builder(
        itemCount: cartItems.length,
        itemBuilder: (context, index) {
          return _buildCartItem(cartItems[index]);
        },
      ),
      bottomNavigationBar: BottomAppBar(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              ElevatedButton(
                onPressed: () {
                  _placeOrder();
                },
                child: const Text('Order History'),
              ),
            ],
          ),
        ),
      ),
    );
  }

  Widget _buildCartItem(CartItem cartItem) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: ListTile(
        title: Text(cartItem.productName),
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Quantity: ${cartItem.quantity}'),
            Text('Total Price: \$${cartItem.quantity * cartItem.price}'),
          ],
        ),
        trailing: Row(
          mainAxisSize: MainAxisSize.min,
          children: [
            IconButton(
              onPressed: () {
                _incrementQuantity(cartItem);
              },
              icon: const Icon(Icons.add),
            ),
            IconButton(
              onPressed: () {
                _decrementQuantity(cartItem);
              },
              icon: const Icon(Icons.remove),
            ),
          ],
        ),
      ),
    );
  }

  void _incrementQuantity(CartItem cartItem) {
    setState(() {
      cartItem.quantity++;
    });
  }

  void _decrementQuantity(CartItem cartItem) {
    setState(() {
      if (cartItem.quantity > 1) {
        cartItem.quantity--;
      }
    });
  }

  void _placeOrder() {
    print('Placing order for items in the cart');
    // Implement logic to place the order and navigate to order history page
        // Navigate to order history page
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const Order_History(title: 'Oder History',)),
    );
  }
}

class CartItem {
  final int id;
  final String productName;
  int quantity;
  final double price;

  CartItem({
    required this.id,
    required this.productName,
    required this.quantity,
    required this.price,
  });
}

