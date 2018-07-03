package com.novo.fidelicia.index;

public class AllVoucherModel {
    int member_id,voucher_id;
    String  cashier_id,card_number,voucher_date,voucher_type,vd_barcode,
            voucher_exp_date,voucher_stage,template_id,created_on,updated_on,sync_status
            ,update_status,created_by,updated_by,voucher_status;
    boolean cr_window,cr_server,up_window,up_server,dl_window,dl_server;

    public AllVoucherModel() {
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
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

    public String getVoucher_status() {
        return voucher_status;
    }

    public void setVoucher_status(String voucher_status) {
        this.voucher_status = voucher_status;
    }
}
