package team5_project.cs442.eventorganizer.database;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import team5_project.cs442.eventorganizer.eventCreator.Event;

/**
 * Created by sangwon on 11/2/2015.
 */
public class EventDatabase {
    public static final String ID_COL = "id";
    public static final String NAME_COL = "name";
    public static final String LOC_COL = "location";
    public static final String START_COL = "starttime";
    public static final String END_COL = "endtime";
    public static final String DESC_COL = "description";
    public static final String HOST_COL = "host";
    public static final String EMAIL_COL = "email";
    public static final String COST_COL = "cost";

    private final String classs = "net.sourceforge.jtds.jdbc.Driver";

    private String mHost;
    private int mPort;
    private String mDatabase;
    private String mTable;
    private String mUser;
    private String mPass;

    private Connection mCon = null;

    public EventDatabase(String host, int port, String database, String table, String user, String pass) {
        mHost = host;
        mPort = port;
        mDatabase = database;
        mTable = table;
        mUser = user;
        mPass = pass;
    }

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + mHost + ":" + mPort +  ";"
                    + "databaseName=" + mDatabase + ";user=" + mUser + ";password="
                    + mPass + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }

        try {
            Log.d("Db conn Open? : ", String.valueOf(conn.isClosed()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public void disconnect() {
        try {
            if (mCon != null) {
                mCon.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> read(String loc) {
        PreparedStatement stmt = null;
        List<Event> list = new ArrayList<Event>();

        try {
            if (loc != null) {
                loc = String.format(" where Location = %s ", loc);
            } else {
                loc = "";
            }

            stmt = mCon.prepareStatement("select * from " + mDatabase + "."  + mTable + loc);

            ResultSet rs = stmt.executeQuery();

            //Date now = new Date();
            while (rs.next()) {

                int eid = rs.getInt(ID_COL);
                long etime1 = rs.getLong(START_COL);

                String ename = rs.getString(NAME_COL);
                String eloc = rs.getString(LOC_COL);
                long etime2 = rs.getLong(END_COL);
                String edesc = rs.getString(DESC_COL);
                String ehost = rs.getString(HOST_COL);
                String ecreator = rs.getString(EMAIL_COL);
                Double ecost = rs.getDouble(COST_COL);

                Event event = new Event(eid, ename, eloc, new Date(etime1), new Date(etime2), edesc, ehost, ecreator, ecost);

                list.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public void insert(Event event) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = mCon.prepareStatement("insert into " + mDatabase + "."  + mTable + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, event.getmEventId());
            stmt.setString(2, event.getmEventName());
            stmt.setString(3, event.getmEventLocation());
            stmt.setLong(4, event.getmEventStartTime().getTime());
            stmt.setLong(5, event.getmEventEndTime().getTime());
            stmt.setString(6, event.getmDescription());
            stmt.setString(7, event.getmHost());
            stmt.setString(8, event.getmEventCreator());
            stmt.setDouble(9, event.getmCost());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void update(Event event) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = mCon.prepareStatement("search * from " + mDatabase + "."  + mTable + " where id = ?");
            stmt. setInt(1, event.getmEventId());

            if (stmt.execute()) {
                ResultSet rs = stmt.getResultSet();
                delete(rs.getInt("ID"));
            }

            insert(event);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void delete(int id) throws SQLException {
        PreparedStatement stmt = null;

        try {
            stmt = mCon.prepareStatement("delete from " + mDatabase + "." + mTable + " where id = ?");
            stmt.setInt(1, id);
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    private String getURL() {
        return String.format("jdbc:mysql://%s:%d/%s", mHost, mPort, mDatabase);
    }

    public boolean isConnected() {
        try {
            return mCon.isValid(10000);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
