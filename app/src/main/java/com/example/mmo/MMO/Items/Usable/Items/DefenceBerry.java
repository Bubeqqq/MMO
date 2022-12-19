package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Items.Usable.UsableDelay;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class DefenceBerry extends Usable {

    private UsableDelay usableDelay;

    public DefenceBerry(Handler handler) {
        super(66, handler);

        oneTimeUsable = true;

        usableDelay = new UsableDelay(80, handler);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() +"\nGrands 20% Defence\nEffect time : 60s\nDelay : 80s";
    }

    @Override
    public String getName() {
        return "Defence Berry";
    }

    @Override
    public void use(int lvl) {
        handler.getTimeBonusesManager().addBonus(TimeBonuses.DEFENCE, 60, (int) (handler.getStatistics().getDefense() * 0.2f));
        usableDelay.resetReady();
    }

    @Override
    public boolean canUse() {
        return usableDelay.isReady();
    }
}
