package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.User;

public class UserInfoResponse {
    private Boolean error;
    private String message;
    private User user;

    public UserInfoResponse(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
