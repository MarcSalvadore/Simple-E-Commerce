import 'dart:io';

import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';
import 'detailProduk.dart'; // Import halaman detailProduk.dart

class Catalog extends StatefulWidget {

  const Catalog({Key? key, required String title}) : super(key: key);
  static const ROUTE_NAME = '/catalog';
  static String id = 'catalog';


  @override
  State<Catalog> createState() => _CatalogState();
}

class _CatalogState extends State<Catalog> {
  String productName = "";
  int price = 0;
  late File imageFile;

  List<Map<String, dynamic>> catalogData = [
    {
      'name': 'Stone Island x New Balance',
      'price': '\Rp. 5.900.000',
      'image':
          'https://cdn.webshopapp.com/shops/314036/files/424123213/1280x1000x3/adidas-new-balance-574-legacy-stone-island-steel-b.jpg',
    },
    {
      'name': 'Iphone 15',
      'price': '\Rp. 16.499.000',
      'image':
          'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-15-model-unselect-gallery-2-202309_GEO_US_FMT_WHH?wid=1280&hei=492&fmt=p-jpg&qlt=80&.v=1692925252704',
    },
    // Add more catalog items as needed
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: CustomAppBar(title: "Catalog"),
      drawer: buildDrawer(context),
      body: ListView.builder(
        itemCount: catalogData.length,
        itemBuilder: (BuildContext context, int index) {
          return Card(
            elevation: 3,
            margin: EdgeInsets.fromLTRB(10, 10, 10, 0),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(10),
            ),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                GestureDetector(
                  onTap: () {
                    // Navigasi ke halaman Product Detail
                    Navigator.push(
                      context,
                      MaterialPageRoute(
                        builder: (context) => ProductDetail(
                          productName: catalogData[index]['name'],
                          productPrice: catalogData[index]['price'],
                          productImage: catalogData[index]['image'],
                        ),
                      ),
                    );
                  },
                  child: ClipRRect(
                    borderRadius:
                        BorderRadius.vertical(top: Radius.circular(10)),
                    child: Image.network(
                      catalogData[index]['image'],
                      height: 150,
                      fit: BoxFit.cover,
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        catalogData[index]['name'],
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      SizedBox(height: 8),
                      Text(
                        catalogData[index]['price'],
                        style: TextStyle(fontSize: 16, color: Colors.green),
                      ),
                      SizedBox(height: 16),
                      Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          ElevatedButton.icon(
                            onPressed: () {
                              // Logika untuk menambahkan produk ke keranjang
                              // (Anda dapat menambahkan logika Anda di sini)
                              ScaffoldMessenger.of(context).showSnackBar(
                                SnackBar(
                                  content: Text('Product added to Cart'),
                                ),
                              );
                            },
                            icon: Icon(Icons.shopping_cart),
                            label: Text('Add to Cart'),
                          ),
                          ElevatedButton.icon(
                            onPressed: () {
                              // Logika untuk menambahkan produk ke histori pesanan
                              // (Anda dapat menambahkan logika Anda di sini)
                              ScaffoldMessenger.of(context).showSnackBar(
                                SnackBar(
                                  content:
                                      Text('Product added to Order History'),
                                ),
                              );
                              // Navigasi ke halaman Order History
                              Navigator.push(
                                context,
                                MaterialPageRoute(
                                  builder: (context) => OrderHistory(),
                                ),
                              );
                            },
                            icon: Icon(Icons.shopping_bag),
                            label: Text('Buy Now'),
                          ),
                        ],
                      ),
                    ],
                  ),
                ),
              ],
            ),
          );
        },
      ),
    );
  }
}

// ... (kode lainnya)

// Halaman Detail Produk
class ProductDetail extends StatelessWidget {
  final String productName;
  final String productPrice;
  final String productImage;

  const ProductDetail({
    Key? key,
    required this.productName,
    required this.productPrice,
    required this.productImage,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return DetailProduk(); // Gunakan halaman DetailProduk
  }
}


// Halaman Histori Pesanan
class OrderHistory extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Order History'),
      ),
      body: Center(
        child: Text('Order History Page'),
      ),
    );
  }
}