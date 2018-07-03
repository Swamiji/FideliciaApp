package com.novo.fidelicia.Membre;

public class ActivityModel {
    String Credit,Date;

    public ActivityModel(String credit, String date) {
        Credit = credit;
        Date = date;
    }

    public ActivityModel() {
    }

    public String getCredit() {
        return Credit;
    }

    public void setCredit(String credit) {
        Credit = credit;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}