package com.example.mmo.MMO.World;

import android.graphics.Canvas;
import android.util.Log;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Dungeons.DungeonGenerator;
import com.example.mmo.MMO.Dungeons.TreasurePattern;
import com.example.mmo.MMO.Entity.Creatures.Bosses.Boss;
import com.example.mmo.MMO.Entity.Creatures.Bosses.GoblinKing;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;

import java.io.IOException;

public class WorldManager {

    private World currentWorld;

    private World[] worlds = new World[64];

    private Handler handler;

    public final static int DUNGEONID = 5;

    public WorldManager(Handler handler){
        this.handler = handler;
        handler.setWorldManager(this);

        try {
            loadWorlds();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){
        currentWorld.tick();
    }

    public void render(Canvas canvas){
        currentWorld.render(canvas);
    }

    public void loadWorlds() throws IOException {
        //id5 is dungeon id

        World mainWorld = new World(handler,
                handler.getGame().getContext().getAssets().open("Worlds/Main1.txt"),
                handler.getGame().getContext().getAssets().open("Worlds/Main2.txt"),
                "Worlds/MainNPC.txt",
                1);

        World hell = new World(handler,
                handler.getGame().getContext().getAssets().open("Worlds/Hell1.txt"),
                handler.getGame().getContext().getAssets().open("Worlds/Hell2.txt"),
                "Worlds/HellNPC.txt",
                2);

        DungeonGenerator generator = new DungeonGenerator(handler);

        generator.addMob(Mob.MAGE);
        generator.addMob(Mob.PINKGOBLIN);

        TreasurePattern pattern = new TreasurePattern();
        pattern.addItem(0, 1, 5);
        pattern.addItem(0, 1, 6);
        pattern.addItem(0, 1, 7);
        pattern.addItem(0, 1, 8);
        pattern.addItem(63, 1, 10);

        generator.setTreasurePattern(pattern);

        Boss b = new GoblinKing(handler, 0, 0);
        b.setSpawnPortalTo(1);

        generator.setBoss(b);

        generator.addPot(0);
        generator.addPot(1);
        generator.addPot(2);

        //World tests = generator.createDungeon(5, 5,15, 14, 15, 0);
    }

    public void setCurrentWorld(int ID){
        if(currentWorld != null){
            handler.getEntityManager().clear();
        }

        Log.println(Log.ASSERT, "World Manager",  "Loaded World with ID of " + ID);

        currentWorld = worlds[ID];
        currentWorld.setPlayerSpawn();

        handler.getItemManager().clearDroppedItems();

        if(currentWorld != null){
            for(MobSpawner m : currentWorld.getSpawnerManager().getSpawners()){
                m.getMobs().clear();
            }
        }

        try {
            currentWorld.loadNPCs();
            handler.getQuestManager().loadLineQuests();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World[] getWorlds() {
        return worlds;
    }

    public World getCurrentWorld() {
        return currentWorld;
    }
}
