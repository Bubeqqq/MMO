package com.example.mmo.MMO.Containers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Statistics.ItemBonus;
import com.example.mmo.R;

import java.util.ArrayList;

public class ContainerItem {

    private int ID, amount, lvl;

    private Paint paint, iconPaint;

    private Handler handler;

    private ArrayList<ItemBonus> bonuses;

    public ContainerItem(int ID, Handler handler, int amount, int lvl, ArrayList<ItemBonus> bonuses){
        this.ID = ID;
        this.amount = amount;
        this.lvl = lvl;
        this.handler = handler;
        this.bonuses = bonuses;

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(Container.slotSize / 4);
        paint.setFakeBoldText(true);

        iconPaint = new Paint();
    }

    public void render(Canvas canvas, float x, float y){
        canvas.drawBitmap(Assets.items[ID],
                null,
                new RectF(x,
                        y,
                        x + Container.slotSize,
                        y + Container.slotSize),
                iconPaint);
        canvas.drawText(String.valueOf(amount), x + Container.slotSize * 0.65f, y + Container.slotSize, paint);

        if(lvl > 0){
            canvas.drawText("+" + lvl, x + Container.slotSize / 10, y + Container.slotSize / 3, paint);
        }
    }

    public void use(){
        if(getItem().getType() != Item.USABLE)
            return;

        Usable u = (Usable) getItem();

        if(!u.canUse())
            return;

        u.use(lvl);

        if(u.isOneTimeUsable()){
            amount--;
            if(amount == 0){
                handler.getSkillManager().getSkillBar().removeItem(this);
            }
        }
    }

    public Item getItem(){
        return handler.getItemManager().getItem(ID);
    }

    public int getID() {
        return ID;
    }

    public int getAmount() {
        return amount;
    }

    public int getLvl() {
        return lvl;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Paint getIconPaint() {
        return iconPaint;
    }

    public void upgrade(){
        lvl++;
    }

    public ArrayList<ItemBonus> getBonuses() {
        return bonuses;
    }

    public String getDescription(){
        String description = getItem().getDescription(lvl, amount) + "\n";

        if(bonuses != null){
            for(ItemBonus i : bonuses){
                description += handler.getStatistics().getName(i.getID()) + ":" + i.getValue() + "%\n";
            }
        }

        return description;
    }

    public void setBonuses(ArrayList<ItemBonus> bonuses) {
        this.bonuses = bonuses;
    }

    public boolean transform(){
        boolean b = getItem().upgrade();
        if(b){
            ID = getItem().getNewId();
            lvl = 1;
        }
        return b;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
