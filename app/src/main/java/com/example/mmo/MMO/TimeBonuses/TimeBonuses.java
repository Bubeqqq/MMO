package com.example.mmo.MMO.TimeBonuses;

public class TimeBonuses {

    public static final int DAMAGE = 0,
            CRITDAMAGE = 1,
            CRITCHANCE = 2,
            RANGE = 3,
            HEALTH = 4,
            LUCK = 5,
            DEFENCE = 6,
            HEALTHREGEN = 7,
            HEALTHREGENTIME = 8;

    private long lastTime, timer;

    private int time, ID, value;


    public TimeBonuses(int time, int ID, int value){
        this.ID = ID;
        this.time = time * 1000;
        this.value = value;

        lastTime = System.currentTimeMillis();
    }

    public boolean tick(){ //return true when effect ends
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();

        return timer > time;
    }

    //getters


    public int getTime() {
        return time;
    }

    public int getID() {
        return ID;
    }

    public int getValue() {
        return value;
    }
}
