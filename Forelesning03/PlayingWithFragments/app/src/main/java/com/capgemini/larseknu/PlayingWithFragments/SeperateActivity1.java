package com.capgemini.larseknu.PlayingWithFragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;


public class SeperateActivity1 extends Activity implements MovieListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seperate1);
    }

    @Override
    public void onFragmentInteraction(int id) {
        FragmentManager fragmentManager = getFragmentManager();
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.detail);

        if(movieDetailFragment != null && movieDetailFragment.isVisible()) {
            movieDetailFragment.setDisplayedDetail(id);
        }
        else {
            Intent intent = new Intent(this, SeperateActivity2.class);
            intent.putExtra("movieDetailIndex", id);
            startActivity(intent);
        }
    }
}
