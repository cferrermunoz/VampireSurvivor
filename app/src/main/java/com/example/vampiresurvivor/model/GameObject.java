package com.example.vampiresurvivor.model;

import android.graphics.Canvas;

public abstract class GameObject {
    protected GameSurfaceView gsv;
    public GameObject(GameSurfaceView gsv){
        this.gsv = gsv;
    }
    public abstract void update();
    public abstract void paint(Canvas canvas);
}
