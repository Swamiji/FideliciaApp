package com.novo.fidelicia.Membre;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.novo.fidelicia.Créditer.Crediter;
import com.novo.fidelicia.R;
import com.novo.fidelicia.Voucher.SatisfactionVoucher;
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.AllMemberModelByCardNumberOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.index.Index_scanner;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Membre extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    Bundle extras;
    String getLoginToken,getCardNumber,getMiBarcode;
    int position;
    Button Update_Ok,Credit_btn,Btn_Un_Bon,Btn_Supprimer,Btn_MemberDelte,Btn_Non;
    String DeleteMemberUrl = "Member/DeleteMember?CardNumber=";
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    String ViewOneMemberByCardNumber = "Member/ViewOneMemberByCardNumber?CardNumber=";

    String getName,getLastName,getdate_of_birth,getage,getphNumber,getzip_code,getcity
            ,getaddress,getemail,getMemberId,getNo_of_points,getDateLastVisit;
    SharedPreference sharedPreference;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    View view;
    TextView Membre;
    DatabaseHelper databaseHelper = null;

    String getCarte,getNom,getPreNom,getAge,getAddressline1,getAddressline2,
            getAddress,getPostalCode,getTelePhone,getEmail,getSoicite,getCivilite,
            getCountry,getCity,getActivity,GetPostalCode,GetStatus,getReplaceBirthDate
            ,getCashierid,getCreated_by,getCreated_on,getUpdated_by,getUpdated_on,getGender,getLasvisit
            ,GetCityThroughZipCode,GetCountry,GetCivility,GetActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membre);
        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(Membre.this);
        Log.e("GetLoginTokenMembre :",""+getLoginToken);
        extras = getIntent().getExtras();

        if(extras!=null)
        {
            getCardNumber = extras.getString("CardNumber");
            getMiBarcode = extras.getString("mi_barcode");

            Log.e("GetCardNumber :",""+getCardNumber);

            ViewOneMemberByCardNumber();
        }
        tabLayout=(TabLayout)findViewById(R.id.Tab_Layout_membre);

        viewPager=(ViewPager)findViewById(R.id.pager);
        Update_Ok = (Button)findViewById(R.id.button_member_update_ok);
        Credit_btn = (Button)findViewById(R.id.Membter_crediter_btn);
        Btn_Un_Bon = (Button)findViewById(R.id.Creer_Un_Bon);
        Btn_Supprimer = (Button)findViewById(R.id.btn_supprimer);
        Membre = (TextView)findViewById(R.id.membre);
        Membre.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        builder = new AlertDialog.Builder(Membre.this);
        view = getLayoutInflater().inflate(R.layout.member_delete_popup,null);
        Btn_MemberDelte = (Button)view.findViewById(R.id.btn_deletemember);
        Btn_Non = (Button)view.findViewById(R.id.non_delete);


        builder.setView(view);
        alertDialog=builder.create();

        Btn_Supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();

                Btn_MemberDelte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(Config.CashierMode == "MultipleCashier") {
                            DeleteMember();
                        }
                        else
                        {
                            inserDeletedMemberInfoIntoLogTable();
                        }
                    }
                });

                Btn_Non.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

            }
        });

        Btn_Un_Bon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Membre.this, SatisfactionVoucher.class);
                intent.putExtra("CardNumber",getCardNumber);
                intent.putExtra("Token",getLoginToken);
                intent.putExtra("mi_barcode",getMiBarcode);
                startActivity(intent);
                //finish();
            }
        });

        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragments(new Activites(),"Activités");
        pagerAdapter.addFragments(new infos(),"Infos");
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

        Update_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Config.CashierMode=="MultipleCashier")
                {
                    infos info = (infos)pagerAdapter.getItem(1);
                    info.UpdateMember();
                }
                else
                {
                    infos info = (infos)pagerAdapter.getItem(1);
                    info.UpdateMemberInSqliteThenToServerByCardNumber();
                }


            }
        });
        Credit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infos info = (infos)pagerAdapter.getItem(1);

                if(Config.CashierMode=="MultipleCashier")
                {

                    info.UpdateMemberInCrediter();

                    Intent intent = new Intent(Membre.this, Crediter.class);
                    intent.putExtra("CardNumber",getCardNumber);
                    intent.putExtra("Token",getLoginToken);
                    intent.putExtra("mi_barcode",getMiBarcode);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    info.UpdateMemberInSqliteThenToServerByCardNumber();
                    Intent intent = new Intent(Membre.this, Crediter.class);
                    intent.putExtra("CardNumber",getCardNumber);
                    intent.putExtra("Token",getLoginToken);
                    intent.putExtra("mi_barcode",getMiBarcode);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

    public void ViewOneMemberByCardNumber() {
        if (InternetUtil.isConnected(getApplicationContext())) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByCardNumber).concat(getCardNumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMembrePage :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMemberMembrePage :", "" + JO);

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
                        getCardNumber = JO.getString("card_number");

                        Log.e("GetUserInformationsMembre :", "" + getName + "\n" + getLastName + "\n" + getdate_of_birth + "\n" +
                                getage + "\n" + getphNumber + "\n" + getzip_code + "\n" + getcity + "\n" + getaddress
                                + "\n" + getemail + "\n" + getMemberId + "\n" + getNo_of_points + "\n" + getDateLastVisit + "\n" + getCardNumber);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
        else
            {
            Toast.makeText(Membre.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }


    public void DeleteMember()
    {
        if(InternetUtil.isConnected(Membre.this)){
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, Config.Url.concat(DeleteMemberUrl).concat(getCardNumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject JO = new JSONObject(response);
                        String JsonResponse = JO.getString("results");

                        Log.e("GetJsonResponseMembreDelete :",""+JsonResponse);

                            Toast.makeText(Membre.this,JsonResponse,Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Membre.this,Index_scanner.class);
                            startActivity(intent);
                            finish();

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
            Toast.makeText(Membre.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void inserDeletedMemberInfoIntoLogTable()
    {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        AllMemberModelByCardNumberOnly allMemberModelByCardNumberOnly = new AllMemberModelByCardNumberOnly();
        AllMemberModel allMemberModel = new AllMemberModel();

        allMemberModelByCardNumberOnly = databaseHelper.GetInformationByCardNumber(getCardNumber);
        getCarte = allMemberModelByCardNumberOnly.getCard_number();
        getCashierid = allMemberModelByCardNumberOnly.getCashier_id();
        getCivilite = allMemberModelByCardNumberOnly.getCivility();
        getNom = allMemberModelByCardNumberOnly.getName();
        getPreNom = allMemberModelByCardNumberOnly.getFirst_name();
        getdate_of_birth = allMemberModelByCardNumberOnly.getBirth_date();
        getAge = String.valueOf(allMemberModelByCardNumberOnly.getAge());
        getSoicite = allMemberModelByCardNumberOnly.getSociety();
        getActivity = allMemberModelByCardNumberOnly.getActivity();
        getAddressline1 = allMemberModelByCardNumberOnly.getAddress_line1();
        getAddressline2 = allMemberModelByCardNumberOnly.getAddress_line2();
        getCity = allMemberModelByCardNumberOnly.getCity();
        getCountry = allMemberModelByCardNumberOnly.getCountry();
        getPostalCode = allMemberModelByCardNumberOnly.getPostal_code();
        getTelePhone = allMemberModelByCardNumberOnly.getPhone();
        getEmail = allMemberModelByCardNumberOnly.getEmail();
        getCreated_by = allMemberModelByCardNumberOnly.getCreated_by();
        getCreated_on = allMemberModelByCardNumberOnly.getCreated_on();
        getUpdated_by = allMemberModelByCardNumberOnly.getUpdated_by();
        getUpdated_on = allMemberModelByCardNumberOnly.getUpdated_on();
        getNo_of_points = String.valueOf(allMemberModelByCardNumberOnly.getNo_of_points());
        getMiBarcode = allMemberModelByCardNumberOnly.getMi_barcode();
        getGender = allMemberModelByCardNumberOnly.getGender();
        getLasvisit = allMemberModelByCardNumberOnly.getLast_visit();


        allMemberModel.setCard_number(getCarte);
        allMemberModel.setCashier_id(getCashierid);
        allMemberModel.setCivility(getCivilite);
        allMemberModel.setName(getNom);
        allMemberModel.setFirst_name(getPreNom);
        allMemberModel.setBirth_date(getReplaceBirthDate);
        allMemberModel.setAge(Integer.parseInt(getAge));
        allMemberModel.setGender(getGender);
        allMemberModel.setSociety(getSoicite);
        allMemberModel.setActivity(getActivity);
        allMemberModel.setAddress_line1(getAddressline1);
        allMemberModel.setAddress_line2(getAddressline2);
        allMemberModel.setPostal_code(getPostalCode);
        allMemberModel.setCity(getCity);
        allMemberModel.setCountry(getCountry);
        allMemberModel.setPhone(getTelePhone);
        allMemberModel.setEmail(getEmail);
        allMemberModel.setLast_visit(getLasvisit);
        allMemberModel.setCreated_by(getCreated_by);
        allMemberModel.setCreated_on(getCreated_on);
        allMemberModel.setUpdated_by(getUpdated_by);
        allMemberModel.setUpdated_on(getUpdated_on);
        allMemberModel.setNo_of_points(Double.parseDouble(getNo_of_points));
        allMemberModel.setMi_barcode(getMiBarcode);
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper.insertDeletedMemberInfoIntoLogTable(allMemberModel);

        boolean check = databaseHelper.GetInformationCardNumberFromLogTableToCheckMember(getCardNumber);

        if(check == true)
        {
            allMemberModel.setCard_number(getCardNumber);
            databaseHelper.DeleteMemberFromMemberInfoTable(allMemberModel);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Data Not Exists..!!!!",Toast.LENGTH_SHORT).show();
        }

    }





}
