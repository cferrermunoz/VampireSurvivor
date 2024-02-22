package com.example.vampiresurvivor.model;

import static androidx.core.math.MathUtils.clamp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.DrawableRes;

import com.example.vampiresurvivor.view.GameSurfaceView;

import java.util.HashMap;

public abstract class SpriteGO extends GameObject {
    protected HashMap<String, SpriteInfo> sprites;
    protected Point posSprite = new Point(500, 500);
    private String state;
    public float lastSeenX;
    private RectF hitbox;

    public SpriteGO(GameSurfaceView gsv) {
        super(gsv);
        sprites = new HashMap<>();
    }

    protected void setState(String state) {
        this.state = state;
    }

    protected SpriteInfo getSpriteInfo() {
        return sprites.get(state);
    }

    @Override
    public void update() {
        SpriteInfo s = getSpriteInfo();
        s.nextFrame();
    }

    @Override
    public void paint (Canvas canvas){
        Point posPjScreen = gsv.getScreenCoordinates(posSprite);
        SpriteInfo s = getSpriteInfo();
        canvas.save();
        float x = getDirection().x;
        if (x==0){
            x = lastSeenX;
            //canvas.scale(-1,1, posPjScreen.x, posPjScreen.y);
        }
        else {
            lastSeenX = x;
            //canvas.scale(1,1, posPjScreen.x, posPjScreen.y);
        }
        if (x < 0){
            canvas.scale(-1,1, posPjScreen.x, posPjScreen.y);
        }
        else {
            canvas.scale(1,1, posPjScreen.x, posPjScreen.y);
        }
        hitbox = new RectF(posPjScreen.x - s.w * 0.5f * getEscala(),
                posPjScreen.y - s.h * 0.5f * getEscala(),
                posPjScreen.x + s.w * 0.5f * getEscala(),
                posPjScreen.y + s.h * 0.5f * getEscala());
        canvas.drawBitmap(s.sprite, new Rect((s.currentFrame) * s.w, 0
                        , (s.currentFrame + 1) * s.w, s.h),
                /*new Rect(p.x, p.y, p.x+spritePjW*4, p.y+spritePjH*4)*/
                hitbox, null);


        canvas.restore();
    }

    public abstract float getEscala();

    public abstract PointF getDirection();

    public Point getPosition() {
        return this.posSprite;
    }
    public RectF getHitBox(){
        return hitbox;
    }
    protected class SpriteInfo {
        private static final int MAX_COUNT = 3;
        public Bitmap sprite;
        public int size;
        public int currentFrame = 0;
        private int fCounter = 0;
        public int w, h;

        public SpriteInfo(@DrawableRes int drawabledRes, int size) {
            this.sprite = BitmapFactory.decodeResource(gsv.getResources(), drawabledRes);
            this.size = size;

            this.w = sprite.getWidth() / size;
            this.h = sprite.getHeight();
        }

        public void nextFrame() {
            fCounter++;
            if(fCounter>MAX_COUNT){
                currentFrame = (currentFrame + 1) % size;
                fCounter = 0;
            }

        }
    }
}
