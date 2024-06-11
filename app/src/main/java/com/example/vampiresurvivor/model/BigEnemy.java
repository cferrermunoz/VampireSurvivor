package com.example.vampiresurvivor.model;

import android.graphics.Point;
import android.graphics.PointF;
import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;


public class BigEnemy extends SpriteGO{
    /**
     * Comptador per canviar la direcció
     */
    private int count = 1000;
    /**
     * Direcció de l'enemic
     */
    private PointF direction = new PointF();
    /**
     * Vida de l'enemic
     */
    private int life = 3;
    private int speed = 5;

    /**
     * Constructor de l'enemic
     * @param gsv GameSurfaceView
     * @param posicion Posició de l'enemic
     */
    public BigEnemy(GameSurfaceView gsv, Point posicion) {
        super(gsv);
        sprites.put("walk", new SpriteInfo(R.drawable.big_enemy, 4));
        setState("walk");
        posSprite.x =  posicion.x;
        posSprite.y =  posicion.y;
        reassignDirection();
    }
    /**
     * Retorna l'escala
     * @return float
     */
    @Override
    public float getEscala() {
        return 4;
    }

    @Override
    public PointF getDirection() {
        return direction;
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
        double distancia = Utils.getDistancia(posSprite, p);
        if (distancia > 0) {
            direction.x = (float) ((p.x - posSprite.x) / distancia * speed);
            direction.y = (float) ((p.y - posSprite.y) / distancia * speed);
        }
    }

    /**
     * Assigna la vida de l'enemic
     * @param valor
     */
    public void setLife(int valor){
        this.life = this.life - valor;
    }
}
