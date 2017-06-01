package com.dyraid.dyraid;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekaokoro on 6/1/17.
 */

public class BasicSignUpRequest extends StringRequest {

    private static final String SIGNUP_REQUEST_URL = AppConfig.SIGNUP_REQUEST_URL;
    private Map<String, String> params;

    public BasicSignUpRequest(String username, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.POST, SIGNUP_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
