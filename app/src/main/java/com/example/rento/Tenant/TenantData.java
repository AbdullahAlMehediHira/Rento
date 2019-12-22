package com.example.rento.Tenant;

public class TenantData {
    private String email, phoneNo, fullname, username, gender, landlordName;

    public TenantData(){

    }

    public TenantData(String tenantfullname, String tenantusername, String tenantphoneNo, String tenantemail, String tenantgender, String tenantlandlordName) {
        fullname = tenantfullname;
        username = tenantusername;
        email = tenantemail;
        phoneNo = tenantphoneNo;
        gender = tenantgender;
        landlordName = tenantlandlordName;

    }

    public String getemail() {
        return email;
    }

    public String getphoneNo() {
        return phoneNo;
    }

    public String getfullname() {
        return fullname;
    }

    public String getusername() {
        return username;
    }

    public String getgender() {
        return gender;
    }

    public String getlandlordName() {
        return landlordName;
    }
}
