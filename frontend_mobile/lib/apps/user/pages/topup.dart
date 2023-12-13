import 'package:flutter/material.dart';
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class TopUp extends StatefulWidget {
  const TopUp({Key? key, required String title}) : super(key: key);

  @override
  State<TopUp> createState() => _TopUpState();
}

class _TopUpState extends State<TopUp> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
       appBar: const CustomAppBar(title: "APAPEDIA"),
      drawer: buildDrawer(context),
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          child: Column(
            children: [
              const Text("Top Up APAPAY"),
              const SizedBox(height: 20),
              const TextField(
                decoration: InputDecoration(
                  hintText: "Enter amount",
                ),
              ),
              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: () {

                },
                child: const Text("Top Up"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
