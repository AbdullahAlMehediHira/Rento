package com.example.rento;

public class TenantData {
    private String Email, PhoneNo, Fullname, Username, Gender, LandlordEmail;

    public TenantData(){

    }

    public TenantData(String fullname, String username, String phoneNo, String email, String gender, String landlordEmail) {
        Fullname = fullname;
        Username = username;
        Email = email;
        PhoneNo = phoneNo;
        Gender = gender;
        LandlordEmail = landlordEmail;

    }

    public String getEmail() {
        return Email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getUsername() {
        return Username;
    }

    public String getGender() {
        return Gender;
    }

    public String getLandlordEmail() {
        return LandlordEmail;
    }
}
