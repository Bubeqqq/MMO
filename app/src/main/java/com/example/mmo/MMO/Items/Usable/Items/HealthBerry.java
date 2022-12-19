package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Items.Usable.UsableDelay;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class HealthBerry extends Usable {

    private UsableDelay usableDelay;

    public HealthBerry(Handler handler) {
        super(67, handler);

        oneTimeUsable = true;

        usableDelay = new UsableDelay(80, handler);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() +"\nGrands 30% Health\nEffect time : 60s\nDelay : 80s";
    }

    @Override
    public String getName() {
        return "Health Berry";
    }

    @Override
    public void use(int lvl) {
        handler.getTimeBonusesManager().addBonus(TimeBonuses.HEALTH, 60, (int) (handler.getStatistics().getHealth() * 0.3f));
        usableDelay.resetReady();
    }

    @Override
    public boolean canUse() {
        return usableDelay.isReady();
    }
}
