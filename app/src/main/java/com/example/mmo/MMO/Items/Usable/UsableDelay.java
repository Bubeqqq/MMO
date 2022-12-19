package com.example.mmo.MMO.Items.Usable;

import com.example.mmo.MMO.Handler;

public class UsableDelay {

    private int time;

    private boolean ready = true;

    private long lastTime, timer;

    public UsableDelay(int time, Handler handler){
        this.time = time * 1000;

        handler.getItemManager().getUsableDelayManager().getDelays().add(this);

        lastTime = System.currentTimeMillis();
    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > time) {
            ready = true;
            timer = 0;
        }
    }

    public void resetReady(){
        timer = 0;
        ready = false;
    }

    public boolean isReady() {
        return ready;
    }
}
