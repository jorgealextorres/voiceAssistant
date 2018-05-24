package com.example.ray.voiceassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void weatherClick(View view){
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void locationClick(View view){
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void calendarClick(View view){
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void toDoClick(View view){
        Intent intent = new Intent(this, ToDoActivity.class);
        startActivity(intent);
    }

}
