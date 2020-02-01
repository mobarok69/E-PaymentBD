package com.mobarok.paymentbd.Retrofit2;



import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.Bill_InfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.DefaultResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.LoginResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.ProductInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.SendMoneyTransactionInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.StatementResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.UserInfoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    //Register User
    @FormUrlEncoded
    @POST("register")
    Call<DefaultResponse> register_user(
            @Field("email") String email,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("pin") String pin,
            @Field("district") String district,
            @Field("address") String address
    );

    //Login
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(
            @Field("phone") String phone,
            @Field("password") String password
    );

    //profile_info
    @FormUrlEncoded
    @POST("getUserInfo")
    Call<UserInfoResponse> getUserInfo(
            @Field("phone") String phone
    );
    @FormUrlEncoded
    @POST("load_send_transaction_info")
    Call<SendMoneyTransactionInfoResponse> getsendTransactionInfo(
            @Field( "receiver_id") String receiver_id,
            @Field( "amount" ) String amount,
            @Field( "user_phone" ) String User_phone
    );
    @FormUrlEncoded
    @POST("load_CashOut_info")
    Call<SendMoneyTransactionInfoResponse> getCashoutInfo(
            @Field( "receiver_id") String receiver_id,
            @Field( "amount" ) String amount,
            @Field( "user_phone" ) String User_phone
    );
    @FormUrlEncoded
    @POST("SendMoney")
    Call<DefaultResponse> ConfirmSendTransaction(
            @Field( "receiver_id") String receiver_id,
            @Field( "amount" ) String amount,
            @Field( "user_id" ) String user_id,
            @Field( "user_pin" ) String user_pin
    );
    @FormUrlEncoded
    @POST("CashOut")
    Call<DefaultResponse> ConfirmCashout(
            @Field( "receiver_id") String receiver_id,
            @Field( "amount" ) String amount,
            @Field( "user_id" ) String user_id,
            @Field( "user_pin" ) String user_pin
    );
    @FormUrlEncoded
    @POST("statement")
    Call<StatementResponse> getStatement(
            @Field( "user_id") String receiver_id,
            @Field( "from") int from,
            @Field( "to") int to
    );
    @FormUrlEncoded
    @POST("productInfo")
    Call<ProductInfoResponse> getProductInfo(
            @Field( "product_code") String product_code
    );
    @FormUrlEncoded
    @POST("ConfirmProductBuy")
    Call<DefaultResponse> confirmProduct(
            @Field( "product_code") String product_code,
            @Field( "user_id") String User_id,
            @Field( "user_pin") String user_pin,
            @Field( "note") String note
    );
    @FormUrlEncoded
    @POST("bill_info")
    Call<Bill_InfoResponse> getBill_info(
            @Field( "company_name") String company_name,
            @Field( "member_id") String member_id,
            @Field( "month") String month
    );
    @FormUrlEncoded
    @POST("bill_Pay")
    Call<DefaultResponse> Pay_Bill_Now(
            @Field( "user_pin") String user_pin,
            @Field( "user_id") String User_id,
            @Field( "bill_id") String Bill_ID
    );
    @FormUrlEncoded
    @POST("update_profile")
    Call<LoginResponse> update_profile(
            @Field("user_id") String user_id,
            @Field("login_token") String login_token,
            @Field("full_name") String full_name,
            @Field("phone") String phone,
            @Field("bkash") String bkash,
            @Field("rocket") String rocket,
            @Field("paypal") String paypal
    );
    @FormUrlEncoded
    @POST("change_password")
    Call<DefaultResponse> change_password(
            @Field("user_id") String user_id,
            @Field("login_token") String login_token,
            @Field("current_password") String full_name,
            @Field("new_password") String phone
    );


    @FormUrlEncoded
    @POST("contact")
    Call<DefaultResponse> contactUs(
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("message") String message
    );



}
