package com.example.mmo.MMO.Items.DraggableEvents;

import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;

public class TransformBook extends DraggableEvents{

    public TransformBook(Handler handler) {
        super(81, handler);
    }

    @Override
    public boolean event(ContainerItem draggedTo, int lvl) {
        Log.println(Log.ASSERT, "Transformation Book", "Used");

        if(draggedTo.getLvl() != 10 || !draggedTo.getItem().haveTransformation()) {
            return false;
        }

        handler.getEq().setUpgrade(true, draggedTo, 81, false, 0);

        return false;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        return getName() + "\nDrag to +10 item.\nSome of it will\nhave stronger forms";
    }

    @Override
    public String getName() {
        return "Transform Book";
    }
}
