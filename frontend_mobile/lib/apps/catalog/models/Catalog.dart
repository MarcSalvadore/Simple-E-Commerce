// To parse this JSON data, do
//
//     final catalog = catalogFromJson(jsonString);

import 'dart:convert';

List<Catalog> catalogFromJson(String str) => List<Catalog>.from(json.decode(str).map((x) => Catalog.fromJson(x)));

String catalogToJson(List<Catalog> data) => json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class Catalog {
    String id;
    String seller;
    int price;
    String productName;
    String productDescription;
    Category category;
    int stock;
    bool isDeleted;

    Catalog({
        required this.id,
        required this.seller,
        required this.price,
        required this.productName,
        required this.productDescription,
        required this.category,
        required this.stock,
        required this.isDeleted,
    });

    factory Catalog.fromJson(Map<String, dynamic> json) => Catalog(
        id: json["id"],
        seller: json["seller"],
        price: json["price"],
        productName: json["productName"],
        productDescription: json["productDescription"],
        category: Category.fromJson(json["category"]),
        stock: json["stock"],
        isDeleted: json["isDeleted"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "seller": seller,
        "price": price,
        "productName": productName,
        "productDescription": productDescription,
        "category": category.toJson(),
        "stock": stock,
        "isDeleted": isDeleted,
    };
}

class Category {
    String id;
    String name;

    Category({
        required this.id,
        required this.name,
    });

    factory Category.fromJson(Map<String, dynamic> json) => Category(
        id: json["id"],
        name: json["name"],
    );

    Map<String, dynamic> toJson() => {
        "id": id,
        "name": name,
    };
}
