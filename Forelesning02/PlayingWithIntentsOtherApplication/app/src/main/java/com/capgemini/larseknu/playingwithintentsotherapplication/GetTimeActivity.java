package com.capgemini.larseknu.playingwithintentsotherapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class GetTimeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_time);

        TextView textView = (TextView) findViewById(R.id.textViewTime);

        Intent intent = getIntent();
        String action = intent.getAction();

        SimpleDateFormat dateFormat = null;

        if (action.equals("com.capgemini.larseknu.action.SHOW_TIME")) {
            dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);
        } else if (action.equals("com.capgemini.larseknu.action.SHOW_DATE"))
        {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        } else {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
        }

        if (dateFormat != null) {
            long now = (new Date()).getTime();
            textView.setText(dateFormat.format(now));
        }
    }
}
