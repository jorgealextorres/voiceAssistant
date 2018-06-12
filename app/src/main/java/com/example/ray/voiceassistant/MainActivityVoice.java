package com.example.ray.voiceassistant;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import common.CalendarService;
import common.WeatherService;

public class MainActivityVoice extends Activity {
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private EditText editTextTextTest;
    private Button buttonTextTest;
    private TextView textViewLastQueryRecognition;
    private TextView textViewCurrentVoiceRecognition;

    private CardView cardViewWeather;
    private CardView cardViewCalendar;
    private CardView cardViewLocation;
    private CardView cardViewToDo;

    //weather
    private TextView textViewCity;
    private TextView textViewCurrentTemp;

    //Calendar
    private ListView listViewCalendar;

    private ImageButton imageButtonKeyboard;
    private ImageButton imageButtonMicro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voice);

        textViewLastQueryRecognition = (TextView) findViewById(R.id.textViewLastQueryRecognition);
        textViewCurrentVoiceRecognition = (TextView) findViewById(R.id.textViewCurrentVoiceRecognition);

        cardViewWeather = (CardView) findViewById(R.id.cardViewWeather);
        cardViewCalendar = (CardView) findViewById(R.id.cardViewCalendar);
        cardViewLocation = (CardView) findViewById(R.id.cardViewLocation);
        cardViewToDo = (CardView) findViewById(R.id.cardViewToDo);

        activateViewCard(0);

        editTextTextTest = (EditText) findViewById(R.id.editTextTextTest);

        buttonTextTest = (Button) findViewById(R.id.buttonTextTest);
        imageButtonKeyboard = (ImageButton) findViewById(R.id.imageButtonKeyboard);
        imageButtonMicro = (ImageButton) findViewById(R.id.imageButtonMicro);

        imageButtonMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                try{
                    startActivityForResult(intent,200);
                }catch (ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"Intent problem", Toast.LENGTH_SHORT).show();
                }
            }
        });



        textViewCity = (TextView) findViewById(R.id.textViewCity);
        textViewCurrentTemp = (TextView) findViewById(R.id.textViewCurrentTemp);

        listViewCalendar = (ListView) findViewById(R.id.listViewCalendar);


    }

    public void clickKeyboard(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickSettings(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void activateViewCard(int codigo){
        switch(codigo){
            case 1: //weather
                cardViewWeather.setVisibility(View.VISIBLE);
                cardViewCalendar.setVisibility(View.INVISIBLE);
                cardViewLocation.setVisibility(View.INVISIBLE);
                cardViewToDo.setVisibility(View.INVISIBLE);
                break;
            case 2: //calendar
                cardViewCalendar.setVisibility(View.VISIBLE);
                cardViewWeather.setVisibility(View.INVISIBLE);
                cardViewLocation.setVisibility(View.INVISIBLE);
                cardViewToDo.setVisibility(View.INVISIBLE);
                break;
            case 3: // to do
                cardViewToDo.setVisibility(View.VISIBLE);
                cardViewWeather.setVisibility(View.INVISIBLE);
                cardViewCalendar.setVisibility(View.INVISIBLE);
                cardViewLocation.setVisibility(View.INVISIBLE);
                break;
            case 4: // location
                cardViewLocation.setVisibility(View.VISIBLE);
                cardViewWeather.setVisibility(View.INVISIBLE);
                cardViewCalendar.setVisibility(View.INVISIBLE);
                cardViewToDo.setVisibility(View.INVISIBLE);
                break;
            default:
                cardViewLocation.setVisibility(View.VISIBLE);
                cardViewWeather.setVisibility(View.INVISIBLE);
                cardViewCalendar.setVisibility(View.INVISIBLE);
                cardViewToDo.setVisibility(View.INVISIBLE);
        }
    }

    public void clickTest(View view){
        String textoReconocido = editTextTextTest.getText().toString();

        manageSpeechRecognition(textoReconocido);
    }

    private void manageSpeechRecognition(String text){

        try {
            textViewLastQueryRecognition.setText(text);

            // Ex: check weather in Barcelona
            if(isCheckWeatherInCity(text.toLowerCase())){
                activateViewCard(1);

                String city = text.toLowerCase().replaceAll("check weather in ", "");

                JSONObject data = WeatherService.getWeatherForToday(city);

                textViewCity.setText(data.getString("name").toUpperCase(Locale.getDefault()) + ", " + data.getJSONObject("sys").getString("country"));
                textViewCurrentTemp.setText(data.getJSONObject("main").getString("temp") + " °C");


            } else if(isCheckCalendar(text.toLowerCase())){
                activateViewCard(2);

                ContentResolver cr = getContentResolver();
                Activity act = this;
                Context ct = this;

                ArrayList eventos = CalendarService.consultaCalendars(cr,ct,act);

                if(eventos != null){
                    ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ct, android.R.layout.simple_list_item_1, eventos);
                    listViewCalendar.setAdapter(arrayAdapter);
                }
            } else{
                activateViewCard(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPattern(String patternRegExp, String text){
        Pattern pattern = Pattern.compile(patternRegExp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    private boolean isCheckWeatherInCity(String sentence){
        String expression = "check weather in \\w";
        return checkPattern(expression, sentence);
    }

    private boolean isCheckCalendar(String sentence){
        String expression = "check calendar";
        return checkPattern(expression, sentence);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200){
            if(resultCode == RESULT_OK && data != null){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textViewCurrentVoiceRecognition.setText(result.get(0));
                manageSpeechRecognition(result.get(0));
            }
        }
    }
}
