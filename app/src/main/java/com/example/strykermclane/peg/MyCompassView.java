package com.example.strykermclane.peg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class MyCompassView extends View {

    private Paint paintGreen;
    private Paint paintRed;
    private Paint paintWhite;
    private float position = 0;
    boolean exerswitch = false;

    public MyCompassView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paintGreen = new Paint();
        paintGreen.setAntiAlias(true);
        paintGreen.setStrokeWidth(6);
        paintGreen.setTextSize(100);
        paintGreen.setStyle(Paint.Style.STROKE);
        paintGreen.setColor(Color.GREEN);
        paintRed = new Paint();
        paintRed.setAntiAlias(true);
        paintRed.setStrokeWidth(5);
        paintRed.setTextSize(100);
        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setColor(Color.RED);
        paintWhite = new Paint();
        paintWhite.setAntiAlias(true);
        paintWhite.setStrokeWidth(5);
        paintWhite.setTextSize(100);
        paintWhite.setStyle(Paint.Style.STROKE);
        paintWhite.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int xPoint = getMeasuredWidth() / 2;
        int yPoint = getMeasuredHeight() / 2;

        canvas.drawColor(Color.BLACK);

        float radius = (float) (Math.max(xPoint, yPoint) * 0.5);
        canvas.drawCircle(xPoint, yPoint, radius, paintWhite);
        // 3.143 is a good approximation for the circle

        canvas.drawLine(xPoint,
                yPoint,
                (float) (xPoint + radius
                        * Math.sin((double) (-position) / 180 * 3.143)),
                (float) (yPoint - radius
                        * Math.cos((double) (-position) / 180 * 3.143)), paintGreen);

        canvas.drawText(String.valueOf(position - 270), xPoint, yPoint, paintWhite);

        //0 is compass north

        float tempX = (float) (xPoint + radius * Math.sin((double) (90) / 180 * 3.143));
        float tempY = (float) (yPoint - radius * Math.cos((double) (90) / 180 * 3.143));

        Path p = new Path();
        p.moveTo(xPoint, yPoint);
        p.lineTo(tempX, tempY); // neutral position line
        p.moveTo(xPoint, yPoint);

        tempX = (float) (xPoint + radius * Math.sin((double) (30) / 180 * 3.143));
        tempY = (float) (yPoint - radius * Math.cos((double) (30) / 180 * 3.143));

        p.lineTo(tempX, tempY);
        if(exerswitch){
            canvas.drawText(("Pronation"), tempX, tempY, paintRed);
        }
        else{
            canvas.drawText(("Extension"), tempX, tempY, paintRed);
        }
        p.moveTo(xPoint, yPoint);

        tempX = (float) (xPoint + radius * Math.sin((double) (150) / 180 * 3.143));
        tempY = (float) (yPoint - radius * Math.cos((double) (150) / 180 * 3.143));

        p.lineTo(tempX, tempY);
        if(exerswitch){
            canvas.drawText(("Supination"), tempX, tempY, paintRed);
        }
        else{
            canvas.drawText(("Flexion"), tempX, tempY, paintRed);
        }
        canvas.drawPath(p, paintRed);

        //lines are rendering at the wrong spots, get needle to start at neutral position
        // max unlar deviation line: 30 - 39 deg
        // max radial deviation line: 20 - 25 deg
        // max flexion line: 60 - 80 deg
        // max extension line: 60 - 75 deg

    }

    public void updateData(float position) {
        this.position = position;
        invalidate();
    }

}