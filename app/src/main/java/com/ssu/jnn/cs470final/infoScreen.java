package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
//

public class infoScreen extends ActionBarActivity{

    private String markerName;
    TextView gestureEvent;
    TextView tv;
    boolean dataLoaded;
    List comments;
    double rating;
    String eventDescription;
    ParseObject objectMarker;

    // I am a comment!
    //test
    //Jamesy comment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);
        Bundle extras = getIntent().getExtras();
        markerName = extras.getString("mName");
        tv = (TextView) findViewById(R.id.textView);
        tv.setText(markerName);
        //detect swipe
        gestureEvent = (TextView)findViewById(R.id.GestureEvent);
        dataLoaded = false;
        getData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_screen, menu);
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

    //http://android-er.blogspot.com/2011/11/detect-swipe-using-simpleongestureliste.html
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event);
    }

    GestureDetector.SimpleOnGestureListener simpleOnGestureListener
            = new GestureDetector.SimpleOnGestureListener(){


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            float sensitvity = 50;

            // TODO Auto-generated method stub
            //left
            if((e1.getX() - e2.getX()) > sensitvity && dataLoaded){
                String [] listArray = new String[comments.size()];
                for(int i = 0; i < comments.size();i++) {
                    listArray[i] = comments.get(i).toString();
                }
                Intent intent = new Intent(new Intent(infoScreen.this,commentView.class));
                intent.putExtra("comments",listArray);
                startActivity(intent);
            }
            //right
            else if((e2.getX() - e1.getX()) > sensitvity){
                finish();
            }else{
            }
            //up
            if((e1.getY() - e2.getY()) > sensitvity){
            }
            //down
            else if((e2.getY() - e1.getY()) > sensitvity){
            }else{
            }



            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("markerInfo");
        query.whereEqualTo("placeName", markerName);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {
                    dataLoaded = true;

                    objectMarker = parseObjects.get(0);
                    comments = objectMarker.getList("comments");
                    rating  = objectMarker.getDouble("rating");
                    eventDescription = objectMarker.getString("Description");

                    RatingBar rb = (RatingBar) findViewById(R.id.ratingBar);
                    rb.setRating((float)rating);
                    TextView descTextView = (TextView) findViewById(R.id.eventDescriptionText);
                    descTextView.setText(eventDescription);

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }

        });
    }

    public void ButtonOnClick (View v) {
        Intent intent = new Intent(new Intent(infoScreen.this,addComment.class));
        intent.putExtra("objectID",objectMarker.getObjectId());
        startActivity(intent);
    }

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);


}
