package com.dyraid.dyraid.items.meal;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dyraid.dyraid.R;
import com.dyraid.dyraid.items.meal.Meal;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by emekaokoro on 5/12/17.
 */

public class MealAdapter extends ArrayAdapter<Meal> {

    // View lookup cache
    private static class ViewHolder {
        TextView num;
        TextView food_name;
        TextView calories;
    }

    private Context context;
    private int resource;
    private ArrayList<Meal> meals;

    public MealAdapter(Context context, ArrayList<Meal> meals) {
        super(context, R.layout.meal_layout, meals);
    }

    public MealAdapter(Context context, int resource, ArrayList<Meal> meals)
    {
        super(context, resource, meals);
        this.context=context;
        this.resource=resource;
        this.meals=meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Meal meal = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        com.dyraid.dyraid.items.meal.MealAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new com.dyraid.dyraid.items.meal.MealAdapter.ViewHolder();
            // LayoutInflater inflater = LayoutInflater.from(getContext()); // for Activity
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // for Fragment in Activity
            convertView = inflater.inflate(R.layout.meal_layout, parent, false);
            viewHolder.num = (TextView) convertView.findViewById(R.id.num);
            viewHolder.food_name = (TextView) convertView.findViewById(R.id.food_name);
            viewHolder.calories = (TextView) convertView.findViewById(R.id.calories);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (com.dyraid.dyraid.items.meal.MealAdapter.ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.num.setText(String.valueOf(position+1));
        viewHolder.food_name.setText(meal.getFood_name());
        viewHolder.calories.setText(meal.getCalories().toString());
        // Return the completed view to render on screen
        return convertView;
    }
}
