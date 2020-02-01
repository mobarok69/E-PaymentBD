package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import android.util.Log;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.SendMoneyInfo;

public class SendMoneyTransactionInfoResponse {
    private boolean error;
    private String message;
    private SendMoneyInfo SendMoneyTransactionInfo;

    public SendMoneyTransactionInfoResponse(boolean error, String message, SendMoneyInfo sendMoneyTransactionInfo) {
        this.error = error;
        this.message = message;
        this.SendMoneyTransactionInfo = sendMoneyTransactionInfo;

        Log.d( "ddd", "SendMoneyTransactionInfoResponse: "+getSendMoneyTransactionInfo().toString() );
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public SendMoneyInfo getSendMoneyTransactionInfo() {
        return SendMoneyTransactionInfo;
    }
}
