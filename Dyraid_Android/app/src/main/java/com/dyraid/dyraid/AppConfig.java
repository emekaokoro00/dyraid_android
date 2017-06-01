package com.dyraid.dyraid;

/**
 * Created by emekaokoro on 6/1/17.
 */

public class AppConfig {

    // Server user login url //Remove hardcode
    //private static final String LOGIN_REQUEST_URL = "http://localhost:8000/home/rest-auth/login";
    public static final String LOGIN_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/login/";

    // Server user register url
    public static String SIGNUP_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/registration/";
}