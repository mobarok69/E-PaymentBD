package com.mobarok.paymentbd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.LoginResponse;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText phone,password;
    private String str_phone,str_password;
    private Button submit,show_register;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        loader = new SpotsDialog( Login.this );
        phone = findViewById( R.id.login_user_phone );
        password = findViewById( R.id.login_user_password );
        submit = findViewById( R.id.login_button_submit );
        show_register = findViewById( R.id.login_button_show_register_panel );
        submit.setOnClickListener( this );
        show_register.setOnClickListener( this );

        if (isLogged()){
            Intent intent = new Intent(Login.this, MainActivity.class );
            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            startActivity( intent );
        }

    }

    private void  loginNow(){
         str_phone = phone.getText().toString().trim();
         str_password = password.getText().toString().trim();

        if ( str_phone.length() > 11 || str_phone.length() <11) {
            phone.setError( "Invalid ! ex: 01717373445" );
            phone.requestFocus();
            return;
        }
        if (str_password.isEmpty()) {

            password.setError( "Required !" );
            password.requestFocus();
            return;
        }
        loader.show();

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login( str_phone,str_password );
        call.enqueue( new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loader.hide();

                if(response.code()!=422){
                    LoginResponse loginResponse = response.body();
                    if(!loginResponse.isError()){

                            SharedPrefManager.getInstance( getApplicationContext() ).saveUser( loginResponse.getUserinfo());
                            Intent intent = new Intent(Login.this, MainActivity.class );
                            intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity( intent );

                    }
                }
                else  {
                    Toast.makeText( getApplicationContext(),"Invalid Phone Or Password ",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                loader.hide();
                Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        } );
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button_submit){
                loginNow();

        }else if (v.getId() == R.id.login_button_show_register_panel){
            Intent intent = new Intent( Login.this,Register.class );
            startActivity( intent );
        }
    }

    private boolean isLogged(){
         if(SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo().getPhone() == null){
             return false;
         }else
            return true;
    }
}
