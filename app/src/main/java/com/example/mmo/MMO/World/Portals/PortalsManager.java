package com.example.mmo.MMO.World.Portals;

import android.graphics.Canvas;

import java.util.ArrayList;

public class PortalsManager {

    private ArrayList<Portal> portals;

    public PortalsManager(){
        portals = new ArrayList<>();
    }

    public void tick(){
        for(Portal p : portals)
            p.tick();
    }

    public void render(Canvas canvas){
        for(Portal p : portals)
            p.render(canvas);
    }

    public void addPortal(Portal p){
        portals.add(p);
    }
}
