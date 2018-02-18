package com.example.oleg.exoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class InformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView text = (TextView) findViewById(R.id.text1);

        text.setMovementMethod(LinkMovementMethod.getInstance());

        //String linkText = "Please look my GitHub <a href='https://github.com/olgertkranga/http-live-stream-using-exoplayer'>My GitHub</a> link.";
        //textViewLink.setText(Html.fromHtml(linkText));
        //textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
