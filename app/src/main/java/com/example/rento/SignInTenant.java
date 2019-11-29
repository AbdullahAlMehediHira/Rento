package com.example.rento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignInTenant extends AppCompatActivity implements OnClickListener {

    private EditText SignInTenantText, SignInTenantPassword;
    private Button SignInTenantButton;
    private TextView SignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_tenant);
        this.setTitle("Tenant SIGNIN");


        SignInTenantText = findViewById(R.id.SignInTenantTextId);
        SignInTenantPassword = findViewById(R.id.SignInTenantPasswordId);
        SignInTenantButton = findViewById(R.id.SignInTenantButtonId);
        SignUpTextView = findViewById(R.id.SignUpTenantTextViewId);

        SignInTenantButton.setOnClickListener(this);
        SignUpTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.SignInTenantButtonId:
                break;

            case R.id.SignUpTenantTextViewId:
                Intent SignUpTenantIntent = new Intent(getApplicationContext(), SignUpTenant.class);
                startActivity(SignUpTenantIntent);
                break;

        }

    }
}
