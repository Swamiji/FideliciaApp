package com.novo.fidelicia.BackgroundTaskService;

public class GiftProductModel {

    int auto_id;
    String updated_by,update_on,gift_product_name;

    public GiftProductModel() {
    }

    public int getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(int auto_id) {
        this.auto_id = auto_id;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUpdate_on() {
        return update_on;
    }

    public void setUpdate_on(String update_on) {
        this.update_on = update_on;
    }

    public String getGift_product_name() {
        return gift_product_name;
    }

    public void setGift_product_name(String gift_product_name) {
        this.gift_product_name = gift_product_name;
    }
}
