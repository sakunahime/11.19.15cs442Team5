package team5_project.cs442.eventorganizer.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import team5_project.cs442.eventorganizer.eventCreator.Event;


public class MySQLiteHelper extends SQLiteOpenHelper {

    Context c;

    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
    String dateInString = "Jan 1, 2015";
    String dateInString1 = "Jan 10, 2015";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Events.db";
    private static final String eventcreator = "eventcreator";
    private static final int mEventId = 0;
    private static final String mEventName = "mEventName";
    private static final String mEventLocation = "mEventLocation";
   private Date mEventStartTime;
    private Date mEventEndTime;
    private static final double mLatitude = 0.0;
    private static final double mLongitude = 0.0;
    private static final String mDescription = "mDescription";
    private static final String mHost = "mHost";
    private static final String mEventCreator = "mEventCreator";
    private static final double mCost = 0.0;

    //private static final String COLUMN_ID = "id";


    private static MySQLiteHelper dbHandler = null;

    public static MySQLiteHelper getDbHandlerInstance(Context context, SQLiteDatabase.CursorFactory factory) {
        if (dbHandler == null) {
            dbHandler = new MySQLiteHelper(context, factory);
        }
        return dbHandler;
    }

    private MySQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        c = context;
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      //  Date date = new Date();
        return dateFormat.format(mEventStartTime);
    }

    private String getDateTime1() {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
      //  Date date = new Date();
        return dateFormat.format(mEventEndTime);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("on create 1");
        String query = "CREATE TABLE " + eventcreator + "(" +
                mEventId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                mEventName + " TEXT not null," +
                mEventLocation + " TEXT not null," +
                mEventStartTime + " date ," +
                mEventEndTime + " date ," +
                mLatitude + " real," +
                mLongitude + " real," +
                mDescription + " TEXT not null," +
                mHost + " TEXT not null," +
                mEventCreator + " TEXT not null," +
                mCost + " real," + ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean addEventDetails(int mEventId, String mEventName, String mEventLocation, Date mEventStartTime, Date mEventEndTime, Double mLatitude, Double mLongitude, String mDescription, String mHost, String mEventCreator, Double mCost) {
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put(String.valueOf(mEventId), mEventId);
            values.put(mEventName, mEventName);
            values.put(mEventLocation, mEventLocation);
            values.put(String.valueOf(mEventStartTime), getDateTime());
            values.put(String.valueOf(mEventEndTime), getDateTime1());
            values.put(String.valueOf(mLatitude), mLatitude);
            values.put(String.valueOf(mLongitude), mLongitude);
            values.put(mDescription, mDescription);
            values.put(mHost, mHost);
            values.put(mEventCreator, mEventCreator);
            values.put(String.valueOf(mCost), mCost);


            if (db.insert(eventcreator, null, values) != -1) {
                result = true;
                Toast.makeText(c, "Event details saved", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(c, "Event details are not saved", Toast.LENGTH_LONG).show();
                result = false;
            }
            db.close();
        }
        return result;
    }

    /*

        public List<String> getEventFromDb(String mEventName) {
            List<String> members = new ArrayList<String>();
            SQLiteDatabase db = getReadableDatabase();

            if (db != null) {
                try {
                    String query = "SELECT * FROM " + eventcreator + " WHERE " + mEventName + " = '" + mEventName + "'";
                    //cursor to point the current position of result
                    Cursor c = db.rawQuery(query, null);
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        String eventDescription = c.getString(c.getColumnIndex(mDescription));
                        members.add(eventDescription);
                        c.moveToNext();
                    }
                    db.close();
                } catch (Exception e) {
                    System.out.println("Error in db: " + e.getMessage());
                }
            }

            return members;
        }
    */
    public List<Event> getAllEventDetailsFromDb() {

        List<Event> eventList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        if (db != null) {
            try {


                //	System.out.println(date);
                //	System.out.println(formatter.format(date));
                String query = "SELECT * FROM " + eventcreator;
                //cursor to point the current position of result
                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    Integer eventid = c.getInt(c.getColumnIndex(String.valueOf(mEventId)));
                    String eventname = c.getString(c.getColumnIndex(mEventName));
                    String eventlocation = c.getString(c.getColumnIndex(mEventLocation));
                    //date = c.getColumnIndex(mEventStartTime);
                  //  String endtime = c.getString(c.getColumnIndex(String.valueOf(mEventEndTime)));
                  //  Date starttime = formatter.parse(dateInString);
                 //   Date endtime = formatter.parse(dateInString1);
                    Date starttime = formatter.parse(getDateTime());
                    Date endtime = formatter.parse(getDateTime1());
                    Double latitute = c.getDouble(c.getColumnIndex(String.valueOf(mLatitude)));
                    Double longitute = c.getDouble(c.getColumnIndex(String.valueOf(mLongitude)));
                    String description = c.getString(c.getColumnIndex(mDescription));
                    String host = c.getString(c.getColumnIndex(mHost));
                    String eventcreator = c.getString(c.getColumnIndex(mEventCreator));
                    Double cost = c.getDouble(c.getColumnIndex(String.valueOf(mCost)));
                    //eventList.add(eventid, eventname, eventlocation, starttime, endtime, latitute, longitute, description, host, eventcreator, cost);
                    //		eventList(eventid, eventname, eventlocation, starttime, endtime, latitute, longitute, description, host, eventcreator, cost);
                    Event e = new Event(eventid, eventname, eventlocation, starttime, endtime, description, host, eventcreator, cost);


                    c.moveToNext();
                }
                db.close();
            } catch (Exception e) {
                System.out.println("Error in db: " + e.getMessage());
            }
        }
        return eventList;
    }


    public boolean updateEventDetailsToDb(Event event){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        Date createdDate = event.getmEventStartTime();
        Date createdDate1 = event.getmEventEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(createdDate);
        String dateString1 = sdf.format(createdDate1);
       // editor.putString("date" + i, dateString);
        if (db != null && event != null) {
            try {
                ContentValues values = new ContentValues();
                values.put(mEventName,event.getmEventName());
                values.put(mEventName, event.getmEventName());
                values.put(mEventLocation, event.getmEventLocation());
                values.put(String.valueOf(mEventStartTime),getDateTime());
                values.put(String.valueOf(mEventEndTime),getDateTime1());
           //     values.put(mEventEndTime, event.getmEventEndTime());
                values.put(mDescription, event.getmDescription());
                values.put(mHost, event.getmHost());
                values.put(mEventCreator, event.getmEventCreator());
                values.put(String.valueOf(mCost), event.getmCost());


                String[] member1 = new String[]{event.getmEventName()};
                String whereClause = mEventName + " = ?" +   " + event.getmEventName() + ";
                if (db.update(eventcreator, values, whereClause, member1) != -1) {
                    result = true;
                } else {
                    result = false;
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return result;
    }



    public boolean deleteEventDetailsToDb(Event event){
        boolean result = false;
        SQLiteDatabase db = getWritableDatabase();
        Date createdDate = event.getmEventStartTime();
        Date createdDate1 = event.getmEventEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(createdDate);
        String dateString1 = sdf.format(createdDate1);
        // editor.putString("date" + i, dateString);
        if (db != null && event != null) {
            try {
                ContentValues values = new ContentValues();
                values.put(mEventName,event.getmEventName());
                values.put(mEventName, event.getmEventName());
                values.put(mEventLocation, event.getmEventLocation());
                values.put(String.valueOf(mEventStartTime),dateString);
                values.put(String.valueOf(mEventEndTime),dateString1);
                //     values.put(mEventEndTime, event.getmEventEndTime());
                values.put(mDescription, event.getmDescription());
                values.put(mHost, event.getmHost());
                values.put(mEventCreator, event.getmEventCreator());
                values.put(String.valueOf(mCost), event.getmCost());


                String[] member1 = new String[]{event.getmEventName()};
                String whereClause = mEventName + " = ?" +   " + event.getmEventName() + ";
                if (db.delete(eventcreator, whereClause, member1) != -1) {
                    result = true;
                } else {
                    result = false;
                }
                db.close();
            }catch(Exception e){
                System.out.println("Error in db: " + e.getMessage());
            }
        }

        return result;
    }


}



