import 'package:flutter/material.dart';

class Order_History extends StatefulWidget {
  const Order_History({Key? key, required String title}) : super(key: key);

class MyApp extends StatelessWidget {
  @override
  State<Order_History> createState() => _OrderHistoryState();
}

class _OrderHistoryState extends State<Order_History> {
  // Menyimpan status order sebagai integer (0-5)
  List<int> orderStatus = [1, 0]; // Contoh status order, sesuaikan dengan data sebenarnya

  // Dropdown items for order status
  List<String> statusOptions = [
    "Menunggu konfirmasi penjual",
    "Dikonfirmasi penjual",
    "Menunggu kurir",
    "Dalam perjalanan",
    "Barang diterima",
    "Selesai",
  ];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: "Order Page"),
      drawer: buildDrawer(context),
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          child: Card(
            elevation: 4,
            child: Column(
              children: [
                const ListTile(
                  title: Text("Order History", style: TextStyle(fontWeight: FontWeight.bold)),
                ),
                _buildOrderItem(0),
                _buildOrderItem(1),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Implementasi untuk melihat detail order atau navigasi ke halaman lain
                  },
                  child: const Text("View Details"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  Widget _buildOrderItem(int index) {
    return ListTile(
      title: Text("Order #${index + 1}"),
      subtitle: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text("Status: ${_getStatusText(orderStatus[index])}"),
          _buildStatusDropdown(index),
        ],
      ),
      trailing: ElevatedButton(
        onPressed: () {
          // Tidak perlu panggil _updateOrderStatus() di sini
        },
        child: const Text("Update Status"),
      ),
    );
  }

  Widget _buildStatusDropdown(int index) {
    return DropdownButton<int>(
      value: orderStatus[index],
      items: statusOptions
          .asMap()
          .entries
          .map(
            (entry) => DropdownMenuItem<int>(
              value: entry.key,
              child: Text(entry.value),
            ),
          )
          .toList(),
      onChanged: (value) {
        _updateOrderStatus(index, value!);
      },
    );
  }

  void _updateOrderStatus(int index, int value) {
    // Implementasi logika untuk mengubah status order berdasarkan dropdown value
    setState(() {
      orderStatus[index] = value;
    });
  }

  String _getStatusText(int status) {
    return statusOptions[status];
  }
}
