package com.ssu.jnn.cs470final;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.parse.ParseException;

import java.util.ArrayList;


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
        //only one button
        //do this
    }
}
