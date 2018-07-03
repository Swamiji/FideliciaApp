package com.novo.fidelicia.Search;

public class SearchModel {
    String Prenom,Nom,Telephone,MiBarcode,CardNumber;

    public SearchModel(String prenom, String nom, String telephone, String miBarcode, String cardNumber) {
        Prenom = prenom;
        Nom = nom;
        Telephone = telephone;
        MiBarcode = miBarcode;
        CardNumber = cardNumber;
    }

    public SearchModel() {
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getMiBarcode() {
        return MiBarcode;
    }

    public void setMiBarcode(String miBarcode) {
        MiBarcode = miBarcode;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }
}
