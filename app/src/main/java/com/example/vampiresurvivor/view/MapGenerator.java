package com.example.vampiresurvivor.view;

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
    private final Bitmap scenario;
    private static final int TILE_SIZE = 64;
    public MapGenerator(Resources r, Context con) {
        File folder = con.getFilesDir();
        File scenarioFile = new File(folder, "scenario.png");
        if (scenarioFile.exists()){
            scenario = BitmapFactory.decodeFile(scenarioFile.getAbsolutePath());
            this.map = BitmapFactory.decodeResource(r, R.drawable.map);
        } else {
            this.map = BitmapFactory.decodeResource(r, R.drawable.map);
            this.tiles = BitmapFactory.decodeResource(r, R.drawable.terrain);
            this.scenario = Bitmap.createBitmap(map.getWidth()*TILE_SIZE, map.getHeight()*TILE_SIZE,
                    Bitmap.Config.RGB_565);

            Canvas canvas = new Canvas(scenario);
            Map<Color, Point> mapping = new HashMap<>();
            mapping.put(Color.valueOf(0,0,0), new Point(0,0));
            mapping.put(Color.valueOf(0,0,1), new Point(192,256));
            mapping.put(Color.valueOf(0,1,0), new Point(320,192));
            mapping.put(Color.valueOf(1,0,0), new Point(0,64));

            for (int i = 0; i < map.getWidth(); i++) {
                for (int j = 0; j < map.getHeight(); j++) {
                    Color c = map.getColor(i,j);
                    //0: gespa
                    //1: blau  aigua (192,256)
                    //2: verd: arbres (320, 192)
                    //3: vermell: roca (0,64)
                    Point p = mapping.get(c);
                    if (p==null){
                        p = mapping.get(Color.valueOf(0,0,0));
                    }
                    Rect rectTile = new Rect(p.x, p.y, p.x + TILE_SIZE, p.y + TILE_SIZE);
                    Rect rectScenario = new Rect(i*TILE_SIZE, j*TILE_SIZE,
                            (i+1)*TILE_SIZE, (j+1)*TILE_SIZE);
                    canvas.drawBitmap(tiles, rectTile, rectScenario, null);
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

    //null pointer exception
    public Bitmap getMap() {
        return map;
    }

    public static int getTileSize() {
        return TILE_SIZE;
    }
}