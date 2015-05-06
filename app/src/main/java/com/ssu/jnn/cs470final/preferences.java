package com.ssu.jnn.cs470final;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class preferences extends ActionBarActivity {

    CheckBox allBoxes[];
    boolean loggedIn;

    Button loginOutButton;
    Button signUpButton;
    Button prefButton;
    SeekBar defaultRadiusBar;
    TextView userText;
    RatingBar minRatingBar;

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
        defaultRadiusBar = (SeekBar)findViewById(R.id.radiusPref);
        userText = (TextView)findViewById(R.id.userText);
        minRatingBar = (RatingBar)findViewById(R.id.minimumRatingBar);



        //Check if logged in
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            loggedIn = true;
            signUpButton.setClickable(false);
            loginOutButton.setText("Logout");

            //currentUser = ParseUser.getCurrentUser();
            List interests = currentUser.getList("currentInterests");
            for (int i = 0; i < interests.size(); i++) {
                for (int j = 0; j < 13; j++) {
                    Log.d("Interest",interests.get(i).toString());
                    Log.d("CheckBox",allBoxes[j].getText().toString());
                    Log.d("--", "--");
                    if ((interests.get(i).toString()).equals(allBoxes[j].getText().toString())) {
                        allBoxes[j].setChecked(true);
                    }
                }
            }
            defaultRadiusBar.setProgress(currentUser.getInt("defaultRadius"));
            minRatingBar.setRating((float)currentUser.getDouble("minimumRating"));
            userText.setText("User: " + currentUser.getUsername());

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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void ButtonOnClick(View v) {
        switch (v.getId()) {
            case R.id.signupbutton:
                Intent signupIntent = new Intent(new Intent(preferences.this, signUp.class));
                startActivity(signupIntent);
                finish();
                break;
            case R.id.updatePrefs:
                JSONArray values = new JSONArray();
                //String temp[] = new String[13];
                for(int i = 0; i < 13; i++) {
                    if (allBoxes[i].isChecked()) {
                        Log.d("value", allBoxes[i].getText().toString());
                        values.put(allBoxes[i].getText().toString());
                    }
                }
                currentUser.put("defaultRadius", defaultRadiusBar.getProgress());
                currentUser.put("currentInterests",values);
                currentUser.put("minimumRating", minRatingBar.getRating());
                currentUser.saveInBackground();
                //currentUser.save();
                finish();
                break;
            case R.id.loginButton:
                //two cases depending if the person is logged in
                if(!loggedIn) {
                    Intent loginIntent = new Intent(new Intent(preferences.this, loginPage.class));
                    startActivity(loginIntent);
                    finish();
                }
                else {
                    currentUser.logOutInBackground();
                    //refreshes activity
                    finish();
                    startActivity(getIntent());
                }
                break;
        }
    }

}
