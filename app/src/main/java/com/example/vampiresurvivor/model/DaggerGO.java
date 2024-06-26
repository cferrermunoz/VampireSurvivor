package com.example.vampiresurvivor.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;

public class DaggerGO extends GameObject{
    private static final int SPEED = 12;
    private int Escala = 8;
    protected Point posSprite = new Point();
    private static Bitmap sprite;
    private final float angle;
    private final PointF direction;
    private static RectF hitbox = null;

    public DaggerGO(GameSurfaceView gsv, Point desti) {
        super(gsv);
        if (sprite == null) {
            Bitmap spriteA = BitmapFactory.decodeResource(gsv.getResources(), R.drawable.dagger);
            sprite = Bitmap.createScaledBitmap(spriteA, spriteA.getWidth() / getEscala(), spriteA.getHeight() / getEscala(), false);
        }
        if (hitbox == null) {
            hitbox = new RectF(0,0,sprite.getWidth()+40,sprite.getHeight()+40);
        }
        posSprite.x = gsv.getPlayerPosition().x;
        posSprite.y = gsv.getPlayerPosition().y;
        direction = new PointF(desti.x-posSprite.x,desti.y-posSprite.y);
        double distancia = Utils.getModule(direction);
        direction.x = (float) (direction.x/distancia)*SPEED;
        direction.y = (float) (direction.y/distancia)*SPEED;
        angle = (float) Math.atan2(direction.y, direction.x);
    }
    @Override
    public void update() {
        posSprite.x = (int) (posSprite.x+direction.x);
        posSprite.y = (int) (posSprite.y+direction.y);
        hitbox = new RectF(posSprite.x-150,posSprite.y-150,posSprite.x+150,posSprite.y+150);
        if(!gsv.isInsideMap(posSprite)){
            gsv.deleteDagger(this);
        }
    }

    @Override
    public void paint(Canvas canvas) {
        canvas.save();
        Point posicioLocal = gsv.getScreenCoordinates(posSprite);
        canvas.rotate(angle, posicioLocal.x, posicioLocal.y);
        canvas.drawBitmap(sprite,
                new Rect(0,0,sprite.getWidth(),sprite.getHeight()),
                new Rect(posicioLocal.x-sprite.getWidth(),posicioLocal.y-sprite.getHeight(),posicioLocal.x+sprite.getWidth(),posicioLocal.y+sprite.getHeight()),
                null);
        canvas.restore();

    }

    @Override
    public RectF getHitBox() {
        return hitbox;
    }

    public int getEscala() {
        return Escala;
    }


}
