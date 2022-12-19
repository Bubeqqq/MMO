package com.example.mmo.MMO.TimeBonuses;

import android.util.Log;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.Statistics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class TimeBonusesManager {

    private ArrayList<TimeBonuses> bonuses;

    private Handler handler;

    public TimeBonusesManager(Handler handler){
        this.handler = handler;

        bonuses = new ArrayList<>();
        handler.setTimeBonusesManager(this);
    }

    public void tick(){
        for(TimeBonuses t : bonuses){
            if(t.tick()){
                bonuses.remove(t);
                Log.println(Log.ASSERT, "TimeBonuses", "Bonus Ended");
                handler.getStatistics().refresh();
                tick();
                return;
            }
        }
    }

    public void addBonus(int ID, int time, int value){
        bonuses.add(new TimeBonuses(time, ID, value));
        handler.getStatistics().refresh();
    }

    public int getDamage(){
        int damage = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.DAMAGE){
                    damage += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getDamage();
        }

        return damage;
    }

    public int getCritDamage(){
        int critDamage = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.CRITDAMAGE){
                    critDamage += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getCritDamage();
        }

        return critDamage;
    }

    public int getCritChance(){
        int critChance = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.CRITCHANCE){
                    critChance += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getCritChance();
        }

        return critChance;
    }

    public int getRange(){
        int range = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.RANGE){
                    range += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getRange();
        }

        return range;
    }

    public int getHealth(){
        int health = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.HEALTH){
                    health += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getHealth();
        }

        return health;
    }

    public int getLuck(){
        int luck = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.LUCK){
                    luck += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getLuck();
        }

        return luck;
    }

    public int getDefence(){
        int defence = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.DEFENCE){
                    defence += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getDefence();
        }

        return defence;
    }

    public int getHealthRegen(){
        int healthRegen = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.HEALTHREGEN){
                    healthRegen += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getHealthRegen();
        }

        return healthRegen;
    }

    public int getHealthRegenTime(){
        int healthRegenTime = 0;
        try{
            for(TimeBonuses b : bonuses){
                if(b.getID() == TimeBonuses.HEALTHREGENTIME){
                    healthRegenTime += b.getValue();
                }
            }
        }catch (ConcurrentModificationException e){
            return getHealthRegenTime();
        }

        return healthRegenTime;
    }
}
