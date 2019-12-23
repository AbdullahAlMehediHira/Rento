package com.example.rento.Tenant;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rento.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
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

public class TenantProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tnusername, tnemail;
    FirebaseAuth mAuth;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TenantLocation mTenantLocation=null;
    Boolean   mLocationPermissionGranted=false;
    String TAG="Tenant profile";
    FirebaseFirestore mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);
        this.setTitle("Tenant");

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        mDb= FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getUserDetails();

        drawer = findViewById(R.id.draw_tenant_layoutId);
        toolbar = findViewById(R.id.tenanttoolbarId);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view_tenant);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        View navHeaderView = navigationView.getHeaderView(0);

        tnemail = navHeaderView.findViewById(R.id.TenantHeaderEmailId);
        tnusername = navHeaderView.findViewById(R.id.TenantHeaderUsernameId);

        FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    String user = dataSnapshot.child("username").getValue().toString();
                    String email = dataSnapshot.child("email").getValue().toString();

                    tnusername.setText(user);
                    tnemail.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantProfileView()).commit();
            navigationView.setCheckedItem(R.id.nav_profile_tenant);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_announcement_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantAnnouncementFragment()).commit();
                break;
            case R.id.nav_payment_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantPaymentFragment()).commit();
                break;
            case R.id.nav_profile_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantProfileView()).commit();
                break;
            case R.id.nav_landlord_info_tenant:
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantLandlordinfoFragment()).commit();
                break;
            case R.id.nav_emergency_tenant:
                Toast.makeText(this, "Fire! Fire! Fire!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_map_View:
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantMapView()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.signout_menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.SignOutMenuId) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(), SignInTenant.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    //step 1
    private void getUserDetails(){
        if(mTenantLocation==null){
            mTenantLocation = new TenantLocation();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("tenant").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String fullname = dataSnapshot.child("fullname").getValue().toString();
                    final String username = dataSnapshot.child("username").getValue().toString();
                    final String gender = dataSnapshot.child("gender").getValue().toString();
                    final String email = dataSnapshot.child("email").getValue().toString();
                    final String phone = dataSnapshot.child("phoneNo").getValue().toString();
                    final String llname = dataSnapshot.child("landlordName").getValue().toString();

                    TenantData tenantData;
                    tenantData = new TenantData(fullname, username, phone, email, gender, llname);
                    mTenantLocation.set_tenant_data(tenantData);
                    mTenantLocation.set_time_stamp(null);

                    getLastLocation();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//            DocumentReference userRef = mDb.collection("TenantData")
//                    .document(FirebaseAuth.getInstance().getUid());
//            userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                    Log.d("Inside on complete","habahaba");
//                    if(task.isSuccessful()){
//                        Log.d(TAG,"onComplete: successfully got the user details.");
//                        TenantData tenantData = task.getResult().toObject(TenantData.class);
//                        Log.d(TAG,""+ task.toString());
//                        mTenantLocation.set_tenant_data(tenantData);
//                        ((TenantDataClient)getApplicationContext()).setTenantData(tenantData);
//                        getLastLocation();
//                    }
//                }
//            });
        }
        else {
            getLastLocation();
        }
    }

    //step 2
    private void getLastLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            ActivityCompat.requestPermissions(this, new String[]
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

                    mTenantLocation.set_geopoint(geopoint);
                    saveUserLocation();
                    return;
                }
            }
        });

    }

    //step 3
    private void saveUserLocation(){
        if(mTenantLocation!=null){

            DocumentReference locationRef = mDb.
                    collection("TenantLocation")
                    .document(FirebaseAuth.getInstance().getUid());
            locationRef.set(mTenantLocation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Log.d(TAG, "onComplete: \n Inserted Userlocation into database."+
                                "\n Longitude:"+mTenantLocation.get_geopoint().getLatitude()+
                                "\nLongitude: "+mTenantLocation.get_geopoint().getLongitude());
                    }
                }
            });
        }
    }
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getUserDetails();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

}