package com.novo.fidelicia.BackgroundTaskService;

public class ReasonMasterModel {
    int reason_id;
    String updated_by,update_on,reason;

    public ReasonMasterModel() {
    }

    public int getReason_id() {
        return reason_id;
    }

    public void setReason_id(int reason_id) {
        this.reason_id = reason_id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
