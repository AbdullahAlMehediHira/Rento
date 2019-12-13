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

public class LandlordProfile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_profile);

        mAuth = FirebaseAuth.getInstance();

        drawer = findViewById(R.id.draw_landlord_layoutId);
        navigationView = findViewById(R.id.nav_view_landlord);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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

        if(item.getItemId() == R.id.SignOutMenuId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent = new Intent(getApplicationContext(), SignInLandlord.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
