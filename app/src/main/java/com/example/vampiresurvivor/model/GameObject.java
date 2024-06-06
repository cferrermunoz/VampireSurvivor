package com.example.vampiresurvivor.model;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.vampiresurvivor.view.GameSurfaceView;

public abstract class GameObject {
    protected GameSurfaceView gsv;
    public GameObject(GameSurfaceView gsv){
        this.gsv = gsv;
    }
    public abstract void update();
    public abstract void paint(Canvas canvas);
    public abstract RectF getHitBox();

}
