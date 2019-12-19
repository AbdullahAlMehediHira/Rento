package com.example.rento.Landlord;

public class LandlordData {
    private String Email, UserName, Fullname, Gender, Address;

    public LandlordData(){

    }

    public LandlordData(String fullname, String username, String email, String gender, String address ) {
        Fullname = fullname;
        Email = email;
        UserName = username;
        Gender = gender;
        Address = address;
    }
}
