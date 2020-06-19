package com.gusrifarisyudaalhafis.uas.model.user;

import com.google.gson.annotations.SerializedName;

//response dari semua model pada user yang disesuaikan
public class Data{

	@SerializedName("Id_User")
	private String idUser;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"Id_User = '" + idUser + '\'' +
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}