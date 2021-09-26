package com.example.currency3;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

// A task with String input parameter, and returns the result as String.
public class DownloadJsonTask
        // AsyncTask<Params, Progress, Result>
        extends AsyncTask<String, Void, String> {


    LinkedList<String> names;
    LinkedList<String> values;
    RecyclerView.Adapter adapter;

    public DownloadJsonTask(LinkedList<String> names, LinkedList<String> values, RecyclerView.Adapter adapter) {
        this.names = names;
        this.values = values;
        this.adapter = adapter;
    }

    @Override
    protected String doInBackground(String... params) {
        String textUrl = params[0];

        InputStream in = null;
        BufferedReader br= null;
        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                br= new BufferedReader(new InputStreamReader(in));

                StringBuilder sb= new StringBuilder();
                String s= null;
                while((s= br.readLine())!= null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // When the task is completed, this method will be called
    // Download complete. Lets update UI
    @Override
    protected void onPostExecute(String result) {
        if(result  != null){


            try {
                JSONObject jsonRoot = new JSONObject(result);
                JSONObject valute = jsonRoot.getJSONObject("Valute");
//                valute.keys();
//                JSONObject AUD = valute.getJSONObject("AUD");
//                adapter.clear();
                int pos = 0;
                for (Iterator<String> it = valute.keys(); it.hasNext(); ) {
                    pos += 1;
                    String key = it.next();
                    JSONObject jsonKey = valute.getJSONObject(key);
                    String name = jsonKey.getString("Name");
                    String value = jsonKey.getString("Value");
                    Log.d("INFO", name + " " + value);
                    names.add(name);
                    values.add(value);

                    adapter.notifyItemChanged(pos);
//                    adapter.add(String.format("%s : %s", name, value));


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            this.textView.setText(result);
        } else{
            Log.e("MyMessage", "Failed to fetch data!");
        }
    }
}