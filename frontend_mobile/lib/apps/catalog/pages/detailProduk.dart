import 'package:flutter/material.dart';

class DetailProduk extends StatelessWidget {
  const DetailProduk({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Product Detail'),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image.network(
              'https://cdn.webshopapp.com/shops/314036/files/424123213/1280x1000x3/adidas-new-balance-574-legacy-stone-island-steel-b.jpg',
              height: 250,
              width: double.infinity,
              fit: BoxFit.cover,
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    'Penjual: John Doe',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Product Name: Stone Island x New Balance',
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Harga: \Rp. 5.900.000',
                    style: TextStyle(fontSize: 18, color: Colors.green),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Category: Fashion',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Product Description: Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam eget lectus at sapien convallis rhoncus.',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 8),
                  Text(
                    'Stock: 10',
                    style: TextStyle(fontSize: 16),
                  ),
                  SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          // Logika untuk menambahkan produk ke keranjang
                          // (Anda dapat menambahkan logika Anda di sini)
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text('Product added to Cart'),
                            ),
                          );
                        },
                        child: Text('Add to Cart'),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          // Logika untuk menambahkan produk ke histori pesanan
                          // (Anda dapat menambahkan logika Anda di sini)
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text('Product added to Order History'),
                            ),
                          );
                          // Navigasi ke halaman Order History
                          Navigator.pushReplacementNamed(context, '/orderHistory');
                        },
                        child: Text('Buy Now'),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
