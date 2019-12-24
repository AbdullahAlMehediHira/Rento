package com.example.rento.Tenant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rento.Landlord.SignUpLandlord;
import com.example.rento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class SignUpTenant extends AppCompatActivity {
    private EditText SignUpTenantEmail, SignUpTenantsLandlordEmail, SignUpTenantPassword, SignUpTenantFullname, SignUpTenantUsername, SignUpTenantPhone;
    private String gender = "";
    private Button SignUpTenantButton;
    private TextView SignInTenantTextView;
    private RadioButton tenantRadioMale, tenantRadioFemale;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_tenant);
        this.setTitle("Tenant SIGNUP");
        mAuth = FirebaseAuth.getInstance();
        mDb=FirebaseFirestore.getInstance();

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
                final String landlordname = SignUpTenantsLandlordEmail.getText().toString().trim();
                String password = SignUpTenantPassword.getText().toString().trim();

                if (tenantRadioMale.isChecked()) {
                    gender = "Male";
                }
                if (tenantRadioFemale.isChecked()) {
                    gender = "Female";
                }
                if(TextUtils.isEmpty(landlordname)){
                    Toast.makeText(getApplicationContext(), "Enter your Landlord's Name", Toast.LENGTH_SHORT).show();
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
                                    final TenantData tenantData = new TenantData(fullname, username, phone, email, gender, landlordname);
                                    FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(tenantData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            FirebaseFirestoreSettings settings =  new FirebaseFirestoreSettings.Builder()
                                                    .setTimestampsInSnapshotsEnabled(true)
                                                    .build();
                                            mDb.setFirestoreSettings(settings);
                                            DocumentReference newUserRef = mDb
                                                    .collection("TenantData")
                                                    .document(FirebaseAuth.getInstance().getUid());
                                            newUserRef.set(tenantData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(SignUpTenant.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(), SignInTenant.class));
                                                }
                                            });

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

    public static class TenantProfileFragment extends Fragment {
        public TenantProfileFragment() {

        }

        DatabaseReference databaseReference;
        private Button editInfotenant;
        private EditText tenantusername, tenantphone;

        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_tenant_profile, container, false);

            databaseReference = FirebaseDatabase.getInstance().getReference("tenant");

            tenantusername = view.findViewById(R.id.tenantusernameid);
            tenantphone = view.findViewById(R.id.tenantphoneid);


            editInfotenant = view.findViewById(R.id.tenantmakechange);

            editInfotenant.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveData();
                }
            });
            return view;
        }

        private void saveData() {
            databaseReference = FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String fullname = dataSnapshot.child("fullname").getValue().toString();
                    final String gender = dataSnapshot.child("gender").getValue().toString();
                    final String email = dataSnapshot.child("email").getValue().toString();
                    final String landlordemail = dataSnapshot.child("landlordName").getValue().toString();

                    final String tnusername = tenantusername.getText().toString().trim();
                    final String tnphoneNo = tenantphone.getText().toString().trim();

                    if (TextUtils.isEmpty(tnusername)) {
                        Toast.makeText(getContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(tnphoneNo)) {
                        Toast.makeText(getContext(), "Enter your address", Toast.LENGTH_SHORT).show();
                    }


                    TenantData tenantData = new TenantData(fullname, tnusername, tnphoneNo, email, gender, landlordemail);
                    FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(tenantData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }
}