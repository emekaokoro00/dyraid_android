package com.dyraid.dyraid;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekaokoro on 4/2/17.
 */

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://localhost:8000/home/rest-auth/login";//Remove hardcode
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", "adaeze");
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

//public class LoginRequest extends JsonObjectRequest {
//    private static final String LOGIN_REQUEST_URL = "http://localhost:8000/home/rest-auth/login";//Remove hardcode
//    private Map<String, String> params;
//
//    public LoginRequest(String email, String password, Response.Listener<JSONObject> listener){
//        super(Request.Method.POST, LOGIN_REQUEST_URL, null, listener, null);
//        params = new HashMap<>();
//        params.put("username", "adaeze");
//        params.put("email", email);
//        params.put("password", password);
//    }
//
//    @Override
//    public Map<String, String> getParams() {
//        return params;
//    }
//}

