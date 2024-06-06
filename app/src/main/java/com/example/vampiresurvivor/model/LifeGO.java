package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;

public class LifeGO extends SpriteGO{
    private int Escala = 3;
    public LifeGO(GameSurfaceView gsv, Point pos) {
        super(gsv);
        sprites.put("life", new SpriteInfo(R.drawable.life, 1));
        setState("life");
        posSprite = pos;
    }
    @Override
    public void update() {
        super.update();
    }

    @Override
    public float getEscala() {
        return Escala;
    }
    @Override
    public PointF getDirection() {
        return new PointF(0,0);
    }

}
