package com.example.mmo.MMO.Items.DraggableEvents;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;

public abstract class DraggableEvents extends Item {

    public DraggableEvents(int ID, Handler handler) {
        super(ID, handler);
    }

    public abstract boolean event(ContainerItem draggedTo, int lvl); //returns if item need to be removed

    @Override
    public void setUpUpgrades() {

    }

    @Override
    public int getType() {
        return Item.DRAGGABLE;
    }
}
