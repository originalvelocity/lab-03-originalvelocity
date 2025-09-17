package com.example.listycitylab3;

import java.io.Serializable;

public class City implements Serializable {
    private String name;
    private String province;

    public City(String city, String province) {
        this.name = city;
        this.province = province;
    }

    // Getters for city name and province
    public String getName() {return name;}
    public String getProvince() {return province;}

    // Setters for city name and province
    public void setName(String name) {this.name = name;}
    public void setProvince(String province) {this.province = province;}
}
