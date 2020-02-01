package com.mobarok.paymentbd;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QcodeScanner extends AppCompatActivity implements View.OnClickListener {

    private Button GenerateButton,Scan_button;
    private EditText input_string;
    private ImageView QCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_qcode_scanner );

        GenerateButton = findViewById( R.id.q_code_button_generate );
        Scan_button = findViewById( R.id.q_code_button_scan );
        input_string = findViewById( R.id.q_code_editZ_text_input );
        QCodeView = findViewById( R.id.q_code_image_view );

        GenerateButton.setOnClickListener( this );
        Scan_button.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == GenerateButton.getId()){
            generateNow();
        }else if (v.getId() == Scan_button.getId()){
            ScanNow();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        final IntentResult result = IntentIntegrator.parseActivityResult( requestCode,resultCode,data );

        if (result !=  null && result.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder( QcodeScanner.this )
                    .setTitle( "Result " )
                    .setMessage( result.getContents() )
                    .setPositiveButton( "Copy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClipboardManager manager = (ClipboardManager) getSystemService( CLIPBOARD_SERVICE );
                            ClipData data = ClipData.newPlainText( "result", result.getContents());
                            manager.setPrimaryClip( data );
                        }
                    } )
                    .setNegativeButton( "Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    } );
            builder.create().show();
        }

        super.onActivityResult( requestCode, resultCode, data );
    }

    private void ScanNow(){
        IntentIntegrator integrator = new IntentIntegrator( QcodeScanner.this);
        integrator.setDesiredBarcodeFormats( IntentIntegrator.QR_CODE_TYPES );
        integrator.setCameraId( 0 );
        integrator.setOrientationLocked( false );
        integrator.setPrompt( "Scanning" );
        integrator.setBeepEnabled( true );
        integrator.setBarcodeImageEnabled( true );
        integrator.initiateScan();

    }
    private void generateNow(){
        String str = input_string.getText().toString();
        if (str != null && !str.isEmpty()){
            try{
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = multiFormatWriter.encode( str, BarcodeFormat.QR_CODE,500,500 );
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap( bitMatrix );
                QCodeView.setImageBitmap( bitmap );
            }catch (Exception e){
                Log.e( "eee", "ScanNow: ",e  );
            }
        }
    }

}
