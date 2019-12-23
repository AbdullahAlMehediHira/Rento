package com.example.rento.Tenant;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rento.R;
import com.example.rento.Tenant.TenantPaymentAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TenantPaymentFragment extends Fragment {
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    TenantPaymentAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tenant_payment, container, false);

        expandableListView = v.findViewById(R.id.expandablelistview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new TenantPaymentAdapter(getContext(), listGroup, listItem);
        expandableListView.setAdapter(adapter);
        initListData();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) adapter.getChild(groupPosition, childPosition);
                switch (selected){
                    case "January":
                        FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                        fragmentTransaction1.replace(R.id.tenant_fragment_container, new TenantPaymentFragmentJanuary()).commit();
                }
                return false;
            }
        });

        return v;
    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));

        String[] array;
        List<String> list = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
        for(String item : array){
            list.add(item);
        }

        listItem.put(listGroup.get(0),list);
        adapter.notifyDataSetChanged();
    }
}
