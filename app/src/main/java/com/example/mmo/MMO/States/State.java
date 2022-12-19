package com.example.mmo.MMO.States;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.example.mmo.MMO.Handler;

public abstract class State {

    //static

    private static State currentState;

    public static State getCurrentState(){
        return currentState;
    }

    public static void setCurrentState(State state){
        currentState = state;
    }

    //abstract

    protected Handler handler;

    public State(Handler handler){
        this.handler = handler;
    }

    public abstract void tick();

    public abstract void render(Canvas canvas);

    public abstract void Touch(MotionEvent event);

    public abstract void stop();

}
