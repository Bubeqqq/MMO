package com.example.mmo.MMO.Quests;

import android.util.Log;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class QuestLine {

    private int index = 0, npcID;

    private int[] questIndex;

    private Quest currentQuest;

    private Handler handler;

    private String questDescription;

    private boolean npcShowedText = false, repetitive;

    private int ID;

    public QuestLine(int[] questIndexes, Handler handler, int ID, boolean repetitive){
        this.questIndex = questIndexes;
        this.handler = handler;
        this.ID = ID;
        this.repetitive = repetitive;
    }

    public void loadQuest(boolean next, boolean instant) throws IOException {
        if(npcShowedText &&  !instant)
            return;

        if(next)
            index++;
        Log.println(Log.ASSERT, "QuestLine", "load quest");
        if(index >= questIndex.length){
            if(repetitive)
                index = 0;
            else
                return;
        }

        Log.println(Log.ASSERT, "QuestLine", index + "");

        InputStream stream = handler.getGame().getContext().getAssets().open("Quests/Quest" + questIndex[index] + ".txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        ArrayList<String> pages = new ArrayList<>();

        int i = 0, npcPages = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while(reader.ready()){
            if(i == 0){
                npcPages = Integer.parseInt(reader.readLine());
                i++;
                continue;
            }

            if(i <= npcPages){
                pages.add(reader.readLine());
            }else if(i == npcPages + 1){
                questDescription = reader.readLine();
            }else{
                stringBuilder.append(reader.readLine()).append(" ");
            }
            i++;
        }

        NpcQuestPattern pattern = new NpcQuestPattern(this);

        String[] properties = stringBuilder.toString().split(" ");
        int instantQuest = Integer.parseInt(properties[0]);
        npcID = Integer.parseInt(properties[1]);

        if(instantQuest == 0)
            for(String s : pages) //add pages to npcs
                pattern.addPanel(s);

        int type = Integer.parseInt(properties[2]);
        int info = Integer.parseInt(properties[3]);
        int toComplete = Integer.parseInt(properties[4]);
        int texture = Integer.parseInt(properties[5]);
        String name = properties[6];
        int rewardCount = Integer.parseInt(properties[7]);

        Quest quest = new Quest(type, info, toComplete, Assets.items[texture], handler, this);
        quest.setName(name);
        quest.setDescription(questDescription);

        int bonus= 0;
        for(int j = 8; j < rewardCount * 4 + 8 + bonus; j += 4){ //add reward
            int bonusesCount = Integer.parseInt(properties[j + 3]);
            ArrayList<ItemBonus> bonuses = null;

            if(bonusesCount != 0){ //add bonuses to reward
                bonuses = new ArrayList<>();
                for(int b = j + 4; b < bonusesCount * 2 + j + 4; b += 2){
                    bonuses.add(new ItemBonus(Integer.parseInt(properties[b]), Integer.parseInt(properties[b + 1])));
                }
            }

            quest.addReward(Integer.parseInt(properties[j]),
                    Integer.parseInt(properties[j + 1]),
                    Integer.parseInt(properties[j + 2]),
                    bonuses);
            j += bonusesCount * 2;
            bonus += bonusesCount * 2;
        }

        pattern.setQuest(quest);

        currentQuest = quest;

        if(instant){
            handler.getQuestManager().addQuest(quest);
            return;
        }

        if(instantQuest == 0)
            handler.getQuestManager().addQuestToNPC(npcID, pattern);
        else{
            npcShowedText = true;
            handler.getQuestManager().addQuest(quest);
        }
    }

    public void setNpcShowedText(boolean npcShowedText) {
        this.npcShowedText = npcShowedText;
    }

    public int getID() {
        return ID;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isNpcShowedText() {
        return npcShowedText;
    }

    public int getIndex() {
        return index;
    }

    public Quest getCurrentQuest() {
        return currentQuest;
    }
}
