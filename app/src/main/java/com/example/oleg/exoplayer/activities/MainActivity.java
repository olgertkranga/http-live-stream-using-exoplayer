package com.example.oleg.exoplayer.activities;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oleg.exoplayer.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        try {
            currencyRate.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        catch (NullPointerException e) {

        }

        ///
        try {
            setKeyListenerForEnter();
        }
        catch (NullPointerException e) {

        }

        Log.e(TAG, "REVOL_1");

        Log.e(TAG, "REVOL_2");

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
        },1000);

        Log.e(TAG, "REVOL_3");

    }

    ///
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {

            case KeyEvent.FLAG_SOFT_KEYBOARD:
                Toast.makeText(this, "5 1", Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "REVOL_X");
                return true;

            case KeyEvent.KEYCODE_3:
                Toast.makeText(this, "ENTER", Toast.LENGTH_SHORT)
                        .show();
                Log.e(TAG, "REVOL_ENTER");
                return true;



            case KeyEvent.KEYCODE_MENU:
                Toast.makeText(this, "Нажата кнопка Меню", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case KeyEvent.KEYCODE_SEARCH:
                Toast.makeText(this, "Нажата кнопка Поиск", Toast.LENGTH_SHORT)
                        .show();
                return true;
            case KeyEvent.KEYCODE_BACK:
                onBackPressed();
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                event.startTracking();
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Toast.makeText(this, "Нажата кнопка громкости", Toast.LENGTH_SHORT)
                        .show();
                return false;

        }
        return super.onKeyDown(keyCode, event);
    }
    */

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

                    playersModelArrayList = parseContent.getInfo(response);

                    customeAdapter = new CustomeAdapter(this,playersModelArrayList);
                    listView.setAdapter(customeAdapter);

                }else {
                    Toast.makeText(MainActivity.this, parseContent.getErrorCode(response), Toast.LENGTH_SHORT).show();
                }
        }
    }

    ///

    public void setKeyListenerForEnter(){

        final EditText search_entry = (EditText) findViewById(R.id.currencyRate);
        search_entry.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    Log.e(TAG, "REVOL_Q");

                    //getSearchResults(v);

                    setKeyListenerForEnter();
                    return true;
                }
                return false;
            }
        });
    }

    ///

}