package com.novo.fidelicia.database;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.novo.fidelicia.index.AllVoucherModel;
import com.novo.fidelicia.new_member.CurrentTimeGet;
import com.novo.fidelicia.utils.SharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String db_name = "Fidelicia.db";
    public static String table_LCDC_member_info = "LCDCMemberInfo";
    public static String table_LCDC_member_reward = "LCDC_MemberRewardInfo";
    public static String table_LCDC_reason_master = "LCDC_reason_master";
    public static String table_LCDC_gift_product = "LCDC_gift_product";
    public static String table_LCDC_ZipCode = "LCDC_ZipCode";
    public static String table_LCDC_gift_voucher = "LCDC_gift_voucher";
    public static String table_LCDC_activity_list = "LCDC_activity_list";
    public static String table_LCDC_civility_list = "LCDC_civility_list";
    public static String table_LCDC_gift_card_details_list = "LCDC_gift_card_details_list";
    public static String table_LCDC_gift_card_list = "LCDC_gift_card_list";
    public static String table_LCDC_member_log_info = "LCDC_member_log_info";
    public static String table_LCDC_gift_voucher_details = "LCDC_gift_voucher_details";


    public static String col_id = "id";
    public static String col_member_id = "member_id";
    public static String col_cashier_id = "cashier_id";
    public static String col_card_number = "card_number";
    public static String col_civility = "civility";
    public static String col_gender = "gender";
    public static String col_name = "name";
    public static String col_first_name = "first_name";
    public static String col_birth_date = "birth_date";
    public static String col_age = "age";
    public static String col_society = "society";
    public static String col_activity = "activity";
    public static String col_address_line1 = "address_line1";
    public static String col_address_line2 = "address_line2";
    public static String col_postal_code = "postal_code";
    public static String col_city = "city";
    public static String col_country = "country";
    public static String col_portable = "portable";
    public static String col_phone = "phone";
    public static String col_email = "email";
    public static String col_last_visit = "last_visit";
    public static String col_balance_card = "balance_card";
    public static String col_turn_over = "turn_over";
    public static String col_average_basket = "average_basket";
    public static String col_total_purchase_order = "total_purchase_order";
    public static String col_total_uses = "total_uses";
    public static String col_in_progress = "in_progress";
    public static String col_npai = "npai";
    public static String col_stop_sms = "stop_sms";
    public static String col_stop_email = "stop_email";
    public static String col_mi_barcode = "mi_barcode";
    public static String col_created_by = "created_by";
    public static String col_created_on = "created_on";
    public static String col_updated_by = "updated_by";
    public static String col_updated_on = "updated_on";
    public static String col_no_of_points = "no_of_points";
    public static String col_cr_window = "cr_window";
    public static String col_cr_server = "cr_server";
    public static String col_up_window = "up_window";
    public static String col_up_server = "up_server";
    public static String col_dl_window = "dl_window";
    public static String col_dl_server = "dl_server";
    public static String col_sync_status = "sync_status";
    public static String col_update_status = "update_status";
    public static String col_reward_type = "reward_type";
    public static String col_auto_id = "auto_id";
    public static String col_gift_product_name = "gift_product_name";
    public static String col_customer_id = "customer_id";
    public static String col_reason_id = "reason_id";
    public static String col_reason = "reason";
    public static String col_reward_id = "reward_id";
    public static String col_comment = "comment";
    public static String col_department = "department";
    public static String col_geographical_code = "geographical_code";
    public static String col_gift_voucher_id = "gift_voucher_id";
    public static String col_text = "text";
    public static String col_value = "value";
    public static String col_activity_id = "activity_id";
    public static String col_civility_id = "civility_id";
    public static String col_gift_card_id = "gift_card_id";
    public static String col_total_amount = "total_amount";
    public static String col_address = "address";
    public static String col_amount = "amount";
    public static String col_credit_debit = "credit_debit";
    public static String col_voucher_id_auto = "voucher_id_auto";
    public static String col_voucher_id = "voucher_id";
    public static String col_voucher_date = "voucher_date";
    public static String col_voucher_type = "voucher_type";
    public static String col_vd_barcode = "vd_barcode";
    public static String col_voucher_exp_date = "voucher_exp_date";
    public static String col_voucher_stage = "voucher_stage";
    public static String col_template_id = "template_id";
    public static String col_voucher_status = "voucher_status";
    public static String col_montent = "montent";


    String[]StringArray = null;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    View view;
    Context context;
    SharedPreference sharedPreference = null;


    public DatabaseHelper(Context context) {
        super(context, db_name, null, 1);
       this.context = context;
    }

    private static final String CREATE_LDC_MEMBER_REWARD_INFO = "CREATE TABLE " + table_LCDC_member_reward + "("
            + col_reward_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_member_id + " INTEGER,"
            + col_cashier_id + " TEXT,"
            + col_card_number + " TEXT,"
            + col_mi_barcode + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_reward_type + " TEXT,"
            + col_no_of_points + " REAL,"
            + col_comment + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    private static final String CREATE_LCDC_GIFT_PRODUCT = "CREATE TABLE " + table_LCDC_gift_product + "("
            + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_auto_id + " INTEGER,"
            + col_gift_product_name + " TEXT,"
            + col_customer_id + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN"+");";

    private static final String CREATE_LCDC_REASON_MASTER = "CREATE TABLE " + table_LCDC_reason_master + "("
            + col_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_reason_id +" INTEGER,"
            + col_reason +" TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_customer_id + " TEXT"+");";

    String CREATE_LDC_MEMBER_INFO = "CREATE TABLE " + table_LCDC_member_info + "("
            + col_member_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_cashier_id + " TEXT,"
            + col_card_number + " TEXT,"
            + col_civility + " TEXT,"
            + col_gender + " TEXT,"
            + col_name + " TEXT,"
            + col_first_name + " TEXT,"
            + col_birth_date + " TEXT ,"
            + col_age + " INTEGER,"
            + col_society + " TEXT,"
            + col_activity + " TEXT,"
            + col_address_line1 + " TEXT,"
            + col_address_line2 + " TEXT,"
            + col_postal_code + " TEXT,"
            + col_city + " TEXT,"
            + col_country + " TEXT,"
            + col_portable + " TEXT,"
            + col_phone + " TEXT,"
            + col_email + " TEXT,"
            + col_last_visit + " TEXT,"
            + col_balance_card + " REAL,"
            + col_turn_over + " REAL,"
            + col_average_basket + " REAL,"
            + col_total_purchase_order + " REAL,"
            + col_total_uses + " REAL,"
            + col_in_progress + " REAL,"
            + col_npai + " BOOLEAN,"
            + col_stop_sms + " BOOLEAN,"
            + col_stop_email + " BOOLEAN,"
            + col_mi_barcode + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_no_of_points + " REAL,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    String CREATE_LDC_ZIP_CODE = "CREATE TABLE "+ table_LCDC_ZipCode + "("
            + col_auto_id + " INTEGER,"
            + col_city + " TEXT,"
            + col_postal_code + " INTEGER,"
            + col_department + " TEXT,"
            + col_geographical_code + " INTEGER,"
            + col_country + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT"+");";

    String CREATE_LCDC_GIFT_VOUCHER = "CREATE TABLE "+ table_LCDC_gift_voucher + "("
            + col_gift_voucher_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_card_number + " TEXT,"
            + col_name + " TEXT,"
            + col_first_name + " TEXT,"
            + col_phone + " TEXT,"
            + col_email + " TEXT,"
            + col_gift_product_name + " TEXT,"
            + col_reason + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_cashier_id + " TEXT,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    String CREATE_LCDC_ACTIVITY_LIST = "CREATE TABLE " + table_LCDC_activity_list + "("
            + col_activity_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_text + " TEXT,"
            + col_value + " TEXT"+");";

    String CREATE_LCDC_CIVILITY_LIST = "CREATE TABLE " + table_LCDC_civility_list + "("
            + col_civility_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_text + " TEXT,"
            + col_value + " TEXT"+");";

    String CREATE_LCDC_GIFT_CARD_DETAILS = "CREATE TABLE "+ table_LCDC_gift_card_details_list + "("
            + col_gift_card_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_card_number + " TEXT,"
            + col_name + " TEXT,"
            + col_first_name + " TEXT,"
            + col_phone + " TEXT,"
            + col_email + " TEXT,"
            + col_address + " TEXT,"
            + col_postal_code + " TEXT,"
            + col_city + " TEXT,"
            + col_country + " TEXT,"
            + col_total_amount + " REAL,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_cashier_id + " TEXT,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    String CREATE_LCDC_GIFT_CARD = "CREATE TABLE "+ table_LCDC_gift_card_list + "("
            + col_gift_card_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_card_number + " TEXT,"
            + col_amount + " REAL,"
            + col_credit_debit + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    String CREATE_LDC_MEMBER_LOG_INFO = "CREATE TABLE " + table_LCDC_member_log_info + "("
            + col_member_id +" INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_cashier_id + " TEXT,"
            + col_card_number + " TEXT,"
            + col_civility + " TEXT,"
            + col_gender + " TEXT,"
            + col_name + " TEXT,"
            + col_first_name + " TEXT,"
            + col_birth_date + " TEXT ,"
            + col_age + " INTEGER,"
            + col_society + " TEXT,"
            + col_activity + " TEXT,"
            + col_address_line1 + " TEXT,"
            + col_address_line2 + " TEXT,"
            + col_postal_code + " TEXT,"
            + col_city + " TEXT,"
            + col_country + " TEXT,"
            + col_phone + " TEXT,"
            + col_email + " TEXT,"
            + col_last_visit + " TEXT,"
            + col_mi_barcode + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_no_of_points + " REAL,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_sync_status+" TEXT,"
            + col_update_status+" TEXT"+");";

    String CREATE_LCDC_GIFT_VOUCHER_DETAILS = "CREATE TABLE "+ table_LCDC_gift_voucher_details + "("
            + col_voucher_id_auto + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + col_voucher_id + " INTEGER,"
            + col_cashier_id + " TEXT,"
            + col_voucher_type + " TEXT,"
            + col_voucher_date + " TEXT,"
            + col_member_id + " INTEGER,"
            + col_card_number + " TEXT,"
            + col_vd_barcode + " TEXT,"
            + col_voucher_exp_date + " TEXT,"
            + col_voucher_stage + " TEXT,"
            + col_montent + " REAL,"
            + col_template_id + " TEXT,"
            + col_created_by + " TEXT,"
            + col_created_on + " TEXT,"
            + col_updated_by + " TEXT,"
            + col_updated_on + " TEXT,"
            + col_cr_window + " BOOLEAN,"
            + col_cr_server + " BOOLEAN,"
            + col_up_window + " BOOLEAN,"
            + col_up_server + " BOOLEAN,"
            + col_dl_window + " BOOLEAN,"
            + col_dl_server + " BOOLEAN,"
            + col_sync_status+ " TEXT,"
            + col_update_status+ " TEXT,"
            + col_voucher_status+ " TEXT"+");";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LDC_MEMBER_REWARD_INFO);
        db.execSQL(CREATE_LCDC_GIFT_PRODUCT);
        db.execSQL(CREATE_LCDC_REASON_MASTER);
        db.execSQL(CREATE_LDC_MEMBER_INFO);
        db.execSQL(CREATE_LDC_ZIP_CODE);
        db.execSQL(CREATE_LCDC_GIFT_VOUCHER);
        db.execSQL(CREATE_LCDC_ACTIVITY_LIST);
        db.execSQL(CREATE_LCDC_CIVILITY_LIST);
        db.execSQL(CREATE_LCDC_GIFT_CARD_DETAILS);
        db.execSQL(CREATE_LCDC_GIFT_CARD);
        db.execSQL(CREATE_LDC_MEMBER_LOG_INFO);
        db.execSQL(CREATE_LCDC_GIFT_VOUCHER_DETAILS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LDC_MEMBER_REWARD_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_GIFT_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_REASON_MASTER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LDC_MEMBER_REWARD_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LDC_ZIP_CODE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_GIFT_VOUCHER);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_ACTIVITY_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_ACTIVITY_LIST);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_GIFT_CARD_DETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_GIFT_CARD);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LDC_MEMBER_LOG_INFO);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_LCDC_GIFT_VOUCHER_DETAILS);
        // create new tables
        onCreate(db);
    }


    public boolean insertMessage(AllMemberModel allMemberModel) {

         SQLiteDatabase database = this.getWritableDatabase();

         ContentValues values = new ContentValues();

        values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_civility, allMemberModel.getCivility());
        values.put(col_gender, allMemberModel.getGender());
        values.put(col_name, allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_birth_date, String.valueOf(allMemberModel.getBirth_date()));
        values.put(col_age, allMemberModel.getAge());
        values.put(col_society, allMemberModel.getSociety());
        values.put(col_activity, allMemberModel.getActivity());
        values.put(col_address_line1, allMemberModel.getAddress_line1());
        values.put(col_address_line2, allMemberModel.getAddress_line2());
        values.put(col_postal_code,allMemberModel.getPostal_code());
        values.put(col_city, allMemberModel.getCity());
        values.put(col_country, allMemberModel.getCountry());
        values.put(col_portable, allMemberModel.getPortable());
        values.put(col_phone, allMemberModel.getPhone());
        values.put(col_email, allMemberModel.getEmail());
        values.put(col_last_visit,allMemberModel.getLast_visit());
        values.put(col_balance_card,allMemberModel.getBalance_card());
        values.put(col_turn_over, allMemberModel.getTurn_over());
        values.put(col_average_basket, allMemberModel.getAverage_basket());
        values.put(col_total_purchase_order, allMemberModel.getTotal_purchase_order());
        values.put(col_total_uses, allMemberModel.getTotal_uses());
        values.put(col_in_progress, allMemberModel.getIn_progress());
        values.put(col_npai, allMemberModel.isNpai());
        values.put(col_stop_sms,allMemberModel.isStop_sms());
        values.put(col_stop_email, allMemberModel.isStop_email());
        values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_sync_status,allMemberModel.getSync_status());
        values.put(col_update_status,allMemberModel.getUpdate_status());

        database.insert(table_LCDC_member_info, null, values);
        database.close();

        return true;

    }

    public void insertDeletedMemberInfoIntoLogTable(AllMemberModel allMemberModel){

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_civility, allMemberModel.getCivility());
        values.put(col_gender, allMemberModel.getGender());
        values.put(col_name, allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_birth_date, String.valueOf(allMemberModel.getBirth_date()));
        values.put(col_age, allMemberModel.getAge());
        values.put(col_society, allMemberModel.getSociety());
        values.put(col_activity, allMemberModel.getActivity());
        values.put(col_address_line1, allMemberModel.getAddress_line1());
        values.put(col_address_line2, allMemberModel.getAddress_line2());
        values.put(col_postal_code,allMemberModel.getPostal_code());
        values.put(col_city, allMemberModel.getCity());
        values.put(col_country, allMemberModel.getCountry());
        values.put(col_phone, allMemberModel.getPhone());
        values.put(col_email, allMemberModel.getEmail());
        values.put(col_last_visit,allMemberModel.getLast_visit());
        values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_sync_status,allMemberModel.getSync_status());
        values.put(col_update_status,allMemberModel.getUpdate_status());

        database.insert(table_LCDC_member_log_info, null, values);
        database.close();

    }

    public int UpdateInformation(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(col_member_id, allMemberModel.getMember_id());
        values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_civility, allMemberModel.getCivility());
        values.put(col_gender, allMemberModel.getGender());
        values.put(col_name, allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_birth_date, String.valueOf(allMemberModel.getBirth_date()));
        values.put(col_age, allMemberModel.getAge());
        values.put(col_society, allMemberModel.getSociety());
        values.put(col_activity, allMemberModel.getActivity());
        values.put(col_address_line1, allMemberModel.getAddress_line1());
        values.put(col_address_line2, allMemberModel.getAddress_line2());
        values.put(col_postal_code,allMemberModel.getPostal_code());
        values.put(col_city, allMemberModel.getCity());
        values.put(col_country, allMemberModel.getCountry());
        values.put(col_portable, allMemberModel.getPortable());
        values.put(col_phone, allMemberModel.getPhone());
        values.put(col_email, allMemberModel.getEmail());
        values.put(col_last_visit,allMemberModel.getLast_visit());
        values.put(col_balance_card,allMemberModel.getBalance_card());
        values.put(col_turn_over, allMemberModel.getTurn_over());
        values.put(col_average_basket, allMemberModel.getAverage_basket());
        values.put(col_total_purchase_order, allMemberModel.getTotal_purchase_order());
        values.put(col_total_uses, allMemberModel.getTotal_uses());
        values.put(col_in_progress, allMemberModel.getIn_progress());
        values.put(col_npai, allMemberModel.isNpai());
        values.put(col_stop_sms,allMemberModel.isStop_sms());
        values.put(col_stop_email, allMemberModel.isStop_email());
        values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, String.valueOf(allMemberModel.getCreated_on()));
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, String.valueOf(allMemberModel.getUpdated_on()));
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_sync_status,allMemberModel.getSync_status());
        values.put(col_update_status,allMemberModel.getUpdate_status());

        String whereClause = col_card_number + " = ? AND " + col_mi_barcode + " = ?"; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {allMemberModel.getCard_number(), allMemberModel.getMi_barcode()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;

    }

    public AllMemberModelByMiBarCodeOnly GetInformationByBarcode(String mi_barcode){
        AllMemberModelByMiBarCodeOnly allMemberModel = null;
        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(mi_barcode) = ?", new String[]{mi_barcode});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByMiBarCodeOnly();
                        allMemberModel.setMember_id(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("member_id")));
                        allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                        allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                        allMemberModel.setCivility(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("civility")));
                        allMemberModel.setGender(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gender")));
                        allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                        allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                        allMemberModel.setBirth_date(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("birth_date")));
                        allMemberModel.setAge(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("age")));
                        allMemberModel.setSociety(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("society")));
                        allMemberModel.setActivity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("activity")));
                        allMemberModel.setAddress_line1(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line1")));
                        allMemberModel.setAddress_line2(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line2")));
                        allMemberModel.setPostal_code(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("postal_code")));
                        allMemberModel.setCity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("city")));
                        allMemberModel.setCountry(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("country")));
                        allMemberModel.setPortable(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("portable")));
                        allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                        allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                        allMemberModel.setNo_of_points((allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points"))));

                        allMemberModel.setLast_visit(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("last_visit")));
                        allMemberModel.setTurn_over(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("turn_over")));
                        allMemberModel.setAverage_basket(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("average_basket")));
                        allMemberModel.setTotal_purchase_order(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("total_purchase_order")));
                        allMemberModel.setTotal_uses(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("total_uses")));
                        allMemberModel.setIn_progress(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("EstadoDeInteres")));
                        allMemberModel.setNpai(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("npai"))));
                        allMemberModel.setStop_sms(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("stop_sms"))));
                        allMemberModel.setStop_email(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("stop_email"))));
                        allMemberModel.setMi_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                        allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                        allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                        allMemberModel.setUpdated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_by")));
                        allMemberModel.setUpdated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_on")));
                        allMemberModel.setCr_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cr_window"))));
                        allMemberModel.setUp_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("up_window"))));
                        allMemberModel.setCr_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cr_server"))));
                        allMemberModel.setUp_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("up_server"))));
                        allMemberModel.setDl_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("dl_server"))));
                        allMemberModel.setDl_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("dl_window"))));

                    } while (allMember_Cursor.moveToNext());
                }
                allMember_Cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public AllMemberModelByCardNumberOnly GetInformationByCardNumber(String card_number){
        AllMemberModelByCardNumberOnly allMemberModel = null;

        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(card_number) = ?", new String[]{card_number});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        allMemberModel.setNo_of_points((allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points"))));
                        Log.e("GetNumberOfPointsCursor",""+allMember_Cursor.getString(allMember_Cursor.getColumnIndex("no_of_points")));
                        allMemberModel.setMember_id(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("member_id")));
                        allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                        allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                        allMemberModel.setCivility(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("civility")));
                        allMemberModel.setGender(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gender")));
                        allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                        allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                        allMemberModel.setBirth_date(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("birth_date")));
                        allMemberModel.setAge(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("age")));
                        allMemberModel.setSociety(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("society")));
                        allMemberModel.setActivity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("activity")));
                        allMemberModel.setAddress_line1(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line1")));
                        allMemberModel.setAddress_line2(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line2")));
                        allMemberModel.setPostal_code(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("postal_code")));
                        allMemberModel.setCity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("city")));
                        allMemberModel.setCountry(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("country")));
                        allMemberModel.setPortable(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("portable")));
                        allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                        allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                        allMemberModel.setLast_visit(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("last_visit")));
                        allMemberModel.setBalance_card(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("balance_card")));
                        allMemberModel.setTurn_over(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("turn_over")));
                        allMemberModel.setAverage_basket(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("average_basket")));
                        allMemberModel.setTotal_purchase_order(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("total_purchase_order")));
                        allMemberModel.setTotal_uses(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("total_uses")));
                        //allMemberModel.setIn_progress(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("EstadoDeInteres")));
                        allMemberModel.setNpai(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("npai"))));
                        allMemberModel.setStop_sms(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("stop_sms"))));
                        allMemberModel.setStop_email(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("stop_email"))));
                        allMemberModel.setMi_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                        allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                        allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                        allMemberModel.setUpdated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_by")));
                        allMemberModel.setUpdated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_on")));

                        allMemberModel.setCr_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cr_window"))));
                        allMemberModel.setUp_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("up_window"))));
                        allMemberModel.setCr_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cr_server"))));
                        allMemberModel.setUp_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("up_server"))));
                        allMemberModel.setDl_server(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("dl_server"))));
                        allMemberModel.setDl_window(Boolean.parseBoolean(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("dl_window"))));

                    } while (allMember_Cursor.moveToNext());
                }
                allMember_Cursor.close();
            }
           /* else
            {
                //alertDialog.show();
                Toast.makeText(context,"ResultNotFound....!!!",Toast.LENGTH_SHORT).show();
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public boolean GetInformationByCardNumberToCheckMember(String card_number){
        AllMemberModelByCardNumberOnly allMemberModel = null;

        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(card_number) = ?", new String[]{card_number});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        /*sharedPreference = new SharedPreference();
                        String cardNumber = allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number"));
                        sharedPreference.saveCardNumber(context,cardNumber);*/
                        return true;

                    } while (allMember_Cursor.moveToNext());
                }

            }
            else
            {
                allMember_Cursor.close();
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean GetInformationByGiftCardNumberToCheckMember(String card_number){
        AllMemberModelByCardNumberOnly allMemberModel = null;

        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_card_details_list + " where trim(card_number) = ?", new String[]{card_number});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        /*sharedPreference = new SharedPreference();
                        String cardNumber = allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number"));
                        sharedPreference.saveCardNumber(context,cardNumber);*/
                        return true;

                    } while (allMember_Cursor.moveToNext());
                }
            }
            else
            {
                allMember_Cursor.close();
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean GetInformationCardNumberFromLogTableToCheckMember(String card_number){
        AllMemberModelByCardNumberOnly allMemberModel = null;

        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_log_info + " where trim(card_number) = ?", new String[]{card_number});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        /*sharedPreference = new SharedPreference();
                        String cardNumber = allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number"));
                        sharedPreference.saveCardNumber(context,cardNumber);*/
                        return true;

                    } while (allMember_Cursor.moveToNext());
                }
            }
            else
            {
                allMember_Cursor.close();
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean GetInformationByMiBarcodeToCheckMember(String mi_barcode){
        AllMemberModelByCardNumberOnly allMemberModel = null;

        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(mi_barcode) = ?", new String[]{mi_barcode});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {

                        return true;

                    } while (allMember_Cursor.moveToNext());
                }

            }
            else
            {
                allMember_Cursor.close();
                return false;
            }
           /* else
            {
                //alertDialog.show();
                Toast.makeText(context,"ResultNotFound....!!!",Toast.LENGTH_SHORT).show();
            }*/
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<AllMemberModelByCardNumberOnly> GetInformationReturnArraylist(String status){
        AllMemberModelByCardNumberOnly allMemberModel = null;
        ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
        try{
            Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(sync_status) = ?", new String[]{status});

            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try{
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModelByCardNumberOnly();
                            allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                            allMemberModel.setCivility(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("civility")));
                            allMemberModel.setGender(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gender")));
                            allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                            allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                            allMemberModel.setBirth_date(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("birth_date")));
                            allMemberModel.setAge(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("age")));
                            allMemberModel.setSociety(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("society")));
                            allMemberModel.setActivity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("activity")));
                            allMemberModel.setAddress_line1(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line1")));
                            allMemberModel.setAddress_line2(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line2")));
                            allMemberModel.setPostal_code(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("postal_code")));
                            allMemberModel.setCity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("city")));
                            allMemberModel.setCountry(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("country")));
                            allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                            allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                            allMemberModel.setMi_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModelByCardMiBarcodes.add(allMemberModel);

                        } while (allMember_Cursor.moveToNext());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                allMember_Cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModelByCardMiBarcodes;
    }

    public ArrayList<AllMemberModelByCardNumberOnly> GetInformationReturnArraylistForUpdateMemberToSerVer(String status){
        AllMemberModelByCardNumberOnly allMemberModel = null;
        ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
          try{
              Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(update_status) = ?", new String[]{status});

              if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                        allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                        allMemberModel.setCivility(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("civility")));
                        allMemberModel.setGender(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gender")));
                        allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                        allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                        allMemberModel.setBirth_date(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("birth_date")));
                        allMemberModel.setAge(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("age")));
                        allMemberModel.setSociety(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("society")));
                        allMemberModel.setActivity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("activity")));
                        allMemberModel.setAddress_line1(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line1")));
                        allMemberModel.setAddress_line2(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line2")));
                        allMemberModel.setPostal_code(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("postal_code")));
                        allMemberModel.setCity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("city")));
                        allMemberModel.setCountry(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("country")));
                        allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                        allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                        allMemberModel.setNo_of_points(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points")));
                        allMemberModel.setMi_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                        allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                        allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                        allMemberModelByCardMiBarcodes.add(allMemberModel);

                    } while (allMember_Cursor.moveToNext());
                }
                allMember_Cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModelByCardMiBarcodes;
    }

    public ArrayList<AllMemberModelByCardNumberOnly> GetInformationReturnArraylistForUpdateMemberCreditPointToSerVer(String status){
        AllMemberModelByCardNumberOnly allMemberModel = null;
        ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
          try{
              Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_info + " where trim(update_status) = ?", new String[]{status});

              if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst()) {
                    do {
                        allMemberModel = new AllMemberModelByCardNumberOnly();
                        allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                        allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                        allMemberModel.setNo_of_points(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points")));
                        allMemberModelByCardMiBarcodes.add(allMemberModel);
                    } while (allMember_Cursor.moveToNext());
                }
                allMember_Cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModelByCardMiBarcodes;
    }

    public int UpdateUp_Window(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_update_status,"NotChecked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_server(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_server, allMemberModel.isCr_server());

        String whereClause = col_card_number + " = ? AND " + col_mi_barcode + " = ?"; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number(),allMemberModel.getMi_barcode()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateUP_Server(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_server, allMemberModel.isUp_server());

        String whereClause = col_card_number + " = ? AND " + col_mi_barcode + " = ?"; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number(),allMemberModel.getMi_barcode()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_Window(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_sync_status,"Sync");

        String whereClause = col_sync_status + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getSync_status()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateInformationByCard(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(col_member_id, allMemberModel.getMember_id());
        //values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_civility, allMemberModel.getCivility());
        //values.put(col_gender, allMemberModel.getGender());
        values.put(col_name, allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_birth_date, String.valueOf(allMemberModel.getBirth_date()));
        values.put(col_age, allMemberModel.getAge());
        values.put(col_society, allMemberModel.getSociety());
        values.put(col_activity, allMemberModel.getActivity());
        values.put(col_address_line1, allMemberModel.getAddress_line1());
        //values.put(col_address_line2, allMemberModel.getAddress_line2());
        values.put(col_postal_code,allMemberModel.getPostal_code());
        values.put(col_city, allMemberModel.getCity());
        values.put(col_country, allMemberModel.getCountry());
        //values.put(col_portable, allMemberModel.getPortable());
        values.put(col_phone, allMemberModel.getPhone());
        values.put(col_email, allMemberModel.getEmail());
        //values.put(col_last_visit,allMemberModel.getLast_visit());
        values.put(col_balance_card,allMemberModel.getBalance_card());
        values.put(col_turn_over, allMemberModel.getTurn_over());
        values.put(col_average_basket, allMemberModel.getAverage_basket());
        values.put(col_total_purchase_order, allMemberModel.getTotal_purchase_order());
        values.put(col_total_uses, allMemberModel.getTotal_uses());
        values.put(col_in_progress, allMemberModel.getIn_progress());
        values.put(col_npai, allMemberModel.isNpai());
        values.put(col_stop_sms,allMemberModel.isStop_sms());
        values.put(col_stop_email, allMemberModel.isStop_email());
        //values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        //values.put(col_created_by, allMemberModel.getCreated_by());
        //values.put(col_created_on, String.valueOf(allMemberModel.getCreated_on()));
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, String.valueOf(allMemberModel.getUpdated_on()));
        //values.put(col_no_of_points,allMemberModel.getNo_of_points());
      /*  values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());*/
        values.put(col_up_window, allMemberModel.isUp_window());
        //values.put(col_up_server, allMemberModel.isUp_server());
       /* values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());*/
        values.put(col_update_status,allMemberModel.getUpdate_status());

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;

    }

    public int UpdateInformationMiBarcode(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        //values.put(col_member_id, allMemberModel.getMember_id());
        //values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_civility, allMemberModel.getCivility());
        //values.put(col_gender, allMemberModel.getGender());
        values.put(col_name, allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_birth_date, String.valueOf(allMemberModel.getBirth_date()));
        values.put(col_age, allMemberModel.getAge());
        values.put(col_society, allMemberModel.getSociety());
        values.put(col_activity, allMemberModel.getActivity());
        values.put(col_address_line1, allMemberModel.getAddress_line1());
        //values.put(col_address_line2, allMemberModel.getAddress_line2());
        values.put(col_postal_code,allMemberModel.getPostal_code());
        values.put(col_city, allMemberModel.getCity());
        values.put(col_country, allMemberModel.getCountry());
        //values.put(col_portable, allMemberModel.getPortable());
        values.put(col_phone, allMemberModel.getPhone());
        values.put(col_email, allMemberModel.getEmail());
        //values.put(col_last_visit,allMemberModel.getLast_visit());
        values.put(col_balance_card,allMemberModel.getBalance_card());
        values.put(col_turn_over, allMemberModel.getTurn_over());
        values.put(col_average_basket, allMemberModel.getAverage_basket());
        values.put(col_total_purchase_order, allMemberModel.getTotal_purchase_order());
        values.put(col_total_uses, allMemberModel.getTotal_uses());
        values.put(col_in_progress, allMemberModel.getIn_progress());
        values.put(col_npai, allMemberModel.isNpai());
        values.put(col_stop_sms,allMemberModel.isStop_sms());
        values.put(col_stop_email, allMemberModel.isStop_email());
        //values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        //values.put(col_created_by, allMemberModel.getCreated_by());
        //values.put(col_created_on, String.valueOf(allMemberModel.getCreated_on()));
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, String.valueOf(allMemberModel.getUpdated_on()));
        //values.put(col_no_of_points,allMemberModel.getNo_of_points());
      /*  values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());*/
        values.put(col_up_window, allMemberModel.isUp_window());
        //values.put(col_up_server, allMemberModel.isUp_server());
       /* values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());*/
        values.put(col_update_status,allMemberModel.getUpdate_status());

        String whereClause = col_mi_barcode + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {allMemberModel.getMi_barcode()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;

    }

    public int UpdateInformationByCardFromCrediter(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, String.valueOf(allMemberModel.getUpdated_on()));
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;

    }

    public int UpdateInformationByMiBarcodeFromCrediter(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, String.valueOf(allMemberModel.getUpdated_on()));
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_up_window, allMemberModel.isUp_window());

        String whereClause = col_mi_barcode + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {allMemberModel.getMi_barcode()};

        int count = database.update(table_LCDC_member_info,values,whereClause,whereArgs);

        return count;

    }

    public void insertPointsRewardByCardNumber(AllMemberModel allMemberModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_member_id, allMemberModel.getMember_id());
        values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_mi_barcode, allMemberModel.getMi_barcode());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_comment,allMemberModel.getComment());
        values.put(col_reward_type,allMemberModel.getReward_type());
        values.put(col_no_of_points,allMemberModel.getNo_of_points());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_sync_status,allMemberModel.getSync_status());
        values.put(col_update_status,allMemberModel.getUpdate_status());

        database.insert(table_LCDC_member_reward, null, values);
        database.close();

    }

    @SuppressLint("LongLogTag")
    public AllMemberModel addAllValuePointsInRewardsPointsTable(String CardNumber){

        AllMemberModel allMemberModel = null;
        SQLiteDatabase database = this.getReadableDatabase();
        int Total = 0;
        Cursor cursor = database.rawQuery("SELECT SUM(no_of_points) as no_of_points FROM " + table_LCDC_member_reward + " where trim(card_number) = ?", new String[]{CardNumber});
        try{

            if(cursor!=null && cursor.getCount()>0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        allMemberModel = new AllMemberModel();
                        allMemberModel.setNo_of_points(cursor.getDouble(cursor.getColumnIndex("no_of_points")));

                    }
                    while (cursor.moveToNext());
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }

    @SuppressLint("LongLogTag")
    public AllMemberModel GetLastVisitDate(String CardNumber){

        AllMemberModel allMemberModel = null;
        SQLiteDatabase database = this.getReadableDatabase();
        int Total = 0;
        Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_member_reward + " where trim(card_number) = ?", new String[]{CardNumber});
        try{

            if(cursor!=null && cursor.getCount()>0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        allMemberModel = new AllMemberModel();
                        allMemberModel.setNo_of_points(cursor.getDouble(cursor.getColumnIndex("created_on")));

                    }
                    while (cursor.moveToLast());
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public ArrayList<AllMemberModel> GetAllInformationFromRewardTable(String CardNumber) {
        ArrayList<AllMemberModel>arrayList = new ArrayList<AllMemberModel>();
        AllMemberModel allMemberModel = null;
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_member_reward + " where trim(card_number) = ?",new String[]{CardNumber});

        try{
            if(cursor != null && cursor.getCount() >0)
            {
                if(cursor.moveToFirst())
                {
                    do{
                        allMemberModel = new AllMemberModel();
                        allMemberModel.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
                        allMemberModel.setNo_of_points(cursor.getDouble(cursor.getColumnIndex("no_of_points")));
                        arrayList.add(allMemberModel);
                    }while (cursor.moveToNext());
                }

                cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return arrayList;
    }

    public AllMemberModel getTaskCount(String cardNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        AllMemberModel allMemberModel = null;
        Cursor cursor= db.rawQuery("SELECT COUNT (*) as reward_id FROM LCDC_MemberRewardInfo  where trim(card_number) = ?" ,
                new String[] {cardNumber});
        try{
            if(cursor!=null && cursor.getCount()>0){

                if(cursor.moveToFirst())
                {
                    do{

                       allMemberModel = new AllMemberModel();
                       allMemberModel.setTotal_count(cursor.getInt(cursor.getColumnIndex("reward_id")));
                    }while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public void  insertViewAllReasonMaster(AllMemberModel allMemberModel){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_reason_id, allMemberModel.getAuto_id());
        values.put(col_reason, allMemberModel.getGift_product_name());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_customer_id, allMemberModel.getCustomer_id());
        database.insert(table_LCDC_reason_master, null, values);
        database.close();
    }

    public int UpdateViewAllReasonMasterInSqlite(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_reason_id, allMemberModel.getAuto_id());
        values.put(col_reason, allMemberModel.getGift_product_name());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_customer_id, allMemberModel.getCustomer_id());

        String whereClause = col_reason_id + " = ?"; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {String.valueOf(allMemberModel.getReason_id())};

        int count = database.update(table_LCDC_reason_master,values,whereClause,whereArgs);

        return count;
    }

    public ArrayList<AllMemberModel> GetReasonInformationFromSqlite() {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_reason_master /*+ " where trim(auto_id) = ?",new String[]{auto_id}*/,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setReason(cursor.getString(cursor.getColumnIndex("reason")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public void insertViewAllGiftProduct(AllMemberModel allMemberModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_auto_id, allMemberModel.getAuto_id());
        values.put(col_gift_product_name, allMemberModel.getGift_product_name());
        values.put(col_customer_id, allMemberModel.getCustomer_id());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        database.insert(table_LCDC_gift_product, null, values);
        database.close();

    }

    public int UpdateViewAllGiftProductInSqlite(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_auto_id, allMemberModel.getAuto_id());
        values.put(col_gift_product_name, allMemberModel.getGift_product_name());
        values.put(col_customer_id, allMemberModel.getCustomer_id());
        values.put(col_created_by, allMemberModel.getCreated_by());
        values.put(col_created_on, allMemberModel.getCreated_on());
        values.put(col_updated_by, allMemberModel.getUpdated_by());
        values.put(col_updated_on, allMemberModel.getUpdated_on());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());

        String whereClause = col_auto_id + " = ?"; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs = {String.valueOf(allMemberModel.getAuto_id())};

        int count = database.update(table_LCDC_gift_product,values,whereClause,whereArgs);

        return count;
    }

    public void insertGiftCardDetailsInSqlite(AllMemberModel allMemberModel){

        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_card_number,allMemberModel.getCard_number());
            contentValues.put(col_name,allMemberModel.getName());
            contentValues.put(col_first_name,allMemberModel.getFirst_name());
            contentValues.put(col_phone,allMemberModel.getPhone());
            contentValues.put(col_email,allMemberModel.getEmail());
            contentValues.put(col_address,allMemberModel.getAddress());
            contentValues.put(col_postal_code,allMemberModel.getPostal_code());
            contentValues.put(col_city,allMemberModel.getCity());
            contentValues.put(col_country,allMemberModel.getCountry());
            contentValues.put(col_total_amount,allMemberModel.getTotal_amount());
            contentValues.put(col_created_by,allMemberModel.getCreated_by());
            contentValues.put(col_created_on,allMemberModel.getCreated_on());
            contentValues.put(col_updated_by,allMemberModel.getUpdated_by());
            contentValues.put(col_updated_on, allMemberModel.getUpdated_on());
            contentValues.put(col_cr_window, allMemberModel.isCr_window());
            contentValues.put(col_cr_server, allMemberModel.isCr_server());
            contentValues.put(col_up_window, allMemberModel.isUp_window());
            contentValues.put(col_up_server, allMemberModel.isUp_server());
            contentValues.put(col_dl_window, allMemberModel.isDl_window());
            contentValues.put(col_dl_server,allMemberModel.isDl_server());
            contentValues.put(col_cashier_id,allMemberModel.getCashier_id());
            contentValues.put(col_sync_status,allMemberModel.getSync_status());
            contentValues.put(col_update_status,allMemberModel.getUpdate_status());

            database.insert(table_LCDC_gift_card_details_list,null,contentValues);
            database.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void insertGiftCardInSqlite(AllMemberModel allMemberModel){

        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_card_number,allMemberModel.getCard_number());
            contentValues.put(col_amount,allMemberModel.getAmount());
            contentValues.put(col_created_by,allMemberModel.getCreated_by());
            contentValues.put(col_created_on,allMemberModel.getCreated_on());
            contentValues.put(col_cr_window, allMemberModel.isCr_window());
            contentValues.put(col_cr_server, allMemberModel.isCr_server());
            contentValues.put(col_up_window, allMemberModel.isUp_window());
            contentValues.put(col_up_server, allMemberModel.isUp_server());
            contentValues.put(col_dl_window, allMemberModel.isDl_window());
            contentValues.put(col_dl_server,allMemberModel.isDl_server());
            contentValues.put(col_credit_debit,allMemberModel.getCredit_debit());
            contentValues.put(col_sync_status,allMemberModel.getSync_status());
            contentValues.put(col_update_status,allMemberModel.getUpdate_status());

            database.insert(table_LCDC_gift_card_list,null,contentValues);
            database.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<AllMemberModel> GetInformationReturnArraylistGiftCardListLastFiveDataByGiftCardNumber(String GiftCardNumber){
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel> allMemberModelByGiftCardNumber = new ArrayList<AllMemberModel>();
           try{
               Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM LCDC_gift_card_list where card_number = ? ORDER BY gift_card_id DESC LIMIT 5 ", new String[]{GiftCardNumber});

               if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModel();
                            allMemberModel.setCredit_debit(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("credit_debit")));
                            allMemberModel.setAmount(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("amount")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModelByGiftCardNumber.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelByGiftCardNumber;
    }

    public AllMemberModel ViewGiftCardMemberDetailsFromSqliteDatabase(String giftCardNumber) {
        AllMemberModel allMemberModel = new AllMemberModel();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_card_details_list + " where trim(card_number) = ?",new String[]{giftCardNumber});
        try {

            if(cursor!=null && cursor.getCount()>0)
            {
                if(cursor.moveToFirst())
                {
                    do {
                        allMemberModel.setCard_number(cursor.getString(cursor.getColumnIndex("card_number")));
                        allMemberModel.setTotal_amount(cursor.getDouble(cursor.getColumnIndex("total_amount")));
                        allMemberModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                        allMemberModel.setFirst_name(cursor.getString(cursor.getColumnIndex("first_name")));
                        allMemberModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                        allMemberModel.setPostal_code(cursor.getString(cursor.getColumnIndex("postal_code")));
                        allMemberModel.setCity(cursor.getString(cursor.getColumnIndex("city")));
                        allMemberModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                        allMemberModel.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                        allMemberModel.setGift_card_id(cursor.getInt(cursor.getColumnIndex("gift_card_id")));
                    }
                    while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allMemberModel;
    }

    public int UpdateGiftCardDetailsInSqlite(AllMemberModel allMemberModel) {
        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_name,allMemberModel.getName());
            contentValues.put(col_first_name,allMemberModel.getFirst_name());
            contentValues.put(col_phone,allMemberModel.getPhone());
            contentValues.put(col_email,allMemberModel.getEmail());
            contentValues.put(col_total_amount,allMemberModel.getTotal_amount());
            contentValues.put(col_updated_by,allMemberModel.getUpdated_by());
            contentValues.put(col_updated_on, allMemberModel.getUpdated_on());
            contentValues.put(col_cashier_id,allMemberModel.getCashier_id());
            contentValues.put(col_update_status,allMemberModel.getUpdate_status());
            contentValues.put(col_up_window,allMemberModel.isUp_window());

            String whereClause = col_card_number + " = ?"; // HERE ARE OUR CONDITONS STARTS

            String[] whereArgs = {allMemberModel.getCard_number()};

            int count = database.update(table_LCDC_gift_card_details_list,contentValues,whereClause,whereArgs);

            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public int UpdateGiftCardDetailsAmountInSqlite(AllMemberModel allMemberModel){

        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(col_total_amount,allMemberModel.getTotal_amount());
            contentValues.put(col_update_status,allMemberModel.getUpdate_status());

            String whereClause = col_card_number + " = ?"; // HERE ARE OUR CONDITONS STARTS

            String[] whereArgs = {allMemberModel.getCard_number()};

            int count = database.update(table_LCDC_gift_card_details_list,contentValues,whereClause,whereArgs);

            return count;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    public ArrayList<AllMemberModel> GetGiftCardDetailsInformationFromSqlite(String syncStatus) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_card_details_list + " where trim(sync_status) = ?",new String[]{syncStatus});

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setGift_card_id(cursor.getInt(cursor.getColumnIndex("gift_card_id")));
                            allMemberModel.setCard_number(cursor.getString(cursor.getColumnIndex("card_number")));
                            allMemberModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                            allMemberModel.setFirst_name(cursor.getString(cursor.getColumnIndex("first_name")));
                            allMemberModel.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                            allMemberModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                            allMemberModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                            allMemberModel.setPostal_code(cursor.getString(cursor.getColumnIndex("postal_code")));
                            allMemberModel.setCity(cursor.getString(cursor.getColumnIndex("city")));
                            allMemberModel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                            allMemberModel.setTotal_amount(cursor.getDouble(cursor.getColumnIndex("total_amount")));
                            allMemberModel.setCreated_by(cursor.getString(cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
                            allMemberModel.setUpdated_by(cursor.getString(cursor.getColumnIndex("updated_by")));
                            allMemberModel.setUpdated_on(cursor.getString(cursor.getColumnIndex("updated_on")));
                            allMemberModel.setCashier_id(cursor.getString(cursor.getColumnIndex("cashier_id")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public ArrayList<AllMemberModel> GetGiftCardDetailsInformationFromSqliteForUpdate(String updateStatus) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_card_details_list + " where trim(update_status) = ?",new String[]{updateStatus});

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setGift_card_id(cursor.getInt(cursor.getColumnIndex("gift_card_id")));
                            allMemberModel.setCard_number(cursor.getString(cursor.getColumnIndex("card_number")));
                            allMemberModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                            allMemberModel.setFirst_name(cursor.getString(cursor.getColumnIndex("first_name")));
                            allMemberModel.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                            allMemberModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                            allMemberModel.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                            allMemberModel.setPostal_code(cursor.getString(cursor.getColumnIndex("postal_code")));
                            allMemberModel.setCity(cursor.getString(cursor.getColumnIndex("city")));
                            allMemberModel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                            allMemberModel.setTotal_amount(cursor.getDouble(cursor.getColumnIndex("total_amount")));
                            allMemberModel.setCreated_by(cursor.getString(cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
                            allMemberModel.setUpdated_by(cursor.getString(cursor.getColumnIndex("updated_by")));
                            allMemberModel.setUpdated_on(cursor.getString(cursor.getColumnIndex("updated_on")));
                            allMemberModel.setCashier_id(cursor.getString(cursor.getColumnIndex("cashier_id")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public ArrayList<AllMemberModel> GetGiftCardInformationFromSqlite(String SycnStatus) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_card_list + " where trim(sync_status) = ?",new String[]{SycnStatus});

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {
                            allMemberModel = new AllMemberModel();
                            allMemberModel.setGift_card_id(cursor.getInt(cursor.getColumnIndex("gift_card_id")));
                            allMemberModel.setCard_number(cursor.getString(cursor.getColumnIndex("card_number")));
                            allMemberModel.setAmount(cursor.getDouble(cursor.getColumnIndex("amount")));
                            allMemberModel.setCredit_debit(cursor.getString(cursor.getColumnIndex("credit_debit")));
                            allMemberModel.setCreated_by(cursor.getString(cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(cursor.getString(cursor.getColumnIndex("created_on")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public ArrayList<AllMemberModel> GetGiftProductInformationFromSqlite(/*String auto_id*/) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_product /*+ " where trim(auto_id) = ?",new String[]{auto_id}*/,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setGift_product_name(cursor.getString(cursor.getColumnIndex("gift_product_name")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }

                    cursor.close();

                }
                catch (Exception e)
                {
                   e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public void insertZipCodeInformation(ArrayList<AllMemberModel> ArrayListallMemberModel) {
        try{
            boolean check;
            SQLiteDatabase database = this.getWritableDatabase();

            for(int i=0;i<ArrayListallMemberModel.size();i++)
            {
                AllMemberModel allMemberModel = ArrayListallMemberModel.get(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(col_auto_id,allMemberModel.getAuto_id());
                contentValues.put(col_city,allMemberModel.getCity());
                contentValues.put(col_postal_code,allMemberModel.getZip_code());
                contentValues.put(col_department,allMemberModel.getDepartment());
                contentValues.put(col_geographical_code,allMemberModel.getGeographical_code());
                contentValues.put(col_country,allMemberModel.getCountry());
                contentValues.put(col_created_by,allMemberModel.getCreated_by());
                contentValues.put(col_created_on,allMemberModel.getCreated_on());

                database.insert(table_LCDC_ZipCode,null,contentValues);
                database.close();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<AllMemberModel> GetAllCityListThroughZIPCodeFromSqliteDatabase(String ZIP_Code) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_ZipCode + " where trim(postal_code) = ?",new String[]{ZIP_Code});

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setCity(cursor.getString(cursor.getColumnIndex("city")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public ArrayList<AllMemberModel> GetAllCityListFromSqliteDatabase() {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_ZipCode + " ORDER BY city DESC LIMIT 50" ,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setCity(cursor.getString(cursor.getColumnIndex("city")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public void insertActivityListIntoSqlite(AllMemberModel allMemberModel) {
        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_text,allMemberModel.getText());
            contentValues.put(col_value,allMemberModel.getValue());

            database.insert(table_LCDC_activity_list,null,contentValues);
            database.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<AllMemberModel> GetActivityListFromSqliteDatabase() {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();

        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_activity_list ,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setValue(cursor.getString(cursor.getColumnIndex("value")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public void insertCivilityListIntoSqlite(AllMemberModel allMemberModel) {
        try{
            SQLiteDatabase database = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(col_text,allMemberModel.getText());
            contentValues.put(col_value,allMemberModel.getValue());

            database.insert(table_LCDC_civility_list,null,contentValues);
            database.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public ArrayList<AllMemberModel> GetCivilityListFromSqliteDatabase() {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_civility_list ,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setValue(cursor.getString(cursor.getColumnIndex("value")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModelArrayList;
    }

    public ArrayList<AllMemberModel> GetCountryListFromSqliteDatabase() {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel>allMemberModelArrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{
            Cursor cursor = database.rawQuery("SELECT * FROM " + table_LCDC_ZipCode + " ORDER BY country DESC LIMIT 1" ,null);

            if(cursor != null && cursor.getCount()>0)
            {
                try{
                    if(cursor.moveToFirst())
                    {
                        do {

                            allMemberModel = new AllMemberModel();
                            allMemberModel.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                            allMemberModelArrayList.add(allMemberModel);

                        }while (cursor.moveToNext());
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return allMemberModelArrayList;
    }

    public boolean CheckIsInDBorNot(/*String auto_id*/) {
        //String selectQuery = "SELECT  * FROM " + table_LCDC_ZipCode + " where trim(auto_id) = ?" ;
        try{
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT  * FROM " + table_LCDC_ZipCode /*+ " where trim(auto_id) = ?",new String[]{auto_id}*/,null);

            if (cursor.getCount() > 0) {
                try{
                    return false;
                }finally {
                    if(cursor!=null)
                    {
                        cursor.close();
                    }
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

            return true;
    }

    public boolean CheckActivityDbExistsOrNot() {

        try{
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT  * FROM " + table_LCDC_activity_list /*+ " where trim(auto_id) = ?",new String[]{auto_id}*/,null);

            if (cursor.getCount() > 0) {
                try{
                    return false;
                }finally {
                    if(cursor!=null)
                    {
                        cursor.close();
                    }
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public boolean CheckCivilityDbExistsOrNot() {

        try{
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT  * FROM " + table_LCDC_civility_list /*+ " where trim(auto_id) = ?",new String[]{auto_id}*/,null);

            if (cursor.getCount() > 0) {
                try{
                    return false;
                }finally {
                    if(cursor!=null)
                    {
                        cursor.close();
                    }
                }
            }
            cursor.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return true;
    }

    public void DeleteZipCodeTable(){
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete(table_LCDC_gift_voucher_details,null,null);
        Log.e("Delete :","DataSuccesfully Deleted");
    }

    public void DeleteMemberFromMemberInfoTable(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getReadableDatabase();
        String whereCluaus = col_card_number + " = ?";
        String[] whereAgrs = {allMemberModel.getCard_number()};
        database.delete(table_LCDC_member_info,whereCluaus,whereAgrs);
    }

    public ArrayList<AllMemberModelByCardNumberOnly> GetInformationReturnArraylistCreditPointRewardTable(String status){
        AllMemberModelByCardNumberOnly allMemberModel = null;
        ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
         try{
             Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_reward + " where trim(update_status) = ?", new String[]{status});

             if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModelByCardNumberOnly();
                            allMemberModel.setReward_id(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("reward_id")));
                            allMemberModel.setMember_id(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("member_id")));
                            allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                            allMemberModel.setNo_of_points(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModel.setUpdated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_by")));
                            allMemberModel.setUpdated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_on")));
                            allMemberModel.setComment(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("comment")));
                            allMemberModel.setReward_type(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("reward_type")));

                            allMemberModelByCardMiBarcodes.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelByCardMiBarcodes;
    }

    public ArrayList<AllMemberModelByCardNumberOnly> GetInformationReturnArraylistCreditPointRewardTableLastFiveData(String CardNumber){
        AllMemberModelByCardNumberOnly allMemberModel = null;
        ArrayList<AllMemberModelByCardNumberOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByCardNumberOnly>();
           try{
               Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM LCDC_MemberRewardInfo where card_number = ? ORDER BY reward_id DESC LIMIT 5 ", new String[]{CardNumber});

               if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModelByCardNumberOnly();
                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                            allMemberModel.setNo_of_points(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModelByCardMiBarcodes.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelByCardMiBarcodes;
    }

    public ArrayList<AllMemberModelByMiBarCodeOnly> GetInformationReturnArraylistCreditPointRewardTableLastFiveDataByMiBarcode(String MiBarcode){
        AllMemberModelByMiBarCodeOnly allMemberModel = null;
        ArrayList<AllMemberModelByMiBarCodeOnly> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModelByMiBarCodeOnly>();
            try{
                Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM LCDC_MemberRewardInfo where mi_barcode = ? ORDER BY reward_id DESC LIMIT 5 ", new String[]{MiBarcode});

                if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModelByMiBarCodeOnly();
                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                            allMemberModel.setNo_of_points(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("no_of_points")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModelByCardMiBarcodes.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelByCardMiBarcodes;
    }

    public int UpdateCr_serverForGift(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_server, allMemberModel.isCr_server());

        String whereClause = col_auto_id + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={String.valueOf(allMemberModel.getAuto_id())};

        int count = database.update(table_LCDC_gift_product,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_serverForReason(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_server, allMemberModel.isCr_server());

        String whereClause = col_reason_id + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={String.valueOf(allMemberModel.getReason_id())};

        int count = database.update(table_LCDC_reason_master,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateUP_ServerForGift(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_server, allMemberModel.isUp_server());

        String whereClause = col_auto_id + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={String.valueOf(allMemberModel.getAuto_id())};

        int count = database.update(table_LCDC_gift_product,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateUP_ServerForReason(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_server, allMemberModel.isUp_server());

        String whereClause = col_reason_id + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={String.valueOf(allMemberModel.getReason_id())};

        int count = database.update(table_LCDC_reason_master,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_WindowForReward(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_sync_status,"Sync");
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_member_reward,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_WindowForGiftCardDetails(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_sync_status,"Sync");
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_gift_card_details_list,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateUp_WindowForGiftCardDetails(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_sync_status,"Sync");
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_gift_card_details_list,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_WindowForGiftCard(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_sync_status,"Sync");
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_gift_card_list,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_WindowForGiftVoucher(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_sync_status,"Sync");
        values.put(col_update_status,"Checked");

        String whereClause = col_card_number + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getCard_number()};

        int count = database.update(table_LCDC_gift_voucher,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_WindowForLogTable(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_window, allMemberModel.isCr_window());

        values.put(col_sync_status,"Sync");

        String whereClause = col_sync_status + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getSync_status()};

        int count = database.update(table_LCDC_member_log_info,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateCr_serverForVoucher(AllMemberModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_cr_server, allMemberModel.isCr_server());

        String whereClause = col_vd_barcode + " = ? " ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getVd_barcode()};

        int count = database.update(table_LCDC_gift_voucher_details,values,whereClause,whereArgs);

        return count;
    }

    public void insertGiftVoucherByCardNumber(AllMemberModel allMemberModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_card_number, allMemberModel.getCard_number());
        values.put(col_name,allMemberModel.getName());
        values.put(col_first_name,allMemberModel.getFirst_name());
        values.put(col_phone,allMemberModel.getPhone());
        values.put(col_email,allMemberModel.getEmail());
        values.put(col_gift_product_name,allMemberModel.getGift_product_name());
        values.put(col_reason,allMemberModel.getReason());
        values.put(col_created_by,allMemberModel.getCreated_by());
        values.put(col_created_on,allMemberModel.getCreated_on());
        values.put(col_cr_window, allMemberModel.isCr_window());
        values.put(col_cr_server, allMemberModel.isCr_server());
        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_up_server, allMemberModel.isUp_server());
        values.put(col_dl_window, allMemberModel.isDl_window());
        values.put(col_dl_server,allMemberModel.isDl_server());
        values.put(col_cashier_id, allMemberModel.getCashier_id());
        values.put(col_sync_status,allMemberModel.getSync_status());
        values.put(col_update_status,allMemberModel.getUpdate_status());

        database.insert(table_LCDC_gift_voucher, null, values);
        database.close();

    }

    public boolean InsertGiftVoucherFromServerToApp(AllVoucherModel allMemberModel) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.e("GetVoucherIdInSqlite",""+allMemberModel.getUpdate_status());
        contentValues.put(col_voucher_id,allMemberModel.getVoucher_id());
        contentValues.put(col_cashier_id,allMemberModel.getCashier_id());
        contentValues.put(col_voucher_type,allMemberModel.getVoucher_type());
        contentValues.put(col_voucher_date,allMemberModel.getVoucher_date());
        contentValues.put(col_member_id,allMemberModel.getMember_id());
        contentValues.put(col_card_number,allMemberModel.getCard_number());
        contentValues.put(col_vd_barcode,allMemberModel.getVd_barcode());
        contentValues.put(col_voucher_exp_date,allMemberModel.getVoucher_exp_date());
        contentValues.put(col_voucher_stage,allMemberModel.getVoucher_stage());
        contentValues.put(col_montent,allMemberModel.getMontent());
        contentValues.put(col_template_id,allMemberModel.getTemplate_id());
        contentValues.put(col_created_by,allMemberModel.getCreated_by());
        contentValues.put(col_created_on,allMemberModel.getCreated_on());
        contentValues.put(col_updated_by,allMemberModel.getUpdated_by());
        contentValues.put(col_updated_on, allMemberModel.getUpdated_on());
        contentValues.put(col_cr_window, allMemberModel.isCr_window());
        contentValues.put(col_cr_server, allMemberModel.isCr_server());
        contentValues.put(col_up_window, allMemberModel.isUp_window());
        contentValues.put(col_up_server, allMemberModel.isUp_server());
        contentValues.put(col_dl_window, allMemberModel.isDl_window());
        contentValues.put(col_dl_server,allMemberModel.isDl_server());
        contentValues.put(col_sync_status,allMemberModel.getSync_status());
        contentValues.put(col_update_status,allMemberModel.getUpdate_status());
        contentValues.put(col_voucher_status,allMemberModel.getVoucher_status());

        database.insert(table_LCDC_gift_voucher_details,null,contentValues);
        database.close();
        return true;
    }

    public ArrayList<AllMemberModel> GetInformationReturnArraylistGiftVoucherTable(String status){
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel> allMemberModelArraylist= new ArrayList<AllMemberModel>();
        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_voucher + " where trim(update_status) = ?", new String[]{status});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModel();

                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                            allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                            allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                            allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                            allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                            allMemberModel.setGift_product_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gift_product_name")));
                            allMemberModel.setReason(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("reason")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));

                            allMemberModelArraylist.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelArraylist;
    }

    public ArrayList<AllMemberModel> GetInformationReturnArraylistFromLogTable(String status){
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel> allMemberModelByCardMiBarcodes = new ArrayList<AllMemberModel>();
        Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_member_log_info + " where trim(sync_status) = ?", new String[]{status});
        try{
            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try{
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllMemberModel();
                            allMemberModel.setCashier_id(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("cashier_id")));
                            allMemberModel.setCard_number(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("card_number")));
                            allMemberModel.setCivility(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("civility")));
                            allMemberModel.setGender(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("gender")));
                            allMemberModel.setName(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("name")));
                            allMemberModel.setFirst_name(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("first_name")));
                            allMemberModel.setBirth_date(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("birth_date")));
                            allMemberModel.setAge(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("age")));
                            allMemberModel.setSociety(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("society")));
                            allMemberModel.setActivity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("activity")));
                            allMemberModel.setAddress_line1(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line1")));
                            allMemberModel.setAddress_line2(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("address_line2")));
                            allMemberModel.setPostal_code(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("postal_code")));
                            allMemberModel.setCity(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("city")));
                            allMemberModel.setCountry(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("country")));
                            allMemberModel.setPhone(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("phone")));
                            allMemberModel.setEmail(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("email")));
                            allMemberModel.setMi_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("mi_barcode")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("created_on")));
                            allMemberModelByCardMiBarcodes.add(allMemberModel);

                        } while (allMember_Cursor.moveToNext());
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                allMember_Cursor.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return allMemberModelByCardMiBarcodes;
    }

    public ArrayList<AllVoucherModel> GetInformationReturnArraylistGiftVoucherDetailsTable(String status){
        AllVoucherModel allMemberModel = null;
        ArrayList<AllVoucherModel> allMemberModelArraylist= new ArrayList<AllVoucherModel>();
        try{
            Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_voucher_details + " where trim(update_status) = ?", new String[]{status});

            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllVoucherModel();
                            allMemberModel.setVoucher_id(allMember_Cursor.getInt(allMember_Cursor.getColumnIndex("voucher_id")));
                            allMemberModel.setVd_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("vd_barcode")));
                            allMemberModel.setCreated_by(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_by")));
                            allMemberModel.setCreated_on(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("updated_on")));
                            allMemberModel.setVoucher_stage(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_stage")));

                            allMemberModelArraylist.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelArraylist;
    }

    public ArrayList<AllVoucherModel> GetInVoucherDateReturnArraylistGiftVoucherDetailsTable(String voucher_status){
        AllVoucherModel allMemberModel = null;
        ArrayList<AllVoucherModel> allMemberModelArraylist= new ArrayList<AllVoucherModel>();
        try{
            Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_voucher_details + " where trim(voucher_status) = ?", new String[]{voucher_status});

            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllVoucherModel();
                            String GetDate = allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_exp_date")).replace("T"," ");
                            String StrDate = new CurrentTimeGet().getCurrentTime();

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date1 = simpleDateFormat.parse(GetDate);
                            Date date2 = simpleDateFormat.parse(StrDate);

                            if(date2.after(date1))
                            {
                                allMemberModel.setVoucher_stage(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_stage")));
                                allMemberModel.setVd_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("vd_barcode")));
                            }

                            allMemberModelArraylist.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelArraylist;
    }

    public ArrayList<AllVoucherModel> GetReturnArraylistGiftVoucherDetailsTableInActivities(String CardNumber){
        AllVoucherModel allMemberModel = null;
        ArrayList<AllVoucherModel> allMemberModelArraylist= new ArrayList<AllVoucherModel>();
        try{
            Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_voucher_details + " where trim(card_number) = ?", new String[]{CardNumber});

            if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                try {
                    if (allMember_Cursor.moveToFirst()) {
                        do {
                            allMemberModel = new AllVoucherModel();
                            String GetDate = allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_exp_date")).replace("T"," ");

                            if(GetDate.contains(" "))
                            {
                                GetDate = GetDate.substring(0,GetDate.indexOf(" "));
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MMM/yyyy");
                                Date date = simpleDateFormat.parse(GetDate);
                                String Date = simpleDateFormat1.format(date);
                                allMemberModel.setVoucher_exp_date(Date);
                            }
                                allMemberModel.setVoucher_stage(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_stage")));
                                allMemberModel.setVoucher_type(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_type")));
                                allMemberModel.setMontent(allMember_Cursor.getDouble(allMember_Cursor.getColumnIndex("montent")));
                                allMemberModel.setVd_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("vd_barcode")));


                            allMemberModelArraylist.add(allMemberModel);
                        } while (allMember_Cursor.moveToNext());
                    }
                }
                finally {

                    if(allMember_Cursor!=null)
                    {
                        allMember_Cursor.close();
                    }
                }

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return allMemberModelArraylist;
    }

    public int UpdateVoucherStageInVoucherDetailsTable(AllVoucherModel allVoucherModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_voucher_stage,allVoucherModel.getVoucher_stage());
        contentValues.put(col_voucher_status,allVoucherModel.getVoucher_status());

        String wherClause = col_vd_barcode + " = ?";
        String[] whereArgs = {allVoucherModel.getVd_barcode()};

        int count = database.update(table_LCDC_gift_voucher_details,contentValues,wherClause,whereArgs);

        return count;
    }

    public int UpdateUp_WindowForVoucher(AllVoucherModel allMemberModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(col_up_window, allMemberModel.isUp_window());
        values.put(col_update_status,"NotChecked");

        String whereClause = col_vd_barcode + " = ?" ; // HERE ARE OUR CONDITONS STARTS

        String[] whereArgs={allMemberModel.getVd_barcode()};

        int count = database.update(table_LCDC_gift_voucher_details,values,whereClause,whereArgs);

        return count;
    }

    public int UpdateAllVoucherInformation(AllVoucherModel allVoucherModel) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(col_voucher_stage,allVoucherModel.getVoucher_stage());
        contentValues.put(col_updated_by,allVoucherModel.getUpdated_by());
        contentValues.put(col_updated_on,allVoucherModel.getUpdated_on());
        contentValues.put(col_up_window,allVoucherModel.isUp_window());
        contentValues.put(col_update_status,allVoucherModel.getUpdate_status());

        String whereClouse = col_vd_barcode + " = ?";
        String[] whereArgs = {allVoucherModel.getVd_barcode()};

        int count = database.update(table_LCDC_gift_voucher_details,contentValues,whereClouse,whereArgs);

        return count;

    }

    public AllVoucherModel getAllVoucherInformation(String vd_barcode) {
        AllVoucherModel allMemberModel = null;
        SQLiteDatabase database = this.getReadableDatabase();
        try
        {
            Cursor allMember_Cursor = database.rawQuery("SELECT * FROM " + table_LCDC_gift_voucher_details + " where trim(vd_barcode) = ?",new String[]{vd_barcode});
            if(allMember_Cursor!=null && allMember_Cursor.getCount()>0)
            {
                do{
                    if(allMember_Cursor.moveToFirst())
                    {
                        allMemberModel = new AllVoucherModel();

                        allMemberModel.setVd_barcode(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("vd_barcode")));
                        allMemberModel.setVoucher_stage(allMember_Cursor.getString(allMember_Cursor.getColumnIndex("voucher_stage")));

                    }

                }while (allMember_Cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        return allMemberModel;
    }

    public boolean GetInformationByVdBarcoderToCheckVoucher(String vd_barcode){
        AllVoucherModel allMemberModel = null;

           try{
               Cursor allMember_Cursor = getReadableDatabase().rawQuery("SELECT * FROM " + table_LCDC_gift_voucher_details + " where trim(vd_barcode) = ?", new String[]{vd_barcode});
               if (allMember_Cursor != null && allMember_Cursor.getCount() > 0) {
                // looping through all rows and adding to list
                if (allMember_Cursor.moveToFirst())
                {
                    do {
                        allMemberModel = new AllVoucherModel();

                        return true;

                    } while (allMember_Cursor.moveToNext());
                }
            }
            else
            {
                allMember_Cursor.close();
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public ArrayList<AllMemberModel> SearchResultsOfMemberDetails(String Query) {
        AllMemberModel allMemberModel = null;
        ArrayList<AllMemberModel> arrayList = new ArrayList<AllMemberModel>();
        SQLiteDatabase database = this.getReadableDatabase();
        try{

           /* Cursor cursor = database.rawQuery("SELECT * FROM LCDCMemberInfo where name like '%"+ Query +"%' OR "
                    +" first_name like '%"+ Query +"%' OR phone like '%"+ Query +"%' ORDER BY member_id ASC  "*//*+" card_number like '%"+ Query +
                    "%' OR "+" mi_barcode like '%"+ Query +
                    "%'*//* , null);*/

            Cursor cursor = database.rawQuery("SELECT * FROM LCDCMemberInfo where name like '%"+ Query +"%' OR "
                    +" first_name like '%"+ Query +"%' OR phone like '%"+ Query +"%' OR "+" card_number like '%"+ Query +
                    "%' OR "+" mi_barcode like '%"+ Query +
                    "%' ORDER BY member_id ASC ", null);
            try {
                if (cursor != null && cursor.getCount() > 0) {
                    do {
                        if (cursor.moveToFirst()) {
                            Log.e("GetFirstNameCursor ",""+cursor.getString(cursor.getColumnIndex("first_name")));
                            allMemberModel = new AllMemberModel();
                            allMemberModel.setFirst_name(cursor.getString(cursor.getColumnIndex("first_name")));
                            allMemberModel.setName(cursor.getString(cursor.getColumnIndex("name")));
                            allMemberModel.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                            allMemberModel.setCard_number(cursor.getString(cursor.getColumnIndex("card_number")));
                            allMemberModel.setMi_barcode(cursor.getString(cursor.getColumnIndex("mi_barcode")));
                            arrayList.add(allMemberModel);
                        }
                    } while (cursor.moveToNext());
                }
            }
            finally {

                if(cursor!=null)
                {
                    cursor.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return arrayList;
    }

    public Cursor RetrievSearchResultsOfMemberDetails(String Query) {
        Cursor cursor = null;
        SQLiteDatabase database = this.getReadableDatabase();
        String[] columns={"name","first_name","phone","card_number","mi_barcode"};

        if(Query!=null && Query.length()>0)
        {
             cursor = database.rawQuery("SELECT * FROM LCDCMemberInfo where name like '%"+ Query +"%' OR "
                    +" first_name like '%"+ Query +"%' OR phone like '%"+ Query +"%' OR "+" card_number like '%"+ Query +
                    "%' OR "+" mi_barcode like '%"+ Query +
                    "%' ORDER BY member_id ASC ", null);

             return cursor;
        }
        cursor=database.query("LCDCMemberInfo",columns,null,null,null,null,null);

        return cursor;
    }

    public boolean CheckSearchResultsOfMemberDetails(String Query) {
        AllMemberModel allMemberModel = null;
        SQLiteDatabase database = this.getReadableDatabase();
        try{

            Cursor cursor = database.rawQuery("SELECT * FROM LCDCMemberInfo where name like '%"+ Query +"%' OR "
                    +" first_name like '%"+ Query +"%' OR phone like '%"+ Query +"%' OR "+" card_number like '%"+ Query +
                    "%' OR "+" mi_barcode like '%"+ Query +
                    "%' ORDER BY member_id ASC ", null);


            if(cursor != null && cursor.getCount()>0)
            {
                if(cursor.moveToFirst()) {
                    do {

                        return true;

                    } while (cursor.moveToNext());
                }
            }
            else
            {
                cursor.close();
                return false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return true;
    }

    public AllVoucherModel getNumberOfVoucherSendCount(String cardNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        AllVoucherModel allMemberModel = null;
        Cursor cursor= db.rawQuery("SELECT COUNT (*) as voucher_id_auto FROM LCDC_gift_voucher_details  where trim(card_number) = ?" ,
                new String[] {cardNumber});
        try{
            if(cursor!=null && cursor.getCount()>0){

                if(cursor.moveToFirst())
                {
                    do{

                        allMemberModel = new AllVoucherModel();
                        allMemberModel.setTotal_count(cursor.getInt(cursor.getColumnIndex("voucher_id_auto")));
                    }while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public AllVoucherModel getNumberOfMontentUtiliseCount(String VoucherStage , String CardNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        AllVoucherModel allMemberModel = null;
        Cursor cursor= db.rawQuery("SELECT COUNT (*) as voucher_id_auto FROM LCDC_gift_voucher_details  where trim(voucher_stage) = ? AND trim(card_number)= ?" ,
                new String[] {VoucherStage,CardNumber});
        try{
            if(cursor!=null && cursor.getCount()>0){

                if(cursor.moveToFirst())
                {
                    do{

                        allMemberModel = new AllVoucherModel();
                        allMemberModel.setTotal_count(cursor.getInt(cursor.getColumnIndex("voucher_id_auto")));
                    }while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }

    public AllVoucherModel getNumberOEncurseCount(String VoucherStage,String CardNumber) {

        SQLiteDatabase db = this.getReadableDatabase();
        AllVoucherModel allMemberModel = null;
        Cursor cursor= db.rawQuery("SELECT COUNT (*) as voucher_id_auto FROM LCDC_gift_voucher_details  where trim(voucher_stage) = ? AND trim(card_number)= ?" ,
                new String[] {VoucherStage,CardNumber});
        try{
            if(cursor!=null && cursor.getCount()>0){

                if(cursor.moveToFirst())
                {
                    do{

                        allMemberModel = new AllVoucherModel();
                        allMemberModel.setTotal_count(cursor.getInt(cursor.getColumnIndex("voucher_id_auto")));
                    }while (cursor.moveToNext());
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allMemberModel;
    }




}
