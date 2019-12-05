package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private TextView textView;
    private Button tenantButton, landlordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("HOME");

        textView = findViewById(R.id.textView);
        tenantButton = findViewById(R.id.tenantButtonId);
        landlordButton = findViewById(R.id.landlordButtonId);

        tenantButton.setOnClickListener(this);
        landlordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.landlordButtonId:
                Intent landlordSignInIntent = new Intent(getApplicationContext(), SignInLandlord.class);
                startActivity(landlordSignInIntent);
                break;
            case R.id.tenantButtonId:
                Intent tenantSignInIntent = new Intent(getApplicationContext(), SignInTenant.class);
                startActivity(tenantSignInIntent);
                break;
        }

    }
}
