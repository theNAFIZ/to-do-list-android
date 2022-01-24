package com.example.todolist;

import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyAdapter;
import Data.DatabaseHandler;
import Model.Todo;

public class MainActivity extends AppCompatActivity {

    private EditText input;
    private Button btn;
    private RecyclerView rcv;
    private List<Todo> listItems = new ArrayList<>();
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);

        rcv = (RecyclerView) findViewById(R.id.rcvID);
        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        reloadList();

        input = (EditText) findViewById(R.id.inputbox);
        btn = (Button) findViewById(R.id.saveBtn);

        btn.setOnClickListener(view -> {
            input.onEditorAction(EditorInfo.IME_ACTION_DONE);
            String msg = input.getText().toString();
            if (!msg.equals("")) {
                Todo todo = new Todo(msg, false);
                dbHandler.addTodo(todo);
                Snackbar.make(view, "Item saved!",Snackbar.LENGTH_LONG).show();
                input.setText("");
                reloadList();
            }
        });
    }

    public void reloadList() {
        listItems = dbHandler.getAllTodos();
        rcv.setAdapter(new MyAdapter(this, listItems));
    }
}