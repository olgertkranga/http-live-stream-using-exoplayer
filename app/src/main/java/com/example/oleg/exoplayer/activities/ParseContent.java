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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseContent {

    private final String KEY_BASE = "base";
    private final String KEY_DATE = "date";
    private final String KEY_RATES = "rates";

    private Activity activity;

    ArrayList<HashMap<String, String>> arraylist;

    public ParseContent(Activity activity)
    {
        this.activity = activity;
    }

    MainActivity ma = new MainActivity();

    public boolean isSuccess(String response) {

       Log.d("PARSIK_101 = ", response);

        try {

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

                Log.d("Response_Codiks : ", String.valueOf(responseCode));
                String response1 = con.getResponseMessage();

                Log.d("Response_Code1 : ", response1.toString());

                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line = null;

                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }

            }catch(IOException e){
                System.out.println("Error" + e);
            }

            response = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(response);

            Log.d("PARSIK_1710 = ", response);

            if ((jsonObject.optString(KEY_BASE).equals("EUR"))&&(jsonObject.optString(KEY_DATE).equals("2018-09-06"))) {

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "No data";
    }

   public ArrayList<PlayersModel> getInfo(String response) {
       ArrayList<PlayersModel> playersModelArrayList = new ArrayList<>();

       try {

            String url = "https://revolut.duckdns.org/latest?base=EUR";

            URL obj, obj1;
            BufferedReader reader, reader1;
            StringBuilder stringBuilder, stringBuilder1;
            HttpURLConnection con, con1;
            stringBuilder = new StringBuilder();

            try {
                obj = new URL(url);
                con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode(); // to check success and failure of API call

                Log.d("Response_Codiks : ", String.valueOf(responseCode));
                String response1 = con.getResponseMessage();
                Log.d("Response_Code1 : ", response1.toString());
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }
            }catch(IOException e){
                System.out.println("Error" + e);
            }

            String response10 = stringBuilder.toString();

            JSONObject jsonObject = new JSONObject(response10);
            JSONObject jsonObject1 = new JSONObject(response);

           Log.d("PARSIK_6 = ", response10);
           Log.d("PARSIK_6054 = ", response);

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

            PlayersModel playersModel1 = new PlayersModel();
            playersModel1.setCurName("EUR");
            playersModel1.setCurRate("1.0000");
            playersModel1.setCurDesc("Euro");
            playersModelArrayList.add(playersModel1);

           /* print substrings */
            for (int i = 0; i < tempArray.length; i++) {

                PlayersModel playersModel = new PlayersModel();

                Log.d("PARSIK_1624 = ", tempArray[i]);

                curName = tempArray[i];
                curName.replaceAll("\\s+","");

                JSONArray json_desc = new JSONObject(response).getJSONArray("currencies_descriptions");

                String curName1 = null;
                String rrate = "0.0000";

                String descStr = null;

                for (int j = 0; j < json_desc.length(); j++) {

                    JSONObject objJson = json_desc.getJSONObject(j);

                    String refStr = objJson.getString("ref");
                    descStr = objJson.getString("desc");

                    Log.d("PARSIK_REF = ", refStr);
                    Log.d("PARSIK_DESC = ", descStr);

                    Log.d("PARSIK_REF1 = ", curName.substring(2, 5));
                    Log.d("PARSIK_REF2 = ", curName.substring(1, 4));

                    if (i == 0) {

                        Log.d("PARSIK_1021 = ", "");

                        curName1 = curName.substring(2, 5);

                        playersModel.setCurName(curName.substring(2, 5));
                        playersModel.setCurRate(curName.substring(7));

                        rrate = curName.substring(6);

                    } else {

                        Log.d("PARSIK_2021 = ", "");

                        playersModel.setCurName(curName.substring(1, 4));
                        playersModel.setCurRate(curName.substring(6));

                        curName1 = curName.substring(1, 4);

                        rrate = curName.substring(6);

                    }

                    if (refStr.equals(curName1)) {
                        playersModel.setCurDesc(descStr);
                    }

                }

                playersModelArrayList.add(playersModel);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return playersModelArrayList;
    }

}