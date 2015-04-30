package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class preferences extends ActionBarActivity {

    CheckBox allBoxes[];
    boolean loggedIn;

    Button loginOutButton;
    Button signUpButton;
    Button prefButton;

    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        //Store all checkboxes in array
        allBoxes = new CheckBox[13];
        allBoxes[0] = (CheckBox)findViewById(R.id.comedyCheck);
        allBoxes[1] = (CheckBox)findViewById(R.id.charityCheck);
        allBoxes[2] = (CheckBox)findViewById(R.id.foodCheck);
        allBoxes[3] = (CheckBox)findViewById(R.id.festivalsCheck);
        allBoxes[4] = (CheckBox)findViewById(R.id.familyCheck);
        allBoxes[5] = (CheckBox)findViewById(R.id.lecturesCheck);
        allBoxes[6] = (CheckBox)findViewById(R.id.musicCheck);
        allBoxes[7] = (CheckBox)findViewById(R.id.museumCheck);
        allBoxes[8] = (CheckBox)findViewById(R.id.moviesCheck);
        allBoxes[9] = (CheckBox)findViewById(R.id.nightCheck);
        allBoxes[10] = (CheckBox)findViewById(R.id.shoppingCheck);
        allBoxes[11] = (CheckBox)findViewById(R.id.sportsCheck);
        allBoxes[12] = (CheckBox)findViewById(R.id.theatreCheck);

        //Get Buttons
        loginOutButton = (Button)findViewById(R.id.loginButton);
        signUpButton = (Button)findViewById(R.id.signupbutton);
        prefButton = (Button)findViewById(R.id.updatePrefs);

        //Check if logged in
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            loggedIn = true;
            signUpButton.setClickable(false);
            loginOutButton.setText("Logout");
        } else {
            loggedIn = false;
            prefButton.setClickable(false);
        }

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
                break;
            case R.id.loginButton:
                //two cases depending if the person is logged in
                if(!loggedIn) {
                    Intent loginIntent = new Intent(new Intent(preferences.this, loginPage.class));
                    startActivity(loginIntent);
                }
                else {
                    currentUser.logOutInBackground();
                    //refreshes activity
                    finish();
                    startActivity(getIntent());
                }
                break;
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
