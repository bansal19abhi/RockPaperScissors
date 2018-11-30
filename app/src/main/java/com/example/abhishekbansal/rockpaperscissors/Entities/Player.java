package com.example.abhishekbansal.rockpaperscissors.Entities;

public class Player {
    private String phoneNumber;
    private double lat;
    private double lng;

    /*Constructor*/
    public Player(String phoneNumber, double lat, double lng) {
        this.phoneNumber = phoneNumber;
        this.lat = lat;
        this.lng = lng;
    }
    /*2º Constructor*/
    public Player(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.lat = lat;
        this.lng = lng;
    }

    /*Getters and setters*/
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
