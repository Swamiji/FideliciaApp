package com.novo.fidelicia.GiftCard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
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
import com.novo.fidelicia.R;
import com.novo.fidelicia.database.AllMemberModel;
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

public class Giftcard_debit extends AppCompatActivity implements View.OnClickListener {

    ImageView phone,home,email,cancel_image;
    TextView debitor,DebitedAmountPopup;
    Button button0 , button1 , button2 , button3 , button4 , button5 , button6 ,
            button7 , button8 , button9 ,buttonDots,buttonClear,oui_je_confirm,non,buttonOK,button_mettre_a_jour;
    EditText editText;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    View view;
    TextView Total_amount,Nom,Prenom,phone_number,address,zip_code,city,email_id;
    String ViewOneGiftCardUrl = "GiftCard/ViewOneGiftCard?card_number=";
    String DebitGiftCardUrl = "GiftCard/DebitGiftCard";
    String getGiftCardNumber,getLoginToken,getTotalAmount,getNom,getPrenom,
            getPhoneNumber,getaddress,getZipcode,getCity,getEmail
            ,getGiftCardId,card_number,getLCDC_gift_card_detailsList,getPonts,
            getDate,getCashierId,getPoints,getCreatedOn,getGiftCreditDebitStatus,convertedDate;
    Bundle extras;
    SharedPreference sharedPreference;
    LinearLayout linear_Address,linear_Zip_City;
    String FinalValue;
    ProgressDialog dialog;
    ListView listView;
    ArrayList<GiftCardDebitModel> giftCardDebitModelArrayList = new ArrayList<>();
    float amount,amount1;
    DatabaseHelper databaseHelper = null;
    ArrayList<AllMemberModel>GetGiftCardList = null;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftcard_debit);
        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(Giftcard_debit.this);
        getCashierId = sharedPreference.getUserName(Giftcard_debit.this);


        Log.e("GetLoginTokenInGiftCardDebit :",""+getLoginToken);

        extras = getIntent().getExtras();
        if(extras!=null)
        {
            getGiftCardNumber = extras.getString("GiftCardNumber");
            Log.e("GetCardNumberInGiftCardDebit :",""+getGiftCardNumber);
        }

        phone = (ImageView)findViewById(R.id.phone);
        home = (ImageView)findViewById(R.id.home_image);
        email = (ImageView)findViewById(R.id.email_image);
        cancel_image = (ImageView)findViewById(R.id.cancel_image);
        debitor = (TextView)findViewById(R.id.debiter);
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
        buttonOK = (Button)findViewById(R.id.buttonOK);
        button_mettre_a_jour = (Button)findViewById(R.id.button_Mettre_a_jour_giftDebit);
        editText = (EditText) findViewById(R.id.editText_value);
        Total_amount = (TextView)findViewById(R.id.credit_amount_debit);
        Nom = (TextView)findViewById(R.id.nom_giftDebit);
        Prenom = (TextView)findViewById(R.id.Prenom_giftDebit);
        phone_number = (TextView)findViewById(R.id.phone_number_giftDebit);
        address = (TextView)findViewById(R.id.address_giftDebit);
        zip_code = (TextView)findViewById(R.id.zipCode_giftDebit);
        city = (TextView)findViewById(R.id.city_giftDebit);
        email_id = (TextView)findViewById(R.id.email_giftDebit);
        linear_Address = (LinearLayout)findViewById(R.id.linear_address);
        linear_Zip_City = (LinearLayout)findViewById(R.id.linear_zip_city);
        listView = (ListView)findViewById(R.id.list_5_Dernières_visites_giftCardDebit);

        phone.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        home.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        email.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        cancel_image.setColorFilter(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        cancel_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        debitor.setTypeface(Typeface.createFromAsset(getApplicationContext().getAssets(),"Roboto-Thin.ttf"));

        // Button Click Handle
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

        ///Popup Dialog Opening.................///////////////////////////

        builder = new AlertDialog.Builder(Giftcard_debit.this);
        view = getLayoutInflater().inflate(R.layout.giftcard_debitted_popup,null);
        oui_je_confirm = (Button)view.findViewById(R.id.oui_je_confirm);
        non = (Button)view.findViewById(R.id.non);
        DebitedAmountPopup = (TextView)view.findViewById(R.id.debited_amount_popup);

        builder.setView(view);
        alertDialog=builder.create();

        // get Value from custom Calculator...///////////////////

            buttonOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FinalValue = editText.getText().toString().trim();
                    float value = Float.parseFloat(FinalValue);
                    amount1 = Float.parseFloat(getTotalAmount);
                    if(amount1>=value)
                    {
                        if (value > 100.0) {
                            alertDialog.show();


                            DebitedAmountPopup.setText(value+" €");

                            oui_je_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(Config.CashierMode=="MultipleCashier")
                                    {
                                        DebitGiftCardAmount();
                                        alertDialog.dismiss();
                                        finish();
                                    }
                                    else
                                    {
                                        DebitGiftCardAmountFromSqliteDatabase();
                                        DebitGiftCardAmountInsertIntoSqliteDatabase();
                                        alertDialog.dismiss();
                                        finish();
                                    }

                                }
                            });
                            non.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }
                            });
                        }

                        else
                        {
                            if(Config.CashierMode=="MultipleCashier")
                            {
                                DebitGiftCardAmount();
                                finish();
                            }
                            else
                            {
                                DebitGiftCardAmountFromSqliteDatabase();
                                DebitGiftCardAmountInsertIntoSqliteDatabase();
                                finish();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext()," Attention : solde négatif ",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            if(Config.CashierMode=="MultipleCashier")
            {
                ViewOneGiftCardMember();
            }
            else
            {
                ViewOneGiftCardMemberFromSqliteDatabase();
            }



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                amount1 = Float.parseFloat(getTotalAmount);

                Log.e("GetTotalAmountFloat :",""+amount1);

                /*String getAmountValue = Float.toString(amount1);

                String FloatConversion= getAmountValue;
                Float convertedFloat3=Float.parseFloat(FloatConversion);
                DecimalFormat df3 = new DecimalFormat("0.00");
                df3.setMaximumFractionDigits(2);
                FloatConversion = df3.format(convertedFloat3);*/

                Total_amount.setText(amount1+" €");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                Log.e("GetTotalAmountInOncreate :",""+getTotalAmount);

                amount1 = Float.parseFloat(getTotalAmount);

                Log.e("GetTotalAmountFloat :",""+amount1);

                String Value = s.toString();

                try {

                    amount = Float.parseFloat(Value);

                    amount1 = amount1-amount;

                    Log.e("NewAmountValueGet :",""+amount1);

                    String FinalValue = Float.toString(amount1);

                    String FloatConversion= FinalValue;
                    Float convertedFloat3=Float.parseFloat(FloatConversion);
                    DecimalFormat df3 = new DecimalFormat("0.00");
                    df3.setMaximumFractionDigits(2);
                    FloatConversion = df3.format(convertedFloat3);

                    Total_amount.setText(FloatConversion+" €");

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        button_mettre_a_jour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Giftcard_debit.this,giftcardUpdate.class);
                intent.putExtra("GiftCardNumber",getGiftCardNumber);
                intent.putExtra("AmountPoints",getTotalAmount);
                intent.putExtra("gift_card_id",getGiftCardId);
                startActivity(intent);
                finish();
            }
        });


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


    public void ViewOneGiftCardMember()
    {
        if(InternetUtil.isConnected(Giftcard_debit.this))
        {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewOneGiftCardUrl)
                    .concat(getGiftCardNumber), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseGiftCard :",""+response);
                    try {
                        JSONObject JO= new JSONObject(response).getJSONObject("results");
                        card_number = JO.getString("card_number");
                        getTotalAmount = JO.getString("total_amount");
                        getNom = JO.getString("name");
                        getPrenom = JO.getString("first_name");
                        getaddress = JO.getString("address");
                        getZipcode = JO.getString("postal_code");
                        getCity = JO.getString("city");
                        getEmail = JO.getString("email");
                        getPhoneNumber = JO.getString("phone");
                        getGiftCardId = JO.getString("gift_card_id");

                        Log.e("GetGiftCardInformation :",""+card_number+"\n"+getTotalAmount+"\n"+getNom+"\n"
                        +getPrenom+"\n"+getaddress+"\n"+getZipcode+"\n"+getCity+"\n"+getEmail+"\n"+getGiftCardId);

                        if(getaddress.equals("") || getaddress.equals("string") || getZipcode.equals("string") || getZipcode.equals("") || getCity.equals("string") || getCity.equals(""))
                        {
                            address.setText("");
                            zip_code.setText("");
                            city.setText("");
                            linear_Address.setVisibility(View.GONE);
                            linear_Zip_City.setVisibility(View.GONE);
                        }
                        String FloatConversion= getTotalAmount;
                        Float convertedFloat3=Float.parseFloat(FloatConversion);
                        DecimalFormat df3 = new DecimalFormat("0.00");
                        df3.setMaximumFractionDigits(2);
                        FloatConversion = df3.format(convertedFloat3);

                        Total_amount.setText(FloatConversion+" €");
                        Nom.setText(getNom);
                        Prenom.setText(getPrenom);
                        phone_number.setText(": "+getPhoneNumber);
                        email_id.setText(": "+getEmail);


                        JSONArray JA = JO.getJSONArray("LCDC_gift_card_detailsList");
                        for (int i=0;i<JA.length();i++)
                        {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            String VisitDate = jsonObject.getString("created_on");
                            String Amount = jsonObject.getString("amount");
                            String Status = jsonObject.getString("credit_debit");

                            Log.e("GetTotalResponse :",""+VisitDate+"\n"+Amount+"\n"+Status);

                            GiftCardDebitModel giftCardDebitModel= new GiftCardDebitModel();
                            giftCardDebitModel.setPoints(Amount);
                            giftCardDebitModel.setLast_visit_date(VisitDate);
                            giftCardDebitModel.setStatus(Status);

                            giftCardDebitModelArrayList.add(giftCardDebitModel);
                            GiftCardDebitAdapter giftCardDebitAdapter
                                    = new GiftCardDebitAdapter(Giftcard_debit.this,giftCardDebitModelArrayList);

                            listView.setAdapter(giftCardDebitAdapter);
                            giftCardDebitAdapter.notifyDataSetChanged();

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
            Toast.makeText(Giftcard_debit.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    public void ViewOneGiftCardMemberFromSqliteDatabase()
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

        if(getaddress.equals("") || getaddress.equals("string") || getZipcode.equals("string") || getZipcode.equals("") || getCity.equals("string") || getCity.equals(""))
        {
            address.setText("");
            zip_code.setText("");
            city.setText("");
            linear_Address.setVisibility(View.GONE);
            linear_Zip_City.setVisibility(View.GONE);
        }
        String FloatConversion= getTotalAmount;
        Float convertedFloat3=Float.parseFloat(FloatConversion);
        DecimalFormat df3 = new DecimalFormat("0.00");
        df3.setMaximumFractionDigits(2);
        FloatConversion = df3.format(convertedFloat3);

        Total_amount.setText(FloatConversion+" €");
        Nom.setText(getNom);
        Prenom.setText(getPrenom);
        phone_number.setText(": "+getPhoneNumber);
        email_id.setText(": "+getEmail);

        GetGiftCardList = new ArrayList<AllMemberModel>();
        GetGiftCardList = databaseHelper.GetInformationReturnArraylistGiftCardListLastFiveDataByGiftCardNumber(getGiftCardNumber);

        for(int i=0;i<GetGiftCardList.size();i++)
        {
            AllMemberModel allMemberModel1 = GetGiftCardList.get(i);
            getPoints = String.valueOf(allMemberModel1.getAmount());
            getCreatedOn = allMemberModel1.getCreated_on();
            Log.e("GetCreatedOn",""+getCreatedOn);
            getGiftCreditDebitStatus = allMemberModel1.getCredit_debit();

            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date = null;
            try {
                date = simpleDateFormat1.parse(getCreatedOn);
                convertedDate = simpleDateFormat2.format(date);
                Log.e("ConvertedDate :",""+convertedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String ModifiedDate = convertedDate.replace(" ","T");
            Log.e("GetModifiedDate",""+ModifiedDate);

            GiftCardDebitModel giftCardDebitModel= new GiftCardDebitModel();
            giftCardDebitModel.setPoints(getPoints);
            giftCardDebitModel.setLast_visit_date(ModifiedDate);
            giftCardDebitModel.setStatus(getGiftCreditDebitStatus);

            giftCardDebitModelArrayList.add(giftCardDebitModel);
            GiftCardDebitAdapter giftCardDebitAdapter
                    = new GiftCardDebitAdapter(Giftcard_debit.this,giftCardDebitModelArrayList);

            listView.setAdapter(giftCardDebitAdapter);
            giftCardDebitAdapter.notifyDataSetChanged();

        }


    }

    public void DebitGiftCardAmount()
    {
        if(InternetUtil.isConnected(Giftcard_debit.this)){
           /* dialog = new ProgressDialog(Giftcard_debit.this);
            dialog.setMessage("Please Wait...");
            dialog.setCancelable(true);
            dialog.show();*/

            FinalValue = editText.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(DebitGiftCardUrl), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    //dialog.dismiss();

                    try {
                        JSONObject JO = new JSONObject(response);
                        String JSonRespose = JO.getString("results");
                        Log.e("GetJsonAmountDebitedResponse :",""+JSonRespose);

                        if(JSonRespose.equals("Enter gift card Id!")
                                ||JSonRespose.equals("Amount should be greater than zero!")||JSonRespose.equals("Enter Valid Mandatory Fields"))
                        {
                            //Toast.makeText(Giftcard_debit.this,JSonRespose,Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //Toast.makeText(Giftcard_debit.this,JSonRespose,Toast.LENGTH_SHORT).show();
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
                    params.put("total_amount",FinalValue);
                    params.put("card_number",getGiftCardNumber);
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
            Toast.makeText(Giftcard_debit.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public void DebitGiftCardAmountFromSqliteDatabase(){
        AllMemberModel allMemberModel = new AllMemberModel();
        String GetTotalAmount = Total_amount.getText().toString().replace("€"," ");
        allMemberModel.setCard_number(getGiftCardNumber);
        allMemberModel.setTotal_amount(Double.parseDouble(GetTotalAmount));
        allMemberModel.setUpdate_status("Checked");
        databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.UpdateGiftCardDetailsAmountInSqlite(allMemberModel);
    }

    public void DebitGiftCardAmountInsertIntoSqliteDatabase(){
        AllMemberModel allMemberModel = new AllMemberModel();
        getDate = new CurrentTimeGet().getCurrentTime();
        FinalValue = editText.getText().toString();
        allMemberModel.setCard_number(getGiftCardNumber);
        allMemberModel.setAmount(Double.parseDouble(FinalValue));
        allMemberModel.setCreated_by(getCashierId);
        allMemberModel.setCreated_on(getDate);
        allMemberModel.setCredit_debit("DEBIT");
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
    }

}
