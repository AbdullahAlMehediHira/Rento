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

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
