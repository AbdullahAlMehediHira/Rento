package com.example.rento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpTenant extends AppCompatActivity {
    private EditText SignUpTenantEmail, SignUpTenantsLandlordEmail, SignUpTenantPassword, SignUpTenantFullname, SignUpTenantUsername, SignUpTenantPhone;
    private String gender = "";
    private Button SignUpTenantButton;
    private TextView SignInTenantTextView;
    private RadioButton tenantRadioMale, tenantRadioFemale;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tenant);
        this.setTitle("Tenant SIGNUP");
        mAuth = FirebaseAuth.getInstance();

        SignUpTenantEmail = findViewById(R.id.SignUpTenantEmailId);
        SignUpTenantsLandlordEmail = findViewById(R.id.SignUpTenantsLandlordEmailId);
        SignUpTenantFullname = findViewById(R.id.signUpTeanatFullnameId);
        SignUpTenantUsername = findViewById(R.id.signUpTeanatUsernameId);
        SignUpTenantPhone = findViewById(R.id.signUpTenantPhoneId);
        tenantRadioFemale = findViewById(R.id.TenantRadioFemale);
        tenantRadioMale = findViewById(R.id.TenantRadioMale);
        SignUpTenantPassword = findViewById(R.id.signUpTenantPasswordId);
        SignUpTenantButton = findViewById(R.id.SignUpTenantButtonId);
        SignInTenantTextView = findViewById(R.id.SignInTenantTextViewId);
        progressBar = findViewById(R.id.tenantProgressId);

        SignInTenantTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignInTenant.class);
                startActivity(intent);
            }
        });

        SignUpTenantButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname = SignUpTenantFullname.getText().toString().trim();
                final String phone = SignUpTenantPhone.getText().toString().trim();
                final String username = SignUpTenantUsername.getText().toString().trim();
                final String email = SignUpTenantEmail.getText().toString().trim();
                final String landlordemail = SignUpTenantsLandlordEmail.getText().toString().trim();
                String password = SignUpTenantPassword.getText().toString().trim();

                if (tenantRadioMale.isChecked()) {
                    gender = "Male";
                }
                if (tenantRadioFemale.isChecked()) {
                    gender = "Female";
                }
                if(TextUtils.isEmpty(landlordemail)){
                    SignUpTenantPassword.setError("Enter you Landlord's Email");
                    SignUpTenantPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(fullname)) {
                    Toast.makeText(getApplicationContext(), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (email.isEmpty()) {
                    SignUpTenantEmail.setError("Enter an email address");
                    SignUpTenantEmail.requestFocus();
                    return;
                }
                if (gender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    SignUpTenantPassword.setError("Minimum length of password should be 6");
                    SignUpTenantPassword.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    SignUpTenantEmail.setError("Enter a valid email address");
                    SignUpTenantEmail.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpTenant.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    TenantData tenantData = new TenantData(fullname, username, phone, email, gender, landlordemail);
                                    FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(tenantData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(SignUpTenant.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), SignInTenant.class));
                                        }
                                    });

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }


                                }

                            }
                        });


            }
        });

    }
}