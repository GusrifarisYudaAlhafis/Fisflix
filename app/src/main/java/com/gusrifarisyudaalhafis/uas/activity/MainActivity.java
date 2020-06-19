package com.gusrifarisyudaalhafis.uas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.manager.SessionManager;
import com.gusrifarisyudaalhafis.uas.fragment.movies.MoviesFragment;
import com.gusrifarisyudaalhafis.uas.fragment.tv.TVFragment;

public class MainActivity extends AppCompatActivity {
    //inisialisasi
    Fragment fragment;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //cek session apakah sudah login
        sessionManager = new SessionManager(MainActivity.this);
        //jika belum, pindah ke login
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        //binding komponen
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            //pengecekan item yang diklik
            switch (item.getItemId()) {
                case R.id.nav_bottom_1:
                    //jalankan fragment movie
                    fragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.nav_bottom_2:
                    //jalankan fragment TV
                    fragment = new TVFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewpager, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }

            return false;
        });

        //ketika masuk main activity, default select id ke movie fragment
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.nav_bottom_1);
        }
    }

    //pindah ke login
    private void moveToLogin() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
    }

    //tampilkan menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //pengecekan item yang diklik
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                //pindah activity
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
            case R.id.action_language:
                //pergi ke pengaturan bahasa pada sistem
                Intent language = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(language);
                break;
            case R.id.action_about:
                //pindah activity
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.action_logout:
                //hapus session dan pindah ke login
                sessionManager.logoutSession();
                moveToLogin();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}