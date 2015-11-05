package team5_project.cs442.eventorganizer.event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import team5_project.cs442.eventorganizer.BaseActivity;
import team5_project.cs442.eventorganizer.R;
import team5_project.cs442.eventorganizer.database.Database;
import team5_project.cs442.eventorganizer.location.LocationLoader;

public class UpdateActivity extends BaseActivity implements View.OnClickListener {

    private SimpleDateFormat dateformat;
    private SimpleDateFormat timeformat;

    private EditText mEditName;
    private Spinner mSpinnerLoc;
    private TextView mTextStartDate;
    private TextView mTextStartTime;
    private TextView mTextEndDate;
    private TextView mTextEndTime;
    private EditText mEditDesc;
    private EditText mEditHost;
    private EditText mEditCost;
    private Button mBtnUpdate;

    private DatePickerDialog mStartDateDialog;
    private TimePickerDialog mStartTimeDialog;
    private DatePickerDialog mEndDateDialog;
    private TimePickerDialog mEndTimeDialog;

    private Calendar mStartCal;
    private Calendar mEndCal;

    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        mEvent = (Event) intent.getSerializableExtra("Event");

        mEditName = (EditText)findViewById(R.id.editUpdateName);
        mSpinnerLoc = (Spinner)findViewById(R.id.spinnerUpdateLoc);
        mTextStartDate = (TextView)findViewById(R.id.editUpdateStartDate);
        mTextStartTime = (TextView)findViewById(R.id.editUpdateStartTime);
        mTextEndDate = (TextView)findViewById(R.id.editUpdateEndDate);
        mTextEndTime = (TextView)findViewById(R.id.editUpdateEndTime);
        mEditDesc = (EditText)findViewById(R.id.editUpdateDesc);
        mEditHost = (EditText)findViewById(R.id.editUpdateHost);
        mEditCost = (EditText)findViewById(R.id.editUpdateCost);
        mBtnUpdate = (Button)findViewById(R.id.btnUpdate);

        mEditName.setText(mEvent.getmEventName());
        initLocation();
        initDates();
        mEditDesc.setText(mEvent.getmDescription());
        mEditHost.setText(mEvent.getmHost());
        mEditCost.setText(String.valueOf(mEvent.getmCost()));

        EditText mEditEmail = (EditText)findViewById(R.id.editUpdateEmail);
        mEditEmail.setText(mEvent.getmEventCreator());
        mEditEmail.setEnabled(false);

        if (mEvent.getmEventCreator().equals(email)) {
            initEditDate();
            mBtnUpdate.setOnClickListener(this);
        } else {
            mEditName.setEnabled(false);
            mSpinnerLoc.setEnabled(false);
            mTextStartDate.setEnabled(false);
            mTextStartTime.setEnabled(false);
            mTextEndDate.setEnabled(false);
            mTextEndTime.setEnabled(false);
            mEditDesc.setEnabled(false);
            mEditHost.setEnabled(false);
            mEditCost.setEnabled(false);
            mBtnUpdate.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mTextStartDate) {
            mStartDateDialog.show();
        } else if (v == mTextStartTime) {
            mStartTimeDialog.show();
        } else if (v == mTextEndDate) {
            mEndDateDialog.show();
        } else if (v == mTextEndTime) {
            mEndTimeDialog.show();
        } else if (v == mBtnUpdate) {
            updateEvent();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void updateEvent() {
        mEvent.setmEventName(mEditName.getText().toString());
        mEvent.setmEventLocation(mSpinnerLoc.getSelectedItem().toString());
        mEvent.setmEventStartTime(mStartCal.getTime());
        mEvent.setmEventEndTime(mEndCal.getTime());
        mEvent.setmDescription(mEditDesc.getText().toString());
        mEvent.setmHost(mEditHost.getText().toString());
        mEvent.setmCost(Double.parseDouble(mEditCost.getText().toString()));

        Database.update(mEvent);

        finish();
    }

    private void initLocation() {
        List<String> locations = LocationLoader.getLocationNames();

        int index = locations.indexOf(mEvent.getmEventLocation());
        if (index < 0) index = 0;

        ArrayAdapter<String> adaptLoc = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, locations);
        mSpinnerLoc.setAdapter(adaptLoc);

        mSpinnerLoc.setSelection(index);
    }

    private void initDates() {
        dateformat = new SimpleDateFormat("E MMM dd, yyyy");
        timeformat = new SimpleDateFormat("hh:mm a");

        mStartCal = Calendar.getInstance();
        mEndCal = Calendar.getInstance();

        mStartCal.setTime(mEvent.getmEventStartTime());
        mEndCal.setTime(mEvent.getmEventEndTime());

        mTextStartDate.setText(dateformat.format(mStartCal.getTime()));
        mTextStartTime.setText(timeformat.format(mStartCal.getTime()));
        mTextEndDate.setText(dateformat.format(mEndCal.getTime()));
        mTextEndTime.setText(timeformat.format(mEndCal.getTime()));
    }

    private void initEditDate() {
        mTextStartDate.setOnClickListener(this);
        mTextStartTime.setOnClickListener(this);
        mTextEndDate.setOnClickListener(this);
        mTextEndTime.setOnClickListener(this);

        mStartDateDialog = createDatePickerDialog(mTextStartDate, mStartCal);
        mStartTimeDialog = createTimePickerDialog(mTextStartTime, mStartCal);
        mEndDateDialog = createDatePickerDialog(mTextEndDate, mEndCal);
        mEndTimeDialog = createTimePickerDialog(mTextEndTime, mEndCal);
    }

    private DatePickerDialog createDatePickerDialog(final TextView text, final Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        changeDate(text, cal, y, m, d);
                    }
                }, year, month, day);
    }

    private TimePickerDialog createTimePickerDialog(final TextView text, final Calendar cal) {
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        return new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        changeTime(text, cal, h, m);
                    }
                }, hour, minute, false);
    }

    private void changeDate(TextView text, Calendar cal, int year, int month, int day) {
        cal.set(year, month, day);
        text.setText(dateformat.format(cal.getTime()));
    }

    private void changeTime(TextView text, Calendar cal, int hour, int minute) {
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        text.setText(timeformat.format(cal.getTime()));
    }
}
