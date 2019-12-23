package com.example.rento.Landlord;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rento.Landlord.LandlordData;
import com.example.rento.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandlordProfileFragment extends Fragment {

    public LandlordProfileFragment() {

    }

    private Button editInfoButton;
    private EditText landlordusername, landlordaddress;
    DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landlord_profile, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("landlord");

        landlordusername = v.findViewById(R.id.username);
        landlordaddress = v.findViewById(R.id.address);


        editInfoButton = v.findViewById(R.id.makechange);

        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


        return v;
    }

    public void saveData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String fullname = dataSnapshot.child("fullname").getValue().toString();
                final String gender = dataSnapshot.child("gender").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();

                final String username = landlordusername.getText().toString().trim();
                final String address = landlordaddress.getText().toString().trim();
                final String Uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getContext(), "Enter your address", Toast.LENGTH_SHORT).show();
                }


                LandlordData landlordData = new LandlordData(fullname, username, email, gender, address);
                FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(landlordData).addOnCompleteListener(new OnCompleteListener<Void>() {
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

