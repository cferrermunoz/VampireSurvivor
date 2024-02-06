package com.example.vampiresurvivor.model;

import android.graphics.Point;

public class Utils {
    public static double getDistancia(Point a, Point b){
        return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }

    public static Point scale(Point a, double factor){
        return new Point((int)(a.x*factor),(int)(a.y*factor));
    }

    public static Point suma(Point punt, Point vector){
        return new Point(punt.x+vector.x,punt.y+vector.y);
    }
}
