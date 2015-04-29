package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class preferences extends ActionBarActivity {

    CheckBox allBoxes[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Store all checkboxes in array
        allBoxes = new CheckBox[13];
        allBoxes[0] = (CheckBox)findViewById(R.id.checkBox);
        allBoxes[1] = (CheckBox)findViewById(R.id.checkBox2);
        allBoxes[2] = (CheckBox)findViewById(R.id.checkBox3);
        allBoxes[3] = (CheckBox)findViewById(R.id.checkBox4);
        allBoxes[4] = (CheckBox)findViewById(R.id.checkBox5);
        allBoxes[5] = (CheckBox)findViewById(R.id.checkBox6);
        allBoxes[6] = (CheckBox)findViewById(R.id.checkBox7);
        allBoxes[7] = (CheckBox)findViewById(R.id.checkBox8);
        allBoxes[8] = (CheckBox)findViewById(R.id.checkBox9);
        allBoxes[9] = (CheckBox)findViewById(R.id.checkBox10);
        allBoxes[10] = (CheckBox)findViewById(R.id.checkBox11);
        allBoxes[11] = (CheckBox)findViewById(R.id.checkBox12);
        allBoxes[12] = (CheckBox)findViewById(R.id.checkBox13);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
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

    public void ButtonOnClick(View v) {
        switch (v.getId()) {
            case R.id.signupbutton:
                Log.i("Clicked", "1");
                Intent signupIntent = new Intent(new Intent(preferences.this, signUp.class));
                startActivity(signupIntent);
                break;
            case R.id.updatePrefs:
                Log.i("Update","Prefs");
                final boolean temp[] = new boolean[13];
                for(int i = 0; i < 13; i++) {
                    temp[i] = allBoxes[i].isChecked();
                }
                //Puts array in db
                /*
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                query.whereEqualTo("username","I am nick");
                query.findInBackground(new FindCallback<ParseObject>() {
                    public void done(List<ParseObject> userList, ParseException e) {
                        if (e == null) {
                            userList.get(0).put("currentInterests",temp);
                            userList.get(0).saveInBackground();

                        } else {
                            Log.d("score", "Error: " + e.getMessage());
                        }
                    }
                });
                */

                //break;
        }
    }

}
