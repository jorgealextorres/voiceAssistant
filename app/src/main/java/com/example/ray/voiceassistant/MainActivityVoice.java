package com.example.ray.voiceassistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityVoice extends Activity {
    private EditText editTextTextTest;
    private Button buttonTextTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voice);

        editTextTextTest = (EditText) findViewById(R.id.editTextTextTest);
        buttonTextTest = (Button) findViewById(R.id.buttonTextTest);


    }

    public void clickKeyboard(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void clickTest(View view){

    }

    private void getSentence(String sentence){
        if(sentence.startsWith("Check weather in ")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

    }
}
