package com.example.mmo.MMO.Entity.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.NpcQuestPattern;
import com.example.mmo.MMO.Quests.Quest;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NPC extends Creature {

    private final int questRange = Tile.TILEWIDTH * 2;

    private boolean active = false, playerInRange;

    private long lastTime, timer;

    private final int guiX, guiY, textSpeed = 50, ID;
    private final RectF guiBounds;

    private int Index = 0, wordsIndex = 0;

    private Paint paint;

    private final ArrayList<NpcQuestPattern> quests;

    //quest after talk

    private boolean repetitive = false;

    public NPC(Handler handler, int x, int y, int ID) {
        super(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, Assets.npcs[ID]);

        this.ID = ID;

        handler.getQuestManager().addNPC(this);

        event = true;

        guiX = handler.getWindowSize().x / 2 - Container.slotSize * handler.getEq().getWidth() / 2;
        guiY = handler.getWindowSize().y / 2 - Container.slotSize * handler.getEq().getHeight() / 2;

        guiBounds = new RectF(guiX,
                guiY,
                guiX + handler.getEq().getWidth() * Container.slotSize,
                guiY + handler.getEq().getHeight() * Container.slotSize);

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(Container.slotSize / 2);

        quests = new ArrayList<>(1);
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

        canvas.drawBitmap(Assets.descriptionArea, null, guiBounds, null);

        if(wordsIndex < quests.get(0).getText().get(Index).length){
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
        }

        if(timer > textSpeed) {
            wordsIndex++;
        }

        String msg = "";
        int line = 1;
        for(int i = 0; i < quests.get(0).getText().get(Index).length; i++){
            if(i > wordsIndex)
                break;

            if(paint.measureText(msg) > guiBounds.right - guiBounds.left - Container.slotSize * 2) { //new line
                msg = quests.get(0).getText().get(Index)[i] + "";
                line++;
            }else
                msg += quests.get(0).getText().get(Index)[i];

            canvas.drawText(msg, guiX + Container.slotSize, guiY + Container.slotSize / 2 * line, paint);
        }
    }

    @Override
    protected void tick() {
        if(Utils.getDistance((int)handler.getEntityManager().getPlayer().getX(),
                (int)handler.getEntityManager().getPlayer().getY(),
                (int) x,
                (int) y) < questRange && !playerInRange){
            playerInRange = true;
            //handler.getQuestManager().event(new QuestEvent(QuestEvent.TALKTONPC, ID, 1));
        }else
            playerInRange = false;

        if(!handler.getInputManager().isInGUI()){
            active = false;
            Index = 0;
            wordsIndex = 0;
        }
    }

    @Override
    public void Touch(MotionEvent event) {
        if(!guiBounds.contains(event.getX(), event.getY()) || event.getAction() != MotionEvent.ACTION_UP)
            return;

        if(quests.isEmpty())
            return;

        if(quests.get(0).getText().isEmpty())
            return;

        if(wordsIndex < quests.get(0).getText().get(Index).length){ //if text isn't rendered, show all text
            wordsIndex = quests.get(0).getText().get(Index).length;
            return;
        }

        if(Index + 1 >= quests.get(0).getText().size()){ //turn off
            active = false;
            Index = 0;
            wordsIndex = 0;
            handler.getInputManager().setInGUI(false);

            if(quests.get(0).getQuest() != null){
                if(!handler.getQuestManager().getQuests().contains(quests.get(0).getQuest())){
                    handler.getQuestManager().addQuest(quests.get(0).getQuest());
                    quests.get(0).getQuestLine().setNpcShowedText(true);

                    if(!repetitive) {
                        quests.remove(0);
                        Log.println(Log.ASSERT, "NPC", "quest option removed");
                    }
                }
            }

            return;
        }

        //next text

        Index++;
        wordsIndex = 0;
    }

    @Override
    public void die() {

    }

    @Override
    public void event() {
        handler.getQuestManager().event(new QuestEvent(QuestEvent.TALKTONPC, ID, 1));

        if(quests.isEmpty())
            return;

        handler.getInputManager().setInGUI(true);
        active = !active;
        wordsIndex = 0;
        Index = 0;
    }

    public void addQuest(NpcQuestPattern pattern){
        quests.add(pattern);
    }

    public void setRepetitive(boolean repetitive) {
        this.repetitive = repetitive;
    }

    public int getID() {
        return ID;
    }

    public void clearQuests(){
        quests.clear();
    }
}
