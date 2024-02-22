package com.example.vampiresurvivor.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.example.vampiresurvivor.view.Utils;

public class JoyStickView extends View implements ViewTreeObserver.OnGlobalLayoutListener {

    private Paint p;
    private Paint pJ;
    private Paint pBack;

    private int w,h, mida, radiJ;

    private Point pJoystick, centre;

    private JoyStickListener mListener;

    public void addListener(JoyStickListener listener){
        mListener = listener;
    }

    public interface JoyStickListener{
        void onJoystickMoved(PointF speed);
    }

    public JoyStickView(Context context) {
        //super(context);
        this(context, null);
    }

    public JoyStickView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        p = new Paint();
        p.setColor(Color.BLUE);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        pJ = new Paint();
        pJ.setColor(Color.BLUE);
        pJ.setStyle(Paint.Style.FILL);
        pBack = new Paint();
        pBack.setColor(Color.TRANSPARENT);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        //============
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPaint(pBack);
        canvas.drawCircle(mida/2,mida/2,mida/2-p.getStrokeWidth()/2,p);
        if (pJoystick != null) {
            canvas.drawCircle(pJoystick.x, pJoystick.y, radiJ, pJ);
        }
    }

    @Override
    public void onGlobalLayout() {
        w = getWidth();
        h = getHeight();
        mida = Math.min(getWidth(),getHeight());
        pJoystick = new Point(w/2,h/2);
        radiJ= mida/4;
        centre = new Point(mida/2,mida/2);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (event.getActionMasked()==MotionEvent.ACTION_UP){
            pJoystick = centre;
            invalidate();
            return true;
        }

        Point touch = new Point((int)event.getX(),(int)event.getY());

        double distancia = Utils.getDistancia(centre,touch);
        double distanciaMax = mida/2-radiJ;
        if (distancia < distanciaMax){
            pJoystick = touch;
        } else {
            Point vector = new Point(touch.x-centre.x,touch.y-centre.y);
            vector = Utils.scale(vector,distanciaMax/distancia);
            pJoystick = Utils.suma(centre,vector);
        }
        invalidate();
        if (mListener != null){
            mListener.onJoystickMoved(getSpeed());
        }
        return true;
    }

    public PointF getSpeed(){
        double DistanciaMax = mida/2 - radiJ;
        Point vector = new Point(pJoystick.x-centre.x,pJoystick.y-centre.y);
        float escala = (float) (1/DistanciaMax);
        return new PointF(vector.x*escala,vector.y*escala);
    }


}
