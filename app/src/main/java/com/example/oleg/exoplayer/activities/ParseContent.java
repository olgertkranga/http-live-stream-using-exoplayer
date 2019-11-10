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

            //String url_cur_description = "https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php";
           //String url_cur_description = "http://jogavisiem.lv/test2/index.php";

            URL obj, obj1;
            BufferedReader reader, reader1;
            StringBuilder stringBuilder, stringBuilder1;
            HttpURLConnection con, con1;
            stringBuilder = new StringBuilder();
            stringBuilder1 = new StringBuilder();

            try {
                obj = new URL(url);
                //obj1 = new URL(url_cur_description);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode(); // to check success and failure of API call
                //con1 = (HttpURLConnection) obj1.openConnection();
                //con1.setRequestMethod("GET");
                //int responseCode1 = con1.getResponseCode();
                //Log.d("PARSIK_2 = ", "2");
                Log.d("Response_Codiks : ", String.valueOf(responseCode));
                String response1 = con.getResponseMessage();
                //String response2 = con1.getResponseMessage();
                Log.d("Response_Code1 : ", response1.toString());
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                //reader1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));
                String line = null;
                //String line1 = null;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                    //System.out.println("String : " + stringBuilder.toString());
                }
                //while ((line1 = reader1.readLine()) != null){
                //    stringBuilder1.append(line1 + "\n");
                //}
                //return stringBuilder.toString();
            }catch(IOException e){
                System.out.println("Error" + e);
            }

            /*
            try {
                obj1 = new URL(url_cur_description);
                con1 = (HttpURLConnection) obj1.openConnection();
                con1.setRequestMethod("GET");
                int responseCode1 = con1.getResponseCode();
                String response2 = con1.getResponseMessage();
                reader1 = new BufferedReader(new InputStreamReader(con1.getInputStream()));

                Log.d("PARSIK_610 = ", reader1.toString());

                String line1 = null;
                while ((line1 = reader1.readLine()) != null) {
                    stringBuilder1.append(line1 + "\n");
                }
                Log.d("PARSIK_609 = ", stringBuilder1.toString());
            }catch(IOException e){
               System.out.println("Error" + e);
            }
            */

           //response = response.replace("\\\"","'");
            //JSONObject jsonObject = new JSONObject(response.substring(1,response.length()-1));

           String response10 = stringBuilder.toString();
           //response = stringBuilder.toString();
            //String response3 = stringBuilder1.toString();

            JSONObject jsonObject = new JSONObject(response10);
            JSONObject jsonObject1 = new JSONObject(response);

           Log.d("PARSIK_6 = ", response10);
           Log.d("PARSIK_6054 = ", response);

           JSONObject r_rates = jsonObject.getJSONObject("rates");

           //JSONArray json_desc = new JSONObject(response).getJSONArray("currencies_descriptions");
           //JSONObject json_desc = jsonObject1.getJSONObject("currencies_descriptions");

            Log.d("PARSIK_15 = ", String.valueOf(r_rates));
            //Log.d("PARSIK_150 = ", String.valueOf(json_desc));

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

                Log.d("PARSIK_1624 = ", tempArray[i]);

                curName = tempArray[i];
                curName.replaceAll("\\s+","");

                JSONArray json_desc = new JSONObject(response).getJSONArray("currencies_descriptions");

                for (int j = 0; j < json_desc.length(); j++) {

                    if (i == 0) {

                        Log.d("PARSIK_1021 = ", "");

                        playersModel.setCurName(curName.substring(2, 5));
                        playersModel.setCurRate(curName.substring(7));

                    } else {

                        Log.d("PARSIK_2021 = ", "");

                        playersModel.setCurName(curName.substring(1, 4));
                        playersModel.setCurRate(curName.substring(6));

                    }

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