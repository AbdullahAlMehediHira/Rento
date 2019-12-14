package com.example.rento;

public class LandlordData {
    public String Email, UserName, Fullname, Gender, Address;

    public LandlordData(){

    }

    public LandlordData(String fullname, String userName, String email, String gender, String address ) {
        Fullname = fullname;
        Email = email;
        UserName = userName;
        Gender = gender;
        Address = address;
    }
}
