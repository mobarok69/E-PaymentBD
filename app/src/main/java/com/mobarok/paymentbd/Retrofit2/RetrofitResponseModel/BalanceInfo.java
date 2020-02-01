package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

public class BalanceInfo {
    private String balance,netBalance;

    public BalanceInfo(String balance, String netBalance) {
        this.balance = balance;
        this.netBalance = netBalance;
    }

    public String getBalance() {
        return balance;
    }

    public String getNetBalance() {
        return netBalance;
    }
}
