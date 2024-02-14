package com.example.vampiresurvivor.model;

import static androidx.core.math.MathUtils.clamp;

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
    //private static final int speed = 10;
    private GameThread gameThread;
    private JoyStickView joystick;
    private CharacterGo player;
    //private Bitmap spritePj;
    //private int spritePjW, spritePjH;
    //private int spritePjFrames;
    //private boolean isMoving;
    private int spritePjCurrentFrame;
//    Point p = new Point(300, 300);
    Paint paint = new Paint();
    Paint pBackground = new  Paint();
    private MapGenerator map;
    private int w,h;
    private int W,H;
    private static final int ESCALA = 2;

    public GameSurfaceView(Context context){
        super(context, null);
    }

    public GameSurfaceView(Context context, AttributeSet as){
        super(context, as);
        this.getHolder().addCallback(this);
        pintar();
        map = new MapGenerator(getResources(), this.getContext());
        w = map.getScenario().getWidth();
        h = map.getScenario().getHeight();

        player = new CharacterGo(this);
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
        Point screenCorner = getScreenCoordinates();

        //canvas.drawPaint(pBackground);
        canvas.drawBitmap(map.getScenario(),
                new Rect(screenCorner.x, screenCorner.y,
                        screenCorner.x+w, screenCorner.y+h),
                new Rect(0,0, w, h), null);
        //canvas.drawCircle(p.x, p.y, 40, paint);

        player.paint(canvas);
    }

    public void update() {
        player.update();
    }

    public JoyStickView getJoystick() {
        return joystick;
    }

    private Point getScreenCoordinates(){
        Point coordCorner = new Point();
        coordCorner.x = player.getPosition().x - w/2;
        coordCorner.y = player.getPosition().y - h/2;

        coordCorner.x = clamp(coordCorner.x, 0, W-w);
        coordCorner.y = clamp(coordCorner.y, 0, H-h);
        return coordCorner;
    }

    public Point getScreenCoordPj(){
        Point coordScreen = new Point();
        Point screenCorner = getScreenCoordinates();
        coordScreen.x = player.getPosition().x - screenCorner.x;
        coordScreen.y = player.getPosition().y - screenCorner.y;
        return coordScreen;
    }
}
