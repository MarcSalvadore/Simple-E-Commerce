package com.apapedia.frontend_webapp.setting;

public class Setting {
    
    final public static String CLIENT_BASE_URL = "http://apap-08.cs.ui.ac.id";
    final public static String CLIENT_LOGIN = CLIENT_BASE_URL + "/validate-ticket";
    final public static String CLIENT_LOGOUT = CLIENT_BASE_URL + "/logout";
    final public static String SERVER_BASE_URL = "https://sso.ui.ac.id/cas2";
    final public static String SERVER_LOGIN = SERVER_BASE_URL + "/login?service=";
    final public static String SERVER_LOGOUT = SERVER_BASE_URL + "/logout?url=";
    final public static String SERVER_VALIDATE_TICKET = SERVER_BASE_URL + "/serviceValidate?ticket=%s&service=%s";
    
    final public static String SERVER_USER_URL = "http://user-web:8081";
    final public static String SERVER_CATALOG_URL = "http://catalog-web:8082";
    final public static String SERVER_ORDER_URL = "http://order-web:8083";
    final public static String SERVER_IMAGE_URL = "http://apap-083.cs.ui.ac.id/api/image/";
}
