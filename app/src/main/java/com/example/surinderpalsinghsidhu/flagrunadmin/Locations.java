package com.example.surinderpalsinghsidhu.flagrunadmin;

public class Locations {


    public String latitude;
    public String longitude;
    public String Name;


    public Locations() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Locations(String latitude, String longitude,String playerName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.Name = playerName;

    }
}
