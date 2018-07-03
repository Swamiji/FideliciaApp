package com.novo.fidelicia.GiftCard;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.new_member.Nouveau_Membre;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class giftcard extends AppCompatActivity {

    ImageView CloseImage;
    Button Btn_Go_GiftCard;
    String getValueOfGiftCard,getLoginToken;
    EditText EditGiftCard;
    String ViewOneGiftCardUrl = "GiftCard/ViewOneGiftCard?card_number=";
    SharedPreference sharedPreference;
    DatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giftcard);
        CloseImage = (ImageView)findViewById(R.id.close_giftcard);
        Btn_Go_GiftCard = (Button)findViewById(R.id.btn_go_gift);
        EditGiftCard = (EditText)findViewById(R.id.gift_card_number);
        EditGiftCard.setInputType(InputType.TYPE_CLASS_PHONE);
        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(giftcard.this);
        Log.e("LoginTokenInGiftCard :",""+getLoginToken);

        CloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Btn_Go_GiftCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isTest())
                {
                     if(Config.CashierMode=="MultipleCashier")
                     {
                         ViewOneGiftCardMember();
                     }
                     else
                     {
                         ViewGiftCardMemberFromSqliteDatabase();
                     }

                }
            }
        });

        EditGiftCard.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER)
                        || keyCode == KeyEvent.KEYCODE_TAB) {
                    // handleInputScan();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (EditGiftCard != null) {
                                EditGiftCard.requestFocus();
                                if(isTest())
                                {
                                    if(Config.CashierMode=="MultipleCashier")
                                    {
                                        ViewOneGiftCardMember();
                                    }
                                    else
                                    {
                                        ViewGiftCardMemberFromSqliteDatabase();
                                    }
                                }
                            }
                        }
                    }, 10); // Remove this Delay Handler IF requestFocus(); works just fine without delay
                    return true;
                }
                return false;
            }
        });
    }
    public boolean isTest() {
        getValueOfGiftCard = EditGiftCard.getText().toString();

        if(getValueOfGiftCard.isEmpty()){
            Toast.makeText(giftcard.this,"Le numéro de la carte ne doit pas être vide .. !!!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(getValueOfGiftCard.length()<0 || getValueOfGiftCard.length()>20)
        {
            Toast.makeText(giftcard.this,"Entrez le numéro de carte valide",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void ViewOneGiftCardMember() {
        if(InternetUtil.isConnected(giftcard.this))
        {
            getValueOfGiftCard = EditGiftCard.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewOneGiftCardUrl)
                    .concat(getValueOfGiftCard), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseGiftCard :",""+response);
                    try {
                        JSONObject JO= new JSONObject(response).getJSONObject("results");
                        String card_number = JO.getString("card_number");
                        Log.e("CardNumber :",""+card_number);

                        if(card_number.equals(getValueOfGiftCard)){
                            Intent intent = new Intent(giftcard.this,Giftcard_debit.class);
                            intent.putExtra("GiftCardNumber",getValueOfGiftCard);
                            startActivity(intent);
                            finish();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    EditGiftCard.setText("");
                                }
                            },3000);
                        }

                        else
                        {
                            finish();
                            Intent intent = new Intent(giftcard.this,giftcardCreate.class);
                            intent.putExtra("GiftCardNumber",getValueOfGiftCard);
                            startActivity(intent);
                            finish();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    EditGiftCard.setText("");
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
            Toast.makeText(giftcard.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewGiftCardMemberFromSqliteDatabase() {
        getValueOfGiftCard = EditGiftCard.getText().toString();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean check = databaseHelper.GetInformationByGiftCardNumberToCheckMember(getValueOfGiftCard);

        if(check == true)
        {

                Intent intent = new Intent(giftcard.this,Giftcard_debit.class);
                intent.putExtra("GiftCardNumber",getValueOfGiftCard);
                startActivity(intent);
                finish();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        EditGiftCard.setText("");
                    }
                },3000);

        }
        else
        {
            finish();
            Intent intent = new Intent(giftcard.this,giftcardCreate.class);
            intent.putExtra("GiftCardNumber",getValueOfGiftCard);
            startActivity(intent);
            finish();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    EditGiftCard.setText("");
                }
            },3000);
        }

    }


}
