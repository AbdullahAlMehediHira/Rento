package com.example.rento.Landlord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.rento.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandlordProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView llusername, llemail;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_profile);
        this.setTitle("Landlord");

        mAuth = FirebaseAuth.getInstance();

        drawer = findViewById(R.id.draw_landlord_layoutId);
        navigationView = findViewById(R.id.nav_view_landlord);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        View navHeaderView = navigationView.getHeaderView(0);

        llemail = navHeaderView.findViewById(R.id.LandlordHeaderEmailId);
        llusername = navHeaderView.findViewById(R.id.LandlordHeaderUsernameId);

        FirebaseDatabase.getInstance().getReference("landlord").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    String user = dataSnapshot.child("username").getValue().toString();
                    String emailid = dataSnapshot.child("email").getValue().toString();

                    llusername.setText(user);
                    llemail.setText(emailid);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandlordProfileView()).commit();
            navigationView.setCheckedItem(R.id.nav_Profile);
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
            case R.id.nav_management:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandlordManagementFragment()).commit();
                break;
            case R.id.nav_Announcement:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandlordAnnouncementFragment()).commit();
                break;
            case R.id.nav_Profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LandlordProfileView()).commit();
                break;
            case R.id.nav_alart:
                Toast.makeText(this, "PAY THE BILL", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_message:
                Toast.makeText(this, "Call me at 9:00", Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(getApplicationContext(), SignInLandlord.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
