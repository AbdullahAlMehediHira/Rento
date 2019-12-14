package com.example.rento;

public class LandlordSignUp {
    public String Email, UserName, Fullname, Gender, Address;

    public LandlordSignUp(){

    }

    public LandlordSignUp(String fullname, String userName, String email, String gender, String address ) {
        Fullname = fullname;
        Email = email;
        UserName = userName;
        Gender = gender;
        Address = address;
    }


}
