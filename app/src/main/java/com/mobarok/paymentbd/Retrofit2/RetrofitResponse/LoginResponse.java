package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import com.google.gson.annotations.SerializedName;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.User;

public class LoginResponse {

    private boolean error;
    @SerializedName( "message" )
    private String msg;
    @SerializedName( "user" )
    private User userinfo;

    public LoginResponse(boolean error, String msg, User userinfo) {
        this.error = error;
        this.msg = msg;
        this.userinfo = userinfo;
    }

    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return msg;
    }

    public User getUserinfo() {
        return userinfo;
    }
}
