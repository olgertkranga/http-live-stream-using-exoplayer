package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.db.SQLiteDatabaseHandler;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ParseContent parseContent;
    private final int jsoncode = 1;
    private ListView listView;

    private ArrayList<PlayersModel> playersModelArrayList;

    private CustomeAdapter customeAdapter;

    private String TAG = MainActivity.class.getCanonicalName();

    EditText currencyRate;

    SQLiteDatabaseHandler db;
    Activity activity;

    int regulator;
    ArrayList<PlayersModel> currency;

    TextView tvname;
    TextView tvcountry;

    RelativeLayout linearRelativeRowItemPayments;

    Button pullRatesButton;
    Button removeAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        Intent i = getIntent();
        db = new SQLiteDatabaseHandler(this);

        this.setTitle("Revolut 1");

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        parseContent = new ParseContent(this);

        listView = (ListView) findViewById(R.id.lv);

        tvname = (TextView) findViewById(R.id.name);
        tvcountry = (TextView) findViewById(R.id.country);

        linearRelativeRowItemPayments = (RelativeLayout) findViewById(R.id.linearRelativeRowItemPayments);

        LayoutInflater factory = getLayoutInflater();
        View regisText = factory.inflate(R.layout.lv_item, null);
        currencyRate = (EditText) regisText.findViewById(R.id.currencyRate);

        pullRatesButton = (Button) findViewById(R.id.pullRatesButton);;
        removeAllButton = (Button) findViewById(R.id.removeAllButton);;

        pullRatesButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Toast.makeText(getApplicationContext(), "PULL CURRENCIES", Toast.LENGTH_LONG).show();

                    parseJson();

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        removeAllButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "DELETE CURRENCIES", Toast.LENGTH_LONG).show();

                db.updateCur("DELETE FROM Currency");

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

        Log.e(TAG, "REVOL_1");
        Log.e(TAG, "REVOL_2");

        currency = (ArrayList) db.getCurrencyList("");

        if (currency.isEmpty()) {
            regulator = 1;
            Log.e(TAG, "REVOL_EMPTY: " + currency);
        } else {
            regulator = 0;
            Log.e(TAG, "REVOL_NOT_EMPTY: " + currency.toString());
        }

        if (regulator == 0) {

            Log.e(TAG, "REVOL_NOT_EMPTY1: " + currency.toString());

            customeAdapter = new CustomeAdapter(this, currency, db) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent){
                    // Get the current item from ListView
                    View view = super.getView(position,convertView,parent);

                    return view;
                }
            };
            listView.setAdapter(customeAdapter);

            Log.e(TAG, "REVOL_CUR_RATE10: ");

            }

        if (regulator == 1) {

            try {

                parseJson();

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void parseJson() throws IOException, JSONException {

        Log.e(TAG, "REVOL_6");

        if (!AndyUtils.isNetworkAvailable(MainActivity.this)) {
            Toast.makeText(MainActivity.this, "Internet is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.e(TAG, "REVOL_7");

        AndyUtils.showSimpleProgressDialog(MainActivity.this);

        new AsyncTask<Void, Void, String>(){

            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {

                    Log.e(TAG, "REVOL_8");

                    HttpRequest req = new HttpRequest(AndyConstants.ServiceType.URL);

                    Log.e(TAG, "REVOL_9");

                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                    //response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();

                    Log.e(TAG, "REVOL_10");

                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }

            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }

    public void onTaskCompleted(String response, int serviceCode) {

        Log.d("responsejson3", response.toString());

        switch (serviceCode) {
            case jsoncode:

                if (parseContent.isSuccess(response)) {

                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog

                    playersModelArrayList = parseContent.getInfo(response);

                    for (int i = 0; i < playersModelArrayList.size(); i++) {

                        Log.d("PARSIK_1624 = ", playersModelArrayList.get(0).getCurName());
                        Log.d("PARSIK_1668 = ", playersModelArrayList.get(1).getCurDesc());

                        PlayersModel currency = new PlayersModel(
                                "2019-11-18", //2
                                playersModelArrayList.get(i).getCurName(), //3
                                playersModelArrayList.get(i).getCurRate(), //4
                                playersModelArrayList.get(i).getCurDesc(),
                                ""
                        );

                        db.addNewCurrency(currency);

                    }


                }else {
                    Toast.makeText(MainActivity.this, parseContent.getErrorCode(response), Toast.LENGTH_SHORT).show();
                }

        }
    }

}