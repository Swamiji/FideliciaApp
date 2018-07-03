package com.novo.fidelicia.Search;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class search extends AppCompatActivity {

    LinearLayout CloseRechercher;
    EditText SearchResultValue;
    String getSearchResultsValue,getLoginToken;
    Button BtnRechercher;
    String ViewMemberbyPageUrl = "Member/ViewMemberByPage?max=10";
    String Page = "&page=1";
    String Search = "&search=";
    String Prenom,Nom,Telephone,CardNumber,MiBarcode,MemberId;
    SharedPreference sharedPreference;
    ImageView SearchImage;
    DatabaseHelper databaseHelper = null;
    ArrayList<AllMemberModel> arrayList = null;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(search.this);
        Log.e("GetLoginTokenSearchResults :",""+getLoginToken);

        CloseRechercher = (LinearLayout) findViewById(R.id.btn_rechercher_close);
        BtnRechercher = (Button)findViewById(R.id.btn_rechercher);
        SearchResultValue = (EditText)findViewById(R.id.member_search_results_value);
        SearchImage = (ImageView)findViewById(R.id.image_search);

        SearchImage.setImageResource(R.drawable.popup_search);

        CloseRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        BtnRechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Config.CashierMode == "MultipleCashier")
                {
                    ViewMemberByPage();
                    SearchImage.setImageResource(R.drawable.popup_search);
                }
                else
                {
                    ViewOneMemberByPageFromSqliteDatabase();
                    SearchImage.setImageResource(R.drawable.popup_search);
                }

            }
        });
    }

    public void ViewMemberByPage()
    {
        if(InternetUtil.isConnected(search.this)){

            getSearchResultsValue = SearchResultValue.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewMemberbyPageUrl)
                    .concat(Page).concat(Search).concat(getSearchResultsValue), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("ViewMemberByPageResult :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        Log.e("ResultJO :",""+JO);

                        JSONArray JA = JO.getJSONArray("results");

                        Log.e("JsonArry",""+JA);

                        if(JO.getString("results").equals("[]"))
                        {
                            SearchImage.setImageResource(R.drawable.popup_noresult);
                        }

                        else
                        {
                            Log.e("Eles_Call :","true");
                            finish();
                            Intent intent = new Intent(search.this,Search_results.class);
                            intent.putExtra("SearchValue",getSearchResultsValue);
                            startActivity(intent);

                            new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SearchResultValue.setText("");
                            }
                        },3000);
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

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);

        }
        else
        {
            Toast.makeText(search.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void ViewOneMemberByPageFromSqliteDatabase()
    {
        getSearchResultsValue = SearchResultValue.getText().toString();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean check = databaseHelper.CheckSearchResultsOfMemberDetails(getSearchResultsValue);


        if(check == true)
        {
            finish();
            Intent intent = new Intent(search.this,Search_results.class);
            intent.putExtra("SearchValue",getSearchResultsValue);
            startActivity(intent);
        }

        else
        {
            SearchImage.setImageResource(R.drawable.popup_noresult);
            Log.e("Eles Called ","Yes");
        }

    }
}
