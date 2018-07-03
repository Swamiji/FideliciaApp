package com.novo.fidelicia.Créditer;

public class CrediterModel {
    String points,last_visit_date;

    public CrediterModel(String points, String last_visit_date) {
        this.points = points;
        this.last_visit_date = last_visit_date;
    }

    public CrediterModel() {
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
}
