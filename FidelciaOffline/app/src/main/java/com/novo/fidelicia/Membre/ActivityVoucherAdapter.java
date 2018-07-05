package com.novo.fidelicia.Membre;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.novo.fidelicia.R;
import com.novo.fidelicia.database.AllMemberModelByMiBarCodeOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.index.AllVoucherModel;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 12-01-2018.
 */

public class ActivityVoucherAdapter extends BaseAdapter {
    private Context var;
    ArrayList<ActivityVoucherModel> items=new ArrayList<ActivityVoucherModel>();
    SharedPreference sharedPreference;
    String getCardnumber,getLoginToken,getGetMiBarcode;
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    String ActivitiesUrl = "Member/ViewOneMemberActivitiesByCardNumber?CardNumber=";
    String getVdBarcode,getVoucherStage,StrDate,getCashier;
    String VoucherUtilisationUrl = "Voucher/VoucherUtilisation";
    AlertDialog.Builder builder3;
    View view3;
    Button Btn_OK_congratulation;
    AlertDialog alertDialog3;
    boolean your_date_is_outdated=true;
    DatabaseHelper databaseHelper = null;
    AllVoucherModel allVoucherModel = new AllVoucherModel();

    @SuppressLint("LongLogTag")
    public ActivityVoucherAdapter(Context var, ArrayList<ActivityVoucherModel> items ) {
        this.var = var;
        this.items = items;
        sharedPreference = new SharedPreference();

        getLoginToken = sharedPreference.getLoginToken(var);
        getCardnumber = sharedPreference.getCardNumber(var);
        getGetMiBarcode = sharedPreference.getMiBarcodeNumber(var);
        getCashier = sharedPreference.getUserName(var);


        Log.e("GetLoginTokenInAdapter",""+getLoginToken);
        Log.e("GetCardNumberInAdapter :",""+getCardnumber);
        Log.e("GetMiBarcodeInAdapter",""+getGetMiBarcode);

        if(Config.CashierMode == "MultipleCashier")
        {
            if(getGetMiBarcode!=null)
            {
                ViewOneMemberByMiBarcode();
            }
        }
        else
        {
            if(getGetMiBarcode!=null)
            {
                ViewOneMemberByMi_BarcodeFromSqliteDatabase();
            }
        }

        builder3 = new AlertDialog.Builder(var);
        LayoutInflater layoutInflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = layoutInflater.inflate(R.layout.congratulation_voucher_popup, null);
        Btn_OK_congratulation = (Button) view3.findViewById(R.id.btn_voucher_congrtulation);
        builder3.setView(view3);
        alertDialog3 = builder3.create();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try{
            ViewHolder holder = null;
            final ActivityVoucherModel pojo=items.get(position);
            LayoutInflater inflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.activities_voucher_row,null);
                holder=new ViewHolder();
                holder.type_de_bon = (TextView)convertView.findViewById(R.id.Type_de_bon);
                holder.date_of_expiration = (TextView)convertView.findViewById(R.id.Date_d_expiration);
                holder.montant = (TextView)convertView.findViewById(R.id.Montant);
                holder.etat = (TextView)convertView.findViewById(R.id.Etat);
                holder.depenser = (Button)convertView.findViewById(R.id.button_despenser);

                holder.type_de_bon.setText(pojo.getType_de_bon());
                holder.date_of_expiration.setText(pojo.getDate_of_expiration());
                holder.montant.setText(pojo.getMontant());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
                Date strDate = sdf.parse(pojo.getDate_of_expiration());
                if (System.currentTimeMillis() > strDate.getTime()&&pojo.getEtat().equals("ENVOYÉ")) {
                    your_date_is_outdated = true;
                    Log.e("Expire_Date","Date is over");
                    holder.depenser.setVisibility(View.GONE);
                    holder.etat.setVisibility(View.VISIBLE);
                    holder.etat.setText("EXPIRÉ");
                }
                else{
                    your_date_is_outdated = false;
                    Log.e("Expire_Date","Date is not over");

                    if(pojo.getEtat().equals("ENVOYÉ"))
                    {
                        holder.depenser.setVisibility(View.VISIBLE);
                        holder.etat.setVisibility(View.GONE);
                        final ViewHolder finalHolder = holder;
                        holder.depenser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                getVdBarcode = pojo.getVd_barcode();
                                Log.e("GetVD_Barcode :",""+getVdBarcode);
                                if(Config.CashierMode == "MultipleCashier")
                                {
                                    VoucherUtilisation();
                                }
                                else
                                {
                                    VoucherUtilistationFromSqliteDatabase();
                                }
                                finalHolder.etat.setVisibility(View.VISIBLE);
                                finalHolder.depenser.setVisibility(View.GONE);
                                finalHolder.etat.setText("UTILISÉ");
                            }
                        });
                    }
                    else
                    {
                        holder.etat.setText(pojo.getEtat());
                        holder.depenser.setVisibility(View.GONE);
                    }
                }
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder
    {
        TextView type_de_bon,date_of_expiration,montant,etat;
        Button depenser;

    }


    public void ViewOneMemberByMiBarcode()
    {
        if (InternetUtil.isConnected(var)){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(getGetMiBarcode), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMiBarcode :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMemberMiBarCode :",""+JO);


                        getCardnumber = JO.getString("card_number");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (error instanceof NetworkError || error instanceof NoConnectionError) {
                                Log.e("ErrrrorNetwork", "" + error);
                            } else if (error instanceof ServerError) {
                                Log.e("ErrrrorServer", "" + error);
                            } else if (error instanceof AuthFailureError) {
                                Log.e("ErrrrorAuthFail", "" + error);
                            } else if (error instanceof ParseError) {
                                Log.e("ErrrrorPrase", "" + error);
                            } else if (error instanceof TimeoutError) {
                                Log.e("ErrrrorTimeOut", "" + error);
                            }
                        }
                    }){

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(var);
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(var, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void VoucherUtilisation() {
        if (InternetUtil.isConnected(var)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(VoucherUtilisationUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("GetVoucherResponse :", "" + response);
                    try {
                        JSONObject JO = new JSONObject(response);
                        String VoucherResponse = JO.getString("results");
                        Log.e("VoucherResponse :", "" + VoucherResponse);

                         if (VoucherResponse.equals("Congratulations! Voucher Used Successfully.")) {
                            alertDialog3.show();
                            Btn_OK_congratulation.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog3.dismiss();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Log.e("ErrrrorNetwork", "" + error);
                    } else if (error instanceof ServerError) {
                        Log.e("ErrrrorServer", "" + error);
                    } else if (error instanceof AuthFailureError) {
                        Log.e("ErrrrorAuthFail", "" + error);
                    } else if (error instanceof ParseError) {
                        Log.e("ErrrrorPrase", "" + error);
                    } else if (error instanceof TimeoutError) {
                        Log.e("ErrrrorTimeOut", "" + error);
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("vd_barcode", getVdBarcode);
                    return Params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(var);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(var, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }


    public void VoucherUtilistationFromSqliteDatabase()
    {

        databaseHelper = new DatabaseHelper(var);

            allVoucherModel = new AllVoucherModel();
            allVoucherModel = databaseHelper.getAllVoucherInformation(getVdBarcode);
            getVdBarcode = allVoucherModel.getVd_barcode();
            getVoucherStage = allVoucherModel.getVoucher_stage();

            Log.e("GetVdBarcode ",""+getVdBarcode);

            if(getVoucherStage.equals("ENVOYÉ"))
            {
                allVoucherModel = new AllVoucherModel();
                alertDialog3.show();
                StrDate = new CurrentTimeGet().getCurrentTime();
                allVoucherModel.setVd_barcode(getVdBarcode);
                allVoucherModel.setVoucher_stage("UTILISÉ");
                allVoucherModel.setUpdated_by(getCashier);
                allVoucherModel.setUpdated_on(StrDate);
                allVoucherModel.setUp_window(false);
                allVoucherModel.setUpdate_status("Checked");

                databaseHelper.UpdateAllVoucherInformation(allVoucherModel);

                Btn_OK_congratulation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                    }
                });
            }
        }

    public void ViewOneMemberByMi_BarcodeFromSqliteDatabase() {
        databaseHelper = new DatabaseHelper(var);
        AllMemberModelByMiBarCodeOnly allMemberModel = new AllMemberModelByMiBarCodeOnly();
        allMemberModel = databaseHelper.GetInformationByBarcode(getGetMiBarcode);
        getCardnumber =allMemberModel.getCard_number();

    }

}
