package com.gusrifarisyudaalhafis.uas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.gusrifarisyudaalhafis.uas.R;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        //berikan delay sebelum pindah activity
        new Handler().postDelayed(() -> SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, MainActivity.class)), 3000);

    }

    //ketika lifecycle pause
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
