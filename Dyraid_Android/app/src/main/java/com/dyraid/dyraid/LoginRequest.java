package com.dyraid.dyraid;

import android.util.Base64;

import com.android.volley.AuthFailureError;
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
    //private static final String LOGIN_REQUEST_URL = "http://localhost:8000/home/rest-auth/login";//Remove hardcode
    private static final String LOGIN_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/login/";//Remove hardcode
    // private static final String LOGIN_REQUEST_URL = "http://192.168.56.56:8000/userlog/api/";//Remove hardcode
    private Map<String, String> params;

    public LoginRequest(String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("username", "adaeze");
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> params = new HashMap<String, String>();
//        String creds = String.format("%s:%s", "<username>", "<password>");
//        String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
//        //String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.NO_WRAP);
//        //params.put("Content-Type", "application/json");
//        params.put("Content-Type", "application/form-data");
//        params.put("Authorization", auth);
//        //params.put("username" , "*****" );
//        //params.put("password" , "*****" );
//        return params;
//    }
//
//    @Override
//    public String getBodyContentType()
//    {
//        return "application/xml";
//    }

//    @Override
//    public Map<String, String> getHeaders() throws AuthFailureError {
//        HashMap<String, String> params = new HashMap<String, String>();
//        String auth = "JWT " + token; // token you will get after successful login
//        params.put("Authorization", auth);
//        return params;
//    }
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

