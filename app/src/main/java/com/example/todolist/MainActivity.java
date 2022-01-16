package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

        listItems = new ArrayList<>();
        try {
            readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        input = (EditText) findViewById(R.id.inputbox);
        btn = (Button) findViewById(R.id.saveBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = input.getText().toString();
                if (!msg.equals("")) {
                    writeToFile(msg);
                }
                try {
                    readFromFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        rcv = (RecyclerView) findViewById(R.id.rcvID);
//        rcv.setHasFixedSize(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));

        //listItems.add(new ListItem("First Todo!"));

        //TODO: Database access. Edit read write method and implement appropriately.

        rcv.setAdapter(new MyAdapter(this, listItems));

    }

    private void writeToFile(String msg) {

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("todolist.txt", MODE_APPEND));

            outputStreamWriter.write(msg);
            outputStreamWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFromFile() throws IOException {

        String result = "";

        InputStream inputStream = openFileInput("todolist.txt");

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String tempString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while( (tempString = bufferedReader.readLine()) != null) {

                listItems.add(new ListItem(tempString));
                stringBuilder.append(tempString);

            }

            inputStream.close();
            result = stringBuilder.toString();

        }

        return result;
    }
}