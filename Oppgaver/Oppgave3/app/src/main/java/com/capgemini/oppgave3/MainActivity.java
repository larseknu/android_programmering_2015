package com.capgemini.oppgave3;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

public class MainActivity extends Activity {
	private EditText _movieTitleInput;
	private TextView _movieTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		_movieTitleInput = (EditText) findViewById(R.id.movieTitleInput);
		_movieTitle = (TextView) findViewById(R.id.movieTitle);
	}
	
	public void getMovie(View view) {
			String movieTitle = _movieTitleInput.getText().toString();
			String url = "http://www.it-stud.hiof.no/android/data/randomData.php";
			
			new GetMovieTitle().execute(url);
	}
	
	private class GetMovieTitle extends AsyncTask<String, Integer, String> {
		@Override
	    protected String doInBackground(String... params) {
			try {
		    	DefaultHttpClient httpclient = new DefaultHttpClient();  
				String response = httpclient.execute(new HttpGet(params[0]), new BasicResponseHandler());
				
				Log.d("Json", response);
				Gson gson = new Gson();
				Movie movie = gson.fromJson(response, Movie.class);
			
				return movie.getTitle() + " - Rating: " + movie.getRating();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Noe feilet";
	    }

	    protected void onPostExecute(String result) {
	        _movieTitle.setText(result);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onNorwegianFlagClicked(MenuItem menuItem) {
		startActivity(new Intent(this, NorwegianFlag.class));
	}
}


