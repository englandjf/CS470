package com.ssu.jnn.cs470final;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;


public class addComment extends ActionBarActivity {

    String objectID;
    Button addComment;
    EditText commentIt;
    RatingBar theRating;
    String selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        Intent intent = getIntent();
        objectID = intent.getStringExtra("objectID");
        selectedClass = intent.getStringExtra("sc");
        addComment = (Button)findViewById(R.id.addComment);
        commentIt = (EditText)findViewById(R.id.actualComment);
        theRating = (RatingBar)findViewById(R.id.individualRating);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_comment, menu);
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

    public void OnButtonClick(View v){
        Log.i("selecteClass",""+selectedClass);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(selectedClass);
        query.getInBackground(objectID,new GetCallback<ParseObject>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if(e == null){
                    List allComments = parseObject.getList("comments");
                    if (allComments != null) {
                        allComments.add(allComments.size(),commentIt.getText().toString());
                        parseObject.put("comments",allComments);
                    }
                    else {
                        String allComments2[] = new String[1];
                        allComments2[0] = commentIt.getText().toString();
                        try {
                            JSONArray allComments3 = new JSONArray(allComments2);
                            parseObject.put("comments",allComments3);
                        }
                        catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                    List allRatings = parseObject.getList("commentRating");
                    if (allRatings != null) {
                        allRatings.add(allRatings.size(),theRating.getRating());
                        parseObject.put("commentRating",allRatings);
                        int numRatings = allRatings.size();
                        float ratingSum = 0.0f;
                        for (int i = 0; i < numRatings; i++) {
                            Number oneRating = (Number)allRatings.get(i);
                            ratingSum = ratingSum + oneRating.floatValue();
                        }
                        float newEventRating = ratingSum/numRatings;
                        parseObject.put("rating", newEventRating);
                    }
                    parseObject.saveInBackground();
                    finish();
                }
                else{
                    //error
                    Log.i("error","error");
                }
            }
        });

    }
}
