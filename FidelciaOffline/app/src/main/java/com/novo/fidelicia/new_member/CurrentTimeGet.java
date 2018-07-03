package com.novo.fidelicia.new_member;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentTimeGet {
    String strDate;
    Calendar cal;
    SimpleDateFormat sdf;


    public String getCurrentTime()
    {
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
        strDate= sdf.format(cal.getTime());
        return strDate;
    }
}

