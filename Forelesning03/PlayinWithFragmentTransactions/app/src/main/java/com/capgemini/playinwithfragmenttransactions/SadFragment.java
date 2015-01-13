package com.capgemini.playinwithfragmenttransactions;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SadFragment extends Fragment {
    final public String LOGTAG = "SadFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.fragment_sad, container, false);
	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOGTAG, "onActivityCreated");

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.d(LOGTAG, "onStart");

        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(LOGTAG, "onStop");

        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d(LOGTAG, "onPause");

        super.onPause();
    }

    @Override
    public void onResume() {
        Log.d(LOGTAG, "onResume");

        super.onResume();
    }

    @Override
    public void onDestroyView() {
        Log.d(LOGTAG, "onDestroyView");

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(LOGTAG, "onDestroy");

        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(LOGTAG, "onDetach");

        super.onDetach();
    }
}
