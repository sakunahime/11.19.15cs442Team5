package team5_project.cs442.eventorganizer;

import android.content.Intent;
import android.location.Location;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team5_project.cs442.eventorganizer.database.Database;
import team5_project.cs442.eventorganizer.event.Event;
import team5_project.cs442.eventorganizer.event.EventListViewActivity;
import team5_project.cs442.eventorganizer.event.EventAdapterForEventInfoWindow;
import team5_project.cs442.eventorganizer.event.EventChecker;
import team5_project.cs442.eventorganizer.location.LocationLoader;

public class MapsActivity extends BaseActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationLoader locationLoader;
    private List<team5_project.cs442.eventorganizer.location.Location> locations;
    private Map<Marker, List<Event>> eventsByMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Intent intent = getIntent();
        email = intent.getExtras().getString("email");
        locationLoader = new LocationLoader();
        eventsByMarker = new HashMap<Marker, List<Event>>();
        locations = locationLoader.getLocation();

        MapsInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuMap:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.

            if (mMap != null) {
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(41.835454, -87.62587));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(41.835454, -87.62587));
                        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                        mMap.moveCamera(center);
                        mMap.animateCamera(zoom);
                    }
                });

            } else {
                Toast.makeText(getApplicationContext(), "Unable to create Maps", Toast.LENGTH_SHORT).show();
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        loadAllEvents();
    }

    public void loadAllEvents() {

        for (team5_project.cs442.eventorganizer.location.Location location : locations) {
            List<Event> events = Database.readList(Database.TAG_LOC, location.getmLocation());
            location.setEvents(events);
        }
        mMap.clear();
        plotMarkers(locations);
    }

    private void plotMarkers(List<team5_project.cs442.eventorganizer.location.Location> locations) {
        if (locations.size() > 0) {
            for (team5_project.cs442.eventorganizer.location.Location location : locations) {
                if (location.getEventsCounter() != 0) {
                    // Create user marker with custom icon and other options
                    MarkerOptions markerOption = new MarkerOptions().position(new LatLng(location.getmLatitude(), location.getmLongitude()));
                    // We should set image dynamically by event time...for now default is blue_flag

                    // by the first event time line, we need to update location icon color..
                    // we assume that the first object is most recent event.
                    Event event = location.getEvents().get(0);
                    BitmapDescriptor icon = EventChecker.eventChecker(event.getmEventStartTime(), event.getmEventEndTime());
                    markerOption.icon(icon);
                    markerOption.title(location.getmLocation());
                    Marker currentMarker = mMap.addMarker(markerOption);
                    currentMarker.setTitle(location.getmLocation());
                    eventsByMarker.put(currentMarker, location.getEvents());
                    mMap.setInfoWindowAdapter(new EventInfoWindowAdapter());

                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
                    {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            showListView(Database.TAG_LOC, marker.getTitle());
                        }
                    });

                    //if (location.getmLocation().equals("Carman Hall")) Log.d("Carman?",String.valueOf(location.getEventsCounter()));
                }
            }
        }
    }

    public class EventInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        public EventInfoWindowAdapter() {
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.flag, null);
            ListView listV = (ListView) v.findViewById(R.id.flag_list_view);
            final List<Event> events = eventsByMarker.get(marker);
            Log.d("Marker & Size:", marker.getTitle() + ":" + String.valueOf(events.size()));
            int resID = R.layout.event_detail_in_flag;
            EventAdapterForEventInfoWindow eventAdapterForEventInfoWindow = new EventAdapterForEventInfoWindow(v.getContext(), resID, events);
            listV.setAdapter(eventAdapterForEventInfoWindow);
            return v;
        }
    }
}



