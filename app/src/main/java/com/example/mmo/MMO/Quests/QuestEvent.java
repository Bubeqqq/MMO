package com.example.mmo.MMO.Quests;

public class QuestEvent {

    //static

    public static int MOBKILLED = 0, ATTACK = 1, BUYITEM = 2, DAMAGEDEALT = 3, TALKTONPC = 4, UPGRADEITEM = 5, TRANSFORMITEM = 6, KILLBOSS = 7;

    //

    public int type, info, amount;

    public QuestEvent(int type, int info, int amount){
        this.type = type;
        this.info = info;
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public int getInfo() {
        return info;
    }

    public int getAmount() {
        return amount;
    }
}
