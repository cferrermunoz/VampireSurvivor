package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;

public class LifeGO extends GameObject{
    private int Escala = 3;
    protected Point posSprite;
    private static RectF hitbox;
    private static Bitmap sprite;


    public LifeGO(GameSurfaceView gsv, Point pos) {
        super(gsv);
        if (sprite == null) {
            Bitmap spr = BitmapFactory.decodeResource(gsv.getResources(), R.drawable.life);
            sprite = Bitmap.createScaledBitmap(spr, spr.getWidth() / getEscala(), spr.getHeight() / getEscala(), false);

        }
        posSprite = gsv.getScreenCoordinates(pos);
        hitbox = new RectF(posSprite.x-sprite.getWidth()/2, posSprite.y-sprite.getHeight()/2, posSprite.x+sprite.getWidth()/2, posSprite.y+sprite.getHeight()/2);
    }

    @Override
    public void update() {

    }

    @Override
    public void paint(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(sprite, posSprite.x, posSprite.y, null);
        canvas.restore();
    }

    @Override
    public RectF getHitBox() {
        return hitbox;
    }

    private int getEscala() {
        return Escala;
    }

}
