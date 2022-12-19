package com.example.mmo.MMO.Saving;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.WorldManager;
import com.example.mmo.R;

public class PlayerSave {

    private Handler handler;

    private final String PLAYER_X = "playerX", PLAYER_Y = "playerY", PLAYER_LVL = "playerLVL", PLAYER_EXP = "playerExp", WORLD_ID = "worldID";

    public PlayerSave(Handler handler){
        this.handler = handler;
    }

    public void savePlayer(){
        SharedPreferences sharedPref = handler.getGame().getContext().getSharedPreferences(handler.getGame().getResources().getString(R.string.preferences_file_key),
                Context.MODE_PRIVATE);

        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPref.edit();

        editor.putFloat(PLAYER_X, handler.getEntityManager().getPlayer().getX());
        editor.putFloat(PLAYER_Y, handler.getEntityManager().getPlayer().getY());
        editor.putInt(PLAYER_LVL, handler.getEntityManager().getPlayer().getLvl());
        editor.putInt(PLAYER_EXP, handler.getEntityManager().getPlayer().getXp());
        editor.putInt(WORLD_ID, handler.getWorld().getID());
        editor.apply();

        Log.println(Log.ASSERT, "Player save", "Player Saved");
    }

    public void loadPlayer(){
        SharedPreferences sharedPref = handler.getGame().getContext().getSharedPreferences(handler.getGame().getResources().getString(R.string.preferences_file_key),
                Context.MODE_PRIVATE);


        int worldID = sharedPref.getInt(WORLD_ID, 1);

        if(worldID == WorldManager.DUNGEONID){
            handler.getWorldManager().setCurrentWorld(1);
            handler.getEntityManager().getPlayer().setPosition(handler.getWorld().getSpawnX(), handler.getWorld().getSpawnY());
        }else {
            handler.getWorldManager().setCurrentWorld(worldID);
            handler.getEntityManager().getPlayer().setPosition(sharedPref.getFloat(PLAYER_X, handler.getWorld().getSpawnX()),
                    sharedPref.getFloat(PLAYER_Y, handler.getWorld().getSpawnY()));
        }

        handler.getEntityManager().getPlayer().setLvl(sharedPref.getInt(PLAYER_LVL, 1));

        handler.getEntityManager().getPlayer().addXP(sharedPref.getInt(PLAYER_EXP, 0));

        Log.println(Log.ASSERT, "Player save", "Player Loaded");
    }
}
