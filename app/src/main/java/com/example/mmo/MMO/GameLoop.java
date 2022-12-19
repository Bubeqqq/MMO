package com.example.mmo.MMO;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


public class GameLoop implements Runnable{

    public static int fps = 0;

    private boolean running = false;

    private Thread thread;

    private Game game;

    private SurfaceHolder surfaceHolder;

    public GameLoop(Game game, SurfaceHolder surfaceHolder){
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run() {
        int fps = 30;
        float timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        long timer = 0;
        int ticks = 0;

        Canvas canvas = null;

        while(running){
                now = System.nanoTime();
                delta += (now - lastTime) / timePerTick;

                timer += now - lastTime;

                lastTime = now;


                if(delta >= 0) {
                    try {
                        canvas = surfaceHolder.lockCanvas();
                        synchronized (surfaceHolder) {
                            game.tick();
                            game.draw(canvas);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } finally {
                        if(canvas != null) {
                            try {
                                surfaceHolder.unlockCanvasAndPost(canvas);
                            } catch(Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    /*canvas = surfaceHolder.lockCanvas();
                    game.tick();
                    game.draw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);*/

                    ticks++;
                    delta--;
                }

            if(timer >= 1000000000)
            {
                this.fps = ticks;
                timer = 0;
                ticks = 0;
            }
        }
    }

    public void startLoop(){
        thread = new Thread(this);
        thread.setName("Game Loop");
        running = true;
        thread.start();
    }

    public void stopLoop(){
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Thread getThread() {
        return thread;
    }
}
