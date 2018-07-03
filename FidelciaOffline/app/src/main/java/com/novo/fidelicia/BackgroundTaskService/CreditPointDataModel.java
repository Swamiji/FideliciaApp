package com.novo.fidelicia.BackgroundTaskService;

public class CreditPointDataModel {

    String cashier_id,card_number,no_of_points;

    public CreditPointDataModel() {
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

    public String getNo_of_points() {
        return no_of_points;
    }

    public void setNo_of_points(String no_of_points) {
        this.no_of_points = no_of_points;
    }
}
