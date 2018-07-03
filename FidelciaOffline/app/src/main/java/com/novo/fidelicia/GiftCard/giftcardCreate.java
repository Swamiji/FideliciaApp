package com.novo.fidelicia.GiftCard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.new_member.Nouveau_Membre;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class giftcardCreate extends AppCompatActivity {

    TextView Nouvelle_Carte_Cadeau;
    Button Annular,Ok;
    String getCardNumberValue,getLoginToken,getMonte_a_crediter,getGiftNom,getGiftPrenom,getGiftTelephone,getGiftEmail,getCashierId,getDate;
    Bundle extras;
    String GiftCardCreateUrl = "GiftCard/AddGiftCard";
    SharedPreference sharedPreference;
    EditText Monte_a_crediter,GiftNom,GiftPrenom,GiftTelephone,GiftEmail;
    ProgressDialog dialog;
    DatabaseHelper databaseHelper = null;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftcard_create);
        extras = getIntent().getExtras();
        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(giftcardCreate.this);
        getCashierId = sharedPreference.getUserName(giftcardCreate.this);
        Monte_a_crediter = (EditText)findViewById(R.id.montant_a_crediter_giftCard_create);
        GiftNom = (EditText)findViewById(R.id.nom_giftCreate);
        GiftPrenom = (EditText)findViewById(R.id.Prenom_giftCreate);
        GiftTelephone = (EditText)findViewById(R.id.telephone_giftCreate);
        GiftEmail = (EditText)findViewById(R.id.email_giftCreate);

        Monte_a_crediter.setInputType(InputType.TYPE_CLASS_PHONE);
        //GiftTelephone.setInputType(InputType.TYPE_CLASS_PHONE);

        Log.e("LoginTokenInCreateGiftCard :",""+getLoginToken);

        if(extras!=null)
        {
            getCardNumberValue = extras.getString("GiftCardNumber");
            Log.e("GetGiftCardNumber :",""+getCardNumberValue);
        }
        Annular = (Button)findViewById(R.id.btn_Annular_giftCardCreate);
        Ok = (Button)findViewById(R.id.btn_ok_giftCardCreate);

        Nouvelle_Carte_Cadeau = (TextView)findViewById(R.id.Nouvelle_Carte_Cadeau);

        Nouvelle_Carte_Cadeau.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        Annular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTest()){

                    if(Config.CashierMode=="MultipleCashier")
                    {
                        GiftCardCreate();
                    }
                    else
                    {
                        GiftCardCreateInSqliteDatabase();
                        GiftCardDebitCreditCreateInSqliteDatabase();
                    }

                }
            }
        });
    }

    public void GiftCardCreate() {
        if(InternetUtil.isConnected(giftcardCreate.this))
        {
            getMonte_a_crediter = Monte_a_crediter.getText().toString();
            getGiftNom = GiftNom.getText().toString();
            getGiftPrenom = GiftPrenom.getText().toString();
            getGiftEmail = GiftEmail.getText().toString();
            getGiftTelephone = GiftTelephone.getText().toString();

            dialog = new ProgressDialog(giftcardCreate.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(GiftCardCreateUrl), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    dialog.dismiss();

                       Log.e("JsonResponseGiftCardCreate",""+response);

                    try {
                        JSONObject JO = new JSONObject(response);

                        String JsonResponse = JO.getString("results");
                        {
                            if(JsonResponse.equals("Enter Valid Mandatory Fields"))
                            {
                                //Toast.makeText(giftcardCreate.this,response,Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //Toast.makeText(giftcardCreate.this,response,Toast.LENGTH_SHORT).show();
                                finish();
                            }
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
                    params.put("card_number",getCardNumberValue);
                    params.put("name",getGiftNom);
                    params.put("first_name",getGiftPrenom);
                    params.put("phone",getGiftTelephone);
                    params.put("email",getGiftEmail);
                    params.put("total_amount",getMonte_a_crediter);

                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization-Token", getLoginToken);
                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }

        else
        {
            Toast.makeText(giftcardCreate.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void GiftCardCreateInSqliteDatabase() {
        getMonte_a_crediter = Monte_a_crediter.getText().toString();
        getGiftNom = GiftNom.getText().toString();
        getGiftPrenom = GiftPrenom.getText().toString();
        getGiftEmail = GiftEmail.getText().toString();
        getGiftTelephone = GiftTelephone.getText().toString();
        getDate = new CurrentTimeGet().getCurrentTime();

        AllMemberModel allMemberModel = new AllMemberModel();
        allMemberModel.setCard_number(getCardNumberValue);
        allMemberModel.setName(getGiftNom);
        allMemberModel.setFirst_name(getGiftPrenom);
        allMemberModel.setPhone(getGiftTelephone);
        allMemberModel.setEmail(getGiftEmail);
        allMemberModel.setAddress("");
        allMemberModel.setPostal_code("");
        allMemberModel.setCity("");
        allMemberModel.setCountry("FRANCE");
        allMemberModel.setTotal_amount(Double.parseDouble(getMonte_a_crediter));
        allMemberModel.setCreated_by(getCashierId);
        allMemberModel.setCreated_on(getDate);
        allMemberModel.setUpdated_by("");
        allMemberModel.setUpdated_on("");
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_window(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setDl_window(true);
        allMemberModel.setCashier_id(getCashierId);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertGiftCardDetailsInSqlite(allMemberModel);
        finish();
    }

    public void GiftCardDebitCreditCreateInSqliteDatabase() {
        getMonte_a_crediter = Monte_a_crediter.getText().toString();
        getGiftNom = GiftNom.getText().toString();
        getGiftPrenom = GiftPrenom.getText().toString();
        getGiftEmail = GiftEmail.getText().toString();
        getGiftTelephone = GiftTelephone.getText().toString();
        getDate = new CurrentTimeGet().getCurrentTime();

        AllMemberModel allMemberModel = new AllMemberModel();
        allMemberModel.setCard_number(getCardNumberValue);
        allMemberModel.setAmount(Double.parseDouble(getMonte_a_crediter));
        allMemberModel.setCreated_by(getCashierId);
        allMemberModel.setCreated_on(getDate);
        allMemberModel.setCredit_debit("CREDIT");
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_window(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setDl_window(true);
        allMemberModel.setCashier_id(getCashierId);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertGiftCardInSqlite(allMemberModel);
        finish();
    }

    public boolean isTest() {
        getMonte_a_crediter = Monte_a_crediter.getText().toString();
        if(getMonte_a_crediter.length()<=0 || getMonte_a_crediter.isEmpty())
        {
            Toast.makeText(giftcardCreate.this,"Pls Entrez le montant ",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*getGiftTelephone = GiftTelephone.getText().toString();
        if(getGiftTelephone.length()<10 || getGiftTelephone.length()>10)
        {
            Toast.makeText(giftcardCreate.this,"Veuillez entrer le numéro de téléphone",Toast.LENGTH_SHORT).show();
            return false;
        }
        getGiftNom = GiftNom.getText().toString();
        if(getGiftNom.isEmpty())
        {
            Toast.makeText(giftcardCreate.this,"Veuillez entrer le nom",Toast.LENGTH_SHORT).show();
            return false;
        }
        getGiftPrenom = GiftPrenom.getText().toString();
        if(getGiftNom.isEmpty())
        {
            Toast.makeText(giftcardCreate.this,"Veuillez entrer le prénom ",Toast.LENGTH_SHORT).show();
            return false;
        }*/

       /* getGiftEmail = GiftEmail.getText().toString().trim();
        if (! isValidMail(getGiftEmail)){
            Toast.makeText(giftcardCreate.this,"Veuillez entrer un identifiant de courriel valide",Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private boolean isValidMail(String stEmailId) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = stEmailId;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
