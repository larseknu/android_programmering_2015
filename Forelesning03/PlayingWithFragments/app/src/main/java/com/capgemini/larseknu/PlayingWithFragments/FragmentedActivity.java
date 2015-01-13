package com.capgemini.larseknu.PlayingWithFragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FragmentedActivity extends Activity implements MovieListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmented);
    }

    @Override
    public void onFragmentInteraction(int id) {
        //Toast.makeText(this, "Item number: " + id, Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getFragmentManager();
        MovieDetailFragment movieDetailFragment = (MovieDetailFragment) fragmentManager.findFragmentById(R.id.detail);

        movieDetailFragment.setDisplayedDetail(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_seperate:
                startSeparateActivity();
                return true;
            case R.id.action_button_handling:
                startButtonHandlingActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startSeparateActivity() {
        Intent intent = new Intent(this, SeperateActivity1.class);
        startActivity(intent);
    }

    private void startButtonHandlingActivity() {
        Intent intent = new Intent(this, ButtonHandlingActivity.class);
        startActivity(intent);
    }
}
