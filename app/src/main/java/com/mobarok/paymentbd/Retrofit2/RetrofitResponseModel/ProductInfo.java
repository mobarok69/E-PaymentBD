package com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel;

public class ProductInfo {
    String product_id,owner_name,product_name,price,vat,vat_amount,total_amount;

    public ProductInfo(String product_id, String owner_name, String product_name, String price, String vat, String vat_amount, String total_amount) {
        this.product_id = product_id;
        this.owner_name = owner_name;
        this.product_name = product_name;
        this.price = price;
        this.vat = vat;
        this.vat_amount = vat_amount;
        this.total_amount = total_amount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getPrice() {
        return price;
    }

    public String getVat() {
        return vat;
    }

    public String getVat_amount() {
        return vat_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }
}
