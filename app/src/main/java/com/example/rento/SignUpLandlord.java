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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpLandlord extends AppCompatActivity {

    private EditText SignUpLandlordText, SignUpLandlordPassword, SignUpLandlordFullname, SignUpLandlordUsername;
    private Button SignUpLandlordButton;
    private RadioButton RadioMale, RadioFemale;
    private String gender = "", address = "";
    private TextView SignInLandlordTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_landlord);
        this.setTitle("Landlord SIGNUP");

        SignUpLandlordFullname = findViewById(R.id.signUpLandlordFullnameId);
        SignUpLandlordUsername = findViewById(R.id.signUpLandlordUsernameId);
        SignUpLandlordText = findViewById(R.id.SignUpLandlordEmailId);
        SignUpLandlordPassword = findViewById(R.id.SignUpLandlordPasswordId);
        SignUpLandlordButton = findViewById(R.id.SignUpLandlordButtonId);
        SignInLandlordTextView = findViewById(R.id.SignInLandlordTextViewId);
        RadioMale = findViewById(R.id.RadioMale);
        RadioFemale = findViewById(R.id.RadioFemale);

        progressBar = findViewById(R.id.landlordProgressId);

        databaseReference = FirebaseDatabase.getInstance().getReference("Landlord");
        mAuth = FirebaseAuth.getInstance();


        SignInLandlordTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SignInLandlordIntent = new Intent(getApplicationContext(), SignInLandlord.class);
                startActivity(SignInLandlordIntent);
            }
        });



        SignUpLandlordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullname = SignUpLandlordFullname.getText().toString().trim();
                final String username = SignUpLandlordUsername.getText().toString().trim();
                final String email = SignUpLandlordText.getText().toString().trim();
                String password = SignUpLandlordPassword.getText().toString().trim();

                if (RadioMale.isChecked()) {
                    gender = "Male";
                }
                if (RadioFemale.isChecked()) {
                    gender = "Female";
                }

                if (TextUtils.isEmpty(fullname)) {
                    Toast.makeText(getApplicationContext(), "Enter your full name", Toast.LENGTH_SHORT).show();
                    return;
                } if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter your user name", Toast.LENGTH_SHORT).show();
                    return;
                } if (email.isEmpty()) {
                    SignUpLandlordText.setError("Enter an email address");
                    SignUpLandlordText.requestFocus();
                    return;
                } if (gender.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter your gender", Toast.LENGTH_SHORT).show();
                    return;
                } if (password.length() < 6) {
                    SignUpLandlordPassword.setError("Minimum length of password should be 6");
                    SignUpLandlordPassword.requestFocus();
                    return;
                } if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    SignUpLandlordText.setError("Enter a valid email address");
                    SignUpLandlordText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpLandlord.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);

                                    if (task.isSuccessful()) {
                                        LandlordData landlordData = new LandlordData(fullname, username, email, gender, address);
                                        FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(landlordData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(SignUpLandlord.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(), SignInLandlord.class));
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

   /* @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUpLandlordButtonId:
                landlordRegister();
                break;

            case R.id.SignInLandlordTextViewId:
                Intent SignInLandlordIntent = new Intent(getApplicationContext(), SignInLandlord.class);
                startActivity(SignInLandlordIntent);
                break;
        }

    }

    private void landlordRegister() {
        final String email = SignUpLandlordText.getText().toString().trim();
        String password = SignUpLandlordPassword.getText().toString().trim();

        if (email.isEmpty()) {
            SignUpLandlordText.setError("Enter an email address");
            SignUpLandlordText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignUpLandlordText.setError("Enter a valid email address");
            SignUpLandlordText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            SignUpLandlordPassword.setError("Enter a password");
            SignUpLandlordPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            SignUpLandlordPassword.setError("Minimum length of password should be 6");
            SignUpLandlordPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    LandlordData landlordSignUp = new LandlordData(email);
                    FirebaseDatabase.getInstance().getReference("Landlords").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(landlordSignUp).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getApplicationContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SignInLandlord.class));

                        }
                    });
                }else{
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User already registered", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error: "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
*/