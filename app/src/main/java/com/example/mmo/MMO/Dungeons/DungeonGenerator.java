package com.example.mmo.MMO.Dungeons;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Bosses.Boss;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Entity.Entity;
import com.example.mmo.MMO.Entity.Static.DungeonTreasure;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Recipe.RecipePattern;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.MMO.World.World;
import com.example.mmo.MMO.World.WorldManager;
import com.example.mmo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DungeonGenerator {

    private int minRoomSize = 7;

    private final int spaceForTreasure = 5, treasureChance = 80, mobChance = 7, minTilesPerMob = 5, skipAfterTreasure = 10, treasureRandomization = 5;

    private final Handler handler;

    private int lastRoomX, lastRoomY;

    private Mob boss;

    private ArrayList<Entity> entities;

    //tile IDs

    private int voidID = 237;

    //walls
    private int upWall = 176;
    private int rightWall = 209;
    private int downWall = 240;
    private int leftWall = 207;
    private int rightUpCorner = 177;
    private int leftUpCorner = 175;
    private int leftDownCorner = 239;
    private int rightDownCorner = 241;

    //floor

    private int floor = 173;

    //mobs

    private final ArrayList<Integer> mobs, pots;

    private TreasurePattern treasurePattern;

    //npc information

    private String name, description;

    private Bitmap texture;

    //size

    private int width = 5, height = 5, maxRoomWidth = 10, maxRoomHeight = 10, roomNumber = 10;

    public DungeonGenerator(Handler handler){
        this.handler = handler;

        mobs = new ArrayList<>();
        pots = new ArrayList<>();
    }

    public World createDungeon(){
        int[][] map = new int[width * maxRoomWidth][height * maxRoomHeight];

        entities = new ArrayList<>();

       boolean[][] rooms = new boolean[width][height];
       rooms[0][0] = true; //start room

        for(int x = 0; x < width * maxRoomWidth; x++){
            for(int y = 0; y < height * maxRoomHeight; y++){
                map[x][y] = voidID;
            }
        }

        //generate room path

        int startX = 0, startY = 0;

        int roomsCreated = 0;

        Random random = new Random();

        while(roomsCreated < roomNumber){
            int direction = random.nextInt(4);

            int oldX = startX, oldY = startY;

            switch (direction){
                case 0 :
                    startX += 1;
                    break;
                case 1 :
                    startX -= 1;
                    break;
                case 2 :
                    startY += 1;
                    break;
                case 3 :
                    startY -= 1;
                    break;
            }

            if(startX < 0)
                startX = 0;
            else if (startX >= width)
                startX = width - 1;

            if(startY < 0)
                startY = 0;
            else if (startY >= height)
                startY = height - 1;

            //generate path between rooms

            Point oldCentre = getCentreOfRoom(oldX, oldY, maxRoomWidth, maxRoomHeight);
            Point newCentre = getCentreOfRoom(startX, startY, maxRoomWidth, maxRoomHeight);

            int xBridge = oldCentre.x, yBridge = oldCentre.y;

            while(xBridge != newCentre.x || yBridge != newCentre.y){
                boolean horizontal = false, vertical = false;

                if(xBridge > newCentre.x) {
                    xBridge--;
                    vertical = true;
                }

                if(xBridge < newCentre.x){
                    xBridge++;
                    vertical = true;
                }

                if(yBridge > newCentre.y){
                    yBridge--;
                    horizontal = true;
                }

                if(yBridge < newCentre.y){
                    yBridge++;
                    horizontal = true;
                }

                if(map[xBridge][yBridge] == voidID)
                    map[xBridge][yBridge] = horizontal ? rightWall : downWall;

                if(horizontal && map[xBridge - 1][yBridge] == voidID){
                    map[xBridge - 1][yBridge] = leftWall;
                }

                if(vertical && map[xBridge][yBridge - 1] == voidID){
                    map[xBridge][yBridge - 1] = upWall;
                }
            }

            //save rooms

            if(rooms[startX][startY])
                 continue;

            rooms[startX][startY] = true;
            roomsCreated++;
        }

        lastRoomX = startX;
        lastRoomY = startY;

        World world = new World(handler,
                map,
                null,
                100,
                100,
                WorldManager.DUNGEONID);

        //generate rooms
        generateRooms(map, rooms, maxRoomWidth, maxRoomHeight, true, world);

        return world;
    }

    private Point getCentreOfRoom(int x, int y, int roomWidth, int roomHeight){
        return new Point((x + 1) * roomWidth - roomWidth / 2, (y + 1) * roomHeight - roomHeight / 2);
    }

    private void generateRooms(int[][] map, boolean[][] rooms, int maxRoomWidth, int maxRoomHeight, boolean treasures, World world){
        Random random = new Random();

        for(int tx = 0; tx < rooms.length; tx++){
            for(int ty = 0; ty < rooms[0].length; ty++){
                if(!rooms[tx][ty])
                    continue;

                int mobSpawnedLastTime = 0, treasureSpawnedLastTime = 0;

                Point newCentre = getCentreOfRoom(tx, ty, maxRoomWidth, maxRoomHeight);

                int roomWidth = 0, roomHeight = 0;

                if(tx == lastRoomX && ty == lastRoomY){ //if room is last
                    roomWidth = maxRoomWidth - 2; //generate max size
                    roomHeight = maxRoomHeight - 2;
                }else if(tx == 0 && ty == 0){ //if room is first
                    roomHeight = minRoomSize; //generate min room size
                    roomWidth = minRoomSize;
                } else {
                    roomWidth = random.nextInt(maxRoomWidth - 1); //set room width
                    if(roomWidth < minRoomSize)
                        roomWidth = minRoomSize;

                    roomHeight = random.nextInt(maxRoomHeight - 1); //set room height
                    if(roomHeight < minRoomSize)
                        roomHeight = minRoomSize;
                }

                if(tx == lastRoomX && ty == lastRoomY){

                    if(boss != null) {
                        Point p = getCentreOfRoom(tx, ty, maxRoomWidth, maxRoomHeight);
                        boss.setPosition(p.x * Tile.TILEWIDTH - boss.getWidth() / 2, p.y * Tile.TILEWIDTH - boss.getHeight() / 2);
                        entities.add(boss);
                    }
                }

                int treasureCount = roomWidth * roomHeight / spaceForTreasure < 1 ? 0 : random.nextInt(roomWidth * roomHeight / spaceForTreasure); //set treasure count in room
                int treasureSpawned = 0;

                for(int x = newCentre.x - roomWidth / 2; x < newCentre.x + roomWidth / 2; x++){
                    for(int y = newCentre.y - roomHeight / 2; y < newCentre.y + roomHeight / 2; y++){

                        //corners

                        if(y == newCentre.y - roomHeight / 2){ //left up corner
                            if(x == newCentre.x - roomWidth / 2){
                                map[x][y] = leftUpCorner;
                                continue;
                            }

                            if(x == newCentre.x + roomWidth / 2 - 1){ //right up corner
                                map[x][y] = rightUpCorner;
                                continue;
                            }
                        }

                        if(y == newCentre.y + roomHeight / 2 - 1){ //left down corner
                            if(x == newCentre.x - roomWidth / 2){
                                map[x][y] = leftDownCorner;
                                continue;
                            }

                            if(x == newCentre.x + roomWidth / 2 - 1){ //right down corner
                                map[x][y] = rightDownCorner;
                                continue;
                            }
                        }

                        //walls

                        if(y == newCentre.y - roomHeight / 2){ //top wall
                            if(map[x][y - 1] == voidID){ //make wall if above is void
                                map[x][y] = upWall;
                                continue;
                            }else { //make floor if path is above
                                map[x][y] = floor;
                                continue;
                            }
                        }

                        if(y == newCentre.y + roomHeight / 2 - 1){ //bottom wall
                            if(map[x][y + 1] == voidID){ //make wall if below is void
                                map[x][y] = downWall;
                                continue;
                            }else { //make floor if path is below
                                map[x][y] = floor;
                                continue;
                            }
                        }

                        if(x == newCentre.x - roomWidth / 2){ //left wall
                            if(map[x - 1][y] == voidID){ //make wall if next to is void
                                map[x][y] = leftWall;
                                continue;
                            }else { //make floor if path is next to
                                map[x][y] = floor;
                                continue;
                            }
                        }

                        if(x == newCentre.x + roomWidth / 2 - 1){ //right wall
                            if(map[x + 1][y] == voidID){ //make wall if void is next to
                                map[x][y] = rightWall;
                                continue;
                            }else { //make floor if path is next to
                                map[x][y] = floor;
                                continue;
                            }
                        }

                        map[x][y] = floor;

                        if(tx == 0 && ty == 0) //skip if room is spawn room
                            continue;

                        if(tx == lastRoomX && ty == lastRoomY){
                            continue;
                        }

                        //generate treasures

                        if(!pots.isEmpty() && treasurePattern != null){
                            if(treasureSpawned < treasureCount && treasures && treasureSpawnedLastTime >= skipAfterTreasure + random.nextInt(treasureRandomization)){
                                if(random.nextInt(100) <= treasureChance){
                                    treasureSpawned++;
                                    treasureSpawnedLastTime = 0;
                                    entities.add(new DungeonTreasure(handler,
                                            x * Tile.TILEWIDTH,
                                            y * Tile.TILEWIDTH,
                                            Assets.pots[pots.get(random.nextInt(pots.size()))], //take random pot texture from available
                                            treasurePattern));
                                }
                            }else
                                treasureSpawnedLastTime++;
                        }

                        //add mobs

                        if(!mobs.isEmpty() && (random.nextInt(100) <= mobChance || mobSpawnedLastTime >= minTilesPerMob)){
                            Mob e = MobSpawner.getMob(mobs.get(random.nextInt(mobs.size())),
                                    x * Tile.TILEWIDTH,
                                    y * Tile.TILEWIDTH,
                                    null);
                            assert e != null;
                            e.setAutoAgro(true);

                            entities.add(e);

                            mobSpawnedLastTime = 0;
                        }else
                            mobSpawnedLastTime++;
                    }
                }
            }
        }
    }

    public void setMaterial(int voidID, int floor, int upWall, int rightWall, int downWall, int leftWall, int rightUpCorner, int leftUpCorner, int leftDownCorner, int rightDownCorner){
        this.voidID = voidID;
        this.floor = floor;
        this.upWall = upWall;
        this.rightWall = rightWall;
        this.downWall = downWall;
        this.leftWall = leftWall;
        this.rightUpCorner = rightUpCorner;
        this.leftUpCorner = leftUpCorner;
        this.leftDownCorner = leftDownCorner;
        this.rightDownCorner = rightDownCorner;

    }

    public void setSize(int width, int height, int maxRoomWidth, int maxRoomHeight, int roomNumber){
        this.width = width;
        this.height = height;
        this.maxRoomHeight = maxRoomHeight;
        this.maxRoomWidth = maxRoomWidth;
        this.roomNumber = roomNumber;

        minRoomSize = (maxRoomHeight + maxRoomWidth) / 8;
    }

    public void loadEntities(){
        for(Entity e : entities){
            handler.getEntityManager().addEntity(e);
        }
    }

    public Point getSpawnPoint(){
        return getCentreOfRoom(0, 0, maxRoomWidth, maxRoomHeight);
    }

    public void addMob(int ID){
        mobs.add(ID);
    }

    public void addPot(int ID){
        pots.add(ID);
    }

    public void setTreasurePattern(TreasurePattern treasurePattern) {
        this.treasurePattern = treasurePattern;
    }

    public void setBoss(Mob boss) {
        this.boss = boss;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getTexture() {
        return texture;
    }

    public void setTexture(Bitmap texture) {
        this.texture = texture;
    }
}
