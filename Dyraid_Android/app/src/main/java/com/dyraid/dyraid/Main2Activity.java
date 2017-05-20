package com.dyraid.dyraid;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class Main2Activity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.dyraid.MESSAGE";
    private static final String USERDETAILS_REQUEST_URL = "http://192.168.56.56:8000/home/rest-auth/user/";//Remove hardcode
    private static final String USERLOGLIST_REQUEST_URL = "http://192.168.56.56:8000/userlog/api/";//Remove hardcode

    private Context context;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

/*
        final TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        final TextView mTextView = (TextView) findViewById(R.id.mTextView);
        // final EditText editText = (EditText) findViewById(R.id.editText);
        final ListView listView = (ListView) findViewById(R.id.mLogListView);

        final User currentUser = new User();

        //get info
        Intent myIntent = getIntent();
        final String currentToken = myIntent.getStringExtra("token");
        // editText.setText(currentToken);

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
                        UserLogAdapter adapter = new UserLogAdapter(Main2Activity.this, userLogs);
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
        queue.add(stringRequest);*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
