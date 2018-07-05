package com.novo.fidelicia.GiftCard;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class giftcardUpdate extends AppCompatActivity {
    TextView Mettre_à_jour_Carte_Cadeau,CardAmount,CardNumber,NewCardAmountValue;
    SharedPreference sharedPreference;
    String getLoginToken,getGiftCardNumber,getGiftCardId,getTotalAmount
            ,getCreditAmount,getNom,getPrenom,getEmail,getTelephone,getaddress,getZipcode,getCity,getPhoneNumber,card_number,strDate,getCashierid;
    Bundle extras;
    String CreditGiftCardUrl = "GiftCard/CreditGiftCard";
    String ViewOneGiftCardUrl = "GiftCard/ViewOneGiftCard?card_number=";
    EditText CreditAmount,Nom,PreNom,Email,Telephone;
    ProgressDialog dialog;
    Button buttonOKUpdate,Annular;
    float amount,amount1;
    DatabaseHelper databaseHelper = null;
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftcard_update);
        sharedPreference = new SharedPreference();
        CardAmount = (TextView)findViewById(R.id.CardAmount_giftUpdate);
        CardNumber = (TextView)findViewById(R.id.CardNumber_giftUpdate);
        CreditAmount = (EditText)findViewById(R.id.montant_a_crediter_amount);
        CreditAmount.setInputType(InputType.TYPE_CLASS_PHONE);

        Nom = (EditText)findViewById(R.id.nom_giftUpdate);
        PreNom = (EditText)findViewById(R.id.Prenom_giftUpdate);
        Email = (EditText)findViewById(R.id.email_giftUpdate);
        Telephone = (EditText)findViewById(R.id.telephone_giftUpdate);
        buttonOKUpdate = (Button)findViewById(R.id.btn_Ok_GiftCardUpdate);
        Annular = (Button)findViewById(R.id.btn_Annular_giftCardUpdate);
        NewCardAmountValue = (TextView)findViewById(R.id.new_Card_amount_value);
        //Telephone.setInputType(InputType.TYPE_CLASS_PHONE);

        getLoginToken = sharedPreference.getLoginToken(giftcardUpdate.this);
        getCashierid = sharedPreference.getUserName(getApplicationContext());

        Log.e("GetLoginTokenInGiftCardUpdate :",""+getLoginToken);
        extras = getIntent().getExtras();
        if(extras!=null){
           getGiftCardNumber = extras.getString("GiftCardNumber");
           getTotalAmount = extras.getString("AmountPoints");
           getGiftCardId = extras.getString("gift_card_id");
           Log.e("GetGiftCardNumber :",""+getGiftCardNumber);
           Log.e("GetTotalAmount :",""+getTotalAmount);
           Log.e("GetGiftCardId :",""+getGiftCardId);
        }
        Mettre_à_jour_Carte_Cadeau = (TextView)findViewById(R.id.Mettre_à_jour_Carte_Cadeau);
        Mettre_à_jour_Carte_Cadeau.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        CardNumber.setText(getGiftCardNumber);
        CardAmount.setText(getTotalAmount+" €");

        buttonOKUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(isTest())
                {*/
                    if(Config.CashierMode=="MultipleCashier")
                    {
                        CreditGiftCard();
                    }
                    else
                    {
                        UpdateCreditGiftCardInSqliteDatabase();
                        GiftCardDebitCreditCreateInSqliteDatabase();
                        finish();
                    }

                //}
            }
        });

        Annular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(Config.CashierMode=="MultipleCashier")
        {
            ViewOneGiftCardMember();
        }
        else
        {
            ViewOnGiftCardMemberFromSqliteDatabase();
        }


    CreditAmount.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            Log.e("GetTotalAmountInOncreate :",""+getTotalAmount);

            amount1 = Float.parseFloat(getTotalAmount);

            Log.e("GetTotalAmountFloat :",""+amount1);

            String ValueAmount = Float.toString(amount1);

            NewCardAmountValue.setText(ValueAmount);


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e("GetTotalAmountInOncreate :",""+getTotalAmount);

            amount1 = Float.parseFloat(getTotalAmount);

            Log.e("GetTotalAmountFloat :",""+amount1);

            String value = s.toString();

            try{
                amount = Float.parseFloat(value);

                amount1 = amount1+amount;

                Log.e("NewAmountValueGet :",""+amount);

                String FinalValue = Float.toString(amount1);

                NewCardAmountValue.setText(FinalValue);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    });

    }

    public void CreditGiftCard()
    {
        if(InternetUtil.isConnected(giftcardUpdate.this)){
            dialog = new ProgressDialog(giftcardUpdate.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();

            getCreditAmount = CreditAmount.getText().toString();
            getTelephone = Telephone.getText().toString();
            getEmail = Email.getText().toString().trim();
            getNom = Nom.getText().toString();
            getPrenom = PreNom.getText().toString();

            if(getCreditAmount.length()<=0)
            {
                getCreditAmount = "0";
            }
            else
            {
                getCreditAmount = CreditAmount.getText().toString();
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(CreditGiftCardUrl), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    try {
                        JSONObject JO = new JSONObject(response);
                        String JsonResponse = JO.getString("results");
                        Log.e("JsonResponse_giftCardUpdate :",""+JsonResponse);

                        if(JsonResponse.equals("Enter gift card Id!")
                                /*|| JsonResponse.equals("Amount should be greater than zero!")*/||JsonResponse.equals("Enter Valid Mandatory Fields"))
                        {
                            //Toast.makeText(giftcardUpdate.this,JsonResponse,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Toast.makeText(giftcardUpdate.this,JsonResponse,Toast.LENGTH_SHORT).show();
                            /*Intent intent =new Intent(giftcardUpdate.this,Index_scanner.class);
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
                    HashMap<String,String> params = new HashMap<String, String>();
                    Log.e("GetGiftCardIdInParams :",""+getGiftCardId);
                    params.put("gift_card_id",getGiftCardId);
                    params.put("total_amount",getCreditAmount);
                    params.put("card_number",getGiftCardNumber);
                    params.put("name",getNom);
                    params.put("first_name",getPrenom);
                    params.put("phone",getTelephone);
                    params.put("email",getEmail);
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
            Toast.makeText(giftcardUpdate.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewOneGiftCardMember()
    {
        if(InternetUtil.isConnected(giftcardUpdate.this))
        {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewOneGiftCardUrl)
                    .concat(getGiftCardNumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseGiftCard :",""+response);
                    try {
                        JSONObject JO= new JSONObject(response).getJSONObject("results");
                        getTotalAmount = JO.getString("total_amount");
                        getNom = JO.getString("name");
                        getPrenom = JO.getString("first_name");
                        getaddress = JO.getString("address");
                        getZipcode = JO.getString("postal_code");
                        getCity = JO.getString("city");
                        getEmail = JO.getString("email");
                        getPhoneNumber = JO.getString("phone");
                        getGiftCardId = JO.getString("gift_card_id");


                        Log.e("GetGiftCardInformation :",""+getTotalAmount+"\n"+getNom+"\n"
                                +getPrenom+"\n"+getaddress+"\n"+getZipcode+"\n"+getCity+"\n"+getEmail+"\n"+getGiftCardId);

                        Nom.setText(getNom);
                        PreNom.setText(getPrenom);
                        Telephone.setText(getPhoneNumber);
                        Email.setText(getEmail);

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
            Toast.makeText(giftcardUpdate.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    public void ViewOnGiftCardMemberFromSqliteDatabase()
    {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        AllMemberModel allMemberModel = new AllMemberModel();
        allMemberModel = databaseHelper.ViewGiftCardMemberDetailsFromSqliteDatabase(getGiftCardNumber);
        card_number = allMemberModel.getCard_number();
        getTotalAmount = String.valueOf(allMemberModel.getTotal_amount());
        getNom = allMemberModel.getName();
        getPrenom = allMemberModel.getFirst_name();
        getaddress = allMemberModel.getAddress();
        getZipcode = allMemberModel.getPostal_code();
        getCity = allMemberModel.getCity();
        getEmail = allMemberModel.getEmail();
        getPhoneNumber = allMemberModel.getPhone();
        getGiftCardId = String.valueOf(allMemberModel.getGift_card_id());


        Log.e("GetGiftCardInformation :",""+card_number+"\n"+getTotalAmount+"\n"+getNom+"\n"
                +getPrenom+"\n"+getaddress+"\n"+getZipcode+"\n"+getCity+"\n"+getEmail+"\n"+getGiftCardId);


       /* String FloatConversion= getTotalAmount;
        Float convertedFloat3=Float.parseFloat(FloatConversion);
        DecimalFormat df3 = new DecimalFormat("0.00");
        df3.setMaximumFractionDigits(2);
        FloatConversion = df3.format(convertedFloat3);*/

        Nom.setText(getNom);
        PreNom.setText(getPrenom);
        Telephone.setText(getPhoneNumber);
        Email.setText(getEmail);
    }

    public void UpdateCreditGiftCardInSqliteDatabase(){

        getCreditAmount = NewCardAmountValue.getText().toString();
        getTelephone = Telephone.getText().toString();
        getEmail = Email.getText().toString().trim();
        getNom = Nom.getText().toString();
        getPrenom = PreNom.getText().toString();
        strDate = new CurrentTimeGet().getCurrentTime();

        databaseHelper = new DatabaseHelper(getApplicationContext());
        AllMemberModel allMemberModel = new AllMemberModel();
        allMemberModel.setCard_number(getGiftCardNumber);
        allMemberModel.setName(getNom);
        allMemberModel.setFirst_name(getPrenom);
        allMemberModel.setEmail(getEmail);
        allMemberModel.setPhone(getTelephone);
        allMemberModel.setTotal_amount(Double.parseDouble(getCreditAmount));
        allMemberModel.setUpdated_by(getCashierid);
        allMemberModel.setUpdated_on(strDate);
        allMemberModel.setCashier_id(getCashierid);
        allMemberModel.setUp_window(false);
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper.UpdateGiftCardDetailsInSqlite(allMemberModel);

    }

    public void GiftCardDebitCreditCreateInSqliteDatabase() {
        getCreditAmount = CreditAmount.getText().toString();
        getNom = Nom.getText().toString();
        getPrenom = PreNom.getText().toString();
        getEmail = Email.getText().toString();
        getTelephone = Telephone.getText().toString();
        strDate = new CurrentTimeGet().getCurrentTime();

        AllMemberModel allMemberModel = new AllMemberModel();
        allMemberModel.setCard_number(getGiftCardNumber);
        allMemberModel.setAmount(Double.parseDouble(getCreditAmount));
        allMemberModel.setCreated_by(getCashierid);
        allMemberModel.setCreated_on(strDate);
        allMemberModel.setCredit_debit("CREDIT");
        allMemberModel.setCr_window(false);
        allMemberModel.setCr_server(true);
        allMemberModel.setUp_window(true);
        allMemberModel.setUp_server(true);
        allMemberModel.setDl_server(true);
        allMemberModel.setDl_window(true);
        allMemberModel.setSync_status("NotSync");
        allMemberModel.setUpdate_status("NotChecked");

        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.insertGiftCardInSqlite(allMemberModel);
        finish();
    }


    public boolean isTest()
    {
        getCreditAmount = CreditAmount.getText().toString();
        if(getCreditAmount.length()<=0 || getCreditAmount.isEmpty())
        {
            Toast.makeText(giftcardUpdate.this,"Pls Entrez le montant ",Toast.LENGTH_SHORT).show();
            return false;
        }
        /*getTelephone = Telephone.getText().toString();
        if(getTelephone.length()<10 || getTelephone.length()>10)
        {
            Toast.makeText(giftcardUpdate.this,"Veuillez entrer le numéro de téléphone",Toast.LENGTH_SHORT).show();
            return false;
        }
        getEmail = Email.getText().toString().trim();
        if (! isValidMail(getEmail)){
            Toast.makeText(giftcardUpdate.this,"Veuillez entrer un identifiant de courriel valide",Toast.LENGTH_SHORT).show();
            return false;
        }
        getNom = Nom.getText().toString();
        if(getNom.isEmpty())
        {
            Toast.makeText(giftcardUpdate.this,"Veuillez entrer le nom",Toast.LENGTH_SHORT).show();
            return false;
        }
        getPrenom = PreNom.getText().toString();
        if(getPrenom.isEmpty())
        {
            Toast.makeText(giftcardUpdate.this,"Veuillez entrer le prénom ",Toast.LENGTH_SHORT).show();
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
