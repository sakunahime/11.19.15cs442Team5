package team5_project.cs442.eventorganizer.event;

import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sangwon on 10/26/15.
 */
public class EventTimeChecker {

    public static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    public static Calendar currentdate = Calendar.getInstance();

    private static String _currentTime;

    private static Date currentTime;


    private static double getDiffHours(final Date _startEvent, final Date _endEvent) {

        _currentTime = EventTimeChecker.formatter.format(currentdate.getTime());
        Log.d("CUrrent date:", _currentTime);

        currentTime = new Date(_currentTime);

        Log.d("!CUrrent date:", String.valueOf(currentTime));
        long diff = _startEvent.getTime() - currentTime.getTime();

        Log.d("Time Checker", _startEvent.getTime() + ":" +  currentTime.getTime());
        double _diffhours = (diff / (60 * 60 * 1000));
        Log.d("Checker result", String.valueOf(_diffhours));
        return _diffhours;
    }

    public static BitmapDescriptor eventChecker(final Date _startEvent, final Date _endEvent) {
        /**
         * We can use this method to differenciate for time..
         */

        double diffhours = getDiffHours(_startEvent, _endEvent);
        if ((currentTime.after(_startEvent) && currentTime.before(_endEvent))) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }
        if ((diffhours <= 2) && (diffhours >= 0)) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
        } else {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
        }
    }

    public static boolean isEventPassed(final Date _startEvent, final Date _endEvent) {
        double diffhours = getDiffHours(_startEvent, _endEvent);

        if ((diffhours >= 0)) {
            return false; //  Not come yet
        }
        if ((currentTime.after(_startEvent) && currentTime.before(_endEvent))) {
            return false; // Event is on going.
        }
        return true; //  Event passed, should filter

    }

}
