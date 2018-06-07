package com.example.ray.voiceassistant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddCalendarEventActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_CALENDAR = 100;
    private static final long calId = 3;


    private EditText eventTitle;
    private EditText eventDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);

        eventTitle = (EditText) findViewById(R.id.eventTitle);
        eventDate = (EditText) findViewById(R.id.eventStartDate);
    }

    public void cilckOk(View view) {

        try {
            /*
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Date dateStart = null;

            dateStart = format.parse(eventDate.getText().toString());


            Calendar myCal = new GregorianCalendar();
            myCal.set(dateStart.getYear(), dateStart.getMonth(), dateStart.getDay());

*/
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            Date date = format.parse(eventDate.getText().toString());

            ContentValues values = new ContentValues();
            values.put(CalendarContract.Events.CALENDAR_ID, calId);
            values.put(CalendarContract.Events.TITLE, eventTitle.getText().toString());
            values.put(CalendarContract.Events.DTSTART, date.getTime());
            values.put(CalendarContract.Events.DTEND, date.getTime());
            values.put(CalendarContract.Events.ALL_DAY, 1);
            values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/Madrid");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                // Permission is not granted
                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_CALENDAR)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                } else {
                    // No explanation needed; request the permission
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_CALENDAR},
                            MY_PERMISSIONS_REQUEST_WRITE_CALENDAR);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }

            }

            Uri uriInsertEvent = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);

            long eventId = new Long(uriInsertEvent.getLastPathSegment());

            System.out.println("new eventId : " + eventId);

            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
