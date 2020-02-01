package com.mobarok.paymentbd.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.BalanceInfo;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.User;


public class SharedPrefManager {
    private static final String SHARED_PREF_NAME="user_information";
    private static SharedPrefManager mInstance;
    private Context context;

    private SharedPrefManager(Context context){
        this.context = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance==null){
            mInstance = new SharedPrefManager( context );
        }
        return  mInstance;
    }

    public void saveUser(User user){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences( SHARED_PREF_NAME,this.context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "id", user.getUser_id() );
        editor.putString( "name", user.getName() );
        editor.putString( "balance", user.getBalance() );
        editor.putString( "net_balance", user.getNet_balance() );
        editor.putString( "phone", user.getPhone() );
        editor.putString( "email", user.getEmail() );
        editor.putString( "district", user.getDistrict() );
        editor.putString( "address", user.getAddress() );
        editor.putString( "account_type", user.getAccount_type() );
        editor.putInt( "status", user.getStatus() );

        editor.apply();
    }

    public User getUserInfo(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences( SHARED_PREF_NAME,this.context.MODE_PRIVATE );
        return new User(

                sharedPreferences.getString( "id", null),
                sharedPreferences.getString( "name", null),
                sharedPreferences.getString( "balance", null ),
                sharedPreferences.getString( "net_balance", null ),
                sharedPreferences.getString( "phone", null),
                sharedPreferences.getString( "email", null),
                sharedPreferences.getString( "district", null),
                sharedPreferences.getString( "balance", null),
                sharedPreferences.getString( "account_type", null),
                sharedPreferences.getInt( "status", -1)
        );
    }
    public boolean clear(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences( SHARED_PREF_NAME,this.context.MODE_PRIVATE );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return sharedPreferences.getString( "id",null ) == null;
    }


    public BalanceInfo getBalanceInfo(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences( SHARED_PREF_NAME,this.context.MODE_PRIVATE );
        return new BalanceInfo(
                sharedPreferences.getString( "balance", null ),
                sharedPreferences.getString( "net_balance", null )
        );
    }


}
