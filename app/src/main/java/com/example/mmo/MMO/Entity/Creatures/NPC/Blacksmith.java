package com.example.mmo.MMO.Entity.Creatures.NPC;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

public class Blacksmith extends Creature {

    private Container eq;

    private boolean active = false;

    private final String description;

    private final Paint paint;

    private final int textSize;

    public Blacksmith(Handler handler, int x, int y) {
        super(handler, DEFAULTWIDTH, DEFAULTHEIGHT, x, y, Assets.blacksmith);

        textSize = (int) (handler.getEq().getAtributesPaintSize());

        Blacksmith b = this;

        eq = new Container((int) (Container.slotSize / 2 + (handler.getEq().getWidth() + 1) * Container.slotSize + handler.getEq().getTableWidth()),
                handler.getWindowSize().y / 2 - Container.slotSize / 2,
                1,
                1,
                handler){

            @Override
            public boolean canAddItem(ContainerItem item){
                if(item.getItem().haveUpgrades()){
                    active = false;
                    eq.setActive(false);

                    handler.getEq().setActive(true);

                    handler.getEq().setUpgrade(true, item, -1, true, 0, b);
                }

                return false;
            }

        };

        event = true;

        description = "Put item in\nslot below\nin order to\nupgrade it.\nIn case of\nfailure item\nwill be \ndestroyed";

        paint = new Paint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(textSize);
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

        handler.getEq().drawTableAt(canvas, handler.getEq().getWidth() * Container.slotSize + Container.slotSize / 2, handler.getEq().getY());

        //draw table with information's

        canvas.drawBitmap(Assets.descriptionArea,
                null,
                new RectF(Container.slotSize / 2 + handler.getEq().getWidth() * Container.slotSize + handler.getEq().getTableWidth(),
                        handler.getEq().getY(),
                        Container.slotSize / 2 + handler.getEq().getWidth() * Container.slotSize + handler.getEq().getTableWidth() + Container.slotSize * 3,
                        handler.getWindowSize().y / 2 - Container.slotSize / 2),
                null);

        int index = 1;
        for(String s : description.split("\\n+")){
            canvas.drawText(s,
                    Container.slotSize / 2 + handler.getEq().getWidth() * Container.slotSize + handler.getEq().getTableWidth() + 30,
                    handler.getEq().getY() + (index + 0.5f) * textSize,
                    paint);
            index++;
        }
    }

    @Override
    protected void tick() {
        if(!handler.getInputManager().isInGUI()) {
            active = false;
            eq.setActive(false);
            handler.getSkillManager().getSkillBar().setActive(true);
        }
    }

    @Override
    public void Touch(MotionEvent event) {

    }

    @Override
    public void die() {

    }

    @Override
    public void event() {
        active = !active;
        eq.setActive(active);
        handler.getInputManager().setInGUI(active);
        handler.getSkillManager().getSkillBar().setActive(!active);
        handler.getEq().setSideActive(true);
    }
}
