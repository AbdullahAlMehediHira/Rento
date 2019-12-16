package com.example.rento;

public class TenantData {
    public String Email, PhoneNo, Fullname, Username, Gender;

    public TenantData(){

    }

    public TenantData(String fullname, String username, String phoneNo, String email, String gender) {
        Fullname = fullname;
        Username = username;
        Email = email;
        PhoneNo = phoneNo;
        Gender = gender;
    }
}
