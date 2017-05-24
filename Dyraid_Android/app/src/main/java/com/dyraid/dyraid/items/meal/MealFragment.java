package com.dyraid.dyraid.items.meal;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dyraid.dyraid.R;
import com.dyraid.dyraid.items.user.User;
import com.dyraid.dyraid.items.meal.Meal;
import com.dyraid.dyraid.items.meal.MealAdapter;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emekaokoro on 5/24/17.
 */

public class MealFragment extends Fragment {

    private static final String MEALLIST_REQUEST_URL = "http://192.168.56.56:8000/meal/api/";//Remove hardcode

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal, container, false);
        final View header = inflater.inflate(R.layout.meal_header, null);

        final User currentUser = new User();
        final FragmentActivity currentActivity = getActivity();

        MealFragmentDisplay(rootView, header, currentUser, currentActivity);

        return rootView;


    }


    static void MealFragmentDisplay(View rootView, final View header, final User currentUser, final FragmentActivity currentActivity) {

        final TextView nameTextView = (TextView) rootView.findViewById(R.id.nameTextView);
        final ListView listView = (ListView) rootView.findViewById(R.id.mLogListView);

        Intent myIntent = currentActivity.getIntent();
        final String currentToken = myIntent.getStringExtra("token");
        //create new Request by Instantiating the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(currentActivity.getApplicationContext());

        // Request to get user log details.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MEALLIST_REQUEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<Meal> meals = new ArrayList<Meal>();
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++) {
                                // items.add(responseArray.getJSONObject(i).toString());
                                String food_name = responseArray.getJSONObject(i).getString("food_name");
                                Integer calories = responseArray.getJSONObject(i).getInt("calories");
                                Meal meal = new Meal(currentUser, food_name, calories);
                                meals.add(meal);
                            }
                        } catch (Exception ex) {

                        }

                        MealAdapter adapter = new MealAdapter(currentActivity, R.layout.meal_layout, meals);
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
        queue.add(stringRequest);
    }




}

