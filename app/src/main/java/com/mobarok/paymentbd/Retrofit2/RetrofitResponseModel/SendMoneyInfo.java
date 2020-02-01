package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

import android.os.Debug;
import android.util.Log;

public class SendMoneyInfo {
    private String reciver_id,reciver_name,reciver_phone,reciver_district,reciver_address,
            reciver_account_type,reciver_status,mustDeposit,net_transaction_amount,fess_amount,
            total_transaction_amount,remaining_balance;

    public SendMoneyInfo(String reciver_id, String reciver_name, String reciver_phone, String reciver_district, String reciver_address,
                         String reciver_account_type, String reciver_status, String mustDeposit, String net_transaction_amount,
                         String fess_amount, String total_transaction_amount, String remaining_balance) {

        this.reciver_id = reciver_id;
        this.reciver_name = reciver_name;
        this.reciver_phone = reciver_phone;
        this.reciver_district = reciver_district;
        this.reciver_address = reciver_address;
        this.reciver_account_type = reciver_account_type;
        this.reciver_status = reciver_status;
        this.mustDeposit = mustDeposit;
        this.net_transaction_amount = net_transaction_amount;
        this.fess_amount = fess_amount;
        this.total_transaction_amount = total_transaction_amount;
        this.remaining_balance = remaining_balance;
    }

    public String getReciver_id() {
        return reciver_id;
    }

    public String getReciver_name() {
        return reciver_name;
    }

    public String getReciver_phone() {
        return reciver_phone;
    }

    public String getReciver_district() {
        return reciver_district;
    }

    public String getReciver_address() {
        return reciver_address;
    }

    public String getReciver_account_type() {
        return reciver_account_type;
    }

    public String getReciver_status() {
        return reciver_status;
    }

    public String getMustDeposit() {
        return mustDeposit;
    }

    public String getNet_transaction_amount() {
        return net_transaction_amount;
    }

    public String getFess_amount() {
        return fess_amount;
    }

    public String getTotal_transaction_amount() {
        return total_transaction_amount;
    }

    public String getRemaining_balance() {
        return remaining_balance;
    }
}
