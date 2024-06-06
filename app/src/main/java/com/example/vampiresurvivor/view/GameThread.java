package com.example.vampiresurvivor.view;
import android.graphics.Canvas;
import android.util.Log;

public class GameThread extends Thread{
    GameSurfaceView gsv;
    private boolean isGameOver = false;

    private static final long TIME_TO_SLEEP = 1000000000/60;

    public GameThread(GameSurfaceView pGsv){
        this.gsv=pGsv;
    }

    public void run(){
        super.run();
        while(!isGameOver){
            long startTime = System.nanoTime();
            Canvas canvas = gsv.getHolder().lockCanvas();
            if(canvas!=null){
                gsv.paint(canvas);
                gsv.getHolder().unlockCanvasAndPost(canvas);
            }
            gsv.update();
            long afterTime = System.nanoTime();
            long remainingTime =TIME_TO_SLEEP - (afterTime - startTime);
            if (remainingTime > 0){
                try {
                    Thread.sleep(remainingTime / 1000000);
                } catch(InterruptedException e) {
                    Log.e("Error", "He petat molt fort");
                }
            }
        }
    }

    public void gameOver(){
        isGameOver = true;
    }
}
