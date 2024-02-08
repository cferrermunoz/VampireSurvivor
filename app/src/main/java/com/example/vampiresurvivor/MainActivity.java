package com.example.vampiresurvivor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vampiresurvivor.model.GameSurfaceView;
import com.example.vampiresurvivor.model.JoyStickView;
import com.example.vampiresurvivor.model.MapGenerator;

public class MainActivity extends AppCompatActivity implements JoyStickView.JoyStickListener {

    private JoyStickView joystick;
    private GameSurfaceView gsv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joystick = findViewById(R.id.joystick);
        //txvCoord = findViewById(R.id.textView);
        gsv = findViewById(R.id.gsv);
        gsv.setJoystick(joystick);
        //joystick.addListener(this)

    }

    @Override
    public void onJoystickMoved(PointF speed) {
        Log.d("XXX", ">"+speed.x + " " + speed.y);
    }
}