package com.example.mmo.MMO.Saving;

import android.os.AsyncTask;

import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveManager{

    private Handler handler;

    private EqSave eqSave;

    private QuestSave questSave;

    private PlayerSave playerSave;

    private long lastTime, timer;

    private boolean running = false;

    private Thread thread;

    //private int autoSave = 300000; // 5min
    private int autoSave = 30000; // 3s

    public SaveManager(Handler handler){
        this.handler = handler;

        eqSave = new EqSave(handler);

        questSave = new QuestSave(handler);

        playerSave = new PlayerSave(handler);
    }

    public void loadSave(){
        eqSave.loadEQ();
        questSave.loadQuests();
        playerSave.loadPlayer();
    }

    public void tick(){
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        if(timer >= autoSave){
            timer = 0;
            save();
        }
    }

    public void save(){
        eqSave.saveEQ(handler.getEq(), handler.getSkillManager().getSkillBar());
        questSave.saveQuests();
        playerSave.savePlayer();

        handler.addAnnouncement("Saved");
    }

    public void addBackgroundSave(){
        ExecutorService executor = Executors.newSingleThreadExecutor();

        EQ eq = handler.getEq();
        Container skillBar = handler.getSkillManager().getSkillBar();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                eqSave.saveEQ(eq, skillBar);
                /*questSave.saveQuests();
                playerSave.savePlayer();*/
            }
        });
    }
}
