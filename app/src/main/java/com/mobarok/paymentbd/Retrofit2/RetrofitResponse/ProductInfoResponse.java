package com.mobarok.paymentbd.Retrofit2.RetrofitResponse;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.ProductInfo;

public class ProductInfoResponse {
    private boolean error;

    private String message;

    private ProductInfo product_info;

    public ProductInfoResponse(boolean error, String message, com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.ProductInfo product_info) {
        this.error = error;
        this.message = message;
        this.product_info = product_info;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.ProductInfo getProductInfo() {
        return product_info;
    }
}
