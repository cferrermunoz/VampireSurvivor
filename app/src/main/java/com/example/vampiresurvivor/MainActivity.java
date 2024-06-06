package com.example.vampiresurvivor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import com.example.vampiresurvivor.view.GameSurfaceView;
import com.example.vampiresurvivor.view.JoyStickView;

public class MainActivity extends AppCompatActivity implements JoyStickView.JoyStickListener {
    private JoyStickView joystick;
    private GameSurfaceView gsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        joystick = findViewById(R.id.joystick);
        gsv = findViewById(R.id.gsv);
        gsv.setJoystick(joystick);
    }

    @Override
    public void onJoystickMoved(PointF speed) {

    }

    public void startMenu() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
        finish();
    }
}