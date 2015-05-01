package com.ssu.jnn.cs470final;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

//
public class mainMap extends FragmentActivity implements  GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Marker temp;

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "biTBkGGBIx7X5Ez7YVcD5poHjgH5puf7qFCQr56l", "Xo6xTmbGMM1fvI9DW16aopFW7S7fOoRRulnahT5H");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);



        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
//                .1f, mLocationListener);

        // Need to get best provider.



        setUpMapIfNeeded();

        Location location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            Log.d("Location", "" + location.getLatitude());

            LatLng temp = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp,15.0f));
        }
        else {
            Log.d("LocInfo", "Location was null");
        }

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("markerInfo");
        query.findInBackground(new FindCallback<ParseObject>() {
           public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
               if (e == null) {
                   for (int i =0; i < parseObjects.size(); i++) {
                       ParseObject pObj = parseObjects.get(i);
                       double lat = pObj.getParseGeoPoint("coordinates").getLatitude();
                       double lon = pObj.getParseGeoPoint("coordinates").getLongitude();
                       String title = pObj.getString("placeName");

                       Marker m = mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(title));
                   }
               }
            }
        });
        // Format for markers.
        //Marker temp1 = mMap.addMarker(new MarkerOptions().position(new LatLng(38.341104, -122.674610)).title("Sonoma State University"));
        //Marker temp2 = mMap.addMarker(new MarkerOptions().position(new LatLng(38.344508, -122.711653)).title("McDonalds"));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        // Activated on any marker click.
        // How to open new activities.
        Intent intent = new Intent(new Intent(mainMap.this, infoScreen.class));
        // Pass additional vars to the new class.
        intent.putExtra("mName", marker.getTitle());
        startActivity(intent);

        return false;
    }

    public void ButtonOnClick(View v) {
        switch (v.getId()) {
            case R.id.settingsButton:
                Log.i("Clicked", "1");
                Intent prefIntent = new Intent(new Intent(mainMap.this, preferences.class));
                startActivity(prefIntent);
                break;
            case R.id.addButton:
                Log.i("Clicked", "2");
                Intent addMarkerIntent = new Intent(new Intent(mainMap.this, addMarker.class));
                startActivity(addMarkerIntent);
                break;
        }
    }
}


