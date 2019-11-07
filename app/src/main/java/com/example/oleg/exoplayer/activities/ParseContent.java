package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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

       Log.d("PARSIK_101 = ", response);

        try {

            ///
            String url = "https://revolut.duckdns.org/latest?base=EUR";

            URL obj;
            BufferedReader reader;
            StringBuilder stringBuilder;
            HttpURLConnection con;
            stringBuilder = new StringBuilder();

            try {


                    obj = new URL(url);

                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode(); // to check success and failure of API call

                //Log.d("PARSIK_2 = ", "2");
                Log.d("Response_Codiks : ", String.valueOf(responseCode));
                String response1 = con.getResponseMessage();

                Log.d("Response_Code1 : ", response1.toString());

                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                String line = null;

                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");

                    //System.out.println("String : " + stringBuilder.toString());
                }
                //return stringBuilder.toString();

            }catch(IOException e){
                System.out.println("Error" + e);
            }


            //response = response.replace("\\\"","'");
            //JSONObject jsonObject = new JSONObject(response.substring(1,response.length()-1));

            response = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(response);
            //json = json.replace("\\\"","'"); JSONObject jo = new JSONObject(json.substring(1,json.length()-1));

            Log.d("PARSIK_1710 = ", response);



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

            //return new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));

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

            ///
            ///
            String url = "https://revolut.duckdns.org/latest?base=EUR";

            URL obj;
            BufferedReader reader;
            StringBuilder stringBuilder;
            HttpURLConnection con;
            stringBuilder = new StringBuilder();

            try {


                obj = new URL(url);

                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode(); // to check success and failure of API call

                //Log.d("PARSIK_2 = ", "2");
                Log.d("Response_Codiks : ", String.valueOf(responseCode));
                String response1 = con.getResponseMessage();

                Log.d("Response_Code1 : ", response1.toString());

                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));


                String line = null;

                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");

                    //System.out.println("String : " + stringBuilder.toString());
                }
                //return stringBuilder.toString();

            }catch(IOException e){
                System.out.println("Error" + e);
            }


            //response = response.replace("\\\"","'");
            //JSONObject jsonObject = new JSONObject(response.substring(1,response.length()-1));

            response = stringBuilder.toString();


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

            String curName;

            /* print substrings */
            for (int i = 0; i < tempArray.length; i++) {

                PlayersModel playersModel = new PlayersModel();

                Log.d("PARSIK_16 = ", tempArray[i]);

                curName = tempArray[i];
                curName.replaceAll("\\s+","");

                if (i==0) {

                    playersModel.setCurName(curName.substring(2,5));
                    playersModel.setCurRate(curName.substring(7));

                } else {

                    playersModel.setCurName(curName.substring(1, 4));
                    playersModel.setCurRate(curName.substring(6));

                }

                playersModelArrayList.add(playersModel);

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