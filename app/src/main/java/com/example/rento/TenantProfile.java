package com.example.rento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class TenantProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantProfileFragment()).commit();
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
                getSupportFragmentManager().beginTransaction().replace(R.id.tenant_fragment_container, new TenantProfileFragment()).commit();
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
}