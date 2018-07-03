package com.novo.fidelicia.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.novo.fidelicia.index.Index_scanner;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.PrefManager;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    Context context;
    final String loginUserUrl = "Auth/Login";
    ProgressDialog dialog;
    SharedPreference sharedPreference;
    EditText UserId,Password;
    Button Ok;
    String stUserName,stPassword,getLoginToken;
    private PrefManager prefManager;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        sharedPreference = new SharedPreference();

        UserId = (EditText) findViewById(R.id.identifiant_id);
        Password = (EditText)findViewById(R.id.Mot_de_passe_password);
        Ok = (Button)findViewById(R.id.btn_Ok);

        context = login.this;

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        getLoginToken = sharedPreference.getLoginToken(getApplicationContext());

        Log.e("GetLoginToken_From_Preference",""+getLoginToken);

        if(getLoginToken!=null)
        {
            startActivity(new Intent(login.this, Index_scanner.class));
            finish();
        }
    }

    private void login() {
        if (InternetUtil.isConnected(context)) {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(loginUserUrl)
                    ,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            Log.e("Response", "" + response);
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                String ResponseObject = jsonResponse.getString("ResponseObject");
                                Log.e("ResponseObject", "" + ResponseObject);
                                String status = jsonResponse.getString("Status");
                                Log.e("GetStatus :",""+status);
                                String Token = jsonResponse.getJSONObject("ResponseObject").getString("Token");
                                String Id = jsonResponse.getJSONObject("ResponseObject").getString("Id").replaceAll("\\\\","");

                                Log.e("Token :",""+Token);
                                Log.e("ID",""+Id);

                                if (status.equals("Authorization Failed")){
                                    Toast.makeText(context,status,Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context,"Login Successful",Toast.LENGTH_SHORT).show();
                                    sharedPreference.saveLoginToken(context,Token);
                                    sharedPreference.saveUserName(context,UserId.getText().toString());
                                    Intent i = new Intent(context, Index_scanner.class);
                                    startActivity(i);
                                    finish();
                                }
                            } catch (JSONException e) {
                                dialog.dismiss();
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
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String,String>();
                stUserName = UserId.getText().toString();
                stPassword = Password.getText().toString();

                map.put("UserName",stUserName);
                map.put("Password",stPassword);
                return map;
            }
        };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

}
