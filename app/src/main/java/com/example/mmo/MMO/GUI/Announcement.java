package com.example.mmo.MMO.GUI;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

import java.util.ArrayList;

public class Announcement implements Runnable{

    private final short SHOW = 0, STAY = 1, HIDE = 2, stayTime = 1500, speed = 1, pixelsPerTick = 2;

    private final int width = Container.slotSize * 4, height = Container.slotSize, y = Container.slotSize * 2;

    private long lastTime, timer;

    private ArrayList<String> announcements;

    private short state = SHOW;

    private int x;

    private Handler handler;

    private Paint paint;

    private boolean running = false;

    private Thread thread;

    public Announcement(Handler handler){
        announcements = new ArrayList<>();
        this.handler = handler;
        handler.setAnnouncement(this);
        x = handler.getWindowSize().x + width;

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(height * 0.4f);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void addAnnouncement(String s){
        announcements.add(s);
    }

    public void tick(){
        if(announcements.isEmpty())
            return;

        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(state == STAY){
            if(timer >= stayTime){
                timer = 0;
                state = HIDE;
            }
        }else {
            if(timer >= speed){
                timer = 0;

                if(state == HIDE){
                    x += pixelsPerTick;

                    if(x >= handler.getWindowSize().x){
                        announcements.remove(0);
                        state = SHOW;
                    }
                }else {
                    x -= pixelsPerTick;

                    if(x <= handler.getWindowSize().x - width)
                        state = STAY;
                }
            }
        }
    }

    public void render(Canvas canvas){
        if(announcements.isEmpty())
            return;

        ArrayList<String> announcements = new ArrayList<>(this.announcements);

        canvas.drawBitmap(Assets.emptyButton,
                null,
                new RectF(x,
                        y,
                        x + width,
                        y + height),
                null);

        canvas.drawText(announcements.get(0), x + width / 2, y + height * 0.6f, paint);
    }

    @Override
    public void run() {
        while(running){
            tick();
        }
    }

    public void start(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
