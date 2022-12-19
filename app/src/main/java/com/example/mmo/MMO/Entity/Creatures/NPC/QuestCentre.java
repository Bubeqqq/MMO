package com.example.mmo.MMO.Entity.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.Quest;
import com.example.mmo.R;

public class QuestCentre extends Creature {

    private boolean active = false;

    private int guiX, guiY;

    private final RectF tableBounds, descriptionBounds;

    private Quest selectedQuest;

    private final Paint paint;

    //slider

    private float yOffset = 0, lastTouch;

    public QuestCentre(Handler handler, int x, int y) {
        super(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, Assets.questCentre);

        event = true;

        guiX = (int) handler.getEq().getX();
        guiY = (int) handler.getEq().getY();

        tableBounds = new RectF(guiX,
                guiY,
                guiX + handler.getEq().getTableWidth(),
                guiY + Container.slotSize * handler.getEq().getHeight());

        descriptionBounds = new RectF(guiX + handler.getEq().getTableWidth(),
                guiY,
                guiX + handler.getEq().getTableWidth() + (handler.getEq().getWidth() + 1) * Container.slotSize,
                guiY + Container.slotSize * handler.getEq().getHeight());

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(Container.slotSize / 2);
    }

    @Override
    protected void render(Canvas canvas) {
        canvas.drawBitmap(getCurrentTexture(),
                null,
                new RectF(x - handler.getCamera().getxOffset(),
                        y - handler.getCamera().getyOffset(),
                        x + width - handler.getCamera().getxOffset(),
                        y + height - handler.getCamera().getyOffset()),
                null);
    }

    @Override
    public void postRender(Canvas canvas) {
        if(!active)
            return;

        canvas.drawBitmap(Assets.table,
                null,
                tableBounds,
                    null);
        canvas.drawBitmap(Assets.descriptionArea,
                null,
                descriptionBounds,
                null);

        //draw slider with quests

        int index = 0;
        for(Quest c : handler.getQuestManager().getQuests()){
            if(guiY + (1 + index) * Container.slotSize + yOffset < guiY || //out of bounds
                    guiY + (2 + index) * Container.slotSize + yOffset > guiY + Container.slotSize * handler.getEq().getHeight()) {
                index += 2;
                continue;
            }

            canvas.drawBitmap(Assets.uiGame[15],  //draw slot
                    null,
                    new RectF(guiX + Container.slotSize / 2,
                            guiY + (1 + index) * Container.slotSize + yOffset,
                            guiX + Container.slotSize * 1.5f,
                            guiY + (2 + index) * Container.slotSize + yOffset),
                    null);
            canvas.drawBitmap(c.getTexture(),  //draw quest texture
                    null,
                    new RectF(guiX + Container.slotSize / 2,
                            guiY + (1 + index) * Container.slotSize + yOffset,
                            guiX + Container.slotSize * 1.5f,
                            guiY + (2 + index) * Container.slotSize + yOffset),
                    null);

            canvas.drawText(c.getName(), //draw quest name
                    guiX + Container.slotSize * 1.75f,
                    guiY + (1.75f + index) * Container.slotSize + yOffset,
                    paint);

            index += 2;
        }

        //draw quest info

        if(selectedQuest == null)
            return;

        index = 1;
        String[] description = selectedQuest.getDescription().split(" ");
        String line = "";
        for(String s : description){ //draw description
            if(paint.measureText(line + " " + s) <= descriptionBounds.right - descriptionBounds.left - Container.slotSize){
                if(!line.equalsIgnoreCase(""))
                    line += " ";

                line += s;
            }else {
                canvas.drawText(line,
                        guiX + handler.getEq().getTableWidth() + Container.slotSize,
                        guiY + index * Container.slotSize / 2,
                        paint);
                line = "";

                index++;
            }
        }

        if(!line.isEmpty()){
            canvas.drawText(line,
                    guiX + handler.getEq().getTableWidth() + Container.slotSize,
                    guiY + index * Container.slotSize / 2,
                    paint);
            index++;
        }

        canvas.drawText("Progress : " + selectedQuest.getProgress() + " / " + selectedQuest.getToComplete(), //draw progress
                guiX + handler.getEq().getTableWidth() + Container.slotSize,
                guiY + index * Container.slotSize / 2,
                paint);
        index += 2;

        canvas.drawText("Rewards : ", //draw progress
                guiX + handler.getEq().getTableWidth() + Container.slotSize,
                guiY + index * Container.slotSize / 2,
                paint);
        index += 2;

        //draw reward items
        int rewardsIndex = 1;
        int y = guiY + index * Container.slotSize / 2;
        for(ContainerItem c : selectedQuest.getReward()){
            canvas.drawBitmap(Assets.uiGame[15],  //draw slot
                    null,
                    new RectF(guiX + handler.getEq().getTableWidth() + rewardsIndex * Container.slotSize,
                            y,
                            guiX + handler.getEq().getTableWidth() + (1 + rewardsIndex) * Container.slotSize,
                            y + Container.slotSize),
                    null);

            c.render(canvas, guiX + handler.getEq().getTableWidth() + rewardsIndex * Container.slotSize, y); //draw item
            rewardsIndex++;
        }
    }

    @Override
    protected void tick() {
        if(!handler.getInputManager().isInGUI()) {
            active = false;
            handler.getSkillManager().getSkillBar().setActive(true);
        }
    }

    @Override
    public void Touch(MotionEvent event) {
        if(!tableBounds.contains(event.getX(), event.getY()) || !active)
            return;

        if(event.getAction() == MotionEvent.ACTION_UP){
            int index = 0;
            for(Quest c : handler.getQuestManager().getQuests()){
                RectF bounds = new RectF(guiX + Container.slotSize / 2,
                                guiY + (1 + index) * Container.slotSize + yOffset,
                                guiX + Container.slotSize * 1.5f,
                                guiY + (2 + index) * Container.slotSize + yOffset);

                if(bounds.contains(event.getX(), event.getY())){
                    selectedQuest = c;
                    return;
                }

                index += 2;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            lastTouch = event.getY();
            return;
        }

        if(event.getY() != lastTouch){
            float offset = event.getY() - lastTouch;

            if(guiY + yOffset + offset <= guiY + Container.slotSize * (handler.getEq().getHeight() - 2) &&
                    guiY + (handler.getQuestManager().getQuests().size() * 2 - 1) * Container.slotSize + yOffset + offset >= guiY)
                yOffset += offset;

            lastTouch = event.getY();
        }
    }

    @Override
    public void die() {

    }

    @Override
    public void event() {
        active = !active;
        yOffset = 0;
        selectedQuest = null;
        handler.getInputManager().setInGUI(true);
        handler.getSkillManager().getSkillBar().setActive(false);
    }
}
