package com.example.listycitylab3;

public class City {
    private String name;
    private String province;

    public City(String name, String province) {
        this.name = name;
        this.province = province;
    }

    // Getters for city name and province name
    public String getName() { return name; }

    public String getProvince() { return province; }

    // Setters for city name and province name
    public void setName(String name) { this.name = name; }

    public void setProvince(String province) { this.province = province; }

    // Overriding the main thing here to get a space between city name and province name
    @Override
    public String toString() {
        return name + "  " + province; // <-- two spaces in between
    }
}
