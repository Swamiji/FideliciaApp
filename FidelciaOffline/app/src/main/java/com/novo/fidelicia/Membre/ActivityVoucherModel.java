package com.novo.fidelicia.Membre;

public class ActivityVoucherModel {
    String type_de_bon,date_of_expiration,montant,etat,vd_barcode;

    public ActivityVoucherModel(String type_de_bon, String date_of_expiration, String montant, String etat, String vd_barcode) {
        this.type_de_bon = type_de_bon;
        this.date_of_expiration = date_of_expiration;
        this.montant = montant;
        this.etat = etat;
        this.vd_barcode = vd_barcode;
    }

    public ActivityVoucherModel() {
    }

    public String getVd_barcode() {
        return vd_barcode;
    }

    public void setVd_barcode(String vd_barcode) {
        this.vd_barcode = vd_barcode;
    }

    public String getType_de_bon() {
        return type_de_bon;
    }

    public void setType_de_bon(String type_de_bon) {
        this.type_de_bon = type_de_bon;
    }

    public String getDate_of_expiration() {
        return date_of_expiration;
    }

    public void setDate_of_expiration(String date_of_expiration) {
        this.date_of_expiration = date_of_expiration;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}