package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

public class DefaultResponse {

    /*********
     * We have to use Same name as JSON Keyname,Otherwise We Need to Add SerilizableName Anotation
     *
     * **********/

    private boolean error;

    private String message;

    public DefaultResponse(boolean error, String msg) {
        this.error = error;
        this.message = msg;
    }

    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return message;
    }
}
