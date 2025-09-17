package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<City> dataList; // changed the ArrayList generic from "String" to "City"
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;

    public void addCity(City city)
    {
        // Add a new city
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        String[] provinces = {"CA", "CA", "RU", "AU", "DE", "AT", "JP", "CH", "JP", "IN"};

        dataList = new ArrayList<>();
        // Cannot just add the cities like a list of String[]. Need to add object City
        //dataList.addAll(Arrays.asList(cities));
        for (int i = 0; i < cities.length; i++)
        {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);
    }
}