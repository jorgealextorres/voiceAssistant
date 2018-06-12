package common;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;

import com.google.android.gms.common.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CalendarService {
    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 100;
    private static final int CALENDAR_ID = Config.calendarNumber; // TODO : cambiar a 1 porque el 3 nos va bien a nosotros específicamente

    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Events.CALENDAR_ID,
            CalendarContract.Events.TITLE,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.DTEND
    };

    public static ArrayList consultaCalendars(ContentResolver cr, Context ct, Activity act) {
        Cursor cur = null;

        Uri uri = CalendarContract.Events.CONTENT_URI;
        String selection = "(" + CalendarContract.Events.CALENDAR_ID + "= ?)";

        String[] selectionArgs = new String[]{String.valueOf(CALENDAR_ID)};


        if (ActivityCompat.checkSelfPermission(ct, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(act,
                    Manifest.permission.READ_CALENDAR)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(act,
                        new String[]{Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_READ_CALENDAR);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        }


        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        ArrayList eventos = new ArrayList<String>();

        while(cur.moveToNext()){

            Date start = new Date(Long.valueOf(cur.getString(2)));
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            eventos.add(new String(cur.getString(1)+ "  on " + df.format(start)));
        }

        if(CollectionUtils.isEmpty(eventos)){
            eventos = null;
        }

        return eventos;

    }

}
