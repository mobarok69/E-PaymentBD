package com.mobarok.paymentbd;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobarok.paymentbd.Retrofit2.RetrofitClient;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponse.StatementResponse;
import com.mobarok.paymentbd.Retrofit2.RetrofitResponseModel.StatementInfo;
import com.mobarok.paymentbd.Storage.SharedPrefManager;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Statement extends AppCompatActivity {
    private AlertDialog loader;
    String str_user_id;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        loader = new SpotsDialog( Statement.this );
        setContentView( R.layout.activity_statement );
        str_user_id = SharedPrefManager.getInstance( getApplicationContext()).getUserInfo().getUser_id();
        list = findViewById( R.id.list_view_statement );

    }

    @Override
    protected void onResume() {
        getDataFromServer();
        super.onResume();
    }

    private void getDataFromServer(){
        loader.show();
        Call<StatementResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .getStatement( str_user_id,0,10);
        call.enqueue( new Callback<StatementResponse>() {
            @Override
            public void onResponse(Call<StatementResponse> call, Response<StatementResponse> response) {
                loader.hide();

                if(!response.body().isError()){

                    CustomList adapter=  new CustomList( response.body().getStatementInfo(),getApplicationContext() );
                    list.setAdapter( adapter );

                }

                ////////////////////////////////////////////////////////////

                // If Any Error Then SHow A Dialog
                else  {
                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG ).show();
                }
                ///////////////////////////////////////////////////////////////////

            }
            @Override
            public void onFailure(Call<StatementResponse> call, Throwable throwable) {
                loader.hide();
                Toast.makeText( getApplicationContext(),"Connection Failed",Toast.LENGTH_LONG).show();
            }
        } );
    }
}
class CustomList extends BaseAdapter {

    private StatementInfo[] statement_info;
    private Context context;
    private LayoutInflater inflater;

    public CustomList(StatementInfo[] statement_info, Context context) {
        this.statement_info = statement_info;
        this.context = context;
    }


    @Override
    public int getCount() {
        return statement_info.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = inflater.inflate( R.layout.statement_demo, parent, false );

        }
        TextView r_date = convertView.findViewById( R.id.demo_list_view_statement_date );
        TextView r_type = convertView.findViewById( R.id.demo_list_view_statement_transaction_type );
        TextView r_amount = convertView.findViewById( R.id.demo_list_view_statement_amount );
        TextView r_id = convertView.findViewById( R.id.demo_list_view_statement_id );

        r_date.setText( statement_info[position].getDate() );
        r_amount.setText( statement_info[position].getTotal_amount() );
        r_id.setText( statement_info[position].getTransaction_id() );
        r_type.setText( statement_info[position].getTransactionType() );

        return convertView;
    }
}
