package com.example.rento.Tenant;

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

public class TenantProfileFragment extends Fragment {
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

        editInfotenant.setOnClickListener(new View.OnClickListener() {
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
                final String landlordemail = dataSnapshot.child("landlordEmail").getValue().toString();

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
