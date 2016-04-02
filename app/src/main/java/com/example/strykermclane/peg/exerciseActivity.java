package com.example.strykermclane.peg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;


public class exerciseActivity extends Activity implements SensorEventListener{

    private static SensorManager sensorManager;
    private MyCompassView compassView;
    private Sensor sensor;
    int exerSwitchCompass;
    private static Vibrator vibrateManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] accelVals;
    private float[] compassVals;
    static final float ALPHA = 0.15f;
    public float orientationGesture;

    /** Called when the activity is first created. */

    protected float[] lowPass( float[] input, float[] output ) {
        if ( output == null ) return input;

        for ( int i=0; i<input.length; i++ ) {
            output[i] = output[i] + ALPHA * (input[i] - output[i]);
        }
        return output;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compassView = new MyCompassView(this);
        setContentView(compassView);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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

        // Setup the sensors
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            
        }
        else {
            Log.d("Compass MainActivity", "accelerometer is null");
            Toast.makeText(this, "accelerometer is null",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magnetometer != null) {
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        }
        else{
                Log.d("Compass MainActivity", "magnetometer is null");
                Toast.makeText(this, "magnetometer is null, BIATCH!",
                        Toast.LENGTH_LONG).show();
                finish();
        }

    }




        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            vibrateManager = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            int sensorType = event.sensor.getType();
            switch (sensorType) {
                case Sensor.TYPE_ACCELEROMETER:
                    accelVals = lowPass( event.values.clone(), accelVals );
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    compassVals = lowPass( event.values.clone(), compassVals );
                    break;
                default:
                    Log.w("Compass MainActivity", "Unknown sensor type " + sensorType);
                    return;

            } if (accelVals != null && compassVals != null) {
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, accelVals, compassVals);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);

                    switch (exerSwitchCompass) {
                        case 0:
                            float azimuth = orientation[0];
                            int azimuthDeg = (int) Math.round(Math.toDegrees(azimuth));
                            if (((azimuthDeg - compassView.defaultDeviation > 40) || (azimuthDeg - compassView.defaultDeviation < -25)) && compassView.vibeSwitch) {
                                vibrateManager.vibrate(100);
                            }
                            orientationGesture = azimuthDeg + 270;
                            break;
                        case 1:
                            float pitch = orientation[1];
                            int pitchDeg = (int) Math.round(Math.toDegrees(pitch));
                            if ((pitchDeg > 60) || (pitchDeg < -60)) {
                                vibrateManager.vibrate(100);
                            }
                            if((pitchDeg > orientationGesture - 270) && (pitchDeg > 0) && (orientationGesture - 270 > 0) && (pitchDeg > compassView.maxUpper)){
                                compassView.maxUpper = pitchDeg;
                            }
                            else if((pitchDeg < orientationGesture - 270) && (pitchDeg < 0) && (orientationGesture - 270 < 0) && (pitchDeg < compassView.maxLower)){
                                compassView.maxLower = pitchDeg;
                            }
                            orientationGesture = pitchDeg + 270;
                            break;
                        case 2:
                            float roll = orientation[2];
                            int rollDeg = (int) Math.round(Math.toDegrees(roll));
                            if ((rollDeg > 60) || (rollDeg < -60)) {
                                vibrateManager.vibrate(100);
                            }
                            if((rollDeg > orientationGesture - 270) && (rollDeg > 0) && (orientationGesture - 270 > 0) && (rollDeg > compassView.maxUpper)){
                                compassView.maxUpper = rollDeg;
                            }
                            else if((rollDeg < orientationGesture - 270) && (rollDeg < 0) && (orientationGesture - 270 < 0) && (rollDeg < compassView.maxLower)){
                                compassView.maxLower = rollDeg;
                            }
                            orientationGesture = rollDeg + 270;
                            break;
                    }
                    compassView.updateData(orientationGesture);
                }
            }


        }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensor != null) {
            sensorManager.unregisterListener(this);
        }
    }


}
