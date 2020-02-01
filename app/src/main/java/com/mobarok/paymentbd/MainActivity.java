package com.mobarok.paymentbd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.User;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout sendMoney,cash_out,bill_pay,balance,payment,statement;
    private TextView user_name,user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        sendMoney = findViewById( R.id.layout_sendMoney );
        cash_out = findViewById( R.id.layout_cashOut );
        bill_pay = findViewById( R.id.layout_bill_pay );
        balance = findViewById( R.id.layout_balance );
        payment = findViewById( R.id.layout_payment );
        statement = findViewById( R.id.layout_statement );
        user_name = findViewById( R.id.short_view_name );
        user_id = findViewById( R.id.short_view_id );

        sendMoney.setOnClickListener( this );
        cash_out.setOnClickListener( this );
        bill_pay.setOnClickListener( this );
        balance.setOnClickListener( this );
        payment.setOnClickListener( this );
        statement.setOnClickListener( this );

        DisplayShortInfo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == sendMoney.getId()){
            Intent intent = new Intent( MainActivity.this,SendMoney.class);
            startActivity( intent );
        }
        else if (v.getId() == cash_out.getId()){
            Intent intent = new Intent( MainActivity.this,Cash_Out.class);
            startActivity( intent );
        }
        else if (v.getId() == bill_pay.getId()){
            Intent intent = new Intent( MainActivity.this,Bill_Pay.class);
            startActivity( intent );
        }
        else if (v.getId() == balance.getId()){

            Intent intent = new Intent( MainActivity.this, Balance.class );
            startActivity( intent );

        }
        else if (v.getId() == payment.getId()){
            Intent intent = new Intent( MainActivity.this,Payments.class);
            startActivity( intent );
        }
        else if (v.getId() == statement.getId()){
            Intent intent = new Intent( MainActivity.this,Statement.class);
            startActivity( intent );
        }

    }
    private void DisplayShortInfo(){
        User info = SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo();
        user_name.setText( info.getName() );
        user_id.setText( info.getUser_id() + " ("+info.getAccount_type()+")" );
    }
}
