package com.example.ray.voiceassistant;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class WeatherActivity extends Activity {

    private static final String OPEN_WEATHER_MAP_API_CALL = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric%s%s";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private EditText text;
    private TextView labelCity;
    private ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        text = (EditText) findViewById(R.id.editText2);
        labelCity = (TextView) findViewById(R.id.textViewCity);
        icon = (ImageView) findViewById(R.id.imageView);

    }

    public void goClick(View view) {
        String urlWithApiKey = String.format(OPEN_WEATHER_MAP_API_CALL, text.getText(), "&appid=", "b3483beadbaac4f77a0e9d41105bab06");

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL urlOpenWeatherMapAPI = new URL(urlWithApiKey);
            HttpURLConnection connection = (HttpURLConnection) urlOpenWeatherMapAPI.openConnection();

            if(connection.getResponseCode() == 200){

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(2048);
                String aux = "";
                while ((aux = reader.readLine()) != null)
                    json.append(aux).append("\n");

                reader.close();

                JSONObject data = new JSONObject(json.toString());

                String cityCountry = data.getString("name").toUpperCase(Locale.getDefault()) + ", " +
                        data.getJSONObject("sys").getString("country");

                labelCity.setText(cityCountry);

                //icon.setImageURI(Uri.parse("http://openweathermap.org/img/w/" + data.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png"));


                String urlIconUriString = "http://openweathermap.org/img/w/" + data.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png";
                URL url = new URL(urlIconUriString);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                icon.setImageBitmap(bmp);


            } else{
                labelCity.setText("Data not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*
    public byte[] getImage(String code) {
        HttpURLConnection con = null ;
        InputStream is = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = (HttpURLConnection) ( new URL(IMG_URL + code + ".png")).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            while ( is.read(buffer) != -1)
                baos.write(buffer);

            return baos.toByteArray();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }

        return null;

    }
    */
}