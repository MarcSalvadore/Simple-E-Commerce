import 'package:flutter/material.dart';
import 'package:frontend_mobile/constant.dart';

class CustomAppBar extends StatelessWidget implements PreferredSizeWidget {
  final String title;

  CustomAppBar({required this.title});

  @override
  Size get preferredSize => Size.fromHeight(kToolbarHeight);

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: Text(
        title,
        style: TextStyle(
          fontWeight: FontWeight.bold,
          color: AppColors.titleColor,
        ),
      ),
      backgroundColor: AppColors.appBarBackgroundColor,
    );
  }
}
