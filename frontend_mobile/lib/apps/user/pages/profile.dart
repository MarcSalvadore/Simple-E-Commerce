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
        title: Text("Profile"),
      ),
      body: Center(
        child: Container(
          padding: const EdgeInsets.all(20),
          child: Card(
            elevation: 4, // Add elevation to the card for a shadow effect
            child: Column(
              children: [
                ListTile(
                  title: Text("Profile", style: TextStyle(fontWeight: FontWeight.bold)),
                ),
                ListTile(
                  title: Text("Name"),
                  subtitle: Text(": ujang"),
                ),
                ListTile(
                  title: Text("Email"),
                  subtitle: Text(": ujang.ganteng123@gmail.com"),
                ),
                ListTile(
                  title: Text("Address"),
                  subtitle: Text(": Jl. bareng yuk"),
                ),
                SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Implementasi code untuk Edit Profile page
                  },
                  child: Text("Edit Profile"),
                ),
                ListTile(
                  title: Text("Apapay", style: TextStyle(fontWeight: FontWeight.bold)),
                ),
                ListTile(
                  title: Text("Balance"),
                  subtitle: Text(": Rp 9.000.000"),
                ),
                ElevatedButton(
                  onPressed: () {
                    // Untuk berpindah ke Withdraw page
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => Withdraw(title: 'Withdraw')),
                    );
                  },
                  child: Text("Withdraw"),
                ),
                SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Implementasi code untuk sign-out user
                  },
                  child: Text("Sign Out"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
