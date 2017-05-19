package com.dyraid.dyraid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dyraid.MESSAGE";
    private static final String USERDETAILS_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/user/";//Remove hardcode
    private static final String USERLOGLIST_REQUEST_URL = "http://192.168.56.56:8000/userlog/api/";//Remove hardcode

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        final TextView mTextView = (TextView) findViewById(R.id.mTextView);
        final EditText editText = (EditText) findViewById(R.id.editText);
        final ListView listView = (ListView) findViewById(R.id.mLogListView);

        final User currentUser = new User();

        //get info
        Intent myIntent = getIntent();
        final String currentToken = myIntent.getStringExtra("token");
        editText.setText(currentToken);

        //create new Request
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);


        // Request to get user details.
        StringRequest userDetailsRequest = new StringRequest(Request.Method.GET, USERDETAILS_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is also: " + response);


                        ArrayAdapter<String> adapter;
                        ArrayList<String> items = new ArrayList<String>();
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            String username = jsonResponse.get("username").toString();
                            String first_name = jsonResponse.get("first_name").toString();
                            String last_name = jsonResponse.get("last_name").toString();
                            currentUser.setUsername(username);
                            currentUser.setFirst_name(first_name);
                            currentUser.setLast_name(last_name);
                            welcomeTextView.setText("Welcome, " + currentUser.getFirst_name());
                        }
                        catch (Exception ex)
                        {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                })
        {
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
                        // Display the first 500 characters of the response string.
                        mTextView.setText("Response is so: "+ response);

                        ArrayList<UserLog> userLogs = new ArrayList<UserLog>();
                        try
                        {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++) {
                                // items.add(responseArray.getJSONObject(i).toString());
                                Date log_time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(responseArray.getJSONObject(i).getString("log_time"));
                                String comment = responseArray.getJSONObject(i).getString("comment");
                                UserLog userLog = new UserLog(currentUser, log_time, comment);
                                userLogs.add(userLog);
                            }
                        }
                        catch (Exception ex)
                        {

                        }

                        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, R.layout.item_layout,R.id.txt,items);
                        UserLogAdapter adapter = new UserLogAdapter(MainActivity.this, userLogs);
                        View header = getLayoutInflater().inflate(R.layout.userlog_header, null);
                        listView.addHeaderView(header);
//                        listView.setAdapter(new android.support.v4.widget.SimpleCursorAdapter(this, R.layout.display, cursor, new String[]{
//                                "_id", "Name", "Address"
//                        }, new int[]{R.id.id, R.id.name, R.id.add}, 0));
                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + currentToken);
                return headers;
            }
        };;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, USERLOGLIST_REQUEST_URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        mTextView.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        mTextView.setText("That didn't work!");
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(userDetailsRequest);
        queue.add(stringRequest);
    }


    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
        String message = "test";
        intent.putExtra(EXTRA_MESSAGE + 'd', message);
        startActivity(intent);
    }
}
