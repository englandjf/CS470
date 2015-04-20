package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.Parse;
import com.parse.ParseObject;

//
public class mainMap extends FragmentActivity implements GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Marker temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "biTBkGGBIx7X5Ez7YVcD5poHjgH5puf7qFCQr56l", "Xo6xTmbGMM1fvI9DW16aopFW7S7fOoRRulnahT5H");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setOnMarkerClickListener(this);
        Marker temp1 = mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        Marker temp2 = mMap.addMarker(new MarkerOptions().position(new LatLng(38.341104, -122.674610)).title("Sonoma State University"));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent intent = new Intent(new Intent(mainMap.this,infoScreen.class));
        intent.putExtra("mName",marker.getTitle());
        startActivity(intent);

        return false;
    }
}
