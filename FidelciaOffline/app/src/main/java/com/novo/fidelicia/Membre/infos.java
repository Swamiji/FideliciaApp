package com.novo.fidelicia.Membre;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.novo.fidelicia.CalculationAge.AgeCalculation;
import com.novo.fidelicia.R;
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.AllMemberModelByCardNumberOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.index.MySingleton;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class infos extends Fragment{

    TextView nouveau_membro,getVille,date_of_birth,Age;
    Spinner spinner_ville_membre,spinner_pays_membre,spinner_profession_membre,spinner_civilite_membre;
    Button Update_Ok;
    View view,view1;
    String getCardnumber,getLoginToken,getGetMiBarcode;
    SharedPreference sharedPreference;
    String CivilityListUrl = "DropDown/CivilityList";
    String CountryListUrl = "DropDown/CountryList";
    String CityListUrl = "DropDown/CityList?postal_code=";
    String ActivityListUrl = "DropDown/ActivityList";
    String ViewOneMemberByCardNumber = "Member/ViewOneMemberByCardNumber?CardNumber=";
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    String UpdateMemberUrl = "Member/UpdateMember";
    ArrayList<String> CivilityList = new ArrayList<String>();
    ArrayList<String> CountryList = new ArrayList<String>();
    ArrayList<String> CityList = new ArrayList<String>();
    ArrayList<String> ActivityList = new ArrayList<String>();
    ArrayList<String> CityListText = new ArrayList<String>();
    LinearLayout linear_dateofBirth;

    String Ville[]=null;
    String Pays[]=null;
    String Profession[]= null;
    String Civilite[]=null;


    EditText Carte,Nom,PreNom,Address,PostalCode,TelePhone,Email,Soicite;

    String getName,getLastName,getdate_of_birth
            ,getage,getphNumber,getzip_code,getcity,getaddress,getemail
            ,getMemberId,getRewardValue,getNo_of_points,getMiBarcode,getSociety,getactivity,getcivilite,getpays,getCashier,strDate;

    String getCarte,getNom,getPreNom,getDateOfBirth,getAge,
            getAddress,getPostalCode,getTelePhone,getEmail,getSoicite,getCivilite,getCountry,getCity,
            getActivity,getCarteNumber,getCurrentDate,GetPostalCode,GetCityThroughZipCode,GetCountry,GetCivility,GetActivity;

    ProgressDialog dialog;
    Context context;
    private static final int DATE_START_DIALOG_ID = 0;
    private int startYear = 1950;
    private int startMonth = 6;
    private int startDay = 15;
    private String month = "jan";
    private AgeCalculation age = null;
    DatabaseHelper databaseHelper = null;
    ArrayList<AllMemberModel>allMemberModelsArrayListThroughZip = null;
    ArrayList<AllMemberModel>allMemberModelsArrayList = null;


    public infos() {
        // Required empty public constructor
    }


    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_infos, container, false);

        age = new AgeCalculation();

        //Update_Ok = (Button)inflater.inflate(R.layout.activity_membre, container, false).findViewById(R.id.button_member_update_ok);


        //Spinner Section.............................................////////////////////////////////////////
        spinner_ville_membre =(Spinner)view.findViewById(R.id.spinner_ville_membre);
        spinner_pays_membre = (Spinner)view.findViewById(R.id.spinner_Pays_membre);
        spinner_profession_membre = (Spinner)view.findViewById(R.id.spinner_profession_membre);
        spinner_civilite_membre = (Spinner)view.findViewById(R.id.spinner_civilite_membre);

        //EditText Section..............................///////////////////////////////////////////////
        Carte = (EditText)view.findViewById(R.id.carte_membre);
        Nom = (EditText)view.findViewById(R.id.nom_membre);
        PreNom = (EditText)view.findViewById(R.id.Prenom_membre);
        date_of_birth = (TextView) view.findViewById(R.id.date_de_naissance_membre);
        Age = (TextView) view.findViewById(R.id.Age_membre);
        Address = (EditText)view.findViewById(R.id.address_membre);
        PostalCode = (EditText)view.findViewById(R.id.postal_code_membre);
        TelePhone = (EditText)view.findViewById(R.id.telephone_membre);
        Email = (EditText)view.findViewById(R.id.Email_membre);
        Soicite = (EditText)view.findViewById(R.id.societe_membre);
        linear_dateofBirth = (LinearLayout)view.findViewById(R.id.linear_dateofBirth);

        Carte.setInputType(InputType.TYPE_CLASS_PHONE);
        PostalCode.setInputType(InputType.TYPE_CLASS_PHONE);
        TelePhone.setInputType(InputType.TYPE_CLASS_PHONE);

        // Fixed the screen Orientation......
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        sharedPreference = new SharedPreference();

        getLoginToken = sharedPreference.getLoginToken(getActivity());
        getCardnumber = sharedPreference.getCardNumber(getActivity());
        getGetMiBarcode = sharedPreference.getMiBarcodeNumber(getActivity());
        getCashier = sharedPreference.getUserName(getActivity());

        Log.e("GetLoginTokenInInfos",""+getLoginToken);
        Log.e("GetCardNumberInInfosThroughSharePreference :",""+getCardnumber);
        Log.e("GetCardNumberInInfosThroughJson",""+getCardnumber);
        Log.e("GetMiBarcodeInInfos",""+getGetMiBarcode);
        Log.e("GetCashierName :",""+getCashier);


        context = getActivity();

        linear_dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(DATE_START_DIALOG_ID).show();
            }
        });

        getCurrentDate = age.getCurrentDate();
        if(getCurrentDate!=null)
        {
            Log.e("GetCurrentDate :",""+getCurrentDate);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Config.CashierMode=="MultipleCashier")
        {
            DropDownCivilityList();
            DropDownCountryList();
            DropDownCityList();
            DropDownActivityList();

            if(getGetMiBarcode!=null)
            {
                ViewOneMemberByMiBarcode();
            }
            else if(getCardnumber!=null)
            {
                ViewOneMemberByCardNumber();
            }
        }

        else
        {
            DropDownCityListFromSqliteDatabase();
            DropDownCountryListFromSqliteDatabase();
            DropDownCivilityListFromSqliteDatabase();
            DropDownActivityListFromSqliteDatabase();

            if(getCardnumber!=null)
            {
                //ViewOneMemberByMiBarcode();
                ViewOneMemberByCardNumberFromSqlitDatabase();
            }
            else if(getGetMiBarcode!=null)
            {
                //ViewOneMemberByCardNumber();

            }
        }

    }

    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case DATE_START_DIALOG_ID :
                return new DatePickerDialog(getActivity(),mDateSetListener,startYear,startMonth,startDay);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            startYear = year;
            startMonth = month;
            startDay = dayOfMonth;

            Log.e("DateCalender :",""+startYear+"\n"+startMonth+"\n"+startDay);
            age.setDateOfBirth(startYear,startMonth,startDay);
            String mDate=convertDate(convertToMillis(dayOfMonth,(startMonth),startYear));
            date_of_birth.setText(mDate);
            date_of_birth.setTextColor(Color.BLACK);
            calculateAge();
        }
    };
    private void calculateAge() {
        age.calculateYear();
        age.calculateMonth();
        age.calculateDay();
        Age.setText(age.getResult());
        Age.setTextColor(Color.BLACK);
    }

    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }

    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;
    }

    public void DropDownCivilityList() {
        if(InternetUtil.isConnected(getActivity()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CivilityListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseCivility",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        CivilityList.clear();
                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueCivility :",""+Value);

                                    //CivilityList.add("Choisir");
                                    CivilityList.add(Value);

                                    Civilite = new String[CivilityList.size()];
                                    Civilite = CivilityList.toArray(Civilite);
                                    //Civilite[0] = "-----Choisir-----";

                                    ArrayAdapter<String>arrayAdapter_civilite
                                            = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Civilite);

                                    spinner_civilite_membre.setAdapter(arrayAdapter_civilite);
                                    String compareValue = getcivilite;
                                    Log.e("CompareValueCivility :",""+compareValue);
                                    if (compareValue != null) {
                                        int spinnerPosition = arrayAdapter_civilite.getPosition(compareValue);
                                        spinner_civilite_membre.setSelection(spinnerPosition);
                                    }

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
                    map.put("Content-Type","application/json");
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCountryList() {
        if(InternetUtil.isConnected(getActivity()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CountryListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseCountry",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        CountryList.clear();
                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueCountry :",""+Value);
                                    CountryList.add(Value);
                                    Pays = new String[CountryList.size()];
                                    Pays = CountryList.toArray(Pays);
                                    //Pays[0] = "-----Choisir-----";
                                    ArrayAdapter<String>arrayAdapter_pays
                                            = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Pays);

                                    spinner_pays_membre.setAdapter(arrayAdapter_pays);
                                    String compareValue = getpays;
                                    if (compareValue != null) {
                                        int spinnerPosition = arrayAdapter_pays.getPosition(compareValue);
                                        spinner_pays_membre.setSelection(spinnerPosition);
                                    }
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
                    map.put("Content-Type","application/json");
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCityList() {
        if(InternetUtil.isConnected(getActivity()))
        {
            PostalCode.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    GetPostalCode = s.toString();
                    Log.e("CityListUrl :",CityListUrl.concat(GetPostalCode));


                    Log.e("GetPostalCode :",""+getPostalCode);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CityListUrl).concat(GetPostalCode), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.e("JsonResponseCity",""+response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("results");
                                CityList.clear();

                                if(jsonArray!=null){
                                    for (int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject JO = jsonArray.getJSONObject(i);
                                        try{
                                            String Value = JO.getString("Value");
                                            Log.e("GetValueCity :",""+Value);
                                            CityList.add(Value);
                                            Ville = new String[CityList.size()];
                                            Ville = CityList.toArray(Ville);
                                            ArrayAdapter<String>arrayAdapter_Ville
                                                    = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Ville);

                                            spinner_ville_membre.setAdapter(arrayAdapter_Ville);

                                            String compareValue = getcity;
                                            if (compareValue != null) {
                                                int spinnerPosition = arrayAdapter_Ville.getPosition(compareValue);
                                                spinner_ville_membre.setSelection(spinnerPosition);
                                            }

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
                            map.put("Content-Type","application/json");
                            return map;
                        }
                    };
                    MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


                }
            });

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CityListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseCity",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        CityList.clear();

                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueCity :",""+Value);
                                    CityList.add(Value);
                                    Ville = new String[CityList.size()];
                                    Ville = CityList.toArray(Ville);
                                    if(getCity.equals(""))
                                    {
                                        Ville[0] = " ";
                                    }
                                    else
                                    {
                                        ArrayAdapter<String>arrayAdapter_Ville
                                                = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Ville);

                                        spinner_ville_membre.setAdapter(arrayAdapter_Ville);
                                    }

                                   /* String compareValue = getcity;
                                    if (compareValue != null) {
                                        int spinnerPosition = arrayAdapter_Ville.getPosition(compareValue);
                                        spinner_ville_membre.setSelection(spinnerPosition);
                                    }*/
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
                    map.put("Content-Type","application/json");
                    return map;
                }
            };
            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void DropDownActivityList() {
        if(InternetUtil.isConnected(getActivity()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ActivityListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseAcitivity",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        ActivityList.clear();
                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueActivity :",""+Value);
                                    ActivityList.add(Value);
                                    Profession = new String[ActivityList.size()];
                                    Profession = ActivityList.toArray(Profession);
                                    //Profession[0] = "-----Choisir-----";
                                    ArrayAdapter<String>arrayAdapter_profession
                                            = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Profession);

                                    spinner_profession_membre.setAdapter(arrayAdapter_profession);
                                    String compareValue = getactivity;
                                    if (compareValue != null) {
                                        int spinnerPosition = arrayAdapter_profession.getPosition(compareValue);
                                        spinner_profession_membre.setSelection(spinnerPosition);
                                    }

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
                    map.put("Content-Type","application/json");
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getActivity());
        //getCity = spinner_ville_membre.getSelectedItem().toString();

        PostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                GetPostalCode = s.toString();
                Log.e("CityListUrl :",CityListUrl.concat(GetPostalCode));

                allMemberModelsArrayListThroughZip = new ArrayList<AllMemberModel>();
                databaseHelper = new DatabaseHelper(getActivity());
                allMemberModelsArrayListThroughZip = databaseHelper.GetAllCityListThroughZIPCodeFromSqliteDatabase(GetPostalCode);
                CityList.clear();

                for(int i=0;i<allMemberModelsArrayListThroughZip.size();i++)
                {
                    AllMemberModel allMemberModel = allMemberModelsArrayListThroughZip.get(i);
                    GetCityThroughZipCode = allMemberModel.getCity();

                    Log.e("GetValueCity :",""+GetCityThroughZipCode);
                    CityList.add(GetCityThroughZipCode);
                    Ville = new String[CityList.size()];
                    Ville = CityList.toArray(Ville);
                   /* if(getCity.equals(""))
                    {
                        Ville[0] = " ";
                    }
                    else
                    {*/
                        ArrayAdapter<String>arrayAdapter_Ville
                                = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Ville);

                        spinner_ville_membre.setAdapter(arrayAdapter_Ville);

                        String compareValue = getcity;
                        if (compareValue != null) {
                            int spinnerPosition = arrayAdapter_Ville.getPosition(compareValue);
                            spinner_ville_membre.setSelection(spinnerPosition);
                        }
                    }

                //}

            }
        });

        allMemberModelsArrayList = databaseHelper.GetAllCityListFromSqliteDatabase();

        for(int i=0;i<allMemberModelsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
            GetCityThroughZipCode = allMemberModel.getCity();

            Log.e("GetValueCityOutSide :",""+GetCityThroughZipCode);
            CityList.add(GetCityThroughZipCode);
            Ville = new String[CityList.size()];
            Ville = CityList.toArray(Ville);
           /* if(getCity.equals(""))
            {
                Ville[0] = " ";
            }
            else
            {*/
                ArrayAdapter<String>arrayAdapter_Ville
                        = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Ville);

                spinner_ville_membre.setAdapter(arrayAdapter_Ville);

               /* String compareValue = getcity;
                if (compareValue != null) {
                    int spinnerPosition = arrayAdapter_Ville.getPosition(compareValue);
                    spinner_ville_membre.setSelection(spinnerPosition);
                }*/
            }

       // }

    }

    public void DropDownCountryListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getActivity());
        allMemberModelsArrayList = databaseHelper.GetCountryListFromSqliteDatabase();


        for(int i=0;i<allMemberModelsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
            GetCountry = allMemberModel.getCountry();

            Log.e("GetValueCountry :",""+GetCountry);
            CountryList.add(GetCountry);
            Pays = new String[CountryList.size()];
            Pays = CountryList.toArray(Pays);
            //Pays[0] = "-----Choisir-----";
            ArrayAdapter<String>arrayAdapter_pays
                    = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Pays);

            spinner_pays_membre.setAdapter(arrayAdapter_pays);
            String compareValue = getpays;
            if (compareValue != null) {
                int spinnerPosition = arrayAdapter_pays.getPosition(compareValue);
                spinner_pays_membre.setSelection(spinnerPosition);
            }
        }

    }

    public void DropDownCivilityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getActivity());
        allMemberModelsArrayList = databaseHelper.GetCivilityListFromSqliteDatabase();

        AllMemberModelByCardNumberOnly allMemberModel1 = new AllMemberModelByCardNumberOnly();
        allMemberModel1 = databaseHelper.GetInformationByCardNumber(getCardnumber);

        CivilityList.clear();
        for(int i=0;i<allMemberModelsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
            GetCivility = allMemberModel.getValue();

            Log.e("GetValueCivility :",""+GetCivility);

            //CivilityList.add("Choisir");
            CivilityList.add(GetCivility);

            Civilite = new String[CivilityList.size()];
            Civilite = CivilityList.toArray(Civilite);
            //Civilite[0] = "-----Choisir-----";

            ArrayAdapter<String>arrayAdapter_civilite
                    = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Civilite);

            spinner_civilite_membre.setAdapter(arrayAdapter_civilite);
            getcivilite = allMemberModel1.getCivility();
            String compareValue = getcivilite;
           /* Log.e("CompareValueCivility :",""+compareValue);*/
            if (compareValue != null) {
                int spinnerPosition = arrayAdapter_civilite.getPosition(compareValue);
                spinner_civilite_membre.setSelection(spinnerPosition);
            }
        }
    }

    public void DropDownActivityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getActivity());
        allMemberModelsArrayList = databaseHelper.GetActivityListFromSqliteDatabase();

        AllMemberModelByCardNumberOnly allMemberModel1 = new AllMemberModelByCardNumberOnly();
        allMemberModel1 = databaseHelper.GetInformationByCardNumber(getCardnumber);

        ActivityList.clear();
        for(int i=0;i<allMemberModelsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
            GetActivity = allMemberModel.getValue();

            Log.e("GetValueActivity :",""+GetActivity);
            ActivityList.add(GetActivity);
            Profession = new String[ActivityList.size()];
            Profession = ActivityList.toArray(Profession);
            //Profession[0] = "-----Choisir-----";
            ArrayAdapter<String>arrayAdapter_profession
                    = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,Profession);

            spinner_profession_membre.setAdapter(arrayAdapter_profession);
            getactivity = allMemberModel1.getActivity();
            String compareValue = getactivity;
            Log.e("CompareValueActivity",""+compareValue);
            if (compareValue != null) {
                int spinnerPosition = arrayAdapter_profession.getPosition(compareValue);
                spinner_profession_membre.setSelection(spinnerPosition);
            }
        }
    }

    public void ViewOneMemberByCardNumber() {
        if (InternetUtil.isConnected(getActivity())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByCardNumber).concat(getCardnumber), new Response.Listener<String>() {
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
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getMiBarcode = JO.getString("mi_barcode");
                        getSociety = JO.getString("society");
                        getcivilite = JO.getString("civility");
                        getactivity = JO.getString("activity");
                        getpays = JO.getString("country");

                        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points+"\n"+getMiBarcode+"\n"+getSociety+"\n"
                        +getcivilite+"\n"+getactivity+"\n"+getpays);

                        String ModifiedDate = getdate_of_birth.replace("T"," ");
                        Log.e("GetDateOfBirth :",""+ModifiedDate);
                        if(ModifiedDate.contains(" ")){
                            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                            Date date = simpleDateFormat.parse(ModifiedDate);
                            Log.e("GetDateOfBirth :",""+ModifiedDate);
                            Log.e("DateGetted :",""+date.getTime());
                        }

                        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
                        Date date = format1.parse(ModifiedDate);
                        String convertedDate = format2.format(date);
                        Log.e("ConvertedDate :",""+convertedDate);

                        if(getage.equals("0"))
                        {
                           /* getage = getage.replace("0","");
                            getdate_of_birth = getdate_of_birth.replace("01/01/1950","");
                            Log.e("GetReplacementAge :",""+getage);
                            Log.e("GetReplacementBirth :",""+getdate_of_birth);*/

                            date_of_birth.setTextColor(Color.WHITE);
                            date_of_birth.setText(getdate_of_birth);
                            Age.setTextColor(Color.WHITE);
                            Age.setText(getage);
                        }
                        else
                        {
                            date_of_birth.setText(convertedDate);
                            Age.setText(getage);
                        }
                        PreNom.setText(getName);
                        Nom.setText(getLastName);
                        TelePhone.setText(getphNumber);
                        PostalCode.setText(getzip_code);
                        Address.setText(getaddress);
                        Email.setText(getemail);
                        Carte.setText(getCardnumber);
                        Soicite.setText(getSociety);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
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
            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        }

        else
        {
            Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void ViewOneMemberByMiBarcode() {
        if (InternetUtil.isConnected(getActivity())){

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(getGetMiBarcode), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMemberMiBarcode :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMemberMiBarCode :",""+JO);

                        getName = JO.getString("first_name");
                        getLastName = JO.getString("name");
                        getdate_of_birth = JO.getString("birth_date");
                        //String ModifiedDate_of_birth = getdate_of_birth.re
                        getage = JO.getString("age");
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getMiBarcode = JO.getString("mi_barcode");
                        getCarteNumber = JO.getString("card_number");
                        getSociety = JO.getString("society");
                        getSociety = JO.getString("society");
                        getcivilite = JO.getString("civility");
                        getactivity = JO.getString("activity");
                        getpays = JO.getString("country");

                        Log.e("GetUserInformations :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                                +"\n"+getemail+"\n"+getMemberId+"\n"+getNo_of_points+"\n"+getMiBarcode+"\n"+getSociety+"\n"
                                +getcivilite+"\n"+getactivity+"\n"+getpays);

                        String ModifiedDate = getdate_of_birth.replace("T"," ");
                        Log.e("GetDateOfBirth :",""+ModifiedDate);
                        if(ModifiedDate.contains(" ")){
                            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                            Log.e("GetDateOfBirth :",""+ModifiedDate);
                        }

                        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
                        Date date = format1.parse(ModifiedDate);
                        String convertedDate = format2.format(date);
                        Log.e("ConvertedDate :",""+convertedDate);

                        if(getage.equals("0"))
                        {
                            /*getage = getage.replace("0","");
                            getdate_of_birth = getdate_of_birth.replace("01/01/1950","");
                            Log.e("GetReplacementAge :",""+getage);
                            Log.e("GetReplacementBirth :",""+getdate_of_birth);*/
                            date_of_birth.setTextColor(Color.WHITE);
                            date_of_birth.setText(getdate_of_birth);
                            Age.setTextColor(Color.WHITE);
                            Age.setText(getage);
                        }
                        else
                        {
                            date_of_birth.setText(convertedDate);
                            Age.setText(getage);
                        }

                        PreNom.setText(getName);
                        Nom.setText(getLastName);
                        TelePhone.setText(getphNumber);
                        PostalCode.setText(getzip_code);
                        Address.setText(getaddress);
                        Email.setText(getemail);
                        Carte.setText(getCarteNumber);
                        Soicite.setText(getSociety);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
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

            MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
        else
        {
            Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void UpdateMember() {
    if(InternetUtil.isConnected(getActivity()))
    {
        getCivilite = spinner_civilite_membre.getSelectedItem().toString();
        getCity = spinner_ville_membre.getSelectedItem().toString();
        getCountry = spinner_pays_membre.getSelectedItem().toString();
        getActivity = spinner_profession_membre.getSelectedItem().toString();
        getCarte = Carte.getText().toString();
        getNom = Nom.getText().toString();
        getPreNom = PreNom.getText().toString();
        getdate_of_birth = date_of_birth.getText().toString();
        getAge = Age.getText().toString();
        getAddress = Address.getText().toString();
        getPostalCode = PostalCode.getText().toString();
        getTelePhone = TelePhone.getText().toString();
        getEmail = Email.getText().toString();
        getSoicite = Soicite.getText().toString();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(true);
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Config.Url.concat(UpdateMemberUrl), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                Log.e("JsonResponse", "" + response);

                try {
                    JSONObject JO = new JSONObject(response);
                    String ResponseObject = JO.getString("results");
                    Log.e("ResponseObject", "" + ResponseObject);

                    if (ResponseObject.equals("Enter Valid Mandatory Fields")) {
                        //Toast.makeText(getActivity(), ResponseObject, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //Toast.makeText(getActivity(), ResponseObject, Toast.LENGTH_SHORT).show();
                        getActivity().finish();
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
            @SuppressLint("LongLogTag")
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Log.e("GetMemberIdInParams",""+getMiBarcode);
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("member_id",getMemberId);
                params.put("card_number",getCarte);
                params.put("civility",getCivilite);
                params.put("name",getNom);
                params.put("first_name",getPreNom);
                if(getdate_of_birth.equals(""))
                {
                    String ConcateDate = startDay +"-"+ month +"-"+ startYear;
                    params.put("birth_date",ConcateDate);
                }
                else
                {
                    params.put("birth_date",getdate_of_birth);
                }

                params.put("age",getAge);
                params.put("address_line1",getAddress);
                params.put("postal_code",getPostalCode);
                params.put("city",getCity);
                params.put("country",getCountry);
                params.put("phone",getTelePhone);
                params.put("email",getEmail);
                params.put("society",getSoicite);
                params.put("activity",getActivity);
                params.put("mi_barcode",getMiBarcode);
                params.put("cashier_id",getCashier);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization-Token", getLoginToken);
                return headers;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    else
    {
        Toast.makeText(getActivity(), "Please check Internet Connection", Toast.LENGTH_LONG).show();
    }
}

    public void UpdateMemberInCrediter() {
        if(InternetUtil.isConnected(getActivity()))
        {
            getCivilite = spinner_civilite_membre.getSelectedItem().toString();
            getCity = spinner_ville_membre.getSelectedItem().toString();
            getCountry = spinner_pays_membre.getSelectedItem().toString();
            getActivity = spinner_profession_membre.getSelectedItem().toString();
            getCarte = Carte.getText().toString();
            getNom = Nom.getText().toString();
            getPreNom = PreNom.getText().toString();
            getdate_of_birth = date_of_birth.getText().toString();
            getAge = Age.getText().toString();
            getAddress = Address.getText().toString();
            getPostalCode = PostalCode.getText().toString();
            getTelePhone = TelePhone.getText().toString();
            getEmail = Email.getText().toString();
            getSoicite = Soicite.getText().toString();

            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, Config.Url.concat(UpdateMemberUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    Log.e("JsonResponse", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        String ResponseObject = JO.getString("results");
                        Log.e("ResponseObject", "" + ResponseObject);

                        if (ResponseObject.equals("Enter Valid Mandatory Fields")) {
                            //Toast.makeText(getActivity(), ResponseObject, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Toast.makeText(getActivity(), ResponseObject, Toast.LENGTH_SHORT).show();
                            //getActivity().finish();
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
                    HashMap<String,String> params = new HashMap<String, String>();
                    params.put("member_id",getMemberId);
                    params.put("card_number",getCarte);
                    params.put("civility",getCivilite);
                    params.put("name",getNom);
                    params.put("first_name",getPreNom);
                    params.put("birth_date",getdate_of_birth);
                    params.put("age",getAge);
                    params.put("address_line1",getAddress);
                    params.put("postal_code",getPostalCode);
                    params.put("city",getCity);
                    params.put("country",getCountry);
                    params.put("phone",getTelePhone);
                    params.put("email",getEmail);
                    params.put("society",getSoicite);
                    params.put("activity",getActivity);
                    params.put("mi_barcode",getMiBarcode);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization-Token", getLoginToken);
                    return headers;
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
        Log.e("GetCardNumberInMethodSqlite :",""+getCardnumber);
        databaseHelper = new DatabaseHelper(getActivity());
        AllMemberModelByCardNumberOnly allMemberModel = new AllMemberModelByCardNumberOnly();
        allMemberModel = databaseHelper.GetInformationByCardNumber(getCardnumber);
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
        getpays = allMemberModel.getCountry();
        getcivilite = allMemberModel.getCivility();
        getactivity = allMemberModel.getActivity();
        //getCardNumber =allMemberModel.getCard_number();



        Log.e("GetUserInformationsfromSqlite :",""+getName+"\n"+getLastName+"\n"+getdate_of_birth+"\n"+
                getage+"\n"+getphNumber+"\n"+getzip_code+"\n"+getcity+"\n"+getaddress
                +"\n"+getemail+"\n"+getNo_of_points+"\n"+getcivilite+"\n"+getactivity);

      /*  String ModifiedDate = getdate_of_birth.replace("T"," ");
        Log.e("GetDateOfBirth :",""+ModifiedDate);
        if(ModifiedDate.contains(" ")){
            ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            Date date = null;

            try {
                date = simpleDateFormat.parse(ModifiedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("GetDateOfBirth :",""+ModifiedDate);
            Log.e("DateGetted :",""+date.getTime());
        }

        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = null;

        try {
            date = format1.parse(ModifiedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String convertedDate = format2.format(date);
        Log.e("ConvertedDate :",""+convertedDate);*/

        if(getage.equals("0"))
        {
                           /* getage = getage.replace("0","");
                            getdate_of_birth = getdate_of_birth.replace("01/01/1950","");
                            Log.e("GetReplacementAge :",""+getage);
                            Log.e("GetReplacementBirth :",""+getdate_of_birth);*/

            date_of_birth.setTextColor(Color.WHITE);
            date_of_birth.setText(getdate_of_birth);
            Age.setTextColor(Color.WHITE);
            Age.setText(getage);
        }
        else
        {
            date_of_birth.setText(getdate_of_birth);
            Age.setText(getage);
        }
        PreNom.setText(getName);
        Nom.setText(getLastName);
        TelePhone.setText(getphNumber);
        PostalCode.setText(getzip_code);
        Address.setText(getaddress);
        Email.setText(getemail);
        Carte.setText(getCardnumber);
        Soicite.setText(getSociety);
    }

    public void UpdateMemberInSqliteThenToServerByCardNumber() {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel= new AllMemberModel();

        getCivilite = spinner_civilite_membre.getSelectedItem().toString();
        getCity = spinner_ville_membre.getSelectedItem().toString();
        getCountry = spinner_pays_membre.getSelectedItem().toString();
        getActivity = spinner_profession_membre.getSelectedItem().toString();
        getCarte = Carte.getText().toString();
        getNom = Nom.getText().toString();
        getPreNom = PreNom.getText().toString();
        getdate_of_birth = date_of_birth.getText().toString();
        getAge = Age.getText().toString();
        getAddress = Address.getText().toString();
        getPostalCode = PostalCode.getText().toString();
        getTelePhone = TelePhone.getText().toString();
        getEmail = Email.getText().toString();
        getSoicite = Soicite.getText().toString();

        String ReplaceDateOfBirth = getdate_of_birth.replace("\\\\","");

        allMemberModel.setCivility(getCivilite);
        allMemberModel.setCity(getCity);
        allMemberModel.setCountry(getCountry);
        allMemberModel.setActivity(getActivity);
        allMemberModel.setCard_number(getCarte);
        allMemberModel.setName(getNom);
        allMemberModel.setFirst_name(getPreNom);
        allMemberModel.setBirth_date(ReplaceDateOfBirth);
        allMemberModel.setAge(Integer.parseInt(getAge));
        allMemberModel.setAddress_line1(getAddress);
        allMemberModel.setPostal_code(getPostalCode);
        allMemberModel.setPhone(getTelePhone);
        allMemberModel.setEmail(getEmail);
        allMemberModel.setSociety(getSoicite);
        allMemberModel.setUpdated_by(getCashier);
        allMemberModel.setUpdated_on(strDate);
        allMemberModel.setLast_visit(strDate);
        allMemberModel.setUp_window(false);
        //allMemberModel.setNo_of_points(Double.parseDouble(getNo_of_points));
       /* allMemberModel.setCr_window(true);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setDl_window(true);*/
        allMemberModel.setUpdate_status("Checked");
        //allMemberModelsArrayList.add(allMemberModel);
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.UpdateInformationByCard(allMemberModel);
        getActivity().finish();
    }

    public void UpdateMemberInSqliteThenToServerByMiBarcode() {
        strDate = new CurrentTimeGet().getCurrentTime();
        Log.e("GetCurrentTime :",""+strDate);
        //allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel= new AllMemberModel();

        getCivilite = spinner_civilite_membre.getSelectedItem().toString();
        getCity = spinner_ville_membre.getSelectedItem().toString();
        getCountry = spinner_pays_membre.getSelectedItem().toString();
        getActivity = spinner_profession_membre.getSelectedItem().toString();
        getCarte = Carte.getText().toString();
        getNom = Nom.getText().toString();
        getPreNom = PreNom.getText().toString();
        getdate_of_birth = date_of_birth.getText().toString();
        getAge = Age.getText().toString();
        getAddress = Address.getText().toString();
        getPostalCode = PostalCode.getText().toString();
        getTelePhone = TelePhone.getText().toString();
        getEmail = Email.getText().toString();
        getSoicite = Soicite.getText().toString();

        String ReplaceDateOfBirth = getdate_of_birth.replace("\\\\","");

        allMemberModel.setCivility(getCivilite);
        allMemberModel.setCity(getCity);
        allMemberModel.setCountry(getCountry);
        allMemberModel.setActivity(getActivity);
        allMemberModel.setMi_barcode(getGetMiBarcode);
        allMemberModel.setName(getNom);
        allMemberModel.setFirst_name(getPreNom);
        allMemberModel.setBirth_date(ReplaceDateOfBirth);
        allMemberModel.setAge(Integer.parseInt(getAge));
        allMemberModel.setAddress_line1(getAddress);
        allMemberModel.setPostal_code(getPostalCode);
        allMemberModel.setPhone(getTelePhone);
        allMemberModel.setEmail(getEmail);
        allMemberModel.setSociety(getSoicite);
        allMemberModel.setUpdated_by(getCashier);
        allMemberModel.setUpdated_on(strDate);
        allMemberModel.setUp_window(false);
        //allMemberModel.setNo_of_points(Double.parseDouble(getNo_of_points));
       /* allMemberModel.setCr_window(true);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setDl_window(true);*/
        allMemberModel.setUpdate_status("Checked");
        //allMemberModelsArrayList.add(allMemberModel);
        databaseHelper = new DatabaseHelper(getActivity());
        databaseHelper.UpdateInformationMiBarcode(allMemberModel);
        getActivity().finish();
    }




}
