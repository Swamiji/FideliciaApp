package com.novo.fidelicia.new_member;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.novo.fidelicia.CalculationAge.AgeCalculation;
import com.novo.fidelicia.Créditer.Crediter;
import com.novo.fidelicia.R;
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Nouveau_Membre extends AppCompatActivity {

    TextView nouveau_membro,date_of_birth,Age;
    Spinner spinner_ville,spinner_pays,spinner_profession,spinner_civilite;
    EditText Carte,Nom,PreNom,Address,PostalCode,TelePhone,Email,Soicite;
    Button CréditerBtn,AnnularBtn;
    String Ville[]=null;
    String Pays[]=null;
    String Profession[]= null;
    String Civilite[]=null;
    String CivilityListUrl = "DropDown/CivilityList";
    String CountryListUrl = "DropDown/CountryList";
    String CityListUrl = "DropDown/CityList?postal_code=";
    String ActivityListUrl = "DropDown/ActivityList";
    String AddNewMemberUrl = "Member/AddMember";
    ArrayList<String> CivilityList = new ArrayList<String>();
    ArrayList<String> CountryList = new ArrayList<String>();
    ArrayList<String> CityList = new ArrayList<String>();
    ArrayList<String> ActivityList = new ArrayList<String>();
    SharedPreference sharedPreference;
    String getLoginToken,Mi_Barcode,getCurrentDate,getUserName,strDate;
    Bundle extra;
    String getCarte,getNom,getPreNom,getdate_of_birth,getAge,
            getAddress,getPostalCode,getTelePhone,getEmail,getSoicite,getCivilite,
            getCountry,getCity,getActivity,GetPostalCode,GetStatus,getReplaceBirthDate
            ,getCashierid,GetCityThroughZipCode,GetCountry,GetCivility,GetActivity,CurrentTimeDate;
    ProgressDialog dialog;
    Calendar myCalendar;
    private int mDay;
    private int mMonth;
    private int mYear;
    private static final int DATE_START_DIALOG_ID = 0;
    private int startYear = 1900;
    private int startMonth = 6;
    private int startDay = 15;
    private AgeCalculation age = null;
    private Calendar end;
    private int endYear;
    private int endMonth;
    private int endDay;
    LinearLayout dateSelection;
    DatabaseHelper databaseHelper = null;
    ArrayList<AllMemberModel>allMemberModelsArrayList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau__membre);

        age = new AgeCalculation();

        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(getApplicationContext());
        getUserName = sharedPreference.getUserName(getApplicationContext());
        getCashierid = sharedPreference.getUserName(getApplicationContext());
        Log.e("GetLoginToken :",""+getLoginToken);
        Log.e("GetUserName :",""+getUserName);
        Log.e("GetCashierid :",""+getCashierid);

        extra = getIntent().getExtras();
        if(extra!=null)
        {
            Mi_Barcode = extra.getString("mi_Barcode");
            Log.e("Mi_Barcode",""+Mi_Barcode);
        }

        //EditText Section..............................///////////////////////////////////////////////
        Carte = (EditText)findViewById(R.id.carte_newMembre);
        Nom = (EditText)findViewById(R.id.nom_newMember);
        PreNom = (EditText)findViewById(R.id.PrenomNewMember);
        date_of_birth = (TextView)findViewById(R.id.date_de_naissance_NewMember);
        Age = (TextView)findViewById(R.id.Age_NewMember);
        Address = (EditText)findViewById(R.id.address_NewMember);
        PostalCode = (EditText)findViewById(R.id.postal_code_NewMember);
        TelePhone = (EditText)findViewById(R.id.telephone_NewMember);
        Email = (EditText)findViewById(R.id.Email_NewMember);
        Soicite = (EditText)findViewById(R.id.Societe_NewMember);
        dateSelection = (LinearLayout)findViewById(R.id.date_of_birth_selection);
        /*TelePhone.setInputType(InputType.TYPE_CLASS_PHONE);
        Carte.setInputType(InputType.TYPE_CLASS_PHONE);
        PostalCode.setInputType(InputType.TYPE_CLASS_PHONE);*/


        getCurrentDate = age.getCurrentDate();
        if(getCurrentDate!=null)
        {
            Log.e("GetCurrentDate :",""+getCurrentDate);
        }

        // Button Section............////////////////////////////
        CréditerBtn = (Button)findViewById(R.id.button_Créditer);
        AnnularBtn = (Button)findViewById(R.id.btn_annular_Nouveau_Membre);

        //Spinner Section.............................................////////////////////////////////////////
        spinner_ville =(Spinner)findViewById(R.id.spinner_ville);
        spinner_pays = (Spinner)findViewById(R.id.spinner_Pays);
        spinner_profession = (Spinner)findViewById(R.id.spinner_profession);
        spinner_civilite = (Spinner)findViewById(R.id.spinner_civilite);

        //TextView Section..........................................////////////////////////////////////////////
        nouveau_membro =(TextView)findViewById(R.id.nouveau_membre);
        nouveau_membro.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        // Fixed the screen Orientation......
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        if(Config.CashierMode=="MultipleCashier")
        {
            DropDownCivilityList();
            DropDownCountryList();
            DropDownCityList();
            DropDownActivityList();
        }
        else
        {
            DropDownCityListFromSqliteDatabase();
            DropDownCountryListFromSqliteDatabase();
            DropDownCivilityListFromSqliteDatabase();
            DropDownActivityListFromSqliteDatabase();
        }

       CréditerBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               if(Config.CashierMode=="MultipleCashier")
               {
                   NewMemberAddMultiCashier();

               }
               else
               {
                   new BackgroundTaskNewMemberAddSingleCashierInSqliteDatabase(getApplicationContext()).execute();
               }

           }
       });

       AnnularBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

       ///Date Selection Section.......................//////////////////////////////

        dateSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog(DATE_START_DIALOG_ID);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            }
        });

    }
    /// Date Selection Method
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id)
        {
            case DATE_START_DIALOG_ID :
                return new DatePickerDialog(this,mDateSetListener,startYear,startMonth,startDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            startYear = year;
            startMonth = month;
            startDay = dayOfMonth;
            age.setDateOfBirth(startYear,startMonth,startDay);
            String mDate=convertDate(convertToMillis(dayOfMonth,(startMonth),startYear));
            //date_of_birth.setText(dayOfMonth+"/"+(startMonth+1)+"/"+startYear);
            date_of_birth.setText(mDate);
            calculateAge();
        }
    };
    private void calculateAge()
    {
        age.calculateYear();
        age.calculateMonth();
        age.calculateDay();
        Age.setText(age.getResult());
    }

    public long convertToMillis(int day, int month, int year) {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.YEAR, year);
        calendarStart.set(Calendar.MONTH, month);
        calendarStart.set(Calendar.DAY_OF_MONTH, day);
        return calendarStart.getTimeInMillis();
    }


    public String convertDate(long mTime) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        String formattedDate = df.format(mTime);
        return formattedDate;
    }

    public void DropDownCivilityList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))

        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CivilityListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseCivility",""+response);
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
                                    CivilityList.add(Value);

                                    Civilite = new String[CivilityList.size()];
                                    Civilite = CivilityList.toArray(Civilite);
                                    //Civilite[0] = "";

                                    ArrayAdapter<String>arrayAdapter_civilite
                                            = new ArrayAdapter<String>(Nouveau_Membre.this,android.R.layout.simple_spinner_dropdown_item,Civilite);

                                    spinner_civilite.setAdapter(arrayAdapter_civilite);

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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCountryList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CountryListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseCountry",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

                        if(jsonArray!=null){
                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject JO = jsonArray.getJSONObject(i);
                                try{
                                    String Value = JO.getString("Value");
                                    Log.e("GetValueCountry :",""+Value);
                                    CountryList.add(Value);
                                    Pays = new String[CountryList.size()];
                                    //Pays[0] = "---Choisir---";
                                    Pays = CountryList.toArray(Pays);
                                    ArrayAdapter<String>arrayAdapter_pays
                                            = new ArrayAdapter<String>(Nouveau_Membre.this,android.R.layout.simple_spinner_dropdown_item,Pays);

                                    spinner_pays.setAdapter(arrayAdapter_pays);
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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCityList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {
            //getPostalCode = PostalCode.getText().toString();
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
                                                    = new ArrayAdapter<String>(Nouveau_Membre.this,android.R.layout.simple_spinner_dropdown_item,Ville);

                                            spinner_ville.setAdapter(arrayAdapter_Ville);
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
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);

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
                                    Ville[0] = " ";
                                    ArrayAdapter<String>arrayAdapter_Ville
                                            = new ArrayAdapter<String>(Nouveau_Membre.this,android.R.layout.simple_spinner_dropdown_item,Ville);

                                    spinner_ville.setAdapter(arrayAdapter_Ville);
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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownActivityList()
    {
        if(InternetUtil.isConnected(getApplicationContext()))
        {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ActivityListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseAcitivity",""+response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");

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
                                    //Profession[0] = "---Choisir---";
                                    ArrayAdapter<String>arrayAdapter_profession
                                            = new ArrayAdapter<String>(Nouveau_Membre.this,android.R.layout.simple_spinner_dropdown_item,Profession);

                                    spinner_profession.setAdapter(arrayAdapter_profession);
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
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void DropDownCityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
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

                allMemberModelsArrayList = new ArrayList<AllMemberModel>();
                databaseHelper = new DatabaseHelper(getApplicationContext());
                allMemberModelsArrayList = databaseHelper.GetAllCityListThroughZIPCodeFromSqliteDatabase(GetPostalCode);
                CityList.clear();

                for(int i=0;i<allMemberModelsArrayList.size();i++)
                {
                    AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
                    GetCityThroughZipCode = allMemberModel.getCity();

                    Log.e("GetValueCity :",""+GetCityThroughZipCode);
                    CityList.add(GetCityThroughZipCode);
                    Ville = new String[CityList.size()];
                    Ville = CityList.toArray(Ville);
                    ArrayAdapter<String>arrayAdapter_Ville
                            = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Ville);

                    spinner_ville.setAdapter(arrayAdapter_Ville);

                }

                //}

            }
        });

        allMemberModelsArrayList = databaseHelper.GetAllCityListFromSqliteDatabase();

        CityList.clear();

        for(int i=0;i<allMemberModelsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = allMemberModelsArrayList.get(i);
            GetCityThroughZipCode = allMemberModel.getCity();

            Log.e("GetValueCityOutSide :",""+GetCityThroughZipCode);
            CityList.add(GetCityThroughZipCode);
            Ville = new String[CityList.size()];
            Ville = CityList.toArray(Ville);

            ArrayAdapter<String>arrayAdapter_Ville
                    = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Ville);

            spinner_ville.setAdapter(arrayAdapter_Ville);


        }

        // }

    }

    public void DropDownCountryListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
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
                    = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Pays);

            spinner_pays.setAdapter(arrayAdapter_pays);

        }

    }

    public void DropDownCivilityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allMemberModelsArrayList = databaseHelper.GetCivilityListFromSqliteDatabase();

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
                    = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Civilite);

            spinner_civilite.setAdapter(arrayAdapter_civilite);

        }
    }

    public void DropDownActivityListFromSqliteDatabase() {
        allMemberModelsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allMemberModelsArrayList = databaseHelper.GetActivityListFromSqliteDatabase();

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
                    = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,Profession);

            spinner_profession.setAdapter(arrayAdapter_profession);

        }
    }

    ///////////// "NewMemberAddMultiCashier" this method work for only BackgroundApiCall.....///////////////////
    public void NewMemberAddMultiCashier(){

        if(InternetUtil.isConnected(getApplicationContext()))
        {
            getCivilite = spinner_civilite.getSelectedItem().toString();
            getCity = spinner_ville.getSelectedItem().toString();
            getCountry = spinner_pays.getSelectedItem().toString();
            getActivity = spinner_profession.getSelectedItem().toString();
            getCarte = Carte.getText().toString();
            getNom = Nom.getText().toString();
            getPreNom = PreNom.getText().toString();

            if(date_of_birth.getText().equals(""))
            {
                Log.e("EditTextCalled :","true");
               /* end = Calendar.getInstance();
                endYear = end.get(Calendar.YEAR);
                Log.e("getCalenderYear :",""+endYear);
                endMonth = end.get(Calendar.MONTH);
                endMonth++;
                endDay = end.get(Calendar.DAY_OF_MONTH);
                String dateOfBirthget = endDay+"/"+endMonth+"/"+endYear;

                Log.e("GetDateOfBirth :",""+dateOfBirthget);*/

               String day="01";
               String month="jan";
               String year="1950";
               getdate_of_birth = day+"/"+month+"/"+year;
               getAge = "0";
            }
            else
            {
                getdate_of_birth = date_of_birth.getText().toString();
                getAge = Age.getText().toString();
            }

            getAddress = Address.getText().toString();
            getPostalCode = PostalCode.getText().toString();
            getTelePhone = TelePhone.getText().toString();
            getEmail = Email.getText().toString();
            getSoicite = Soicite.getText().toString();

            dialog = new ProgressDialog(Nouveau_Membre.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();

            StringRequest stringRequest= new StringRequest(Request.Method.POST, Config.Url.concat(AddNewMemberUrl),
                     new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();

                    Log.e("JsonResponse", "" + response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String ResponseObject = jsonObject.getString("results");
                        Log.e("ResponseObject", "" + ResponseObject);

                        if (ResponseObject.equals("Enter Valid Mandatory Fields") ||(ResponseObject.equals("Phone No already exists!"))) {
                            //Toast.makeText(getApplicationContext(), ResponseObject, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //Toast.makeText(getApplicationContext(), ResponseObject, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Nouveau_Membre.this,Crediter.class);
                            intent.putExtra("CardNumber",getCarte);
                            intent.putExtra("Token",getLoginToken);
                            intent.putExtra("mi_barcode",Mi_Barcode);
                            startActivity(intent);
                            finish();
                            //AddMemberToSqliteDatabase();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            },
                    new Response.ErrorListener() {
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization-Token", getLoginToken);
                    return headers;
                }

                @SuppressLint("LongLogTag")
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Log.e("GetDateOfBirthInParams :",""+getdate_of_birth);
                    Log.e("GetAgeInParams :",""+getAge);
                    HashMap<String,String> params = new HashMap<String, String>();
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
                    params.put("mi_barcode",Mi_Barcode);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        else
        {
            Toast.makeText(Nouveau_Membre.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
            //AddMemberToSqliteDatabase();
            finish();
        }

    }

    ///////////// "NewMemberAddSingleCashier" this method work for SqliteDatabase + BackgroundApiCall.....///////////////////

    public class BackgroundTaskNewMemberAddSingleCashierInSqliteDatabase extends AsyncTask<Void,Void,AllMemberModel>{
        Context context;
        ProgressDialog dialog;

        public BackgroundTaskNewMemberAddSingleCashierInSqliteDatabase(Context context) {
            this.context = context;
            databaseHelper = new DatabaseHelper(context);
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Nouveau_Membre.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected AllMemberModel doInBackground(Void... voids) {
            strDate = new CurrentTimeGet().getCurrentTime();
            Log.e("GetCurrentTime :",""+strDate);

            AllMemberModel allMemberModel = new AllMemberModel();

            getCivilite = spinner_civilite.getSelectedItem().toString();
            getCity = spinner_ville.getSelectedItem().toString();
            getCountry = spinner_pays.getSelectedItem().toString();
            getActivity = spinner_profession.getSelectedItem().toString();
            getCarte = Carte.getText().toString();
            getNom = Nom.getText().toString();
            getPreNom = PreNom.getText().toString();
            CurrentTimeDate = new CurrentTimeGet().getCurrentTime();

            if(date_of_birth.getText().equals(""))
            {
                Log.e("EditTextCalled :","true");

                String day="01";
                String month="jan";
                String year="1950";
                getdate_of_birth = day+"/"+month+"/"+year;
                getAge = "0";
                getReplaceBirthDate = getdate_of_birth.replace("\\\\","");

            }
            else
            {
                getdate_of_birth = date_of_birth.getText().toString();
                getAge = Age.getText().toString();
                getReplaceBirthDate = getdate_of_birth.replace("\\\\","");
            }

            getAddress = Address.getText().toString();
            getPostalCode = PostalCode.getText().toString();
            getTelePhone = TelePhone.getText().toString();
            getEmail = Email.getText().toString();
            getSoicite = Soicite.getText().toString();

            if(getCivilite.equals("Mlle"))
            {
                allMemberModel.setGender("Male");
            }
            else if(getCivilite.equals("Mme"))
            {
                allMemberModel.setGender("Female");
            }
            else if(getCivilite.equals("M"))
            {
                allMemberModel.setGender("");
            }

            allMemberModel.setCard_number(getCarte);
            allMemberModel.setCashier_id(getCashierid);
            allMemberModel.setCivility(getCivilite);
            allMemberModel.setName(getNom);
            allMemberModel.setFirst_name(getPreNom);
            allMemberModel.setBirth_date(getReplaceBirthDate);
            allMemberModel.setAge(Integer.parseInt(getAge));
            allMemberModel.setSociety(getSoicite);
            allMemberModel.setActivity(getActivity);
            allMemberModel.setAddress_line1(getAddress);
            allMemberModel.setAddress_line2("");
            allMemberModel.setPostal_code(getPostalCode);
            allMemberModel.setCity(getCity);
            allMemberModel.setCountry(getCountry);
            allMemberModel.setPortable("");
            allMemberModel.setPhone(getTelePhone);
            allMemberModel.setEmail(getEmail);
            allMemberModel.setLast_visit(CurrentTimeDate);
            allMemberModel.setBalance_card(0);
            allMemberModel.setTurn_over(0);
            allMemberModel.setAverage_basket(0);
            allMemberModel.setTotal_purchase_order(0);
            allMemberModel.setTotal_uses(0);
            allMemberModel.setIn_progress(0);
            allMemberModel.setNpai(false);
            allMemberModel.setStop_email(false);
            allMemberModel.setStop_email(false);
            allMemberModel.setCreated_by(getUserName);
            allMemberModel.setCreated_on(strDate);
            allMemberModel.setUpdated_by("");
            allMemberModel.setUpdated_on("");
            allMemberModel.setNo_of_points(0.0);
            allMemberModel.setMi_barcode(Mi_Barcode);
            allMemberModel.setCr_window(false);
            allMemberModel.setCr_server(true);
            allMemberModel.setUp_window(false);
            allMemberModel.setUp_server(true);
            allMemberModel.setDl_window(true);
            allMemberModel.setDl_server(true);
            allMemberModel.setSync_status("NotSync");
            allMemberModel.setUpdate_status("NotChecked");

            return allMemberModel;
        }

        @Override
        protected void onPostExecute(AllMemberModel allMemberModel) {
            if(allMemberModel!=null)
            {
                dialog.dismiss();
                databaseHelper.insertMessage(allMemberModel);

                Intent intent = new Intent(Nouveau_Membre.this,Crediter.class);
                intent.putExtra("CardNumber",getCarte);
                intent.putExtra("Token",getLoginToken);
                intent.putExtra("mi_barcode",Mi_Barcode);
                startActivity(intent);
                finish();
            }
        }
    }

}
