package com.example.dream;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

//Klasse für die To-Do-Liste
public class ToDoList extends AppCompatActivity {

    private Button profil;

    private ArrayList<String> tasks;
    private ArrayAdapter<String> taskAdapter;
    private ListView listView;
    private Button addTask;

    //onCreate Methode, onClick Methoden
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);

        profil = (Button) findViewById(R.id.profil);
        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToDoList.this, Profile.class));
            }
        });

        listView = findViewById(R.id.listView);
        addTask = findViewById(R.id.addTask);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        tasks = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tasks);
        listView.setAdapter(taskAdapter);
        setDeleteTask();
    }

    //Methode zum löschen der Task in der ListView
    private void setDeleteTask() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Task wurde gelöscht!", Toast.LENGTH_LONG).show();
                tasks.remove(i);
                taskAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //Methode zum hinzufügen von Tasks
    private void addItem(View v) {
        EditText input = findViewById(R.id.editTask);
        String itemText = input.getText().toString();

        if (!(itemText.equals(""))) {
            taskAdapter.add(itemText);
            input.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "Bitte einen Text eingeben!", Toast.LENGTH_LONG).show();
        }
    }
}