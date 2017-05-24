package com.dyraid.dyraid.items.userlog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dyraid.dyraid.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by emekaokoro on 5/12/17.
 */

public class UserLogAdapter extends ArrayAdapter<UserLog> {

    // View lookup cache
    private static class ViewHolder {
        TextView num;
        TextView log_time;
        TextView comment;
    }

    private Context context;
    private int resource;
    private ArrayList<UserLog> user_logs;

    public UserLogAdapter(Context context, ArrayList<UserLog> user_logs) {
        super(context, R.layout.userlog_layout, user_logs);
    }

    public UserLogAdapter(Context context, int resource, ArrayList<UserLog> user_logs)
    {
        super(context, resource, user_logs);
        this.context=context;
        this.resource=resource;
        this.user_logs=user_logs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        UserLog user_log = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            // LayoutInflater inflater = LayoutInflater.from(getContext()); // for Activity
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // for Fragment in Activity
            convertView = inflater.inflate(R.layout.userlog_layout, parent, false);
            viewHolder.num = (TextView) convertView.findViewById(R.id.num);
            viewHolder.log_time = (TextView) convertView.findViewById(R.id.log_time);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.comment);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.num.setText(String.valueOf(position+1));
        viewHolder.log_time.setText(user_log.getLog_time().toString());
        viewHolder.comment.setText(user_log.getComment());
        // Return the completed view to render on screen
        return convertView;
    }
}