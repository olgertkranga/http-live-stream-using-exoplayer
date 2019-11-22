package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.db.SQLiteDatabaseHandler;
import com.example.oleg.exoplayer.models.Currency;

import java.security.AccessControlContext;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class CustomeAdapter extends BaseAdapter {

    //private Activity context;
    public ArrayList<Currency> currency;
    SQLiteDatabaseHandler db;

    private Activity context;
    //private Context context;

    private ArrayList<PlayersModel> playersModelArrayList;

    ///
    ViewHolder vh;

    public CustomeAdapter(Activity context, ArrayList<PlayersModel> playersModelArrayList, SQLiteDatabaseHandler db) {
        this.context = context;
        this.playersModelArrayList = playersModelArrayList;
        this.db = db;
    }

    public CustomeAdapter(AccessControlContext accessControlContext, ArrayList<PlayersModel> playersModelArrayList) {
    }

    //public CustomeAdapter(Context context, ArrayList<PlayersModel> playersModelArrayList) {
    //    this.context = context;
    //    this.playersModelArrayList = playersModelArrayList;
    //}

    public static class ViewHolder {

        TextView tvname;
        EditText currencyRate;
        TextView tvcountry;

        //protected TextView tvname, currencyRate, tvcountry;
        //protected TextView tvname, tvcountry, tvcity;
    }

    /*
    @Override
    public int getViewTypeCount()
    {

        if(getCount() > 0){
            return getCount();
        }else{
            return super.getViewTypeCount();
        }

        //return getCount();
    } */

/*
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
*/



/*
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
/*
        return convertView;
    }
    */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = context.getLayoutInflater();

        ///
        ///ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();

            row = inflater.inflate(R.layout.lv_item, null, true);

            vh.tvname = (TextView) row.findViewById(R.id.name);
            vh.currencyRate = (EditText) row.findViewById(R.id.currencyRate);
            vh.tvcountry = (TextView) row.findViewById(R.id.country);

            // store the holder with the view.
            row.setTag(vh);

        } else {

            vh = (ViewHolder) convertView.getTag();

        }

        vh.tvname.setText(playersModelArrayList.get(position).getCurName());
        vh.currencyRate.setText(String.valueOf(playersModelArrayList.get(position).getCurRate()));
        vh.tvcountry.setText(playersModelArrayList.get(position).getCurDesc());
        //holder.tvname.setText(playersModelArrayList.get(position).getCurName());
        //holder.currencyRate.setText(String.valueOf(playersModelArrayList.get(position).getCurRate()));
        //holder.tvcountry.setText(playersModelArrayList.get(position).getCurDesc());

        final int positionPopup = position;

        ///
        vh.currencyRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST41 = ");
                //db.updateCur("UPDATE Currency SET cur_rate = " + vh.currencyRate.getText() + " WHERE cur_name = 'EUR'");
                //db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + vh.currencyRate.getText() + " WHERE cur_name <> 'EUR'");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST42 = ");
                //db.updateCur("UPDATE Currency SET cur_rate = " + vh.currencyRate.getText() + " WHERE cur_name = 'EUR'");
                //db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + vh.currencyRate.getText() + " WHERE cur_name <> 'EUR'");
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //db.updateCur("UPDATE Currency SET cur_rate = " + vh.currencyRate.getText() + " WHERE cur_name = 'EUR'");
                //db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + vh.currencyRate.getText() + " WHERE cur_name <> 'EUR'");
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST43 = ");
            }
        });

        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {
        return playersModelArrayList.size();
    }

}