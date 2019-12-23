package com.example.rento.Tenant;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rento.R;
import com.example.rento.Tenant.TenantData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TenantPaymentFragmentJanuary extends Fragment {


    public TenantPaymentFragmentJanuary() {
        // Required empty public constructor
    }

    FirebaseFirestore mdb;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tenant_payment_january, container, false);

        button = v.findViewById(R.id.JanuaryPaymentbtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        return v;
    }

    private void saveData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String fullname = dataSnapshot.child("fullname").getValue().toString();
                final String username = dataSnapshot.child("username").getValue().toString();
                final String gender = dataSnapshot.child("gender").getValue().toString();
                final String email = dataSnapshot.child("email").getValue().toString();
                final String phone = dataSnapshot.child("phoneNo").getValue().toString();
                final String llname = dataSnapshot.child(("landlordName")).getValue().toString();

                final TenantData tenantData = new TenantData(fullname, username, phone, email, gender, llname);
                FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(tenantData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        TenantPaymentRecords tenantPaymentRecords = new TenantPaymentRecords(tenantData, "January", true);
                        mdb = FirebaseFirestore.getInstance();
                        DocumentReference TenRef = mdb.collection("Payment Records").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        TenRef.set(tenantPaymentRecords).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Your Payment has been recorded", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
}



