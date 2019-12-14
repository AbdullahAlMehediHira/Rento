package com.example.rento;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordProfileView extends Fragment {


    private TextView llfn, llun, llad, llgd, llem;
    private Button editbtn;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landlord_profile_view, container, false);

        llfn = v.findViewById(R.id.FullnameView);
        llun = v.findViewById(R.id.UsernameView);
        llem = v.findViewById(R.id.EmailView);
        llgd = v.findViewById(R.id.GenderView);
        llad = v.findViewById(R.id.AddressView);

        editbtn = v.findViewById(R.id.editinfo);

     /*   editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new LandlordProfileFragment()).commit();

            }
        });*/

        return  v;
    }

    @Override
    public void onStart() {
        databaseReference = FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fullname = dataSnapshot.child("Fullname").getValue().toString();
                String username = dataSnapshot.child("UserName").getValue().toString();
                String gender = dataSnapshot.child("Gender").getValue().toString();
                String email = dataSnapshot.child("Email").getValue().toString();
                String address = dataSnapshot.child("Address").getValue().toString();

                llfn.setText(fullname);
                llun.setText(username);
                llgd.setText(gender);
                llem.setText(email);
                llad.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
}
