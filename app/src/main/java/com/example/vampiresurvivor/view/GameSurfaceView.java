package com.example.vampiresurvivor.view;

import static androidx.core.math.MathUtils.clamp;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import com.example.vampiresurvivor.MainActivity;
import com.example.vampiresurvivor.R;
import com.example.vampiresurvivor.model.BatGO;
import com.example.vampiresurvivor.model.BigEnemy;
import com.example.vampiresurvivor.model.CharacterGO;
import com.example.vampiresurvivor.model.DaggerGO;
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
    private MediaPlayer mP_bat;
    private MediaPlayer mP_dagger;

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
        mP_bat = MediaPlayer.create(context, R.raw.bat_sound);
        mP_dagger = MediaPlayer.create(context, R.raw.knife);
    }

    public void pintar(){
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(50);
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
        mP_bat.setDisplay(surfaceHolder);
        if (mP_bat != null && !mP_bat.isPlaying()) {
            mP_bat.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameThread.gameOver();
        if (mP_bat != null) {
            mP_bat.release();
            mP_bat = null;
        }
        if (mP_dagger != null) {
            mP_dagger.release();
            mP_dagger = null;
        }
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
        for (int i = 0; i<garlics.size();i++){
            garlics.get(i).paint(canvas);
        }
        for (int i = 0; i<lifes.size();i++){
            lifes.get(i).paint(canvas);
        }
        player.paint(canvas);
        canvas.drawText("Life: "+player.getLife(), 20, 45, paint);
        canvas.drawText("HP: "+player.getCount(), 20, 95, paint);
    }

    public void update() {
        for (int i = 0; i<bats.size(); i++){
            BatGO bgo = bats.get(i);
            bgo.update();
            if (RectF.intersects(bgo.getHitBox(), player.getHitBox())) {
                bats.remove(bgo);
                player.setLife(player.getCount()-1);
            }
            if (player.getGarlic() && Utils.getDistancia(player.getPosition(), bgo.getPosition())<player.getRadi_garlic()){
                bats.remove(bgo);
            }

        }
        for (int i = 0; i<vampires.size(); i++){
            BigEnemy be = vampires.get(i);
            be.update();
            if (RectF.intersects(be.getHitBox(), player.getHitBox())) {
                player.setLife(player.getCount()-3);
                vampires.remove(be);
            }
            if (player.getGarlic() && Utils.getDistancia(player.getPosition(), be.getPosition())<player.getRadi_garlic())
                be.setLife(1);
        }
        for (int i = 0; i<daggers.size(); i++){
            DaggerGO dgo = daggers.get(i);
            dgo.update();
            for (int j = 0; j<bats.size(); j++){
                BatGO bgo = bats.get(j);
                if (RectF.intersects(dgo.getHitBox(), bgo.getHitBox())) {
                    bats.remove(bgo);
                    daggers.remove(dgo);
                    Log.d("Intersection", "Bat");
                }
            }
            for (int j = 0; j<vampires.size(); j++){
                BigEnemy be = vampires.get(j);
                if (RectF.intersects(dgo.getHitBox(), be.getHitBox())) {
                    be.setLife(1);
                    daggers.remove(dgo);
                    Log.d("Intersection", "Vampire");
                }
            }

        }
        for (int i = 0; i<garlics.size(); i++){
            GarlicGO ggo = garlics.get(i);
            if (player.getHitBox() != null && ggo.getHitBox() != null){
                if (RectF.intersects(ggo.getHitBox(), player.getHitBox())) {
                    garlics.remove(ggo);
                    player.setGarlic(true);
                }
            }
        }
        for (int i = 0; i<lifes.size(); i++){
            LifeGO lgo = lifes.get(i);
            if (RectF.intersects(lgo.getHitBox(), player.getHitBox())) {
                player.setLife((int) (player.getCount() * 1.5));
                player.setMAX_LIFE(1.5);
                lifes.remove(lgo);
            }
        }

        player.update();
        count++;
        if (count%300 == 0){
            vampires.add(new BigEnemy(this, getRandomPoint()));
        } else if (count%150 == 0){
            bats.add(new BatGO(this, getRandomPoint()));
        } else if (count%200 == 0){
            if (!vampires.isEmpty() || !bats.isEmpty()){
                daggers.add(new DaggerGO(this));
                if (mP_dagger != null && !mP_dagger.isPlaying()) {
                    mP_dagger.start();
                }
            }
        } else if (count%10 == 0){
            lifes.add(new LifeGO(this, getRandomPoint()));
            Point pos = new Point((int) (Math.random()*W), (int) (Math.random()*H));
            Log.d("Garlic", pos.toString());
            garlics.add(new GarlicGO(this, pos));
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

    public void deleteDagger(DaggerGO dagger){
        daggers.remove(dagger);
    }
    public void deleteBat(BatGO bat){
        bats.remove(bat);
    }
    public void deleteVampire(BigEnemy vampire){
        vampires.remove(vampire);
    }

    /**
     * Check if the position is inside the map
     * @param posicio Point
     * @return Boolean true if the position is inside the map
     */
    public Boolean isInsideMap(Point posicio){
        return posicio.x >= 0 && posicio.x <= W && posicio.y >= 0 && posicio.y <= H;
    }
    /**
     * Gets a random point inside the screen
     * @return Point with the position inside the screen
     */
    public Point getRandomPoint() {
        return new Point((int) (Math.random()*W), (int) (Math.random()*H));
    }

    public boolean isWalkable(Point posicio){
        if (!isInsideMap(posicio)) return false;
        int y = posicio.y / MapGenerator.getTileSize();
        int x = posicio.x / MapGenerator.getTileSize();
        return map.getMap().getColor(x,y).equals(Color.valueOf(0,0,0));
    }


    public void restart() {
        player.restart();
        bats.clear();
        vampires.clear();
        daggers.clear();
        garlics.clear();
        lifes.clear();
    }

    public void gameOver() {
        gameThread.gameOver();
        MainActivity activity = (MainActivity) getContext();
        activity.startMenu();
    }
}
