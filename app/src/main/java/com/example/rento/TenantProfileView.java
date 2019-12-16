package com.example.rento;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
public class TenantProfileView extends Fragment {


    public TenantProfileView() {
        // Required empty public constructor
    }


    private TextView tnfn, tnun, tnphn, tngd, tnem;
    private Button tenanteditbtn;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tenant_profile_view, container, false);

        tnfn = v.findViewById(R.id.tenantFullnameView);
        tnun = v.findViewById(R.id.tenantUsernameView);
        tnem = v.findViewById(R.id.tenantEmailView);
        tngd = v.findViewById(R.id.tenantGenderView);
        tnphn = v.findViewById(R.id.tenantPhoneNo);

        tenanteditbtn = v.findViewById(R.id.tenanteditinfo);

        tenanteditbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new TenantProfileFragment()).commit();

            }
        });

        return  v;
    }

    @Override
    public void onStart() {
        databaseReference = FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String fullname = dataSnapshot.child("Fullname").getValue().toString();
                final String username = dataSnapshot.child("Username").getValue().toString();
                final String gender = dataSnapshot.child("Gender").getValue().toString();
                final String email = dataSnapshot.child("Email").getValue().toString();
                final String phone = dataSnapshot.child("PhoneNo").getValue().toString();

                tnfn.setText(fullname);
                tnun.setText(username);
                tngd.setText(gender);
                tnem.setText(email);
                tnphn.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        super.onStart();
    }

}
