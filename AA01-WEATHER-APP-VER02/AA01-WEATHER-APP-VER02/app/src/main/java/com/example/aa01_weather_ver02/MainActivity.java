package com.example.aa01_weather_ver02;
/*
Hussein Alsowadi
Last Updated: 1/23/22
Myweather App Version 2.0
This is a prototype/concept weather app. More Versions to come
App provides 7 predetermined Cities and returns weather information
Screen One has accurate Data
Screen Two has placeholder for High, Low, and 3 day forecast
 */

import com.example.aa01_weather_ver02.databinding.ActivityMainBinding;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

     String json = " ";
    //weather json strings
    final String stringUrl = "https://api.openweathermap.org/data/2.5/weather?zip=48197,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl2 = "https://api.openweathermap.org/data/2.5/weather?zip=85365,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl3 = "https://api.openweathermap.org/data/2.5/weather?zip=99703,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl4 = "https://api.openweathermap.org/data/2.5/weather?zip=48187,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl5 = "https://api.openweathermap.org/data/2.5/weather?zip=78954,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl6 = "https://api.openweathermap.org/data/2.5/weather?zip=12235,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";
    final String stringUrl7 = "https://api.openweathermap.org/data/2.5/weather?zip=48223,us&appid=553a855bc2d549ac8dc0f12b2ef3d5b8";

//other variables
    double tempHigh;
    double tempLow;
    double temp;
    String city;
    String Zip;
    String condition;

    ArrayList<String> lv_Zip = new ArrayList<String>();
    ArrayList<String> lv_City = new ArrayList<String>();
    ArrayList<Integer> lv_Temp = new ArrayList<Integer>();
    ArrayList<String> lv_Con = new ArrayList<String>();

    MyRecyclerViewAdapter lv_adapter;
    ArrayList<MyRecyclerViewData> lv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ////setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //this pulls live data on start
        getSupportActionBar().hide();
            try {
            parseJson(httpInThread(stringUrl));
            parseJson(httpInThread(stringUrl2));
            parseJson(httpInThread(stringUrl3));
            parseJson(httpInThread(stringUrl4));
            parseJson(httpInThread(stringUrl5));
            parseJson(httpInThread(stringUrl6));
            parseJson(httpInThread(stringUrl7));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // :'(
        lv_Zip.add("48197");
        lv_Zip.add("85465");
        lv_Zip.add("99703");
        lv_Zip.add("48187");
        lv_Zip.add("78954");
        lv_Zip.add("12235");
        lv_Zip.add("48223");
        lv_data = new ArrayList<>();
            for (int i = 0; i < lv_Zip.size(); i++) {
                lv_data.add(new MyRecyclerViewData(lv_Zip.get(i), lv_City.get(i), (lv_Temp.get(i)), lv_Con.get(i)));
            }
            RecyclerView lv_recyclerView = findViewById(R.id.vv_rvList);
            lv_recyclerView.setLayoutManager(new LinearLayoutManager(this));

            lv_adapter = new MyRecyclerViewAdapter(lv_data,this);
            lv_recyclerView.setAdapter(lv_adapter);
            lv_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

/*
        //setting fab press
        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Different way to show fab message
                //// Snackbar.make(view, R.string.fab, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                Toast.makeText(getApplicationContext(), R.string.fab, Toast.LENGTH_LONG).show();
               // httpInThread(stringUrl);
            }
        });
*/
    }



    private String parseJson(String input) {


        String output = "Error parsing Json";
        JSONParser parser = new JSONParser();
        Calendar calender =  Calendar.getInstance();

        try {
            JSONObject jsonRootObject = (JSONObject) parser.parse(input);
            JSONArray jsonWeatherArray = (JSONArray) jsonRootObject.get("weather");
            condition = ((JSONObject) jsonWeatherArray.get(0)).get("main").toString();
            JSONObject jsonMainObject = (JSONObject) jsonRootObject.get("main");
            temp = Double.parseDouble(jsonMainObject.get("temp").toString());
            tempHigh = Double.parseDouble(jsonMainObject.get("temp_max").toString());
            tempLow = Double.parseDouble(jsonMainObject.get("temp_min").toString());
            city = jsonRootObject.get("name").toString();
            Zip = jsonRootObject.get("timezone").toString();
            lv_Con.add(condition);
            lv_City.add(city);

            lv_Temp.add((int) ((temp*1.8)-460));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output;
    }

    private String httpInThread(String url) throws InterruptedException {
        Thread th =  new Thread(new Runnable() {
            String result = "HTTPS unable to get";
            @Override
            public void run() {
                // do your stuff

                try {
                    result = downloadUrl(url);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                json = result;


            }
        });
        th.start();
        th.join();


return json;


    }

    private String downloadUrl(String urlString) throws IOException {
        String result = "Download https error";
        InputStream in = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            in = conn.getInputStream();

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (IOException e) {
        } finally {
            if(in != null)
                try {
                    in.close();
                    reader.close();
                } catch (IOException e) {
                }
        }
        return result;
    }



}