package com.example.mmo.MMO.Quests;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.EntityManager;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.io.IOException;
import java.util.ArrayList;

public class Quest {

    private int progress;

    private final ArrayList<ContainerItem> reward;

    private final int type, toComplete, info;

    private final Handler handler;

    private final Bitmap texture;

    private String name = "quest", description;

    private QuestLine questLine;

    public Quest(int type, int info, int toComplete, Bitmap texture, Handler handler, QuestLine questLine){
        this.type = type;
        this.toComplete = toComplete;
        this.info = info;
        this.handler = handler;
        this.texture = texture;
        this.questLine = questLine;

        reward = new ArrayList<>(2);
    }

    public void addReward(int ID, int amount, int lvl, ArrayList<ItemBonus> bonuses){
        reward.add(new ContainerItem(ID, handler, amount, lvl, bonuses));
    }


    public void addProgress(int amount){
        progress += amount;

        Log.println(Log.ASSERT, "QUEST", "progress " + progress + " / " + toComplete);

        if(progress >= toComplete){
            handler.addAnnouncement("Quest Completed");

            for(ContainerItem c : reward)
                handler.getEq().getEq().addItem(c);

            handler.getQuestManager().removeQuest(this);
            try {
                questLine.setNpcShowedText(false);
                questLine.loadQuest(true, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.println(Log.ASSERT, "QUEST", "Quest Completed");
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getToComplete() {
        return toComplete;
    }

    public int getType() {
        return type;
    }

    public int getInfo() {
        return info;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ContainerItem> getReward() {
        return reward;
    }
}
