package com.example.rento;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LandlordProfileFragment extends Fragment {

   public LandlordProfileFragment(){

   }

    private Button editInfoButton;
    private EditText landlordfirstName, landlordlastName, landlordaddress, landlordcity, landlordzip;
    DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_landlord_profile, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReference("Landlords");

        landlordfirstName = v.findViewById(R.id.firstname);
        landlordlastName = v.findViewById(R.id.lastname);
        landlordcity = v.findViewById(R.id.city);
        landlordaddress = v.findViewById(R.id.address);
        landlordzip = v.findViewById(R.id.zipcode);

        editInfoButton = v.findViewById(R.id.editinfo);

        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    return v;
    }

    public void saveData() {
        String firstname = landlordfirstName.getText().toString().trim();
        String lastname = landlordlastName.getText().toString().trim();
        String address = landlordaddress.getText().toString().trim();
        String city = landlordcity.getText().toString().trim();
        String zip = landlordzip.getText().toString().trim();

        if(TextUtils.isEmpty(firstname)){
            Toast.makeText(getContext(), "Enter your first name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(lastname)){
            Toast.makeText(getContext(), "Enter your last name", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(address)){
            Toast.makeText(getContext(), "Enter your address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(city)){
            Toast.makeText(getContext(), "Enter your city", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(zip)){
            Toast.makeText(getContext(), "Enter your zip", Toast.LENGTH_SHORT).show();
        }
        else{
            databaseReference.push();
            Landlord landlord = new Landlord(firstname, lastname, address, city, zip);
            databaseReference.setValue(landlord);
            Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
            landlordfirstName.setText("");
            landlordlastName.setText("");
            landlordaddress.setText("");
            landlordcity.setText("");
            landlordzip.setText("");
        }
    }

}
