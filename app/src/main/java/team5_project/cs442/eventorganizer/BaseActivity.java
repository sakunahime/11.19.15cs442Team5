package team5_project.cs442.eventorganizer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5_project.cs442.eventorganizer.database.Database;
import team5_project.cs442.eventorganizer.event.CreateActivity;
import team5_project.cs442.eventorganizer.event.Event;
import team5_project.cs442.eventorganizer.event.EventListViewActivity;
import team5_project.cs442.eventorganizer.event.Tuple;

public class BaseActivity extends FragmentActivity {

    public static Activity listInstance;

    protected static String email;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCreate:
                Intent createActivity = new Intent(this, CreateActivity.class);
                startActivity(createActivity);
                return true;
            case R.id.menuMap:
                returnToMap();
                return true;
            case R.id.menuMine:
                if (listInstance != null) {
                    listInstance.finish();
                }
                showListView(Database.TAG_EMAIL, email);
                return true;
            //case R.id.menuOut:
              //  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void returnToMap() {
        Intent backtoMap = new Intent(this, MapsActivity.class);
        backtoMap.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backtoMap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        backtoMap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        backtoMap.putExtra("email", email);
        startActivity(backtoMap);
        finish();
    }

    public void showListView(String tag, String value) {
        Intent i = new Intent(getApplicationContext(), EventListViewActivity.class);
        Bundle bundle = new Bundle();
        Tuple tuple = new Tuple(tag, value);
        bundle.putSerializable("Tuple", (Serializable) tuple);
        i.putExtras(bundle);
        startActivity(i);
    }
}
