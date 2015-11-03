package team5_project.cs442.eventorganizer.database;

import android.util.Log;

/**
 * Created by sangwon on 11/3/15.
 */
public class ConnectDB {

    private static final String host = "107.206.120.195";
    private static int port = 3306;
    private static String database = "organizer";
    private static String table = "events";
    private static String user = "event";
    private static String pass = "event123";

    private static EventDatabase eventDb;

    public ConnectDB() {
        eventDb = new EventDatabase(host, port, database, table, user, pass);
        eventDb.CONN();
    }

    public static EventDatabase getDBConnection() {
        if(eventDb == null) {
            new EventDatabase(host, port, database, table, user, pass);
            eventDb.CONN();
        }
        Log.d("DB Connect : ", "Got db connection.. ");

        return eventDb;
    }

    public boolean connectDB() {
        return eventDb.isConnected();
    }

}
