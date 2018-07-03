package com.novo.fidelicia.Membre;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.novo.fidelicia.index.MySingleton;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Activites extends Fragment {

View view;
String getCardnumber,getLoginToken,getMiBarcode,getChiffre_d_Affaires,getPanier_Moyen,getNombre_de_visite
        ,getCrée_le,getBons_d_achat_envoyés,getMontant_Utilisés,getEn_Cours,getNom,getPreNom
        ,getPoints,getLastVisitDate,getCreditPoint,getCarteNumber,gettype_de_bon,getdate_of_expiration,getmontant,getetat,getvd_barcode,getPt;
SharedPreference sharedPreference;
String ActivitiesUrl = "Member/ViewOneMemberActivitiesByCardNumber?CardNumber=";
String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
TextView Chiffre_d_Affaires,Panier_Moyen,Nombre_de_visite,Crée_le,
        Bons_d_achat_envoyés,Montant_Utilisés,En_Cours,PreNom,Nom,PointsPts,Votre_Membre,Nombre_de_points;
ArrayList<ActivityModel> ActivityArrayListPoints = new ArrayList<ActivityModel>();
ArrayList<ActivityVoucherModel> ActivityArrayDePoints = new ArrayList<ActivityVoucherModel>();
ListView ListPointsDate,ListNombreDePoints;
DatabaseHelper databaseHelper = null;
Double getCount;
ArrayList<AllMemberModel>arrayListAllModel = null;
    public Activites() {
        // Required empty public constructor
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_activites, container, false);

        Chiffre_d_Affaires = (TextView)view.findViewById(R.id.Chiffre_d_Affaires);
        Panier_Moyen = (TextView)view.findViewById(R.id.Panier_Moyen);
        Nombre_de_visite = (TextView)view.findViewById(R.id.Nombre_de_visite);
        Crée_le = (TextView)view.findViewById(R.id.Crée_le);
        Bons_d_achat_envoyés = (TextView)view.findViewById(R.id.Bons_d_achat_envoyés);
        Montant_Utilisés = (TextView)view.findViewById(R.id.Montant_Utilisés);
        En_Cours = (TextView)view.findViewById(R.id.En_Cours);
        Nom = (TextView)view.findViewById(R.id.name);
        PreNom = (TextView)view.findViewById(R.id.firstName);
        PointsPts = (TextView)view.findViewById(R.id.point_pts);
        Votre_Membre = (TextView)view.findViewById(R.id.Votre_Membre);
        Nombre_de_points = (TextView)view.findViewById(R.id.Nombre_de_points);
        ListPointsDate = (ListView)view.findViewById(R.id.listView_membre_Points);
        ListNombreDePoints = (ListView)view.findViewById(R.id.listView_nombre_de_point);

        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(getActivity());
        getCardnumber = sharedPreference.getCardNumber(getActivity());
        getMiBarcode = sharedPreference.getMiBarcodeNumber(getActivity());

        Log.e("GetLoginTokenInActivities :",""+getLoginToken);
        Log.e("GetCardNumberInActivities :",""+getCardnumber);
        Log.e("GetMiBarcodeInActivities :",""+getMiBarcode);

        Votre_Membre.setPaintFlags(Votre_Membre.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Nombre_de_points.setPaintFlags(Nombre_de_points.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        return view;

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onResume() {
        super.onResume();
        if(Config.CashierMode=="MultipleCashier")
        {
            if(getCardnumber!=null)
            {
                ViewOneMemberActivitiesByCardNumber();
            }
            else if(getMiBarcode!=null)
            {
                ViewOneMemberByMiBarcode();
            }
        }

        else
        {
           /* if(getCardnumber!=null)
            {
                ViewOneMemberByCardNumberFromSqlitDatabase();
            }
            else if(getMiBarcode!=null)
            {

            }*/
           Log.e("CardNumberGetInSingleCashierPart",""+getCardnumber);
            ViewOneMemberActivitiesByCardNumberFromSqliteDatabase(getCardnumber);
            ViewAllCreditPointsDetailsList(getCardnumber);

        }
    }

    public void ViewOneMemberByMiBarcode() {
        if (InternetUtil.isConnected(getActivity())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(getMiBarcode), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMiBarcode* :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("card_numberInTheBarcodeMethod :",""+JO);

                        getCardnumber = JO.getString("card_number");
                        sharedPreference.saveCardNumber(getActivity(),getCardnumber);

                        Log.e("GetCardNumberInBarCode :",""+getCardnumber);


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

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private void ViewOneMemberActivitiesByCardNumber() {
        if(InternetUtil.isConnected(getActivity()))
        {
            Log.e("GetCardNumberInMethod :",""+getCardnumber);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ActivitiesUrl).concat(getCardnumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("GetJsonResponseActivities :",""+JO);

                        getChiffre_d_Affaires = JO.getString("chiffre_d_affaires");
                        getPanier_Moyen = JO.getString("painer_moyen");
                        getNombre_de_visite = JO.getString("nombre_de_visite");
                        getCrée_le = JO.getString("member_creation_date");
                        getBons_d_achat_envoyés = JO.getString("bons_d_achat_envoyes");
                        getMontant_Utilisés = JO.getString("montant_utlises");
                        getEn_Cours = JO.getString("en_cours");
                        getNom = JO.getString("name");
                        getPreNom = JO.getString("first_name");
                        getPoints = JO.getString("number_of_points");

                        if(getCrée_le.contains(" ")){
                            getCrée_le= getCrée_le.substring(0, getCrée_le.indexOf(" "));
                            Log.e("GetDateOfBirth :",""+getCrée_le);
                        }

                        String FloatConversionChiffre_d_Affaires = getChiffre_d_Affaires;
                        Float convertedFloat=Float.parseFloat(FloatConversionChiffre_d_Affaires);
                        DecimalFormat df = new DecimalFormat("0.00");
                        df.setMaximumFractionDigits(2);
                        FloatConversionChiffre_d_Affaires = df.format(convertedFloat);
                        Chiffre_d_Affaires.setText(FloatConversionChiffre_d_Affaires+" €");

                        String FloatConversionPanier_Moyen = getPanier_Moyen;
                        Float convertedFloat1=Float.parseFloat(FloatConversionPanier_Moyen);
                        DecimalFormat df1 = new DecimalFormat("0.00");
                        df1.setMaximumFractionDigits(2);
                        FloatConversionPanier_Moyen = df1.format(convertedFloat1);
                        Panier_Moyen.setText(FloatConversionPanier_Moyen+" €");

                        Nombre_de_visite.setText(getNombre_de_visite);
                        Crée_le.setText(getCrée_le);

                       /* String FloatConversionBons_d_achat_envoyés = getBons_d_achat_envoyés;
                        Float convertedFloat2=Float.parseFloat(FloatConversionBons_d_achat_envoyés);
                        DecimalFormat df2 = new DecimalFormat("0.00");
                        df2.setMaximumFractionDigits(2);
                        FloatConversionBons_d_achat_envoyés = df2.format(convertedFloat2);*/
                        Bons_d_achat_envoyés.setText(getBons_d_achat_envoyés);

                        String FloatConversionMontant_Utilisés = getMontant_Utilisés;
                        Float convertedFloat3=Float.parseFloat(FloatConversionMontant_Utilisés);
                        DecimalFormat df3 = new DecimalFormat("0.00");
                        df3.setMaximumFractionDigits(2);
                        FloatConversionMontant_Utilisés = df3.format(convertedFloat3);
                        Montant_Utilisés.setText(FloatConversionMontant_Utilisés+" €");

                        En_Cours.setText(getEn_Cours);
                        Nom.setText(getNom);
                        PreNom.setText(getPreNom+" ");
                        PointsPts.setText(getPoints+" pts");

                        JSONArray JA = JO.getJSONArray("Member_Credits_List");
                        for (int i=0;i<JA.length();i++)
                        {
                            JSONObject jsonObject = JA.getJSONObject(i);
                            Log.e("GetJsonArrayResult :",""+jsonObject);

                            getLastVisitDate = jsonObject.getString("date_of_visits").replace("\\\\","");
                            if(getLastVisitDate.contains(" ")){
                                getLastVisitDate= getLastVisitDate.substring(0, getLastVisitDate.indexOf(" "));
                                Log.e("GetLastDate :",""+getLastVisitDate);
                            }
                            getCreditPoint = jsonObject.getString("credits");
                            Log.e("getCreditPoint :",""+getCreditPoint);
                            Log.e("GetLastDateOutSide :",""+getLastVisitDate);

                            ActivityModel activityModel = new ActivityModel();
                            activityModel.setDate(getLastVisitDate);
                            activityModel.setCredit(getCreditPoint);
                            ActivityArrayListPoints.add(activityModel);
                            ActivityAdapter activityAdapter = new ActivityAdapter(getActivity(),ActivityArrayListPoints);
                            ListPointsDate.setAdapter(activityAdapter);
                            activityAdapter.notifyDataSetChanged();
                        }

                        JSONArray JA1 = JO.getJSONArray("Member_Voucher_List");
                        ActivityArrayDePoints.clear();
                        for (int i=0;i<JA1.length();i++)
                        {
                            JSONObject jsonObject = JA1.getJSONObject(i);
                            Log.e("GetJsonArrayResultVoucher :",""+jsonObject);

                            gettype_de_bon = jsonObject.getString("type_de_bon");
                            getvd_barcode = jsonObject.getString("vd_barcode");

                            getdate_of_expiration = jsonObject.getString("date_of_expiration").replace("\\\\","");
                            if(getdate_of_expiration.contains(" ")){
                                getdate_of_expiration= getdate_of_expiration.substring(0, getdate_of_expiration.indexOf(" "));
                                Log.e("GetLastDate :",""+getdate_of_expiration);
                            }

                            getmontant = jsonObject.getString("montant");
                            getetat = jsonObject.getString("etat");

                            Log.e("getAllInformationVoucher :",""+gettype_de_bon+"\n"+getdate_of_expiration+"\n"
                            +getmontant+"\n"+getetat);

                            ActivityVoucherModel activityVoucherModel = new ActivityVoucherModel();
                            activityVoucherModel.setType_de_bon(gettype_de_bon);
                            activityVoucherModel.setDate_of_expiration(getdate_of_expiration);
                            activityVoucherModel.setMontant(getmontant);
                            activityVoucherModel.setEtat(getetat);
                            activityVoucherModel.setVd_barcode(getvd_barcode);
                            ActivityArrayDePoints.add(activityVoucherModel);

                            ActivityVoucherAdapter activityVoucherAdapter = new ActivityVoucherAdapter(getActivity(),ActivityArrayDePoints);
                            ListNombreDePoints.setAdapter(activityVoucherAdapter);
                            activityVoucherAdapter.notifyDataSetChanged();
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
                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    public void ViewOneMemberByCardNumberFromSqlitDatabase() {
        Log.e("GetCardNumberInMethodSqlite :",""+getMiBarcode);
        databaseHelper = new DatabaseHelper(getActivity());
        AllMemberModelByMiBarCodeOnly allMemberModel = new AllMemberModelByMiBarCodeOnly();
        allMemberModel = databaseHelper.GetInformationByBarcode(getMiBarcode);
        getCardnumber = allMemberModel.getCard_number();

        Log.e("GetCardNumberFromSqliteDatabase :",""+getCardnumber);

    }

    @SuppressLint("LongLogTag")
    private void ViewOneMemberActivitiesByCardNumberFromSqliteDatabase(String getCardnumber)
    {
        AllMemberModel allMemberModel = new AllMemberModel();
        AllMemberModel allMemberModel1 = new AllMemberModel();
        AllMemberModelByCardNumberOnly allMemberModel2 = new AllMemberModelByCardNumberOnly();
        databaseHelper = new DatabaseHelper(getActivity());
        allMemberModel = databaseHelper.addAllValuePointsInRewardsPointsTable(getCardnumber);
        allMemberModel1 = databaseHelper.getTaskCount(getCardnumber);
        allMemberModel2 = databaseHelper.GetInformationByCardNumber(getCardnumber);
        getChiffre_d_Affaires = String.valueOf(allMemberModel.getNo_of_points());

        getCount = Double.valueOf(getChiffre_d_Affaires)/Double.valueOf(String.valueOf(allMemberModel1.getTotal_count()));
        Log.e("GetNoOfCount",""+allMemberModel.getTotal_count());
        Log.e("getPanier_Moyen",""+getCount);

        getPanier_Moyen = String.valueOf(getCount);
        String FloatConversionPanier_Moyen = getPanier_Moyen;
        Float convertedFloat1=Float.parseFloat(FloatConversionPanier_Moyen);
        DecimalFormat df1 = new DecimalFormat("0.00");
        df1.setMaximumFractionDigits(2);
        FloatConversionPanier_Moyen = df1.format(convertedFloat1);
        Panier_Moyen.setText(FloatConversionPanier_Moyen+" €");

        String FloatConversionChiffre_d_Affaires = getChiffre_d_Affaires;
        Float convertedFloat=Float.parseFloat(FloatConversionChiffre_d_Affaires);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(2);
        FloatConversionChiffre_d_Affaires = df.format(convertedFloat);
        Chiffre_d_Affaires.setText(FloatConversionChiffre_d_Affaires+" €");

        Nombre_de_visite.setText(String.valueOf(allMemberModel1.getTotal_count()));

        getNom = allMemberModel2.getName();
        getPreNom = allMemberModel2.getFirst_name();
        getPoints = String.valueOf(allMemberModel2.getNo_of_points());

        String FloatConversionPoints = getPoints;
        Float convertedFloat2=Float.parseFloat(FloatConversionPoints);
        DecimalFormat df2 = new DecimalFormat("0.00");
        df1.setMaximumFractionDigits(2);
        FloatConversionPoints = df2.format(convertedFloat2);
        PointsPts.setText(FloatConversionPoints+" pts");

        Nom.setText(" "+getNom);
        PreNom.setText(getPreNom);

        Log.e("GetTotalNoOfPointsFromRewardTable",""+getChiffre_d_Affaires);
    }

    @SuppressLint("LongLogTag")
    private void ViewAllCreditPointsDetailsList(String CardNumber)
    {
        arrayListAllModel = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getActivity());
        arrayListAllModel = databaseHelper.GetAllInformationFromRewardTable(CardNumber);

        for(int i=0;i<arrayListAllModel.size();i++)
        {
            AllMemberModel allMemberModel = arrayListAllModel.get(i);

            getLastVisitDate = allMemberModel.getCreated_on();
            getCreditPoint = String.valueOf(allMemberModel.getNo_of_points());

            if(getLastVisitDate.contains(" ")){
                getLastVisitDate= getLastVisitDate.substring(0, getLastVisitDate.indexOf(" "));
                Log.e("GetLastDate :",""+getLastVisitDate);
            }

            Crée_le.setText(getLastVisitDate);

            ActivityModel activityModel = new ActivityModel();
            activityModel.setDate(getLastVisitDate);
            activityModel.setCredit(getCreditPoint);
            ActivityArrayListPoints.add(activityModel);
            ActivityAdapter activityAdapter = new ActivityAdapter(getActivity(),ActivityArrayListPoints);
            ListPointsDate.setAdapter(activityAdapter);
            activityAdapter.notifyDataSetChanged();

            Log.e("GetAllInformationFromRewardTable",""+getLastVisitDate+"\n"+getCreditPoint);
        }

    }



}
