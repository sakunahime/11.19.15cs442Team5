package team5_project.cs442.eventorganizer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.plus.Plus;

import java.util.List;

import team5_project.cs442.eventorganizer.R;
import team5_project.cs442.eventorganizer.database.Database;
import team5_project.cs442.eventorganizer.event.Event;
import team5_project.cs442.eventorganizer.event.EventTimeChecker;
import team5_project.cs442.eventorganizer.event.Tuple;

/**
 * Created by sangwon on 10/23/15.
 */
public class EventListViewActivity extends BaseActivity implements View.OnClickListener {

    private Tuple mTuple;

    /**
     * EventType
     * 0 = All
     * 1 = Ongoing
     * 2 = Upcoming in 5 hours
     * 3 = Future (After 5 hours)
     * 4 = Mine
     */
    private int eventType;

    private Button allEvents;

    private Button onGoingEvents;

    private Button upComingEvents;

    private Button futureEvents;

    private Button myEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flag_for_activity);

        eventType = 0;

        allEvents = (Button) findViewById(R.id.allEvents);
        allEvents.setOnClickListener(this);

        onGoingEvents = (Button) findViewById(R.id.onGoingEvents);
        onGoingEvents.setOnClickListener(this);

        upComingEvents = (Button) findViewById(R.id.upComingEvents);
        upComingEvents.setOnClickListener(this);

        futureEvents = (Button) findViewById(R.id.futureEvents);
        futureEvents.setOnClickListener(this);

        myEvents = (Button) findViewById(R.id.myEvents);
        myEvents.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        mTuple = (Tuple) bundle.getSerializable("Tuple");

        listInstance = this;
    }

    @Override
    public void onClick(View v) {
        if (v == allEvents) {
            eventType = 0;
            onResume();
            return;
        } else if (v == onGoingEvents) {
            eventType = 1;
            onResume();
            return;
        } else if (v == upComingEvents) {
            eventType = 2;
            onResume();
            return;
        } else if (v == futureEvents) {
            eventType = 3;
            onResume();
            return;
        } else if (v == myEvents) {
            eventType = 4;
            onResume();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int resID = R.layout.event_info_list_view;
        List<Event> tempEvents;
        if (eventType == 4 || (mTuple.getKey().equals(Database.TAG_LOC))) {
            tempEvents = Database.readList(mTuple.getKey(), mTuple.getValue());
        } else {
            tempEvents = Database.readList();
            tempEvents = EventTimeChecker.isRightEventTypes(tempEvents, eventType);
        }

        final List<Event> events = tempEvents;

        EventAdapterForListViewActivity eventAdapterForEventInfo = new EventAdapterForListViewActivity(this, resID, events);

        ListView list = (ListView) findViewById(R.id.flag_list_view_activity);
        list.setAdapter(eventAdapterForEventInfo);

        list.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), UpdateActivity.class);
                Event event = events.get(position);
                i.putExtra("Event", event);
                startActivity(i);
            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}