package com.example.rento.Landlord;

import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LandlordManagementFragment extends Fragment {

    private RecyclerView mytenantlist;
    private DatabaseReference databaseReference, usersref;
    private FirebaseAuth mAuth;
    private String currentUserID;
    ArrayList<TenantData> list;
    TenantListAdapter tenantListAdapter;



    LandlordManagementFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View tenantview = inflater.inflate(R.layout.fragment_landlord_management, container, false);

        mytenantlist = (RecyclerView) tenantview.findViewById(R.id.tenant_list);
        mytenantlist.setLayoutManager(new LinearLayoutManager(getContext()));

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("tenant");
        list = new ArrayList<TenantData>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    TenantData tenant = dataSnapshot1.getValue(TenantData.class);
                    list.add(tenant);
                }
                tenantListAdapter = new TenantListAdapter(getContext(), list);
                mytenantlist.setAdapter(tenantListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });


        return tenantview;
    }
}

    /*@Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<TenantData>()
                .setQuery(databaseReference, TenantData.class).build();

        FirebaseRecyclerAdapter<TenantData, TenantViewHolder> adapter = new FirebaseRecyclerAdapter<TenantData, TenantViewHolder>() {
            @Override
            protected void onBindViewHolder(@NonNull TenantViewHolder tenantViewHolder, int position, @NonNull TenantData tenantData)
            {
                String userId = getRef(position).getKey();

            }

            @NonNull
            @Override
            public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tenant_display_layout, viewGroup, false);
                TenantViewHolder viewHolder = new TenantViewHolder(view);
                return  viewHolder;
            }
        };

    }




    public  static class TenantViewHolder extends RecyclerView.ViewHolder
    {
        TextView username, userstatus;
        CircleImageView profileImage;
        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tenant_profile_name);
            userstatus = itemView.findViewById(R.id.tenant_status);
            profileImage = itemView.findViewById(R.id.tenantimageView);


        }
    }
}
*/