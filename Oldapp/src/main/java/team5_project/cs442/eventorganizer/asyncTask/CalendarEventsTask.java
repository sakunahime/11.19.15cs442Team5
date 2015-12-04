package team5_project.cs442.eventorganizer.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import team5_project.cs442.eventorganizer.activities.UpdateActivity;

public class CalendarEventsTask extends AsyncTask<Void, Void, List<String>> {

    private static final String TAG = "CalendarEventsTask";

    private UpdateActivity activity = null;
    private Calendar service = null;
    private Exception error = null;

    public CalendarEventsTask(UpdateActivity activity, GoogleAccountCredential credential) {
        this.activity = activity;

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        service = new Calendar.Builder(transport, jsonFactory, credential)
                .setApplicationName("IIT Event Organizer")
                .build();
    }

    @Override
    protected List<String> doInBackground(Void... params) {
        try {
            return getDataFromApi();
        } catch (IOException e) {
            error = e;
            cancel(true);
            return null;
        }
    }

    private List<String> getDataFromApi() throws IOException {
        Log.v(TAG, "Getting data from API");

        List<Event> events = service.events().list("primary").execute().getItems();

        List<String> names = new ArrayList<String>();

        for (Event event : events) {
            names.add(event.getSummary());
        }

        return names;
    }

    @Override
    protected void onCancelled() {
        if (error != null) {
            if (error instanceof GooglePlayServicesAvailabilityIOException) {
                // TODO: Show error
                error.printStackTrace();
            } else if (error instanceof UserRecoverableAuthIOException) {
                activity.startActivityForResult(
                        ((UserRecoverableAuthIOException) error).getIntent(),
                        UpdateActivity.REQUEST_AUTHORIZATION);
            } else {
                // TODO: Show error
                error.printStackTrace();
            }
        } else {
            // TODO: Show error
        }
    }
}
