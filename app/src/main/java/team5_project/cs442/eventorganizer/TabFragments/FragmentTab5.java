package team5_project.cs442.eventorganizer.TabFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import team5_project.cs442.eventorganizer.R;
import team5_project.cs442.eventorganizer.event.Tuple;

/**
 * Created by Woot on 11/20/2015.
 */
public class FragmentTab5 extends Fragment{

    private Tuple mTuple;
    private TextView mEventTypeText;
    private int eventType;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_info_list_view, container, false);
        return view;
    }

    public static class EventsTabListener implements android.app.ActionBar.TabListener {
        Fragment fragment;

        public EventsTabListener(Fragment fragment) {
            this.fragment = fragment;
        }

        public void onTabSelected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            ft.replace(R.id.fragment_container, fragment);
        }

        public void onTabUnselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            ft.remove(fragment);
        }

        public void onTabReselected(android.app.ActionBar.Tab tab, android.app.FragmentTransaction ft) {
            // nothing done here
        }
    }
}