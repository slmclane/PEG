package com.example.strykermclane.peg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HandExercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Hand Exercises");

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_selection, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bar_CareGuide2) {
            Intent myIntent = new Intent(this, careguide.class);
            startActivity(myIntent);
        }
        return super.onOptionsItemSelected(item);
    }
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
