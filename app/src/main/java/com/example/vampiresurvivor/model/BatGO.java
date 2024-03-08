package com.example.vampiresurvivor.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;

public class BatGO extends SpriteGO {
    private static final int SPEED = 5;


    public BatGO(GameSurfaceView gsv, Point posicion) {
        super(gsv);

        sprites.put("fly", new SpriteInfo(R.drawable.bat_sheet_r, 9));
        setState("fly");

        posSprite = posicion;
    }
    @Override
    public float getEscala() {
        return 4;
    }

    @Override
    public PointF getDirection() {
        Point playerPos = gsv.getPlayerPosition();
        return new PointF(playerPos.x - posSprite.x, playerPos.y - posSprite.y);
    }

    @Override
    public void update() {
        super.update();
        Point playerPos = gsv.getPlayerPosition();
        PointF v = new PointF(playerPos.x - posSprite.x, playerPos.y - posSprite.y);
        double distancia = Utils.getDistancia(posSprite, playerPos);
        if (distancia > 0) {
            v.x /= distancia;
            v.y /= distancia;
        }
        posSprite.x += v.x * SPEED;
        posSprite.y += v.y * SPEED;
        if(!gsv.isInsideMap(posSprite)){
            gsv.deleteBat(this);
        }
    }

}
