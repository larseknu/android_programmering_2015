package com.capgemini.larseknu.playingwithtabs;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

@Deprecated
public class ListNavigationActivity extends Activity {
    ActionBar.OnNavigationListener _navigationListener;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleNavigationListener.SetupListNavigation(this, R.array.tabName, R.array.tabFragmentNames
        );
    }

}