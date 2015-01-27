package com.capgemini.larseknu.playingwithtabs;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

@Deprecated
public class TabbedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SimpleTabListener.SetupTabbedNavigation(this, R.array.tabName, R.array.tabFragmentNames);

/*		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		ActionBar.TabListener tabListener1 = new SimpleTabListener(this, "com.capgemini.larseknu.playingwithtabs.iPhoneOrDroidFragment", R.id.content_fragment);
		ActionBar.Tab iPhoneDroidTab = actionBar.newTab();
		iPhoneDroidTab.setText("iPhone or Droid");
		iPhoneDroidTab.setTabListener(tabListener1);
		actionBar.addTab(iPhoneDroidTab);

		ActionBar.TabListener tablistener2 = new SimpleTabListener(this, "com.capgemini.larseknu.playingwithtabs.GoodCodeFragment");
		ActionBar.Tab goodCodeTab = actionBar.newTab()
											.setText("Good Code")
											.setTabListener(tablistener2);
		actionBar.addTab(goodCodeTab);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_tab_control, menu);
        return true;
    }

    public void onToggleTitleClick(MenuItem menuItem) {
        ActionBar actionBar = getActionBar();

        int currentOptions = actionBar.getDisplayOptions();
        boolean currentVisibleValue =
                (currentOptions & ActionBar.DISPLAY_SHOW_TITLE) != 0;
        boolean newVisibleValue = !currentVisibleValue;

        actionBar.setDisplayShowHomeEnabled(newVisibleValue);
        actionBar.setDisplayShowTitleEnabled(newVisibleValue);
    }
}
