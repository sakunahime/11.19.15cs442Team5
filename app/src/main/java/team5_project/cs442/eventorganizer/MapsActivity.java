package team5_project.cs442.eventorganizer;

import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import team5_project.cs442.eventorganizer.team5_project.cs442.eventorganizer.eventCreator.EventDetailActivity;
import team5_project.cs442.eventorganizer.team5_project.cs442.eventorganizer.eventCreator.Event;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ConcurrentHashMap<Marker, Event> mFlagsHashMap;
    private ArrayList<Event> mFlagsArray = new ArrayList<Event>();
    private DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private Calendar currentdate = Calendar.getInstance();
    private TimeZone obj = TimeZone.getTimeZone("CDT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFlagsHashMap = new ConcurrentHashMap<Marker, Event>();
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
            Log.d("Zoom : ", "Are you still loading?!");


            if (mMap != null) {
                setUpMap();
                CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(41.835454, -87.62587));
                CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
                mMap.moveCamera(center);
                mMap.animateCamera(zoom);

                mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        Log.d("Zoom : ", "Are you here?!");
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
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        loadAllEvents();
    }

    private void loadAllEvents() {
        Event mtccFlag = new Event(1, "Always Party!", "MTCC", new Date("10/31/2015 11:00"), new Date("10/31/2015 13:00"), 41.835454d, -87.62587d, "Whatever", "Sangwon", "smoon3@hawk.iit.edu", 0d);
        mFlagsArray.add(mtccFlag);
        plotMarkers(mFlagsArray);
    }

    private void plotMarkers(ArrayList<Event> events) {
        if (events.size() > 0) {
            for (Event event : events) {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(event.getmLatitude(), event.getmLongitude()));
                // We should set image dynamically by event time...for now default is blue_flag
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.orange_flag));

                Marker currentMarker = mMap.addMarker(markerOption);
                mFlagsHashMap.put(currentMarker, event);

                mMap.setInfoWindowAdapter(new EventInfoWindowAdapter());
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        Log.d("Map Initializer : ", "Intialized with IIT MTCC");
        //mMap.addMarker(new MarkerOptions().position(new LatLng(41.835454, -87.62587)).title("IIT"));
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

            Event event = mFlagsHashMap.get(marker);

            Intent deatilIntent = new Intent(getBaseContext(), EventDetailActivity.class);
            deatilIntent.putExtra("Event", (Serializable) event);
            startActivity(deatilIntent);

            //ImageView markerIcon = (ImageView) v.findViewById(R.id.flag_icon);
            //markerIcon.setImageResource(R.drawable.orange_flag);
            return v;
        }

        private int manageMarkerIcon(Date _startEvent, Date _endEvent) {
            /**
             * We can use this method to differenciate for time..
             */
            formatter.setTimeZone(obj);
            String currentDateTime = formatter.format(currentdate.getTime());
            String startEvent = formatter.format(_startEvent);
            String endEvent = formatter.format(_endEvent);

            /**
            if (markerIcon.equals("green"))
                return R.drawable.orange_flag;
            /**else if(markerIcon.equals("icon2"))
             return R.drawable.icon2;
             else if(markerIcon.equals("icon3"))
             return R.drawable.icon3;
             else if(markerIcon.equals("icon4"))
             return R.drawable.icon4;
             else if(markerIcon.equals("icon5"))
             return R.drawable.icon5;
             else if(markerIcon.equals("icon6"))
             return R.drawable.icon6;
             else if(markerIcon.equals("icon7"))
             return R.drawable.icon7;
            else
                return R.drawable.orange_flag;
            */
            return R.drawable.orange_flag;
        }
    }


}
