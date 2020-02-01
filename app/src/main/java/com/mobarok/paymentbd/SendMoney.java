package com.mobarok.paymentbd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.DefaultResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.LoginResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.SendMoneyTransactionInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.SendMoneyInfo;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import java.text.DecimalFormat;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMoney extends AppCompatActivity implements View.OnClickListener {
    private android.app.AlertDialog loader;
    private SendMoneyInfo info ;
    private LinearLayout amountAndReciverIdCollector,info_displayer;
    private Button continue_button,submit_button;
    private EditText reciverID,amount,user_pin;
    private String str_reciverID,str_amount,str_user_pin,str_user_phone,str_user_id;
    private TextView info_reciver_id,info_name,info_district,info_account_type,info_amount,info_fees,info_total_amount,info_after_balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_send_money );
        loader = new SpotsDialog( SendMoney.this );
        setObjectsIDs();
        requestAmountAndUser();
    }



    private void requestAmountAndUser(){
        amountAndReciverIdCollector.setVisibility( LinearLayout.VISIBLE );
        info_displayer.setVisibility( LinearLayout.GONE );
    }
    private void DisplayRecieverAmount(){
        amountAndReciverIdCollector.setVisibility( LinearLayout.GONE );
        info_displayer.setVisibility( LinearLayout.VISIBLE );
    }
    private void loadTransactionInfo(){
        if (load_ID_and_Amount_Value()){
            loader.show();
            Call<SendMoneyTransactionInfoResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getsendTransactionInfo( str_reciverID,str_amount,str_user_phone);
            call.enqueue( new Callback<SendMoneyTransactionInfoResponse>() {
                @Override
                public void onResponse(Call<SendMoneyTransactionInfoResponse> call, Response<SendMoneyTransactionInfoResponse> response) {
                    loader.hide();

                    if(response.code()!=422){
                        if (response.body().isError()){
                            showDialog(response.body().getMessage());
                            return;
                        }
                         info = response.body().getSendMoneyTransactionInfo();

                         DisplayRecieverAmount();


                        info_reciver_id.setText( info.getReciver_id() );
                        info_name.setText( info.getReciver_name() );
                        info_district.setText( info.getReciver_district() );
                        info_account_type.setText( info.getReciver_account_type() );
                        info_amount.setText( info.getNet_transaction_amount() + " BDT");
                        info_fees.setText(  info.getFess_amount() + " BDT");
                        info_total_amount.setText(  info.getTotal_transaction_amount()+" BDT");
                        info_after_balance.setText(  info.getRemaining_balance() +" BDT");


                    }

                    ////////////////////////////////////////////////////////////

                    // If Any Error Then SHow A Dialog
                else  {
                    showDialog( "Receiver Information Not Found!" );
                }
                ///////////////////////////////////////////////////////////////////

                }
                @Override
                public void onFailure(Call<SendMoneyTransactionInfoResponse> call, Throwable throwable) {
                    loader.hide();

                    Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
                }
            } );
        }
    }
    private void ConfirmTransaction(){
        str_user_pin = user_pin.getText().toString().trim();
        if (str_user_pin.isEmpty()){
            user_pin.setError( "4 Digit Pin Required !" );
            user_pin.requestFocus();
            return;
        }

        Double balance = Double.parseDouble( info.getRemaining_balance() );
        Double mustDeposit = Double.parseDouble( info.getMustDeposit() );

        if (balance < mustDeposit){
            showDialog( "Doesn't Have Sufficient Amount to Send " );
            return;
        }
        loader.show();
        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .ConfirmSendTransaction( str_reciverID,str_amount,str_user_id,str_user_pin );
        call.enqueue( new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                loader.hide();

                    DefaultResponse res = response.body();
                    if (res.isError()){
                        showDialog( res.getMsg() );
                    }else{
                        AlertDialog.Builder builder =new AlertDialog.Builder(SendMoney.this);
                        builder.setMessage(res.getMsg())
                                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent( SendMoney.this, MainActivity.class );
                                        startActivity( intent );
                                    }
                                })
                                .create()
                                .show();

                    }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable throwable) {
                loader.hide();

                showDialog( "Failed to Connect . Check Your Internet Connection !" );
            }
        } );
    }
    private boolean load_ID_and_Amount_Value(){
        str_reciverID = reciverID.getText().toString().trim();
        str_amount = amount.getText().toString().trim();
        str_user_phone = SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo().getPhone();
        str_user_id = SharedPrefManager.getInstance( SendMoney.this ).getUserInfo().getUser_id();
        if (str_reciverID.length() < 11 || str_reciverID.length() > 12){
            reciverID.setError( "Invalid Id or Phone!" );
            reciverID.requestFocus();
            return false;
        }
        if (str_amount.isEmpty()){
            amount.setError( "Required" );
            amount.requestFocus();
            return false;
        }
        if (str_user_phone == null || str_user_phone.length() < 11 || str_user_id == null){
            SharedPrefManager.getInstance( getApplicationContext() ).clear();
            Intent intent = new Intent( SendMoney.this,Login.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
        }
        return  true;
    }

    private void setObjectsIDs() {
        amountAndReciverIdCollector = findViewById( R.id.send_money_layout_request );
        info_displayer = findViewById( R.id.send_money_layout_info );
        continue_button = findViewById( R.id.send_money_button_continue_transaction );
        submit_button = findViewById( R.id.send_money_button_confirm_transaction );
        reciverID = findViewById( R.id.send_money_input_receiver_id );
        amount = findViewById( R.id.send_money_input_amount );
        user_pin = findViewById( R.id.send_money_input_user_pin );
        info_reciver_id = findViewById( R.id.send_money_text_receiver_id );
        info_name = findViewById( R.id.send_money_text_receiver_name );
        info_district = findViewById( R.id.send_money_text_receiver_district );
        info_account_type = findViewById( R.id.send_money_text_receiver_id_type );
        info_amount = findViewById( R.id.send_money_transaction_amount );
        info_fees = findViewById( R.id.send_money_transaction_fess );
        info_total_amount = findViewById( R.id.send_money_transaction_total_amount );
        info_after_balance = findViewById( R.id.send_money_transaction_after_balance );

        continue_button.setOnClickListener( this );
        submit_button.setOnClickListener( this );


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == continue_button.getId()){
            loadTransactionInfo();
        }
        else if (v.getId() == submit_button.getId()){
            ConfirmTransaction();
        }
    }

    private void showDialog(String msg){
        AlertDialog.Builder builder =new AlertDialog.Builder(SendMoney.this);
        builder.setMessage(msg)
                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }
}
