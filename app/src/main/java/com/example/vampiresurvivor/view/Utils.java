package com.example.vampiresurvivor.view;

import android.graphics.Point;
import android.graphics.PointF;

public class Utils {
    /**
     * Retorna la distancia que hi ha entre dos punts
     * @param a Point
     * @param b Point
     * @return double distancia
     */
    public static double getDistancia(Point a, Point b){
        return Math.sqrt(Math.pow(a.x-b.x,2)+Math.pow(a.y-b.y,2));
    }

    /**
     * Retorna el vector escalat al factor
     * @param a Point vector
     * @param factor double factor
     * @return Point vector
     */
    public static Point scale(Point a, double factor){
        return new Point((int)(a.x*factor),(int)(a.y*factor));
    }

    /**
     * Retorna la posició resultant de sumar un vector a un punt
     * @param punt Point punt
     * @param vector Point vector
     * @return
     */
    public static Point suma(Point punt, Point vector){
        return new Point(punt.x+vector.x,punt.y+vector.y);
    }

    /**
     * Devuelve el modulo del vector
     * @param vector PointF vector
     * @return modulo
     */
    public static double getModule(PointF vector){
        return Math.sqrt(Math.pow(vector.x,2)+Math.pow(vector.y,2));
    }
}
