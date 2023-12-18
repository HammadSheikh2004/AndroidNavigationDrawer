package com.example.navigation_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout nav_drawer;
    NavigationView nav_view;
    Toolbar app_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_drawer = findViewById(R.id.nav_drawer);
        nav_view = findViewById(R.id.nav_view);
        app_bar = findViewById(R.id.app_bar);
        setSupportActionBar(app_bar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, nav_drawer, app_bar,
                R.string.OpenDrawer, R.string.CloseDrawer);
        nav_drawer.addDrawerListener(toggle);
        toggle.syncState();

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    loadFragment(new HomeFragment());
                }
                if (id == R.id.about) {
                    loadFragment(new AboutFragment());
                }
                if (id == R.id.contact) {
                    loadFragment(new ContactFragment());
                }
                nav_drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        app_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
                    nav_drawer.closeDrawer(GravityCompat.START);
                } else {
                    nav_drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        loadFragment(new HomeFragment());
    }
    @Override
    public void onBackPressed() {
        if (nav_drawer.isDrawerOpen(GravityCompat.START)) {
            nav_drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (currentFragment instanceof HomeFragment) {
                finish();
            } else if (currentFragment instanceof AboutFragment) {
                loadFragment(new HomeFragment());
            } else if (currentFragment instanceof ContactFragment) {
                loadFragment(new AboutFragment());
            } else {
                super.onBackPressed();
            }
        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
