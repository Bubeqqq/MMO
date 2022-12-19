package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Items.Usable.UsableDelay;
import com.example.mmo.MMO.TimeBonuses.TimeBonuses;

public class LuckBerry extends Usable {

    private UsableDelay usableDelay;

    public LuckBerry(Handler handler) {
        super(65, handler);

        oneTimeUsable = true;

        usableDelay = new UsableDelay(80, handler);
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() +"\nGrands 25% luck\nEffect time : 60s\nDelay : 80s";
    }

    @Override
    public String getName() {
        return "Luck Berry";
    }

    @Override
    public void use(int lvl) {
        handler.getTimeBonusesManager().addBonus(TimeBonuses.LUCK, 60, (int) (handler.getStatistics().getLuck() * 0.25f));
        usableDelay.resetReady();
    }

    @Override
    public boolean canUse() {
        return usableDelay.isReady();
    }
}
