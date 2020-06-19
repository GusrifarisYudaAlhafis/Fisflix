package com.gusrifarisyudaalhafis.uas.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gusrifarisyudaalhafis.uas.R;

public class AboutActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //inisialisasi dan binding komponen yang digunakan
        Animation small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        Animation bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation bottom_to_top2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2);

        toolbar = findViewById(R.id.toolbar_detail);
        ImageView iv_profile = findViewById(R.id.img_about);
        TextView tv_name = findViewById(R.id.name);
        TextView tv_nim = findViewById(R.id.nim);
        TextView tv_class = findViewById(R.id.room);

        //berikan animasi
        iv_profile.startAnimation(small_to_big);
        tv_name.startAnimation(bottom_to_top);
        tv_nim.startAnimation(bottom_to_top2);
        tv_class.startAnimation(bottom_to_top2);

        //atur gambar dengan glide
        Glide.with(getApplicationContext()).load(R.drawable.profile).apply(new RequestOptions().override(300, 300)).into(iv_profile);

        initToolbar();
    }

    //beri title pada toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.about);
    }

    //beri fungsi ketika tombol back pada toolbar diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            this.finish();
        }
        return true;
    }
}
