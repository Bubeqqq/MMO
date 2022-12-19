package com.example.mmo.MMO.EQ;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.NPC.Blacksmith;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Input.Button;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Items.NoUse.Gold;
import com.example.mmo.MMO.Items.NoUse.SackOfGold;
import com.example.mmo.MMO.Items.NoUse.Silver;
import com.example.mmo.MMO.Items.Recipe.Recipe;
import com.example.mmo.MMO.Items.Recipe.RecipePattern;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Saving.EqSave;
import com.example.mmo.R;

import java.util.Random;

public class EQ {

    private Container equipment, eq;

    private float x, y, tableWidth;

    private final int width = 9, height = 8, atributesPaintSize, upgradeRenderYoffset = Container.slotSize / 4;

    private Handler handler;

    private Paint atributesPaint;

    private final Random r;

    //upgrade

    private int upgraderID = 0, bonus = 0;

    private boolean upgrade = false, destroy;

    private Blacksmith blacksmith;

    private ContainerItem upgradeItem;

    private Button ok, cancel;

    //switch

    private boolean renderNewItem = false;

    private RectF tableBounds;

    public EQ(Handler handler){
        handler.setEq(this);
        this.handler = handler;

        atributesPaintSize = Container.slotSize * height / 20;
        atributesPaint = new Paint();
        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
        atributesPaint.setTextSize(atributesPaintSize);

        r = new Random();

        x = handler.getWindowSize().x / 3 - width * Container.slotSize / 2;
        y = handler.getWindowSize().y / 2 - height * Container.slotSize / 2;
        tableWidth = (int) ((Container.slotSize * height) / 1.5f);

        equipment = new Container((int) (x + width * Container.slotSize + tableWidth), (int) y, 1, height, handler){
            @Override
            public void render(Canvas canvas){
                canvas.drawBitmap(Assets.equipment,
                        null,
                        new RectF(getX(),
                                y,
                                getX() + slotSize,
                                y + slotSize * height),
                        null);

                for(int i = 0; i < height; i++){
                    if(getItems()[0][i] != null)
                        getItems()[0][i].render(canvas, getX(), y + i * slotSize);
                }
            }

            @Override
            public void afterGetItem(boolean toContainer, ContainerItem cItem){
                handler.getStatistics().refresh();
            }

            @Override
            public boolean canAddItem(ContainerItem item){
                Log.println(Log.ASSERT, "EQ", String.valueOf(handler.getEntityManager().getPlayer().getLvl()));
                return handler.getEntityManager().getPlayer().getLvl() >= item.getItem().getMinLvl();
            }
        };

        equipment.setSupportDraggableEvents(false);

        equipment.setTypeAt(0, 0, Item.WEAPON);
        equipment.setTypeAt(0, 1, Item.WEAPON);
        equipment.setTypeAt(0, 2, Item.ORB);
        equipment.setTypeAt(0, 3, Item.ORB);
        equipment.setTypeAt(0, 4, Item.NECKLACE);
        equipment.setTypeAt(0, 5, Item.SHIELD);
        equipment.setTypeAt(0, 6, Item.HELMET);
        equipment.setTypeAt(0, 7, Item.CHESTPLATE);

        eq = new Container((int) (x + tableWidth), (int) y, width, height - 2, handler);
        eq.setPlayerEQ(true);

        //upgrade buttons

        ok = new Button(x, y + height * Container.slotSize, 0, Assets.okButton,null, handler){
            @Override
            public void render(Canvas canvas){
                if(!isShow())
                    return;

                canvas.drawBitmap(texture,
                        null,
                        getBounds(),
                        null);
            }

            @Override
            public void clicked(){
                if(!upgrade)
                    return;

                if(upgradeItem == null)
                    return;

                //transform
                if(upgradeItem.getLvl() == 10){
                    if(upgradeItem.transform()){
                        eq.takeItem(upgraderID, 1, 0); //take item used to upgrade
                        handler.getQuestManager().event(new QuestEvent(QuestEvent.TRANSFORMITEM, upgradeItem.getID(), 1));
                    }else
                        handler.addAnnouncement("Not Enough Items");
                    setUpgrade(false, null, 0, false, 0); //turn off upgrade menu
                    handler.getContainerManager().setLastHolded(upgradeItem);
                    return;
                }

                if(upgradeItem.getItem().canUpgrade(upgradeItem.getLvl())){
                    upgradeItem.getItem().takeItems(upgradeItem.getLvl()); //take items required to upgrade
                    eq.takeItem(upgraderID, 1, 0); //take item used to upgrade
                    handler.getQuestManager().event(new QuestEvent(QuestEvent.UPGRADEITEM, upgradeItem.getID(), 1));

                    if(r.nextInt(100) < upgradeItem.getItem().getUpgrades()[upgradeItem.getLvl() - 1].getPercent() + bonus) { // upgrade percent
                        upgradeItem.upgrade();
                    }else if(destroy){ //destroy (blacksmith)
                        eq.removeItem(upgradeItem);
                        handler.addAnnouncement("Item Destroyed");
                        Log.println(Log.ASSERT, "EQ", "item removed");
                    }

                    if(blacksmith != null){ //go back to blacksmith menu
                        blacksmith.event();
                        setActive(false);
                        setSideActive(true);

                    }

                    setUpgrade(false, null, 0, false, 0); //turn off upgrade menu
                }else{ //player don't have required items
                    handler.addAnnouncement("Not Enough Items");
                    if(blacksmith != null){ //go back to blacksmith menu
                        blacksmith.event();
                        setActive(false);
                        setSideActive(true);
                    }

                    setUpgrade(false, null, 0, false, 0); //turn off menu
                }
            }
        };

        ok.setBounds(new RectF(x,
                y + (height - 1.5f) * Container.slotSize,
                x + tableWidth / 2,
                y + Container.slotSize * height));
        handler.getInputManager().addButton(ok);

        cancel = new Button(x, y + height * Container.slotSize, 0, Assets.backButton,null, handler){
            @Override
            public void render(Canvas canvas){
                if(!isShow())
                    return;

                canvas.drawBitmap(texture,
                        null,
                        getBounds(),
                        null);
            }

            @Override
            public void clicked(){
                if(blacksmith != null){
                    blacksmith.event();
                    setActive(false);
                    setSideActive(true);
                }

                setUpgrade(false, null, 0, false, 0);
            }
        };

        cancel.setBounds(new RectF(x + tableWidth / 2,
                y + (height - 1.5f) * Container.slotSize,
                x + tableWidth,
                y + Container.slotSize * height));
        handler.getInputManager().addButton(cancel);

        tableBounds = new RectF(this.x,
                this.y,
                this.x + tableWidth,
                this.y + Container.slotSize * height);
    }

    public void render(Canvas canvas){
        if(!isActive())
            return;

        canvas.drawBitmap(Assets.table,
                null,
                tableBounds,
                null);

        ok.render(canvas);
        cancel.render(canvas);

        if(!upgrade)
            renderItemInfo(canvas);
        else
            renderUpgrade(canvas);

    }

    public void renderItemInfo(Canvas canvas){
        ContainerItem holded = handler.getContainerManager().getLastHolded();

        if (holded != null && holded.getItem().getDescription(0, holded.getAmount()) != null) {
            //draw bonuses

            renderItemInfo(canvas, x, y, holded);
        }
    }

    public void renderUpgrade(Canvas canvas){
        if(upgradeItem == null)
            return;

        if(renderNewItem){
            ContainerItem newItem;

            if(upgradeItem.getLvl() != 10)
                newItem = new ContainerItem(upgradeItem.getID(),
                        handler,
                        upgradeItem.getAmount(),
                        upgradeItem.getLvl() + 1,
                        upgradeItem.getBonuses());
            else
                newItem = new ContainerItem(upgradeItem.getItem().getNewId(),
                        handler,
                        upgradeItem.getAmount(),
                        1,
                        upgradeItem.getBonuses());

            renderItemInfo(canvas, this.x, this.y, newItem);

            canvas.drawText("Item preview",
                    x + Container.slotSize / 2,
                    y + Container.slotSize * 0.65f,
                    atributesPaint);

            return;
        }

        //render items required to upgrade

        Recipe recipe;

        if(upgradeItem.getLvl() == 10)
            recipe = upgradeItem.getItem().getTransformation();
        else
             recipe = upgradeItem.getItem().getUpgrades()[upgradeItem.getLvl() - 1];

        if(upgradeItem.getLvl() != 10)
            canvas.drawText("Grands " + upgradeItem.getItem().getLvlUpgrade() * 100 + "% upgrade",
                    x + Container.slotSize / 2,
                    y + Container.slotSize * 0.65f,
                    atributesPaint);
        else
            canvas.drawText("Transform to new Form",
                    x + Container.slotSize / 2,
                    y + Container.slotSize * 0.65f,
                    atributesPaint);

        if(bonus == 0)
            canvas.drawText("Chance : " + recipe.getPercent() + "%",
                    x +  + Container.slotSize / 2,
                    y + Container.slotSize,
                    atributesPaint);
        else
            canvas.drawText("Chance : " + recipe.getPercent() + "%" + " + " + bonus + "%",
                    x + Container.slotSize / 2,
                    y + Container.slotSize,
                    atributesPaint);


        int index = 0;
        for(RecipePattern e : recipe.getRecipes()){
            canvas.drawBitmap(Assets.uiGame[15],
                    null,
                    new RectF(this.x + Container.slotSize / 2,
                            this.y + Container.slotSize * (1 + index) + upgradeRenderYoffset,
                            this.x + Container.slotSize / 2 + Container.slotSize,
                            this.y + Container.slotSize * (2 + index) + upgradeRenderYoffset),
                    null);

            canvas.drawBitmap(Assets.items[e.getID()],
                    null,
                    new RectF(this.x + Container.slotSize / 2,
                            this.y + Container.slotSize * (1 + index) + upgradeRenderYoffset,
                            this.x + Container.slotSize / 2 + Container.slotSize,
                            this.y + Container.slotSize * (2 + index) + upgradeRenderYoffset),
                            null);

            if(e.getMinLvl() != 0){
                canvas.drawText("min. Lvl : " + e.getMinLvl(),
                        this.x + Container.slotSize * 2,
                        this.y + Container.slotSize * (1 + index) + atributesPaintSize + upgradeRenderYoffset,
                        atributesPaint);
            }

            canvas.drawText("Amount : " + e.getAmount(),
                    this.x + Container.slotSize * 2,
                    this.y + Container.slotSize * (2 + index) + upgradeRenderYoffset,
                    atributesPaint);
            index += 2;
        }

        canvas.drawBitmap(Assets.uiGame[15],
                null,
                new RectF(this.x + Container.slotSize / 2,
                        this.y + Container.slotSize * (1 + index) + upgradeRenderYoffset,
                        this.x + Container.slotSize / 2 + Container.slotSize,
                        this.y + Container.slotSize * (2 + index) + upgradeRenderYoffset),
                null);

        canvas.drawBitmap(Assets.items[89],
                null,
                new RectF(this.x + Container.slotSize / 2,
                        this.y + Container.slotSize * (index + 1) + upgradeRenderYoffset,
                        this.x + Container.slotSize / 2 + Container.slotSize,
                        this.y + Container.slotSize * (index + 2) + upgradeRenderYoffset),
                null);

        canvas.drawText("Coins : " + recipe.getMoney(),
                this.x + Container.slotSize * 2,
                this.y + Container.slotSize * (index + 2) + upgradeRenderYoffset,
                atributesPaint);
    }

    public void touch(MotionEvent event){
        if(tableBounds.contains(event.getX(), event.getY()) && event.getAction() == MotionEvent.ACTION_UP) {
            setRenderNewItem(!renderNewItem);
        }
    }

    //getters setters

    public Container getEquipment() {
        return equipment;
    }

    public void setActive(boolean active){
        equipment.setActive(active);
        eq.setActive(active);

        setUpgrade(false, null, 0, false, 0);

        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);

        x = size.x / 3 - width * Container.slotSize / 2;
        y = size.y / 2 - height * Container.slotSize / 2;

        eq.setX((int) (x + tableWidth));
        eq.setY((int) y);
    }

    public void setSideActive(boolean active){
        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);

        eq.setActive(active);

        y = size.y / 2 - height * Container.slotSize / 2;

        eq.setX(Container.slotSize / 2);
        eq.setY((int) y);
    }

    public boolean isActive(){
        return equipment.isActive();
    }

    public float getX() {
        return x;
    }

    public int getHeight() {
        return height;
    }

    public float getTableWidth() {
        return tableWidth;
    }

    public float getY() {
        return y;
    }

    public int getMoney() {
        int money = 0;
        for(int y = 0; y < eq.getHeight(); y++){
            for(int x = 0; x < eq.getWidth(); x++){
                if(eq.getItems()[x][y] != null){
                    int ID = eq.getItems()[x][y].getID();
                    switch (ID){
                        case 89 :{
                            money += Gold.GOLDVALUE * eq.getItems()[x][y].getAmount();
                            break;
                        }
                        case 94 :{
                            money += SackOfGold.SACKVALUE * eq.getItems()[x][y].getAmount();
                            break;
                        }
                        case 96 :{
                            money += Silver.SILVERVALUE * eq.getItems()[x][y].getAmount();
                            break;
                        }
                    }
                }
            }
        }

        return money;
    }

    public void addMoney(int money){
        int f = money;
        while(f >= SackOfGold.SACKVALUE){
           eq.addItem(new ContainerItem(94, handler, 1, 0, null));
           f -= SackOfGold.SACKVALUE;
        }
        while(f >= Gold.GOLDVALUE){
            eq.addItem(new ContainerItem(89, handler, 1, 0, null));
            f -= Gold.GOLDVALUE;
        }
        while(f >= Silver.SILVERVALUE){
            eq.addItem(new ContainerItem(96, handler, 1, 0, null));
            f -= Silver.SILVERVALUE;
        }
    }

    public void takeMoney(int money){
        int moneyTaken = takeCoins(0, money);

        while(moneyTaken < money){
            if(eq.takeItem(89, 1, 0)){
                eq.addItem(new ContainerItem(96, handler, Gold.GOLDVALUE / Silver.SILVERVALUE, 0, null));
            }else{
                eq.takeItem(94, 1, 0);
                eq.addItem(new ContainerItem(89, handler, SackOfGold.SACKVALUE / Gold.GOLDVALUE, 0, null));
            }

            moneyTaken += takeCoins(moneyTaken, money);
        }

    }

    private int takeCoins(int takenAlready, int toTake){
        int moneyTaken = 0;
        while(moneyTaken + takenAlready + Silver.SILVERVALUE <= toTake && eq.takeItem(96, 1, 0)){
            moneyTaken += Silver.SILVERVALUE;
        }
        while(moneyTaken + takenAlready + Gold.GOLDVALUE <= toTake && eq.takeItem(89, 1, 0)){
            moneyTaken += Gold.GOLDVALUE;
        }
        while(moneyTaken + takenAlready + SackOfGold.SACKVALUE <= toTake && eq.takeItem(94, 1, 0)){
            moneyTaken += SackOfGold.SACKVALUE;
        }
        return moneyTaken;
    }

    public Container getEq() {
        return eq;
    }

    public int getWidth() {
        return width;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade, ContainerItem upgradeItem, int upgraderID, boolean destroy, int bonus, Blacksmith b){
        this.upgrade = upgrade;
        if(upgradeItem != null)
            this.upgradeItem = upgradeItem;

        this.destroy = destroy;
        this.upgraderID = upgraderID;
        this.bonus = bonus;

        this.blacksmith = b;

        ok.setShow(upgrade);
        cancel.setShow(upgrade);
    }

    public void setUpgrade(boolean upgrade, ContainerItem upgradeItem, int upgraderID, boolean destroy, int bonus){
        this.upgrade = upgrade;
        renderNewItem = false;
        if(upgradeItem != null)
            this.upgradeItem = upgradeItem;

        this.destroy = destroy;
        this.upgraderID = upgraderID;
        this.bonus = bonus;

        this.blacksmith = null;

        ok.setShow(upgrade);
        cancel.setShow(upgrade);
    }

    public void drawTableAt(Canvas canvas, float x, float y){
        canvas.drawBitmap(Assets.table,
                null,
                new RectF(x,
                        y,
                        x + tableWidth,
                        y + Container.slotSize * height),
                null);

        ContainerItem holded = handler.getContainerManager().getLastHolded();

        if (holded != null && holded.getItem().getDescription(0, holded.getAmount()) != null) {
            //draw bonuses
            int index = 0;
            for (String s : holded.getDescription().split("\\n+")) {
                if(holded.getItem().getNumberOfAtributes() != 0){
                    if(index < holded.getItem().getNumberOfAtributes() + 2)
                        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
                    else
                        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.yellow));
                }

                if(holded.getItem().getMinLvl() > 0){
                    if(index == 1)
                        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.red));
                }

                canvas.drawText(s, x + Container.slotSize / 2,
                        y + index * atributesPaintSize + Container.slotSize * 3,
                        atributesPaint);
                index++;
            }

            //draw value
            atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
            index++;
            for (String s : holded.getItem().getSellDescription(holded.getAmount()).split("\\n+")) {
                canvas.drawText(s, x + Container.slotSize / 2,
                        y + index * atributesPaintSize + Container.slotSize * 3,
                        atributesPaint);
                index++;
            }

            canvas.drawBitmap(Assets.uiGame[15],
                    null,
                    new RectF(x + tableWidth / 3,
                            y + Container.slotSize,
                            x + tableWidth / 3 + Container.slotSize,
                            y + Container.slotSize * 2),
                    null);

            canvas.drawBitmap(Assets.items[holded.getID()],
                    null,
                    new RectF(x + tableWidth / 3,
                            y + Container.slotSize,
                            x + tableWidth / 3 + Container.slotSize,
                            y + Container.slotSize * 2),
                    null);
        }
    }

    private float renderItemInfo(Canvas canvas, float x, float y, ContainerItem holded){ //return y value of bottom
        int index = 0;
        for (String s : holded.getDescription().split("\\n+")) {
            if(holded.getItem().getNumberOfAtributes() != 0){
                if(index < holded.getItem().getNumberOfAtributes() + 2)
                    atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
                else
                    atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.yellow));
            }

            if(holded.getItem().getMinLvl() > 0){
                if(index == 1)
                    atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.red));
            }

            canvas.drawText(s, x + Container.slotSize / 2,
                    y + index * atributesPaintSize + Container.slotSize * 3,
                    atributesPaint);
            index++;
        }

        //draw value
        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
        index++;
        for (String s : holded.getItem().getSellDescription(holded.getAmount()).split("\\n+")) {
            canvas.drawText(s, x + Container.slotSize / 2,
                    y + index * atributesPaintSize + Container.slotSize * 3,
                    atributesPaint);
            index++;
        }

        canvas.drawBitmap(Assets.uiGame[15],
                null,
                new RectF(x + tableWidth / 3,
                        y + Container.slotSize,
                        x + tableWidth / 3 + Container.slotSize,
                        y + Container.slotSize * 2),
                null);

        canvas.drawBitmap(Assets.items[holded.getID()],
                null,
                new RectF(x + tableWidth / 3,
                        y + Container.slotSize,
                        x + tableWidth / 3 + Container.slotSize,
                        y + Container.slotSize * 2),
                null);

        return y + index * atributesPaintSize + Container.slotSize * 3;
    }

    public int getAtributesPaintSize() {
        return atributesPaintSize;
    }

    public void setRenderNewItem(boolean renderNewItem){
        ok.setShow(!renderNewItem);
        cancel.setShow(!renderNewItem);

        this.renderNewItem = renderNewItem;
    }
}
