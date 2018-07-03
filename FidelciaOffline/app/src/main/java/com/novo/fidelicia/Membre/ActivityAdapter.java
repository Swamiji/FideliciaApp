package com.novo.fidelicia.Membre;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.novo.fidelicia.Créditer.Crediter;
import com.novo.fidelicia.R;
import com.novo.fidelicia.Search.SearchModel;
import com.novo.fidelicia.utils.SharedPreference;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by User on 12-01-2018.
 */

public class ActivityAdapter extends BaseAdapter {
    private Context var;
    ArrayList<ActivityModel> items=new ArrayList<ActivityModel>();
    SharedPreference sharedPreference;

    public ActivityAdapter(Context var, ArrayList<ActivityModel> items ) {
        this.var = var;
        this.items = items;
        sharedPreference = new SharedPreference();
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
        try{
            ViewHolder holder = null;
            final ActivityModel pojo=items.get(position);
            LayoutInflater inflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.activities_row,null);
                holder=new ViewHolder();
                holder.VisiteDate = (TextView)convertView.findViewById(R.id.visite_date_activity);
                holder.CreditPoints = (TextView)convertView.findViewById(R.id.credit_point_activity);

                String FloatConversion = pojo.getCredit();
                Float convertedFloat=Float.parseFloat(FloatConversion);
                DecimalFormat df = new DecimalFormat("0.00");
                df.setMaximumFractionDigits(2);
                FloatConversion = df.format(convertedFloat);

                holder.VisiteDate.setText(pojo.getDate());
                holder.CreditPoints.setText(FloatConversion+" pts");
            }
            else
            {
                holder=(ViewHolder)convertView.getTag();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return convertView;
    }

    public class ViewHolder
    {
        TextView VisiteDate,CreditPoints;


    }
}
