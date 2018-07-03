package com.novo.fidelicia.GiftCard;

import android.content.Context;
import android.icu.text.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.novo.fidelicia.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by User on 12-01-2018.
 */

public class GiftCardDebitAdapter extends BaseAdapter {
    private Context var;
    ArrayList<GiftCardDebitModel> items=new ArrayList<GiftCardDebitModel>();
    String convertedDate,convertedTime;

    public GiftCardDebitAdapter(Context var, ArrayList<GiftCardDebitModel> items ) {
        this.var = var;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        final GiftCardDebitModel pojo=items.get(position);
        LayoutInflater inflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.last_visit_giftcard_debit_layout,null);
            holder=new ViewHolder();

            holder.Points_gain = (TextView)convertView.findViewById(R.id.no_of_points);
            holder.Date = (TextView)convertView.findViewById(R.id.last_visit_date);
            holder.Minus_Plus = (TextView)convertView.findViewById(R.id.plus_minus);

            String ModifiedDate = pojo.getLast_visit_date().replace("T"," ");
            String ModifiedTime = pojo.getLast_visit_date().replace("T"," ");

            Log.e("GetDateOfBirth :",""+ModifiedDate);
            if(ModifiedDate.contains(" ")){
                ModifiedDate= ModifiedDate.substring(0, ModifiedDate.indexOf(" "));
                Log.e("GetDateOfBirthAdapter :",""+ModifiedDate);
            }

            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = null;
            try {
                date = format1.parse(ModifiedDate);
                convertedDate = format2.format(date);
                Log.e("ConvertedDate :",""+convertedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("hh:mm");
            Date date1 = null;
            try {
                 dateFormat1.applyPattern("yyyy-mm-dd hh:mm:ss");
                 date1 = dateFormat1.parse(ModifiedTime);
                 convertedTime = dateFormat2.format(date1);
                Log.e("ConvertedTime :",""+convertedTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String Concatination = convertedDate+"  "+convertedTime;

            String FloatConversion= pojo.getPoints();
            Float convertedFloat3=Float.parseFloat(FloatConversion);
            DecimalFormat df3 = new DecimalFormat("0.00");
            df3.setMaximumFractionDigits(2);
            FloatConversion = df3.format(convertedFloat3);

            holder.Points_gain.setText(FloatConversion+" €");
            holder.Date.setText(Concatination);

            if(pojo.getStatus().equals("DEBIT"))
            {
                holder.Minus_Plus.setText("-");
            }
            else if(pojo.getStatus().equals("CREDIT"))
            {
                holder.Minus_Plus.setText("+");
            }



        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }



        return convertView;
    }

    public class ViewHolder
    {
        TextView Points_gain,Date,Minus_Plus;

    }
}
