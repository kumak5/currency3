package com.example.currency3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //    private TextView textView;
    private RecyclerView currencyListView;
    private Button buttonJson;
    LinkedList<String> names = new LinkedList<>();
    LinkedList<String> values = new LinkedList<>();
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.buttonJson = this.findViewById(R.id.button_json);
        this.currencyListView = this.findViewById(R.id.currencyListView);


//        this.adapter = new Adapter<>(this,
//                android.R.layout.simple_list_item_1);


        currencyListView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomRecyclerAdapter(names, values);
        currencyListView.setAdapter(adapter);



        this.buttonJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                downloadAndShowJson(view);

            }
        });
    }


    // When user click on the "Download Json".
    public void clearScreen(){
        adapter.notifyItemRangeRemoved(0, names.size());
        names.clear(); values.clear();
    }
    public void downloadAndShowJson(View view) {
        String jsonUrl = "https://www.cbr-xml-daily.ru/daily_json.js";

        // Create a task to download and display json content.
        clearScreen();
        DownloadJsonTask task = new DownloadJsonTask(names, values, adapter);

        // Execute task (Pass jsonUrl).
        task.execute(jsonUrl);
    }


}