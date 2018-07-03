package com.novo.fidelicia.index;

public class UpdateMemberModel {
    int member_id;
    String card_number,mi_barcode;

    public UpdateMemberModel() {
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getMi_barcode() {
        return mi_barcode;
    }

    public void setMi_barcode(String mi_barcode) {
        this.mi_barcode = mi_barcode;
    }
}
