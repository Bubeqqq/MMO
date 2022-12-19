package com.example.mmo.MMO.Entity.Creatures.Mobs;

import android.text.SpanWatcher;
import android.util.Log;

import com.example.mmo.MMO.Entity.Creatures.Bosses.GoblinKing;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.DarkBall;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Ghost;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Mage;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.PinkGoblin;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Pumpkin;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.SkeletonSoldier;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Zombie;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.util.ArrayList;

public class MobSpawner {

    private long lastTime;
    private long timer;
    private final long spawnDelay;

    private final ArrayList<Mob> mobs;

    private int x, y;

    private final short mobAmount;
    private final int range = Tile.TILEWIDTH, ID;

    private static Handler handler;

    public MobSpawner(Handler handler, int x, int y, short mobAmount, long spawnDelay, int ID){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.mobAmount = mobAmount;
        this.spawnDelay = spawnDelay * 1000;

        mobs = new ArrayList<>();

        lastTime = System.currentTimeMillis();
    }

    public void tick(){
        if(mobs.isEmpty()){
            timer += System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();

            if(timer >= spawnDelay){
                short spawned = 0;
                for(int x = this.x - range; x < this.x + range; x += Tile.TILEWIDTH){
                    for(int y = this.y - range; y < this.y + range; y += Tile.TILEWIDTH){
                        if(!handler.getTileManager().getTiles()[handler.getWorld().getWorld()[x / Tile.TILEWIDTH][y / Tile.TILEWIDTH]].isSolid()){
                            Mob mob = getMob(ID, x, y, this);
                            mobs.add(mob);
                            handler.getEntityManager().addEntity(mob);
                            spawned++;

                            if(spawned >= mobAmount) {
                                timer = 0;
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void removeMob(Mob mob){
        mobs.remove(mob);

        if(mobs.isEmpty())
            lastTime = System.currentTimeMillis();
    }

    public static Mob getMob(int ID, int x, int y, MobSpawner spawner){
        if(handler == null)
            return null;

        switch (ID){
            case Mob.MAGE:return new Mage(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.PUMPKIN:return new Pumpkin(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.SKELETONSOLDIER:return new SkeletonSoldier(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.DARKBALL:return new DarkBall(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.GHOST:return new Ghost(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.PINKGOBLIN:return new PinkGoblin(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
            case Mob.GOBLINKING:return new GoblinKing(handler, x, y);
            default :return new Zombie(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, x, y, spawner);
        }
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }
}
