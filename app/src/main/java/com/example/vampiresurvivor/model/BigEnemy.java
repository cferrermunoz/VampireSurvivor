package com.example.vampiresurvivor.model;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;

public class BigEnemy extends SpriteGO{
    private static final int SPEED = 5;
    private int count = 1000;
    private PointF direction;
    private int life = 3;

    public BigEnemy(GameSurfaceView gsv, Point posicion) {
        super(gsv);

        sprites.put("walk", new SpriteInfo(R.drawable.big_enemy, 4));
        setState("walk");
        posSprite = posicion;
        direction = new PointF(0,0);
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
        //comptador per canviar la direcció
        if (count > 0) {
            count--;
        } else  {
            count = 1000;
            reassignDirection();
        }

        posSprite.x = (int) (posSprite.x+direction.x);
        posSprite.y = (int) (posSprite.y+direction.y);
        if(!gsv.isInsideMap(posSprite)){
            gsv.deleteVampire(this);
        }

        for (GameObject go : gsv.getDaggers()) {
            if (RectF.intersects(go.getHitBox(), getHitBox())) {
                life--;
                if (life == 0) {
                    gsv.deleteVampire(this);
                    break;
                }
            }
        }

        if(!gsv.isInsideMap(posSprite)){
            gsv.deleteVampire(this);
        }
    }

    public void reassignDirection(){
        Point p = gsv.getRandomPoint();
        direction = new PointF(p.x,p.y);
    }
}
