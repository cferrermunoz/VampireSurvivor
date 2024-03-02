package com.example.vampiresurvivor.model;

import static androidx.core.math.MathUtils.clamp;

import android.graphics.Canvas;
import android.graphics.PointF;
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
    }


    @Override
    public void update() {
        super.update();

        if (count > 0) {
            count--;
        }

        for (GameObject go : gsv.getBats()) {
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

        if (gsv.getJoystick() != null) {
            PointF dir = gsv.getJoystick().getSpeed();
            posSprite.x += dir.x * SPEED;
            posSprite.y += dir.y * SPEED;

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
        if (life >= 0)
            super.paint(canvas);
    }

}
