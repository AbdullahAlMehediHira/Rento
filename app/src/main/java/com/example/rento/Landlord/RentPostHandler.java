package com.example.rento.Landlord;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rento.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import static com.example.rento.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class RentPostHandler extends Fragment {
    TextView SquareFeet,Cost,NumOfRoom;
    Button Post;
    private FusedLocationProviderClient fusedLocationProviderClient;
    RentPost rentPost;
    private String temp;
    private String TAG="Landord";
    FirebaseFirestore mDb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.landlord_rent_post_hadler, container, false);
        SquareFeet=v.findViewById(R.id.Square_Feet);
        Cost = v.findViewById(R.id.Cost);
        NumOfRoom =v.findViewById(R.id.Num_Of_Room);
        mDb=FirebaseFirestore.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        Post=v.findViewById(R.id.LandlordRentPostButton);
        Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLandlordDetails();
                Toast.makeText(getContext(),"Post Added Successfully",Toast.LENGTH_SHORT).show();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new LandlordProfileView()).commit();
            }
        });



    return v;
    }
    private void getLandlordLocation(){

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;

        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if(task.isSuccessful()){
                    Location location = task.getResult();
                    Log.d(TAG,"Location successfully retrived \n latitude: "+location.getLongitude());

                    GeoPoint geopoint = new GeoPoint(location.getLatitude(),location.getLongitude());
                    Log.d(TAG,"onComplete: latitude: "+geopoint.getLatitude());
                    Log.d(TAG,"onComplete: longitude: "+geopoint.getLongitude());
                    rentPost.setgeopoint(geopoint);
                    saveRentPostLocation();
                }
            }
        });
    }
    private void getLandlordDetails(){
        if(rentPost==null){

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String fullname = dataSnapshot.child("fullname").getValue().toString();
                    String username = dataSnapshot.child("username").getValue().toString();
                    String gender = dataSnapshot.child("gender").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();
                    String address = dataSnapshot.child("address").getValue().toString();


                    LandlordData landlordData= new LandlordData( fullname, username, email,  gender, address);
                    String squareFeet= SquareFeet.getText().toString();
                    String cost=Cost.getText().toString();
                    String numberOfRooms =NumOfRoom.getText().toString();

                    rentPost = new RentPost( null,  cost,  numberOfRooms,  null,  landlordData,  null, squareFeet);
//                    rentPost.setLandlordData(landlordData);
//                    rentPost.setCost(Cost.getText().toString());
//                    rentPost.setSquareFeet(SquareFeet.getText().toString());
//                    rentPost.setNumberOfRooms(NumOfRoom.getText().toString());

                    getLandlordLocation();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            getLandlordLocation();
        }
    }
    private void saveRentPostLocation(){
        if(rentPost!=null){

            DocumentReference locationRef = mDb.
                    collection("RentPosts")
                    .document(FirebaseAuth.getInstance().getUid());
            locationRef.set(rentPost).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Log.d(TAG, "onComplete: \n Inserted Userlocation into database."+
                                "\n Longitude:"+rentPost.getgeopoint().getLatitude()+
                                "\nLongitude: "+rentPost.getgeopoint().getLongitude());
                    }
                }
            });
        }
    }


}
