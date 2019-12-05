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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpLandlord extends AppCompatActivity implements OnClickListener {

    private EditText SignUpLandlordText, SignUpLandlordPassword;
    private Button SignUpLandlordButton;
    private TextView SignInLandlordTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_landlord);
        this.setTitle("Landlord SIGNUP");
        mAuth = FirebaseAuth.getInstance();

        SignUpLandlordText = findViewById(R.id.SignUpLandlordTextId);
        SignUpLandlordPassword = findViewById(R.id.SignUpLandlordPasswordId);
        SignUpLandlordButton = findViewById(R.id.SignUpLandlordButtonId);
        SignInLandlordTextView = findViewById(R.id.SignInLandlordTextViewId);
        progressBar = findViewById(R.id.landlordProgressId);

        SignUpLandlordButton.setOnClickListener(this);
        SignInLandlordTextView.setOnClickListener(this);
    }

    @Override
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
        String email = SignUpLandlordText.getText().toString().trim();
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
                    Toast.makeText(getApplicationContext(), "Registration is Successful", Toast.LENGTH_SHORT).show();

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
