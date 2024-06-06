package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;

public class GarlicGO extends SpriteGO{
    private int Escala = 4;
    protected Point posSprite;
    public GarlicGO(GameSurfaceView gsv, Point pos) {
        super(gsv);
        sprites.put("garlic", new SpriteInfo(R.drawable.garlic, 1));
        setState("garlic");
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
