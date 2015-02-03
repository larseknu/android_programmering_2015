package com.capgemini.playingwiththreads;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends Activity {
    PlaceholderFragment mFragment;
    Thread mWorkerThread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        mFragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mFragment)
                    .commit();
        }

		StrictMode.enableDefaults(); 
	}

	public void doAsyncWork() {
        DownloadWebPageTask asyncTask = new DownloadWebPageTask();
        asyncTask.execute("http://www.it-stud.hiof.no/android/data/randomData.php");
	}
	
	public void doWork() {
		mWorkerThread = new Thread(new Runnable() {
			public void run() {
                Worker worker = new Worker(MainActivity.this);
                updateUI("Starting");

                Location location = worker.getLocation();
                updateUI("Retrieved Location");

                String address = worker.reverseGeocode(location);
                updateUI("Retrieved Address");

                worker.save(location, address, "FancyFileName.out");
                updateUI("Done");
			}
		});
		mWorkerThread.start();
	}

	public void updateUI(String message) {
        mFragment.setDisplayedTextWithPost(message);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.do_work:
			doWork();
			break;
		case R.id.do_async_work:
			doAsyncWork();
			break;
		default:
			super.onOptionsItemSelected(item);
			break;
		}
		return true;
	}

    public class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            for (String url : urls) {
                DefaultHttpClient client = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                try {
                    response = client.execute(httpGet, new BasicResponseHandler());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            mFragment.setDisplayedTextWithPost(result);
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private TextView mTextView;
        private String mTempText;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mTextView = (TextView) rootView.findViewById(R.id.output_text_view);
            return rootView;
        }

        public void setDisplayedText(String outputText)
        {
            mTextView.setText(outputText);
        }

        public void setDisplayedTextWithPost(String outputText)
        {
            mTempText = outputText;
            mTextView.post(new Runnable() {
                public void run() {
                    mTextView.setText(mTempText);
                }
		    });
        }
    }
}