package com.example.vampiresurvivor.model;

import android.graphics.PointF;

import com.example.vampiresurvivor.view.GameSurfaceView;

public class VampireGO extends SpriteGO{


    public VampireGO(GameSurfaceView gsv) {
        super(gsv);
    }

    @Override
    public float getEscala() {
        return 0;
    }

    @Override
    public PointF getDirection() {
        return null;
    }
}
