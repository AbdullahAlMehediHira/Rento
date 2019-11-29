package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LandlordProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_profile);
        this.setTitle("Landlord Profile");
    }
}
