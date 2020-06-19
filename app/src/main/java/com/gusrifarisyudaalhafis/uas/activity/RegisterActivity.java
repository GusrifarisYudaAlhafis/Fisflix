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
import com.gusrifarisyudaalhafis.uas.model.user.Register;
import com.gusrifarisyudaalhafis.uas.api.user.APIClient;
import com.gusrifarisyudaalhafis.uas.api.user.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    //inisialisasi
    private TextInputEditText et_username, et_email, et_password, et_passwordConfirm;
    AlertDialog alert;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //binding komponen yang digunakan
        Animation small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        Animation bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation bottom_to_top2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2);

        TextView tv_register = findViewById(R.id.tv_register);
        Button sign_up = findViewById(R.id.btn_signUp);
        Button login = findViewById(R.id.btn_login);
        TextInputLayout til_username = findViewById(R.id.til_username);
        TextInputLayout til_email = findViewById(R.id.til_email);
        TextInputLayout til_password = findViewById(R.id.til_password);
        TextInputLayout til_passwordConfirm = findViewById(R.id.til_passwordConfirm);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_passwordConfirm = findViewById(R.id.et_passwordConfirm);

        //berikan animasi
        tv_register.startAnimation(small_to_big);
        sign_up.startAnimation(bottom_to_top2);
        login.startAnimation(bottom_to_top2);
        til_username.startAnimation(bottom_to_top);
        til_email.startAnimation(bottom_to_top);
        til_password.startAnimation(bottom_to_top);
        til_passwordConfirm.startAnimation(bottom_to_top);

        //beri fungsi ketika tombol diklik
        sign_up.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    public void submitRegister(String username, String email, String pass) {
        //buat loading
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(R.string.loading);
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();

        //dapatkan callback
        Callback<Register> callback = new Callback<Register>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<Register> call, Response<Register> response) {
                if (response.isSuccessful()) {
                    //ambil status
                    int status = Objects.requireNonNull(response.body()).getStatus();
                    if (status > 0) {
                        //beri notif berupa toast dan pindah activity
                        Toast.makeText(RegisterActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                }
                alert.dismiss();
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<Register> call, @NotNull Throwable t) {
                //beri notif berupa toast
                Toast.makeText(getApplicationContext(), R.string.e_email, Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        };

        //uji coba API
        try {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Register> call = apiInterface.registerResponse(username, email, pass);
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e("Pesan", "Error: " + e.getMessage());
        }
    }

    //pengecekan tombol yang diklik
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //pindah activity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_signUp:
                //ambil data pada komponen
                String username = Objects.requireNonNull(et_username.getText()).toString();
                String email = Objects.requireNonNull(et_email.getText()).toString();
                String pass = Objects.requireNonNull(et_password.getText()).toString();
                String passConfirm = Objects.requireNonNull(et_passwordConfirm.getText()).toString();
                //lakukan pengecekan persyaratan
                if (username.isEmpty()) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.n_user);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (email.isEmpty()) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.n_email);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (pass.isEmpty() || passConfirm.isEmpty()) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.n_pass);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (!email.trim().matches(emailPattern)) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.i_email);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (pass.length() < 6) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.e_pass_6);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (!pass.equals(passConfirm)) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage(R.string.e_pass);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else {
                    //persyaratan terpenuhi, lanjut ke pengiriman data
                    submitRegister(username, email, pass);
                }
                break;
        }
    }
}
