package com.example.rento;

public class TenantData {
    public String Email, PhoneNo, Fullname, Gender;

    public TenantData(){

    }

    public TenantData(String fullname, String phoneNo, String email, String gender) {
        Fullname = fullname;
        Email = email;
        PhoneNo = phoneNo;
        Gender = gender;
    }
}
