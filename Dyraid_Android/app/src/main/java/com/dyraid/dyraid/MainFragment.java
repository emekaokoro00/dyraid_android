package com.dyraid.dyraid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dyraid.dyraid.items.user.User;
import com.dyraid.dyraid.items.userlog.UserLog;
import com.dyraid.dyraid.items.userlog.UserLogAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekaokoro on 5/22/17.
 */

public class MainFragment extends Fragment {

    private static final String USERDETAILS_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/user/";//Remove hardcode
    private static final String USERLOGLIST_REQUEST_URL = "http://192.168.56.56:8000/userlog/api/";//Remove hardcode

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        final View header = inflater.inflate(R.layout.userlog_header, null);

        final User currentUser = new User();
        final FragmentActivity currentActivity = getActivity();

        Main2ActivityDisplay(rootView, header, currentUser, currentActivity);

        return rootView;
    }


    static void Main2ActivityDisplay(View rootView, final View header, final User currentUser, final FragmentActivity currentActivity) {

        final TextView nameTextView = (TextView) rootView.findViewById(R.id.nameTextView);
        final ListView listView = (ListView) rootView.findViewById(R.id.mLogListView);

        Intent myIntent = currentActivity.getIntent();
        final String currentToken = myIntent.getStringExtra("token");
        //create new Request by Instantiating the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(currentActivity.getApplicationContext());


        // Request to get user details.
        StringRequest userDetailsRequest = new StringRequest(Request.Method.GET, USERDETAILS_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayAdapter<String> adapter;
                        ArrayList<String> items = new ArrayList<String>();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String username = jsonResponse.get("username").toString();
                            String first_name = jsonResponse.get("first_name").toString();
                            String last_name = jsonResponse.get("last_name").toString();
                            currentUser.setUsername(username);
                            currentUser.setFirst_name(first_name);
                            currentUser.setLast_name(last_name);
                            nameTextView.setText("Welcome, " + currentUser.getFirst_name());
                        } catch (Exception ex) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // mTextView.setText("That didn't work!");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + currentToken);
                return headers;
            }
        };

        // Request to get user log details.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, USERLOGLIST_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<UserLog> userLogs = new ArrayList<UserLog>();
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++) {
                                // items.add(responseArray.getJSONObject(i).toString());
                                Date log_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(responseArray.getJSONObject(i).getString("log_time"));
                                String comment = responseArray.getJSONObject(i).getString("comment");
                                UserLog userLog = new UserLog(currentUser, log_time, comment);
                                userLogs.add(userLog);
                            }
                        } catch (Exception ex) {

                        }

                        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item_layout,R.id.txt,items);
                        // UserLogAdapter adapter = new UserLogAdapter(currentActivity.getApplicationContext(), userLogs);
                        UserLogAdapter adapter = new UserLogAdapter(currentActivity, R.layout.item_layout, userLogs);
                        listView.addHeaderView(header);
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // mTextView.setText("That didn't work!");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + currentToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(userDetailsRequest);
        queue.add(stringRequest);
    }




}