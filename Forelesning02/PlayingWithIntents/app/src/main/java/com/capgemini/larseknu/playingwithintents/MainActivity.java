package com.capgemini.larseknu.playingwithintents;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   }

    public void startOtherActivity(View view)
    {
        // Call OtherActivity with an explicit intent
        Intent intent = new Intent(this, OtherActivity.class);
        startActivity(intent);
    }

    public void implicitlyStartOtherActivity(View view)
    {
        // Call OtherActivity with an implicit intent
        Intent intent = new Intent("com.capgemini.larseknu.action.OTHER_ACTIVITY");
        startActivity(intent);
    }

    public void getTime(View view)
    {
        // Get date with an implicit intent
        Intent intent = new Intent("com.capgemini.larseknu.action.SHOW_TIME");
        // Sets a flag that the opened activity should be deleted from the history stack when the user navigates away from it
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void getDate(View view)
    {
        // Get time with an implicit intent
        Intent intent = new Intent("com.capgemini.larseknu.action.SHOW_DATE");
        startActivity(intent);
    }

    public void openWebsite(View view)
    {
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse("http://www.capgemini.no");
        intent.setData(uri);
        startActivity(intent);
    }

    public void runServiceJob(View view)
    {
        counter++;
        MyIntentService.startActionProduce(this, "Android clones", counter);
    }
}
