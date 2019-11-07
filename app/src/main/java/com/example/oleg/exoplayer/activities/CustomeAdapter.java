package com.example.oleg.exoplayer.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.oleg.exoplayer.R;

import java.util.ArrayList;

/**
 * Created by Parsania Hardik on 03-Jan-17.
 */
public class CustomeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PlayersModel> playersModelArrayList;

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
        return playersModelArrayList.size();
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
            holder.tvcountry = (TextView) convertView.findViewById(R.id.country);
            //holder.tvcity = (TextView) convertView.findViewById(R.id.city);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(playersModelArrayList.get(position).getCurName() + "                  " + playersModelArrayList.get(position).getCurRate());

        holder.tvcountry.setText("");
        /*
        holder.tvname.setText("Name: "+playersModelArrayList.get(position).getName());
        holder.tvcountry.setText("Country: "+playersModelArrayList.get(position).getCountry());
        holder.tvcity.setText("City: "+playersModelArrayList.get(position).getCity());
        */

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname, tvcountry;
        //protected TextView tvname, tvcountry, tvcity;
    }

}