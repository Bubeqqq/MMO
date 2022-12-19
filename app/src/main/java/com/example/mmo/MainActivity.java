package com.example.mmo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Game;

public class MainActivity extends AppCompatActivity {

    private Game game;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        game = new Game(this, this);
        setContentView(game);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game.touch(event);
        return true;
    }

    @Override
        protected void onPause() {
        super.onPause();
        game.stop();
        Log.println(Log.ASSERT, "Main", "App closed");
    }
}