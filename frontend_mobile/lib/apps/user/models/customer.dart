import 'dart:convert';

import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:http/http.dart' as http;

class Customer {
  final String username;
  final String name;
  final String email;
  final String address;
  final String? cartId;
  final String id;
  final int balance;
  final String password;

  const Customer({
    required this.username,
    required this.name,
    required this.email,
    required this.address,
    required this.cartId,
    required this.id,
    required this.balance,
    required this.password
  });

  factory Customer.fromJson(Map<String, dynamic> json) {
    return Customer(
      username: json['username'],
      name: json['name'],
      email: json['email'],
      address: json['address'],
      cartId: json['cartId'],
      id: json['id'],
      balance: json['balance'],
        password: json['password']
    );
  }
}
