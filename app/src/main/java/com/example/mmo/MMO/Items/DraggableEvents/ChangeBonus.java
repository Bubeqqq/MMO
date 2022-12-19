package com.example.mmo.MMO.Items.DraggableEvents;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;

import java.util.ArrayList;

public class ChangeBonus extends DraggableEvents{

    public ChangeBonus(Handler handler) {
        super(95, handler);
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        if(draggedTo.getItem().getNumberOfAtributes() == 0)
            return false;

        if(draggedTo.getBonuses() == null)
            return false;

        int size = draggedTo.getBonuses().size();

        draggedTo.getBonuses().clear();

        for(int i = 0; i < size; i++){
            draggedTo.getBonuses().add(handler.getStatistics().getRandomBonus());
        }
        handler.getContainerManager().setLastHolded(draggedTo);
        return true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nChange all\nbonuses of item";
    }

    @Override
    public String getName() {
        return "Changer";
    }
}
