package com.example.vampiresurvivor.view;

import static androidx.core.math.MathUtils.clamp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.vampiresurvivor.model.BatGO;
import com.example.vampiresurvivor.model.BigEnemy;
import com.example.vampiresurvivor.model.CharacterGO;
import com.example.vampiresurvivor.model.DaggerGO;
import com.example.vampiresurvivor.model.GameObject;
import com.example.vampiresurvivor.model.GarlicGO;
import com.example.vampiresurvivor.model.LifeGO;

import java.util.ArrayList;
import java.util.List;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    //private static final int speed = 10;
    private GameThread gameThread;
    private JoyStickView joystick;
    private CharacterGO player;
    private List<BatGO> bats = new ArrayList<>();
    private List<DaggerGO> daggers = new ArrayList<>();
    private List<BigEnemy> vampires = new ArrayList<>();
    private List<GarlicGO> garlics = new ArrayList<>();
    private List<LifeGO> lifes = new ArrayList<>();
    Paint paint = new Paint();
    Paint pBackground = new  Paint();
    private MapGenerator map;
    private int w,h;
    private int W,H;
    private int count = 0;

    public GameSurfaceView(Context context){
        super(context, null);
    }

    public GameSurfaceView(Context context, AttributeSet as){
        super(context, as);
        this.getHolder().addCallback(this);
        pintar();
        map = new MapGenerator(getResources(), this.getContext());
        W = map.getScenario().getWidth();
        H = map.getScenario().getHeight();

        player = new CharacterGO(this);
    }

    public void pintar(){
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        pBackground.setStyle(Paint.Style.FILL);
        pBackground.setColor(Color.WHITE);
    }

    public void setJoystick(JoyStickView joystick) {
        this.joystick = joystick;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        w = getWidth();
        h = getHeight();
        gameThread = new GameThread(this);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameThread.gameOver();
    }

    public void paint(Canvas canvas) {
        Point screenCorner = getScreenCornerCoordinates();
        canvas.drawBitmap(map.getScenario(),
                new Rect(screenCorner.x, screenCorner.y,
                        screenCorner.x+w, screenCorner.y+h),
                new Rect(0,0, w, h), null);
        for (int i = 0; i<bats.size();i++){
            bats.get(i).paint(canvas);
        }
        for (int i = 0; i<vampires.size();i++){
            vampires.get(i).paint(canvas);
        }
        for (int i = 0; i<daggers.size();i++){
            daggers.get(i).paint(canvas);
        }
        
        player.paint(canvas);
    }

    public void update() {
        for (int i = 0; i<bats.size();i++){
            bats.get(i).update();
        }
        for (int i = 0; i<vampires.size();i++){
            vampires.get(i).update();
        }
        for (int i = 0; i<daggers.size();i++){
            daggers.get(i).update();
        }

        player.update();

        count++;
        if (count%5000 == 0){
            vampires.add(new BigEnemy(this, getRandomPoint()));
        } else if (count%2500 == 0){
            bats.add(new BatGO(this, getRandomPoint()));
        } else if (count%500 == 0){
            if (vampires.size()>0 || bats.size()>0){
                daggers.add(new DaggerGO(this));
            }
        } else if (count%6000 == 0){
            lifes.add(new LifeGO(this, getRandomPoint()));
        } else if (count%10000 == 0){
            garlics.add(new GarlicGO(this, getRandomPoint()));
        }
    }

    public JoyStickView getJoystick() {
        return joystick;
    }

    public Point getScreenCoordinates(Point posicio){
        Point coordCorner = new Point();
        Point screenCorner = getScreenCornerCoordinates();
        coordCorner.x = posicio.x - screenCorner.x;
        coordCorner.y = posicio.y - screenCorner.y;
        return coordCorner;
    }

    private Point getScreenCornerCoordinates(){
        Point coordCorner = new Point();
        coordCorner.x = player.getPosition().x - w/2;
        coordCorner.y = player.getPosition().y - h/2;

        coordCorner.x = clamp(coordCorner.x, 0, W-w);
        coordCorner.y = clamp(coordCorner.y, 0, H-h);
        return coordCorner;
    }

    public Point getPlayerPosition(){
        return player.getPosition();
    }
    public List<BatGO> getBats() {
        return bats;
    }
    public List<BigEnemy> getVampires() {
        return vampires;
    }
    public List<DaggerGO> getDaggers() {
        return daggers;
    }

    public void deleteDagger(DaggerGO dagger){
        daggers.remove(dagger);
    }
    public void deleteBat(BatGO bat){
        bats.remove(bat);
    }
    public void deleteVampire(BigEnemy vampire){
        vampires.remove(vampire);
    }

    public Boolean isInsideMap(Point posicio){
        return posicio.x >= 0 && posicio.x <= W && posicio.y >= 0 && posicio.y <= H;
    }
    public Point getRandomPoint() {
        return new Point((int) (Math.random()*W), (int) (Math.random()*H));
    }
}
