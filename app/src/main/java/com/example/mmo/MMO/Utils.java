package com.example.mmo.MMO;

import android.graphics.Point;
import android.util.Log;

public class Utils {

    public static int getDistance(int x1, int y1, int x2, int y2){
        return (int) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
