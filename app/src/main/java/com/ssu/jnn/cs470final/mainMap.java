package com.ssu.jnn.cs470final;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Network;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//
public class mainMap extends FragmentActivity implements  GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Marker temp;

    LocationManager mLocationManager;

    ParseUser currentUser;

    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "biTBkGGBIx7X5Ez7YVcD5poHjgH5puf7qFCQr56l", "Xo6xTmbGMM1fvI9DW16aopFW7S7fOoRRulnahT5H");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        currentUser = ParseUser.getCurrentUser();


        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_LOW);
        criteria.setAltitudeRequired(false);
        String bestProvider = mLocationManager.getBestProvider(criteria, true);

        setUpMapIfNeeded();
        /*
        locationAssistant();
        Timer myTimer = new Timer();
        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                Log.i("meow","yes");
                locationAssistant();
            }
        };
        myTimer.schedule(TT,0,5000);
        */
        mMap.setMyLocationEnabled(true);


        // Try to get GPS Location
        Location location = mLocationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            // We got one; Use it.
            Log.d("Location Provider", bestProvider);
            Log.d("Location-Lat", "" + location.getLatitude());
            Log.d("Location-Long", "" + location.getLongitude());

            LatLng temp = new LatLng(location.getLatitude(),location.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
        }
        else {
            // No Location received, try using Network.
            Log.d("Location", "GPS Location was null");
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                Log.d("Location Provider", LocationManager.NETWORK_PROVIDER);
                Log.d("Location-Lat", "" + location.getLatitude());
                Log.d("Location-Long", "" + location.getLongitude());

                LatLng temp = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
            }
            else {
                // That didn't work either; give up.
                Log.d("Location", "Network Location was null");
            }
        }

        locationAssistant();
        Timer myTimer = new Timer();
        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                Log.i("meow","yes");
                locationAssistant();
            }
        };
        myTimer.schedule(TT,0,60000);

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
                startActivityForResult(prefIntent, 1);
                break;
            case R.id.addButton:
                Log.i("Clicked", "2");
                Intent addMarkerIntent = new Intent(new Intent(mainMap.this, addMarker.class));
                startActivityForResult(addMarkerIntent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.wtf("In OnActivityResult", "a");
        redrawMap();
    }

    void redrawMap () {
        Log.wtf("redrawing", "a");
        ParseGeoPoint parseLocation = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
        final int userRadius = currentUser.getInt("defaultRadius");
        float userMinRating = (float)currentUser.getDouble("minimumRating");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("markerInfo");
        query.whereWithinMiles("coordinates",parseLocation,userRadius);
        try {
            final List tempList = query.find();
            mMap.clear();
            for(int i = 0; i < tempList.size();i++){
                ParseObject pObj = (ParseObject) tempList.get(i);
                double lat = pObj.getParseGeoPoint("coordinates").getLatitude();
                double lon = pObj.getParseGeoPoint("coordinates").getLongitude();
                String title = pObj.getString("placeName");
                double rating = pObj.getDouble("rating");
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title(title));
            }

            Circle c = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(location.getLatitude(), location.getLongitude()))
                    .radius(userRadius * 1609.34)
                    .strokeWidth(1)
                    .strokeColor(Color.BLUE));


                }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void locationAssistant()
    {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_FINE);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_LOW);
        criteria.setAltitudeRequired(false);
        String bestProvider = mLocationManager.getBestProvider(criteria, true);
        // Try to get GPS Location
        location = mLocationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            // We got one; Use it.
            Log.d("Location Provider", bestProvider);
            Log.d("Location-Lat", "" + location.getLatitude());
            Log.d("Location-Long", "" + location.getLongitude());

            final LatLng temp = new LatLng(location.getLatitude(),location.getLongitude());

            Handler mainHandler = new Handler(getMainLooper());
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
                }
            });
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
        }
        else {
            // No Location received, try using Network.
            Log.d("Location", "GPS Location was null");
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                Log.d("Location Provider", LocationManager.NETWORK_PROVIDER);
                Log.d("Location-Lat", "" + location.getLatitude());
                Log.d("Location-Long", "" + location.getLongitude());

                final LatLng temp = new LatLng(location.getLatitude(), location.getLongitude());
                Handler mainHandler = new Handler(getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                       // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
                    }
                });
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(temp));
               // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(temp, 15.0f));
            }
            else {
                // That didn't work either; give up.
                Log.d("Location", "Network Location was null");
            }
        }

        ParseGeoPoint parseLocation = new ParseGeoPoint(location.getLatitude(),location.getLongitude());
        final int userRadius = currentUser.getInt("defaultRadius");
        final float userMinRating = (float)currentUser.getDouble("minimumRating");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("markerInfo");
        query.whereWithinMiles("coordinates",parseLocation,userRadius);
        try {
            final List tempList = query.find();
            Handler mainHandler = new Handler(getMainLooper());
            mainHandler.post(new Runnable(){
                @Override
                public void run()
                {
                    mMap.clear();
                    for(int i = 0; i < tempList.size();i++) {
                        ParseObject pObj = (ParseObject) tempList.get(i);
                        double lat = pObj.getParseGeoPoint("coordinates").getLatitude();
                        double lon = pObj.getParseGeoPoint("coordinates").getLongitude();
                        String title = pObj.getString("placeName");
                        double rating = pObj.getDouble("rating");
                        if (rating >= userMinRating) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(title));
                        }

                        Circle c = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(location.getLatitude(), location.getLongitude()))
                                .radius(userRadius * 1609.34)
                                .strokeWidth(1)
                                .strokeColor(Color.BLUE));
                    }
                }
            });
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


