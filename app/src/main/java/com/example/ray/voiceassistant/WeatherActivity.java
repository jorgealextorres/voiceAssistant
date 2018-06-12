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

import common.WeatherService;

public class WeatherActivity extends Activity {

    //private static final String OPEN_WEATHER_MAP_API_CALL = "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric%s%s&cnt=2";
    private static String IMG_URL = "http://openweathermap.org/img/w/";
    private EditText text;
    private TextView labelCity;
    private ImageView icon;

    private TextView temperatura;
    private TextView temperaturaMax;
    private TextView temperaturaMin;

    private TextView labelCity2;
    private ImageView icon2;

    private TextView temperatura2;
    private TextView temperaturaMax2;
    private TextView temperaturaMin2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        text = (EditText) findViewById(R.id.editText2);
        labelCity = (TextView) findViewById(R.id.textViewCity);
        icon = (ImageView) findViewById(R.id.imageView);
        temperatura = (TextView) findViewById(R.id.textViewTemperatura);
        temperaturaMax = (TextView) findViewById(R.id.textViewTemperaturaMax);
        temperaturaMin = (TextView) findViewById(R.id.textViewTemperaturaMin);
        labelCity2 = (TextView) findViewById(R.id.textViewCity2);
        icon2 = (ImageView) findViewById(R.id.imageView2);
        temperatura2 = (TextView) findViewById(R.id.textViewTemperatura2);
        temperaturaMax2 = (TextView) findViewById(R.id.textViewTemperaturaMax2);
        temperaturaMin2 = (TextView) findViewById(R.id.textViewTemperaturaMin2);

    }

    public void goClick(View view) {


        JSONObject data = WeatherService.getWeatherForNDays(text.getText().toString(), 2);
        String cityCountry = null;
        try {


            cityCountry = data.getJSONObject("city").getString("name").toUpperCase(Locale.getDefault()) + ", " + data.getJSONObject("city").getString("country");

            JSONObject dataToday = data.getJSONArray("list").getJSONObject(0);
            labelCity.setText(cityCountry);
            temperatura.setText(dataToday.getJSONObject("main").getString("temp") + " C");
            temperaturaMax.setText("max : " + dataToday.getJSONObject("main").getString("temp_max") + " °C");
            temperaturaMin.setText("min : " + dataToday.getJSONObject("main").getString("temp_min") + " °C");

            String urlIconUriString = "http://openweathermap.org/img/w/" + dataToday.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png";
            URL url = new URL(urlIconUriString);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            icon.setImageBitmap(bmp);


            // mañana
            JSONObject dataTomorrow = data.getJSONArray("list").getJSONObject(1);
            labelCity2.setText(cityCountry);
            temperatura2.setText(dataTomorrow.getJSONObject("main").getString("temp") + " C");
            temperaturaMax2.setText("max : " + dataTomorrow.getJSONObject("main").getString("temp_max") + " °C");
            temperaturaMin2.setText("min : " + dataTomorrow.getJSONObject("main").getString("temp_min") + " °C");


            String urlIconUriString2 = "http://openweathermap.org/img/w/" + dataTomorrow.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png";
            URL url2 = new URL(urlIconUriString2);
            Bitmap bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
            icon2.setImageBitmap(bmp2);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}