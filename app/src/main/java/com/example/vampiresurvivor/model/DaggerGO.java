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
    private static final int SPEED = 10;
    private int Escala = 6;
    protected Point posSprite = new Point();
    private static Bitmap sprite;
    private float angle;
    private PointF direction;
    private RectF hitbox;

    public DaggerGO(GameSurfaceView gsv) {
        super(gsv);
        if (sprite == null) {
            Bitmap spriteA = BitmapFactory.decodeResource(gsv.getResources(), R.drawable.dagger);
            sprite = Bitmap.createScaledBitmap(spriteA, (int) (spriteA.getWidth() / getEscala()), (int) (spriteA.getHeight() / getEscala()), false);
        }
        posSprite.x = gsv.getPlayerPosition().x;
        posSprite.y = gsv.getPlayerPosition().y;
        int bats = gsv.getBats().size();
        //get a random number
        int bat = (int) (Math.random()*bats);
        Point desti = gsv.getBats().get(bat).getPosition();
        direction = new PointF(desti.x-posSprite.x,desti.y-posSprite.y);
        double distancia = Utils.getModule(direction);
        direction.x = (float) (direction.x/distancia);
        direction.y = (float) (direction.y/distancia);
        angle = (float) Math.atan2(direction.x, direction.y);
    }
    @Override
    public void update() {
        posSprite.x = (int) (posSprite.x+direction.x);
        posSprite.y = (int) (posSprite.y+direction.y);

    }

    @Override
    public void paint(Canvas canvas) {
        canvas.save();
        canvas.rotate(angle, posSprite.x, posSprite.y);
        canvas.drawBitmap(sprite,
                new Rect(0,0,sprite.getWidth(),sprite.getHeight()),
                new Rect(posSprite.x-sprite.getWidth(),posSprite.y-sprite.getHeight(),posSprite.x+sprite.getWidth(),posSprite.y+sprite.getHeight()),
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
