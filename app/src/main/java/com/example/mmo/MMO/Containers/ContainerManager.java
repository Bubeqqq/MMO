package com.example.mmo.MMO.Containers;

import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.DraggableEvents.DraggableEvents;
import com.example.mmo.MMO.Items.Item;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class ContainerManager {

    private Handler handler;

    private ArrayList<Container> containers;

    private ContainerItem holdedItem, lastHolded;

    private int holdedItemX, holdedItemY; //coordinates from which player took the item

    private Container holdedItemContainer; //Container from which player took the item

    private Point lastTouch;

    public ContainerManager(Handler handler){
        this.handler = handler;
        handler.setContainerManager(this);

        containers = new ArrayList<>();
        lastTouch = new Point();

        Container.slotSize = handler.getWindowSize().y / 10;
    }

    public void tick(){

    }

    public void render(Canvas canvas){
        for(Container c : containers) {
            if(!c.isActive())
                continue;
            c.render(canvas);
        }

        if(holdedItem != null)
            holdedItem.render(canvas, lastTouch.x - Container.slotSize / 2, lastTouch.y - Container.slotSize / 2);
    }

    public void touch(MotionEvent event){

        if(holdedItem == null){
            Container selected = null; // container in which player clicked
            boolean isInBounds = false; //checks if touch is in one of containers
            for(Container c : containers){
                if(!c.isActive() || !c.isInteract())
                    continue;

                if(c.getBounds().contains(event.getX(), event.getY())) {
                    isInBounds = true;
                    selected = c;
                    break;
                }
            }

            if(!isInBounds) // if touch is outside of containers end method
                return;

            int x = (int) ((event.getX() - selected.getX()) / Container.slotSize);
            int y = (int) ((event.getY() - selected.getY()) / Container.slotSize);

            holdedItem = selected.getItems()[x][y];
            lastHolded = holdedItem;
            holdedItemX = x;
            holdedItemY = y;
            holdedItemContainer = selected;
        }

        if(holdedItem == null)
            return;

        lastTouch.set((int) event.getX(), (int) event.getY()); //set lastTouch for holdedItem render

        if(event.getAction() != MotionEvent.ACTION_UP) //code after handle item drop
            return;

        Container newContainer = null;

        for(Container c : containers){ //search for Container in which item was dropped
            if(!c.isActive() || !c.isInteract())
                continue;

            if(c.getBounds().contains(event.getX(), event.getY())) {
                newContainer = c;
                break;
            }
        }

        if(newContainer == null){ // if not found, drop item
            if(holdedItemContainer.canDrop()){
                handler.getItemManager().addDroppedItem(handler.getEntityManager().getPlayer().getX(), //add droppped item
                        handler.getEntityManager().getPlayer().getY(),
                        holdedItem.getAmount(),
                        holdedItem.getID(),
                        holdedItem.getLvl(),
                        holdedItem.getBonuses());
                holdedItemContainer.getItems()[holdedItemX][holdedItemY] = null; //empty slot in container
            }
            holdedItem = null;
            return;
        }

        int x = (int) ((event.getX() - newContainer.getX()) / Container.slotSize);
        int y = (int) ((event.getY() - newContainer.getY()) / Container.slotSize);

        if(x == holdedItemX && y == holdedItemY && newContainer == holdedItemContainer) { //skip if player move item to the same slot
            holdedItem = null;
            return;
        }

        if(newContainer.getItems()[x][y] == null){ //if new slot is free

            if(!newContainer.canAddItem(holdedItem) || !holdedItemContainer.canRemoveItem()){
                holdedItem = null;
                return;
            }

            if(newContainer.getTypes()[x][y] != holdedItem.getItem().getType() && newContainer.getTypes()[x][y] != -1){
                holdedItem = null;
                return;
            }


            newContainer.getItems()[x][y] = holdedItem;

            if(!holdedItemContainer.isPernament())
                holdedItemContainer.getItems()[holdedItemX][holdedItemY] = null;//empty slot in container
            else
                holdedItemContainer.getItems()[holdedItemX][holdedItemY] = new ContainerItem(holdedItem.getID(), handler, holdedItem.getAmount(), holdedItem.getLvl(), holdedItem.getBonuses());

            newContainer.afterGetItem(true, holdedItem);
            holdedItemContainer.afterGetItem(false, holdedItem);
            holdedItem = null;
            return;
        }

        if(newContainer.getItems()[x][y].getID() == holdedItem.getID() && !newContainer.isPernament()){ //if items are the same and new Container can be changed //merge
            if(!newContainer.canAddItem(holdedItem) || !holdedItemContainer.canRemoveItem()){
                holdedItem = null;
                return;
            }

            newContainer.getItems()[x][y].addAmount(holdedItem.getAmount());

            if(newContainer.getItems()[x][y].getAmount() > newContainer.getItems()[x][y].getItem().getStackLimit()){ //if amount goes above stack limit
                if(!holdedItemContainer.isPernament()){
                    holdedItem.setAmount(newContainer.getItems()[x][y].getAmount() - newContainer.getItems()[x][y].getItem().getStackLimit()); //if container can be change, set amount of first stack
                }else{
                    handler.getEq().getEq().addItem(new ContainerItem(holdedItem.getID(), //if container cannot be change add rest of items to player eq
                            handler,
                            newContainer.getItems()[x][y].getAmount() - newContainer.getItems()[x][y].getItem().getStackLimit(),
                            holdedItem.getLvl(),
                            holdedItem.getBonuses()));
                }
                newContainer.getItems()[x][y].setAmount(newContainer.getItems()[x][y].getItem().getStackLimit());
            }else{
                if(!holdedItemContainer.isPernament())
                    holdedItemContainer.getItems()[holdedItemX][holdedItemY] = null;
                else
                    holdedItemContainer.getItems()[holdedItemX][holdedItemY] = new ContainerItem(holdedItem.getID(), handler, holdedItem.getAmount(), holdedItem.getLvl(), holdedItem.getBonuses());


            }

            newContainer.afterGetItem(true, holdedItem);
            holdedItemContainer.afterGetItem(false, holdedItem);
            holdedItem = null;
            return;
        }

        //draggable event

        if(holdedItem.getItem().getType() == Item.DRAGGABLE && newContainer.supportDraggableEvents() && holdedItemContainer.supportDraggableEvents()){
            DraggableEvents draggableEvent = (DraggableEvents) holdedItem.getItem();



            if(draggableEvent.event(newContainer.getItems()[x][y], holdedItem.getLvl())){
                holdedItem.setAmount(holdedItem.getAmount() - 1);

                if(holdedItem.getAmount() == 0)
                    holdedItemContainer.getItems()[holdedItemX][holdedItemY] = null;
            }
        }

        //

        newContainer.itemDragToBusySlot(true, holdedItem, newContainer, holdedItemContainer);
        holdedItem = null;
    }

    //


    //getters


    public ContainerItem getHoldedItem() {
        return holdedItem;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public ContainerItem getLastHolded() {
        return lastHolded;
    }

    public void setLastHolded(ContainerItem lastHolded) {
        this.lastHolded = lastHolded;
    }
}
