package team5_project.cs442.eventorganizer.TabFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import team5_project.cs442.eventorganizer.R;

/**
 * Created by Woot on 11/20/2015.
 */
public class FragmentTab3 extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab, container, false);
        TextView textview = (TextView) view.findViewById(R.id.tabtextview);
        textview.setText("Tab 3");
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
