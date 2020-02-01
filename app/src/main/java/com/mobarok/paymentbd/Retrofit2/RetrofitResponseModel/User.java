package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

public class User {
    private  String user_id,name,balance,net_balance,phone,email,district,address,account_type;
    private int status;



    public User(String user_id, String name, String balance, String net_balance, String phone, String email, String district, String address, String account_type, int status) {
        this.user_id = user_id;
        this.name = name;
        this.balance = balance;
        this.net_balance = net_balance;
        this.phone = phone;
        this.email = email;
        this.district = district;
        this.address = address;
        this.account_type = account_type;
        this.status = status;
    }
    public String getNet_balance() {
        return net_balance;
    }
    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getBalance() {
        return balance;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddress() {
        return address;
    }

    public String getAccount_type() {
        return account_type;
    }

    public int getStatus() {
        return status;
    }
}
