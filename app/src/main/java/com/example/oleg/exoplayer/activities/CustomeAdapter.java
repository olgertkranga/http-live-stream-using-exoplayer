package com.example.oleg.exoplayer.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.db.SQLiteDatabaseHandler;
import com.example.oleg.exoplayer.models.Currency;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
public class CustomeAdapter extends BaseAdapter {

    //private Activity context;
    public ArrayList<Currency> currency;
    SQLiteDatabaseHandler db;

    private Context context;

    private ArrayList<PlayersModel> playersModelArrayList;

    public CustomeAdapter(Context context, ArrayList<PlayersModel> playersModelArrayList, SQLiteDatabaseHandler db) {
        this.context = context;
        this.playersModelArrayList = playersModelArrayList;
        this.db = db;
    }

    public CustomeAdapter(Context context, ArrayList<PlayersModel> playersModelArrayList) {
        this.context = context;
        this.playersModelArrayList = playersModelArrayList;
    }

    @Override
    public int getViewTypeCount()
    {

        if(getCount() > 0){
            return getCount();
        }else{
            return super.getViewTypeCount();
        }

        //return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount()
    {
        //try {
            return playersModelArrayList.size();
        //}
        //catch (NullPointerException e) {
        //    return 0;
        //}
    }

    @Override
    public Object getItem(int position) {
        return playersModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.currencyRate = (EditText) convertView.findViewById(R.id.currencyRate);
            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);

            //holder.tvcity = (TextView) convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(playersModelArrayList.get(position).getCurName());
        holder.currencyRate.setText(String.valueOf(playersModelArrayList.get(position).getCurRate()));
        holder.tvcountry.setText(playersModelArrayList.get(position).getCurDesc());

        //holder.currencyRate.setText(currency.get(position).getCurRate());
        //holder.tvname.setText(playersModelArrayList.get(position).getCurName() + "                  " + playersModelArrayList.get(position).getCurRate());
        //holder.tvcountry.setText(playersModelArrayList.get(position).getCurDesc());

        /*
        holder.tvname.setText("Name: "+playersModelArrayList.get(position).getName());
        holder.tvcountry.setText("Country: "+playersModelArrayList.get(position).getCountry());
        holder.tvcity.setText("City: "+playersModelArrayList.get(position).getCity());
        */

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, currencyRate, tvcountry;
        //protected TextView tvname, tvcountry, tvcity;
    }

}