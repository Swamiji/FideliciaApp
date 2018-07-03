package com.novo.fidelicia.database;

import java.sql.Date;

public class AllMemberModel {
    int member_id,age,auto_id,reason_id,zip_code,geographical_code,gift_card_id,total_count,voucher_id;
    String cashier_id,card_number,civility,gender,name,first_name,society,activity,address_line1,address_line2,postal_code,
            city,country,portable,phone,email,mi_barcode,created_by,updated_by,reward_type,comment,customer_id
            ,gift_product_name,reason,voucher_date,voucher_type,vd_barcode,voucher_exp_date,voucher_stage,template_id;
    String birth_date,last_visit,created_on,updated_on,sync_status,update_status,department,text,value,address,credit_debit;
    float balance_card,turn_over,average_basket,total_purchase_order,total_uses,in_progress;
    Double no_of_points,amount,total_amount;
    boolean npai,stop_sms,stop_email,cr_window,cr_server,up_window,up_server,dl_window,dl_server;
    public AllMemberModel() {

    }

    public AllMemberModel(int member_id, String card_number, String mi_barcode) {
        this.member_id = member_id;
        this.card_number = card_number;
        this.mi_barcode = mi_barcode;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isNpai() {
        return npai;
    }

    public void setNpai(boolean npai) {
        this.npai = npai;
    }

    public boolean isStop_sms() {
        return stop_sms;
    }

    public void setStop_sms(boolean stop_sms) {
        this.stop_sms = stop_sms;
    }

    public boolean isStop_email() {
        return stop_email;
    }

    public void setStop_email(boolean stop_email) {
        this.stop_email = stop_email;
    }

    public boolean isCr_window() {
        return cr_window;
    }

    public void setCr_window(boolean cr_window) {
        this.cr_window = cr_window;
    }

    public boolean isCr_server() {
        return cr_server;
    }

    public void setCr_server(boolean cr_server) {
        this.cr_server = cr_server;
    }

    public boolean isUp_window() {
        return up_window;
    }

    public void setUp_window(boolean up_window) {
        this.up_window = up_window;
    }

    public boolean isUp_server() {
        return up_server;
    }

    public void setUp_server(boolean up_server) {
        this.up_server = up_server;
    }

    public boolean isDl_window() {
        return dl_window;
    }

    public void setDl_window(boolean dl_window) {
        this.dl_window = dl_window;
    }

    public boolean isDl_server() {
        return dl_server;
    }

    public void setDl_server(boolean dl_server) {
        this.dl_server = dl_server;
    }

    public String getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(String cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCivility() {
        return civility;
    }

    public void setCivility(String civility) {
        this.civility = civility;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getAddress_line1() {
        return address_line1;
    }

    public void setAddress_line1(String address_line1) {
        this.address_line1 = address_line1;
    }

    public String getAddress_line2() {
        return address_line2;
    }

    public void setAddress_line2(String address_line2) {
        this.address_line2 = address_line2;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPortable() {
        return portable;
    }

    public void setPortable(String portable) {
        this.portable = portable;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMi_barcode() {
        return mi_barcode;
    }

    public void setMi_barcode(String mi_barcode) {
        this.mi_barcode = mi_barcode;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getLast_visit() {
        return last_visit;
    }

    public void setLast_visit(String last_visit) {
        this.last_visit = last_visit;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public float getBalance_card() {
        return balance_card;
    }

    public void setBalance_card(float balance_card) {
        this.balance_card = balance_card;
    }

    public float getTurn_over() {
        return turn_over;
    }

    public void setTurn_over(float turn_over) {
        this.turn_over = turn_over;
    }

    public float getAverage_basket() {
        return average_basket;
    }

    public void setAverage_basket(float average_basket) {
        this.average_basket = average_basket;
    }

    public float getTotal_purchase_order() {
        return total_purchase_order;
    }

    public void setTotal_purchase_order(float total_purchase_order) {
        this.total_purchase_order = total_purchase_order;
    }

    public float getTotal_uses() {
        return total_uses;
    }

    public void setTotal_uses(float total_uses) {
        this.total_uses = total_uses;
    }

    public float getIn_progress() {
        return in_progress;
    }

    public void setIn_progress(float in_progress) {
        this.in_progress = in_progress;
    }

    public Double getNo_of_points() {
        return no_of_points;
    }

    public void setNo_of_points(Double no_of_points) {
        this.no_of_points = no_of_points;
    }

    public String getSync_status() {
        return sync_status;
    }

    public void setSync_status(String sync_status) {
        this.sync_status = sync_status;
    }

    public String getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(String update_status) {
        this.update_status = update_status;
    }

    public String getReward_type() {
        return reward_type;
    }

    public void setReward_type(String reward_type) {
        this.reward_type = reward_type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public int getReason_id() {
        return reason_id;
    }

    public void setReason_id(int reason_id) {
        this.reason_id = reason_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getGift_product_name() {
        return gift_product_name;
    }

    public void setGift_product_name(String gift_product_name) {
        this.gift_product_name = gift_product_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public int getGeographical_code() {
        return geographical_code;
    }

    public void setGeographical_code(int geographical_code) {
        this.geographical_code = geographical_code;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public int getGift_card_id() {
        return gift_card_id;
    }

    public void setGift_card_id(int gift_card_id) {
        this.gift_card_id = gift_card_id;
    }

    public String getCredit_debit() {
        return credit_debit;
    }

    public void setCredit_debit(String credit_debit) {
        this.credit_debit = credit_debit;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_date() {
        return voucher_date;
    }

    public void setVoucher_date(String voucher_date) {
        this.voucher_date = voucher_date;
    }

    public String getVoucher_type() {
        return voucher_type;
    }

    public void setVoucher_type(String voucher_type) {
        this.voucher_type = voucher_type;
    }

    public String getVd_barcode() {
        return vd_barcode;
    }

    public void setVd_barcode(String vd_barcode) {
        this.vd_barcode = vd_barcode;
    }

    public String getVoucher_exp_date() {
        return voucher_exp_date;
    }

    public void setVoucher_exp_date(String voucher_exp_date) {
        this.voucher_exp_date = voucher_exp_date;
    }

    public String getVoucher_stage() {
        return voucher_stage;
    }

    public void setVoucher_stage(String voucher_stage) {
        this.voucher_stage = voucher_stage;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }
}
