package com.capgemini.larseknu.playingwithgooglemaps;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class MapsActivity extends Activity implements GoogleMap.OnMapLongClickListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    private LatLng mMyPosition = new LatLng(0,0);
    private GMapV2Direction mMapDirection;

    private LatLng HIOF = new LatLng(59.12797849, 11.35272861);
    private LatLng FREDRIKSTAD = new LatLng(59.21047628, 10.93994737);

    // Number of kitten markers currently on the map
    private int mKittyCounter = 0;
    private ArrayList<Marker> mKittyMarkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        mKittyMarkers = new ArrayList<>();
        mMapDirection = new GMapV2Direction();

        if (savedInstanceState == null) {
            // Move the camera to the "starting position" at Østfold University College, with 13 in zoom and no tilt and bearing north
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(HIOF, 13, 0, 0)));
            // Animate the camera from the position to Fredrikstad Kino over a duration of 2 seconds
            mMap.animateCamera(CameraUpdateFactory.newLatLng(FREDRIKSTAD), 2000, null);
        }
        else {
            // We get our KittenLocation from the savedInstance bundle
            KittenLocation kittenLocation = savedInstanceState.getParcelable("found_kitten");
            if (kittenLocation != null) {
                // We add the kitten marker to the map
                addKittenMarker(kittenLocation.getLatLng(), "Found Kitten");
            }
        }

        Spinner spinner = (Spinner) findViewById(R.id.layers_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the MapFragment.
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMarkers();
                setUiSettings();
                mMap.setOnMapLongClickListener(this);
                mMap.setMyLocationEnabled(true);
            }
        }
    }

    private void setUiSettings() {
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setTiltGesturesEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
    }

    private void setUpMarkers() {
        // Add marker at the location of Østfold University College
        mMap.addMarker(new MarkerOptions().position(HIOF).title("Østfold University College"));
        // Add marker at the location of Fredrikstad Kino
        mMap.addMarker(new MarkerOptions().position(FREDRIKSTAD).title("Fredrikstad Kino"));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // We want to save one of the kittens if one exists
        if (!mKittyMarkers.isEmpty()) {
            // Gets the first kitten
            Marker kittyMarker = mKittyMarkers.get(0);
            // Instantiate KittenLocation, which is a parcelable class, based on data from the marker
            KittenLocation kittenLocation = new KittenLocation(kittyMarker.getTitle(), new LatLng(kittyMarker.getPosition().latitude, kittyMarker.getPosition().longitude));
            // Put the KittenLocation data into the bundle
            outState.putParcelable("found_kitten", kittenLocation);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        addKittenMarker(latLng);
    }

    private void addKittenMarker(LatLng kittenLocation) {
        addKittenMarker(kittenLocation, "Kitty Invasion");
    }

    private void addKittenMarker(LatLng kittenLocation, String snippet) {
        // Get a kittenIcon from the drawable resources. Must be named "kitten_0X", where X is a number.
        BitmapDescriptor kittenIcon = BitmapDescriptorFactory.fromResource(getResources().getIdentifier("kitten_0" + (mKittyCounter%3+1), "drawable", this.getPackageName()));
        // One more kitten is to be added
        mKittyCounter++;
        // Create all the marker options for the kitty marker
        MarkerOptions markerOptions = new MarkerOptions().position(kittenLocation)
                .title("Mittens the " + mKittyCounter + ".")
                .snippet(snippet)
                .icon(kittenIcon);
        // Add the marker to the map
        Marker kittyMarker = mMap.addMarker(markerOptions);
        // Add the marker to the kittyMarkersArray
        mKittyMarkers.add(kittyMarker);
    }

    static void animateMarker(Marker marker, LatLng finalPosition) {
        TypeEvaluator<LatLng> typeEvaluator = new TypeEvaluator<LatLng>() {
            @Override
            public LatLng evaluate(float fraction, LatLng startValue, LatLng endValue) {
                double lat = (endValue.latitude - startValue.latitude) * fraction + startValue.latitude;
                double lng = (endValue.longitude - startValue.longitude) * fraction + startValue.longitude;
                return new LatLng(lat, lng);
            }
        };
        Property<Marker, LatLng> property = Property.of(Marker.class, LatLng.class, "position");
        ObjectAnimator animator = ObjectAnimator.ofObject(marker, property, typeEvaluator, finalPosition);
        animator.setDuration(1000);
        animator.start();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String layerType = (String) parent.getItemAtPosition(position);
        if (layerType.equals(getString(R.string.hybrid))) {
            mMap.setMapType(MAP_TYPE_HYBRID);

        } else if (layerType.equals(getString(R.string.satellite))) {
            mMap.setMapType(MAP_TYPE_SATELLITE);

        } else if (layerType.equals(getString(R.string.terrain))) {
            mMap.setMapType(MAP_TYPE_TERRAIN);

        } else if (layerType.equals(getString(R.string.none))) {
            mMap.setMapType(MAP_TYPE_NONE);

        } else {
            mMap.setMapType(MAP_TYPE_NORMAL);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.kitty_attack:
                for (Marker kittyMarker : mKittyMarkers)
                    animateMarker(kittyMarker, FREDRIKSTAD);
                break;
            case R.id.remove_kittens:
                removeAllKittyMarkers();
                break;
            case R.id.draw_route:
                Location myLocation = mMap.getMyLocation();
                mMyPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                new drawRoute().execute();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private class drawRoute extends AsyncTask<Void, Void, PolylineOptions> {
        @Override
        protected PolylineOptions doInBackground(Void... params) {
            Document doc = mMapDirection.getDocument(mMyPosition, FREDRIKSTAD, GMapV2Direction.MODE_DRIVING);

            ArrayList<LatLng> directionPoint = mMapDirection.getDirection(doc);
            PolylineOptions rectLine = new PolylineOptions().width(3).color(Color.BLUE);

            for (int i = 0; i < directionPoint.size(); i++) {
                rectLine.add(directionPoint.get(i));
            }

            return rectLine;
        }

        @Override
        protected void onPostExecute(PolylineOptions rectLine) {
            mMap.addPolyline(rectLine);
        }
    }


    private void removeAllKittyMarkers() {
        for (Marker kittyMarker : mKittyMarkers) {
            kittyMarker.remove();
        }
        mKittyMarkers.clear();
        mKittyCounter = 0;
    }
}
