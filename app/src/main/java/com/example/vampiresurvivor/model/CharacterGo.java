package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;

public class CharacterGo extends GameObject{
    private static final int ESCALA = 4;
    private static final int SPEED = 10;
    Point posPj = new Point(300, 300);
    private Bitmap spritePj;
    private int spritePjW, spritePjH;
    private int spritePjFrames;
    private int spritePjCurrentFrame;
    private boolean isMoving;

    public CharacterGo(GameSurfaceView gsv) {
        super(gsv);
        spritePj = BitmapFactory.decodeResource(gsv.getResources(), R.drawable.amongus_sprites);
        spritePjFrames = 5;
        spritePjW = spritePj.getWidth() / spritePjFrames;
        spritePjH = spritePj.getHeight();
    }
    @Override
    public void update() {
        if(gsv.getJoystick() !=null){
            PointF dir = gsv.getJoystick().getSpeed();
            posPj.x+=dir.x*SPEED;
            posPj.y+=dir.y*SPEED;

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

    @Override
    public void paint(Canvas canvas) {
        Point posPjScreen = gsv.getScreenCoordPj();
        canvas.drawBitmap(spritePj, new Rect((spritePjCurrentFrame-1)*spritePjW,0
                        ,spritePjCurrentFrame*spritePjW, spritePjH),
                /*new Rect(p.x, p.y, p.x+spritePjW*4, p.y+spritePjH*4)*/
                new RectF(posPjScreen.x-spritePjW*0.5f*ESCALA,
                        posPjScreen.y-spritePjH*0.5f*ESCALA,
                        posPjScreen.x+spritePjW*0.5f*ESCALA,
                        posPjScreen.y+spritePjH*0.5f*ESCALA), null);
    }

    public Point getPosition(){
        return this.posPj;
    }
}
