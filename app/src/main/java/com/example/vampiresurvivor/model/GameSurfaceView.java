package com.example.vampiresurvivor.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.vampiresurvivor.R;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final int speed = 10;
    private GameThread gameThread;
    private JoyStickView joystick;
    private Bitmap spritePj;
    private int spritePjW, spritePjH;
    private int spritePjFrames;
    private boolean isMoving;
    private int spritePjCurrentFrame;
    Point p = new Point(300, 300);
    Paint paint = new Paint();
    Paint pBackground = new  Paint();
    private MapGenerator map;
    private int w,h;
    private static final int ESCALA = 2;

    public GameSurfaceView(Context context){
        super(context, null);
    }

    public GameSurfaceView(Context context, AttributeSet as){
        super(context, as);
        this.getHolder().addCallback(this);
        pintar();
        spritePj = BitmapFactory.decodeResource(getResources(), R.drawable.amongus_sprites);
        spritePjFrames = 4;
        spritePjW = spritePj.getWidth() / spritePjFrames;
        spritePjH = spritePj.getHeight();

        map = new MapGenerator(getResources(), this.getContext());
    }

    public void pintar(){
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        pBackground.setStyle(Paint.Style.FILL);
        pBackground.setColor(Color.WHITE);
    }

    public void setJoystick(JoyStickView joystick) {
        this.joystick = joystick;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        w = getWidth();
        h = getHeight();
        gameThread = new GameThread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameThread.gameOver();
    }

    public void paint(Canvas canvas) {
        //canvas.drawPaint(pBackground);
        canvas.drawBitmap(map.getScenario(),
                p.x-w/2, p.y-h/2, null);;
        //canvas.drawCircle(p.x, p.y, 40, paint);
        canvas.drawBitmap(spritePj,
                new Rect((spritePjCurrentFrame-1)*spritePjW,0,spritePjCurrentFrame*spritePjW, spritePjH),
                //new Rect(p.x, p.y, p.x+spritePjW*7, p.y+spritePjH*7),
                new RectF(w/2-spritePjW*0.5f*ESCALA, h/2-spritePjH*0.5f*ESCALA, w/2+spritePjW*0.5f*ESCALA, h/2+spritePjH*0.5f*ESCALA),
                pBackground);
    }

    public void update() {
        if(joystick !=null){
            PointF dir = joystick.getSpeed();
            p.x-=dir.x*speed;
            p.y-=dir.y*speed;

            boolean isMovingNext = Math.abs(dir.x)>0.001 || Math.abs(dir.y) > 0.001;

            if (!isMoving && isMovingNext) {
                spritePjCurrentFrame = 1;
            }

            isMoving = isMovingNext;

            if(isMoving) {
                spritePjCurrentFrame++;
                if (spritePjCurrentFrame > spritePjFrames) {
                    spritePjCurrentFrame = 2;
                }
            } else {
                spritePjCurrentFrame = 1;
            }

        }
    }
}
