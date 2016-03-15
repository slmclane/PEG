package com.example.strykermclane.peg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class exerciseActivity extends Activity {

    private static SensorManager sensorService;
    private MyCompassView compassView;
    private Sensor sensor;
    int exerSwitchCompass;
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassView = new MyCompassView(this);
        setContentView(compassView);

        Intent myIntent = getIntent();
        exerSwitchCompass = (myIntent.getIntExtra("exerSwitch",3));
        switch(exerSwitchCompass){
            case 0:
                compassView.exerSwitchCompass = 0;
                break;
            case 1:
                compassView.exerSwitchCompass = 1;
                break;
            case 2:
                compassView.exerSwitchCompass = 2;
                break;
        }

        sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensor != null) {
            sensorService.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            float orientationGesture = 0;
            switch(exerSwitchCompass){
                case 0:
                    orientationGesture = event.values[0] + 270;
                    break;
                case 1:
                    orientationGesture = event.values[1] + 270;
                    break;
                case 2:
                    orientationGesture = event.values[2] + 270;
                    break;
            }
            compassView.updateData(orientationGesture);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensorService.unregisterListener(mySensorEventListener);
        }
    }

}
