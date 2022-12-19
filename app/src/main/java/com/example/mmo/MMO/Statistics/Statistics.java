package com.example.mmo.MMO.Statistics;

import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Player;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Armor.Armor;
import com.example.mmo.MMO.Items.Chestplate.Chestplate;
import com.example.mmo.MMO.Items.Helmet.Helmet;
import com.example.mmo.MMO.Items.Necklace.Necklace;
import com.example.mmo.MMO.Items.Orbs.Orb;
import com.example.mmo.MMO.Items.Shield.Shield;
import com.example.mmo.MMO.Items.Swords.Sword;
import com.example.mmo.R;

import java.util.Random;

public class Statistics {

    //static

    public static final int DAMAGE = 0,
                            CRITCHANCE = 1,
                            CRITDAMAGE = 2,
                            RANGE = 3,
                            HEALTH = 4,
                            LUCK = 5,
                            DEFENSE = 6,
                            HEALTHREGEN = 7,
                            HEALTHREGENTIME = 8,
                            SKILLDAMAGE = 9,
                            SKILLCOOLDOWN = 10;

    //percentage bonuses

    private float damagePercent,
            critDamagePercent,
            critChancePercent,
            rangePercent,
            healthPercent,
            luckPercent,
            defensePercent,
            healthRegenPercent,
            healthRegenTimePercent,
            skillDamagePercent,
            skillCooldownPercent;


    //

    private final Handler handler;

    //statistic

    private float damage,
                    critDamage,
                    critChance,
                    range,
                    health,
                    luck,
                    defense,
                    healthRegen,
                    healthRegenTime,
                    skillDamage,
                    skillCooldown;

    private Random random;

    public Statistics(Handler handler){
        this.handler = handler;
        handler.setStatistics(this);

        random = new Random();
    }

    public String getName(int ID){
        switch (ID){
            case DAMAGE : return "Damage";
            case CRITCHANCE : return  "Crit Chance";
            case CRITDAMAGE: return "Crit Damage";
            case RANGE: return "Range";
            case HEALTH:return "Health";
            case LUCK:return "Luck";
            case DEFENSE:return "Defence";
            case HEALTHREGEN:return "Health Regen";
            case HEALTHREGENTIME:return "Regen Time";
            case SKILLDAMAGE:return "Skill Damage";
            case SKILLCOOLDOWN:return "Skill Cooldown";
            default:return "NULL";
        }
    }

    public void refresh(){
        damage = 1;
        critDamage = 2;
        critChance = 0;
        range = Player.BASICRANGE;
        health = Player.BASICHEALTH;
        luck = 0;
        defense = 0;
        healthRegen = Player.BASICHEALING;
        healthRegenTime = Player.BASEHEALDELAY;
        skillDamage = 1;
        skillCooldown = 1;

        for(int i = 0; i < handler.getEq().getEquipment().getHeight(); i++){
            if(handler.getEq().getEquipment().getItems()[0][i] == null)
                continue;

            Armor a = (Armor) handler.getEq().getEquipment().getItems()[0][i].getItem();
            ContainerItem c = handler.getEq().getEquipment().getItems()[0][i];

            for(int j = 0; j < a.getNumberOfAtributes(); j++){
                addBonus(a.getAtributesIDs()[j], a.getAtribute(c.getLvl(), j));
            }

            if(c.getBonuses() != null){
                damagePercent = 0;
                critDamagePercent = 0;
                critChancePercent = 0;
                rangePercent = 0;
                healthPercent = 0;
                luckPercent = 0;
                defensePercent = 0;
                healthRegenPercent = 0;
                healthRegenTimePercent = 0;
                skillDamagePercent = 0;
                skillCooldownPercent = 0;

                for(ItemBonus bonus : c.getBonuses()){
                    addPercentageBonus(bonus.getID(), bonus.getValue());
                }

                damage += damage * damagePercent / 100;
                critChance += critChance * critChancePercent / 100;
                critDamage += critDamage * critDamagePercent / 100;
                range += range * rangePercent / 100;
                health += health * healthPercent / 100;
                luck += luck * luckPercent / 100;
                defense += defense * defensePercent / 100;
                healthRegen += healthRegen * healthRegenPercent / 100;
                healthRegenTime -= healthRegenTime * healthRegenTimePercent / 100;
                skillDamage += skillDamagePercent / 100;
                skillCooldown -= skillCooldownPercent / 100;
            }
        }

        //time bonuses
        damage += handler.getTimeBonusesManager().getDamage();
        critDamage += handler.getTimeBonusesManager().getCritDamage();
        critChance += handler.getTimeBonusesManager().getCritChance();
        range += handler.getTimeBonusesManager().getRange();
        health += handler.getTimeBonusesManager().getHealth();
        luck += handler.getTimeBonusesManager().getLuck();
        defense += handler.getTimeBonusesManager().getDefence();
        healthRegen += handler.getTimeBonusesManager().getHealthRegen();
        healthRegenTime += handler.getTimeBonusesManager().getHealthRegenTime();

        handler.getEntityManager().getPlayer().setMaxHealth((int) health);

        Log.println(Log.ASSERT, "-------", "--------------------");
        Log.println(Log.ASSERT, "Damage:", damage + "");
        Log.println(Log.ASSERT, "Crit Chance:", critChance + "");
        Log.println(Log.ASSERT, "Crit Damage:", critDamage + "");
        Log.println(Log.ASSERT, "range:", range + "");
        Log.println(Log.ASSERT, "health:", health + "");
        Log.println(Log.ASSERT, "luck:", luck + "");
        Log.println(Log.ASSERT, "defence:", defense + "");
        Log.println(Log.ASSERT, "healthRegen:", healthRegen + "");
        Log.println(Log.ASSERT, "healthRegenTime:", healthRegenTime + "");
        Log.println(Log.ASSERT, "Skill damage:", skillDamage + "");
        Log.println(Log.ASSERT, "Skill cooldown:", skillCooldown + "");
        Log.println(Log.ASSERT, "-------", "--------------------");
    }

    private void addPercentageBonus(int ID, float value){
        switch (ID){
            case DAMAGE:{ damagePercent += value;
                break;}
            case CRITCHANCE:{ critChancePercent += value;
                break;}
            case CRITDAMAGE:{ critDamagePercent += value;
                break;}
            case RANGE:{ rangePercent += value;
                break;}
            case HEALTH:{ healthPercent += value;
                break;}
            case LUCK:{ luckPercent += value;
                break;}
            case DEFENSE:{ defensePercent += value;
                break;}
            case HEALTHREGEN:{ healthRegenPercent += value;
                break;}
            case HEALTHREGENTIME:{ healthRegenTimePercent += value;
                break;
            }
            case SKILLDAMAGE:{
                skillDamagePercent += value;
                break;
            }
            case SKILLCOOLDOWN:{
                skillCooldownPercent += value;
            }
        }
    }

    private void addBonus(int ID, float value){
        switch (ID){
            case DAMAGE:{ damage += value;
                break;}
            case CRITCHANCE:{ critChance += value;
                break;}
            case CRITDAMAGE:{ critDamage += value;
                break;}
            case RANGE:{ range += value;
                break;}
            case HEALTH:{ health += value;
                break;}
            case LUCK:{ luck += value;
                break;}
            case DEFENSE:{ defense += value;
                break;}
            case HEALTHREGEN:{ healthRegen += value;
                break;}
            case HEALTHREGENTIME:{ healthRegenTime += value;
                break; }
            case SKILLDAMAGE:{
                skillDamage += value / 100;
            }
        }
    }

    public ItemBonus getRandomBonus(){
        Random r = new Random();

        int ID = r.nextInt(11);
        float value = r.nextInt(15) + 1;
        return new ItemBonus(ID, value);
    }

    //getters setters


    public float getDamage() {
        if(random.nextInt(100) < critChance){
            return critDamage;
        }else{
            return damage;
        }
    }

    public float getCritDamage() {
        return critDamage;
    }

    public float getRange() {
        return range;
    }

    public float getHealth() {
        return health;
    }

    public float getLuck() {
        return luck;
    }

    public float getDefense() {
        return defense;
    }

    public float getHealthRegen() {
        return healthRegen;
    }

    public float getHealthRegenTime() {
        return healthRegenTime;
    }

    public float getSkillDamage() {
        return skillDamage;
    }

    public float getSkillCooldown() {
        return skillCooldown;
    }
}
