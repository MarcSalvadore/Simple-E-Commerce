import 'package:flutter/material.dart';
import 'package:frontend_mobile/apps/user/pages/change_password.dart';
import 'package:frontend_mobile/apps/user/pages/profile.dart';
import 'package:frontend_mobile/apps/user/pages/update-profile.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;

import '../../catalog/pages/catalog.dart';

class SettingsPage extends StatelessWidget {
  static const ROUTE_NAME = '/settings';

  Future<void> deleteAccount(BuildContext context) async {
    try {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      String? jwtToken = prefs.getString('jwtToken');

      Map<String, dynamic>? decodedToken = JwtDecoder.decode(jwtToken!);
      String? userId = decodedToken?['userId'];

      final response = await http.delete(
        Uri.parse("http://10.0.2.2:8081/api/user/$userId/delete"),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $jwtToken'
        },
      );

      if (response.statusCode == 200) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Account deleted successfully'),
            duration: const Duration(seconds: 3),
          ),
        );
        await prefs.remove('jwtToken');
        Navigator.pop(context, MaterialPageRoute(builder: (context) => Catalog(title: 'Catalog')));
      } else {
        print('Failed to delete account. Status code: ${response.statusCode}');
        print('Response body: ${response.body}');
      }
    } catch (e) {
      print('Error: $e');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Settings'),
      ),
      body: ListView(
        children: [
          Card(
            child: ListTile(
              title: Text('Edit Profile'),
              onTap: () {
                Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(builder: (context) => const UpdateProfilePage(title: "Update Profile")),
                );
              },
            ),
          ),
          Divider(),
          Card(
            child: ListTile(
              title: Text('Change Password'),
              onTap: () {
                Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(builder: (context) => const ChangePasswordPage()),
                );
              },
            ),
          ),
          Divider(),
          Card(
            child: ListTile(
              title: Text('Delete Account'),
              onTap: () {
                deleteAccount(context);
              },
            ),
          ),
        ],
      ),
    );
  }
}
