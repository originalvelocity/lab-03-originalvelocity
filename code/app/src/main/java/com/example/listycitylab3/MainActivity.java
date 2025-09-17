package com.example.listycitylab3;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Variable declaration
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addCityButton, editCityButton, deleteCityButton;
    int cityListPosition = -1;
    TextInputEditText cityNameInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Views need to be bound**
        cityList = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.btn_add_city);
        editCityButton = findViewById(R.id.btn_edit_city);
        deleteCityButton = findViewById(R.id.btn_delete_city);


        String[] cities = {"Liberia", "Playa Flamingo", "Tamarindo", "Canas", "La Fortuna", "Quepos", "Jaco", "Nosara", "Ciudad Quesada","San Jose", "Siquirres", "Cartago", "David"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter); // Attach ArrayAdapter to ListView, will display the content of ArrayList


        // Select from list of cities on screen
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < dataList.size()) {
                    cityListPosition = position;
                }
            }
        });

        // Add a city button shows a pop-up to which we add
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = new EditText(MainActivity.this);
                input.setHint("Enter a city name...");

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add City")
                        .setView(input)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cityName = input.getText().toString().trim();
                                if (!cityName.isEmpty()) {
                                    dataList.add(cityName);
                                    cityAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        // Edit a city button logic
        editCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityListPosition < 0 || cityListPosition >= dataList.size()) {
                    Toast.makeText(MainActivity.this, "Please select a city to edit", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String currentName = dataList.get(cityListPosition);
                final EditText input = new EditText(MainActivity.this);
                input.setText(currentName);
                input.setSelection(currentName.length());

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Edit City")
                        .setView(input)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = input.getText().toString().trim();
                                if (!newName.isEmpty()) {
                                    dataList.set(cityListPosition, newName);
                                    cityAdapter.notifyDataSetChanged();
                                    cityListPosition = -1; // reset selection after edit
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        // Delete a city button logic
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityListPosition >= 0) {
                    dataList.remove(cityListPosition);
                    // Prevent further deletion if user hasn't selected city
                    cityListPosition = -1;
                }
                cityAdapter.notifyDataSetChanged();
            }
        });
    }
}