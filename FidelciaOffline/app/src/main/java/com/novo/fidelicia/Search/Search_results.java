package com.novo.fidelicia.Search;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search_results extends AppCompatActivity implements SwipyRefreshLayout.OnRefreshListener{
    String ViewMemberbyPageUrl = "Member/ViewMemberByPage?max=10";
    int currentPage =1;
    String Page = "&page=";
    String Search = "&search=";
    String getSearchResults,getLoginToken;
    Bundle extras;
    String Prenom,Nom,Telephone,CardNumber,MiBarcode,MemberId;
    ListView listView;
    ArrayList<SearchModel> searchArrayList = new ArrayList<SearchModel>();
    SharedPreference sharedPreference;
    LinearLayout imageClose;
    public static final int DISMISS_TIMEOUT = 2000;
    SwipyRefreshLayout swipyRefreshLayout;
    ArrayList<AllMemberModel> arrayList = null;
    DatabaseHelper databaseHelper = null;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        sharedPreference = new SharedPreference();

        getLoginToken = sharedPreference.getLoginToken(Search_results.this);
        Log.e("GetLoginTokenSearchResults :",""+getLoginToken);
        imageClose = (LinearLayout)findViewById(R.id.image_search_close);
        swipyRefreshLayout = (SwipyRefreshLayout)findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setOnRefreshListener(this);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        extras = getIntent().getExtras();
        if(extras!=null)
        {
            getSearchResults = extras.getString("SearchValue");
            Log.e("GetSearchValue :",""+getSearchResults);
        }

        listView = (ListView)findViewById(R.id.listView_searchResults);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Config.CashierMode=="MultipleCashier")
        {
            ViewMemberByPage();
        }
        else
        {
            ViewMemberByPageFromSqliteDatabase();
        }

    }

    public void ViewMemberByPage()
    {
        if(InternetUtil.isConnected(Search_results.this)){

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewMemberbyPageUrl)
                    .concat(Page).concat(String.valueOf(currentPage)).concat(Search).concat(getSearchResults), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("ViewMemberByPageResult :",""+response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        Log.e("ResultJO :",""+JO);

                        JSONArray JA = JO.getJSONArray("results");
                        Log.e("ResultJA :",""+JA);

                        searchArrayList.clear();

                        for(int i=0;i<JA.length();i++)
                        {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            Prenom = jsonObject.getString("first_name");
                            Nom = jsonObject.getString("name");
                            Telephone = jsonObject.getString("phone");
                            CardNumber = jsonObject.getString("card_number");
                            MiBarcode = jsonObject.getString("mi_barcode");
                            MemberId = jsonObject.getString("member_id");

                            Log.e("GetResultsViewMemberByPage :",""+Prenom+"\n"+Nom+"\n"+Telephone+"\n"+CardNumber+"\n"
                                    +"\n"+MiBarcode+"\n"+MemberId);

                            SearchModel searchModel = new SearchModel();
                            searchModel.setPrenom(Prenom);
                            searchModel.setNom(Nom);
                            searchModel.setTelephone(Telephone);
                            searchModel.setMiBarcode(MiBarcode);
                            searchModel.setCardNumber(CardNumber);

                            searchArrayList.add(searchModel);
                            SearchAdapter searchAdapter = new SearchAdapter(Search_results.this,searchArrayList);
                            listView.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();
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
            Toast.makeText(Search_results.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void ViewMemberByPageFromSqliteDatabase()
    {
        databaseHelper = new DatabaseHelper(getApplicationContext());

        Cursor cursor = databaseHelper.RetrievSearchResultsOfMemberDetails(getSearchResults);
        searchArrayList.clear();

        while (cursor.moveToNext())
        {
            Prenom = cursor.getString(cursor.getColumnIndex("first_name"));
            Nom = cursor.getString(cursor.getColumnIndex("name"));
            CardNumber = cursor.getString(cursor.getColumnIndex("card_number"));
            Telephone = cursor.getString(cursor.getColumnIndex("phone"));
            MiBarcode = cursor.getString(cursor.getColumnIndex("mi_barcode"));


            SearchModel searchModel = new SearchModel();
            searchModel.setPrenom(Prenom);
            searchModel.setNom(Nom);
            searchModel.setTelephone(Telephone);
            searchModel.setMiBarcode(MiBarcode);
            searchModel.setCardNumber(CardNumber);

            searchArrayList.add(searchModel);

        }
        databaseHelper.close();
        SearchAdapter searchAdapter = new SearchAdapter(Search_results.this,searchArrayList);
        listView.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }
    @Override
    public void onRefresh(SwipyRefreshLayoutDirection direction) {
        Log.d("MainActivity", "Refresh triggered at "
                + (direction == SwipyRefreshLayoutDirection.TOP ? "top" : "bottom"));
        currentPage = currentPage + 10;
        if(Config.CashierMode=="MultipleCashier")
        {
            ViewMemberByPage();
        }
        else
        {
            ViewMemberByPageFromSqliteDatabase();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Hide the refresh after 2sec
                Search_results.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipyRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }, DISMISS_TIMEOUT);
    }
}
