package com.novo.fidelicia.index;

public class VoucherDetailsModel {
    int voucher_id;
    String  card_number,vd_barcode,updated_by,updated_on,voucher_stage;

    public VoucherDetailsModel() {
    }


    public int getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(int voucher_id) {
        this.voucher_id = voucher_id;
    }


    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getVd_barcode() {
        return vd_barcode;
    }

    public void setVd_barcode(String vd_barcode) {
        this.vd_barcode = vd_barcode;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }

    public String getVoucher_stage() {
        return voucher_stage;
    }

    public void setVoucher_stage(String voucher_stage) {
        this.voucher_stage = voucher_stage;
    }
}
