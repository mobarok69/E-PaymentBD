package com.mobarok.paymentbd;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.DefaultResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.ProductInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.SendMoneyTransactionInfoResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.ProductInfo;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payments extends AppCompatActivity implements View.OnClickListener {
    private android.app.AlertDialog loader;
    private LinearLayout layout_product_code,product_info;
    private ProductInfo info;
    private EditText product_code,user_pin,comment;
    private String str_product_code,str_user_pin,str_comment,str_user_id;
    private TextView id,name,owner,net_price,vat_amount,total_amount;
    private Button continue_button,submit_button,scan_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_payments );
        loader = new SpotsDialog( Payments.this );
        product_code = findViewById( R.id.payment_input_product_code );
        user_pin = findViewById( R.id.payment_input_user_pin );
        comment = findViewById( R.id.payment_input_user_comment );
        id = findViewById( R.id.payment_info_product_id );
        name = findViewById( R.id.payment_info_product_name );
        owner = findViewById( R.id.payment_info_product_owner);
        net_price = findViewById( R.id.payment_info_product_net_price );
        vat_amount = findViewById( R.id.payment_info_product_vat_amount );
        total_amount = findViewById( R.id.payment_info_product_total_price );
        layout_product_code = findViewById( R.id.payment_layout_product_code_request );
        product_info = findViewById( R.id.payment_layout_product_info );
        continue_button = findViewById( R.id.payment_button_continue );
        submit_button = findViewById( R.id.payment_button_submit );
        scan_button = findViewById( R.id.payment_button_ScanNow );

        continue_button.setOnClickListener( this );
        submit_button.setOnClickListener( this );
        scan_button.setOnClickListener( this );

        DisplayProductCodeRequest();

    }
    private void loadProductInfo(){
        str_product_code = product_code.getText().toString().trim();
        if (str_product_code.isEmpty()){
            product_code.setError( "Required !" );
            product_code.requestFocus();
            return;
        }
        loader.show();

            Call<ProductInfoResponse> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getProductInfo( str_product_code);
            call.enqueue( new Callback<ProductInfoResponse>() {
                @Override
                public void onResponse(Call<ProductInfoResponse> call, Response<ProductInfoResponse> response) {
                    loader.hide();

                    if (response.code() != 422) {
                        if (response.body().isError()) {
                            showDialog( response.body().getMessage() );
                            return;
                        }
                        info = response.body().getProductInfo();

                        DisplayProductInfo();


                        id.setText( info.getProduct_id() );
                        name.setText( info.getProduct_name() );
                        owner.setText( info.getOwner_name() );
                        vat_amount.setText( info.getVat_amount() + " BDT " + "( " + info.getVat() + "% )" );
                        total_amount.setText( info.getTotal_amount() + " BDT" );
                        net_price.setText( info.getPrice() + " BDT" );


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
                public void onFailure(Call<ProductInfoResponse> call, Throwable throwable) {
                    loader.hide();

                    Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
                }
            } );

    }
    private void ConfirmTransaction(){
        str_user_pin = user_pin.getText().toString().trim();
        str_comment = comment.getText().toString().trim();
        str_user_id = SharedPrefManager.getInstance( getApplicationContext() ).getUserInfo().getUser_id();
        if (str_user_pin.isEmpty()){
            user_pin.setError( "4 Digit Pin Required !" );
            user_pin.requestFocus();
            return;
        }
        loader.show();

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .confirmProduct( str_product_code,str_user_id,str_user_pin,str_comment);
        call.enqueue( new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                loader.hide();

                DefaultResponse res = response.body();
                if (res.isError()){
                    showDialog( res.getMsg() );
                }else{
                    AlertDialog.Builder builder =new AlertDialog.Builder(Payments.this);
                    builder.setMessage(res.getMsg())
                            .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent( Payments.this, MainActivity.class );
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

    @Override
    public void onClick(View v) {
        if (v.getId() == continue_button.getId()){
            loadProductInfo();
        }else if (v.getId() == submit_button.getId()){
            ConfirmTransaction();
        }else  if (v.getId() == scan_button.getId()){
            ScanProductCode();
        }
    }
    private void DisplayProductInfo(){
        product_info.setVisibility( LinearLayout.VISIBLE );
        layout_product_code.setVisibility( LinearLayout.GONE );
    }
    private void DisplayProductCodeRequest(){
        product_info.setVisibility( LinearLayout.GONE );
        layout_product_code.setVisibility( LinearLayout.VISIBLE );
    }
    private void showDialog(String msg){
        AlertDialog.Builder builder =new AlertDialog.Builder(Payments.this);
        builder.setMessage(msg)
                .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }

    private void  ScanProductCode(){
            IntentIntegrator integrator = new IntentIntegrator( Payments.this);
            integrator.setDesiredBarcodeFormats( IntentIntegrator.QR_CODE_TYPES );
            integrator.setCameraId( 0 );
            integrator.setOrientationLocked( false );
            integrator.setPrompt( "Scanning" );
            integrator.setBeepEnabled( true );
            integrator.setBarcodeImageEnabled( true );
            integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult( requestCode,resultCode,data );

        if (result !=  null && result.getContents() != null){

                  product_code.setText(result.getContents().trim());
                  loadProductInfo();
        }
        else {
            showDialog( "Information Not Found!" );
        }

        super.onActivityResult( requestCode, resultCode, data );
    }

}
