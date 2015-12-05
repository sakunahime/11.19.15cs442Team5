package team5_project.cs442.eventorganizer.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import team5_project.cs442.eventorganizer.R;
import team5_project.cs442.eventorganizer.event.Event;

/**
 * Created by sangwon on 10/17/15.
 */
public class EventAdapterForListViewActivity extends ArrayAdapter<Event> implements Serializable {

    private int resource;

    public EventAdapterForListViewActivity(Context context,
                                           int resource,
                                           List<Event> events) {
        super(context, resource, events);
        this.resource = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LinearLayout event_view;

        final Event event = getItem(position);

        String event_name = event.getmEventName();

        String event_start_time = new SimpleDateFormat("EEE MMM d ''yy hh:mm aaa").format(event.getmEventStartTime());
        String event_end_time = new SimpleDateFormat("EEE MMM d ''yy hh:mm aaa").format(event.getmEventEndTime());

       // String event_start_time = String.valueOf(event.getmEventStartTime());
        //String event5_end_time = String.valueOf(event.getmEventEndTime());

        if (convertView == null) {
            event_view = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, event_view, true);
        } else {
            event_view = (LinearLayout) convertView;
        }

        TextView event_name_text = (TextView) event_view.findViewById(R.id.event_name_info);
        TextView event_stime_text = (TextView) event_view.findViewById(R.id.event_start_time_info);
        TextView event_etime_text = (TextView) event_view.findViewById(R.id.event_end_time_info);

        event_name_text.setText(event_name);
        event_stime_text.setText(event_start_time);
        event_etime_text.setText(event_end_time);

        return event_view;
    }
}


