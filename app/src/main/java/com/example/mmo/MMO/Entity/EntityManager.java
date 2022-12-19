package com.example.mmo.MMO.Entity;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Entity.Creatures.Mobs.SpawnerManager;
import com.example.mmo.MMO.Entity.Creatures.Player;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.World;
import com.example.mmo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;

public class EntityManager {

    private final ArrayList<Entity> entities;
    private final ArrayList<Entity> loadedEntities;

    private final Player player;

    private final Point p;

    private final Handler handler;

    private final TextPaint paint;

    private Creature selectedEntity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public EntityManager(Handler handler){
        handler.setEntityManager(this);
        this.handler = handler;

        entities = new ArrayList<>();
        loadedEntities = new ArrayList<>();

        player = new Player(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, 3000, 3000);
        entities.add(player);

        p = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(p);

        paint = new TextPaint();
        paint.setColor(handler.getGame().getResources().getColor(R.color.white));
        paint.setTextSize(40);
        //paint.setTypeface(handler.getGame().getResources().getFont(R.font.pixel));
    }

    public void tick(){
        ArrayList<Entity> loadedEntities = new ArrayList<>(this.loadedEntities);
        for(Entity e : loadedEntities){
            if(e == player)
                continue;

            e.animationTick();
            e.entityTick();
        }

        player.animationTick();
        player.entityTick();
    }

    public void render(Canvas canvas){
        ArrayList<Entity> loadedEntities = new ArrayList<>(this.loadedEntities);

        for(Entity e : loadedEntities){
            if(e == player)
                continue;

            e.entityRender(canvas);
        }

        player.entityRender(canvas);

        if(selectedEntity != null) {
            canvas.drawBitmap(Assets.emptyButton, 1500, 10, null);
            canvas.drawBitmap(selectedEntity.getDefaultTexture(),
                    null,
                    new RectF(1510,
                            25,
                            1600,
                            110),
                    null);
            selectedEntity.getHealthBar().RenderAtPoint(canvas, 1600, 25, 300, 100);
            canvas.drawText("LV:" + selectedEntity.getLvl(), 1905, 85, paint);
        }
    }

    public void postRender(Canvas canvas){
        player.postRender(canvas);

        for(Entity e : loadedEntities){
            if(e == player)
                continue;

            e.postRender(canvas);
        }
    }

    public void touch(MotionEvent event){
        ArrayList<Entity> loadedEntities = new ArrayList<>(this.loadedEntities);

        for (Entity e : loadedEntities) {
            if (e == player)
                continue;

            if(e != null)
                e.Touch(event);
        }

        player.Touch(event);
    }

    public void setLoadedEntities(){
        loadedEntities.clear();
        for(Entity e : entities){
            if(e.isLoadAlways()){
                loadedEntities.add(e);
                continue;
            }

            if(e.getX() - handler.getCamera().getxOffset() + e.getWidth() < 0 || e.getX() - handler.getCamera().getxOffset() - e.getWidth() > p.x)
                continue;
            if(e.getY() - handler.getCamera().getyOffset() + e.getHeight() < 0 || e.getY() - handler.getCamera().getyOffset() - e.getHeight() > p.y)
                continue;

            loadedEntities.add(e);
        }

        isSelectedEntityLoaded();
    }

    public void addEntity(Entity e){
        entities.add(e);
        setLoadedEntities();
    }

    public void removeEntity(Entity e){
        entities.remove(e);
        setLoadedEntities();
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Entity> getEntities() {
        return loadedEntities;
    }

    public void setSelectedEntity(Creature selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    private void isSelectedEntityLoaded(){
        if(!loadedEntities.contains(selectedEntity))
            selectedEntity = null;
    }

    public void clear(){
        entities.clear();
        entities.add(player);

        setLoadedEntities();
    }
}
