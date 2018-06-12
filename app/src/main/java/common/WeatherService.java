package common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class WeatherService {
    private static final String OPEN_WEATHER_MAP_API_CALL_FORECAST = "http://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric%s%s%s%s";
    private static final String OPEN_WEATHER_MAP_API_CALL_WEATHER = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric%s%s";

    public static JSONObject getWeather(String cityInput, String url){
        //String urlWithApiKey = String.format(OPEN_WEATHER_MAP_API_CALL, cityInput, "&appid=", "b3483beadbaac4f77a0e9d41105bab06", "&cnt=", numberOfDaysInput);
        JSONObject data = null;

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            URL urlOpenWeatherMapAPI = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlOpenWeatherMapAPI.openConnection();

            if(connection.getResponseCode() == 200){

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer json = new StringBuffer(2048);
                String aux = "";
                while ((aux = reader.readLine()) != null)
                    json.append(aux).append("\n");

                reader.close();

                data = new JSONObject(json.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            data = null;
        } finally{
            return data;
        }

    }

    public static JSONObject getWeatherForNDays(String cityInput, int numberOfDaysInput){
        String urlWithApiKey = String.format(OPEN_WEATHER_MAP_API_CALL_FORECAST, cityInput, "&appid=", "b3483beadbaac4f77a0e9d41105bab06", "&cnt=", numberOfDaysInput);
        return getWeather(cityInput, urlWithApiKey);
    }
    public static JSONObject getWeatherForToday(String cityInput){
        String urlWithApiKey = String.format(OPEN_WEATHER_MAP_API_CALL_WEATHER, cityInput, "&appid=", "b3483beadbaac4f77a0e9d41105bab06");
        return getWeather(cityInput, urlWithApiKey);
    }
}
