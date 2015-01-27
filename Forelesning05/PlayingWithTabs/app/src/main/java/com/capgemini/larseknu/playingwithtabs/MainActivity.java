package com.capgemini.larseknu.playingwithtabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;


public class MainActivity extends Activity {

    ShareActionProvider mProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mProvider = (ShareActionProvider) menu.findItem(R.id.menu_share).getActionProvider();
        mProvider.setShareIntent(shareIntent());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.tabbed_activity:
                startActivity(new Intent(this, TabbedActivity.class));
                return true;
            case R.id.list_activity:
                startActivity(new Intent(this, ListNavigationActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Intent shareIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Super secret intel: The real color of coca cola is green!");
        return intent;
    }
}
