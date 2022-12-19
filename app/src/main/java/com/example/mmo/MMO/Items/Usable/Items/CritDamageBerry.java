package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Items.Usable.UsableDelay;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class CritDamageBerry extends Usable {

    private UsableDelay usableDelay;

    public CritDamageBerry(Handler handler) {
        super(64, handler);

        oneTimeUsable = true;

        usableDelay = new UsableDelay(80, handler);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nGrands 30% damage\nboost\nEffect time : 60s\nDelay : 80s";
    }

    @Override
    public String getName() {
        return "CritDamage Berry";
    }

    @Override
    public void use(int lvl) {
        handler.getTimeBonusesManager().addBonus(TimeBonuses.CRITDAMAGE, 60, (int) (handler.getStatistics().getCritDamage() * 0.3f));
        usableDelay.resetReady();
    }

    @Override
    public boolean canUse() {
        return usableDelay.isReady();
    }
}
