package com.example.rento.Landlord;

public class LandlordData {
    private String email, username, fullname, gender, address;

    public LandlordData(){

    }

    public LandlordData(String landlordfullname, String landlordusername, String landlordemail, String landlordgender, String landlordaddress ) {
        fullname = landlordfullname;
        email = landlordemail;
        username = landlordusername;
        gender = landlordgender;
        address = landlordaddress;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String landlordemail) {
        email = landlordemail;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String landlordusername) {
        username = landlordusername;
    }

    public String getfullname() {
        return fullname;
    }

    public void setfullname(String landlordfullname) {
        fullname = landlordfullname;
    }

    public String getgender() {
        return gender;
    }

    public void setGender(String landlordgender) {
        gender = landlordgender;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String landlordaddress) {
        address = landlordaddress;
    }
}
