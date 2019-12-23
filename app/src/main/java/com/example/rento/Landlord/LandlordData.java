package com.example.rento.Landlord;

public class LandlordData  {
    private String email, username, fullname, gender, address;

    public LandlordData(){

    }

    public LandlordData(String fullname, String username, String email, String gender, String address) {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.gender = gender;
        this.address = address;

    }

    public void setGender(String gender){
        this.gender = gender;
    }


    public String getemail() {
        return email;
    }



    public String getusername() {
        return this.username;
    }



    public String getfullname() {
        return fullname;
    }


    public String getgender() {
        return gender;
    }



    public String getaddress() {
        return this.address;
    }





}
