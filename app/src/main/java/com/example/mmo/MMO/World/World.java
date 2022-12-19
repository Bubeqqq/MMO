package com.example.mmo.MMO.World;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Dungeons.DungeonLoader;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Entity.Creatures.Mobs.SpawnerManager;
import com.example.mmo.MMO.Entity.Creatures.NPC.Blacksmith;
import com.example.mmo.MMO.Entity.Creatures.NPC.DungeonGuardian;
import com.example.mmo.MMO.Entity.Creatures.NPC.NPC;
import com.example.mmo.MMO.Entity.Creatures.NPC.QuestCentre;
import com.example.mmo.MMO.Entity.Creatures.NPC.Shop;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Portals.Portal;
import com.example.mmo.MMO.World.Portals.PortalsManager;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class World {

    private int width, height;

    private float spawnX, spawnY;

    private int[][] world, secondLayer;

    private PortalsManager portals;

    private Handler handler;

    private Point p;

    private int ID;

    private SpawnerManager spawnerManager;

    private String NPCs;

    public World(Handler handler, InputStream worldStream, InputStream secondWorldStream, String NPCs ,int ID){
        this.handler = handler;
        this.ID = ID;
        this.NPCs = NPCs;

        handler.getWorldManager().getWorlds()[ID] = this;

        p = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(p);

        portals = new PortalsManager();

        spawnerManager = new SpawnerManager();

        try {
            loadWorld(worldStream, secondWorldStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public World(Handler handler, int[][] firstLayer, int[][] secondLayer, int spawnX, int spawnY ,int ID){
        this.handler = handler;
        this.ID = ID;
        this.world = firstLayer;
        this.spawnX = spawnX;
        this.spawnY = spawnY;


        width = firstLayer.length;
        height = firstLayer[0].length;

        if(secondLayer != null)
            this.secondLayer = secondLayer;
        else{
            this.secondLayer = new int[width][height];
            for(int x = 0; x < width; x++){
                for(int y = 0; y < height; y++){
                    this.secondLayer[x][y] = -1;
                }
            }
        }

        handler.getWorldManager().getWorlds()[ID] = this;

        p = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(p);

        portals = new PortalsManager();

        spawnerManager = new SpawnerManager();
    }

    public void render(Canvas canvas){
        int xStart = (int) Math.max(0,  handler.getCamera().getxOffset() / Tile.TILEWIDTH);
        int xEnd = (int) Math.min(width, (handler.getCamera().getxOffset() + handler.getWindowSize().x) / Tile.TILEWIDTH + 1);
        int yStart = (int) Math.max(0,  handler.getCamera().getyOffset() / Tile.TILEWIDTH);
        int yEnd = (int) Math.min(height, (handler.getCamera().getyOffset() + handler.getWindowSize().y) / Tile.TILEWIDTH + 1);

        for(int y = yStart; y < yEnd; y++){
            for(int x = xStart; x < xEnd; x++){
                if(world[x][y] != -1)
                    handler.getTileManager().getTiles()[world[x][y]].render(canvas, x, y);

                if(secondLayer[x][y] != -1){
                    handler.getTileManager().getSecondLayer()[secondLayer[x][y]].render(canvas, x, y);
                }
            }
        }

        portals.render(canvas);
    }

    public void tick(){
        portals.tick();
        spawnerManager.tick();
    }

    public void loadWorld(InputStream worldStream, InputStream secondLayerStream) throws IOException{
        //first layer
        BufferedReader reader = new BufferedReader(new InputStreamReader(worldStream));

        String tilesAsString = new String();

        while(reader.ready()){
            tilesAsString += reader.readLine() + " ";
        }

        String[] tiles = tilesAsString.split(" ");

        //seconds layer

        reader = new BufferedReader(new InputStreamReader(secondLayerStream));

        StringBuilder secondAsString = new StringBuilder(new String());

        while(reader.ready()){
            secondAsString.append(reader.readLine()).append(" ");
        }

        String[] secondsTiles = secondAsString.toString().split(" ");

        //

        width = Integer.parseInt(tiles[0]);
        height = Integer.parseInt(tiles[1]);

        spawnX = Integer.parseInt(secondsTiles[0]) * Tile.TILEWIDTH;
        spawnY = Integer.parseInt(secondsTiles[1]) * Tile.TILEWIDTH;

        world = new int[width][height];
        secondLayer = new int[width][height];

        Log.println(Log.ASSERT, "World", String.valueOf(width * height));

        for(int y = 0, i = 0; y < height; y++){
            for (int x = 0; x < width; x++, i++){
                world[x][y] = Integer.parseInt(tiles[i + 2]);


                int a = Integer.parseInt(secondsTiles[i + 2]);

                if(a >= 5000){
                    portals.addPortal(new Portal(handler, x * Tile.TILEWIDTH, y * Tile.TILEWIDTH, a - 5000));
                    secondLayer[x][y] = -1;
                }else if(a > 1023){
                    spawnerManager.addSpawner(new MobSpawner(handler, x * Tile.TILEWIDTH, y * Tile.TILEWIDTH, (short) 4, 1, a - 1023));
                    secondLayer[x][y] = -1;
                }else {
                    secondLayer[x][y] = a;
                }
            }
        }

        reader.close();
        worldStream.close();
    }

    public void loadNPCs() throws IOException {
        if(NPCs == null)
            return;

        InputStream npc = handler.getGame().getContext().getAssets().open(NPCs);

        BufferedReader reader = new BufferedReader(new InputStreamReader(npc));

        StringBuilder tilesAsString = new StringBuilder("");

        while(reader.ready()){
            tilesAsString.append(reader.readLine()).append(" ");
        }

        reader.close();
        npc.close();

        String[] numbers = tilesAsString.toString().split(" ");

        for(int i = 0; i < numbers.length; i++){
            switch (numbers[i]){
                case "shop" :{
                    i += addShop(numbers, i);
                    break;
                }
                case "blacksmith":{
                    int x = Integer.parseInt(numbers[i + 1]) * Tile.TILEWIDTH;
                    int y = Integer.parseInt(numbers[i + 2]) * Tile.TILEWIDTH;
                    handler.getEntityManager().addEntity(new Blacksmith(handler, x, y));
                    i += 2;
                    break;
                }
                case "quest":{
                    int x = Integer.parseInt(numbers[i + 1]) * Tile.TILEWIDTH;
                    int y = Integer.parseInt(numbers[i + 2]) * Tile.TILEWIDTH;
                    handler.getEntityManager().addEntity(new QuestCentre(handler, x, y));
                    i += 2;
                    break;
                }
                case "npc":{
                    int x = Integer.parseInt(numbers[i + 1]) * Tile.TILEWIDTH;
                    int y = Integer.parseInt(numbers[i + 2]) * Tile.TILEWIDTH;
                    int ID = Integer.parseInt(numbers[i + 3]);

                    handler.getEntityManager().addEntity(new NPC(handler, x, y, ID));
                    i += 3;
                    break;
                }
                case "dungeon":{
                    int x = Integer.parseInt(numbers[i + 1]) * Tile.TILEWIDTH;
                    int y = Integer.parseInt(numbers[i + 2]) * Tile.TILEWIDTH;
                    DungeonGuardian guardian = new DungeonGuardian(handler, x, y);
                    int dungeonNumber = Integer.parseInt(numbers[i + 3]);

                    for(int j = i + 4; j < i + 4 + dungeonNumber; j++){
                        guardian.addDungeon(DungeonLoader.loadDungeon("Dungeons/Dungeon" + numbers[j] + ".txt", handler));
                    }

                    handler.getEntityManager().addEntity(guardian);

                    i += 3 + dungeonNumber;
                }
            }
        }
    }

    private int addShop(String[] numbers, int index){
        int x = Integer.parseInt(numbers[index + 1]) * Tile.TILEWIDTH;
        int y = Integer.parseInt(numbers[index + 2]) * Tile.TILEWIDTH;

        Shop shop = new Shop(handler, x, y);

        int items = Integer.parseInt(numbers[index + 3]);

        for(int i = 0; i < items * 3; i += 3){
            int ID = Integer.parseInt(numbers[index + i + 4]);
            int amount = Integer.parseInt(numbers[index + i + 5]);
            int upgrade = Integer.parseInt(numbers[index + i + 6]);
            shop.addShopItem(new ContainerItem(ID, handler, amount, upgrade, null));
        }

        handler.getEntityManager().addEntity(shop);

        return items;
    }

    public void setPlayerSpawn(){
        handler.getEntityManager().getPlayer().setPosition(spawnX, spawnY);
    }

    public int[][] getWorld() {
        return world;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[][] getSecondLayer() {
        return secondLayer;
    }

    public int getID() {
        return ID;
    }

    public SpawnerManager getSpawnerManager() {
        return spawnerManager;
    }

    public float getSpawnX() {
        return spawnX;
    }

    public float getSpawnY() {
        return spawnY;
    }

    public void setWorld(int[][] world) {
        this.world = world;
    }

    public PortalsManager getPortals() {
        return portals;
    }
}
