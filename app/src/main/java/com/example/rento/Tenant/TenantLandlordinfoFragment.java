package com.example.rento.Tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rento.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TenantLandlordinfoFragment extends Fragment {
    private TextView landlordname, landlordaddress, landlordemail;
    DatabaseReference tenantdatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tenant_landlordinfo, container, false);
        tenantdatabaseReference = FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        landlordname = v.findViewById(R.id.llname);
        landlordaddress = v.findViewById(R.id.lladdress);
        landlordemail = v.findViewById(R.id.llemail);

        return v;
    }
}
