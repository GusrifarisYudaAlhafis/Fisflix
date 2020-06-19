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
import com.gusrifarisyudaalhafis.uas.model.user.Update;
import com.gusrifarisyudaalhafis.uas.api.user.APIClient;
import com.gusrifarisyudaalhafis.uas.api.user.APIInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    //inisialisasi
    private TextInputEditText et_username, et_email;
    AlertDialog alert;
    SessionManager sessionManager;
    String Id_User, username, email;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        //binding komponen yang digunakan
        sessionManager = new SessionManager(UpdateActivity.this);

        Animation small_to_big = AnimationUtils.loadAnimation(this, R.anim.small_to_big);
        Animation bottom_to_top = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top);
        Animation bottom_to_top2 = AnimationUtils.loadAnimation(this, R.anim.bottom_to_top2);

        TextView tv_update = findViewById(R.id.tv_update);
        Button btn_back = findViewById(R.id.btn_back);
        Button btn_update = findViewById(R.id.btn_update);
        TextInputLayout til_username = findViewById(R.id.til_username);
        TextInputLayout til_email = findViewById(R.id.til_email);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);

        //berikan animasi
        tv_update.startAnimation(small_to_big);
        btn_back.startAnimation(bottom_to_top2);
        btn_update.startAnimation(bottom_to_top2);
        til_username.startAnimation(bottom_to_top);
        til_email.startAnimation(bottom_to_top);

        //ambil session
        Id_User = sessionManager.getUserDetail().get(SessionManager.ID_USER);
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        //masukkan data ke komponen
        et_username.setText(username);
        et_email.setText(email);

        //beri fungsi ketika tombol diklik
        btn_update.setOnClickListener(this);
        btn_back.setOnClickListener(this);
    }

    public void submitUpdate(String Id_User, String username, String email) {
        //buat loading
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setMessage(R.string.loading);
        builder.setCancelable(false);
        alert = builder.create();
        alert.show();

        //dapatkan callback
        Callback<Update> callback = new Callback<Update>() {
            //ketika berhasil
            @Override
            public void onResponse(@NotNull Call<Update> call, Response<Update> response) {
                if (response.isSuccessful()) {
                    //ambil status
                    int status = Objects.requireNonNull(response.body()).getStatus();
                    if (status > 0) {
                        //ambil data, simpan session, beri notif berupa toast, pindah activity
                        Data data = response.body().getData();
                        sessionManager.createLoginSession(data);
                        Toast.makeText(UpdateActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateActivity.this, ProfileActivity.class));
                        finish();
                    }
                }
                alert.dismiss();
            }

            //ketika gagal
            @Override
            public void onFailure(@NotNull Call<Update> call, @NotNull Throwable t) {
                //beri notif berupa toast
                Toast.makeText(getApplicationContext(), R.string.e_update, Toast.LENGTH_SHORT).show();
                alert.dismiss();
            }
        };

        //uji coba API
        try {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Update> call = apiInterface.updateResponse(Id_User, username, email);
            call.enqueue(callback);
        } catch (Exception e) {
            Log.e("Pesan", "Error: " + e.getMessage());
        }
    }

    //pengecekan tombol yang diklik
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                //pindah activity
                startActivity(new Intent(UpdateActivity.this, ProfileActivity.class));
                finish();
                break;
            case R.id.btn_update:
                //ambil data pada komponen
                String username = Objects.requireNonNull(et_username.getText()).toString();
                String email = Objects.requireNonNull(et_email.getText()).toString();
                //lakukan pengecekan persyaratan
                if (username.isEmpty()) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage(R.string.n_user);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (email.isEmpty()) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage(R.string.n_email);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else if (!email.trim().matches(emailPattern)) {
                    //tampilkan alert
                    final AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setMessage(R.string.i_email);
                    builder.setCancelable(false);
                    builder.setPositiveButton(R.string.ok, (dialog, which) -> builder.setCancelable(true));
                    builder.show();
                } else {
                    //persyaratan terpenuhi, lanjut ke pengiriman data
                    submitUpdate(Id_User, username, email);
                }
                break;
        }
    }
}
