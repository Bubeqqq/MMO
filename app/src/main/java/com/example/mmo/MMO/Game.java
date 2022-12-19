package com.example.mmo.MMO;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Skills.Explosion;
import com.example.mmo.MMO.States.GameState;
import com.example.mmo.MMO.States.State;
import com.example.mmo.MainActivity;
import com.example.mmo.R;

public class Game extends SurfaceView implements SurfaceHolder.Callback{

    private GameLoop gameLoop;

    private Handler handler;

    private MainActivity mainActivity;

    private MotionEvent event;

    //States

    private State gameState;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Game(Context context, MainActivity mainActivity){
        super(context);

        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        this.mainActivity = mainActivity;

        gameLoop = new GameLoop(this, surfaceHolder);
        gameLoop.startLoop();

        handler = new Handler(this);

        new Assets(handler);

        gameState = new GameState(handler);

        State.setCurrentState(gameState);

        setFocusable(true);
    }

    public void touch(MotionEvent event){
        this.event = event;
    }

    public void tick(){
        if(!(State.getCurrentState() == null)){
            if(event != null){
                State.getCurrentState().Touch(event);
                if(event.getAction() == MotionEvent.ACTION_UP)
                    event = null;
            }
            State.getCurrentState().tick();
        }
    }

    public void stop(){
        Log.println(Log.ASSERT, "Game", "Stop");
        State.getCurrentState().stop();
        gameLoop.stopLoop();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameLoop.getThread().getState().equals(Thread.State.TERMINATED)) {
            SurfaceHolder surface = getHolder();
            surface.addCallback(this);
            gameLoop = new GameLoop(this, surface);
            gameLoop.startLoop();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        try{
            super.draw(canvas);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        if(State.getCurrentState() != null && canvas != null){
            State.getCurrentState().render(canvas);
            Paint p = new Paint();
            p.setTextSize(40);
            p.setColor(handler.getGame().getResources().getColor(R.color.yellow));
            canvas.drawText("FPS : " + GameLoop.fps, 100, 500, p);
        }
    }

    //getters
    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
