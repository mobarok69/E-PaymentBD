package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.Bill_Info;

public class Bill_InfoResponse {
    private boolean error;

    private String message;

    private Bill_Info bill_info;

    public Bill_InfoResponse(boolean error, String message, Bill_Info bill_info) {
        this.error = error;
        this.message = message;
        this.bill_info = bill_info;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Bill_Info getBill_info() {
        return bill_info;
    }
}
