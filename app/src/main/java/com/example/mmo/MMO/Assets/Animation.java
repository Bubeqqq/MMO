package com.example.mmo.MMO.Assets;

import android.graphics.Bitmap;

public class Animation {

    private int index, speed;
    private long lastTime, timer;
    private Bitmap[] frames;

    private int frameJump = 1;

    public Animation(Bitmap[] frames, int speed) {
        this.speed = speed;
        this.frames = frames;
        lastTime = System.currentTimeMillis();
        index = 0;
        timer = 0;
    }

    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer > speed) {
            index += frameJump;
            timer = 0;
            if(index >= frames.length)
                index = 0;
        }
    }

    public Bitmap GetCurrentFrame() {
        return frames[index];
    }

    public void setFrame(int i){
        index = i;
    }

    public int getIndex() {
        return index;
    }

    public void setFrameJump(int frameJump) {
        this.frameJump = frameJump;
    }
}
