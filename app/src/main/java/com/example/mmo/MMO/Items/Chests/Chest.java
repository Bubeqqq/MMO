package com.example.mmo.MMO.Items.Chests;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.R;

import java.util.ArrayList;
import java.util.Random;

public abstract class Chest extends Item {

    protected int keyLvl, openedChest;

    protected ArrayList<Drop> drops;

    public Chest(int ID, Handler handler) {
        super(ID, handler);

        stackLimit = 1;

        drops = new ArrayList<>();
    }

    @Override
    public int getType() {
        return Item.CHEST;
    }

    public void open(ContainerItem chest){
        Random r = new Random();
        Drop d = drops.get(r.nextInt(drops.size() - 1));

        if(!handler.getEq().getEq().addItem(new ContainerItem(d.getID(), handler, d.getAmount(), d.getLvl(), null))){
            handler.getItemManager().addDroppedItem(handler.getEntityManager().getPlayer().getX(),
                    handler.getEntityManager().getPlayer().getY(),
                    d.getID(),
                    d.getAmount(),
                    d.getLvl(),
                    null);
        }

        chest.setID(openedChest);
    }

    public int getKeyLvl() {
        return keyLvl;
    }
}
