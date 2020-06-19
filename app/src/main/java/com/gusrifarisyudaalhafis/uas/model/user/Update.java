package com.gusrifarisyudaalhafis.uas.model.user;

import com.google.gson.annotations.SerializedName;

//model update
public class Update{

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private int status;

    public void setData(Data data){
        this.data = data;
    }

    public Data getData(){
        return data;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "Update{" +
                        "data = '" + data + '\'' +
                        ",status = '" + status + '\'' +
                        "}";
    }
}