package com.example.rento.Landlord;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rento.R;
import com.example.rento.Tenant.TenantData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LandlordManagementFragment extends Fragment {

    private RecyclerView mytenantlist;
    private DatabaseReference tenantdatabaseReference, landlorddatabaseReference;
    private FirebaseAuth mAuth;
    private String currentUserID;
    ArrayList<TenantData> list;
    TenantListAdapter tenantListAdapter;
    LandlordData landlordData;
    public String llusername;


    LandlordManagementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View tenantview = inflater.inflate(R.layout.fragment_landlord_management, container, false);

        landlorddatabaseReference = FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        landlordData = new LandlordData();

        mytenantlist = (RecyclerView) tenantview.findViewById(R.id.tenant_list);
        mytenantlist.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        list = new ArrayList<TenantData>();
        return tenantview;
    }

     public void onStart() {
        landlorddatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String llusername = dataSnapshot.child("username").getValue().toString();
                Query query = FirebaseDatabase.getInstance().getReference("tenant")
                        .orderByChild("landlordName")
                        .equalTo(llusername);
                Log.d("habajaba", " " + llusername);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                            TenantData tenant = dataSnapshot1.getValue(TenantData.class);
                            list.add(tenant);
                        }
                        tenantListAdapter = new TenantListAdapter(getContext(), list);
                        mytenantlist.setAdapter(tenantListAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();
    }
}
