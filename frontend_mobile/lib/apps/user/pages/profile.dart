import 'package:flutter/material.dart';
import 'withdraw.dart'; // Import the Withdraw screen

class Profile extends StatefulWidget {
  const Profile({Key? key, required String title}) : super(key: key);

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Profile"),
      ),
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          child: Card(
            elevation: 4, // Add elevation to the card for a shadow effect
            child: Column(
              children: [
                const ListTile(
                  title: Text("Profile", style: TextStyle(fontWeight: FontWeight.bold)),
                ),
                const ListTile(
                  title: Text("Name"),
                  subtitle: Text(": ujang"),
                ),
                const ListTile(
                  title: Text("Email"),
                  subtitle: Text(": ujang.ganteng123@gmail.com"),
                ),
                const ListTile(
                  title: Text("Address"),
                  subtitle: Text(": Jl. bareng yuk"),
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Implementasi code untuk Edit Profile page
                  },
                  child: const Text("Edit Profile"),
                ),
                const ListTile(
                  title: Text("Apapay", style: TextStyle(fontWeight: FontWeight.bold)),
                ),
                const ListTile(
                  title: Text("Balance"),
                  subtitle: Text(": Rp 9.000.000"),
                ),
                ElevatedButton(
                  onPressed: () {
                    // Untuk berpindah ke Withdraw page
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const Withdraw(title: 'Withdraw')),
                    );
                  },
                  child: const Text("Withdraw"),
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Implementasi code untuk sign-out user
                  },
                  child: const Text("Sign Out"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
