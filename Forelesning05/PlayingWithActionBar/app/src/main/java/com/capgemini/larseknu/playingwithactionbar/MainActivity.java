package com.capgemini.larseknu.playingwithactionbar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            toggleActionBar();

        return super.onTouchEvent(event);
    }

    private void toggleActionBar() {
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            if (actionBar.isShowing())
                actionBar.hide();
            else
                actionBar.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_option_1:
                Toast.makeText(this, "Option 1", Toast.LENGTH_SHORT).show();
                setActionBarLogo();
                return true;
            case R.id.menu_option_2:
                Toast.makeText(this, "Option 2", Toast.LENGTH_SHORT).show();
                setActionBarTitleAndSubtitle();
                return true;
            case R.id.menu_option_3:
                Toast.makeText(this, "Option 3", Toast.LENGTH_SHORT).show();
                toggleTitleAndSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBarLogo() {
        ActionBar actionBar = getActionBar();
        actionBar.setLogo(R.drawable.ic_capgemini_logo);
    }

    public void setActionBarTitleAndSubtitle() {
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Capgemini");
        actionBar.setSubtitle("People Matter. Results Count.");
    }

    public void toggleTitleAndSubtitle() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void onExitClicked(MenuItem item) {
        finish();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
