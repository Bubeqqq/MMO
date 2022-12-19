package com.example.mmo.MMO.Containers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;

public class Container {

    //static

    public static int slotSize;

    //

    private int width, height, x, y;

    private ContainerManager manager;

    private ContainerItem[][] items;

    private int[][] types;

    private boolean hollow = false, active, playerEQ;

    private Paint paint;

    private boolean interact = true, pernament = false, drop = true;

    private Handler handler;

    private boolean supportDraggableEvents = true;

    public Container(int x, int y, int width, int height, Handler handler){
        this.handler = handler;
        this.width = width;
        this.height = height;
        this.manager = handler.getContainerManager();
        this.x = x;
        this.y = y;

        items = new ContainerItem[width][height];
        types = new int[width][height];

        setAllTypes(-1);

        manager.getContainers().add(this);
    }

    public void render(Canvas canvas){
        for(int x = 0; x < width; x++){ //draw eq slots
            for(int y = 0; y < height; y++){
                if(!hollow){
                    canvas.drawBitmap(Assets.uiGame[15],
                            null,
                            new RectF(x * slotSize + this.x,
                                    y * slotSize + this.y,
                                    x * slotSize + this.x + slotSize,
                                    y * slotSize + this.y + slotSize),
                            null);
                    if(items[x][y] != null) {
                        if(items[x][y] != manager.getHoldedItem()) { //skip holded item
                            items[x][y].render(canvas, x * slotSize + this.x, y * slotSize + this.y);
                            setPaint(x, y, items[x][y].getIconPaint());
                        }
                    }
                }else{
                    canvas.drawRect(
                            new RectF(x * slotSize + this.x,
                                    y * slotSize + this.y,
                                    x * slotSize + this.x + slotSize,
                                    y * slotSize + this.y + slotSize),
                            paint);

                    if(items[x][y] != null) {
                        if (items[x][y] != manager.getHoldedItem()) { //skip holded item
                            items[x][y].render(canvas, x * slotSize + this.x, y * slotSize + this.y);
                            setPaint(x, y, items[x][y].getIconPaint());
                        }
                    }
                }
            }
        }
    }

    public void removeItem(ContainerItem cItem){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                if(items[x][y] == cItem){
                    items[x][y] = null;
                }
            }
        }
    }

    public boolean takeItem(int ID, int amount, int minLvl){
        int minX = 0, minY = 0, min = Integer.MAX_VALUE;
        boolean taken = false;

            for(int x = 0; x < getWidth(); x++) {
                for (int y = 0; y < getHeight(); y++) {
                    if(getItems()[x][y] == null)
                        continue;

                    if (getItems()[x][y].getID() == ID && getItems()[x][y].getAmount() >= amount && getItems()[x][y].getLvl() >= minLvl) {
                        if (getItems()[x][y].getLvl() < min) {
                            min = getItems()[x][y].getLvl();
                            minX = x;
                            minY = y;
                            taken = true;
                        }
                    }
                }
            }

            if(!taken)
                return false;

            getItems()[minX][minY].addAmount(-amount);

            if(getItems()[minX][minY].getAmount() == 0)
                getItems()[minX][minY] = null;

            return true;
    }

    public boolean haveItem(int ID, int amount, int minLvl){
            for(int x = 0; x < getWidth(); x++){
                for(int y = 0; y < getHeight(); y++){
                    if(getItems()[x][y] == null)
                        continue;

                    if(getItems()[x][y].getID() == ID && getItems()[x][y].getAmount() >= amount && getItems()[x][y].getLvl() >= minLvl){
                        return true;
                    }
                }
            }

        return false;
    }

    public boolean addItem(ContainerItem cItem){
        for(int y = 0; y < height; y++){ //search for the same ID
            for(int x = 0; x < width; x++){
                if(items[x][y] == null)
                    continue;

                if(items[x][y].getID() == cItem.getID() &&
                        items[x][y].getLvl() == cItem.getLvl() &&
                        items[x][y].getAmount() + cItem.getAmount() <= items[x][y].getItem().getStackLimit()){

                    items[x][y].addAmount(cItem.getAmount());

                    return true;
                }
            }
        }

        for(int y = 0; y < height; y++){ //add for the first free slot
            for(int x = 0; x < width; x++){
                if(items[x][y] == null){
                    items[x][y] = cItem;
                    return true;
                }
            }
        }

        return false;
    }

    //Override

    public void afterGetItem(boolean toContainer, ContainerItem cItem){} //called when item is added(true) or removed(false) from container

    public boolean canAddItem(ContainerItem item){return true;}

    public boolean canRemoveItem(){return true;}

    public void itemDragToBusySlot(boolean toContainer, ContainerItem cItem, Container newContainer, Container holdedContainer){};

    //

    public void setPaint(int x, int y, Paint itemPaint){} //called before every slot with itemrender

    //getters setters

    public void setTypeAt(int x, int y, int type){
        types[x][y] = type;
    }

    public void setAllTypes(int type){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                types[x][y] = type;
            }
        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public RectF getBounds(){
        return new RectF(x, y, x + slotSize * width, y + slotSize * height);
    }

    public ContainerItem[][] getItems() {
        return items;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getTypes() {
        return types;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setHollow(boolean hollow) {
        this.hollow = hollow;

        if(hollow) {
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPlayerEQ() {
        return playerEQ;
    }

    public void setPlayerEQ(boolean playerEQ) {
        this.playerEQ = playerEQ;
    }

    public boolean isInteract() {
        return interact;
    }

    public void setInteract(boolean interact) {
        this.interact = interact;
    }

    public Paint getPaint() {
        return paint;
    }

    public boolean isPernament() {
        return pernament;
    }

    public void setPernament(boolean pernament) {
        this.pernament = pernament;
    }

    public boolean canDrop() {
        return drop;
    }

    public void setDrop(boolean drop) {
        this.drop = drop;
    }

    public boolean supportDraggableEvents() {
        return supportDraggableEvents;
    }

    public void setSupportDraggableEvents(boolean supportDraggableEvents) {
        this.supportDraggableEvents = supportDraggableEvents;
    }
}
