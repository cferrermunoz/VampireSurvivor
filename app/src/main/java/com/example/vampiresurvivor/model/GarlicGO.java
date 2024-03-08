package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;

public class GarlicGO extends GameObject{
    private int Escala = 1;
    protected Point posSprite;
    private static Bitmap sprite;
    private RectF hitbox;
    public GarlicGO(GameSurfaceView gsv, Point pos) {
        super(gsv);
        if (sprite == null) {
            Bitmap spr = BitmapFactory.decodeResource(gsv.getResources(), R.drawable.garlic);
            sprite = Bitmap.createScaledBitmap(spr, spr.getWidth() / getEscala(), spr.getHeight() / getEscala(), false);
        }
        posSprite = pos;
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
