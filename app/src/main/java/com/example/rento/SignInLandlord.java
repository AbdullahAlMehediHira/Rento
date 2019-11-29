package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInLandlord extends AppCompatActivity implements OnClickListener {

    private EditText SignInLandlordText, SignInLandlordPassword;
    private Button SignInLandlordButton;
    private TextView SignUpLandlordTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_landlord);
        this.setTitle("Landlord SIGNIN");

        SignInLandlordText = findViewById(R.id.SignInLandlordTextId);
        SignInLandlordPassword = findViewById(R.id.SignInLandlordPasswordId);
        SignInLandlordButton = findViewById(R.id.SignInLandlordButtonId);
        SignUpLandlordTextView = findViewById(R.id.SignUpLandlordTextViewId);

        SignInLandlordButton.setOnClickListener(this);
        SignUpLandlordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignInLandlordButtonId:

                break;

            case R.id.SignUpLandlordTextViewId:
                Intent SignUpLandlordIntent = new Intent(getApplicationContext(), SignUpLandlord.class);
                startActivity(SignUpLandlordIntent);
                break;
        }


    }
}
