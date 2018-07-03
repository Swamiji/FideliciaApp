package com.novo.fidelicia.Voucher;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.AllMemberModelByCardNumberOnly;
import com.novo.fidelicia.database.AllMemberModelByMiBarCodeOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SatisfactionVoucher extends AppCompatActivity {

    ImageView Close,phone,home,email;
    TextView Un_Bon_text;
    Button Creer_Un_bon_mettre_btn,Btn_SatisfactionVoucher,BtnOk;
    TextView Name,LastName,Phone_number,Address,Zip_Code,City,Email,Total_Points,LastVistDate;
    String getCardNumber,getName,getLastName,getdate_of_birth
            ,getage,getphNumber,getzip_code,getcity,getaddress,getemail
            ,getLoginToken,getMemberId,getRewardValue
            ,getNo_of_points,getMiBarcode,getPoints_gain,getDateLastVisit,getMontant,getRaison,getGetCardNumber,getCashierId,strDate;
    Bundle extras;
    String ViewOneMemberByCardNumber = "Member/ViewOneMemberByCardNumber?CardNumber=";
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    String SatisfactionVoucherUrl = "Voucher/AddVoucher";
    Spinner Montant,Raison;
    String GetProductListUrl = "DropDown/GiftProductList";
    String GetRaisonListUrl = "DropDown/ReasonList";
    String[]ProductList = null;
    String[]RaisonList = null;
    ArrayList<String>ProductArrayList = new ArrayList<String>();
    ArrayList<String>RaisonArrayList = new ArrayList<String>();
    ProgressDialog dialog;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    View view;
    SharedPreference sharedPreference;
    ArrayList<AllMemberModel> ArraylistGiftProduct = null;
    ArrayList<AllMemberModel> ArraylistReason = null;
    AllMemberModel allMemberModel;
    DatabaseHelper databaseHelper = null;
    String GiftProductList,ReasonList;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satisfaction_voucher);

        sharedPreference = new SharedPreference();
        getCashierId = sharedPreference.getUserName(getApplicationContext());
        extras = getIntent().getExtras();
        if(extras!=null)
        {
            getCardNumber = extras.getString("CardNumber");
            getLoginToken = extras.getString("Token");
            getMiBarcode = extras.getString("mi_barcode");
            Log.e("GetCardNumber :",""+getCardNumber);
            Log.e("GetLoginToken :",""+getLoginToken);
            Log.e("GetMi_Barcode :",""+getMiBarcode);
        }


        phone = (ImageView)findViewById(R.id.phone_un_bon);
        home = (ImageView)findViewById(R.id.home_image_un_bon);
        email = (ImageView)findViewById(R.id.email_image_un_bon);
        Close = (ImageView)findViewById(R.id.cancel_image_un_bon);
        Un_Bon_text = (TextView)findViewById(R.id.Un_Bon_Text);

        Name=(TextView)findViewById(R.id.prenom_voucher);
        LastName=(TextView)findViewById(R.id.nom_voucher);
        Phone_number = (TextView)findViewById(R.id.phone_number_voucher);
        Address = (TextView)findViewById(R.id.address_voucher);
        Zip_Code = (TextView)findViewById(R.id.zipCode_voucher);
        City = (TextView)findViewById(R.id.city_voucher);
        Email = (TextView)findViewById(R.id.email_voucher);
        Total_Points = (TextView)findViewById(R.id.total_point_voucher);
        LastVistDate = (TextView)findViewById(R.id.lastDate_voucher);
        Creer_Un_bon_mettre_btn = (Button)findViewById(R.id.button_Mettre_a_jour_creer_un_bon);
        Montant = (Spinner)findViewById(R.id.spinner_montant_voucher);
        Raison = (Spinner)findViewById(R.id.spinner_raison_voucher);
        BtnOk = (Button)findViewById(R.id.btn_voucher_satisfaction);

        phone.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        home.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        email.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        Close.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        Un_Bon_text.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        builder = new AlertDialog.Builder(SatisfactionVoucher.this);
        view = getLayoutInflater().inflate(R.layout.satisfactionvoucher_create_popup,null);
        Btn_SatisfactionVoucher = (Button)view.findViewById(R.id.btn_satifactionVoucher);
        builder.setView(view);
        alertDialog=builder.create();

        if(Config.CashierMode=="MultipleCashier")
        {
            Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Creer_Un_bon_mettre_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            if(getMiBarcode!=null)
            {
                ViewOneMemberByMiBarCode();
            }
            else if(getCardNumber!=null)
            {
                ViewOneMemberByCardNumber();
            }

            BtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.show();

                    Btn_SatisfactionVoucher.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SatisfactionVoucher();
                            alertDialog.dismiss();
                        }
                    });

                }
            });

        }

        else
        {
            Close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            Creer_Un_bon_mettre_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            if(getMiBarcode!=null)
            {
                ViewOneMemberByMi_BarcodeFromSqliteDatabase();
            }
            else if(getCardNumber!=null)
            {
               ViewOneMemberByCard_NumberFromSqliteDatabase();
            }

            BtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialog.show();

                    Btn_SatisfactionVoucher.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AddSatisfactionVoucherInSqliteDatabse();
                            alertDialog.dismiss();
                        }
                    });

                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Config.CashierMode=="MultipleCashier")
        {
            DropDownProductList();
            DropDownRaisonList();
        }
        else
        {
            DropDownGiftProductListFromSqlite();
            DropDownReasonListFromSqlite();
        }
    }

    public void ViewOneMemberByCardNumber()
    {
        if (InternetUtil.isConnected(getApplicationContext())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByCardNumber).concat(getCardNumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMember :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMember :",""+JO);

                        getName = JO.getString("first_name");
                        getLastName = JO.getString("name");
                        getdate_of_birth = JO.getString("birth_date");
                        //String ModifiedDate_of_birth = getdate_of_birth.re
                        getage = JO.getString("age");
                        int AgeModified = Integer.parseInt(getage);
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getDateLastVisit = JO.getString("last_visit");
                        getGetCardNumber = JO.getString("card_number");




                        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points+"\n"+getDateLastVisit+"\n"+getGetCardNumber);

                        String ModifiedDate = getDateLastVisit.replace("T"," ");
                        Log.e("GetDateOfBirth :",""+ModifiedDate);
                        if(ModifiedDate.contains(" ")){
                            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                            Log.e("GetDateOfBirth :",""+ModifiedDate);
                        }

                        Name.setText(getName);
                        LastName.setText(" "+getLastName);
                        Phone_number.setText(" : "+getphNumber);
                        Zip_Code.setText(getzip_code);
                        City.setText(getcity);
                        Address.setText(" : "+getaddress);
                        Email.setText(" : "+getemail);
                        Total_Points.setText(getNo_of_points+" €");
                        LastVistDate.setText(ModifiedDate);


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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

        else
        {
            Toast.makeText(SatisfactionVoucher.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void ViewOneMemberByMiBarCode()
    {
        if (InternetUtil.isConnected(getApplicationContext())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(getMiBarcode), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMiBarCode :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMemberMiBarCode :",""+JO);

                        getName = JO.getString("first_name");
                        getLastName = JO.getString("name");
                        getdate_of_birth = JO.getString("birth_date");
                        //String ModifiedDate_of_birth = getdate_of_birth.re
                        getage = JO.getString("age");
                        int AgeModified = Integer.parseInt(getage);
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getDateLastVisit = JO.getString("last_visit");
                        getGetCardNumber = JO.getString("card_number");

                        Log.e("GetUserInformationsMiBarcode :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points+"\n"+getGetCardNumber);

                        String ModifiedDate = getDateLastVisit.replace("T"," ");
                        Log.e("GetDateOfBirth :",""+ModifiedDate);
                        if(ModifiedDate.contains(" ")){
                            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                            Log.e("GetDateOfBirth :",""+ModifiedDate);
                        }

                        Name.setText(getName);
                        LastName.setText(" "+getLastName);
                        Phone_number.setText(" : "+getphNumber);
                        Zip_Code.setText(getzip_code);
                        City.setText(getcity);
                        Address.setText(" : "+getaddress);
                        Email.setText(" : "+getemail);
                        Total_Points.setText(getNo_of_points+" €");
                        LastVistDate.setText(ModifiedDate);


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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }

        else
        {
            Toast.makeText(SatisfactionVoucher.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void SatisfactionVoucher()
    {
        if(InternetUtil.isConnected(SatisfactionVoucher.this))
        {
            dialog = new ProgressDialog(SatisfactionVoucher.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
            getMontant = Montant.getSelectedItem().toString();
            getRaison = Raison.getSelectedItem().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(SatisfactionVoucherUrl),
                    new Response.Listener<String>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Log.e("GetJsonResponseVoucher :",""+response);
                            try {
                                JSONObject JO = new JSONObject(response);
                                String JsonResponse = JO.getString("results");
                                Log.e("VoucherSubmitResponse :",""+JsonResponse);

                                if(response.equals("Enter Valid Mandatory Fields"))
                                {
                                    //Toast.makeText(SatisfactionVoucher.this,JsonResponse,Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    //Toast.makeText(SatisfactionVoucher.this,JsonResponse,Toast.LENGTH_SHORT).show();

                                   /* Intent intent = new Intent(SatisfactionVoucher.this,Index_scanner.class);
                                    startActivity(intent);*/
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();

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
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> Params = new HashMap<String, String>();
                    Log.e("CardNumberGetted :",getGetCardNumber);
                    Params.put("gift_product_name",getMontant);
                    Params.put("reason",getRaison);
                    Params.put("card_number",getGetCardNumber);
                    return Params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(SatisfactionVoucher.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void DropDownProductList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(GetProductListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseProductList",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueCivility :",""+Value);

                                    //CivilityList.add("Choisir");
                                    ProductArrayList.add(Value);

                                    ProductList = new String[ProductArrayList.size()];
                                    ProductList = ProductArrayList.toArray(ProductList);

                                    ArrayAdapter<String> arrayAdapter_civilite
                                            = new ArrayAdapter<String>(SatisfactionVoucher.this,android.R.layout.simple_spinner_dropdown_item,ProductList);

                                    Montant.setAdapter(arrayAdapter_civilite);

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Authorization-Token",getLoginToken);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(SatisfactionVoucher.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void DropDownRaisonList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(GetRaisonListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseRaisonList",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueRaison :",""+Value);

                                    //CivilityList.add("Choisir");
                                    RaisonArrayList.add(Value);

                                    RaisonList = new String[RaisonArrayList.size()];
                                    RaisonList = RaisonArrayList.toArray(RaisonList);

                                    ArrayAdapter<String> arrayAdapter_Raison
                                            = new ArrayAdapter<String>(SatisfactionVoucher.this,android.R.layout.simple_spinner_dropdown_item,RaisonList);

                                    Raison.setAdapter(arrayAdapter_Raison);

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
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
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("Authorization-Token",getLoginToken);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(SatisfactionVoucher.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void DropDownGiftProductListFromSqlite()
    {

        ArraylistGiftProduct = new ArrayList<AllMemberModel>();
        allMemberModel = new AllMemberModel();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        ArraylistGiftProduct = databaseHelper.GetGiftProductInformationFromSqlite();

        ProductArrayList.clear();

        for(int i=0;i<ArraylistGiftProduct.size();i++)
        {
            allMemberModel = ArraylistGiftProduct.get(i);
            GiftProductList = allMemberModel.getGift_product_name();

            ProductArrayList.add(GiftProductList);

            ProductList = new String[ProductArrayList.size()];
            ProductList = ProductArrayList.toArray(ProductList);

            ArrayAdapter<String> arrayAdapter_productList
                    = new ArrayAdapter<String>(SatisfactionVoucher.this,android.R.layout.simple_spinner_dropdown_item,ProductList);

            Montant.setAdapter(arrayAdapter_productList);
        }

    }

    public void DropDownReasonListFromSqlite() {
        ArraylistReason = new ArrayList<AllMemberModel>();
        allMemberModel = new AllMemberModel();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        ArraylistReason = databaseHelper.GetReasonInformationFromSqlite();

        ProductArrayList.clear();

        for (int i = 0; i < ArraylistReason.size(); i++) {
            allMemberModel = ArraylistReason.get(i);
            ReasonList = allMemberModel.getReason();

            //CivilityList.add("Choisir");
            RaisonArrayList.add(ReasonList);

            RaisonList = new String[RaisonArrayList.size()];
            RaisonList = RaisonArrayList.toArray(RaisonList);

            ArrayAdapter<String> arrayAdapter_Raison
                    = new ArrayAdapter<String>(SatisfactionVoucher.this,android.R.layout.simple_spinner_dropdown_item,RaisonList);

            Raison.setAdapter(arrayAdapter_Raison);
        }
    }


    public void ViewOneMemberByMi_BarcodeFromSqliteDatabase()
    {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        AllMemberModelByMiBarCodeOnly allMemberModel = new AllMemberModelByMiBarCodeOnly();
        allMemberModel = databaseHelper.GetInformationByBarcode(getMiBarcode);
        getName = allMemberModel.getFirst_name();
        getLastName = allMemberModel.getName();
        getdate_of_birth = allMemberModel.getBirth_date();
        //String ModifiedDate_of_birth = getdate_of_birth.re
        getage = String.valueOf(allMemberModel.getAge());
        getphNumber = allMemberModel.getPhone();
        getzip_code = allMemberModel.getPostal_code();
        getcity = allMemberModel.getCity();
        getaddress = allMemberModel.getAddress_line1();
        getemail = allMemberModel.getEmail();

        Log.e("GetPointsFromSqlite :",""+allMemberModel.getNo_of_points());

        getNo_of_points = String.valueOf(allMemberModel.getNo_of_points());
        getCardNumber =allMemberModel.getCard_number();
        getMemberId = String.valueOf(allMemberModel.getMember_id());
        getDateLastVisit = allMemberModel.getLast_visit();
        String ModifiedDate = getDateLastVisit.replace("T"," ");
        Log.e("GetDateOfBirth :",""+ModifiedDate);
        if(ModifiedDate.contains(" ")){
            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
            Log.e("GetDateOfBirth :",""+ModifiedDate);
        }



        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                +"\n"+getemail+"\n"+getNo_of_points+"\n"+getCardNumber);

        Name.setText(getName);
        LastName.setText(getLastName);
        Phone_number.setText(" : "+getphNumber);
        Zip_Code.setText(getzip_code);
        City.setText(getcity);
        Address.setText(" : "+getaddress);
        Email.setText(" : "+getemail);
        Total_Points.setText(getNo_of_points+" pts");
        LastVistDate.setText(ModifiedDate);
    }

    public void ViewOneMemberByCard_NumberFromSqliteDatabase()
    {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        AllMemberModelByCardNumberOnly allMemberModel = new AllMemberModelByCardNumberOnly();
        allMemberModel = databaseHelper.GetInformationByCardNumber(getCardNumber);
        getName = allMemberModel.getFirst_name();
        getLastName = allMemberModel.getName();
        getdate_of_birth = allMemberModel.getBirth_date();
        //String ModifiedDate_of_birth = getdate_of_birth.re
        getage = String.valueOf(allMemberModel.getAge());
        getphNumber = allMemberModel.getPhone();
        getzip_code = allMemberModel.getPostal_code();
        getcity = allMemberModel.getCity();
        getaddress = allMemberModel.getAddress_line1();
        getemail = allMemberModel.getEmail();
        getNo_of_points = String.valueOf(allMemberModel.getNo_of_points());
        getCardNumber =allMemberModel.getCard_number();
        getMemberId = String.valueOf(allMemberModel.getMember_id());

        getDateLastVisit = allMemberModel.getLast_visit();
        String ModifiedDate = getDateLastVisit.replace("T"," ");
        Log.e("GetDateOfBirth :",""+ModifiedDate);
        if(ModifiedDate.contains(" ")){
            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
            Log.e("GetDateOfBirth :",""+ModifiedDate);
        }



        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                +"\n"+getemail+"\n"+getNo_of_points+"\n"+getCardNumber);

        Name.setText(getName);
        LastName.setText(getLastName);
        Phone_number.setText(" : "+getphNumber);
        Zip_Code.setText(getzip_code);
        City.setText(getcity);
        Address.setText(" : "+getaddress);
        Email.setText(" : "+getemail);
        Total_Points.setText(getNo_of_points+" pts");
        LastVistDate.setText(ModifiedDate);
    }

    public void AddSatisfactionVoucherInSqliteDatabse()
    {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();

        getMontant = Montant.getSelectedItem().toString();
        getRaison = Raison.getSelectedItem().toString();

        AllMemberModel allMemberModel= new AllMemberModel();

        allMemberModel.setCard_number(getCardNumber);
        allMemberModel.setName(getLastName);
        allMemberModel.setFirst_name(getName);
        allMemberModel.setPhone(getphNumber);
        allMemberModel.setEmail(getemail);
        allMemberModel.setGift_product_name(getMontant);
        allMemberModel.setReason(getRaison);
        allMemberModel.setCreated_by(getCashierId);
        allMemberModel.setCreated_on(strDate);
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_window(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_window(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setCashier_id(getCashierId);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertGiftVoucherByCardNumber(allMemberModel);
        finish();
    }

}
