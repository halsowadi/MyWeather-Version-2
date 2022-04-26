package com.example.aa01_weather_ver02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import com.example.aa01_weather_ver02.databinding.ActivityMyListDetailsBinding;

public class MyListDetailsActivity extends AppCompatActivity {
ActivityMyListDetailsBinding binding;

    ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#11459f"));   //support bar color
    final String cloudy = "!";
    final String windy = "C";
    final String snow = "9";
    final String clear = "I";
    final String rain = "3";
    final String thunder = "F";
    final String mist = "F";
    final String drizzle = "-";
    boolean convert=true;       //flag to switch between F and C


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ////setContentView(R.layout.activity_main);
        binding = ActivityMyListDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (getIntent() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Back");
            //pulling data from extras
            Bundle extras = getIntent().getExtras();
            String lv_Time = extras != null ? extras.getString("Time") : "Empty";
            String lv_City = extras != null ? extras.getString("City") : "Empty";
            String lv_Temp = extras != null ? extras.getString("Temp") : "Empty";
            String lv_Con = extras != null ? extras.getString("Con") : "Empty";

            //setting backbar background color
            getSupportActionBar().setBackgroundDrawable(colorDrawable);

            //setting up weather icons
            Typeface lv_customFont = ResourcesCompat.getFont(this, R.font.climacons);
            binding.WeatherIcon.setTypeface(lv_customFont);
            binding.WeatherIcon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 200);

            binding.Day1.setText("Wen");
            binding.Day1Icon.setText(cloudy);
            binding.Day1Icon.setTypeface(lv_customFont);
            binding.Day1Icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

            binding.Day2.setText("Thu");
            binding.Day2Icon.setText(windy);
            binding.Day2Icon.setTypeface(lv_customFont);
            binding.Day2Icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

            binding.Day3.setText("Fri");
            binding.Day3Icon.setText(snow);
            binding.Day3Icon.setTypeface(lv_customFont);
            binding.Day3Icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);

            binding.Temp.setText(lv_Temp + "°F");
            binding.City.setText(lv_City);
            binding.Weather.setText(lv_Con);
            binding.Convert.setOnClickListener(

                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Implement event handling
                            if (convert==true) {
                                binding.Temp.setText(String.valueOf((int) ((Double.valueOf(lv_Temp) - 32) / 1.8)) + "°C");
                                convert=false;
                            }
                            else {
                                binding.Temp.setText(lv_Temp + "°F");
                                convert = true;
                            }
                        }

                    });
            // case for all possible main conditions
            switch (lv_Con) {

                case "Snow":
                    binding.WeatherIcon.setText(snow);
                    return;
                case "Clear":
                    binding.WeatherIcon.setText(clear);
                    return;
                case "Clouds":
                    binding.WeatherIcon.setText(cloudy);
                    return;
                case "Rain":
                    binding.WeatherIcon.setText(rain);
                    return;
                case "Drizzle":
                    binding.WeatherIcon.setText(drizzle);
                case "Thunderstorm":
                    binding.WeatherIcon.setText(thunder);
                    return;
                case "Mist":
                    binding.WeatherIcon.setText(mist);
                    return;
                default:
                    binding.WeatherIcon.setText(clear);


            }

        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent lv_it = new Intent(MyListDetailsActivity.this, MainActivity.class);
        this.startActivity(lv_it);
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true;
    }
}