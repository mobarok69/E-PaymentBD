package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

public class StatementInfo {
    private String date,transactionType,total_amount,transaction_id;

    public StatementInfo(String date, String transactionType, String total_amount, String transaction_id) {
        this.date = date;
        this.transactionType = transactionType;
        this.total_amount = total_amount;
        this.transaction_id = transaction_id;
    }

    public String getDate() {
        return date;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }
}
