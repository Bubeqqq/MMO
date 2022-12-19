package com.example.mmo.MMO.Items.DraggableEvents;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.util.ArrayList;

public class AddBonus extends DraggableEvents{

    public AddBonus(Handler handler) {
        super(90, handler);
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        if(draggedTo.getItem().getNumberOfAtributes() == 0)
            return false;

        if(draggedTo.getBonuses() == null)
            draggedTo.setBonuses(new ArrayList<>());

        if(draggedTo.getBonuses().size() >= 3)
            return false;

        draggedTo.getBonuses().add(handler.getStatistics().getRandomBonus());
        handler.getContainerManager().setLastHolded(draggedTo);
        return true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\n\nAdd special Bonus \nto your equipment\n\n" +
                "max 3 bonuses";
    }

    @Override
    public String getName() {
        return "Item Enchantment";
    }
}
