package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Model.ListItem;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button btn;
    private RecyclerView rcv;
    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.inputbox);
        btn = (Button) findViewById(R.id.saveBtn);

        rcv = (RecyclerView) findViewById(R.id.rcvID);
//        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        listItems.add(new ListItem("First Todo!"));

        //TODO: File read write functionalities
        //TODO: Edit layout and fix overlapping issues

        rcv.setAdapter(new MyAdapter(this, listItems));

    }
}