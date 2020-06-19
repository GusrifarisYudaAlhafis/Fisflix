package com.gusrifarisyudaalhafis.uas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.manager.SessionManager;
import com.gusrifarisyudaalhafis.uas.model.user.Delete;
import com.gusrifarisyudaalhafis.uas.api.user.APIClient;
import com.gusrifarisyudaalhafis.uas.api.user.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    //inisialisasi
    private Toolbar toolbar;
    SessionManager sessionManager;
    String Id_User, username, email;
    AlertDialog alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //binding komponen
        sessionManager = new SessionManager(ProfileActivity.this);

        Animation small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        Animation bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation bottom_to_top2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2);

        toolbar = findViewById(R.id.toolbar_detail);
        ImageView iv_profile = findViewById(R.id.img_profile);
        TextView tv_name = findViewById(R.id.name);
        TextView tv_email = findViewById(R.id.email);
        Button btn_update = findViewById(R.id.btn_update);
        Button btn_delete = findViewById(R.id.btn_delete);

        //berikan animasi
        iv_profile.startAnimation(small_to_big);
        tv_name.startAnimation(bottom_to_top);
        tv_email.startAnimation(bottom_to_top);
        btn_update.startAnimation(bottom_to_top2);
        btn_delete.startAnimation(bottom_to_top2);

        //ambil session
        Id_User = sessionManager.getUserDetail().get(SessionManager.ID_USER);
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        //masukkan data ke komponen
        tv_name.setText(username);
        tv_email.setText(email);

        //atur gambar dengan glide
        Glide.with(getApplicationContext()).load(R.drawable.userpt).apply(new RequestOptions().override(300, 300)).into(iv_profile);

        initToolbar();

        //beri fungsi ketika tombol diklik
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
    }

    //beri title pada toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.profile);
    }

    //beri fungsi ketika tombol back pada toolbar diklik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            this.finish();
        }
        return true;
    }

    //pengecekan tombol yang diklik
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                //pindah activity
                startActivity(new Intent(ProfileActivity.this, UpdateActivity.class));
                break;
            case R.id.btn_delete:
                //berikan konfirmasi
                confirmDelete();
                break;
        }
    }

    //berikan alert konfirmasi penghapusan akun
    private void confirmDelete() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage(R.string.confirm_message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.pos_btn, (dialog, which) -> submitDelete(Id_User));
        builder.setNegativeButton(R.string.neg_btn, (dialog, which) -> builder.setCancelable(true));
        builder.show();
    }

    private void submitDelete(String Id_User) {
        //buat loading
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setMessage(R.string.loading);
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();

        //dapatkan callback
        Callback<Delete> callback = new Callback<Delete>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<Delete> call, Response<Delete> response) {
                if (response.isSuccessful()) {
                    //ambil status
                    int status = Objects.requireNonNull(response.body()).getStatus();
                    if (status > 0) {
                        //beri notif berupa toast, hapus session, dan pindah ke login
                        Toast.makeText(ProfileActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                        sessionManager.logoutSession();
                        moveToLogin();
                    }
                }
                alert.dismiss();
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<Delete> call, @NotNull Throwable t) {
                //beri notif berupa toast
                Toast.makeText(getApplicationContext(), R.string.e_delete, Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        };

        //uji coba API
        try {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Delete> call = apiInterface.deleteResponse(Id_User);
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e("Pesan", "Error: " + e.getMessage());
        }
    }

    //pindah ke login
    private void moveToLogin() {
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
        finish();
    }
}
