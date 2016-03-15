package com.example.strykermclane.peg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * <h1>Landing Page</h1>
 *
 *
 *
 * @author  Stryker McLane
 * @version 1.0
 * @since   1/2016
 */

public class LandingPage extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing_page, menu);
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

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        /**
         * This method bring the user to the next screen.
         *
         *show the usage of various javadoc Tags.
         * @param view
         * @return Nothing
         */
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        // Do something in response to button
    }
    /** Called when the user clicks the TILT! button */
    public void goToRadialUlnarDeviation(View view) {
        /**
         * This method bring the user to the next screen.
         *
         *show the usage of various javadoc Tags.
         * @param view
         * @return Nothing
         */
        int exerSwitch = 3;
        Intent intentRadialUlnarDeviation = new Intent(this, exerciseActivity.class);
        intentRadialUlnarDeviation.putExtra("exerSwitch",0);
        startActivity(intentRadialUlnarDeviation);

    }
    /** Called when the user clicks the TILT! button */
    public void goToPronationSupination(View view) {
        /**
         * This method bring the user to the next screen.
         *
         *show the usage of various javadoc Tags.
         * @param view
         * @return Nothing
         */
        int exerSwitch = 3;
        Intent intentPronationSupination = new Intent(this, exerciseActivity.class);
        intentPronationSupination.putExtra("exerSwitch",1);
        startActivity(intentPronationSupination);

    }
    /** Called when the user clicks the TILT! button */
    public void goToFlexionExtension(View view) {
        /**
         * This method bring the user to the next screen.
         *
         *show the usage of various javadoc Tags.
         * @param view
         * @return Nothing
         */
        int exerSwitch = 3;
        Intent intentFlexionExtension = new Intent(this, exerciseActivity.class);
        intentFlexionExtension.putExtra("exerSwitch",2);
        startActivity(intentFlexionExtension);

    }
}
