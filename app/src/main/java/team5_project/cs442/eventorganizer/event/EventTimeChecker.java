package team5_project.cs442.eventorganizer.event;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sangwon on 10/26/15.
 */
public class EventTimeChecker {

    private static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");

    private static TimeZone obj = TimeZone.getTimeZone("CST");

    private static String _currentTime;

    private static Date currentTime;


    public static BitmapDescriptor eventChecker(final Date _startEvent, final Date _endEvent) {
        /**
         * We can use this method to differenciate for time..
         */
        _currentTime = EventTimeChecker.formatter.format(new Date());
        currentTime = new Date(_currentTime);

        long diff = _startEvent.getTime() - currentTime.getTime();

        int diffhours = (int) (diff / (60 * 60 * 1000));

        if (currentTime.equals(_startEvent) || (currentTime.after(_startEvent) && currentTime.before(_endEvent))) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        }
        if ((diffhours <= 2) && (diffhours > 0)) {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
        } else {
            return BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET);
        }
    }

    public static boolean isEventPassed(final Date _startEvent, final Date _endEvent) {
        /**
         * We can use this method to differenciate for time..
         */
        _currentTime = EventTimeChecker.formatter.format(new Date());
        currentTime = new Date(_currentTime);

        long diff = _startEvent.getTime() - currentTime.getTime();

        int diffhours = (int) (diff / (60 * 60 * 1000));

        if ((diffhours > 0)) {
            return false; //  Not come yet
        }
        if ((currentTime.after(_startEvent) && currentTime.before(_endEvent))) {
            return false; // Event is on going.
        }
        return true; //  Event passed, should filter

    }

}
