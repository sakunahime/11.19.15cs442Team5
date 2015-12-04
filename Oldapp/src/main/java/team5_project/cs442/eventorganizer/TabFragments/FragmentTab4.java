package team5_project.cs442.eventorganizer.TabFragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5_project.cs442.eventorganizer.R;

/**
 * Created by Woot on 11/20/2015.
 */
public class FragmentTab4 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab, container, false);
        TextView textview = (TextView) view.findViewById(R.id.tabtextview);
        textview.setText("Tab 4");
        return view;
    }

    public static class EventsTabListener implements android.app.ActionBar.TabListener {
        android.app.Fragment fragment;

        public EventsTabListener(android.app.Fragment fragment) {
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