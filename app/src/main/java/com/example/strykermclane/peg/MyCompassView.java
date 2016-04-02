package com.example.strykermclane.peg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MyCompassView extends View {

    // max unlar deviation line: 30 - 39 deg
    // max radial deviation line: 20 - 25 deg
    // max flexion line: 60 - 80 deg
    // max extension line: 60 - 75 deg
    private int maxFlexion = -60;
    private int maxExtension = 60;
    private int maxPronation = 60;
    private int maxSupination = -60;
    private int maxUlnarDeviation = 40;
    private int maxRadialDeviation = -25;

    private int regulateValue(int currentPosition)
    {
        int regulatedValue = 0;
        regulatedValue = 90 - currentPosition;
        return regulatedValue;
    }


    private Paint paintGreen;
    private Paint paintRed;
    private Paint paintWhite;
    private Paint paintGradient;
    private float position = 0;
    public int defaultDeviation = 0;
    public int exerSwitchCompass;
    public boolean vibeSwitch = false;
    Bitmap calibrateButtonBMP;
    private String currentRotation;
    public int maxUpper = 0;
    public int maxLower = 0;

    public MyCompassView(Context context) {
        super(context);
        init();
    }

    private void init() {

        //paintGreen: used for pointer
        paintGreen = new Paint();
        paintGreen.setAntiAlias(true);
        paintGreen.setStrokeWidth(20);
        paintGreen.setTextSize(100);
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setColor(Color.GREEN);

        //paintRed:
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setStrokeWidth(5);
        paintRed.setTextSize(100);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setColor(Color.WHITE);

        //paintWhite: used for current rotation reading
        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setStrokeWidth(5);
        paintWhite.setTextSize(250);
        paintWhite.setStyle(Paint.Style.FILL);
        paintWhite.setColor(Color.WHITE);

        //paintGradient: used for body of compass
        paintGradient = new Paint();
        paintGradient.setStrokeWidth(1);
        paintGradient.setStrokeCap(Paint.Cap.SQUARE);
        paintGradient.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int xPoint = getMeasuredWidth() / 2;
        int yPoint = getMeasuredHeight() / 2;

        //Draw Background
        canvas.drawColor(Color.BLACK);

        //Draw Calibrate BMP if exerSwictch = 0 (Radial / Ulnar Deviation Exercise Selected)
        calibrateButtonBMP = BitmapFactory.decodeResource(getResources(),R.drawable.calibratebutton);
        if(exerSwitchCompass == 0) {
            canvas.drawBitmap(calibrateButtonBMP, 0, 0, null);
        }

        //Calculate radius of circle based on screen size
        float radius = (float) (Math.max(xPoint, yPoint) * 0.5);

        //rect1: rectangle used to create compass arc
        RectF rect1 = new RectF(xPoint - radius, yPoint - radius, xPoint + radius, yPoint + radius);

        int[] colors = {Color.RED, Color.BLACK, Color.RED};
        float[] positions = {0f, 0.5f, 1f};
        SweepGradient gradient = new SweepGradient(100, 100, colors, positions);
        paintGradient.setShader(gradient);;

        //

        //tempX, tempY: coordinates for endpoint of neutral position line
        float tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(0)) / 180 * 3.143));
        float tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(0))  / 180 * 3.143));

        Path p = new Path();
        p.moveTo(xPoint, yPoint);
        p.lineTo(tempX, tempY); // neutral position line
        canvas.drawText(String.valueOf(defaultDeviation), tempX, tempY, paintRed);
        p.moveTo(xPoint, yPoint);


        switch(exerSwitchCompass) {
            case 0:
                //30 degrees
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxUlnarDeviation))  / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxUlnarDeviation)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // top line
                canvas.drawText(("Ulnar"), tempX, tempY - 100, paintRed);
                canvas.drawText(("Deviation"), tempX, tempY, paintRed);
                p.moveTo(xPoint, yPoint);
                //-25 degrees
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxRadialDeviation))  / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxRadialDeviation)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // bottom line
                canvas.drawText(("Radial"), tempX, tempY + 100, paintRed);
                canvas.drawText(("Deviation"), tempX, tempY + 200, paintRed);
                canvas.drawArc(rect1, 25, 295, true, paintGradient);
                break;
            case 1:
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxPronation)) / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxPronation)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // top line
                canvas.drawText(("Pronation"), tempX, tempY, paintRed);
                p.moveTo(xPoint, yPoint);
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxSupination)) / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxSupination)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // bottom line
                canvas.drawText(("Supination"), tempX, tempY, paintRed);
                canvas.drawArc(rect1, 60, 240, true, paintGradient);
                break;
            case 2:
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxExtension)) / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxExtension)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // top line
                canvas.drawText(("Extension"), tempX, tempY, paintRed);
                p.moveTo(xPoint, yPoint);
                tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxFlexion)) / 180 * 3.143));
                tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxFlexion)) / 180 * 3.143));
                p.lineTo(tempX, tempY); // bottom line
                canvas.drawText(("Flexion"), tempX, tempY, paintRed);
                canvas.drawArc(rect1, 60, 240, true, paintGradient);
                break;
        }
        if((exerSwitchCompass == 1) || (exerSwitchCompass == 2)) {
            p.moveTo(xPoint, yPoint);
            tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxUpper)) / 180 * 3.143));
            tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxUpper)) / 180 * 3.143));
            p.lineTo(tempX, tempY); // top line
            canvas.drawText(("Max:" + maxUpper + "\u00B0"), getMeasuredWidth() - 500, getMeasuredHeight() / 2, paintRed);
            p.moveTo(xPoint, yPoint);
            tempX = (float) (xPoint + radius * Math.sin((double) (regulateValue(maxLower)) / 180 * 3.143));
            tempY = (float) (yPoint - radius * Math.cos((double) (regulateValue(maxLower)) / 180 * 3.143));
            p.lineTo(tempX, tempY); // bottom line
            canvas.drawText(("Max:" + maxLower + "\u00B0"), getMeasuredWidth() - 500, (getMeasuredHeight() / 2)+ 100, paintRed);
            canvas.drawArc(rect1, 60, 240, true, paintGradient);
        }


        canvas.drawPath(p, paintRed);



        if(exerSwitchCompass == 0) {
            position -= defaultDeviation;
        }


        //Draw Pointer
        canvas.drawLine(xPoint,
                yPoint,
                (float) (xPoint + radius
                        * Math.sin((double) (-position) / 180 * 3.143)),
                (float) (yPoint - radius
                        * Math.cos((double) (-position) / 180 * 3.143)), paintGreen);


        currentRotation = String.valueOf(position - 270) + "\u00B0";
        //Draw current Rotation Value
        canvas.drawText(currentRotation, xPoint - (radius), yPoint + 100, paintWhite);
    }

    public void updateData(float position) {
        this.position = position;
        invalidate();
    }

    @Override
     public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                    calibrateButtonClicked();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    void calibrateButtonClicked(){
        defaultDeviation = (int) position - 270;
        if(exerSwitchCompass == 0){
            vibeSwitch = true;
        }
        invalidate();
    }

    void syncUserMaximum(){
        Context context = this.getContext();
        String maxUpperString = Integer.toString(maxUpper);
        String maxLowerString = Integer.toString(maxLower);
        String userMax = maxUpperString +","+ maxLowerString;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("recovery.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(maxUpperString);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }private String readFromFile() {
        Context context = this.getContext();

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("recovery.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}