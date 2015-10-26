package team5_project.cs442.eventorganizer.team5_project.cs442.eventorganizer.location;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import team5_project.cs442.eventorganizer.team5_project.cs442.eventorganizer.eventCreator.Event;

/**
 * Created by sangwon on 10/26/15.
 */
public class Location {

    private String mLocation;

    private Double mLatitude;

    private Double mLongitude;

    private List<Event> events;

    private Marker marker;

    public Location(String mLocation, Double mLatitude, Double mLongitude) {
        this.mLocation = mLocation;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        events = new ArrayList<Event>();
    }

    public String getmLocation() {
        return mLocation;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public Double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    private boolean setMarker(Marker marker) {
        this.marker = marker;
        return (this.marker != null) ?  true : false;
    }

    private boolean setEvents(List<Event> events) {


        return (this.events == null) ? false : true;
    }
}
