package com.example.vampiresurvivor.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PaintView extends View {
    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Paint p = new Paint();
//        p.setColor(Color.RED);
//        p.setStyle(Paint.Style.FILL);
//        canvas.drawPaint(p);
//
//        Paint pLine = new Paint();
//        pLine.setColor(Color.BLUE);
//        pLine.setStyle(Paint.Style.STROKE);
//        pLine.setStrokeWidth(10);
//
//        Path path = new Path();
//        path.moveTo(0, 0);
//        path.lineTo(500, 500);
//        path.lineTo(500, 1000);
//        path.cubicTo(500, 1500, 1000, 1500, 1000, 1000);
//        canvas.drawPath(path, pLine);
    }



}
