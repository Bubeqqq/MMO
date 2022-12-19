package com.example.mmo.MMO.Entity.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Dungeons.DungeonGenerator;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Input.Button;
import com.example.mmo.MMO.Quests.Quest;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.MMO.World.World;
import com.example.mmo.MMO.World.WorldManager;
import com.example.mmo.R;

import java.util.ArrayList;

public class DungeonGuardian extends Creature {

    private int guiX, guiY;

    private boolean active = false;

    private final RectF tableBounds, descriptionBounds;

    private ArrayList<DungeonGenerator> dungeons;

    private DungeonGenerator selectedDungeon;

    private final Paint paint;

    private Button start;

    private ContainerItem pass;

    //slider

    private float yOffset = 0, lastTouch;

    public DungeonGuardian(Handler handler, int x, int y) {
        super(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, Assets.goblinKing);

        event = true;

        guiX = (int) handler.getEq().getX();
        guiY = (int) handler.getEq().getY();

        dungeons = new ArrayList<>();

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(Container.slotSize / 2);

        tableBounds = new RectF(guiX,
                guiY,
                guiX + handler.getEq().getTableWidth(),
                guiY + Container.slotSize * handler.getEq().getHeight());

        descriptionBounds = new RectF(guiX + handler.getEq().getTableWidth(),
                guiY,
                guiX + handler.getEq().getTableWidth() + (handler.getEq().getWidth() + 1) * Container.slotSize,
                guiY + Container.slotSize * handler.getEq().getHeight());

        start = new Button(guiX + handler.getEq().getTableWidth() * 1.5f,
                guiY + Container.slotSize * (handler.getEq().getHeight() - 1),
                0,
                Assets.okButton,
                null,
                handler){

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
                Log.println(Log.ASSERT, "Dungeon", "Dungeon Created");

                if(pass != null){
                    if(handler.getEq().getEq().haveItem(pass.getID(), pass.getAmount(), pass.getLvl())){
                        startDungeon(selectedDungeon);
                        handler.getEq().getEq().takeItem(pass.getID(), pass.getAmount(), pass.getLvl());
                    }
                }else
                    startDungeon(selectedDungeon);
            }

        };

        handler.getInputManager().addButton(start);

        start.setBounds(new RectF(guiX + handler.getEq().getTableWidth() * 1.5f + Tile.TILEWIDTH * 0.75f,
                guiY + Container.slotSize * (handler.getEq().getHeight() - 2.5f),
                guiX + handler.getEq().getTableWidth() * 1.5f + Tile.TILEWIDTH * 4.25f,
                guiY + Container.slotSize * (handler.getEq().getHeight() - 1)));

        start.setShow(false);
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

        //draw slider with dungeons

        int index = 0;
        for(DungeonGenerator dungeon : dungeons){
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
            canvas.drawBitmap(dungeon.getTexture(),  //draw quest texture
                    null,
                    new RectF(guiX + Container.slotSize / 2,
                            guiY + (1 + index) * Container.slotSize + yOffset,
                            guiX + Container.slotSize * 1.5f,
                            guiY + (2 + index) * Container.slotSize + yOffset),
                    null);

            canvas.drawText(dungeon.getName(), //draw quest name
                    guiX + Container.slotSize * 1.75f,
                    guiY + (1.75f + index) * Container.slotSize + yOffset,
                    paint);

            index += 2;
        }

        //draw quest info

        if(selectedDungeon == null){
            start.setShow(false);
            return;
        }

        start.setShow(true);
        start.render(canvas);

        index = 1;
        String[] description = selectedDungeon.getDescription().split(" ");
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
    }

    @Override
    protected void tick() {
        if(!handler.getInputManager().isInGUI()) {
            active = false;
            handler.getSkillManager().getSkillBar().setActive(true);
            start.setShow(false);
        }
    }

    @Override
    public void Touch(MotionEvent event) {
        if(!tableBounds.contains(event.getX(), event.getY()) || !active)
            return;

        if(event.getAction() == MotionEvent.ACTION_UP){
            int index = 0;
            for(DungeonGenerator dungeon : dungeons){
                RectF bounds = new RectF(guiX + Container.slotSize / 2,
                        guiY + (1 + index) * Container.slotSize + yOffset,
                        guiX + Container.slotSize * 1.5f,
                        guiY + (2 + index) * Container.slotSize + yOffset);

                if(bounds.contains(event.getX(), event.getY())){
                    selectedDungeon = dungeon;
                    Log.println(Log.ASSERT, "Dungeons", dungeon.getName());
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
        selectedDungeon = null;
        handler.getInputManager().setInGUI(true);
        handler.getSkillManager().getSkillBar().setActive(false);
        start.setShow(true);
    }

    public void addDungeon(DungeonGenerator dungeon){
        dungeons.add(dungeon);
    }

    private void startDungeon(DungeonGenerator generator){
        generator.createDungeon();

        active = false;
        start.setShow(false);
        handler.getSkillManager().getSkillBar().setActive(true);
        handler.getInputManager().setInGUI(false);
        Point spawn = generator.getSpawnPoint();
        Log.println(Log.ASSERT, "Spawn x", spawn.x + "");

        handler.getWorldManager().setCurrentWorld(WorldManager.DUNGEONID);
        handler.getEntityManager().getPlayer().setPosition(spawn.x * Tile.TILEWIDTH, spawn.y * Tile.TILEWIDTH);
        generator.loadEntities();
    }
}
