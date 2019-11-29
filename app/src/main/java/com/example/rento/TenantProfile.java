package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TenantProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);
        this.setTitle("Tenanat Profile");
    }
}
