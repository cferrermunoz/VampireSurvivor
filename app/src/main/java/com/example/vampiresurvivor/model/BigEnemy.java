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
    /**
     * Velocitat de l'enemic
     */
    private static final int SPEED = 5;
    /**
     * Comptador per canviar la direcció
     */
    private int count = 1000;
    /**
     * Direcció de l'enemic
     */
    private PointF direction;
    /**
     * Vida de l'enemic
     */
    private int life = 3;

    /**
     * Constructor de l'enemic
     * @param gsv GameSurfaceView
     * @param posicion Posició de l'enemic
     */
    public BigEnemy(GameSurfaceView gsv, Point posicion) {
        super(gsv);
        sprites.put("walk", new SpriteInfo(R.drawable.big_enemy, 4));
        setState("walk");
        posSprite = posicion;
        direction = new PointF(0,0);
    }
    /**
     * Retorna l'escala
     * @return float
     */
    @Override
    public float getEscala() {
        return 4;
    }
    /**
     * Retorna la direcció de l'enemic
     * @return PointF
     */
    @Override
    public PointF getDirection() {
        Point playerPos = gsv.getPlayerPosition();
        return new PointF(playerPos.x - posSprite.x, playerPos.y - posSprite.y);
    }
    /**
     * Actualitza l'enemic
     */
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
        //moviment de l'enemic
        posSprite.x = (int) (posSprite.x+direction.x);
        posSprite.y = (int) (posSprite.y+direction.y);
        if(!gsv.isInsideMap(posSprite)){
            gsv.deleteVampire(this);
        }

        //per garlic
        if (life<0){
            gsv.deleteVampire(this);
        }
    }
    /**
     * Funció que canvia la direcció de l'enemic
     */
    public void reassignDirection(){
        Point p = gsv.getRandomPoint();
        direction = new PointF(p.x,p.y);
    }

    /**
     * Assigna la vida de l'enemic
     * @param valor
     */
    public void setLife(int valor){
        this.life = this.life - valor;
    }
}
