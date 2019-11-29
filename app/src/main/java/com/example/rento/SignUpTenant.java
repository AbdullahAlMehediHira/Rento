package com.example.rento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpTenant extends AppCompatActivity implements OnClickListener{
    private EditText SignUpTenantText, SignUpTenantPassword;
    private Button SignUpTenantButton;
    private TextView SignInTenantTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tenant);
        this.setTitle("Tenant SIGNUP");
        mAuth = FirebaseAuth.getInstance();

        SignUpTenantText = findViewById(R.id.SignUpTenantTextId);
        SignUpTenantPassword = findViewById(R.id.SignUpTenantPasswordId);
        SignUpTenantButton = findViewById(R.id.SignUpTenantButtonId);
        SignInTenantTextView = findViewById(R.id.SignInTenantTextViewId);

        SignUpTenantButton.setOnClickListener(this);
        SignInTenantTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.SignUpTenantButtonId:
                tenantRegister();
                break;

            case R.id.SignInTenantTextViewId:
                Intent SignInTenantIntent = new Intent(getApplicationContext(), SignInTenant.class);
                startActivity(SignInTenantIntent);
                break;

        }

    }

    private void tenantRegister() {
        String email = SignUpTenantText.getText().toString().trim();
        String password = SignUpTenantPassword.getText().toString().trim();

        if (email.isEmpty()) {
            SignUpTenantText.setError("Enter an email address");
            SignUpTenantText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignUpTenantText.setError("Enter a valid email address");
            SignUpTenantText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            SignUpTenantPassword.setError("Enter a password");
            SignUpTenantPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            SignUpTenantPassword.setError("Minimum length of password should be 6");
            SignUpTenantPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Registration is notSuccessful", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
}
