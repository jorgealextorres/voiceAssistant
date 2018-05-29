package com.example.ray.voiceassistant;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;

public class CalendarActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 100;
    private static final int CALENDAR_ID = 3;

    public static final String[] EVENT_PROJECTION = new String[]{
            /*CalendarContract.Calendars._ID,
            CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.OWNER_ACCOUNT,*/
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };

    /*
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;
    */

    private TextView textViewCalendarName;
    private ListView listViewEventos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        textViewCalendarName = (TextView) findViewById(R.id.textViewCalendarName);
        listViewEventos = (ListView) findViewById(R.id.listViewEventos);

        consultaCalendars();
    }

    private void consultaCalendars() {
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        //Uri uri = CalendarContract.Calendars.CONTENT_URI;
        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "(" + CalendarContract.Events.CALENDAR_ID + "= ?)";
        /*String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + "= ?) AND (" +
                CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND (" +
                CalendarContract.Calendars.OWNER_ACCOUNT + " = ? ))";*/
        String[] selectionArgs = new String[]{String.valueOf(CALENDAR_ID)};
        //String[] selectionArgs = new String[]{"jorgealextorres@gmail.com", "com.google", "jorgealextorres@gmail.com"};

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
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
                    Manifest.permission.READ_CALENDAR)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_READ_CALENDAR);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }


        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        //cur = cr.query(uri, projection, selection, selectionArgs, null);

        ArrayList eventos = new ArrayList<String>();

        while(cur.moveToNext()){

            /*
            long calId = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;


            calId = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);*/


            //String title = null;

            //title = cur.getString(1);

            // QUE se hace con toda esta info??????

            //textViewCalendarName.setText(title);

            Date start = new Date(Long.valueOf(cur.getString(2)));
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            eventos.add(new String(cur.getString(1)+ "  on " + df.format(start)));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventos);
        listViewEventos.setAdapter(arrayAdapter);


    }

    public void clickAddEvent(View view){
        Intent intent = new Intent(this, AddCalendarEventActivity.class);
        startActivity(intent);
    }

}
