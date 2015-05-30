package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class commentView extends ActionBarActivity {

    String[] comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_view);
        //String[] codeLearnChapters = new String[] { "Android Introduction","Android Setup/Installation","Android Hello World","Android Layouts/Viewgroups","Android Activity & Lifecycle","Intents in Android"};

        //get comments
        Bundle extras = getIntent().getExtras();
        comments = extras.getStringArray("comments");
        Log.d("Comments2","Comments" + comments[0]);

        ArrayAdapter<String> listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        ListView commentList = (ListView)findViewById(R.id.listView);
        commentList.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comment_view, menu);
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
            if((e1.getX() - e2.getX()) > sensitvity){

            }
            //right
            else if((e2.getX() - e1.getX()) > sensitvity){
                finish();
            }

            //up
            if((e1.getY() - e2.getY()) > sensitvity){
            }
            //down
            else if((e2.getY() - e1.getY()) > sensitvity){
            }



            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    GestureDetector gestureDetector
            = new GestureDetector(simpleOnGestureListener);

}
