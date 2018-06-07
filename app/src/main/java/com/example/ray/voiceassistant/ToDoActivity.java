package com.example.ray.voiceassistant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ToDoActivity extends Activity {
    private ListView listaTareas;
    private EditText editTextTask;
    private ArrayList tareas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        listaTareas = (ListView) findViewById(R.id.listaTareas);
        editTextTask = (EditText) findViewById(R.id.editTextTask);
    }

    public void clickAdd(View view){
        String tareaNueva = editTextTask.getText().toString();
        tareas.add(tareaNueva);
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tareas);
        listaTareas.setAdapter(arrayAdapter);
    }
}
