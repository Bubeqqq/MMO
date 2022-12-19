package com.example.mmo.MMO.Items.Usable;

import java.util.ArrayList;

public class UsableDelayManager {

    private ArrayList<UsableDelay> delays;

    public UsableDelayManager(){
        delays = new ArrayList<>();
    }

    public void tick(){
        for(UsableDelay d : delays){
            d.tick();
        }
    }

    public ArrayList<UsableDelay> getDelays() {
        return delays;
    }
}
