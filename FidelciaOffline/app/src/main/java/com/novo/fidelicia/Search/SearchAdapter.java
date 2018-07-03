package com.novo.fidelicia.Search;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.novo.fidelicia.Créditer.CrediterModel;
import com.novo.fidelicia.Membre.Membre;
import com.novo.fidelicia.R;
import com.novo.fidelicia.utils.SharedPreference;

import java.util.ArrayList;

/**
 * Created by User on 12-01-2018.
 */

public class SearchAdapter extends BaseAdapter {
    private Context var;
    ArrayList<SearchModel> items=new ArrayList<SearchModel>();
    SharedPreference sharedPreference;

    public SearchAdapter(Context var, ArrayList<SearchModel> items ) {
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
            final SearchModel pojo=items.get(position);
            LayoutInflater inflater = (LayoutInflater)var.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
            {
                convertView=inflater.inflate(R.layout.result_item_row_layout,null);
                holder=new ViewHolder();

                holder.Prenom = (TextView)convertView.findViewById(R.id.result_memberName);
                holder.Nom = (TextView)convertView.findViewById(R.id.result_memberLastName);
                holder.Telephone = (TextView)convertView.findViewById(R.id.result_member_PhoneNumber);
                holder.Voir = (Button)convertView.findViewById(R.id.button_voir);
                holder.Credit = (Button)convertView.findViewById(R.id.button_crediter);

                holder.Prenom.setText(pojo.getPrenom());
                holder.Nom.setText(pojo.getNom());
                holder.Telephone.setText(pojo.getTelephone());

                holder.Voir.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(View v) {

                        try{
                            sharedPreference.saveCardNumber(var,items.get(position).getCardNumber());
                            sharedPreference.saveMiBarcodeNumber(var,items.get(position).getMiBarcode());

                            Intent intent = new Intent(var, Membre.class);
                            Log.e("GetCardNumberFromIntent :",""+items.get(position).getCardNumber());
                            Log.e("GetMiBarCodeFromIntent :",""+items.get(position).getMiBarcode());
                            intent.putExtra("CardNumber",items.get(position).getCardNumber());
                            intent.putExtra("mi_barcode",items.get(position).getMiBarcode());
                            var.startActivity(intent);
                            ((Activity)var).finish();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                });

                holder.Credit.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onClick(View v) {

                        try{
                            Intent intent = new Intent(var, Crediter.class);
                            Log.e("GetCardNumberFromIntent :",""+items.get(position).getCardNumber());
                            Log.e("GetMiBarCodeFromIntent :",""+items.get(position).getMiBarcode());
                            intent.putExtra("CardNumber",items.get(position).getCardNumber());
                            intent.putExtra("mi_barcode",items.get(position).getMiBarcode());
                            var.startActivity(intent);
                            ((Activity)var).finish();


                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                });


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
        TextView Prenom,Nom,Telephone;
        Button Voir,Credit;

    }
}
