import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/catalog/pages/catalog.dart';
import 'package:frontend_mobile/apps/user/models/customer.dart';
import 'package:frontend_mobile/apps/user/pages/login.dart';
import 'package:frontend_mobile/apps/user/pages/topup.dart';
import 'package:frontend_mobile/apps/user/pages/update-profile.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';

class Profile extends StatefulWidget {
  const Profile({Key? key, required String title}) : super(key: key);

  @override
  State<Profile> createState() => _ProfileState();
}

class _ProfileState extends State<Profile> {
  late Future<Customer> futureCustomer;
  final Future<SharedPreferences> _prefs = SharedPreferences.getInstance();

  Future<Customer> getUserData() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? _jwtToken = prefs.getString('jwtToken');
    String? _userId = prefs.getString('userId');
    var res = await http.get(
      Uri.parse("https://apap-082.cs.ui.ac.id/api/user/$_userId"),
      headers: <String, String>{
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
    );
    return Customer.fromJson(jsonDecode(res.body));
  }

  Future<void> _logout() async {
    final SharedPreferences prefs = await _prefs;
    setState(() {
      prefs.remove('jwtToken');
      prefs.remove('username');
      prefs.remove('userId');
    });
    Navigator.push(context, MaterialPageRoute(builder: (context) => Catalog(title: 'Catalog')));
  }

  @override
  void initState() {
    super.initState();
    futureCustomer = getUserData();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Customer>(
      future: futureCustomer,
      builder: (context, AsyncSnapshot<Customer> snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return Center(
            child: CircularProgressIndicator(),
          );
        } else if (snapshot.hasError) {
          return Text('Error: ${snapshot.error}');
        } else if (snapshot.hasData) {
          Customer customer = snapshot.data!;
          return Scaffold(
            appBar: AppBar(
              title: Text("Hello, ${customer.username}"),
              centerTitle: true,
              leading: IconButton(icon: Icon(Icons.arrow_back_ios), onPressed: () {
                Navigator.push(
                  context, MaterialPageRoute(builder: (context) => const Catalog(title: 'Catalog',)),
                );
              },),
            ),
            body: Center(
              child: SingleChildScrollView(
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    CircleAvatar(
                      radius: 60,
                      backgroundColor: Colors.grey[300],
                      child: const Icon(
                        Icons.account_circle,
                        size: 100,
                      ),
                    ),
                    SizedBox(height: 20),
                    Card(
                      elevation: 4,
                      child: Padding(
                        padding: const EdgeInsets.all(20),
                        child: Column(
                          mainAxisAlignment: MainAxisAlignment.center,
                          crossAxisAlignment: CrossAxisAlignment.center,
                          children: [
                            Text(
                              "Profile",
                              style: TextStyle(
                                fontWeight: FontWeight.bold,
                                fontSize: 20,
                              ),
                            ),
                            SizedBox(height: 10),
                            buildProfileInfo("Name", "${customer.name}"),
                            buildProfileInfo("Email", "${customer.email}"),
                            buildProfileInfo("Address", "${customer.address}"),
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
                            Divider(),
                            Text(
                              "Apapay",
                              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
                            ),
                            buildProfileInfo("Balance", "${customer.balance}"),
                            ElevatedButton(
                              onPressed: () {
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
                                _logout();
                              },
                              child: Text("Sign Out"),
                            ),
                          ],
                        ),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          );
        }
        else {
          return Center(
            child: Text('Data not available'),
          );
        }
      },
    );
  }

  Widget buildProfileInfo(String title, String value) {
    return ListTile(
      title: Text(title),
      subtitle: Text(value),
    );
  }
}
