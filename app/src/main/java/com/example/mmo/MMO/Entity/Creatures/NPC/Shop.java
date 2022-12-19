package com.example.mmo.MMO.Entity.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Utils;
import com.example.mmo.R;

public class Shop extends Creature {

    private Container shop;

    private boolean active = false;

    private boolean canBuy = false;

    private Paint atributesPaint;

    private final int atributesPaintSize = 50;

    public Shop(Handler handler, int x, int y) {
        super(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, Assets.shop);

        atributesPaint = new Paint();
        atributesPaint.setColor(handler.getGame().getResources().getColor(R.color.white));
        atributesPaint.setTextSize(atributesPaintSize);

        shop = new Container(250, 50, 3, 7, handler){

            @Override
            public void afterGetItem(boolean toContainer, ContainerItem cItem){
                if(toContainer){
                    removeItem(cItem);
                    handler.getEq().addMoney(cItem.getItem().getSellValue(cItem.getAmount()));
                }else {
                    handler.getEq().takeMoney(cItem.getItem().getBuyValue(cItem.getAmount()));
                    handler.getQuestManager().event(new QuestEvent(QuestEvent.BUYITEM, cItem.getItem().getType(), 1));
                }
            }

            @Override
            public boolean canRemoveItem(){
                Log.println(Log.ASSERT, "Shop", String.valueOf(canBuy));

                return canBuy;
            }

            @Override
            public void itemDragToBusySlot(boolean toContainer, ContainerItem cItem, Container newContainer, Container holdedContainer){
                if(newContainer == holdedContainer)
                    return;

                if(toContainer){
                    //removeItem(cItem);
                    handler.getEq().getEq().removeItem(cItem);
                    handler.getEq().addMoney(cItem.getItem().getSellValue(cItem.getAmount()));
                }
            }

        };
        event = true;
        shop.setActive(false);
        shop.setDrop(false);
        shop.setPernament(true);
        shop.setSupportDraggableEvents(false);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawBitmap(getCurrentTexture(),
                null,
                new RectF(x - handler.getCamera().getxOffset(),
                        y - handler.getCamera().getyOffset(),
                        x + width - handler.getCamera().getxOffset(),
                        y + height - handler.getCamera().getyOffset()),
                null);
    }

    @Override
    public void postRender(Canvas canvas){
        if(!active)
            return;

        handler.getEq().drawTableAt(canvas, handler.getEq().getWidth() * Container.slotSize + Container.slotSize / 2, handler.getEq().getY());
    }

    @Override
    protected void tick() {
        if(!handler.getInputManager().isInGUI()){
            active = false;
            handler.getSkillManager().getSkillBar().setActive(true);
            shop.setActive(active);
        }
    }

    @Override
    public void Touch(MotionEvent event) {
        if(!active)
            return;

        ContainerItem holded = handler.getContainerManager().getHoldedItem();
        if(holded == null)
            return;

        canBuy = handler.getEq().getMoney() >= holded.getItem().getBuyValue(holded.getAmount());
    }

    @Override
    public void die() {

    }

    @Override
    public void event() {
        active = !active;
        shop.setActive(active);
        handler.getSkillManager().getSkillBar().setActive(!active);
        handler.getInputManager().setInGUI(active);

        handler.getEq().setSideActive(active);
        shop.setX((int) (handler.getEq().getWidth() * Container.slotSize + Container.slotSize / 2 + handler.getEq().getTableWidth()));
        shop.setY((int) handler.getEq().getY());
    }

    public void addShopItem(ContainerItem item){
        shop.addItem(item);
    }
}
