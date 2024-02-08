package com.example.vampiresurvivor.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.vampiresurvivor.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MapGenerator {
    private Bitmap map;
    private Bitmap tiles;
    private Bitmap scenario;
    private static final int TILE_SIZE = 64;
    private static final int SCREEN_TILE_SIZE = TILE_SIZE*4;
    public MapGenerator(Resources r, Context con) {
        File folder = con.getFilesDir();
        File scenarioFile = new File(folder, "scenario.png");
        if (scenarioFile.exists()){
            scenario = BitmapFactory.decodeFile(scenarioFile.getAbsolutePath());
        } else {
            map = BitmapFactory.decodeResource(r, R.drawable.map_mini);
            tiles = BitmapFactory.decodeResource(r, R.drawable.terrain);
            scenario = Bitmap.createBitmap(map.getWidth()*SCREEN_TILE_SIZE,
                    map.getHeight()*SCREEN_TILE_SIZE,
                    Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(scenario);
            Map<Color, Point> mapping = new HashMap<>();
            mapping.put(Color.valueOf(0,0,0), new Point(0,0));
            mapping.put(Color.valueOf(0,0,1), new Point(192,256));
            mapping.put(Color.valueOf(0,1,0), new Point(320,192));
            mapping.put(Color.valueOf(1,0,0), new Point(0,64));
            for(int i = 0; i < map.getWidth(); i++) {
                for(int j = 0; j < map.getHeight(); j++) {
                    Color c = map.getColor(i,j);
                    Point p = mapping.get(c);
                    if (p==null){
                        p = mapping.get(Color.valueOf(0,0,0));
                    }
                    //Log.d("XXX", "Color: "+c.toString()+" Point: "+p.toString());
                    Rect rectTile = new Rect(p.x, p.y, p.x+TILE_SIZE, p.y+TILE_SIZE);
                    Rect rectScenario = new Rect(i*SCREEN_TILE_SIZE, j*SCREEN_TILE_SIZE, (i+1)*SCREEN_TILE_SIZE, (i+1)*SCREEN_TILE_SIZE);
                    canvas.drawBitmap(tiles, rectTile, rectScenario, null);

                    //desar escenari

                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(scenarioFile);
                scenario.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e){
                Log.d("Error", "Error png");
            }
        }

    }

    public Bitmap getScenario() {
        return scenario;
    }
}