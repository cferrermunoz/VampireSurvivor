package com.example.vampiresurvivor.model;

import static androidx.core.math.MathUtils.clamp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.Utils;

public class CharacterGO extends SpriteGO {
    /**
     * Velocitat del personatge
     */
    private static final int SPEED = 10;
    /**
     * Vida màxima del personatge
     */
    private int MAX_LIFE = 10;
    /**
     * Frame actual del personatge
     */
    private int spritePjCurrentFrame;
    /**
     * Indica si el personatge està en moviment
     */
    boolean isMoving;
    /**
     * Vida del personatge
     */
    private int life = 3;
    /**
     * Comptador per a la vida
     */
    private int count;
    /**
     * Indica si el personatge té all
     */
    private Boolean garlic = false;
    /**
     * Pintura del cercle de l'all
     */
    private final Paint pCircle;
    /**
     * Pintura de la vida
     */
    private final Paint pLife;
    /**
     * Pintura del dany
     */
    private final Paint pNoLife;
    /**
     * Comptador all
     */
    private int sec_garlic = 1200;
    /**
     * Radi de l'efecte de l'all
     */
    private final int radi_garlic = 500;

    private RectF RectLife;

    /**
     * Retorna l'escala
     *
     * @return float
     */
    @Override
    public float getEscala() {
        return 4;
    }

    /**
     * Retorna la direcció del personatge
     *
     * @return PointF
     */
    @Override
    public PointF getDirection() {
        return gsv.getJoystick().getSpeed();
    }

    /**
     * Constructor del personatge
     *
     * @param gsv GameSurfaceView
     */
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
        pNoLife = new Paint();
        pNoLife.setColor(Color.RED);
        pNoLife.setStyle(Paint.Style.FILL);
        this.count = 10;
    }

    /**
     * Actualitza el personatge
     */
    @Override
    public void update() {
        super.update();

        if (garlic) {
            if (sec_garlic > 0) {
                sec_garlic--;
            } else if (sec_garlic == 0) {
                garlic = false;
            }
        }

        if (count <= 0) {
            life--;
            count = 10;
            gsv.restart();
        }
        if (life == 0) {
            gsv.gameOver();
        }


        if (gsv.getJoystick() != null) {
            PointF dir = gsv.getJoystick().getSpeed();
            //mirar si està dins del mapa
            Point newPos = new Point((int) (posSprite.x + dir.x * SPEED), (int) (posSprite.y + dir.y * SPEED));
            if (gsv.isWalkable(newPos)) {
                //mirar si la nova posició la permet el terreny
                posSprite.x += dir.x * SPEED;
                posSprite.y += dir.y * SPEED;
            } else {
                //si no està dins del mapa, mirar si està a la vora
                Point newPosX = new Point((int) (posSprite.x + dir.x * SPEED), posSprite.y);
                Point newPosY = new Point(posSprite.x, (int) (posSprite.y + dir.y * SPEED));
                if (gsv.isWalkable(newPosX)) {
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
            //RectLife = new RectF(posSprite.x + (float) sprites.get("idle").w * 2 , posSprite.y + 100, (posSprite.x - (float) sprites.get("idle").w * 2) * count / MAX_LIFE, posSprite.y + 120);
        }
    }


    /**
     * Pinta el personatge
     *
     * @param canvas Canvas
     */
    @Override
    public void paint(Canvas canvas) {
        if (life >= 0) {
            super.paint(canvas);
            Point p = gsv.getScreenCoordinates(posSprite);
            float barWidth = (float) sprites.get("idle").w * 4; // Total width of the health bar
            float barHeight = 20; // Height of the health bar
            // Calculate the width of the green (health) part of the bar
            float greenBarWidth = barWidth * count / MAX_LIFE;
            // Define the red (empty) part of the bar (full width)
            RectF RectNoLife = new RectF(p.x - barWidth / 2, p.y + 100, p.x + barWidth / 2, p.y + 100 + barHeight);
            // Define the green (health) part of the bar (dynamic width)
            RectLife = new RectF(p.x - barWidth / 2, p.y + 100, p.x - barWidth / 2 + greenBarWidth, p.y + 100 + barHeight);
            canvas.drawRect(RectNoLife, pNoLife);
            canvas.drawRect(RectLife, pLife);
            if (garlic) {
                canvas.drawCircle(p.x, p.y, radi_garlic, pCircle);
            }
        }
    }

    public void restart() {
        posSprite = new Point(100, 100);
    }

    public void setLife(int i) {
        count = i;
    }

    public int getLife() {
        return life;
    }

    public int getCount() {
        return count;
    }

    public void setGarlic(boolean b) {
        garlic = b;
        this.sec_garlic = 1200;
    }

    public boolean getGarlic() {
        return garlic;
    }

    public double getRadi_garlic() {
        return radi_garlic;
    }

    public void setMAX_LIFE(double valor) {
        this.MAX_LIFE *= valor;
    }
}
