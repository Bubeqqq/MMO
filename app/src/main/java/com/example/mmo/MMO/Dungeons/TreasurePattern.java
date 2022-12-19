package com.example.mmo.MMO.Dungeons;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Recipe.RecipePattern;

import java.util.ArrayList;
import java.util.Random;

public class TreasurePattern {

    private ArrayList<RecipePattern> items;

    public TreasurePattern(){
        items = new ArrayList<>();
    }

    public void addItem(int ID, int amount, int lvl){
        items.add(new RecipePattern(ID, amount, lvl));
    }

    public void open(Handler handler){
        Random random = new Random();

        RecipePattern pattern = items.get(random.nextInt(items.size()));

        if(!handler.getEq().getEq().addItem(new ContainerItem(pattern.getID(), handler, pattern.getAmount(), pattern.getMinLvl(), null))){
            handler.getItemManager().addDroppedItem(handler.getEntityManager().getPlayer().getX(),
                    handler.getEntityManager().getPlayer().getY(),
                    pattern.getAmount(),
                    pattern.getID(),
                    pattern.getMinLvl(),
                    null);
        }
    }
}
