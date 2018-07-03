package com.novo.fidelicia.Créditer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novo.fidelicia.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by User on 12-01-2018.
 */

public class CrediterAdapter extends BaseAdapter {
    private Context var;
    ArrayList<CrediterModel> items=new ArrayList<CrediterModel>();

    public CrediterAdapter(Context var, ArrayList<CrediterModel> items ) {
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
        final CrediterModel pojo=items.get(position);
        LayoutInflater inflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.last_visit_layout,null);
            holder=new ViewHolder();

            holder.Points_gain = (TextView)convertView.findViewById(R.id.no_of_points);
            holder.Date = (TextView)convertView.findViewById(R.id.last_visit_date);

            String FloatConversion = pojo.getPoints();
            Float convertedFloat=Float.parseFloat(FloatConversion);
            DecimalFormat df = new DecimalFormat("0.00");
            df.setMaximumFractionDigits(2);
            FloatConversion = df.format(convertedFloat);
            holder.Points_gain.setText(FloatConversion+" pts");
            holder.Date.setText(pojo.getLast_visit_date());

        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }



        return convertView;
    }

    public class ViewHolder
    {
        TextView Points_gain,Date;

    }
}
