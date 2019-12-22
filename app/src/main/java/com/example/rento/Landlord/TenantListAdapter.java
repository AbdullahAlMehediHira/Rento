package com.example.rento.Landlord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rento.R;
import com.example.rento.Tenant.TenantData;

import java.util.ArrayList;

public class TenantListAdapter extends RecyclerView.Adapter<TenantListAdapter.TenantViewHolder> {

    Context context;
    ArrayList<TenantData> tenantData;

    public TenantListAdapter(Context c, ArrayList<TenantData> t){
        context = c;
        tenantData = t;
    }

    @NonNull
    @Override
    public TenantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TenantViewHolder(LayoutInflater.from(context).inflate(R.layout.tenant_display_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TenantViewHolder holder, int position) {
        holder.email.setText(tenantData.get(position).getemail());
        holder.name.setText(tenantData.get(position).getfullname());
    }

    @Override
    public int getItemCount() {
        return tenantData.size();
    }

    class TenantViewHolder extends RecyclerView.ViewHolder{

        TextView name, email;
        ImageView ppic;
        public TenantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nametenant);
            email = (TextView) itemView.findViewById(R.id.emailtenant);
            ppic = (ImageView) itemView.findViewById(R.id.profilePic);
        }
    }

}
