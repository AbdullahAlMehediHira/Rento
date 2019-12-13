package com.example.rento;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandlordProfileView extends Fragment {


    public LandlordProfileView() {
        // Required empty public constructor
    }

    private TextView llfn, llln, llad, llct, llzp;
    private Button editbtn;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_landlord_profile_view, container, false);

        llfn = v.findViewById(R.id.firstnameview);
        llln = v.findViewById(R.id.lastnameview);
        llad = v.findViewById(R.id.addressview);
        llct = v.findViewById(R.id.cityview);
        llzp = v.findViewById(R.id.zipcodeview);

        editbtn = v.findViewById(R.id.editinfo);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new LandlordProfileFragment()).commit();

            }
        });

        return  v;
    }

    @Override
    public void onStart() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Landlords");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstName").getValue().toString();
                String lastname = dataSnapshot.child("lastName").getValue().toString();
                String address = dataSnapshot.child("address").getValue().toString();
                String city = dataSnapshot.child("city").getValue().toString();
                String zip = dataSnapshot.child("zipCode").getValue().toString();

                llfn.setText(firstname);
                llln.setText(lastname);
                llad.setText(address);
                llct.setText(city);
                llzp.setText(zip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }
}
