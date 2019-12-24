package com.example.rento.Landlord;


import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class RentPost {
    private @ServerTimestamp
    Date timestamp;



    private String cost;
    private GeoPoint geoPoint;
    private LandlordData landlordData;
    private String numberofrooms;
    boolean available=true;
    private String avater;
    private String squarefeet;

    public RentPost(Date timestamp, String cost, String numberofrooms, GeoPoint geoPoint, LandlordData landlordData, String avater, String squarefeet){
       this.timestamp=timestamp;
       this.cost=cost;
       this.geoPoint=geoPoint;
       this.landlordData = landlordData;
       this.numberofrooms=numberofrooms;
       this.avater = avater;
       this.squarefeet = squarefeet;

    }
    public RentPost(){


    }


    public String getcost() {
        return cost;
    }



    public GeoPoint getgeopoint() {
        return geoPoint;
    }

    public void setgeopoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getnumberofrooms() {
        return numberofrooms;
    }

    public String getsquarefeet(){
        return squarefeet;
    }

    public LandlordData getlandlordData() {
        return landlordData;
    }
    public boolean getavailable(){
        return available;
    }

    public void setavailable(boolean available) {
        this.available = available;
    }




}

