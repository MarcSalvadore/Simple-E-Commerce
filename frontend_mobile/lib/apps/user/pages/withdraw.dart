import 'package:flutter/material.dart';

class Withdraw extends StatefulWidget {
  const Withdraw({Key? key, required String title}) : super(key: key);

  @override
  State<Withdraw> createState() => _WithdrawState();
}

class _WithdrawState extends State<Withdraw> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Withdraw"),
      ),
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          child: Column(
            children: [
              const Text("Withdraw APAPAY"),
              const SizedBox(height: 20),
              const TextField(
                decoration: InputDecoration(
                  hintText: "Enter amount",
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {
                  // Handle withdrawal logic here
                },
                child: const Text("Withdraw"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
