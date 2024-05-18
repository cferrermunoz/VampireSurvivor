package com.example.vampiresurvivor.model;

import static androidx.core.math.MathUtils.clamp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;

public class CharacterGO extends SpriteGO {

    private static final int SPEED = 10;
    private int spritePjCurrentFrame;
    private boolean isMoving;
    private int life = 5;
    private int count = 0;
    private Boolean garlic = false;
    private Paint pCircle;
    private Paint pLife;
    private int sec_garlic = 0;

    private int radi_garlic = 500;


    @Override
    public float getEscala() {
        return 4;
    }

    @Override
    public PointF getDirection() {
        return gsv.getJoystick().getSpeed();
    }

    public CharacterGO(GameSurfaceView gsv) {
        super(gsv);
        sprites.put("idle", new SpriteInfo(R.drawable.player_sprite_idle, 1));
        sprites.put("walk", new SpriteInfo(R.drawable.player_sprite_walk, 3));
        sprites.put("run", new SpriteInfo(R.drawable.amongus_sprites, 5));
        setState("idle");
        pCircle = new Paint();
        pCircle.setColor(Color.RED);
        pCircle.setStrokeWidth(5);
        pCircle.setStyle(Paint.Style.STROKE);
        pLife = new Paint();
        pLife.setColor(Color.GREEN);
        pLife.setStyle(Paint.Style.FILL);
    }


    @Override
    public void update() {
        super.update();

        if (count > 0) {
            count--;
        }
        if (garlic){
            if (sec_garlic > 0) {
                sec_garlic--;
            } else if (sec_garlic == 0){
                garlic = false;
            }
        }


        for (BatGO go : gsv.getBats()) {
            if (garlic && Utils.getDistancia(posSprite, go.getPosition())<radi_garlic){
                gsv.deleteBat(go);
            }
            if (RectF.intersects(go.getHitBox(), getHitBox())) {
                if (count == 0) {
                    life--;
                    if (life == 0) {
                        Log.i("Vida", "Has muerto");
                    } else {
                        count = 60;
                        Log.d("Vida", "Vida: " + life);
                    }
                }
            }
        }
        for (BigEnemy go : gsv.getVampires()) {
            if (garlic && Utils.getDistancia(posSprite, go.getPosition())<radi_garlic){
                go.setLife(1);
            }
            if (RectF.intersects(go.getHitBox(), getHitBox())) {
                if (count == 0) {
                    life = life - 5;
                    if (life == 0) {
                        Log.i("Vida", "Has muerto");
                    } else {
                        count = 60;
                        Log.d("Vida", "Vida: " + life);
                    }
                }
            }
        }

        for (GarlicGO go : gsv.getGarlics()) {
            if (RectF.intersects(go.getHitBox(), getHitBox())) {
                garlic = true;
                gsv.deleteGarlic(go);
                sec_garlic = 60;
            }
        }

        for (LifeGO go : gsv.getLifes()) {
            if (RectF.intersects(go.getHitBox(), getHitBox())) {
                life = (int) (life * 1.5);
                gsv.deleteLife(go);
            }
        }


        if (gsv.getJoystick() != null) {
            PointF dir = gsv.getJoystick().getSpeed();
            //mirar si està dins del mapa
            Point newPos = new Point((int) (posSprite.x + dir.x * SPEED), (int) (posSprite.y + dir.y * SPEED));
            if (gsv.isInsideMap(newPos) && gsv.isWalkable(newPos)) {
                //mirar si la nova posició la permet el terreny
                posSprite.x += dir.x * SPEED;
                posSprite.y += dir.y * SPEED;
            } else {
                //si no està dins del mapa, mirar si està a la vora
                Point newPosX = new Point((int) (posSprite.x + dir.x * SPEED), posSprite.y);
                Point newPosY = new Point(posSprite.x, (int) (posSprite.y + dir.y * SPEED));
                if (gsv.isInsideMap(newPosX) && gsv.isWalkable(newPosX)) {
                    posSprite.x += dir.x * SPEED;
                }
                if (gsv.isInsideMap(newPosY) && gsv.isWalkable(newPosY)) {
                    posSprite.y += dir.y * SPEED;
                }
            }

            SpriteInfo s = getSpriteInfo();

            boolean isMovingNext = Math.abs(dir.x) > 0.001 || Math.abs(dir.y) > 0.001;

            if (isMovingNext) {
                if (Utils.getModule(dir) >= 0.75d) {
                    setState("run");
                } else {
                    setState("walk");
                }
            } else {
                setState("idle");
            }

            isMoving = isMovingNext;
            s.nextFrame();

            if (isMoving) {
                spritePjCurrentFrame++;
                if (spritePjCurrentFrame > s.size) {
                    spritePjCurrentFrame = 2;
                }
            } else {
                spritePjCurrentFrame = 1;
            }
        }
    }

    @Override
    public void paint(Canvas canvas) {
        if (life >= 0) {
            super.paint(canvas);
            Point p = gsv.getScreenCoordinates(posSprite);
            RectF Rectlife = new RectF(p.x + (float) sprites.get("idle").w * 2 , p.y + 100, p.x - (float) sprites.get("idle").w * 2 , p.y + 120);
            canvas.drawRect(Rectlife, pLife);
            if (garlic) {
                canvas.drawCircle(p.x, p.y, radi_garlic, pCircle);
            }
        }
    }
}
