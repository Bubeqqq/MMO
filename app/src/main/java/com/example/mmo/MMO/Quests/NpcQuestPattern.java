package com.example.mmo.MMO.Quests;

import java.util.ArrayList;

public class NpcQuestPattern {

    private final ArrayList<char[]> text;

    private QuestLine questLine;

    private Quest quest;

    public NpcQuestPattern(QuestLine questLine){
        text = new ArrayList<>();
        this.questLine = questLine;
    }

    public void addPanel(String text){
        this.text.add(text.toCharArray());
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public ArrayList<char[]> getText() {
        return text;
    }

    public QuestLine getQuestLine() {
        return questLine;
    }
}
