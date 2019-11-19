package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oleg.exoplayer.R;
import com.example.oleg.exoplayer.db.SQLiteDatabaseHandler;
import com.example.oleg.exoplayer.models.Currency;

import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ParseContent parseContent;
    private final int jsoncode = 1;
    private ListView listView;

    private ArrayList<PlayersModel> playersModelArrayList;
    //private ArrayList<Currency> currency;

    private CustomeAdapter customeAdapter;

    private String TAG = MainActivity.class.getCanonicalName();

    EditText currencyRate;
    EditText currencyRate1;

    ///
    SQLiteDatabaseHandler db;
    Activity activity;
    //ArrayList<Currency> currency;
    int regulator;
    ArrayList<PlayersModel> currency;

    TextView tvname;
    TextView tvcountry;

    LinearLayout linearLayoutRowItemPayments;

    Button pullRatesButton;
    Button removeAllButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///
        activity = this;
        Intent i = getIntent();
        db = new SQLiteDatabaseHandler(this);

        this.setTitle("Revolut 7");

        ///
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        parseContent = new ParseContent(this);

        listView = (ListView) findViewById(R.id.lv);

        currencyRate = (EditText) findViewById(R.id.currencyRate);

        ///
        tvname = (TextView) findViewById(R.id.name);
        tvcountry = (TextView) findViewById(R.id.country);

        ///
        linearLayoutRowItemPayments = (LinearLayout) findViewById(R.id.linearLayoutRowItemPayments);

        pullRatesButton = (Button) findViewById(R.id.pullRatesButton);;
        removeAllButton = (Button) findViewById(R.id.removeAllButton);;

        pullRatesButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
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
                db.updateCur("DELETE FROM Currency");

                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });

        ///
       //db.updateCur("DELETE FROM Currency");

        //try {
        //    currencyRate.setInputType(InputType.TYPE_CLASS_NUMBER);
        //} catch (NullPointerException e) {
//
  //      }

        Log.e(TAG, "REVOL_1");
        Log.e(TAG, "REVOL_2");

        ///

        currency = (ArrayList) db.getCurrencyList("");

        //playersModelArrayList = parseContent.getInfo(response);
        //customeAdapter = new CustomeAdapter(this,playersModelArrayList);
        //listView.setAdapter(customeAdapter);

        if (currency.isEmpty()) {
            regulator = 1;
            Log.e(TAG, "REVOL_EMPTY: " + currency);
        } else {
            regulator = 0;
            Log.e(TAG, "REVOL_NOT_EMPTY: " + currency.toString());
        }

        ///

        if (regulator == 0) {

            Log.e(TAG, "REVOL_NOT_EMPTY1: " + currency.toString());

            //customeAdapter = new CustomeAdapter(this, currency, db) {
            //    @Override
            //    public View getView(int position, View convertView, ViewGroup parent) {
            //        // Get the current item from ListView
            //       View view = super.getView(position, convertView, parent);
            //       return view;
            //    }
            //};

            customeAdapter = new CustomeAdapter(this, currency, db);
            listView.setAdapter(customeAdapter);

            ///
            currencyRate1 = (EditText) findViewById(R.id.currencyRate1);
            currencyRate1.addTextChangedListener(new EditTextWatcher());
            ///
            //disableTextWatcher = false;

            //currencyRate.setText("1");
            Log.e(TAG, "REVOL_CUR_RATE10: ");
            //try {
            //    currencyRate.addTextChangedListener(new EditTextWatcher1());
            //} catch (NullPointerException e) {}

            }

        if (regulator == 1) {

            try {

                parseJson();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        ///

        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {

                //currencyRate.setText("1");
                Log.e(TAG, "REVOL_CUR_RATE11: ");
                try {
                    currencyRate.addTextChangedListener(new EditTextWatcher1());
                } catch (NullPointerException e) {}

                handler.postDelayed( this, 1000 );
            }
        }, 1000 );

        ///

  /*
        if (regulator == 1) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {

                        parseJson();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);

            Log.e(TAG, "REVOL_3");

        }
*/


  //////
        /*
        final Handler handler = new Handler();
        handler.postDelayed( new Runnable() {

            @Override
            public void run() {

                try {
                    for (int i = 0; i < currency.size(); i++) {

                        if (tvname.getText() == "EUR") {
                            if (currency.get(i).getCurName() == "EUR") {
                                db.updateCur("UPDATE Currency SET cur_rate = " + currencyRate.getText() + " WHERE cur_name = 'EUR'");
                                db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + currencyRate.getText() + " WHERE cur_name <> 'EUR'");
                            }
                        }

                        //Log.e(TAG, "REVOL_CUR: " + currency.get(i).getCurRate());
                    }

                } catch (NullPointerException e) {}
                handler.postDelayed( this, 1000 );
            }
        }, 1000 );
        */

        ///

        ///

    }

    ///
    private class EditTextWatcher1 implements TextWatcher {
        public void afterTextChanged(Editable s) {
            // No implementation
            if (tvname.getText() == "EUR") {
                db.updateCur("UPDATE Currency SET cur_rate = " + currencyRate.getText() + " WHERE cur_name = 'EUR'");
                db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + currencyRate.getText() + " WHERE cur_name <> 'EUR'");
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // No implementation
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //db.updateCur("UPDATE Currency SET cur_rate = " + currencyRate.getText() + " WHERE cur_name = 'EUR'");
            //db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + currencyRate.getText() + " WHERE cur_name <> 'EUR'");
            //Intent intent = getIntent();
            //finish();
            //startActivity(intent);
        }
    }
    ///

    ///
    private class EditTextWatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {
            // No implementation
            db.updateCur("UPDATE Currency SET cur_rate = " + currencyRate1.getText() + " WHERE cur_name = 'EUR'");
            db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + currencyRate1.getText() + " WHERE cur_name <> 'EUR'");
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // No implementation
        }
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //db.updateCur("UPDATE Currency SET cur_rate = " + currencyRate1.getText() + " WHERE cur_name = 'EUR'");
            //db.updateCur("UPDATE Currency SET cur_rate = cur_rate * " + currencyRate1.getText() + " WHERE cur_name <> 'EUR'");
            //Intent intent = getIntent();
            //finish();
            //startActivity(intent);
        }
    }
    ///

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

        Log.d("responsejson", response.toString());

        switch (serviceCode) {
            case jsoncode:

                if (parseContent.isSuccess(response)) {

                    AndyUtils.removeSimpleProgressDialog();  //will remove progress dialog

                    ///currency
                    //parseContent.getInfo(response);

                    ///
                    //Currency currency = new Currency(
                    //        "2019-11-18", //2
                    //        "EUR", //3
                    //        1.0001, //4
                    //        "Euro",
                    //        ""
                    //);

                    //Log.d("PARSIK_1666 = ", currency.getCurName());
                    ///db = new SQLiteDatabaseHandler(this);
                    ///

                    //db.addNewCurrency(currency);

                    //Intent intent = getIntent();
                    //finish();
                    //startActivity(intent);

                    ///
                    //playersModelArrayList = parseContent.getInfo(response);

                    //customeAdapter = new CustomeAdapter(this,playersModelArrayList);
                    //customeAdapter = new CustomeAdapter(this, playersModelArrayList);
                    //listView.setAdapter(customeAdapter);

                    playersModelArrayList = parseContent.getInfo(response);

                    for (int i = 0; i < playersModelArrayList.size(); i++) {

                        //PlayersModel playersModel = new PlayersModel();

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