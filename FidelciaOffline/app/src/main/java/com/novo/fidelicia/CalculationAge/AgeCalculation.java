package com.novo.fidelicia.CalculationAge;

import android.util.Log;

import java.util.Calendar;

public class AgeCalculation {
    private int startYear;
    private int startMonth;
    private int startDay;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int resYear;
    private int resMonth;
    private int resDay;
    private Calendar start;
    private Calendar end;

    public String getCurrentDate()
    {
        end = Calendar.getInstance();
        endYear = end.get(Calendar.YEAR);
        Log.e("getCalenderYear :",""+endYear);
        endMonth = end.get(Calendar.MONTH);
        endMonth++;
        endDay = end.get(Calendar.DAY_OF_MONTH);
        return endDay+":"+endMonth+":"+endYear;
    }

    public void setDateOfBirth(int sYear,int sMonth,int sDay)
    {
        startYear = sYear;
        startMonth = sMonth;
        startMonth++;
        startDay = sDay;
    }

    public void calculateYear()
    {

        resYear = endYear-startYear;
        Log.e("EndYear",""+endYear);
        Log.e("StartYear",""+startYear);
        Log.e("GetYear",""+resYear);
    }

    public void calculateMonth()
    {
        if(endMonth>=startMonth){
            resMonth = endMonth-startMonth;
        }
        else
        {
            resMonth = endMonth-startMonth;
            resMonth = 12+resMonth;
            resMonth--;
        }
    }

    public void calculateDay()
    {
        if(endDay>=startDay)
        {
            resDay = endDay-startDay;
        }
        else
        {
            resDay = endDay-startDay;
            resDay = 30+resDay;
            if(resMonth==0)
            {
                resMonth=11;
                resYear--;
            }
            else
            {
                resMonth--;
            }
        }
    }

    public String getResult()
    {
        return resYear+"";
    }

    public long getSecond()
    {
        start = Calendar.getInstance();
        start.set(Calendar.YEAR,startYear);
        start.set(Calendar.MONTH,startMonth);
        start.set(Calendar.DAY_OF_MONTH,startDay);
        start.set(Calendar.HOUR,12);
        start.set(Calendar.MINUTE,30);
        start.set(Calendar.SECOND,30);
        start.set(Calendar.MILLISECOND,30);
        long now = end.getTimeInMillis();
        long old = start.getTimeInMillis();
        long diff = old-now;
        return diff/1000;

    }
}
