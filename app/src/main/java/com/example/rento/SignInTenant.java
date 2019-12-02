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

public class SignInTenant extends AppCompatActivity implements OnClickListener {

    private EditText SignInTenantText, SignInTenantPassword;
    private Button SignInTenantButton;
    private TextView SignUpTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_tenant);
        this.setTitle("Tenant SIGNIN");

        mAuth = FirebaseAuth.getInstance();
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
                tenantlogin();
                break;

            case R.id.SignUpTenantTextViewId:
                Intent SignUpTenantIntent = new Intent(getApplicationContext(), SignUpTenant.class);
                startActivity(SignUpTenantIntent);
                break;

        }

    }

    private void tenantlogin() {
        String email = SignInTenantText.getText().toString().trim();
        String password = SignInTenantPassword.getText().toString().trim();

        if (email.isEmpty()) {
            SignInTenantText.setError("Enter an email address");
            SignInTenantText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignInTenantText.setError("Enter a valid email address");
            SignInTenantText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            SignInTenantPassword.setError("Enter a password");
            SignInTenantPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            SignInTenantPassword.setError("Minimum length of password should be 6");
            SignInTenantPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent tenantclassintent = new Intent(getApplicationContext(), TenantProfile.class);
                    tenantclassintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(tenantclassintent);
                }else{
                    Toast.makeText(getApplicationContext(), "login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
