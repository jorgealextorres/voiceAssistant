package com.example.ray.voiceassistant;

import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import common.Config;

public class SettingsActivity extends Activity {

    private Button buttonOk;
    private EditText editTextCalendarId;
    private Map<String,String> settingData;
    private final String filename = "settings.obj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        buttonOk = (Button) findViewById(R.id.buttonOk);
        editTextCalendarId = (EditText) findViewById(R.id.editTextCalendarId);

        readDataFromFile();

        if(settingData != null && settingData.containsKey("calendarID")){
            Config.calendarNumber = Integer.valueOf(settingData.get("calendarID"));
            editTextCalendarId.setText(settingData.get("calendarID"));
        } else{
            editTextCalendarId.setText(Config.calendarNumber.toString());
            colectData();
            saveDataInFile();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        readDataFromFile();

        Config.calendarNumber = Integer.valueOf(settingData.get("calendarID"));

    }

    private void colectData(){
        settingData = new HashMap<>();
        settingData.put("calendarID", editTextCalendarId.getText().toString());
    }

    private void saveDataInFile(){
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(settingData);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile(){
        FileInputStream fileInputStream;

        try {
            fileInputStream = openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            settingData = (Map<String, String>) objectInputStream.readObject();
            objectInputStream.close();
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        settingData = new HashMap<>();
    }

    public void clickOk(View view){
        colectData();
        saveDataInFile();
    }
}
