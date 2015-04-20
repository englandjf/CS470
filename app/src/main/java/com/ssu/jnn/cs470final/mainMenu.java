package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class mainMenu extends ActionBarActivity {


    //Button mapButton;
    //Button loginButton;
    //Button signButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.i("open","1");
        //connect to buttons
        //mapButton = (Button) findViewById(R.id.mv);
        //loginButton = (Button) findViewById(R.id.lb);
        //signButton = (Button) findViewById(R.id.su);


    }

    public void ButtonOnClick(View v) {
        switch(v.getId()){
            case R.id.mv:
                Log.i("Clicked","1");
                Intent mapIntent = new Intent(new Intent(mainMenu.this,mainMap.class));
                startActivity(mapIntent);
                break;
            case R.id.lb:
                Log.i("Clicked","2");
                break;
            case R.id.su:
                Log.i("Clicked","3");
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
}
