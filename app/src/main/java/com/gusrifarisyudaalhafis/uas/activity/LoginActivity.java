package com.gusrifarisyudaalhafis.uas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gusrifarisyudaalhafis.uas.R;
import com.gusrifarisyudaalhafis.uas.manager.SessionManager;
import com.gusrifarisyudaalhafis.uas.model.user.Data;
import com.gusrifarisyudaalhafis.uas.model.user.Login;
import com.gusrifarisyudaalhafis.uas.api.user.APIClient;
import com.gusrifarisyudaalhafis.uas.api.user.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //inisialisasi
    private TextInputEditText et_email, et_password;
    AlertDialog alert;
    SessionManager sessionManager;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //binding komponen yang digunakan
        Animation small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        Animation bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation bottom_to_top2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2);

        TextView tv_login = findViewById(R.id.tv_login);
        Button sign_in = findViewById(R.id.btn_signIn);
        Button register = findViewById(R.id.btn_register);
        TextInputLayout til_email = findViewById(R.id.til_email);
        TextInputLayout til_password = findViewById(R.id.til_password);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        //berikan animasi
        tv_login.startAnimation(small_to_big);
        sign_in.startAnimation(bottom_to_top2);
        register.startAnimation(bottom_to_top2);
        til_email.startAnimation(bottom_to_top);
        til_password.startAnimation(bottom_to_top);

        //beri fungsi ketika tombol diklik
        sign_in.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    public void submitLogin(String email, String pass) {
        //buat loading
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(R.string.loading);
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();

        //dapatkan callback
        Callback<Login> callback = new Callback<Login>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()) {
                    //ambil status
                    int status = Objects.requireNonNull(response.body()).getStatus();
                    if (status > 0) {
                        //ambil data, simpan session, beri notif berupa toast, pindah activity
                        sessionManager = new SessionManager(LoginActivity.this);
                        Data data = response.body().getData();
                        sessionManager.createLoginSession(data);
                        Toast.makeText(LoginActivity.this, R.string.welcome, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                }
                alert.dismiss();
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<Login> call, @NotNull Throwable t) {
                //beri notif berupa toast
                Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        };

        //uji coba API
        try {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Login> call = apiInterface.loginResponse(email, pass);
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e("Pesan", "Error: " + e.getMessage());
        }
    }

    //pengecekan tombol yang diklik
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                case R.id.btn_register:
                    //pindah activity
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                    break;
                case R.id.btn_signIn:
                    //ambil data pada komponen
                    String email = Objects.requireNonNull(et_email.getText()).toString();
                    String pass = Objects.requireNonNull(et_password.getText()).toString();
                    //lakukan pengecekan persyaratan
                    if (email.isEmpty()) {
                        //tampilkan alert
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.n_email);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                        builder.show();
                    } else if (pass.isEmpty()) {
                        //tampilkan alert
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.n_pass);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                        builder.show();
                    } else if (!email.trim().matches(emailPattern)) {
                        //tampilkan alert
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.i_email);
                        builder.setCancelable(false);
                        builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                        builder.show();
                    } else  {
                        //persyaratan terpenuhi, lanjut ke pengiriman data
                        submitLogin(email, pass);
                    }
                    break;
        }
    }
}