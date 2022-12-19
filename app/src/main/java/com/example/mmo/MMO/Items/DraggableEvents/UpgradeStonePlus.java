package com.example.mmo.MMO.Items.DraggableEvents;

import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;

public class UpgradeStonePlus extends DraggableEvents{

    public UpgradeStonePlus(Handler handler) {
        super(82, handler);
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        Log.println(Log.ASSERT, "UpgradeStone plus", "Used");

        if(!draggedTo.getItem().haveUpgrades())
            return false;

        if(draggedTo.getLvl() >= 10) {
            return false;
        }

        handler.getEq().setUpgrade(true, draggedTo, 82, false, 10);

        return false;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nDrag to any item to\nupgrade it\nGives additional 10%\nchances to upgrade";
    }

    @Override
    public String getName() {
        return "Upgrade Stone+";
    }
}
