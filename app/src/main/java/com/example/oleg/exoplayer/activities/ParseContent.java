package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseContent {

    private final String KEY_BASE = "base";
    private final String KEY_DATE = "date";
    private final String KEY_RATES = "rates";
    //private final String KEY_SUCCESS = "status";
    //private final String KEY_MSG = "message";

    private Activity activity;

    ArrayList<HashMap<String, String>> arraylist;

    public ParseContent(Activity activity) {
        this.activity = activity;
    }

   public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            Log.d("PARSIK_1 = ", response);

            if ((jsonObject.optString(KEY_BASE).equals("EUR"))&&(jsonObject.optString(KEY_DATE).equals("2018-09-06"))) {
            //if (jsonObject.optString(KEY_SUCCESS).equals("true")) {

                Log.d("PARSIK_2 = ", "2");

                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

   public String getErrorCode(String response) {

        try {

            Log.d("PARSIK_3 = ", "3");

            JSONObject jsonObject = new JSONObject(response);
            return jsonObject.getString(KEY_RATES);
            //return jsonObject.getString(KEY_MSG);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

   public ArrayList<PlayersModel> getInfo(String response) {
       ArrayList<PlayersModel> playersModelArrayList = new ArrayList<>();
        try {

            JSONObject jsonObject = new JSONObject(response);

            Log.d("PARSIK_6 = ", response);

            JSONObject r_rates = jsonObject.getJSONObject("rates");

            Log.d("PARSIK_15 = ", String.valueOf(r_rates));

            String r_ratesStr;
            r_ratesStr = String.valueOf(r_rates);
            r_ratesStr = r_ratesStr.replace('{',' ');
            r_ratesStr = r_ratesStr.replace('}',' ');
            String[] tempArray;
            String delimiter = ",";
            tempArray = r_ratesStr.split(delimiter);

            /* print substrings */
            for (int i = 0; i < tempArray.length; i++) {
                Log.d("PARSIK_16 = ", tempArray[i]);
            }


            //for (int i = 0; i < jsonObjectRates.length(); i++) {

            //    Log.d("WWWWW = ", jsonObjectRates(i));

            //}


            /*
            if ((jsonObject.optString(KEY_BASE).equals("EUR"))) {
            //if ((jsonObject.optString(KEY_BASE).equals("EUR"))&&(jsonObject.optString(KEY_DATE).equals("2018-09-06"))) {
            //if (jsonObject.getString(KEY_SUCCESS).equals("true")) {

                Log.d("PARSIK_61 = ", KEY_BASE);

                arraylist = new ArrayList<HashMap<String, String>>();

                JSONArray dataArray = jsonObject.getJSONArray("rates");

                Log.d("PARSIK_4 = ", dataArray.toString());

                for (int i = 0; i < dataArray.length(); i++) {

                    PlayersModel playersModel = new PlayersModel();

                    JSONObject dataobj = dataArray.getJSONObject(i);

                    playersModel.setCurName(dataobj.getString(AndyConstants.Params.curName));
                    playersModel.setCurRate(dataobj.getString(AndyConstants.Params.curRate));

                    //playersModel.setName(dataobj.getString(AndyConstants.Params.NAME));
                    //playersModel.setCountry(dataobj.getString(AndyConstants.Params.COUNTRY));
                    //playersModel.setCity(dataobj.getString(AndyConstants.Params.CITY));

                    playersModelArrayList.add(playersModel);

                    Log.d("PARSIK_5 = ", playersModelArrayList.toString());*/

                //}
            //}

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playersModelArrayList;
    }

}