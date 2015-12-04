package team5_project.cs442.eventorganizer.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import team5_project.cs442.eventorganizer.R;
import team5_project.cs442.eventorganizer.TabFragments.FragmentTab1;
import team5_project.cs442.eventorganizer.TabFragments.FragmentTab2;
import team5_project.cs442.eventorganizer.TabFragments.FragmentTab3;
import team5_project.cs442.eventorganizer.TabFragments.FragmentTab4;

/**
 * Created by Anna on 11/20/2015.
 */
public class TabActivity extends Activity {
    ActionBar.Tab tab1, tab2, tab3, tab4;
    android.app.Fragment fragmentTab1 = new FragmentTab1();
    android.app.Fragment fragmentTab2 = new FragmentTab2();
    android.app.Fragment fragmentTab3 = new FragmentTab3();
    android.app.Fragment fragmentTab4 = new FragmentTab4();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tab1 = actionBar.newTab().setText("1");
        tab2 = actionBar.newTab().setText("2");
        tab3 = actionBar.newTab().setText("3");
        tab4 = actionBar.newTab().setText("4");

        tab1.setTabListener(new FragmentTab1.EventsTabListener(fragmentTab1));
        tab2.setTabListener(new FragmentTab2.EventsTabListener(fragmentTab2));
        tab3.setTabListener(new FragmentTab3.EventsTabListener(fragmentTab3));
        tab4.setTabListener(new FragmentTab4.EventsTabListener(fragmentTab4));

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);
        actionBar.addTab(tab3);
        actionBar.addTab(tab4);
    }
}