package com.mobarok.paymentbd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.UserInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.BalanceInfo;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Balance extends AppCompatActivity implements View.OnClickListener
{
    private TextView available_balance,netBalance,circle_available_balance;
    private Button button;
    private AlertDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_balance );

        available_balance = findViewById( R.id.balance_available_balance );
        netBalance = findViewById( R.id.balance_net_balance );
        circle_available_balance = findViewById( R.id.balance_circle_available_balance );
        button = findViewById( R.id.balance_button_refresh );
        loader = new SpotsDialog( Balance.this );
        button.setOnClickListener( this );
        setBalance();
    }

    @Override
    protected void onResume() {
        loadDataFromServer();
        super.onResume();
    }

    private void setBalance(){
        try {
            BalanceInfo balance = SharedPrefManager.getInstance( getApplicationContext() ).getBalanceInfo();
            available_balance.setText( String.valueOf( balance.getBalance() ) );
            circle_available_balance.setText( String.valueOf( balance.getBalance() ) );
            netBalance.setText( String.valueOf( balance.getNetBalance() ) );
        }catch (Exception e){
            Log.d( "ssss", "setBalance: "+e );
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == button.getId())
            loadDataFromServer();
    }

    private void loadDataFromServer(){
        loader.show();
        Call<UserInfoResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getUserInfo( SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo().getPhone() );
        call.enqueue( new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                loader.hide();

                UserInfoResponse user_info = response.body();
                if(!user_info.isError()){
                 SharedPrefManager.getInstance( getApplicationContext() ).saveUser( user_info.getUser());
                    setBalance();
                }
                else  {
                    Toast.makeText( getApplicationContext(),"Failed to Load Balance ! ",Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable throwable) {
                loader.hide();

                Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        } );
    }
}
