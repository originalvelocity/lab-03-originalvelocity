package com.example.listycitylab3;

import android.os.Bundle;

public class addCityFragment {

    // Code portion from lab 3 Hint #3
    static addCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);

        addCityFragment fragment = new addCityFragment();
        fragment.setArguments(args);

        return fragment;
    }

    private void setArguments(Bundle args) {

    }
}
