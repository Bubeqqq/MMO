package com.example.mmo.MMO.Items;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.util.ArrayList;

public class DroppedItem {

    private int ID, amount, lvl;

    private float x, y;

    private Handler handler;

    private final ArrayList<ItemBonus> bonuses;

    public DroppedItem(int ID, int amount, float x, float y, int lvl, ArrayList<ItemBonus> bonuses, Handler handler){
        this.x = x;
        this.y = y;
        this.amount = amount;
        this.ID = ID;
        this.handler = handler;
        this.lvl = lvl;
        this.bonuses = bonuses;
    }

    public void render(Canvas canvas){
        canvas.drawBitmap(Assets.items[ID],
                null,
                new RectF(x - handler.getCamera().getxOffset(),
                        y - handler.getCamera().getyOffset(),
                        x + Container.slotSize - handler.getCamera().getxOffset(),
                        y + Container.slotSize - handler.getCamera().getyOffset()),
                null);
    }

    public RectF getBounds(){
        return new RectF(x, y, x + Container.slotSize, y + Container.slotSize);
    }

    //getters

    public int getID() {
        return ID;
    }

    public int getAmount() {
        return amount;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getLvl() {
        return lvl;
    }

    public ArrayList<ItemBonus> getBonuses() {
        return bonuses;
    }
}
