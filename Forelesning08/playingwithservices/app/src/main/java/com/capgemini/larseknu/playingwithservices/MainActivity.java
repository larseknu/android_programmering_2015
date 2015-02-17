package com.capgemini.larseknu.playingwithservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }

        LogHelper.ProcessAndThreadId("MyActivity.onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.do_work:
                doWork();
                break;
            case R.id.do_more_work:
                doMoreWork();
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void doMoreWork() {
        LogHelper.ProcessAndThreadId("MainActivity.doMoreWork");
        Intent intent = new Intent(this, WorkerService.class);
        startService(intent);
    }

    private void doWork() {
        LogHelper.ProcessAndThreadId("MainActivity.doWork");

        Intent intent = new Intent(this, OnDemandWorkerService.class);
        startService(intent);
    }
}
