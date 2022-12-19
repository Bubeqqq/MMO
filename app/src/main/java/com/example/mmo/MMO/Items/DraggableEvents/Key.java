package com.example.mmo.MMO.Items.DraggableEvents;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Chests.Chest;
import com.example.mmo.MMO.Items.Item;

public class Key extends DraggableEvents {

    public Key(Handler handler) {
        super(98, handler);
        stackLimit = 1;
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        if(draggedTo.getItem().getType() != Item.CHEST)
            return false;

        Chest chest = (Chest) draggedTo.getItem();

        if(chest.getKeyLvl() != lvl)
            return false;

        chest.open(draggedTo);
        return true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
