package com.example.mmo.MMO.Skills;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Item;
import com.example.mmo.MMO.Items.Usable.Skills.FireSlashItem;
import com.example.mmo.MMO.Items.Usable.Usable;
import com.example.mmo.MMO.Skills.Passive.DamageBoost;
import com.example.mmo.MMO.Skills.Passive.DefenceBoost;
import com.example.mmo.MMO.Skills.Summon.SummonMage;

public class SkillManager {

    private Skill[] allSkills;

    private int x, y;
    private Handler handler;

    private Container skillBar;

    private final int skillBarWidth = 9;

    public SkillManager(Handler handler){
        handler.setSkillManager(this);
        this.handler= handler;

        allSkills = new Skill[16];

        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);

        x = size.x / 2 - skillBarWidth * Container.slotSize / 2;
        y = size.y - Container.slotSize * 2;

        skillBar = new Container(x, y, skillBarWidth, 1, handler){

            @Override
            public void setPaint(int x, int y, Paint itemPaint){
                Usable u = (Usable) getItems()[x][y].getItem();

                if(u.canUse()){
                    itemPaint.setColorFilter(null);
                }else{
                    ColorFilter filter = new LightingColorFilter(0xFF7F7F7F, 0x11000000);
                    itemPaint.setColorFilter(filter);
                }
            }
        };
        skillBar.setActive(true);
        skillBar.setAllTypes(Item.USABLE);

        addSkills();
    }

    public Skill[] getAllSkills() {
        return allSkills;
    }

    public void tick(){
        for(Skill s : allSkills) {
           if(s == null)
               continue;
           s.tick();
        }
    }

    public void render(Canvas canvas){
        for(Skill s : allSkills) {
            if(s == null)
                continue;
            s.render(canvas);
        }
    }

    public void addSkills(){
        new Explosion(handler);
        new Bomb(handler);
        new IceBomb(handler);
        new FireSpin(handler);
        new Nebula(handler);
        new DamageBoost(handler);
        new DefenceBoost(handler);
        new FireSlash(handler);
        new SummonMage(handler);
    }

    public void touch(MotionEvent e){
            if(!skillBar.getBounds().contains(e.getX(), e.getY()))
                return;

            if(handler.getEq().isActive())
                return;

            float tx = e.getX() - x;

            try{
                if(skillBar.getItems()[(int) (tx) / Container.slotSize][0] != null && e.getAction() == MotionEvent.ACTION_UP){
                    skillBar.getItems()[(int) (tx) / Container.slotSize][0].use();
                }
            }catch (IndexOutOfBoundsException a) {
                return;
            }
    }

    public void setInteractive(boolean interactive) {
        skillBar.setInteract(interactive);
    }

    public Container getSkillBar() {
        return skillBar;
    }

    public void setDefaultPosition(){
        Point size = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);
        x = size.x / 2 - skillBarWidth * Container.slotSize / 2;
        y = size.y - Container.slotSize * 2;

        skillBar.setX(x);
        skillBar.setY(y);
    }

    public void setPosition(float x, float y){
        skillBar.setX((int) x);
        skillBar.setY((int) y);
    }

    public void setHollow(boolean hollow){
        skillBar.setHollow(hollow);
    }

    public void setInEq(boolean inEq){
        setInteractive(inEq);

        if(handler.getEq().isActive()){
            handler.getSkillManager().setPosition(handler.getEq().getX() + handler.getEq().getTableWidth(),
                    handler.getEq().getY() + (handler.getEq().getHeight() - 1) * Container.slotSize);
        }else {
            handler.getSkillManager().setDefaultPosition();
        }
    }
}
