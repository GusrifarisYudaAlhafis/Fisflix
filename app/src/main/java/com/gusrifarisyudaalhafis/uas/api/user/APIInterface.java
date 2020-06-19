package com.gusrifarisyudaalhafis.uas.api.user;

import com.gusrifarisyudaalhafis.uas.model.user.Delete;
import com.gusrifarisyudaalhafis.uas.model.user.Login;
import com.gusrifarisyudaalhafis.uas.model.user.Register;
import com.gusrifarisyudaalhafis.uas.model.user.Update;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    //POST login
    @FormUrlEncoded
    @POST("loginandroid.php")
    Call<Login> loginResponse(@Field("email") String email, @Field("pass") String pass);

    //POST register
    @FormUrlEncoded
    @POST("registerandroid.php")
    Call<Register> registerResponse(@Field("username") String username, @Field("email") String email, @Field("pass") String pass);

    //POST update
    @FormUrlEncoded
    @POST("updateandroid.php")
    Call<Update> updateResponse(@Field("Id_User") String Id_User, @Field("username") String username, @Field("email") String email);

    //POST delete
    @FormUrlEncoded
    @POST("deleteandroid.php")
    Call<Delete> deleteResponse(@Field("Id_User") String Id_User);
}
