package com.example.rento.Landlord;

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

import com.example.rento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInLandlord extends AppCompatActivity implements OnClickListener {

    private EditText SignInLandlordText, SignInLandlordPassword;
    private Button SignInLandlordButton;
    private TextView SignUpLandlordTextView;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_landlord);
        this.setTitle("Landlord SIGNIN");

        mAuth = FirebaseAuth.getInstance();

        SignInLandlordText = findViewById(R.id.SignInLandlordTextId);
        SignInLandlordPassword = findViewById(R.id.SignInLandlordPasswordId);
        SignInLandlordButton = findViewById(R.id.SignInLandlordButtonId);
        SignUpLandlordTextView = findViewById(R.id.SignUpLandlordTextViewId);
        progressBar = findViewById(R.id.landlordProgressId);

        SignInLandlordButton.setOnClickListener(this);
        SignUpLandlordTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SignInLandlordButtonId:
                landlordLogin();
                break;

            case R.id.SignUpLandlordTextViewId:
                Intent SignUpLandlordIntent = new Intent(getApplicationContext(), SignUpLandlord.class);
                startActivity(SignUpLandlordIntent);
                break;
        }


    }

    private void landlordLogin() {
        String email = SignInLandlordText.getText().toString().trim();
        String password = SignInLandlordPassword.getText().toString().trim();

        if (email.isEmpty()) {
            SignInLandlordText.setError("Enter an email address");
            SignInLandlordText.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SignInLandlordText.setError("Enter a valid email address");
            SignInLandlordText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            SignInLandlordPassword.setError("Enter a password");
            SignInLandlordPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            SignInLandlordPassword.setError("Minimum length of password should be 6");
            SignInLandlordPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Intent landlordclassintent = new Intent(getApplicationContext(), LandlordProfile.class);
                    landlordclassintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(landlordclassintent);
                }else{
                    Toast.makeText(getApplicationContext(), "login unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
