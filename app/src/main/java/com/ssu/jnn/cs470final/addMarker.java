package com.ssu.jnn.cs470final;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class addMarker extends ActionBarActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    int sYr, sMo, sDy, sHr, sMn;

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
        categoriesArray.add("Other");

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

    public static class TimePickerFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), (addMarker)getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }
    }

    public static class DatePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (addMarker)getActivity(), year, month, day);
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView dateText = (TextView)findViewById(R.id.dateText);
        dateText.setText(month+1 + "/" + day + "/" + year);
        sYr = year;
        sMo = month+1;
        sDy = day;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView timeText = (TextView) findViewById(R.id.timeText);
        sHr = hourOfDay;
        sMn = minute;
        if (hourOfDay > 12) {
            timeText.setText(String.format("%02d:%02d PM", hourOfDay - 12, minute));
        }
        else if (hourOfDay == 0) {
            timeText.setText(String.format("%02d:%02d AM", 12, minute));
        }
        else if (hourOfDay == 12) {
            timeText.setText(String.format("%02d:%02d PM", 12, minute));
        }
        else {
            timeText.setText(String.format("%02d:%02d AM", hourOfDay, minute));
        }
    }

    public void OnDateSelect(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void OnTimeSelect(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void ButtonOnClick(View v){
        EditText placeNameField = (EditText)findViewById(R.id.markerName);
        Spinner categoryField = (Spinner)findViewById(R.id.categories);
        EditText addressField = (EditText)findViewById(R.id.address);
        EditText descriptionField = (EditText)findViewById(R.id.description);

        double latitude = 0;
        double longitude = 0;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;

        if(addressField.getText().length() == 0) {
            Toast.makeText(this,"Empty Address",Toast.LENGTH_SHORT);
            finish();
        }

        try {
            addresses = geocoder.getFromLocationName(addressField.getText().toString(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Address not found",Toast.LENGTH_SHORT);
            finish();
        }
        Address a = addresses.get(0);
        if(a != null) {
            latitude = a.getLatitude();
            longitude = a.getLongitude();
        }

        Date startDateTime = new Date(sYr-1900, sMo-1, sDy-1, sHr+3, sMn);

        ParseObject newMarker = new ParseObject("markerInfo");
        newMarker.put("placeName", placeNameField.getText().toString());
        newMarker.put("category", categoryField.getSelectedItem().toString());
        newMarker.put("Description", descriptionField.getText().toString());
        newMarker.put("coordinates", new ParseGeoPoint(latitude,longitude));
        try {
            newMarker.put("commentRating", new JSONArray(new float[]{3}));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        newMarker.put("rating", 3);
        //newMarker.put("startDate", startDateTime);
        newMarker.saveInBackground();
        finish();
    }
}
