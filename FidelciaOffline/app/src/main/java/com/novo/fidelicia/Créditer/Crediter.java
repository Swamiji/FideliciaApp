package com.novo.fidelicia.Créditer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.novo.fidelicia.Membre.Membre;
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

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Crediter extends AppCompatActivity implements View.OnClickListener{
    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9 ,buttonDots,buttonClear,buttonOk,buttonHipent
            ,buttonCreditPoint100ptsPopupConfirm,buttonCreditPoint100ptsPopupNON,button_Mettre_a_jour;
    Bundle extras;
    String getCardNumber,getName,getLastName,getdate_of_birth
            ,getage,getphNumber,getzip_code,getcity,getaddress,getemail
            ,getLoginToken,getMemberId,getRewardValue,getNo_of_points,
            getMiBarcode,getPoints_gain,getDateLastVisit,getRewardValueFlot,getPonts,strDate,getCashierId,getCreatedOn,convertedDate,getPoints;

    Double GetAmount;

    EditText editText;
    TextView Name,LastName,date_of_birth,age,phNumber
            ,zip_code,city,address,email,No_Of_Points,member_name,member_lastName,creditPoints;
    String ViewOneMemberByCardNumber = "Member/ViewOneMemberByCardNumber?CardNumber=";
    String AddMemberRewardUrl = "MemberReward/AddMemberReward";
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    ProgressDialog dialog;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    ListView LastVisitList;
    View view;
    ArrayList<CrediterModel>LastVisitModel=new ArrayList<>();
    ImageView close_btn;
    SharedPreference sharedPreference;
    float amount1,amount;
    DatabaseHelper databaseHelper;
    ArrayList<AllMemberModelByCardNumberOnly> arrayListReward = null;
    ArrayList<AllMemberModelByMiBarCodeOnly> arrayListRewardByMiBarcode = null;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crediter);

        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(Crediter.this);
        getCashierId = sharedPreference.getUserName(Crediter.this);
        Log.e("GetLoginToken :",""+getLoginToken);

        extras = getIntent().getExtras();
        if(extras!=null)
        {
            getCardNumber = extras.getString("CardNumber");
            //getLoginToken = extras.getString("Token");
            getMiBarcode = extras.getString("mi_barcode");
            Log.e("GetCardNumber :",""+getCardNumber);
            Log.e("GetMi_Barcode :",""+getMiBarcode);

        }
        ///Button Section........////////////////////////////////////

        button0 = (Button) findViewById(R.id.buttonZero);
        button1 = (Button) findViewById(R.id.buttonOne);
        button2 = (Button) findViewById(R.id.buttonTwo);
        button3 = (Button) findViewById(R.id.buttonThree);
        button4 = (Button) findViewById(R.id.buttonFour);
        button5 = (Button) findViewById(R.id.buttonFive);
        button6 = (Button) findViewById(R.id.buttonSix);
        button7 = (Button) findViewById(R.id.buttonSeven);
        button8 = (Button) findViewById(R.id.buttonEight);
        button9 = (Button) findViewById(R.id.buttonNine);
        buttonDots = (Button) findViewById(R.id.buttonDots);
        buttonClear = (Button) findViewById(R.id.buttonSupper);
        buttonHipent = (Button) findViewById(R.id.buttonHipent);
        buttonOk = (Button) findViewById(R.id.buttonOK);
        button_Mettre_a_jour = (Button)findViewById(R.id.button_Mettre_à_jour);
        close_btn = (ImageView) findViewById(R.id.close_credit);

        ///EditText Section........./////////////////////////////////////

         editText = (EditText) findViewById(R.id.editText_value);
         getRewardValueFlot = editText.getText().toString();

         /////......TextView Section..................///////////////////////////
        Name = (TextView)findViewById(R.id.name_credit);
        LastName = (TextView)findViewById(R.id.lastName_credit);
        date_of_birth = (TextView)findViewById(R.id.date_of_birth_credit);
        age = (TextView)findViewById(R.id.age_credit);
        phNumber = (TextView)findViewById(R.id.phone_number_credit);
        zip_code = (TextView)findViewById(R.id.zip_code_credit);
        city = (TextView)findViewById(R.id.city_credit);
        address = (TextView)findViewById(R.id.address_credit);
        email = (TextView)findViewById(R.id.email_credit);
        No_Of_Points = (TextView)findViewById(R.id.no_of_points);

        ///ListView Section........////////////////////////////////////
        LastVisitList = (ListView)findViewById(R.id.list_5_Dernières_visites);

        ////.............. Button Click Handle.............////////////

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonDots.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
        buttonHipent.setOnClickListener(this);

        /// Method Called...../////////////////////////////

        builder = new AlertDialog.Builder(Crediter.this);
        view = getLayoutInflater().inflate(R.layout.creditpoint_100pts_popup,null);
        buttonCreditPoint100ptsPopupConfirm = (Button)view.findViewById(R.id.oui_je_confirm);
        buttonCreditPoint100ptsPopupNON = (Button)view.findViewById(R.id.non);
        member_name = (TextView)view.findViewById(R.id.member_name);
        member_lastName = (TextView)view.findViewById(R.id.member_last_name);
        creditPoints = (TextView)view.findViewById(R.id.credit_points_popup);
        builder.setView(view);
        alertDialog=builder.create();

        if(Config.CashierMode=="MultipleCashier")
        {
            if(getMiBarcode!=null)
            {
                ViewOneMemberByMi_Barcode();
            }
            else if(getCardNumber!=null)
            {
                ViewOneMemberByCardNumber();
            }


            button_Mettre_a_jour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(Crediter.this, Membre.class);
                    intent.putExtra("Token",getLoginToken);
                    intent.putExtra("CardNumber",getCardNumber);
                    intent.putExtra("mi_barcode",getMiBarcode);
                    startActivity(intent);

                }
            });


            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getNo_of_points = editText.getText().toString();
                    float value = Float.parseFloat(getNo_of_points);
                    if(value>100.0)
                    {

                        creditPoints.setText(value+" points"+" ?");

                        alertDialog.show();
                        buttonCreditPoint100ptsPopupConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddMemberReward();
                                finish();
                                alertDialog.dismiss();

                            }
                        });

                        buttonCreditPoint100ptsPopupNON.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    }
                    else
                    {
                        AddMemberReward();
                        finish();
                    }
                }
            });

            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    amount1 = Float.parseFloat(getPonts);
                    No_Of_Points.setText(amount1+" pts");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {

                    Log.e("GetTotalAmountInOncreate :",""+getPonts);

                    amount1 = Float.parseFloat(getPonts);

                    Log.e("GetTotalAmountFloat :",""+amount1);

                    String Value = s.toString();

                    try {

                        amount = Float.parseFloat(Value);

                        amount1 = amount1+amount;

                        Log.e("NewAmountValueGet :",""+amount1);

                        String FinalValue = Float.toString(amount1);

                        String FloatConversion= FinalValue;
                        Float convertedFloat3=Float.parseFloat(FloatConversion);
                        DecimalFormat df3 = new DecimalFormat("0.00");
                        df3.setMaximumFractionDigits(2);
                        FloatConversion = df3.format(convertedFloat3);

                        No_Of_Points.setText(FloatConversion+" pts");
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        }

        //////////////////////////// Else Part For Single Cashier Functionality........////////////////////////////////

        else
        {
            if(getMiBarcode!=null)
            {
                try
                {
                    ViewOneMemberByMi_BarcodeFromSqliteDatabase();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            else if(getCardNumber!=null)
            {
                 ViewOneMemberByCard_NumberFromSqliteDatabase();
            }

            button_Mettre_a_jour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    Intent intent = new Intent(Crediter.this, Membre.class);
                    intent.putExtra("Token",getLoginToken);
                    intent.putExtra("CardNumber",getCardNumber);
                    intent.putExtra("mi_barcode",getMiBarcode);
                    sharedPreference.saveCardNumber(getApplicationContext(),getCardNumber);
                    sharedPreference.saveMiBarcodeNumber(getApplicationContext(),getMiBarcode);
                    startActivity(intent);

                }
            });

            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getNo_of_points = editText.getText().toString();
                    float value = Float.parseFloat(getNo_of_points);
                    if(value>100.0)
                    {
                       /* Log.e("GetCreditPointsOnCreate :",""+getPonts);
                        float points1 = Float.parseFloat(getPonts);
                        Log.e("GetFloatPoints :",""+points1);

                        try{

                            value = value+points1;

                            Log.e("GetFloatPoints :",""+value);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        String FinalPoints = Float.toString(value);*/

                        creditPoints.setText(value+" points"+" ?");

                        alertDialog.show();
                        buttonCreditPoint100ptsPopupConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(getCardNumber!=null)
                                {
                                    UpdateMemberCreditPointsInSqliteThenToServer();
                                }
                                else if(getMiBarcode!=null)
                                {
                                    UpdateMemberCreditPointsInSqliteThenToServerThroughBarcode();
                                }

                                AddRewardPointsInSqliteDatabse();
                                finish();
                                alertDialog.dismiss();

                            }
                        });

                        buttonCreditPoint100ptsPopupNON.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                    }

                    else
                    {
                        if(getCardNumber!=null)
                        {
                            UpdateMemberCreditPointsInSqliteThenToServer();
                        }
                        else if(getMiBarcode!=null)
                        {
                            UpdateMemberCreditPointsInSqliteThenToServerThroughBarcode();
                        }
                        AddRewardPointsInSqliteDatabse();
                        finish();
                    }

                }
            });

            close_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    amount1 = Float.parseFloat(getPonts);

               /* Log.e("GetTotalAmountFloat :",""+amount1);

                String getAmountValue = Float.toString(amount1);
                String FloatConversion= getAmountValue;
                Float convertedFloat3=Float.parseFloat(FloatConversion);
                DecimalFormat df3 = new DecimalFormat("0.00");
                df3.setMaximumFractionDigits(2);
                FloatConversion = df3.format(convertedFloat3);*/

                    No_Of_Points.setText(amount1+" pts");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Log.e("GetTotalAmountInOncreate :",""+getPonts);

                    amount1 = Float.parseFloat(getPonts);

                    Log.e("GetTotalAmountFloat :",""+amount1);

                    String Value = s.toString();

                    try {

                        amount = Float.parseFloat(Value);

                        amount1 = amount1+amount;

                        Log.e("NewAmountValueGet :",""+amount1);

                        String FinalValue = Float.toString(amount1);

                        String FloatConversion= FinalValue;
                        Float convertedFloat3=Float.parseFloat(FloatConversion);
                        DecimalFormat df3 = new DecimalFormat("0.00");
                        df3.setMaximumFractionDigits(2);
                        FloatConversion = df3.format(convertedFloat3);

                        No_Of_Points.setText(FloatConversion+" pts");

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                }
            });

        }

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.buttonZero :
                editText.setText(editText.getText()+"0");
                break;

            case R.id.buttonOne:
                editText.setText(editText.getText()+"1");
                break;

            case R.id.buttonTwo:
                editText.setText(editText.getText()+"2");
                break;

            case R.id.buttonThree:
                editText.setText(editText.getText()+"3");
                break;

            case R.id.buttonFour:
                editText.setText(editText.getText()+"4");
                break;

            case R.id.buttonFive:
                editText.setText(editText.getText()+"5");
                break;

            case R.id.buttonSix:
                editText.setText(editText.getText()+"6");
                break;

            case R.id.buttonSeven:
                editText.setText(editText.getText()+"7");
                break;

            case R.id.buttonEight:
                editText.setText(editText.getText()+"8");
                break;

            case R.id.buttonNine:
                editText.setText(editText.getText()+"9");
                break;

            case R.id.buttonDots:
                editText.setText(editText.getText()+".");
                break;

            case R.id.buttonHipent:
                editText.setText(editText.getText()+"-");
                break;

            case R.id.buttonSupper:
                String text = editText.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    //String newText = text.substring(1, text.length()); //delete from left
                    //or
                    String newText1 = text.substring(0, text.length() - 1); //delete from right
                   /* et.setText(newText);
                    et.setSelection(newText.length());*/
                    //or
                    editText.setText(newText1);
                    editText.setSelection(newText1.length());
                }
                break;
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
                        getPonts = JO.getString("no_of_points");

                        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points);

                        String ModifiedDate = getdate_of_birth.replace("T"," ");
                        Log.e("GetDateOfBirth :",""+ModifiedDate);
                        if(ModifiedDate.contains(" ")){
                            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                            Log.e("GetDateOfBirth :",""+ModifiedDate);
                        }

                        if(getage.equals("0"))
                        {
                            date_of_birth.setVisibility(View.GONE);
                            age.setVisibility(View.GONE);
                        }
                        else
                        {
                            date_of_birth.setText("Né le "+getdate_of_birth);
                            age.setText(AgeModified+" ans");
                            date_of_birth.setVisibility(View.VISIBLE);
                            age.setVisibility(View.VISIBLE);
                        }
                        Name.setText(getName);
                        LastName.setText(getLastName);
                        phNumber.setText(" : "+getphNumber);
                        zip_code.setText(getzip_code);
                        city.setText(getcity);
                        address.setText(" : "+getaddress);
                        email.setText(" : "+getemail);
                        String FloatConversion= getPonts;
                        Float convertedFloat3=Float.parseFloat(FloatConversion);
                        DecimalFormat df3 = new DecimalFormat("0.00");
                        df3.setMaximumFractionDigits(2);
                        FloatConversion = df3.format(convertedFloat3);
                        No_Of_Points.setText(FloatConversion+" pts");
                        member_name.setText(getName);
                        member_lastName.setText(getLastName+" de");

                        JSONArray JA = JO.getJSONArray("MemberLastVisitList");
                        Log.e("GetResultOfArray",""+JA);
                        for (int i=0;i<JA.length();i++)
                        {
                          JSONObject jsonObject = JA.getJSONObject(i);
                          getPoints_gain=jsonObject.getString("points_gained");
                          getDateLastVisit = jsonObject.getString("created_on");

                          CrediterModel crediterModel = new CrediterModel();

                          crediterModel.setLast_visit_date(jsonObject.getString("created_on"));
                          crediterModel.setPoints(jsonObject.getString("points_gained"));
                          LastVisitModel.add(crediterModel);

                          CrediterAdapter crediterAdapter = new CrediterAdapter(Crediter.this,LastVisitModel);
                          LastVisitList.setAdapter(crediterAdapter);
                          crediterAdapter.notifyDataSetChanged();

                            Log.e("GetResultOfArray :",""+getPoints_gain+"\n"+getDateLastVisit);
                        }


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
            Toast.makeText(Crediter.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void ViewOneMemberByMi_Barcode()
    {
        if (InternetUtil.isConnected(getApplicationContext())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(getMiBarcode), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMiBarcode :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMemberMiBarcode :",""+JO);

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
                        getPonts = JO.getString("no_of_points");
                        getCardNumber = JO.getString("card_number");



                        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points+"\n"+getCardNumber);

                        if(getage.equals("0"))
                        {
                            date_of_birth.setVisibility(View.GONE);
                            age.setVisibility(View.GONE);
                        }
                        else
                        {
                            date_of_birth.setText("Né le "+getdate_of_birth);
                            age.setText(AgeModified+" ans");
                            date_of_birth.setVisibility(View.VISIBLE);
                            age.setVisibility(View.VISIBLE);
                        }
                        Name.setText(getName);
                        LastName.setText(getLastName);
                        phNumber.setText(" : "+getphNumber);
                        zip_code.setText(getzip_code);
                        city.setText(getcity);
                        address.setText(" : "+getaddress);
                        email.setText(" : "+getemail);
                        No_Of_Points.setText(getPonts+" pts");
                        member_name.setText(getName);
                        member_lastName.setText(getLastName+" de");

                        JSONArray JA = JO.getJSONArray("MemberLastVisitList");
                        Log.e("GetResultOfArray",""+JA);
                        for (int i=0;i<JA.length();i++)
                        {
                            JSONObject jsonObject = JA.getJSONObject(i);
                            getPoints_gain=jsonObject.getString("points_gained");
                            getDateLastVisit = jsonObject.getString("created_on");

                            CrediterModel crediterModel = new CrediterModel();

                            crediterModel.setLast_visit_date(jsonObject.getString("created_on"));
                            crediterModel.setPoints(jsonObject.getString("points_gained"));
                            LastVisitModel.add(crediterModel);

                            CrediterAdapter crediterAdapter = new CrediterAdapter(Crediter.this,LastVisitModel);
                            LastVisitList.setAdapter(crediterAdapter);
                            crediterAdapter.notifyDataSetChanged();

                            Log.e("GetResultOfArray :",""+getPoints_gain+"\n"+getDateLastVisit);
                        }


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
            Toast.makeText(Crediter.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void AddMemberReward()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {
           /* dialog = new ProgressDialog(Crediter.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();*/
            getRewardValue = editText.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(AddMemberRewardUrl)
                    , new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //dialog.dismiss();

                    Log.e("JsonResponse", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        String JsonResponse = JO.getString("results");
                        Log.e("JsonResponseReward :",""+JsonResponse);

                        if(JsonResponse.equals("Enter Valid Mandatory Fields") ||(JsonResponse.equals("Phone No already exists!")))
                        {
                            //Toast.makeText(Crediter.this,JsonResponse,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Toast.makeText(Crediter.this,JsonResponse,Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //dialog.dismiss();
                    if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Log.e("ErrrrorNetwork", "" + error);
                        Toast.makeText(Crediter.this,"Something may be wrong.....",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Log.e("ErrrrorServer", "" + error);
                        Toast.makeText(Crediter.this,"Something may be wrong.....",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof AuthFailureError) {
                        Log.e("ErrrrorAuthFail", "" + error);
                        Toast.makeText(Crediter.this,"Something may be wrong.....",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Log.e("ErrrrorPrase", "" + error);
                        Toast.makeText(Crediter.this,"Something may be wrong.....",Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Log.e("ErrrrorTimeOut", "" + error);
                        Toast.makeText(Crediter.this,"Something may be wrong.....",Toast.LENGTH_SHORT).show();
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> Params = new HashMap<String, String>();
                    Log.e("MemberIdGet :",""+getMemberId);
                    Log.e("points_gainedGet :",""+getRewardValue);
                    Log.e("CardNumberGet :",""+getCardNumber);
                    Params.put("member_id",getMemberId);
                    Params.put("card_number",getCardNumber);
                    Params.put("points_gained",getRewardValue);
                    return Params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> Headers = new HashMap<String, String>();
                    Log.e("LoginTokenGet :",""+getLoginToken);
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        else
        {
            Toast.makeText(Crediter.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
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

        getPonts = String.valueOf(allMemberModel.getNo_of_points());
        getCardNumber =allMemberModel.getCard_number();
        getMemberId = String.valueOf(allMemberModel.getMember_id());



        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                +"\n"+getemail+"\n"+getNo_of_points+"\n"+getCardNumber);

        if(getage.equals("0"))
        {
            date_of_birth.setVisibility(View.GONE);
            age.setVisibility(View.GONE);
        }
        else
        {
            date_of_birth.setText("Né le "+getdate_of_birth);
            age.setText(getage+" ans");
            date_of_birth.setVisibility(View.VISIBLE);
            age.setVisibility(View.VISIBLE);
        }
        Name.setText(getName);
        LastName.setText(getLastName);
        phNumber.setText(" : "+getphNumber);
        zip_code.setText(getzip_code);
        city.setText(getcity);
        address.setText(" : "+getaddress);
        email.setText(" : "+getemail);
        No_Of_Points.setText(getPonts+" pts");
        member_name.setText(getName);
        member_lastName.setText(getLastName+" de");

        arrayListRewardByMiBarcode = new ArrayList<AllMemberModelByMiBarCodeOnly>();
        arrayListRewardByMiBarcode = databaseHelper.GetInformationReturnArraylistCreditPointRewardTableLastFiveDataByMiBarcode(getMiBarcode);

        for(int i=0;i<arrayListRewardByMiBarcode.size();i++)
        {
            AllMemberModelByMiBarCodeOnly allMemberModel1 = arrayListRewardByMiBarcode.get(i);
            getPoints = String.valueOf(allMemberModel1.getNo_of_points());
            getCreatedOn = allMemberModel1.getCreated_on();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            Date date = null;
            try {
                date = simpleDateFormat1.parse(getCreatedOn);
                convertedDate = simpleDateFormat2.format(date);
                Log.e("ConvertedDate :",""+convertedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CrediterModel crediterModel = new CrediterModel();

            crediterModel.setLast_visit_date(convertedDate);
            crediterModel.setPoints(getPoints);
            LastVisitModel.add(crediterModel);

            CrediterAdapter crediterAdapter = new CrediterAdapter(Crediter.this,LastVisitModel);
            LastVisitList.setAdapter(crediterAdapter);
            crediterAdapter.notifyDataSetChanged();

        }


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
        getPonts = String.valueOf(allMemberModel.getNo_of_points());
        getMiBarcode = allMemberModel.getMi_barcode();
        GetAmount = allMemberModel.getNo_of_points();
        getMemberId = String.valueOf(allMemberModel.getMember_id());

        Log.e("GetNoOfPoints :",""+GetAmount);

        //getCardNumber =allMemberModel.getCard_number();



        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                +"\n"+getemail+"\n"+getPonts+"\n"+getMemberId);

        if(getage.equals("0"))
        {
            date_of_birth.setVisibility(View.GONE);
            age.setVisibility(View.GONE);
        }
        else
        {
            date_of_birth.setText("Né le "+getdate_of_birth);
            age.setText(getage+" ans");
            date_of_birth.setVisibility(View.VISIBLE);
            age.setVisibility(View.VISIBLE);
        }
        Name.setText(getName);
        LastName.setText(getLastName);
        phNumber.setText(" : "+getphNumber);
        zip_code.setText(getzip_code);
        city.setText(getcity);
        address.setText(" : "+getaddress);
        email.setText(" : "+getemail);
        No_Of_Points.setText(getPonts+" pts");
        member_name.setText(getName);
        member_lastName.setText(getLastName+" de");

        arrayListReward = new ArrayList<AllMemberModelByCardNumberOnly>();
        arrayListReward = databaseHelper.GetInformationReturnArraylistCreditPointRewardTableLastFiveData(getCardNumber);

        for(int i=0;i<arrayListReward.size();i++)
        {
            AllMemberModelByCardNumberOnly allMemberModel1 = arrayListReward.get(i);
            getPoints = String.valueOf(allMemberModel1.getNo_of_points());
            getCreatedOn = allMemberModel1.getCreated_on();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            Date date = null;
            try {
                date = simpleDateFormat1.parse(getCreatedOn);
                convertedDate = simpleDateFormat2.format(date);
                Log.e("ConvertedDate :",""+convertedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            CrediterModel crediterModel = new CrediterModel();

            crediterModel.setLast_visit_date(convertedDate);
            crediterModel.setPoints(getPoints);
            LastVisitModel.add(crediterModel);

            CrediterAdapter crediterAdapter = new CrediterAdapter(Crediter.this,LastVisitModel);
            LastVisitList.setAdapter(crediterAdapter);
            crediterAdapter.notifyDataSetChanged();

        }
    }

    public void UpdateMemberCreditPointsInSqliteThenToServer()
    {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel= new AllMemberModel();
        allMemberModel.setUp_window(false);
        allMemberModel.setCard_number(getCardNumber);
        allMemberModel.setUpdated_by(getCashierId);
        allMemberModel.setUpdated_on(strDate);
        allMemberModel.setUpdate_status("Checked");
        allMemberModel.setNo_of_points(Double.parseDouble(String.valueOf(amount1)));

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.UpdateInformationByCardFromCrediter(allMemberModel);
        finish();
    }

    public void UpdateMemberCreditPointsInSqliteThenToServerThroughBarcode()
    {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel= new AllMemberModel();
        allMemberModel.setUp_window(false);
        allMemberModel.setMi_barcode(getMiBarcode);
        allMemberModel.setUpdated_by(getCashierId);
        allMemberModel.setUpdated_on(strDate);
        allMemberModel.setUpdate_status("Checked");
        allMemberModel.setNo_of_points(Double.parseDouble(String.valueOf(amount1)));

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.UpdateInformationByMiBarcodeFromCrediter(allMemberModel);
        finish();
    }

    public void AddRewardPointsInSqliteDatabse()
    {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel= new AllMemberModel();
        allMemberModel.setMember_id(Integer.parseInt(getMemberId));
        allMemberModel.setCashier_id(getCashierId);
        allMemberModel.setCard_number(getCardNumber);
        allMemberModel.setMi_barcode(getMiBarcode);
        allMemberModel.setCreated_by(getCashierId);
        allMemberModel.setUpdated_on("");
        allMemberModel.setUpdated_by("");
        allMemberModel.setComment("");
        allMemberModel.setCreated_on(strDate);
        allMemberModel.setReward_type("CREDIT");
        allMemberModel.setNo_of_points(Double.parseDouble(String.valueOf(amount)));
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_window(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_window(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertPointsRewardByCardNumber(allMemberModel);
        finish();
    }


}
