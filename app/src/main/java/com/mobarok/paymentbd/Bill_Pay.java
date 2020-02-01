package com.mobarok.paymentbd;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.Bill_InfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.DefaultResponse;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.Bill_Info;
import com.mobarok.paymentbd.Storage.SharedPrefManager;


import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Bill_Pay extends AppCompatActivity implements View.OnClickListener {
    private android.app.AlertDialog loader;
    private Bill_Info info;
    private LinearLayout polli_biddut,wasa,bdcom,petro_bangla,pgcb;
    private LinearLayout layout_BillInfoInput,layout_bill_info,layout_select_bill;
    private String str_company_name,str_user_id,str_month,str_user_pin,str_bill_id;
    private TextView text_bill_id,text_company_name,text_user_id,text_user_name,text_Month,text_net_amount,text_vat,text_status,total_amount;
    private EditText input_month,input_user_id,user_pin;
    private Button button_continue,button_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_bill_pay );
        loader = new SpotsDialog( Bill_Pay.this );
        setViewReference();
        DisplaySelectBill();
    }
    private void loadBillInfo(){
        str_month = input_month.getText().toString().trim();
        str_user_id = input_user_id.getText().toString().trim();

        if (str_month.length() != 7){
            input_month.setError( "Error ! Exp: 06/2019" );
            input_month.requestFocus();
            return;
        }
        loader.show();

        Call<Bill_InfoResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getBill_info( str_company_name,str_user_id,str_month);
        call.enqueue( new Callback<Bill_InfoResponse>() {
            @Override
            public void onResponse(Call<Bill_InfoResponse> call, Response<Bill_InfoResponse> response) {
                loader.hide();

                if (response.code() != 422) {
                    if (response.body().isError()) {
                        showDialog( response.body().getMessage() );
                        return;
                    }
                    info = response.body().getBill_info();

                    text_bill_id.setText( info.getBill_id() );
                    text_company_name.setText( info.getCompany_name() );
                    text_user_id.setText( info.getUser_id() );
                    text_user_name.setText( info.getUser_name() );
                    text_Month.setText( info.getMonth() );
                    text_net_amount.setText( info.getNet_amount() +" BDT");
                    text_vat.setText( info.getVat_amount() +" BDT");
                    total_amount.setText( info.getTotal_amount()+" BDT" );


                    int temp = Integer.parseInt( info.getStatus() );
                    String st =null;

                    if (temp == 0) st = "UNPAID";
                    else if (temp == 1){
                        st ="PAID";
                        text_status.setTextColor( getResources().getColor( R.color.Green ) );
                    }
                    text_status.setText( st );

                    DisplayBillInformation();

                }

                ////////////////////////////////////////////////////////////

                // If Any Error Then SHow A Dialog
                else {
                    Toast.makeText( getApplicationContext(), response.message(), Toast.LENGTH_LONG ).show();
                    showDialog( "Information Not Found!" );
                }
                ///////////////////////////////////////////////////////////////////

            }
            @Override
            public void onFailure(Call<Bill_InfoResponse> call, Throwable throwable) {
                loader.hide();

                Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        } );

    }
    private void PayBillNow(){
        str_user_id = SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo().getUser_id();
        str_user_pin = user_pin.getText().toString();
        str_bill_id = info.getBill_id();

        if (Integer.parseInt( info.getStatus() ) == 1){
            showDialog( "You Already PAID this Bill" );
            return;
        }
        loader.show();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .Pay_Bill_Now(str_user_pin,str_user_id,str_bill_id);
        call.enqueue( new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                loader.hide();
                DefaultResponse res = response.body();
                if (res.isError()){
                    showDialog( res.getMsg() );
                }else{
                    AlertDialog.Builder builder =new AlertDialog.Builder(Bill_Pay.this);
                    builder.setMessage(res.getMsg())
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent( Bill_Pay.this, MainActivity.class );
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

                Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        } );
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == polli_biddut.getId()){
            str_company_name = "PolliBiddut";
            DisplayBillINfoInput();
        }
        else if (v.getId() == wasa.getId()){
            str_company_name = "DhakaWasa";
            DisplayBillINfoInput();
        }
        else if (v.getId() == bdcom.getId()){
            str_company_name = "BDCOM";
            DisplayBillINfoInput();
        }
        else if (v.getId() == petro_bangla.getId()){
            str_company_name = "PetroBangla";
            DisplayBillINfoInput();
        }
        else if (v.getId() == pgcb.getId()){
            str_company_name = "PGCB";
            DisplayBillINfoInput();
        }
        else if (v.getId() == button_continue.getId()){
            loadBillInfo();
        }
        else if (v.getId() == button_confirm.getId()){
            PayBillNow();
        }
    }
    private void DisplaySelectBill(){
        layout_select_bill.setVisibility( LinearLayout.VISIBLE );
        layout_BillInfoInput.setVisibility( LinearLayout.GONE );
        layout_bill_info.setVisibility( LinearLayout.GONE );
    }
    private void DisplayBillINfoInput(){
        layout_select_bill.setVisibility( LinearLayout.GONE );
        layout_BillInfoInput.setVisibility( LinearLayout.VISIBLE );
        layout_bill_info.setVisibility( LinearLayout.GONE );
    }
    private void DisplayBillInformation(){
        layout_select_bill.setVisibility( LinearLayout.GONE );
        layout_BillInfoInput.setVisibility( LinearLayout.GONE );
        layout_bill_info.setVisibility( LinearLayout.VISIBLE );
    }
    private  void setViewReference(){

        polli_biddut = findViewById( R.id.select_bill_item_polli_biddut );
        wasa = findViewById( R.id.select_bill_item_wassa );
        bdcom = findViewById( R.id.select_bill_item_BDCom );
        petro_bangla = findViewById( R.id.select_bill_item_petro_bangla );
        pgcb = findViewById( R.id.select_bill_item_PGCB );

        input_month = findViewById( R.id.select_bill_input_bill_month );
        input_user_id = findViewById( R.id.select_bill_input_account_id );
        user_pin = findViewById( R.id.pay_bill_user_secret_pin );

        layout_bill_info = findViewById( R.id.pay_bill_layout_bill_info );
        layout_BillInfoInput = findViewById( R.id.pay_bill_layout_get_bill_info_input );
        layout_select_bill = findViewById( R.id.pay_bill_layout_select_bill );

        text_bill_id = findViewById( R.id.pay_bill_info_bill_id );
        text_company_name = findViewById( R.id.pay_bill_info_company_name );
        text_user_id = findViewById( R.id.pay_bill_info_user_id );
        text_user_name = findViewById( R.id.pay_bill_info_member_name );
        text_Month = findViewById( R.id.pay_bill_info_billing_month );
        text_net_amount = findViewById( R.id.pay_bill_info_net_amount );
        text_vat = findViewById( R.id.pay_bill_info_vat_amount );
        text_status = findViewById( R.id.pay_bill_info_bill_status );
        total_amount = findViewById( R.id.pay_bill_info_total_amount );

        button_confirm = findViewById( R.id.pay_bill_button_confirm_payment );
        button_continue = findViewById( R.id.pay_bill_button_continue );

        polli_biddut.setOnClickListener( this );
        wasa.setOnClickListener( this );
        bdcom.setOnClickListener( this );
        petro_bangla.setOnClickListener( this );
        pgcb.setOnClickListener( this );

        button_continue.setOnClickListener( this );
        button_confirm.setOnClickListener( this );
    }
    private void showDialog(String msg){
        AlertDialog.Builder builder =new AlertDialog.Builder(Bill_Pay.this);
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
