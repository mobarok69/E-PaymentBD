package com.mobarok.paymentbd;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.DefaultResponse;

import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private android.app.AlertDialog loader;
    private Button submit;
    private EditText user_name,email,phone,password_1,password_2,transaction_pin1,transction_pin2,address,district;
    private String str_user_name,str_email,str_phone,str_password_1,str_password_2,str_transaction_pin1,str_transction_pin2,str_address,str_district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        loader = new SpotsDialog( Register.this );
        user_name = findViewById( R.id.register_user_name);
        email = findViewById( R.id.register_user_email);
        phone = findViewById( R.id.register_user_phone);
        password_1 = findViewById( R.id.register_password_1);
        password_2 = findViewById( R.id.register_password_2);
        transaction_pin1 = findViewById( R.id.register_transaction_pin_1);
        transction_pin2 = findViewById( R.id.register_transaction_pin_2);
        address = findViewById( R.id.register_user_address);
        district = findViewById( R.id.register_user_district);

        submit = findViewById( R.id.register_button_submit );
        submit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        } );
    }

    void signUp() {

        str_user_name = user_name.getText().toString().trim();
        str_email = email.getText().toString().trim();
        str_phone = phone.getText().toString().trim();
        str_password_1 = password_1.getText().toString().trim();
        str_password_2 = password_2.getText().toString().trim();
        str_transaction_pin1 = transaction_pin1.getText().toString().trim();
        str_transction_pin2 = transction_pin2.getText().toString().trim();
        str_address = address.getText().toString().trim();
        str_district = district.getText().toString().trim();

        if (str_user_name.isEmpty()) {
            user_name.setError( "Required!" );
            user_name.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher( str_email ).matches()) {

            email.setError( "Enter a Valid Email !" );
            email.requestFocus();
            return;
        }
        if (str_phone.isEmpty()) {
            phone.setError( "Required !" );
            phone.requestFocus();
            return;
        }
        if (str_phone.length() > 12 || str_phone.length() < 11) {
            phone.setError( "Enter Valid Phone Number" );
            phone.requestFocus();
            return;
        }
        if (str_password_2.isEmpty()) {
            password_2.setError( "Required !" );
            password_2.requestFocus();
            return;
        }
        if (str_password_1.length() < 6) {
            password_1.setError( "Minimum 6 Character Required !" );
            password_1.requestFocus();
            return;
        }

        if (!str_password_1.equals( str_password_2 )) {
            password_1.setError( "Password Doesn't  Match!" );
            password_2.setError( "Password Doesn't  Match!" );
            password_1.requestFocus();
            return;
        }
        /***********if Validation is Complete Then Call TO The server API************/
        loader.show();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register_user( str_email,str_user_name,str_phone,str_password_1,str_transaction_pin1,str_district,str_address );
        call.enqueue( new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                loader.hide();
                DefaultResponse dr = response.body();
                if (!dr.isError()) {

                    AlertDialog.Builder builder =new AlertDialog.Builder(Register.this);
                    builder.setMessage(dr.getMsg())
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent( Register.this,Login.class );
                                    startActivity( intent );
                                }
                            })
                            .create()
                            .show();

                } else {

                    Toast.makeText( getApplicationContext(),dr.getMsg(), Toast.LENGTH_LONG ).show();
                }

            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable throwable) {
                loader.hide();

                Toast.makeText( getApplicationContext(), "Failed To Connect", Toast.LENGTH_LONG ).show();
            }
        } );
    }


}
