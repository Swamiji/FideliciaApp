package com.novo.fidelicia.GiftCard;

public class GiftCardDebitModel {
    String points,last_visit_date,status;

    public GiftCardDebitModel(String points, String last_visit_date,String status) {
        this.points = points;
        this.last_visit_date = last_visit_date;
        this.status = status;
    }

    public GiftCardDebitModel() {
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getLast_visit_date() {
        return last_visit_date;
    }

    public void setLast_visit_date(String last_visit_date) {
        this.last_visit_date = last_visit_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
