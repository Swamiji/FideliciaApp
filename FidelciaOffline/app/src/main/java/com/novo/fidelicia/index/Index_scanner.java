package com.novo.fidelicia.index;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.novo.fidelicia.BackgroundTaskService.SensorService;
import com.novo.fidelicia.Créditer.Crediter;
import com.novo.fidelicia.GiftCard.Giftcard_debit;
import com.novo.fidelicia.GiftCard.giftcard;
import com.novo.fidelicia.GiftCard.giftcardCreate;
import com.novo.fidelicia.R;
import com.novo.fidelicia.Search.search;
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.AllMemberModelByCardNumberOnly;
import com.novo.fidelicia.database.AllMemberModelByMiBarCodeOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.new_member.Nouveau_Membre;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Index_scanner extends AppCompatActivity {

    SharedPreference sharedPreference;
    String getLoginToken;
    Button ScannerInputGO, Btn_recommencer, Btn_Ok_error1, Btn_Ok_error2, Btn_OK_congratulation;
    EditText ScannerInputValue;
    String PrefixValue;
    ImageView Carte_careadue, Reachercher;
    String ViewOneMemberByCardNumber = "Member/ViewOneMemberByCardNumber?CardNumber=";
    String ViewOneMemberByMi_Barcode = "Member/ViewOneMemberByMiBarCode?MiBarCode=";
    String VoucherUtilisationUrl = "Voucher/VoucherUtilisation";
    String ViewOneGiftCardUrl = "GiftCard/ViewOneGiftCard?card_number=";
    String getCardNumber, getName, getLastName, getdate_of_birth, getage, getphNumber, getzip_code,
            getcity, getaddress, getemail, getMemberId, getRewardValue, getNo_of_points, getMiBarcode,
            getPoints_gain, getDateLastVisit, getValueOfGiftCard, results,getVdBarcode,getCashier,StrDate,getVoucherStage;

    AlertDialog.Builder builder, builder1, builder2, builder3;
    AlertDialog alertDialog, alertDialog1, alertDialog2, alertDialog3;
    View view, view1, view2, view3;
    ArrayList<AllMemberModel> searchArrayList = new ArrayList<AllMemberModel>();
    String member_id,card_number,mi_barcode;
    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    DatabaseHelper databaseHelper = null;
    AllVoucherModel allVoucherModel = new AllVoucherModel();

    Intent mServiceIntent;
    private SensorService mSensorService;

    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ctx = this;
        setContentView(R.layout.activity_index_scanner);

        mSensorService = new SensorService();
        mServiceIntent = new Intent(getApplicationContext(), mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

        sharedPreference = new SharedPreference();
        ScannerInputGO = (Button) findViewById(R.id.btn_Go);
        ScannerInputValue = (EditText) findViewById(R.id.scanning_value_input);
        ScannerInputValue.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(ScannerInputValue, InputMethodManager.SHOW_IMPLICIT);
        ScannerInputValue.setFocusable(true);

        Carte_careadue = (ImageView) findViewById(R.id.carte_cadeau);
        Reachercher = (ImageView) findViewById(R.id.RechherCcher);

        getLoginToken = sharedPreference.getLoginToken(getApplicationContext());
        getCashier = sharedPreference.getUserName(getApplicationContext());

        sharedPreference.clearCardNumber(Index_scanner.this);
        sharedPreference.clearMiBarcodeNumber(Index_scanner.this);

        builder = new AlertDialog.Builder(Index_scanner.this);
        builder1 = new AlertDialog.Builder(Index_scanner.this);
        builder2 = new AlertDialog.Builder(Index_scanner.this);
        builder3 = new AlertDialog.Builder(Index_scanner.this);
        view = getLayoutInflater().inflate(R.layout.home_error_popup, null);
        view1 = getLayoutInflater().inflate(R.layout.voucher_error1_popup, null);
        view2 = getLayoutInflater().inflate(R.layout.voucher_error2_popup, null);
        view3 = getLayoutInflater().inflate(R.layout.congratulation_voucher_popup, null);

        Btn_recommencer = (Button) view.findViewById(R.id.btn_recommencer);
        Btn_Ok_error1 = (Button) view1.findViewById(R.id.btn_voucher_error1);
        Btn_Ok_error2 = (Button) view2.findViewById(R.id.btn_voucher_error2);
        Btn_OK_congratulation = (Button) view3.findViewById(R.id.btn_voucher_congrtulation);

        builder.setView(view);
        builder1.setView(view1);
        builder2.setView(view2);
        builder3.setView(view3);
        alertDialog = builder.create();
        alertDialog1 = builder1.create();
        alertDialog2 = builder2.create();
        alertDialog3 = builder3.create();

        Log.e("GetLoginToken_FromLoginPage", "" + getLoginToken);

        ScannerInputGO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isTest();
                String strDate= new CurrentTimeGet().getCurrentTime();
                Log.e("GetCurrentTime :",""+strDate);
                /*sharedPreference.saveCardNumber(getApplicationContext(),ScannerInputValue.getText().toString());
                sharedPreference.saveMiBarcodeNumber(getApplicationContext(),ScannerInputValue.getText().toString());*/
            }
        });

        Carte_careadue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index_scanner.this, giftcard.class);
                startActivity(intent);
                /*BackgroundTask backgroundTask = new BackgroundTask(getApplicationContext());
                backgroundTask.execute();*/
            }
        });

        Reachercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Index_scanner.this, search.class);
                startActivity(intent);
            }
        });

        ///This Code is for Scanning Input value to set on editText and automatically get the action///////////////////////

        ScannerInputValue.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER)
                        || keyCode == KeyEvent.KEYCODE_TAB) {
                    // handleInputScan();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (ScannerInputValue != null) {
                                ScannerInputValue.requestFocus();
                                isTest();
                               /* sharedPreference.saveCardNumber(getApplicationContext(),ScannerInputValue.getText().toString());
                                sharedPreference.saveMiBarcodeNumber(getApplicationContext(),ScannerInputValue.getText().toString());*/

                            }
                        }
                    }, 10); // Remove this Delay Handler IF requestFocus(); works just fine without delay
                    return true;
                }
                return false;
            }
        });

        Log.e("GetAllCardNumber :",""+card_number);

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("isMyServiceRunning?", true+"");
                return true;
            }
        }
        Log.i ("isMyServiceRunning?", false+"");
        return false;
    }


    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("MAINACT", "onDestroy!");
        super.onDestroy();
    }

    public void isTest() {

        PrefixValue = ScannerInputValue.getText().toString();

        if (PrefixValue.startsWith("5100")) {

                if(Config.CashierMode=="MultipleCashier")
                {
                    VoucherUtilisation();
                }
                else
                {
                    VoucherUtilistationFromSqliteDatabase();
                }

        } else {
            if (PrefixValue.startsWith("9101")) {

                        if (Config.CashierMode == "MultipleCashier") {
                            ViewOneMemberByMi_Brcode();
                            Log.e("MiBacodeCalled :", "yes");
                        }
                        else {
                            ViewOneMemberByMiBarcodeFromSqliteDatabase();
                        }
            }
            else {

                    if (Config.CashierMode == "MultipleCashier") {
                        ViewOneGiftCardMember();
                        Log.e("CardNumberCalled :", "yes");
                    } else {

                        ViewGiftCardMemberFromSqliteDatabase();

                    }

            }
        }
    }

    public void ViewOneMemberByCardNumber() {

        if (InternetUtil.isConnected(getApplicationContext())) {

            PrefixValue = ScannerInputValue.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByCardNumber).concat(PrefixValue), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMember :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMember :", "" + JO);

                        getName = JO.getString("first_name");
                        getLastName = JO.getString("name");
                        getdate_of_birth = JO.getString("birth_date");
                        //String ModifiedDate_of_birth = getdate_of_birth.re
                        getage = JO.getString("age");
                        int AgeModified = Integer.parseInt(getage);
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getCardNumber = JO.getString("card_number");

                        if (PrefixValue.equals(getCardNumber)) {
                            Intent intent = new Intent(Index_scanner.this, Crediter.class);
                            intent.putExtra("CardNumber", PrefixValue);
                            sharedPreference.saveCardNumber(Index_scanner.this, PrefixValue);
                            startActivity(intent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ScannerInputValue.setText("");
                                }
                            },3000);
                        }
                        else {

                            alertDialog.show();
                            Btn_recommencer.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                    ScannerInputValue.setText("");
                                }
                            });
                        }
                        Log.e("GetUserInformations :", "" + getName + "\n" + getLastName + "\n" + getdate_of_birth + "\n" +
                                getage + "\n" + getphNumber + "\n" + getzip_code + "\n" + getcity + "\n" + getaddress
                                + "\n" + getemail + "\n" + getMemberId + "\n" + getNo_of_points + "\n" + getCardNumber);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
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
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Index_scanner.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void ViewOneMemberByMi_Brcode() {
        if (InternetUtil.isConnected(getApplicationContext())) {

            PrefixValue = ScannerInputValue.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    Config.Url.concat(ViewOneMemberByMi_Barcode).concat(PrefixValue), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseViewOneMember :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        Log.e("ResponseObjectViewOneMember :", "" + JO);

                        getName = JO.getString("first_name");
                        getLastName = JO.getString("name");
                        getdate_of_birth = JO.getString("birth_date");
                        //String ModifiedDate_of_birth = getdate_of_birth.re
                        getage = JO.getString("age");
                        int AgeModified = Integer.parseInt(getage);
                        getphNumber = JO.getString("phone");
                        getzip_code = JO.getString("postal_code");
                        getcity = JO.getString("city");
                        getaddress = JO.getString("address_line1");
                        getemail = JO.getString("email");
                        getMemberId = JO.getString("member_id");
                        getNo_of_points = JO.getString("no_of_points");
                        getCardNumber = JO.getString("card_number");
                        getMiBarcode = JO.getString("mi_barcode");

                        if (PrefixValue.equals(getMiBarcode)) {
                            Intent intent = new Intent(Index_scanner.this, Crediter.class);
                            intent.putExtra("mi_barcode", PrefixValue);
                            intent.putExtra("Token", getLoginToken);
                            //sharedPreference.saveCardNumber(Index_scanner.this,PrefixValue);
                            sharedPreference.saveMiBarcodeNumber(Index_scanner.this, PrefixValue);
                            startActivity(intent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ScannerInputValue.setText("");
                                }
                            },3000);
                        }
                        else {
                            Intent intent = new Intent(Index_scanner.this, Nouveau_Membre.class);
                            intent.putExtra("mi_Barcode", PrefixValue);
                            sharedPreference.saveMiBarcodeNumber(Index_scanner.this, PrefixValue);
                            startActivity(intent);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ScannerInputValue.setText("");
                                }
                            },3000);
                        }

                        Log.e("GetUserInformations :", "" + getName + "\n" + getLastName + "\n" + getdate_of_birth + "\n" +
                                getage + "\n" + getphNumber + "\n" + getzip_code + "\n" + getcity + "\n" + getaddress
                                + "\n" + getemail + "\n" + getMemberId + "\n" + getNo_of_points + "\n" + getCardNumber + "\n" + getMiBarcode);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },
                    new Response.ErrorListener() {
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
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Index_scanner.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void VoucherUtilisation() {
        if (InternetUtil.isConnected(Index_scanner.this)) {
            PrefixValue = ScannerInputValue.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.Url.concat(VoucherUtilisationUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("GetVoucherResponse :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        String VoucherResponse = JO.getString("results");
                        Log.e("VoucherResponse :", "" + VoucherResponse);

                        if (VoucherResponse.equals("Sorry! Voucher Expired.")) {

                            alertDialog2.show();
                            Btn_Ok_error2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog2.dismiss();
                                    ScannerInputValue.setText("");
                                }
                            });
                        } else if (VoucherResponse.equals("Congratulations! Voucher Used Successfully.")) {
                            alertDialog3.show();
                            Btn_OK_congratulation.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog3.dismiss();
                                    ScannerInputValue.setText("");
                                }
                            });
                        } else if (VoucherResponse.equals("Sorry! Voucher Already Used.")) {
                            alertDialog1.show();
                            Btn_Ok_error1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog1.dismiss();
                                    ScannerInputValue.setText("");
                                }
                            });
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
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("vd_barcode", PrefixValue);
                    return Params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Index_scanner.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public void VoucherUtilistationFromSqliteDatabase()
    {
        PrefixValue = ScannerInputValue.getText().toString();

        databaseHelper = new DatabaseHelper(getApplicationContext());

        boolean check = databaseHelper.GetInformationByVdBarcoderToCheckVoucher(PrefixValue);


        if(check == true)
        {
            allVoucherModel = new AllVoucherModel();
            allVoucherModel = databaseHelper.getAllVoucherInformation(PrefixValue);
            getVdBarcode = allVoucherModel.getVd_barcode();
            getVoucherStage = allVoucherModel.getVoucher_stage();

            Log.e("GetVdBarcode ",""+getVdBarcode);

            if(getVoucherStage.equals("ENVOYÉ"))
            {
                allVoucherModel = new AllVoucherModel();
                alertDialog3.show();
                StrDate = new CurrentTimeGet().getCurrentTime();
                allVoucherModel.setVd_barcode(getVdBarcode);
                allVoucherModel.setVoucher_stage("UTILISÉ");
                allVoucherModel.setUpdated_by(getCashier);
                allVoucherModel.setUpdated_on(StrDate);
                allVoucherModel.setUp_window(false);
                allVoucherModel.setUpdate_status("Checked");

                databaseHelper.UpdateAllVoucherInformation(allVoucherModel);

                Btn_OK_congratulation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog3.dismiss();
                        ScannerInputValue.setText("");
                    }
                });
            }
            else if(getVoucherStage.equals("UTILISÉ"))
            {
                alertDialog1.show();
                Btn_Ok_error1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                        ScannerInputValue.setText("");
                    }
                });
            }
            else if(getVoucherStage.equals("EXPIRÉ"))
            {
                alertDialog2.show();
                Btn_Ok_error2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog2.dismiss();
                        ScannerInputValue.setText("");
                    }
                });
            }
        }
    }

    public void ViewOneGiftCardMember() {
        if (InternetUtil.isConnected(Index_scanner.this)) {
            getValueOfGiftCard = ScannerInputValue.getText().toString();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewOneGiftCardUrl)
                    .concat(getValueOfGiftCard), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("JsonResponseGiftCard :", "" + response);
                    try {
                        JSONObject JO = new JSONObject(response).getJSONObject("results");
                        String card_number = JO.getString("card_number");
                        Log.e("CardNumber :", "" + card_number);

                        if (card_number.equals(getValueOfGiftCard)) {
                            Intent intent = new Intent(Index_scanner.this, Giftcard_debit.class);
                            intent.putExtra("GiftCardNumber", getValueOfGiftCard);
                            startActivity(intent);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ScannerInputValue.setText("");
                                }
                            },3000);
                        }
                        else {

                            ViewOneMemberByCardNumber();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ScannerInputValue.setText("");
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
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization-Token", getLoginToken);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(Index_scanner.this, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("LongLogTag")
    public void ViewOneMemberByCardNumberFromSqliteDatabase()
    {
        PrefixValue = ScannerInputValue.getText().toString();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean check = databaseHelper.GetInformationByCardNumberToCheckMember(PrefixValue);

            if(check == true)
            {
                Intent intent = new Intent(Index_scanner.this, Crediter.class);
                intent.putExtra("CardNumber", PrefixValue);
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScannerInputValue.setText("");
                    }
                },3000);
            }

            else  {

                alertDialog.show();
                Btn_recommencer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        ScannerInputValue.setText("");
                        sharedPreference.clearCardNumber(getApplicationContext());
                    }
                });
            }
    }

    @SuppressLint("LongLogTag")
    public void ViewOneMemberByMiBarcodeFromSqliteDatabase()
    {
        PrefixValue = ScannerInputValue.getText().toString();
        Log.e("GetPrefixValue :",""+PrefixValue);
        databaseHelper = new DatabaseHelper(getApplicationContext());


        boolean check = databaseHelper.GetInformationByMiBarcodeToCheckMember(PrefixValue);


            if(check == true)
            {
                Intent intent = new Intent(Index_scanner.this, Crediter.class);
                intent.putExtra("mi_barcode", PrefixValue);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScannerInputValue.setText("");
                    }
                },3000);
            }

            else  {

                Intent intent = new Intent(Index_scanner.this, Nouveau_Membre.class);
                intent.putExtra("mi_Barcode", PrefixValue);
                startActivity(intent);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScannerInputValue.setText("");
                    }
                },3000);

                }
    }

    public void ViewGiftCardMemberFromSqliteDatabase() {
        getValueOfGiftCard = ScannerInputValue.getText().toString();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        boolean check = databaseHelper.GetInformationByGiftCardNumberToCheckMember(getValueOfGiftCard);

        if(check == true)
        {

            Intent intent = new Intent(Index_scanner.this,Giftcard_debit.class);
            intent.putExtra("GiftCardNumber",getValueOfGiftCard);
            startActivity(intent);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ScannerInputValue.setText("");
                }
            },3000);

        }
        else
        {
            ViewOneMemberByCardNumberFromSqliteDatabase();
        }

    }

}
