package com.gusrifarisyudaalhafis.uas.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.gusrifarisyudaalhafis.uas.model.user.Data;

import java.util.HashMap;

public class SessionManager {
    //inisialisasi
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //buat constant
    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String ID_USER = "Id_User";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

    //constructor dengan context
    @SuppressLint("CommitPrefEdits")
    public SessionManager (Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    //buat session login
    public void createLoginSession(Data user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(ID_USER, user.getIdUser());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
    }

    //simpan data yang didapat
    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(ID_USER, sharedPreferences.getString(ID_USER, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        return user;
    }

    //status sudah login
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    //hapus session
    public void logoutSession() {
        editor.clear();
        editor.commit();
    }
}
