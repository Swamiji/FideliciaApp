package com.novo.fidelicia.BackgroundTaskService;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.novo.fidelicia.GiftCard.GiftCardDetailsModel;
import com.novo.fidelicia.Membre.DeleteMemberModel;
import com.novo.fidelicia.Membre.UpdateDataModel;
import com.novo.fidelicia.Voucher.SatisfactionVoucher;
import com.novo.fidelicia.Voucher.SatisfactionVoucherModel;
import com.novo.fidelicia.database.AllMemberModel;
import com.novo.fidelicia.database.AllMemberModelByCardNumberOnly;
import com.novo.fidelicia.database.DatabaseHelper;
import com.novo.fidelicia.index.AllVoucherModel;
import com.novo.fidelicia.index.MySingleton;
import com.novo.fidelicia.index.UpdateMemberModel;
import com.novo.fidelicia.index.VoucherDetailsModel;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.utils.Config;
import com.novo.fidelicia.utils.InternetUtil;
import com.novo.fidelicia.utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InterruptedIOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    public int counter = 0;
    String ViewAllMemberListInsertedInWebNotInApp = "SyncMember/ViewAllMemberListInsertedInWebNotInApp";
    String UpdateAllMemberListServerTrueInsertedInApp = "SyncMember/UpdateAllMemberListServerTrueInsertedInApp";
    String ViewAllMemberListUpdatedInWebNotInApp = "SyncMember/ViewAllMemberListUpdatedInWebNotInApp";
    String UpdateAllMemberListServerTrueUpdatedInApp = "SyncMember/UpdateAllMemberListServerTrueUpdatedInApp";
    String InsertAllMemberFromAppToServer = "SyncMember/InsertAllMemberFromAppToServer";
    String UpdateAllMemberFromAppToServer = "SyncMember/UpdateAllMemberFromAppToServer";
    String InsertAllMemberRewardFromAppToServer = "SyncMember/InsertAllMemberRewardFromAppToServer";
    String ViewAllGiftProductListUrl = "SyncGiftProduct/ViewAllGiftProductListInsertedInWebNotInApp";
    String UpdateGiftProductListUrl = "SyncGiftProduct/UpdateAllGiftProductListServerTrueInsertedInApp";
    String ReasonMasterUrl = "SyncReason/ViewAllReasonListInsertedInWebNotInApp";
    String UpdateReasonMasterUrl = "SyncReason/UpdateAllReasonListServerTrueInsertedInApp";
    String ViewAllGiftProductListUpdatedInWebNotInApp = "SyncGiftProduct/ViewAllGiftProductListUpdatedInWebNotInApp";
    String UpdateAllGiftProductListServerTrueUpdatedInApp = "SyncGiftProduct/UpdateAllGiftProductListServerTrueUpdatedInApp";
    String ViewAllReasonListUpdatedInWebNotInApp = "SyncReason/ViewAllReasonListUpdatedInWebNotInApp";
    String UpdateAllReasonListServerTrueUpdatedInApp = "SyncReason/UpdateAllReasonListServerTrueUpdatedInApp";
    String ViewAllZipCodeListInsertedInWeb = "SyncZipCode/ViewAllZipCodeListInsertedInWeb";
    String InsertAllSatisfactionVoucherAppToServer = "SyncVoucher/InsertAllSatisfactionVoucherAppToServer";
    String CivilityList = "DropDown/CivilityList";
    String ActivityList = "DropDown/ActivityList";
    String InsertAllGiftCardInsertedInAppNotInWeb = "SyncGiftCard/InsertAllGiftCardInsertedInAppNotInWeb";
    String UpdateAllGiftCardUpdatedInAppNotInWeb = "SyncGiftCard/UpdateAllGiftCardUpdatedInAppNotInWeb";
    String InsertAllGiftCardDetailsInsertedInAppNotInWeb = "SyncGiftCard/InsertAllGiftCardDetailsInsertedInAppNotInWeb";
    String DeleteAllMemberFromAppToServer = "SyncMember/DeleteAllMemberFromAppToServer";
    String GetAllVoucherListInsertedInServerNotInApp = "SyncVoucher/GetAllVoucherListInsertedInServerNotInApp";
    String UpdateVoucherCrServerTrueInsertedInAppFromWeb = "SyncVoucher/UpdateVoucherCrServerTrueInsertedInAppFromWeb";
    String UpdateVoucherDetailsUpdatedInAppNotInWeb = "SyncVoucher/UpdateVoucherDetailsUpdatedInAppNotInWeb";


    ArrayList<UpdateMemberModel> updateMembersList = new ArrayList<UpdateMemberModel>();
    ArrayList<GiftProductModel> giftProductModelList = new ArrayList<GiftProductModel>();
    ArrayList<ReasonMasterModel> reasonMasterModelList = new ArrayList<ReasonMasterModel>();
    ArrayList<SatisfactionVoucherModel> satisfactionVoucherModelList = new ArrayList<SatisfactionVoucherModel>();
    ArrayList<VoucherDetailsModel> voucherDetailsModelArrayList = new ArrayList<VoucherDetailsModel>();
    ArrayList<AllVoucherModel> allVoucherModelArrayList = new ArrayList<AllVoucherModel>();

    DatabaseHelper databaseHelper = null;
    Context context;
    ProgressDialog dialog;
    int MY_SOCKET_TIMEOUT_MS = 30000;
    SharedPreference sharedPreference;
    String getLoginToken;
    ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = null;
    ArrayList<DataModelSqlite> dataModelSqlitesArrayList = null;
    ArrayList<UpdateDataModel> dataModelArrayList = null;
    ArrayList<CreditPointDataModel> CreditPointsArrayList = null;
    ArrayList<RewardPointModel> RewardPointModelArrayList = null;
    ArrayList<AllMemberModel> SatisfactionArrayList = null;
    ArrayList<AllMemberModel> GiftCardDetailsArrayList = null;
    ArrayList<AllMemberModel> LogTableMemberDeleteArrayList = null;
    ArrayList<GiftCardDetailsModel>GiftCardArraylist = null;
    ArrayList<DeleteMemberModel>deleteMemberModelArrayList = null;
    AllVoucherModel allMemberModel = null;
    ArrayList<AllMemberModel>InsertZipArrayList = new ArrayList<AllMemberModel>();
    ArrayList<AllMemberModel>ZipCodeArrayList = new ArrayList<AllMemberModel>();

    String[] StringArray = null;
    int age;
    String cashier_id, card_number, civility, gender, name, first_name, society, activity, address_line1, address_line2, postal_code,
            city, country, phone, email, mi_barcode, created_by, created_on, birth_date, no_of_points, reward_id, reward_type, member_id
            , updated_by, updated_on, comment, gift_product_name, reason,gift_card_id,address,total_amount,amount,credit_debit,getVoucherId,
            getVdBarcode,getVoucherStage,StrDate,auto_id,geographical_code,department;

    @SuppressLint("LongLogTag")

    public SensorService(Context applicationContext) {
        super();
        Log.i("HERE", "here I am!");
        this.context = applicationContext;
    }

    public SensorService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        startTimer();
       /* Thread thread = new Thread(new MyThreadClass(startId));
        thread.start();*/

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.novo.fidelicia.RestartSensor");
        sendBroadcast(broadcastIntent);
        //stoptimertask();
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime = 0;

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 8000, 8000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                if (InternetUtil.isConnected(getApplicationContext())) {
                    //Log.i("in timer", "in timer ++++  "+ (counter++));

                    if(!(Config.CashierMode=="MultipleCashier"))
                    {
                        ViewAllZipCode();
                        viewAllMemberList();
                        viewAllVoucherListFromServerToAppDatabase();
                        ViewAllMemberListUpdatedInWebNotInApp();
                        GetInformationAsArrayListFromSqliteDatabase();
                        GetInformationAsArrayListFromSqliteDatabaseForSendToServerCheckUpWindow();
                        GetInformationFromPointsRewardTable();
                        ViewAllGiftProductList();
                        ViewAllReasonMaster();
                        UpdateViewAllGiftProductList();
                        UpdateViewAllReasonMasterList();
                        GetSatisfactionVoucherListFromSqalite();
                        ViewCivilityList();
                        ViewActivityList();
                        GetGiftCardDetailsListFromSqliteDatabase();
                        GetGiftCardListFromSqliteDatabase();
                        GiftCardListDetailsForUpdateFromAppToServer();
                        DeleteAllMemberFromLogTableAppToServer();
                        viewAllVoucherListForUpdateVoucherStageFromAppDatabase();
                        UpdateVoucherStage();

                    /*  databaseHelper = new DatabaseHelper(getApplicationContext());
                      databaseHelper.DeleteZipCodeTable();*/
                    }
                }
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreference = new SharedPreference();
        getLoginToken = sharedPreference.getLoginToken(getApplicationContext());
        Log.e("GetLoginToken_FromLoginPage", "" + getLoginToken);
    }

    public class MyThreadClass implements Runnable{

        int service_id;

        public MyThreadClass(int service_id) {
            this.service_id = service_id;
        }

        @Override
        public void run() {
            synchronized (this)
            {
                try
                {
                    //startTimer();
                    wait(60000);
                    //initializeTimerTask();

                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                stopSelf(service_id);
            }
        }
    }

    private void viewAllMemberList() {
        if (InternetUtil.isConnected(getApplicationContext())) {

            Log.e("GetLoginToken_InMethod", "" + getLoginToken);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllMemberListInsertedInWebNotInApp),
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //dialog.dismiss();

                            Log.e("ViewAllMemberList :", "" + response);

                            try {
                                JSONObject JO = new JSONObject(response);
                                JSONArray JA = JO.getJSONArray("results");
                                updateMembersList.clear();
                                Log.e("ResultJA :", "" + JA);

                                if (JA != null) {
                                    updateMembersList = new ArrayList<UpdateMemberModel>();
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject jsonObject = JA.getJSONObject(i);

                                        UpdateMemberModel updateMember = new UpdateMemberModel();
                                        updateMember.setMember_id(Integer.parseInt(jsonObject.getString("member_id")));
                                        updateMember.setMi_barcode(jsonObject.getString("mi_barcode"));
                                        updateMember.setCard_number(jsonObject.getString("card_number"));
                                        updateMembersList.add(updateMember);

                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        //allMemberModel.setMember_id(Integer.parseInt(jsonObject.getString("member_id")));
                                        allMemberModel.setCard_number(jsonObject.getString("card_number"));
                                        allMemberModel.setCashier_id(jsonObject.getString("cashier_id"));
                                        allMemberModel.setCivility(jsonObject.getString("civility"));
                                        allMemberModel.setGender(jsonObject.getString("gender"));
                                        allMemberModel.setName(jsonObject.getString("name"));
                                        allMemberModel.setFirst_name(jsonObject.getString("first_name"));
                                        allMemberModel.setBirth_date(jsonObject.getString("birth_date"));
                                        allMemberModel.setAge(Integer.parseInt(jsonObject.getString("age")));
                                        allMemberModel.setSociety(jsonObject.getString("society"));
                                        allMemberModel.setActivity(jsonObject.getString("activity"));
                                        allMemberModel.setAddress_line1(jsonObject.getString("address_line1"));
                                        allMemberModel.setAddress_line2(jsonObject.getString("address_line2"));
                                        allMemberModel.setPostal_code(jsonObject.getString("postal_code"));
                                        allMemberModel.setCity(jsonObject.getString("city"));
                                        allMemberModel.setCountry(jsonObject.getString("country"));
                                        allMemberModel.setPortable(jsonObject.getString("portable"));
                                        allMemberModel.setPhone(jsonObject.getString("phone"));
                                        allMemberModel.setEmail(jsonObject.getString("email"));
                                        allMemberModel.setLast_visit((jsonObject.getString("last_visit")));
                                        allMemberModel.setBalance_card(Float.parseFloat(jsonObject.getString("balance_card")));
                                        allMemberModel.setTurn_over(Float.parseFloat(jsonObject.getString("turn_over")));
                                        allMemberModel.setAverage_basket(Float.parseFloat(jsonObject.getString("average_basket")));
                                        allMemberModel.setTotal_purchase_order(Float.parseFloat(jsonObject.getString("total_purchase_order")));
                                        allMemberModel.setTotal_uses(Float.parseFloat(jsonObject.getString("total_uses")));
                                        allMemberModel.setIn_progress(Float.parseFloat(jsonObject.getString("total_uses")));
                                        allMemberModel.setNpai(jsonObject.getBoolean("npai"));
                                        allMemberModel.setStop_email(jsonObject.getBoolean("stop_sms"));
                                        allMemberModel.setStop_email(jsonObject.getBoolean("stop_email"));
                                        allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                                        allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                                        allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                                        allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                                        allMemberModel.setNo_of_points(Double.parseDouble(jsonObject.getString("no_of_points")));
                                        allMemberModel.setMi_barcode(jsonObject.getString("mi_barcode"));
                                        allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                                        allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                                        allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                                        allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                                        allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                                        allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                                        allMemberModel.setSync_status("Sync");
                                        allMemberModel.setUpdate_status("NotChecked");

                                        if (jsonObject.getBoolean("cr_server") == false) {
                                            databaseHelper = new DatabaseHelper(getApplicationContext());
                                            boolean check = databaseHelper.insertMessage(allMemberModel);

                                            if(check == true)
                                            {
                                                new BackgroundTask(updateMembersList, getApplicationContext()).execute();
                                            }
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public class BackgroundTask extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<UpdateMemberModel> arrayListData = new ArrayList<UpdateMemberModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTask(ArrayList<UpdateMemberModel> arrayList, Context context) {
            this.arrayListData = arrayList;
            this.ctx = context;

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            for (int i = 0; i < arrayListData.size(); i++) {
                UpdateMemberModel updateMember = arrayListData.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("member_id", updateMember.getMember_id());
                    JO.put("card_number", updateMember.getCard_number());
                    JO.put("mi_barcode", updateMember.getMi_barcode());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("MemberList", jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateAllMemberListServerTrueInsertedInApp), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    //Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        //Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Member List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("MemberList");
                                //Log.e("GetJsonArrayInAsync :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        //Log.e("GetAllCardNumberInAsync :", "" + JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setMi_barcode(JO.getString("mi_barcode"));
                                        allMemberModel.setCr_server(true);
                                        databaseHelper.UpdateCr_server(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void ViewAllMemberListUpdatedInWebNotInApp() {
        if (InternetUtil.isConnected(getApplicationContext())) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllMemberListUpdatedInWebNotInApp),
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //dialog.dismiss();

                            Log.e("ViewAllMemberList :", "" + response);

                            try {
                                JSONObject JO = new JSONObject(response);
                                JSONArray JA = JO.getJSONArray("results");
                                updateMembersList.clear();
                                Log.e("ResultJA :", "" + JA);

                                if (JA != null) {
                                    updateMembersList = new ArrayList<UpdateMemberModel>();
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject jsonObject = JA.getJSONObject(i);
                                        UpdateMemberModel updateMember = new UpdateMemberModel();
                                        updateMember.setMember_id(Integer.parseInt(jsonObject.getString("member_id")));
                                        updateMember.setMi_barcode(jsonObject.getString("mi_barcode"));
                                        updateMember.setCard_number(jsonObject.getString("card_number"));
                                        updateMembersList.add(updateMember);

                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        //allMemberModel.setMember_id(Integer.parseInt(jsonObject.getString("member_id")));
                                        allMemberModel.setCard_number(jsonObject.getString("card_number"));
                                        allMemberModel.setCashier_id(jsonObject.getString("cashier_id"));
                                        allMemberModel.setCivility(jsonObject.getString("civility"));
                                        allMemberModel.setGender(jsonObject.getString("gender"));
                                        allMemberModel.setName(jsonObject.getString("name"));
                                        allMemberModel.setFirst_name(jsonObject.getString("first_name"));
                                        allMemberModel.setBirth_date(jsonObject.getString("birth_date"));
                                        allMemberModel.setAge(Integer.parseInt(jsonObject.getString("age")));
                                        allMemberModel.setSociety(jsonObject.getString("society"));
                                        allMemberModel.setActivity(jsonObject.getString("activity"));
                                        allMemberModel.setAddress_line1(jsonObject.getString("address_line1"));
                                        allMemberModel.setAddress_line2(jsonObject.getString("address_line2"));
                                        allMemberModel.setPostal_code(jsonObject.getString("postal_code"));
                                        allMemberModel.setCity(jsonObject.getString("city"));
                                        allMemberModel.setCountry(jsonObject.getString("country"));
                                        allMemberModel.setPortable(jsonObject.getString("portable"));
                                        allMemberModel.setPhone(jsonObject.getString("phone"));
                                        allMemberModel.setEmail(jsonObject.getString("email"));
                                        allMemberModel.setLast_visit((jsonObject.getString("last_visit")));
                                        allMemberModel.setBalance_card(Float.parseFloat(jsonObject.getString("balance_card")));
                                        allMemberModel.setTurn_over(Float.parseFloat(jsonObject.getString("turn_over")));
                                        allMemberModel.setAverage_basket(Float.parseFloat(jsonObject.getString("average_basket")));
                                        allMemberModel.setTotal_purchase_order(Float.parseFloat(jsonObject.getString("total_purchase_order")));
                                        allMemberModel.setTotal_uses(Float.parseFloat(jsonObject.getString("total_uses")));
                                        allMemberModel.setIn_progress(Float.parseFloat(jsonObject.getString("total_uses")));
                                        allMemberModel.setNpai(jsonObject.getBoolean("npai"));
                                        allMemberModel.setStop_email(jsonObject.getBoolean("stop_sms"));
                                        allMemberModel.setStop_email(jsonObject.getBoolean("stop_email"));
                                        allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                                        allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                                        allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                                        allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                                        allMemberModel.setNo_of_points(Double.parseDouble(jsonObject.getString("no_of_points")));
                                        allMemberModel.setMi_barcode(jsonObject.getString("mi_barcode"));
                                        allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                                        allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                                        allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                                        allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                                        allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                                        allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                                        allMemberModel.setSync_status("Sync");
                                        allMemberModel.setUpdate_status("NotChecked");

                                        if (jsonObject.getBoolean("up_server") == false) {
                                            databaseHelper = new DatabaseHelper(getApplicationContext());
                                            databaseHelper.UpdateInformation(allMemberModel);
                                            new BackgroundTaskUpdateMember(updateMembersList, getApplicationContext()).execute();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        } else {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public class BackgroundTaskUpdateMember extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<UpdateMemberModel> arrayListData = new ArrayList<UpdateMemberModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject JO = new JSONObject();

        public BackgroundTaskUpdateMember(ArrayList<UpdateMemberModel> arrayList, Context context) {
            this.arrayListData = arrayList;
            this.ctx = context;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            for (int i = 0; i < arrayListData.size(); i++) {
                UpdateMemberModel updateMember = arrayListData.get(i);
                try {
                    JO.put("member_id", updateMember.getMember_id());
                    JO.put("card_number", updateMember.getCard_number());
                    JO.put("mi_barcode", updateMember.getMi_barcode());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(JO);

            try {
                jsonObject.put("MemberList", jsonArray);
                Log.e("GetJsonObject ", "" + jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateAllMemberListServerTrueUpdatedInApp), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);

                    try {
                        String JsonResponse = response.getString("results");

                        Log.e("JsonResponseAsync :", "" + JsonResponse);

                        if (JsonResponse.equals("Member List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                Log.e("JsonObjectInAsync :", "" + jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("MemberList");
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsync :", "" + JO);
                                        Log.e("GetAllCardNumberInAsync :", "" + JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setMi_barcode(JO.getString("mi_barcode"));
                                        allMemberModel.setUp_server(true);
                                        databaseHelper.UpdateUP_Server(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    @SuppressLint("LongLogTag")

    public void GetInformationAsArrayListFromSqliteDatabase() {
        allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
        AllMemberModelByCardNumberOnly allMemberModelByCardMiBarcode = new AllMemberModelByCardNumberOnly();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allMemberModelByCardMiBarcodes = databaseHelper.GetInformationReturnArraylist("NotSync");
        for (int i = 0; i < allMemberModelByCardMiBarcodes.size(); i++) {
            allMemberModelByCardMiBarcode = allMemberModelByCardMiBarcodes.get(i);
            age = allMemberModelByCardMiBarcode.getAge();
            cashier_id = allMemberModelByCardMiBarcode.getCashier_id();
            card_number = allMemberModelByCardMiBarcode.getCard_number();
            civility = allMemberModelByCardMiBarcode.getCivility();
            gender = allMemberModelByCardMiBarcode.getGender();
            name = allMemberModelByCardMiBarcode.getName();
            first_name = allMemberModelByCardMiBarcode.getFirst_name();
            society = allMemberModelByCardMiBarcode.getSociety();
            activity = allMemberModelByCardMiBarcode.getActivity();
            address_line1 = allMemberModelByCardMiBarcode.getAddress_line1();
            address_line2 = allMemberModelByCardMiBarcode.getAddress_line2();
            postal_code = allMemberModelByCardMiBarcode.getPostal_code();
            city = allMemberModelByCardMiBarcode.getCity();
            country = allMemberModelByCardMiBarcode.getCountry();
            phone = allMemberModelByCardMiBarcode.getPhone();
            email = allMemberModelByCardMiBarcode.getEmail();
            mi_barcode = allMemberModelByCardMiBarcode.getMi_barcode();
            created_by = allMemberModelByCardMiBarcode.getCreated_by();
            created_on = allMemberModelByCardMiBarcode.getCreated_on();
            birth_date = allMemberModelByCardMiBarcode.getBirth_date();

            DataModelSqlite dataModelSqlite = new DataModelSqlite();
            dataModelSqlite.setAge(age);
            dataModelSqlite.setCashier_id(cashier_id);
            dataModelSqlite.setCard_number(card_number);
            dataModelSqlite.setCivility(civility);
            dataModelSqlite.setGender(gender);
            dataModelSqlite.setName(name);
            dataModelSqlite.setFirst_name(first_name);
            dataModelSqlite.setSociety(society);
            dataModelSqlite.setActivity(activity);
            dataModelSqlite.setAddress_line1(address_line1);
            dataModelSqlite.setAddress_line2(address_line2);
            dataModelSqlite.setPostal_code(postal_code);
            dataModelSqlite.setCity(city);
            dataModelSqlite.setCountry(country);
            dataModelSqlite.setPhone(phone);
            dataModelSqlite.setEmail(email);
            dataModelSqlite.setMi_barcode(mi_barcode);
            dataModelSqlite.setCreated_by(created_by);
            dataModelSqlite.setCreated_on(created_on);
            dataModelSqlite.setBirth_date(birth_date);

            dataModelSqlitesArrayList = new ArrayList<DataModelSqlite>();
            dataModelSqlitesArrayList.add(dataModelSqlite);
            new BackgroundTaskPostDataFromSQliteToServer(dataModelSqlitesArrayList, getApplicationContext()).execute();
        }

    }

    public class BackgroundTaskPostDataFromSQliteToServer extends AsyncTask<Void, Void, JSONObject> {

        ArrayList<DataModelSqlite> arrayList = new ArrayList<DataModelSqlite>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskPostDataFromSQliteToServer(ArrayList<DataModelSqlite> arrayList, Context context) {

            this.arrayList = arrayList;
            this.ctx = context;

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            for (int i = 0; i < arrayList.size(); i++) {
                DataModelSqlite dataModelSqlite = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("card_number", dataModelSqlite.getCard_number());
                    JO.put("cashier_id", dataModelSqlite.getCashier_id());
                    JO.put("civility", dataModelSqlite.getCivility());
                    JO.put("gender", dataModelSqlite.getGender());
                    JO.put("name", dataModelSqlite.getName());
                    JO.put("first_name", dataModelSqlite.getFirst_name());
                    JO.put("birth_date", dataModelSqlite.getBirth_date());
                    JO.put("age", dataModelSqlite.getAge());
                    JO.put("society", dataModelSqlite.getSociety());
                    JO.put("activity", dataModelSqlite.getActivity());
                    JO.put("address_line1", dataModelSqlite.getAddress_line1());
                    JO.put("address_line2", dataModelSqlite.getAddress_line2());
                    JO.put("postal_code", dataModelSqlite.getPostal_code());
                    JO.put("city", dataModelSqlite.getCity());
                    JO.put("country", dataModelSqlite.getCountry());
                    JO.put("phone", dataModelSqlite.getPhone());
                    JO.put("email", dataModelSqlite.getEmail());
                    JO.put("created_by", dataModelSqlite.getCreated_by());
                    JO.put("created_on", dataModelSqlite.getCreated_on());
                    JO.put("mi_barcode", dataModelSqlite.getMi_barcode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(JO);

                try {
                    jsonObject.put("MemberList", jsonArray);
                    Log.e("GetJsonObject ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(InsertAllMemberFromAppToServer), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServer :", "" + JsonResponse);

                        if (JsonResponse.equals("Member Saved Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            AllMemberModel allMemberModel = new AllMemberModel();

                            allMemberModel.setSync_status("NotSync");
                            allMemberModel.setCr_window(true);
                            databaseHelper.UpdateCr_Window(allMemberModel);
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

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void GetInformationAsArrayListFromSqliteDatabaseForSendToServerCheckUpWindow() {
        allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
        AllMemberModelByCardNumberOnly allMemberModelByCardMiBarcode = new AllMemberModelByCardNumberOnly();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allMemberModelByCardMiBarcodes = databaseHelper.GetInformationReturnArraylistForUpdateMemberToSerVer("Checked");
        for (int i = 0; i < allMemberModelByCardMiBarcodes.size(); i++) {
            allMemberModelByCardMiBarcode = allMemberModelByCardMiBarcodes.get(i);
            age = allMemberModelByCardMiBarcode.getAge();
            cashier_id = allMemberModelByCardMiBarcode.getCashier_id();
            card_number = allMemberModelByCardMiBarcode.getCard_number();
            civility = allMemberModelByCardMiBarcode.getCivility();
            gender = allMemberModelByCardMiBarcode.getGender();
            name = allMemberModelByCardMiBarcode.getName();
            first_name = allMemberModelByCardMiBarcode.getFirst_name();
            society = allMemberModelByCardMiBarcode.getSociety();
            activity = allMemberModelByCardMiBarcode.getActivity();
            address_line1 = allMemberModelByCardMiBarcode.getAddress_line1();
            address_line2 = allMemberModelByCardMiBarcode.getAddress_line2();
            postal_code = allMemberModelByCardMiBarcode.getPostal_code();
            city = allMemberModelByCardMiBarcode.getCity();
            country = allMemberModelByCardMiBarcode.getCountry();
            phone = allMemberModelByCardMiBarcode.getPhone();
            email = allMemberModelByCardMiBarcode.getEmail();
            mi_barcode = allMemberModelByCardMiBarcode.getMi_barcode();
            created_by = allMemberModelByCardMiBarcode.getCreated_by();
            created_on = allMemberModelByCardMiBarcode.getCreated_on();
            birth_date = allMemberModelByCardMiBarcode.getBirth_date();
            no_of_points = String.valueOf(allMemberModelByCardMiBarcode.getNo_of_points());
            Log.e("GetNoOfPointsFromSQL :", "" + no_of_points);

            UpdateDataModel dataModelSqlite = new UpdateDataModel();
            dataModelSqlite.setAge(age);
            dataModelSqlite.setCashier_id(cashier_id);
            dataModelSqlite.setCard_number(card_number);
            dataModelSqlite.setCivility(civility);
            dataModelSqlite.setGender(gender);
            dataModelSqlite.setName(name);
            dataModelSqlite.setFirst_name(first_name);
            dataModelSqlite.setSociety(society);
            dataModelSqlite.setActivity(activity);
            dataModelSqlite.setAddress_line1(address_line1);
            dataModelSqlite.setAddress_line2(address_line2);
            dataModelSqlite.setPostal_code(postal_code);
            dataModelSqlite.setCity(city);
            dataModelSqlite.setCountry(country);
            dataModelSqlite.setPhone(phone);
            dataModelSqlite.setEmail(email);
            dataModelSqlite.setNo_of_points(no_of_points);
            dataModelSqlite.setMi_barcode(mi_barcode);
            dataModelSqlite.setCreated_by(created_by);
            dataModelSqlite.setCreated_on(created_on);
            dataModelSqlite.setBirth_date(birth_date);

            dataModelArrayList = new ArrayList<UpdateDataModel>();
            dataModelArrayList.add(dataModelSqlite);
            new BackGroundTaskUpdateMemberInSqliteThenToServer(dataModelArrayList, getApplicationContext()).execute();
        }

    }

    public class BackGroundTaskUpdateMemberInSqliteThenToServer extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<UpdateDataModel> arrayList = new ArrayList<UpdateDataModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackGroundTaskUpdateMemberInSqliteThenToServer(ArrayList<UpdateDataModel> arrayList, Context ctx) {
            this.arrayList = arrayList;
            this.ctx = ctx;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {

                UpdateDataModel dataModelSqlite = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("card_number", dataModelSqlite.getCard_number());
                    JO.put("cashier_id", dataModelSqlite.getCashier_id());
                    JO.put("civility", dataModelSqlite.getCivility());
                    JO.put("gender", dataModelSqlite.getGender());
                    JO.put("name", dataModelSqlite.getName());
                    JO.put("first_name", dataModelSqlite.getFirst_name());
                    JO.put("birth_date", dataModelSqlite.getBirth_date().replaceAll("\\\\", ""));
                    JO.put("age", dataModelSqlite.getAge());
                    JO.put("society", dataModelSqlite.getSociety());
                    JO.put("activity", dataModelSqlite.getActivity());
                    JO.put("address_line1", dataModelSqlite.getAddress_line1());
                    JO.put("address_line2", dataModelSqlite.getAddress_line2());
                    JO.put("postal_code", dataModelSqlite.getPostal_code());
                    JO.put("city", dataModelSqlite.getCity());
                    JO.put("country", dataModelSqlite.getCountry());
                    JO.put("phone", dataModelSqlite.getPhone());
                    JO.put("email", dataModelSqlite.getEmail());
                    JO.put("no_of_points", dataModelSqlite.getNo_of_points());
                    JO.put("created_by", dataModelSqlite.getCreated_by());
                    JO.put("created_on", dataModelSqlite.getCreated_on());
                    JO.put("mi_barcode", dataModelSqlite.getMi_barcode());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(JO);

                try {
                    jsonObject.put("MemberList", jsonArray);
                    Log.e("GetJsonObject ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return jsonObject;

        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(UpdateAllMemberFromAppToServer), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerForUpdateMember :", "" + JsonResponse);

                        if (JsonResponse.equals("Member Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsync :", "" + jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("MemberList");
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsync :", "" + JO);
                                        Log.e("GetAllCardNumberInAsync :", "" + JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setUp_window(true);
                                        databaseHelper.UpdateUp_Window(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }

    }

    @SuppressLint("LongLogTag")
    public void GetInformationFromPointsRewardTable() {
        allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
        AllMemberModelByCardNumberOnly allMemberModelByCardNumber = new AllMemberModelByCardNumberOnly();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allMemberModelByCardMiBarcodes = databaseHelper.GetInformationReturnArraylistCreditPointRewardTable("NotChecked");
        for (int i = 0; i < allMemberModelByCardMiBarcodes.size(); i++) {
            allMemberModelByCardNumber = allMemberModelByCardMiBarcodes.get(i);

            reward_id = String.valueOf(allMemberModelByCardNumber.getReward_id());
            Log.e("GetPointFromRewardTable :", "" + reward_id);
            member_id = String.valueOf(allMemberModelByCardNumber.getMember_id());
            no_of_points = String.valueOf(allMemberModelByCardNumber.getNo_of_points());
            comment = allMemberModelByCardNumber.getComment();
            created_by = allMemberModelByCardNumber.getCreated_by();
            created_on = allMemberModelByCardNumber.getCreated_on();
            updated_by = allMemberModelByCardNumber.getUpdated_by();
            updated_on = allMemberModelByCardNumber.getUpdated_by();
            reward_type = allMemberModelByCardNumber.getReward_type();
            cashier_id = allMemberModelByCardNumber.getCashier_id();
            card_number = allMemberModelByCardNumber.getCard_number();

            RewardPointModel rewardPointModel = new RewardPointModel();
            rewardPointModel.setReward_id(Integer.parseInt(reward_id));
            rewardPointModel.setMember_id(Integer.parseInt(member_id));
            rewardPointModel.setPoints_gained(no_of_points);
            rewardPointModel.setComment(comment);
            rewardPointModel.setCreated_by(created_by);
            rewardPointModel.setCreated_on(created_on);
            rewardPointModel.setUpdated_by(updated_by);
            rewardPointModel.setUpdated_on(updated_on);
            rewardPointModel.setReward_type(reward_type);
            rewardPointModel.setCashier_id(cashier_id);
            rewardPointModel.setCard_number(card_number);

            RewardPointModelArrayList = new ArrayList<RewardPointModel>();
            RewardPointModelArrayList.add(rewardPointModel);
            new BackgroudTaskSendPointToServerFromRewardTable(RewardPointModelArrayList, getApplicationContext()).execute();

        }
    }

    public class BackgroudTaskSendPointToServerFromRewardTable extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<RewardPointModel> arrayList = new ArrayList<RewardPointModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroudTaskSendPointToServerFromRewardTable(ArrayList<RewardPointModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }


        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {

                RewardPointModel rewardPointModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("reward_id", rewardPointModel.getReward_id());
                    JO.put("member_id", rewardPointModel.getMember_id());
                    JO.put("points_gained", rewardPointModel.getPoints_gained());
                    JO.put("comment", rewardPointModel.getComment());
                    JO.put("created_by", rewardPointModel.getCreated_by());
                    JO.put("created_on", rewardPointModel.getCreated_on());
                    JO.put("updated_by", rewardPointModel.getUpdated_by());
                    JO.put("updated_on", rewardPointModel.getUpdated_on());
                    JO.put("reward_type", rewardPointModel.getReward_type());
                    JO.put("cashier_id", rewardPointModel.getCashier_id());
                    JO.put("card_number", rewardPointModel.getCard_number());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(JO);

                try {
                    jsonObject.put("objMemRewardList", jsonArray);
                    Log.e("GetJsonObjectCreditPoint :", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(InsertAllMemberRewardFromAppToServer), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerForUpdateMember :", "" + JsonResponse);

                        if (JsonResponse.equals("Reward Added Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsync :", "" + jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("objMemRewardList");
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsync :", "" + JO);
                                        Log.e("GetAllCardNumberInAsync :", "" + JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setCr_window(true);
                                        databaseHelper.UpdateCr_WindowForReward(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void ViewAllGiftProductList() {
        if (InternetUtil.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllGiftProductListUrl), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Log.e("ViewAllGiftList :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        JSONArray JA = JO.getJSONArray("results");
                        giftProductModelList.clear();
                        Log.e("ResultJAGiftProduct :", "" + JA);

                        if (JA != null) {
                            giftProductModelList = new ArrayList<GiftProductModel>();
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject jsonObject = JA.getJSONObject(i);

                                GiftProductModel giftProductModel = new GiftProductModel();
                                giftProductModel.setAuto_id(Integer.parseInt(jsonObject.getString("auto_id")));
                                giftProductModel.setUpdated_by(jsonObject.getString("updated_by"));
                                giftProductModel.setUpdate_on(jsonObject.getString("updated_on"));
                                giftProductModel.setGift_product_name(jsonObject.getString("gift_product_name"));
                                giftProductModelList.add(giftProductModel);

                                AllMemberModel allMemberModel = new AllMemberModel();

                                allMemberModel.setAuto_id(Integer.parseInt(jsonObject.getString("auto_id")));
                                allMemberModel.setGift_product_name(jsonObject.getString("gift_product_name"));
                                allMemberModel.setCustomer_id("");
                                allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                                allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                                allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                                allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                                allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                                allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                                allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                                allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                                allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                                allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));

                                if (jsonObject.getBoolean("cr_server") == false) {
                                    databaseHelper = new DatabaseHelper(getApplicationContext());
                                    databaseHelper.insertViewAllGiftProduct(allMemberModel);
                                    new BackgroundTaskUpdateGiftProductList(giftProductModelList, getApplicationContext()).execute();

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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public class BackgroundTaskUpdateGiftProductList extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<GiftProductModel> arrayList = new ArrayList<GiftProductModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskUpdateGiftProductList(ArrayList<GiftProductModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {
                GiftProductModel giftProductModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("auto_id", giftProductModel.getAuto_id());
                    JO.put("updated_by", giftProductModel.getUpdated_by());
                    JO.put("updated_on", giftProductModel.getUpdate_on());
                    JO.put("gift_product_name", giftProductModel.getGift_product_name());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("objGiftProductModel", jsonArray);
                    Log.e("GetJsonObjectGiftProductList ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateGiftProductListUrl), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Gift Product List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("objGiftProductModel");
                                Log.e("GetJsonArrayInAsyncForGift :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetAllCardNumberInAsyncForGift :", "" + JO.getString("auto_id"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setAuto_id(Integer.parseInt(JO.getString("auto_id")));
                                        allMemberModel.setCr_server(true);
                                        databaseHelper.UpdateCr_serverForGift(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void UpdateViewAllGiftProductList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllGiftProductListUpdatedInWebNotInApp)
                , new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {

                Log.e("ViewAllGiftUpdateList :", "" + response);

                try {
                    JSONObject JO = new JSONObject(response);
                    JSONArray JA = JO.getJSONArray("results");
                    giftProductModelList.clear();
                    Log.e("ResultJAGiftProductUpdate :", "" + JA);

                    if (JA != null) {
                        giftProductModelList = new ArrayList<GiftProductModel>();
                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            GiftProductModel giftProductModel = new GiftProductModel();
                            giftProductModel.setAuto_id(Integer.parseInt(jsonObject.getString("auto_id")));
                            giftProductModel.setUpdated_by(jsonObject.getString("updated_by"));
                            giftProductModel.setUpdate_on(jsonObject.getString("updated_on"));
                            giftProductModel.setGift_product_name(jsonObject.getString("gift_product_name"));
                            giftProductModelList.add(giftProductModel);

                            AllMemberModel allMemberModel = new AllMemberModel();

                            allMemberModel.setAuto_id(Integer.parseInt(jsonObject.getString("auto_id")));
                            allMemberModel.setGift_product_name(jsonObject.getString("gift_product_name"));
                            allMemberModel.setCustomer_id("");
                            allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                            allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                            allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                            allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                            allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                            allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                            allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                            allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                            allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                            allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                            //Log.e("GetGiftProductListofUpServer :",""+allMemberModel.getAuto_id()+"\n"+allMemberModel.getGift_product_name());


                            if (jsonObject.getBoolean("up_server") == false) {
                                databaseHelper = new DatabaseHelper(getApplicationContext());
                                databaseHelper.UpdateViewAllGiftProductInSqlite(allMemberModel);
                                Log.e("GetGiftProductListofUpServer :", "" + allMemberModel.getAuto_id() + "\n" + allMemberModel.getGift_product_name());
                                new BackgroundTaskUpdateGiftProductListInSqlite(giftProductModelList, getApplicationContext()).execute();

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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> Headers = new HashMap<String, String>();
                Headers.put("Authorization-Token", getLoginToken);
                return Headers;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public class BackgroundTaskUpdateGiftProductListInSqlite extends AsyncTask<Void, Void, JSONObject> {

        ArrayList<GiftProductModel> arrayList = new ArrayList<GiftProductModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskUpdateGiftProductListInSqlite(ArrayList<GiftProductModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {
                GiftProductModel giftProductModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("auto_id", giftProductModel.getAuto_id());
                    JO.put("updated_by", giftProductModel.getUpdated_by());
                    JO.put("updated_on", giftProductModel.getUpdate_on());
                    JO.put("gift_product_name", giftProductModel.getGift_product_name());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("objGiftProductModel", jsonArray);
                    Log.e("GetJsonObjectGiftProductListUpdate ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateAllGiftProductListServerTrueUpdatedInApp), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Gift Product List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("objGiftProductModel");
                                Log.e("GetJsonArrayInAsyncForGiftUpdate :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetAllCardNumberInAsyncForGiftUpdate :", "" + JO.getString("auto_id"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setAuto_id(Integer.parseInt(JO.getString("auto_id")));
                                        allMemberModel.setCr_server(true);
                                        databaseHelper.UpdateUP_ServerForGift(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void ViewAllReasonMaster() {
        if (InternetUtil.isConnected(getApplicationContext())) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ReasonMasterUrl), new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("ViewAllReasonMasterList :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        JSONArray JA = JO.getJSONArray("results");
                        reasonMasterModelList.clear();
                        Log.e("ResultJAReasonMaster :", "" + JA);

                        if (JA != null) {
                            reasonMasterModelList = new ArrayList<ReasonMasterModel>();
                            for (int i = 0; i < JA.length(); i++) {
                                JSONObject jsonObject = JA.getJSONObject(i);

                                ReasonMasterModel reasonMasterModel = new ReasonMasterModel();
                                reasonMasterModel.setReason_id(Integer.parseInt(jsonObject.getString("reason_id")));
                                reasonMasterModel.setUpdated_by(jsonObject.getString("updated_by"));
                                reasonMasterModel.setUpdate_on(jsonObject.getString("updated_on"));
                                reasonMasterModel.setReason(jsonObject.getString("reason"));
                                reasonMasterModelList.add(reasonMasterModel);

                                AllMemberModel allMemberModel = new AllMemberModel();

                                allMemberModel.setAuto_id(Integer.parseInt(jsonObject.getString("reason_id")));
                                allMemberModel.setGift_product_name(jsonObject.getString("reason"));
                                allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                                allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                                allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                                allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                                allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                                allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                                allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                                allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                                allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                                allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                                allMemberModel.setCustomer_id("");

                                if (jsonObject.getBoolean("cr_server") == false) {
                                    databaseHelper = new DatabaseHelper(getApplicationContext());
                                    databaseHelper.insertViewAllReasonMaster(allMemberModel);
                                    new BackgroundTaskUpdateReasonMasterList(reasonMasterModelList, getApplicationContext()).execute();

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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        } else {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    public class BackgroundTaskUpdateReasonMasterList extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<ReasonMasterModel> arrayList = new ArrayList<ReasonMasterModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskUpdateReasonMasterList(ArrayList<ReasonMasterModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {
                ReasonMasterModel reasonMasterModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("reason_id", reasonMasterModel.getReason_id());
                    JO.put("updated_by", reasonMasterModel.getUpdated_by());
                    JO.put("updated_on", reasonMasterModel.getUpdate_on());
                    JO.put("reason", reasonMasterModel.getReason());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("objReasonMaster", jsonArray);
                    Log.e("GetJsonObjectReasonList ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateReasonMasterUrl), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Reason List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("objReasonMaster");
                                Log.e("GetJsonArrayInAsyncForReason :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetAllCardNumberInAsyncForReason :", "" + JO.getString("reason_id"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setReason_id(Integer.parseInt(JO.getString("reason_id")));
                                        allMemberModel.setCr_server(true);
                                        databaseHelper.UpdateCr_serverForReason(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void UpdateViewAllReasonMasterList() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllReasonListUpdatedInWebNotInApp)
                , new Response.Listener<String>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(String response) {

                Log.e("ViewAllGiftUpdateList :", "" + response);

                try {
                    JSONObject JO = new JSONObject(response);
                    JSONArray JA = JO.getJSONArray("results");
                    reasonMasterModelList.clear();
                    Log.e("ResultJAGiftProductUpdate :", "" + JA);

                    if (JA != null) {
                        reasonMasterModelList = new ArrayList<ReasonMasterModel>();
                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            ReasonMasterModel reasonMasterModel = new ReasonMasterModel();
                            reasonMasterModel.setReason_id(Integer.parseInt(jsonObject.getString("reason_id")));
                            reasonMasterModel.setUpdated_by(jsonObject.getString("updated_by"));
                            reasonMasterModel.setUpdate_on(jsonObject.getString("updated_on"));
                            reasonMasterModel.setReason(jsonObject.getString("reason"));
                            reasonMasterModelList.add(reasonMasterModel);

                            AllMemberModel allMemberModel = new AllMemberModel();

                            allMemberModel.setAuto_id(Integer.parseInt(jsonObject.getString("reason_id")));
                            allMemberModel.setGift_product_name(jsonObject.getString("reason"));
                            allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                            allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                            allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                            allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                            allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                            allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                            allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                            allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                            allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                            allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                            allMemberModel.setCustomer_id("");

                            if (jsonObject.getBoolean("up_server") == false) {
                                databaseHelper = new DatabaseHelper(getApplicationContext());
                                databaseHelper.UpdateViewAllReasonMasterInSqlite(allMemberModel);
                                new BackgroundTaskUpdateReasonMasterListInSqlite(reasonMasterModelList, getApplicationContext()).execute();

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
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> Headers = new HashMap<String, String>();
                Headers.put("Authorization-Token", getLoginToken);
                return Headers;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public class BackgroundTaskUpdateReasonMasterListInSqlite extends AsyncTask<Void, Void, JSONObject> {

        ArrayList<ReasonMasterModel> arrayList = new ArrayList<ReasonMasterModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskUpdateReasonMasterListInSqlite(ArrayList<ReasonMasterModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for (int i = 0; i < arrayList.size(); i++) {
                ReasonMasterModel reasonMasterModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("reason_id", reasonMasterModel.getReason_id());
                    JO.put("updated_by", reasonMasterModel.getUpdated_by());
                    JO.put("updated_on", reasonMasterModel.getUpdate_on());
                    JO.put("reason", reasonMasterModel.getReason());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("objReasonMaster", jsonArray);
                    Log.e("GetJsonObjectReasonMasterListUpdate ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateAllReasonListServerTrueUpdatedInApp), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Reason List Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("objReasonMaster");
                                Log.e("GetJsonArrayInAsyncForReasonUpdate :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetAllCardNumberInAsyncForReasonUpdate :", "" + JO.getString("reason_id"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setReason_id(Integer.parseInt(JO.getString("reason_id")));
                                        allMemberModel.setUp_server(true);
                                        databaseHelper.UpdateUP_ServerForReason(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void ViewAllZipCode() {
        boolean check;
        databaseHelper = new DatabaseHelper(getApplicationContext());

        check = databaseHelper.CheckIsInDBorNot();
        if (check == true) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ViewAllZipCodeListInsertedInWeb)
                    , new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("GetJsonResponseZipCode :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        JSONArray JA = JO.getJSONArray("results");
                        AllMemberModel allMemberModel = null;

                        //InsertZipArrayList.clear();
                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setAuto_id(Integer.parseInt(jsonObject.getString("auto_id")));
                            allMemberModel.setCity(jsonObject.getString("city"));
                            allMemberModel.setZip_code(Integer.parseInt(jsonObject.getString("postal_code")));
                            allMemberModel.setDepartment(jsonObject.getString("department"));
                            allMemberModel.setGeographical_code(Integer.parseInt(jsonObject.getString("geographical_code")));
                            allMemberModel.setCountry(jsonObject.getString("country"));
                            allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                            allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                            InsertZipArrayList = new ArrayList<AllMemberModel>();
                            InsertZipArrayList.add(allMemberModel);
                            new BackGroundTaskForInsertZipCode(InsertZipArrayList,getApplicationContext()).execute();
                        }

                    }
                    catch (JSONException e) {
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
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

    }

    public class BackGroundTaskForInsertZipCode extends AsyncTask<Void,Void,ArrayList<AllMemberModel>>{

        ArrayList<AllMemberModel>arrayList = new ArrayList<AllMemberModel>();
        Context context;
        DatabaseHelper databaseHelper = null;

        public BackGroundTaskForInsertZipCode(ArrayList<AllMemberModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
            databaseHelper = new DatabaseHelper(context);
        }

        @Override
        protected ArrayList<AllMemberModel> doInBackground(Void... voids) {
            for(int i = 0;i<arrayList.size();i++)
            {
                AllMemberModel allMemberModel = arrayList.get(i);
                auto_id = String.valueOf(allMemberModel.getAuto_id());
                city = allMemberModel.getCity();
                postal_code = allMemberModel.getPostal_code();
                department = allMemberModel.getDepartment();
                geographical_code = String.valueOf(allMemberModel.getGeographical_code());
                country = allMemberModel.getCountry();
                created_by = allMemberModel.getCreated_by();
                created_on = allMemberModel.getCreated_on();

                AllMemberModel zipCodeModel = new AllMemberModel();
                zipCodeModel.setAuto_id(Integer.parseInt(auto_id));
                zipCodeModel.setCity(city);
                zipCodeModel.setPostal_code(postal_code);
                zipCodeModel.setDepartment(department);
                zipCodeModel.setGeographical_code(Integer.parseInt(geographical_code));
                zipCodeModel.setCountry(country);
                zipCodeModel.setCreated_by(created_by);
                zipCodeModel.setCreated_on(created_on);

                ZipCodeArrayList = new ArrayList<AllMemberModel>();
                ZipCodeArrayList.add(zipCodeModel);

                return ZipCodeArrayList;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<AllMemberModel> allMemberModels) {

            databaseHelper.insertZipCodeInformation(allMemberModels);
        }
    }

    public void ViewCivilityList() {

        boolean check;
        databaseHelper = new DatabaseHelper(getApplicationContext());

        check = databaseHelper.CheckCivilityDbExistsOrNot();
        if (check == true) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(CivilityList)
                    , new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("GetJsonResponseCivilityList :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        JSONArray JA = JO.getJSONArray("results");

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            AllMemberModel allMemberModel = new AllMemberModel();

                            allMemberModel.setText(jsonObject.getString("Text"));
                            allMemberModel.setValue(jsonObject.getString("Value"));

                            databaseHelper.insertCivilityListIntoSqlite(allMemberModel);
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
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }

    }

    public void ViewActivityList() {

        boolean check;
        databaseHelper = new DatabaseHelper(getApplicationContext());

        check = databaseHelper.CheckActivityDbExistsOrNot();
        if (check == true) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(ActivityList)
                    , new Response.Listener<String>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(String response) {

                    Log.e("GetJsonResponseActivityList :", "" + response);

                    try {
                        JSONObject JO = new JSONObject(response);
                        JSONArray JA = JO.getJSONArray("results");

                        for (int i = 0; i < JA.length(); i++) {
                            JSONObject jsonObject = JA.getJSONObject(i);

                            AllMemberModel allMemberModel = new AllMemberModel();

                            allMemberModel.setText(jsonObject.getString("Text"));
                            allMemberModel.setValue(jsonObject.getString("Value"));

                            databaseHelper.insertActivityListIntoSqlite(allMemberModel);
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
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void GetSatisfactionVoucherListFromSqalite() {
        SatisfactionArrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel = new AllMemberModel();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        SatisfactionArrayList = databaseHelper.GetInformationReturnArraylistGiftVoucherTable("NotChecked");

        for(int i=0;i<SatisfactionArrayList.size();i++)
        {
            allMemberModel = SatisfactionArrayList.get(i);

            card_number = allMemberModel.getCard_number();
            name = allMemberModel.getName();
            first_name = allMemberModel.getFirst_name();
            phone = allMemberModel.getPhone();
            email = allMemberModel.getEmail();
            gift_product_name = allMemberModel.getGift_product_name();
            reason = allMemberModel.getReason();
            created_by = allMemberModel.getCreated_by();
            created_on = allMemberModel.getCreated_on();
            cashier_id = allMemberModel.getCashier_id();

            SatisfactionVoucherModel satisfactionVoucherModel = new SatisfactionVoucherModel();
            satisfactionVoucherModel.setCard_number(card_number);
            satisfactionVoucherModel.setName(name);
            satisfactionVoucherModel.setFirst_name(first_name);
            satisfactionVoucherModel.setPhone(phone);
            satisfactionVoucherModel.setEmail(email);
            satisfactionVoucherModel.setGift_product_name(gift_product_name);
            satisfactionVoucherModel.setReason(reason);
            satisfactionVoucherModel.setCreated_by(created_by);
            satisfactionVoucherModel.setCreated_on(created_on);
            satisfactionVoucherModel.setCashier_id(cashier_id);

            satisfactionVoucherModelList = new ArrayList<SatisfactionVoucherModel>();
            satisfactionVoucherModelList.add(satisfactionVoucherModel);
            new BackGroundTaskForSendVoucherListAppToServer(satisfactionVoucherModelList,getApplicationContext()).execute();

        }
    }

    public class BackGroundTaskForSendVoucherListAppToServer extends AsyncTask<Void,Void,JSONObject>{

        ArrayList<SatisfactionVoucherModel> arrayList = new ArrayList<SatisfactionVoucherModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackGroundTaskForSendVoucherListAppToServer(ArrayList<SatisfactionVoucherModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            for(int i=0;i<arrayList.size();i++)
            {

                SatisfactionVoucherModel satisfactionVoucherModel = arrayList.get(i);
                JSONObject JO = new JSONObject();
                try {

                    JO.put("card_number",satisfactionVoucherModel.getCard_number());
                    JO.put("name",satisfactionVoucherModel.getName());
                    JO.put("first_name",satisfactionVoucherModel.getFirst_name());
                    JO.put("phone",satisfactionVoucherModel.getPhone());
                    JO.put("email",satisfactionVoucherModel.getEmail());
                    JO.put("gift_product_name",satisfactionVoucherModel.getGift_product_name());
                    JO.put("reason",satisfactionVoucherModel.getReason());
                    JO.put("created_by",satisfactionVoucherModel.getCreated_by());
                    JO.put("created_on",satisfactionVoucherModel.getCreated_on());
                    JO.put("cashier_id",satisfactionVoucherModel.getCashier_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                jsonArray.put(JO);

                try {
                    jsonObject.put("LCDC_gift_voucherForAppModelList",jsonArray);
                    Log.e("GetJsonObjectGiftVoucher :",""+jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(InsertAllSatisfactionVoucherAppToServer), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerVoucherCreation :",""+JsonResponse);

                        if(JsonResponse.equals("Satisfaction Voucher Created Successfully!"))
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsyncVoucherCreation :",""+jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("LCDC_gift_voucherForAppModelList");
                                if(JA!=null)
                                {
                                    for (int i=0;i<JA.length();i++)
                                    {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsyncVoucherCreation :",""+JO);
                                        Log.e("GetAllCardNumberInAsyncVoucherCreation :",""+JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setCr_window(true);
                                        databaseHelper.UpdateCr_WindowForGiftVoucher(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void GetGiftCardDetailsListFromSqliteDatabase() {
        GiftCardDetailsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        GiftCardDetailsArrayList = databaseHelper.GetGiftCardDetailsInformationFromSqlite("NotSync");

        GiftCardArraylist = new ArrayList<GiftCardDetailsModel>();
        for(int i=0;i<GiftCardDetailsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = GiftCardDetailsArrayList.get(i);
            gift_card_id = String.valueOf(allMemberModel.getGift_card_id());
            card_number = allMemberModel.getCard_number();
            name = allMemberModel.getName();
            first_name = allMemberModel.getFirst_name();
            phone = allMemberModel.getPhone();
            email = allMemberModel.getEmail();
            address = allMemberModel.getAddress();
            postal_code = allMemberModel.getPostal_code();
            city = allMemberModel.getCity();
            country = allMemberModel.getCountry();
            total_amount = String.valueOf(allMemberModel.getTotal_amount());
            created_by = allMemberModel.getCreated_by();
            created_on = allMemberModel.getCreated_on();
            updated_by = allMemberModel.getUpdated_by();
            updated_on = allMemberModel.getUpdated_on();
            cashier_id = allMemberModel.getCashier_id();

            GiftCardDetailsModel giftCardDetailsModel = new GiftCardDetailsModel();
            giftCardDetailsModel.setGift_card_id(gift_card_id);
            giftCardDetailsModel.setCard_number(card_number);
            giftCardDetailsModel.setName(name);
            giftCardDetailsModel.setFirst_name(first_name);
            giftCardDetailsModel.setPhone(phone);
            giftCardDetailsModel.setEmail(email);
            giftCardDetailsModel.setAddress(address);
            giftCardDetailsModel.setPostal_code(postal_code);
            giftCardDetailsModel.setCity(city);
            giftCardDetailsModel.setCountry(country);
            giftCardDetailsModel.setTotal_amount(total_amount);
            giftCardDetailsModel.setCreated_by(created_by);
            giftCardDetailsModel.setCreated_on(created_on);
            giftCardDetailsModel.setUpdated_by(updated_by);
            giftCardDetailsModel.setUpdated_on(updated_on);
            giftCardDetailsModel.setCashier_id(cashier_id);

            GiftCardArraylist.add(giftCardDetailsModel);
            new BackGroundTaskForSendGiftCardDetailsListAppToServer(GiftCardArraylist,getApplicationContext()).execute();
        }
    }

    public class BackGroundTaskForSendGiftCardDetailsListAppToServer extends AsyncTask<Void,Void,JSONObject>{

        ArrayList<GiftCardDetailsModel> arrayList = new ArrayList<GiftCardDetailsModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        public BackGroundTaskForSendGiftCardDetailsListAppToServer(ArrayList<GiftCardDetailsModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject JO = new JSONObject();
            for(int i=0;i<arrayList.size();i++)
            {

                GiftCardDetailsModel giftCardDetailsModel = arrayList.get(i);
                try {

                    JO.put("gift_card_id",giftCardDetailsModel.getGift_card_id());
                    JO.put("card_number",giftCardDetailsModel.getCard_number());
                    JO.put("name",giftCardDetailsModel.getName());
                    JO.put("first_name",giftCardDetailsModel.getFirst_name());
                    JO.put("phone",giftCardDetailsModel.getPhone());
                    JO.put("email",giftCardDetailsModel.getEmail());
                    JO.put("address",giftCardDetailsModel.getAddress());
                    JO.put("postal_code",giftCardDetailsModel.getPostal_code());
                    JO.put("city",giftCardDetailsModel.getCity());
                    JO.put("country",giftCardDetailsModel.getCountry());
                    JO.put("total_amount",giftCardDetailsModel.getTotal_amount());
                    JO.put("created_by",giftCardDetailsModel.getCreated_by());
                    JO.put("created_on",giftCardDetailsModel.getCreated_on());
                    JO.put("updated_by",giftCardDetailsModel.getUpdated_by());
                    JO.put("updated_on",giftCardDetailsModel.getUpdated_on());
                    JO.put("cashier_id",giftCardDetailsModel.getCashier_id());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            jsonArray.put(JO);

            try {
                jsonObject.put("objGiftCardModel",jsonArray);
                Log.e("GetJsonObjectGiftCard :",""+jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(InsertAllGiftCardInsertedInAppNotInWeb), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerGiftCardMemberCreation :",""+JsonResponse);

                        if(JsonResponse.equals("Gift Card Created Successfully!"))
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsyncGiftCardMemberCreation :",""+jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("objGiftCardModel");
                                if(JA!=null)
                                {
                                    for (int i=0;i<JA.length();i++)
                                    {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsyncGiftCardMemberCreation :",""+JO);
                                        Log.e("GetAllCardNumberInAsyncGiftCardMemberCreation :",""+JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setCr_window(true);
                                        databaseHelper.UpdateCr_WindowForGiftCardDetails(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void GetGiftCardListFromSqliteDatabase() {
        GiftCardDetailsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        GiftCardDetailsArrayList = databaseHelper.GetGiftCardInformationFromSqlite("NotSync");

        GiftCardArraylist = new ArrayList<GiftCardDetailsModel>();
        for (int i = 0; i < GiftCardDetailsArrayList.size(); i++) {
            AllMemberModel allMemberModel = GiftCardDetailsArrayList.get(i);
            gift_card_id = String.valueOf(allMemberModel.getGift_card_id());
            card_number = allMemberModel.getCard_number();
            amount = String.valueOf(allMemberModel.getAmount());
            credit_debit = allMemberModel.getCredit_debit();
            created_by = allMemberModel.getCreated_by();
            created_on = allMemberModel.getCreated_on();

            GiftCardDetailsModel giftCardDetailsModel = new GiftCardDetailsModel();
            giftCardDetailsModel.setGift_card_id(gift_card_id);
            giftCardDetailsModel.setCard_number(card_number);
            giftCardDetailsModel.setAmount(amount);
            giftCardDetailsModel.setCredit_debit(credit_debit);
            giftCardDetailsModel.setCreated_by(created_by);
            giftCardDetailsModel.setCreated_on(created_on);

            GiftCardArraylist.add(giftCardDetailsModel);
            new BackGroundTaskForSendGiftCardListAppToServer(GiftCardArraylist,getApplicationContext()).execute();
        }
    }

    public class BackGroundTaskForSendGiftCardListAppToServer extends AsyncTask<Void,Void,JSONObject>{

        ArrayList<GiftCardDetailsModel> arrayList = new ArrayList<GiftCardDetailsModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        public BackGroundTaskForSendGiftCardListAppToServer(ArrayList<GiftCardDetailsModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject JO = new JSONObject();
            for(int i=0;i<arrayList.size();i++)
            {

                GiftCardDetailsModel giftCardDetailsModel = arrayList.get(i);
                try {

                    JO.put("gift_card_id",giftCardDetailsModel.getGift_card_id());
                    JO.put("card_number",giftCardDetailsModel.getCard_number());
                    JO.put("amount",giftCardDetailsModel.getAmount());
                    JO.put("credit_debit",giftCardDetailsModel.getCredit_debit());
                    JO.put("created_by",giftCardDetailsModel.getCreated_by());
                    JO.put("created_on",giftCardDetailsModel.getCreated_on());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            jsonArray.put(JO);

            try {
                jsonObject.put("objGiftCardDetailsModel",jsonArray);
                Log.e("GetJsonObjectGiftCard :",""+jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(InsertAllGiftCardDetailsInsertedInAppNotInWeb), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerGiftCardMemberCreation :",""+JsonResponse);

                        if(JsonResponse.equals("Gift Card Created Successfully!"))
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsyncGiftCardMemberCreation :",""+jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("objGiftCardDetailsModel");
                                if(JA!=null)
                                {
                                    for (int i=0;i<JA.length();i++)
                                    {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsyncGiftCardMemberCreation :",""+JO);
                                        Log.e("GetAllCardNumberInAsyncGiftCardMemberCreation :",""+JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setCr_window(true);
                                        databaseHelper.UpdateCr_WindowForGiftCard(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    public void GiftCardListDetailsForUpdateFromAppToServer(){

        GiftCardDetailsArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        GiftCardDetailsArrayList = databaseHelper.GetGiftCardDetailsInformationFromSqliteForUpdate("NotChecked");

        GiftCardArraylist = new ArrayList<GiftCardDetailsModel>();
        for(int i=0;i<GiftCardDetailsArrayList.size();i++)
        {
            AllMemberModel allMemberModel = GiftCardDetailsArrayList.get(i);
            gift_card_id = String.valueOf(allMemberModel.getGift_card_id());
            card_number = allMemberModel.getCard_number();
            name = allMemberModel.getName();
            first_name = allMemberModel.getFirst_name();
            phone = allMemberModel.getPhone();
            email = allMemberModel.getEmail();
            address = allMemberModel.getAddress();
            postal_code = allMemberModel.getPostal_code();
            city = allMemberModel.getCity();
            country = allMemberModel.getCountry();
            total_amount = String.valueOf(allMemberModel.getTotal_amount());
            created_by = allMemberModel.getCreated_by();
            created_on = allMemberModel.getCreated_on();
            updated_by = allMemberModel.getUpdated_by();
            updated_on = allMemberModel.getUpdated_on();
            cashier_id = allMemberModel.getCashier_id();

            GiftCardDetailsModel giftCardDetailsModel = new GiftCardDetailsModel();
            giftCardDetailsModel.setGift_card_id(gift_card_id);
            giftCardDetailsModel.setCard_number(card_number);
            giftCardDetailsModel.setName(name);
            giftCardDetailsModel.setFirst_name(first_name);
            giftCardDetailsModel.setPhone(phone);
            giftCardDetailsModel.setEmail(email);
            giftCardDetailsModel.setAddress(address);
            giftCardDetailsModel.setPostal_code(postal_code);
            giftCardDetailsModel.setCity(city);
            giftCardDetailsModel.setCountry(country);
            giftCardDetailsModel.setTotal_amount(total_amount);
            giftCardDetailsModel.setCreated_by(created_by);
            giftCardDetailsModel.setCreated_on(created_on);
            giftCardDetailsModel.setUpdated_by(updated_by);
            giftCardDetailsModel.setUpdated_on(updated_on);
            giftCardDetailsModel.setCashier_id(cashier_id);

            GiftCardArraylist.add(giftCardDetailsModel);
            new BackGroundTaskForSendGiftCardListForUpdateFromAppToServer(GiftCardArraylist,getApplicationContext()).execute();
        }

    }

    public class BackGroundTaskForSendGiftCardListForUpdateFromAppToServer extends AsyncTask<Void,Void,JSONObject>{

        ArrayList<GiftCardDetailsModel> arrayList = new ArrayList<GiftCardDetailsModel>();
        Context context;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray1 = new JSONArray();

        public BackGroundTaskForSendGiftCardListForUpdateFromAppToServer(ArrayList<GiftCardDetailsModel> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {
            JSONObject JO = new JSONObject();
            for(int i=0;i<arrayList.size();i++)
            {

                GiftCardDetailsModel giftCardDetailsModel = arrayList.get(i);
                try {

                    JO.put("gift_card_id",giftCardDetailsModel.getGift_card_id());
                    JO.put("card_number",giftCardDetailsModel.getCard_number());
                    JO.put("name",giftCardDetailsModel.getName());
                    JO.put("first_name",giftCardDetailsModel.getFirst_name());
                    JO.put("phone",giftCardDetailsModel.getPhone());
                    JO.put("email",giftCardDetailsModel.getEmail());
                    JO.put("address",giftCardDetailsModel.getAddress());
                    JO.put("postal_code",giftCardDetailsModel.getPostal_code());
                    JO.put("city",giftCardDetailsModel.getCity());
                    JO.put("country",giftCardDetailsModel.getCountry());
                    JO.put("total_amount",giftCardDetailsModel.getTotal_amount());
                    JO.put("created_by",giftCardDetailsModel.getCreated_by());
                    JO.put("created_on",giftCardDetailsModel.getCreated_on());
                    JO.put("updated_by",giftCardDetailsModel.getUpdated_by());
                    JO.put("updated_on",giftCardDetailsModel.getUpdated_on());
                    JO.put("cashier_id",giftCardDetailsModel.getCashier_id());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            jsonArray.put(JO);

            try {
                jsonObject.put("objGiftCardModel",jsonArray);
                Log.e("GetJsonObjectGiftCard :",""+jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    Config.Url.concat(UpdateAllGiftCardUpdatedInAppNotInWeb), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServerGiftCardMemberCreation :",""+JsonResponse);

                        if(JsonResponse.equals("Gift Card Updated Successfully!"))
                        {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            //allMemberModel.setSync_status("NotChecked");

                            try {
                                Log.e("JsonObjectInAsyncGiftCardMemberCreation :",""+jsonObject);
                                JSONArray JA = jsonObject.getJSONArray("objGiftCardModel");
                                if(JA!=null)
                                {
                                    for (int i=0;i<JA.length();i++)
                                    {
                                        JSONObject JO = JA.getJSONObject(i);
                                        Log.e("GetJsonObjectinAsyncGiftCardMemberCreation :",""+JO);
                                        Log.e("GetAllCardNumberInAsyncGiftCardMemberCreation :",""+JO.getString("card_number"));
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setCard_number(JO.getString("card_number"));
                                        allMemberModel.setUp_window(true);
                                        databaseHelper.UpdateUp_WindowForGiftCardDetails(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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

                    Map<String,String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token",getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void DeleteAllMemberFromLogTableAppToServer() {
        LogTableMemberDeleteArrayList = new ArrayList<AllMemberModel>();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        LogTableMemberDeleteArrayList = databaseHelper.GetInformationReturnArraylistFromLogTable("NotSync");

        deleteMemberModelArrayList = new ArrayList<DeleteMemberModel>();
        for (int i = 0; i < LogTableMemberDeleteArrayList.size(); i++) {
            AllMemberModel allMemberModel = LogTableMemberDeleteArrayList.get(i);
            age = allMemberModel.getAge();
            cashier_id = allMemberModel.getCashier_id();
            card_number = allMemberModel.getCard_number();
            civility = allMemberModel.getCivility();
            gender = allMemberModel.getGender();
            name = allMemberModel.getName();
            first_name = allMemberModel.getFirst_name();
            society = allMemberModel.getSociety();
            activity = allMemberModel.getActivity();
            address_line1 = allMemberModel.getAddress_line1();
            address_line2 = allMemberModel.getAddress_line2();
            postal_code = allMemberModel.getPostal_code();
            city = allMemberModel.getCity();
            country = allMemberModel.getCountry();
            phone = allMemberModel.getPhone();
            email = allMemberModel.getEmail();
            mi_barcode = allMemberModel.getMi_barcode();
            created_by = allMemberModel.getCreated_by();
            created_on = allMemberModel.getCreated_on();
            birth_date = allMemberModel.getBirth_date();

           DeleteMemberModel deleteMemberModel = new DeleteMemberModel();

            deleteMemberModel.setAge(age);
            deleteMemberModel.setCashier_id(cashier_id);
            deleteMemberModel.setCard_number(card_number);
            deleteMemberModel.setCivility(civility);
            deleteMemberModel.setGender(gender);
            deleteMemberModel.setName(name);
            deleteMemberModel.setFirst_name(first_name);
            deleteMemberModel.setSociety(society);
            deleteMemberModel.setActivity(activity);
            deleteMemberModel.setAddress_line1(address_line1);
            deleteMemberModel.setAddress_line2(address_line2);
            deleteMemberModel.setPostal_code(postal_code);
            deleteMemberModel.setCity(city);
            deleteMemberModel.setCountry(country);
            deleteMemberModel.setPhone(phone);
            deleteMemberModel.setEmail(email);
            deleteMemberModel.setMi_barcode(mi_barcode);
            deleteMemberModel.setCreated_by(created_by);
            deleteMemberModel.setCreated_on(created_on);
            deleteMemberModel.setBirth_date(birth_date);
            deleteMemberModelArrayList.add(deleteMemberModel);

            new BackgroundTaskPostDeleteMemberFromLogTableSQliteToServer(deleteMemberModelArrayList,getApplicationContext()).execute();


        }
    }

    public class BackgroundTaskPostDeleteMemberFromLogTableSQliteToServer extends AsyncTask<Void, Void, JSONObject> {

        ArrayList<DeleteMemberModel> arrayList = new ArrayList<DeleteMemberModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject JO = new JSONObject();

        public BackgroundTaskPostDeleteMemberFromLogTableSQliteToServer(ArrayList<DeleteMemberModel> arrayList, Context context) {

            this.arrayList = arrayList;
            this.ctx = context;

        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
             jsonObject = new JSONObject();
            for (int i = 0; i < arrayList.size(); i++) {
                DeleteMemberModel deleteMemberModel = arrayList.get(i);
                try {
                     JO = new JSONObject();
                    JO.put("card_number", deleteMemberModel.getCard_number());
                    JO.put("cashier_id", deleteMemberModel.getCashier_id());
                    JO.put("civility", deleteMemberModel.getCivility());
                    JO.put("gender", deleteMemberModel.getGender());
                    JO.put("name", deleteMemberModel.getName());
                    JO.put("first_name", deleteMemberModel.getFirst_name());
                    JO.put("birth_date", deleteMemberModel.getBirth_date());
                    JO.put("age", deleteMemberModel.getAge());
                    JO.put("society", deleteMemberModel.getSociety());
                    JO.put("activity", deleteMemberModel.getActivity());
                    JO.put("address_line1", deleteMemberModel.getAddress_line1());
                    JO.put("address_line2", deleteMemberModel.getAddress_line2());
                    JO.put("postal_code", deleteMemberModel.getPostal_code());
                    JO.put("city", deleteMemberModel.getCity());
                    JO.put("country", deleteMemberModel.getCountry());
                    JO.put("phone", deleteMemberModel.getPhone());
                    JO.put("email", deleteMemberModel.getEmail());
                    JO.put("created_by", deleteMemberModel.getCreated_by());
                    JO.put("created_on", deleteMemberModel.getCreated_on());
                    JO.put("mi_barcode", deleteMemberModel.getMi_barcode());
                    jsonArray.put(JO);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                jsonObject.put("MemberList", jsonArray);
                Log.e("GetJsonObject ", "" + jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return jsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    Config.Url.concat(DeleteAllMemberFromAppToServer), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponseFromServer :", "" + JsonResponse);

                        if (JsonResponse.equals("Member Deleted Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());
                            AllMemberModel allMemberModel = new AllMemberModel();
                            allMemberModel.setSync_status("NotSync");
                            allMemberModel.setCr_window(true);
                            databaseHelper.UpdateCr_WindowForLogTable(allMemberModel);
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

                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        }
    }

    private void viewAllVoucherListFromServerToAppDatabase() {
        if (InternetUtil.isConnected(getApplicationContext())) {

            Log.e("GetLoginToken_InMethod", "" + getLoginToken);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.Url.concat(GetAllVoucherListInsertedInServerNotInApp),
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //dialog.dismiss();

                            Log.e("ViewAllVoucherList :", "" + response);

                            try {
                                JSONObject JO = new JSONObject(response);
                                JSONArray JA = JO.getJSONArray("results");
                                voucherDetailsModelArrayList.clear();
                                Log.e("ResultJA :", "" + JA);

                                if (JA != null) {
                                    voucherDetailsModelArrayList = new ArrayList<VoucherDetailsModel>();

                                   for (int i = 0; i < JA.length(); i++) {
                                        JSONObject jsonObject = JA.getJSONObject(i);

                                        VoucherDetailsModel voucherDetailsModel = new VoucherDetailsModel();
                                        voucherDetailsModel.setVoucher_id(Integer.parseInt(jsonObject.getString("voucher_id")));
                                        voucherDetailsModel.setCard_number(jsonObject.getString("card_number"));
                                        voucherDetailsModel.setVd_barcode(jsonObject.getString("vd_barcode"));
                                        voucherDetailsModelArrayList.add(voucherDetailsModel);

                                        allMemberModel = new AllVoucherModel();
                                        allMemberModel.setVoucher_id(Integer.parseInt(jsonObject.getString("voucher_id")));
                                        allMemberModel.setCashier_id(jsonObject.getString("cashier_id"));
                                        allMemberModel.setVoucher_type(jsonObject.getString("voucher_type"));
                                        allMemberModel.setVoucher_date(jsonObject.getString("voucher_date"));
                                        allMemberModel.setMember_id(Integer.parseInt(jsonObject.getString("member_id")));
                                        allMemberModel.setCard_number(jsonObject.getString("card_number"));
                                        allMemberModel.setVd_barcode(jsonObject.getString("vd_barcode"));
                                        allMemberModel.setVoucher_exp_date(jsonObject.getString("voucher_exp_date"));
                                        allMemberModel.setVoucher_stage(jsonObject.getString("voucher_stage"));
                                        allMemberModel.setMontent(Double.valueOf(jsonObject.getString("voucher_amount")));
                                        allMemberModel.setTemplate_id("");
                                        allMemberModel.setCreated_by(jsonObject.getString("created_by"));
                                        allMemberModel.setCreated_on(jsonObject.getString("created_on"));
                                        allMemberModel.setUpdated_by(jsonObject.getString("updated_by"));
                                        allMemberModel.setUpdated_on(jsonObject.getString("updated_on"));
                                        allMemberModel.setCr_window(jsonObject.getBoolean("cr_window"));
                                        allMemberModel.setCr_server(jsonObject.getBoolean("cr_server"));
                                        allMemberModel.setUp_window(jsonObject.getBoolean("up_window"));
                                        allMemberModel.setUp_server(jsonObject.getBoolean("up_server"));
                                        allMemberModel.setDl_window(jsonObject.getBoolean("dl_window"));
                                        allMemberModel.setDl_server(jsonObject.getBoolean("dl_server"));
                                        allMemberModel.setSync_status("Sync");
                                        allMemberModel.setUpdate_status("NotChecked");

                                       StrDate = new CurrentTimeGet().getCurrentTime();
                                       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                       Date date1 = simpleDateFormat.parse(jsonObject.getString("voucher_exp_date").replace("T"," "));
                                       Date date2 = simpleDateFormat.parse(StrDate);

                                       Log.e("DateValue",""+date1 + " " + date2);

                                       if(date2.after(date1))
                                       {
                                           allMemberModel.setVoucher_status("Invalid");
                                       }
                                       else
                                       {
                                           allMemberModel.setVoucher_status("Valid");
                                       }

                                        if (jsonObject.getBoolean("cr_server") == false) {
                                            databaseHelper = new DatabaseHelper(getApplicationContext());
                                            databaseHelper.InsertGiftVoucherFromServerToApp(allMemberModel);
                                            new BackgroundTaskToPostGiftVoucherDetailsFromAppToServer(voucherDetailsModelArrayList, getApplicationContext()).execute();
                                        }

                                        }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (ParseException e) {
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
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        else

            {
            Toast.makeText(context, "Please check Internet Connection", Toast.LENGTH_LONG).show();
            }
    }

    public class BackgroundTaskToPostGiftVoucherDetailsFromAppToServer extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<VoucherDetailsModel> arrayListData = new ArrayList<VoucherDetailsModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskToPostGiftVoucherDetailsFromAppToServer(ArrayList<VoucherDetailsModel> arrayList, Context context) {
            this.arrayListData = arrayList;
            this.ctx = context;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            for (int i = 0; i < arrayListData.size(); i++) {
                VoucherDetailsModel voucherDetailsModel = arrayListData.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("voucher_id", voucherDetailsModel.getVoucher_id());
                    JO.put("vd_barcode",voucherDetailsModel.getVd_barcode());
                    JO.put("card_number", voucherDetailsModel.getCard_number());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("LCDC_voucher_detailsCrServerTrueModelList", jsonArray);
                    Log.e("GetJsonObjectOFVoucher ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateVoucherCrServerTrueInsertedInAppFromWeb), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Voucher cr_server True Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("LCDC_voucher_detailsCrServerTrueModelList");
                                Log.e("GetJsonArrayInAsyncVoucher :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        AllMemberModel allMemberModel = new AllMemberModel();
                                        allMemberModel.setVd_barcode(JO.getString("vd_barcode"));
                                        allMemberModel.setCr_server(true);
                                        databaseHelper.UpdateCr_serverForVoucher(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    private void viewAllVoucherListForUpdateVoucherStageFromAppDatabase() {

        databaseHelper = new DatabaseHelper(getApplicationContext());
        allVoucherModelArrayList = new ArrayList<AllVoucherModel>();
        allVoucherModelArrayList = databaseHelper.GetInformationReturnArraylistGiftVoucherDetailsTable("Checked");


        for (int i = 0; i < allVoucherModelArrayList.size(); i++) {

            AllVoucherModel allVoucherModel = allVoucherModelArrayList.get(i);
            getVoucherId = String.valueOf(allVoucherModel.getVoucher_id());
            getVdBarcode = allVoucherModel.getVd_barcode();
            getVoucherStage = allVoucherModel.getVoucher_stage();
            updated_by = allVoucherModel.getUpdated_by();
            updated_on = allVoucherModel.getUpdated_on();

            VoucherDetailsModel voucherDetailsModel = new VoucherDetailsModel();
            voucherDetailsModel.setVoucher_id(Integer.parseInt(getVoucherId));
            voucherDetailsModel.setVd_barcode(getVdBarcode);
            voucherDetailsModel.setVoucher_stage(getVoucherStage);
            voucherDetailsModel.setUpdated_by(updated_by);
            voucherDetailsModel.setUpdated_on(updated_on);
            voucherDetailsModelArrayList.add(voucherDetailsModel);

                new BackgroundTaskToPostGiftVoucherDetailsForVoucher_stageFromAppToServer(voucherDetailsModelArrayList, getApplicationContext()).execute();
        }
    }

    public class BackgroundTaskToPostGiftVoucherDetailsForVoucher_stageFromAppToServer extends AsyncTask<Void, Void, JSONObject> {
        ArrayList<VoucherDetailsModel> arrayListData = new ArrayList<VoucherDetailsModel>();
        Context ctx;
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        public BackgroundTaskToPostGiftVoucherDetailsForVoucher_stageFromAppToServer(ArrayList<VoucherDetailsModel> arrayList, Context context) {
            this.arrayListData = arrayList;
            this.ctx = context;
        }

        @SuppressLint("LongLogTag")
        @Override
        protected JSONObject doInBackground(Void... voids) {

            for (int i = 0; i < arrayListData.size(); i++) {
                VoucherDetailsModel voucherDetailsModel = arrayListData.get(i);
                JSONObject JO = new JSONObject();
                try {
                    JO.put("voucher_id", voucherDetailsModel.getVoucher_id());
                    JO.put("vd_barcode",voucherDetailsModel.getVd_barcode());
                    JO.put("updated_by", voucherDetailsModel.getUpdated_by());
                    JO.put("updated_on",voucherDetailsModel.getUpdated_on());
                    JO.put("voucher_stage",voucherDetailsModel.getVoucher_stage());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                jsonArray.put(JO);

                try {
                    jsonObject.put("LCDC_voucher_detailsUpdateModelList", jsonArray);
                    Log.e("GetJsonObjectOFVoucherStage ", "" + jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

        @Override
        protected void onPostExecute(final JSONObject jsonObject) {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT
                    , Config.Url.concat(UpdateVoucherDetailsUpdatedInAppNotInWeb), jsonObject, new Response.Listener<JSONObject>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onResponse(JSONObject response) {

                    Log.e("GetResponse :", "" + response);
                    try {
                        String JsonResponse = response.getString("results");
                        Log.e("GetJsonResponse :", "" + JsonResponse);

                        if (JsonResponse.equals("Voucher Details Updated Successfully!")) {
                            databaseHelper = new DatabaseHelper(getApplicationContext());

                            try {
                                JSONArray JA = jsonObject.getJSONArray("LCDC_voucher_detailsUpdateModelList");
                                Log.e("GetJsonArrayInAsyncVoucherStage :", "" + JA);
                                if (JA != null) {
                                    for (int i = 0; i < JA.length(); i++) {
                                        JSONObject JO = JA.getJSONObject(i);
                                        AllVoucherModel allMemberModel = new AllVoucherModel();
                                        allMemberModel.setVd_barcode(JO.getString("vd_barcode"));
                                        allMemberModel.setUp_window(true);
                                        databaseHelper.UpdateUp_WindowForVoucher(allMemberModel);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> Headers = new HashMap<String, String>();
                    Headers.put("Authorization-Token", getLoginToken);
                    return Headers;
                }
            };

            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(300000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
    }

    public void UpdateVoucherStage() {
        databaseHelper = new DatabaseHelper(getApplicationContext());
        allVoucherModelArrayList = new ArrayList<AllVoucherModel>();
        allVoucherModelArrayList = databaseHelper.GetInVoucherDateReturnArraylistGiftVoucherDetailsTable("Invalid");

        for(int i=0;i<allVoucherModelArrayList.size();i++)
        {
            AllVoucherModel allVoucherModel = allVoucherModelArrayList.get(i);
            getVoucherStage = allVoucherModel.getVoucher_stage();
            getVdBarcode = allVoucherModel.getVd_barcode();

            AllVoucherModel voucherModel = new AllVoucherModel();
            voucherModel.setVd_barcode(getVdBarcode);
            voucherModel.setVoucher_stage("EXPIRÉ");
            voucherModel.setVoucher_status("Valid");
            databaseHelper.UpdateVoucherStageInVoucherDetailsTable(voucherModel);

        }
    }





    }





