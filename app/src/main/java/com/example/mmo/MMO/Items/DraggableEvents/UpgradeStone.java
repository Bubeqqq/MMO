package com.example.mmo.MMO.Items.DraggableEvents;

import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public class UpgradeStone extends DraggableEvents {

    public UpgradeStone(Handler handler) {
        super(86, handler);

        haveDraggableEvent = true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nDrag to any item to\nupgrade it";
    }

    @Override
    public String getName() {
        return "Upgrade Stone";
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        Log.println(Log.ASSERT, "UpgradeStone", "Used");

        if(!draggedTo.getItem().haveUpgrades())
            return false;

        if(draggedTo.getLvl() >= 10)
            return false;

        handler.getEq().setUpgrade(true, draggedTo, 86, false, 0);

        return false;
    }
}
