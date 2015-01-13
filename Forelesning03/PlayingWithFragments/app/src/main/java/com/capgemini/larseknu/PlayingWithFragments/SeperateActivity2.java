package com.capgemini.larseknu.PlayingWithFragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class SeperateActivity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seperate2);

        Intent startupIntent = getIntent();

        int movieDetailIndex = startupIntent.getIntExtra("movieDetailIndex", 0);

        FragmentManager fragmentManager = getFragmentManager();
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.detail);
        movieDetailFragment.setDisplayedDetail(movieDetailIndex);
    }
}
