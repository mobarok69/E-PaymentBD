package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

public class Bill_Info {
    private String bill_id,company_name,user_id,user_name,month,net_amount,vat_amount,total_amount,status;

    public Bill_Info(String bill_id, String company_name, String user_id, String user_name, String month, String net_amount, String vat_amount, String total_amount, String status) {
        this.bill_id = bill_id;
        this.company_name = company_name;
        this.user_id = user_id;
        this.user_name = user_name;
        this.month = month;
        this.net_amount = net_amount;
        this.vat_amount = vat_amount;
        this.total_amount = total_amount;
        this.status = status;
    }

    public String getBill_id() {
        return bill_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getMonth() {
        return month;
    }

    public String getNet_amount() {
        return net_amount;
    }

    public String getVat_amount() {
        return vat_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getStatus() {
        return status;
    }
}
