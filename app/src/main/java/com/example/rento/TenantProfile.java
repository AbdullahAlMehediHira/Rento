package com.example.rento;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TenantProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tnusername, tnemail;
    FirebaseAuth mAuth;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_profile);
        this.setTitle("Tenant");

        mAuth = FirebaseAuth.getInstance();

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
                        String user = dataSnapshot.child("Username").getValue().toString();
                        String email = dataSnapshot.child("Email").getValue().toString();

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
}