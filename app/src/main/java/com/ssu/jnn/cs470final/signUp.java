package com.ssu.jnn.cs470final;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class signUp extends ActionBarActivity {

    EditText user;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user = (EditText)findViewById(R.id.username);
        pass = (EditText)findViewById(R.id.password);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
    public void ButtonOnClick(View v) throws ParseException {
        switch (v.getId()) {
            case R.id.doSignUpButton:
                ParseUser pUser = new ParseUser();
                pUser.setUsername(user.getText().toString());
                pUser.setPassword(pass.getText().toString());
                try {
                    pUser.signUp();
                    Toast.makeText(getApplicationContext(), "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (ParseException e) {
                   Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                   finish();
                }

                break;
        }
    }

}
