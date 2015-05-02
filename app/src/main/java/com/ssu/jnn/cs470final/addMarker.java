package com.ssu.jnn.cs470final;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class addMarker extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marker);

        ArrayList<String> categoriesArray = new ArrayList();
        categoriesArray.add("Comedy");
        categoriesArray.add("Charity Event");
        categoriesArray.add("Food and Drink");
        categoriesArray.add("Festivals");
        categoriesArray.add("Family Event");
        categoriesArray.add("Lectures and Workshops");
        categoriesArray.add("Museums");
        categoriesArray.add("Music");
        categoriesArray.add("Movies");
        categoriesArray.add("Night Life");
        categoriesArray.add("Shopping");
        categoriesArray.add("Sports");
        categoriesArray.add("Theatre");

        Spinner spinner = (Spinner) findViewById(R.id.categories);
        ArrayAdapter<String> categoriesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoriesArray);

        categoriesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoriesArrayAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_marker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ButtonOnClick(View v){
        EditText placeNameField = (EditText)findViewById(R.id.pinName);
        Spinner categoryField = (Spinner)findViewById(R.id.categories);
        EditText addressField = (EditText)findViewById(R.id.address);
        EditText descriptionField = (EditText)findViewById(R.id.description);

        double latitude = 0;
        double longitude = 0;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(addressField.getText().toString(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address a = addresses.get(0);
        Log.wtf("address", a.toString());
        if(addresses.size() > 0) {
            latitude = a.getLatitude();
            longitude = a.getLongitude();
        }

        ParseObject newMarker = new ParseObject("markerInfo");
        newMarker.put("placeName", placeNameField.getText().toString());
        newMarker.put("category", categoryField.getSelectedItem().toString());
        newMarker.put("Description", descriptionField.getText().toString());
        newMarker.put("coordinates", new ParseGeoPoint(latitude,longitude));
        newMarker.saveInBackground();
    }
}
