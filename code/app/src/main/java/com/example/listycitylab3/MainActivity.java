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

// ChatGPT suggested imports for it's functions
import android.widget.LinearLayout;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    // Variable declaration
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> dataList;
    Button addCityButton, editCityButton, deleteCityButton;
    int cityListPosition = -1;
    // TextInputEditText cityNameInput; <-- possible demolition


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


        City[] cities = {
                new City("Liberia", "CR-G"),
                new City("Playa Flamingo", "CR-G"),
                new City("Tamarindo", "CR-G"),
                new City("Cañas", "CR-G"),
                new City("La Fortuna", "CR-A"),
                new City("Quepos", "CR-P"),
                new City("Jacó", "CR-P"),
                new City("Nosara", "CR-G"),
                new City("Ciudad Quesada", "CR-A"),
                new City("San José", "CR-S"),
                new City("Siquirres", "CR-L"),
                new City("Cartago", "CR-C"),
                new City("David", "CR-C")
        };

        dataList = new ArrayList<>(Arrays.asList(cities)); // <-- scuffed addition T_T
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
                // Build a container which houses the inputs and title
                LinearLayout container = new LinearLayout(MainActivity.this);
                container.setOrientation(LinearLayout.VERTICAL);
                int pad = (int) (16 * getResources().getDisplayMetrics().density);
                container.setPadding(pad, pad, pad, pad);

                // City gets thrown in here
                final EditText cityInput = new EditText(MainActivity.this);
                cityInput.setHint("e.g. Halifax");
                cityInput.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                // Province gets thrown in here
                final EditText provinceInput = new EditText(MainActivity.this);
                provinceInput.setHint("e.g. NS");
                provinceInput.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                // Add fields to the container set-up just above...
                container.addView(cityInput);
                container.addView(provinceInput);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add City")
                        .setView(container)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = cityInput.getText().toString().trim();
                                String province = provinceInput.getText().toString().trim();

                                if (!name.isEmpty() && !province.isEmpty()) {
                                    dataList.add(new City(name, province));
                                    cityAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "Bruh... Put both fields!", Toast.LENGTH_SHORT).show(); // pop-up to guide the user... 'cause why not
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
                    Toast.makeText(MainActivity.this, "Please select a city to edit...", Toast.LENGTH_SHORT).show();
                    return;
                }

                final City current = dataList.get(cityListPosition);

                // Build the same 2-field container programmatically
                LinearLayout container = new LinearLayout(MainActivity.this);
                container.setOrientation(LinearLayout.VERTICAL);
                int pad = (int) (16 * getResources().getDisplayMetrics().density);
                container.setPadding(pad, pad, pad, pad);

                final EditText cityInput = new EditText(MainActivity.this);
                cityInput.setHint("e.g. Halifax");
                cityInput.setText(current.getName());

                final EditText provinceInput = new EditText(MainActivity.this);
                provinceInput.setHint("e.g. NS");
                provinceInput.setText(current.getProvince());

                container.addView(cityInput);
                container.addView(provinceInput);

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Edit City")
                        .setView(container)
                        .setPositiveButton("Yep", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = cityInput.getText().toString().trim();
                                String newProv = provinceInput.getText().toString().trim();

                                if (!newName.isEmpty() && !newProv.isEmpty()) {
                                    current.setName(newName);
                                    current.setProvince(newProv);
                                    cityAdapter.notifyDataSetChanged();
                                    cityListPosition = -1; // reset selection after edit
                                } else {
                                    Toast.makeText(MainActivity.this, "Bruh... Put both fields!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Nah", new DialogInterface.OnClickListener() {
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