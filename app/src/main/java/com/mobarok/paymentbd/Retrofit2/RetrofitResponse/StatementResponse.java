package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.StatementInfo;

public class StatementResponse {
    private boolean error;

    private String message;

    private StatementInfo[] statementInfo;

    public StatementResponse(boolean error, String message, StatementInfo[] statementInfo) {
        this.error = error;
        this.message = message;
        this.statementInfo = statementInfo;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public StatementInfo[] getStatementInfo() {
        return statementInfo;
    }
}
