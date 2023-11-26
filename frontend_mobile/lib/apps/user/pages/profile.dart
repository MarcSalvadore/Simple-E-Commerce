import 'package:flutter/material.dart';
import 'topup.dart'; // Import the Withdraw screen
import 'update-profile.dart'; // Import the Update Profile screen
import 'package:frontend_mobile/components/appbar.dart';
import 'package:frontend_mobile/components/drawer.dart';

class Profile extends StatefulWidget {
  const Profile({Key? key, required String title}) : super(key: key);

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(title: "APAPEDIA"),
      drawer: buildDrawer(context),
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
                    Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => UpdateProfilePage(title: 'UpdateProfilePage')),
                    );
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
                      MaterialPageRoute(builder: (context) => TopUp(title: 'TopUp')),
                    );
                  },
                  child: Text("Top Up"),
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
