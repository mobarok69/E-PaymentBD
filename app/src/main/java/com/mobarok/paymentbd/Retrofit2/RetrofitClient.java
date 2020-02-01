package com.mobarok.paymentbd.Retrofit2;


import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String baseUrl="http://10.0.2.2";
    private static final String AUTH = "Basic "+ Base64.encodeToString( ("mobarok:1234").getBytes(),Base64.NO_WRAP );
    private static final String baseUrl="https://mobarok.tk/Api/ewallet/public/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient(){
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor( new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request org = chain.request();
                        Request.Builder builder = org.newBuilder()
                                .addHeader( "Authorization",AUTH )
                                .method( org.method(),org.body() );
                        Request request = builder.build();
                        return chain.proceed( request );
                    }
                } ).build();
        retrofit = new Retrofit.Builder()
                .baseUrl( baseUrl )
                .addConverterFactory( GsonConverterFactory.create() )
                .client( httpClient )
                .build();
    }
    public static synchronized RetrofitClient getInstance(){
        if(mInstance==null){
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    public API getApi(){

        return  retrofit.create( API.class );
    }
}
