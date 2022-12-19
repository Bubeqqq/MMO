package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class SmallPotion extends Usable {

    public SmallPotion(Handler handler) {
        super(78, handler);

        oneTimeUsable = true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        String s = "Regen 5% of total life";

        return s;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void use(int lvl) {
        handler.getEntityManager().getPlayer().addHealth((int) (handler.getStatistics().getHealth() * 0.05f));
    }

    @Override
    public boolean canUse() {
        if(handler.getEntityManager().getPlayer().getHealth() < handler.getStatistics().getHealth() * 0.95f){
            return true;
        }else
            return false;
    }
}
