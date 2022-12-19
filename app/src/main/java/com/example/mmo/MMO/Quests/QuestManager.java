package com.example.mmo.MMO.Quests;

import android.util.Log;

import com.example.mmo.MMO.Entity.Creatures.NPC.NPC;
import com.example.mmo.MMO.Handler;

import java.io.IOException;
import java.util.ArrayList;

public class QuestManager {

    private ArrayList<Quest> quests;
    private ArrayList<QuestLine> questsLines;

    private ArrayList<NPC> NPCs;

    private Handler handler;

    public QuestManager(Handler handler){
        handler.setQuestManager(this);
        this.handler = handler;

        quests = new ArrayList<>();
        questsLines = new ArrayList<>();
        NPCs = new ArrayList<>();

        addQuestLines();
    }

    public void event(QuestEvent event){
        ArrayList<Quest> quests = new ArrayList<>(this.quests);
        for(Quest q : quests){
            if(event.getType() == q.getType() && (q.getInfo() == -1 || q.getInfo() == event.getInfo())){
                q.addProgress(event.getAmount());
            }
        }
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
    }

    public void addQuest(Quest quest){
        quests.add(quest);
        handler.addAnnouncement("New Quest");
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public void addNPC(NPC npc){
        NPCs.add(npc);
    }


    public void addQuestToNPC(int ID, NpcQuestPattern pattern){
        for(NPC npc : NPCs){
            if(npc.getID() == ID){
                npc.addQuest(pattern);
            }
        }
    }

    public void loadLineQuests(){ //load quests to npc after load them
        for(NPC npc : NPCs){
            npc.clearQuests();
        }

        for(QuestLine q : questsLines){
            try {
                q.loadQuest(false, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addQuestLines(){
        questsLines.add(new QuestLine(new int[]{0, 1, 2}, handler, 0, true));
    }

    public ArrayList<QuestLine> getQuestsLines() {
        return questsLines;
    }
}
