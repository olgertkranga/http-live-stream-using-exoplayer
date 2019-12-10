package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.db.SQLiteDatabaseHandler;
import java.security.AccessControlContext;
import java.util.ArrayList;
import static android.content.ContentValues.TAG;

public class CustomeAdapter extends BaseAdapter {

    SQLiteDatabaseHandler db;

    private Activity context;
        private ArrayList<PlayersModel> playersModelArrayList;

    ViewHolder vh;

    public CustomeAdapter(Activity context, ArrayList<PlayersModel> playersModelArrayList, SQLiteDatabaseHandler db) {
        this.context = context;
        this.playersModelArrayList = playersModelArrayList;
        this.db = db;
    }

    public CustomeAdapter(AccessControlContext accessControlContext, ArrayList<PlayersModel> playersModelArrayList) {
    }

    public static class ViewHolder {

        TextView tvname;
        EditText currencyRate;
        TextView tvcountry;

        ///
        ImageView currency_flag_ImageView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            vh = new ViewHolder();

            row = inflater.inflate(R.layout.lv_item, null, true);

            vh.tvname = (TextView) row.findViewById(R.id.name);
            vh.currencyRate = (EditText) row.findViewById(R.id.currencyRate);
            vh.tvcountry = (TextView) row.findViewById(R.id.country);

            vh.currency_flag_ImageView = (ImageView) row.findViewById(R.id.currency_flag);

            // store the holder with the view.
            row.setTag(vh);

        } else {

            vh = (ViewHolder) convertView.getTag();

        }

        vh.tvname.setText(playersModelArrayList.get(position).getCurName());
        vh.currencyRate.setText(String.valueOf(playersModelArrayList.get(position).getCurRate()));
        vh.tvcountry.setText(playersModelArrayList.get(position).getCurDesc());

        ///
        ///
        if (vh.tvname.getText().toString().equals("AUD")) {
            //android:src="@drawable/chf_flag"
            //vh.currency_flag_ImageView.set
            vh.currency_flag_ImageView.setBackgroundResource(R.drawable.aud_flag);
        } else {
            vh.currency_flag_ImageView.setBackgroundResource(R.drawable.eur_flag);
        }
        ///

        final int positionPopup = position;

        vh.currencyRate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST42 = " + vh.currencyRate.getText());
                //
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST45 = " + playersModelArrayList.get(positionPopup).getCurName());
                Log.e(TAG, "REVOL_EDIT_TEXT_LIST44 = " + playersModelArrayList.get(positionPopup).getCurRate());

            if (playersModelArrayList.get(positionPopup).getCurName() == "EUR") {
                db.updateCur("UPDATE Currency SET cur_rate = " + vh.currencyRate.getText() + " WHERE cur_name = 'EUR'");
                db.updateCur("UPDATE Currency SET cur_rate = (cur_rate * " + vh.currencyRate.getText() + ") WHERE cur_name <> 'EUR'");
            } else {
                db.updateCur("UPDATE Currency SET cur_rate = (cur_rate /" + vh.currencyRate.getText() + ") WHERE cur_name = 'EUR'");
                String numStr;
                numStr = vh.currencyRate.getText().toString();
                db.updateCur("UPDATE Currency SET cur_rate = (cur_rate * ("+ numStr +"/(SELECT cur_rate FROM Currency WHERE cur_name = 'EUR'))) WHERE cur_name <> 'EUR'");
            }

            }

            @Override
            public void afterTextChanged(Editable editable) {
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