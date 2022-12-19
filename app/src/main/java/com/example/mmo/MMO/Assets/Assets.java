package com.example.mmo.MMO.Assets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.World.Tiles.Tile;
import com.example.mmo.R;

public class Assets {

    private Handler handler;

    //Textures

    public static Bitmap tileset, tilesetObjects;

    public static Bitmap[] items = new Bitmap[512];

    public static Bitmap[] uiGame = new Bitmap[16];

    public static Bitmap table;

    public static Bitmap equipment, bar, health, okButton, backButton, emptyButton, expBar, descriptionArea;

    public static Bitmap[] player, zombie, explosion, boom, ice, fireSpin, nebula,
                            boost, protection, shop, hit, skeleton_soldier, mage, pumpkin, portal,
                            ghost, pinkGoblin, ball, fireSlash, questCentre, blacksmith, goblinKing;

    public static Bitmap[][] npcs, pots;

    public Assets(Handler handler){
        this.handler = handler;

        loadAssets();
    }

    private void loadAssets(){
        //load NPCs

        npcs = new Bitmap[2][];

        Bitmap npc = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.npc0); //add npc
        npcs[0] = LoadToBitmapArray(npc, 4);
        npc = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.npc1); //add npc
        npcs[1] = LoadToBitmapArray(npc, 4);

        //load single bitmaps

        tileset = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.tileset);
        emptyButton = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.empty_button);
        tilesetObjects = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.tileset_objects);
        okButton = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.ok_button);
        backButton = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.back_button);

        bar = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.bar);
        health = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.health_bar);
        expBar = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.exp_bar);
        descriptionArea = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.description_area);

        table = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.table);
        equipment = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.equipment);

        //load items

        int nextIndex = 0;

        Bitmap swords = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.swords);

        nextIndex += LoadToBitmapArrayAtIndex(swords, 8, items, nextIndex);

        Bitmap i = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.items); //add items
        nextIndex += LoadToBitmapArrayAtIndex(i, 6, items, nextIndex);

        Bitmap ar = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.armor); //add items

        nextIndex += LoadToBitmapArrayAtIndex(ar, 8, items, nextIndex);

        Bitmap skills = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.skills); //add skills

        nextIndex += LoadToBitmapArrayAtIndex(skills, 8, items, nextIndex);

        Bitmap chests = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.treasure_chests); //add chests

        nextIndex += LoadToBitmapArrayAtIndex(chests, 6, items, nextIndex);

        Log.println(Log.ASSERT, "Assets", "Loaded " + nextIndex + "Items");

        //load arrays

        Bitmap uiGame = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.game_ui);
        this.uiGame = LoadToBitmapArray(uiGame, 4);

        Bitmap bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.player); //add items
        player = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.blacksmith); //add items
        blacksmith = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.quest_centre); //add items
        questCentre = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.zombie); //add items
        zombie = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.shop); //add items
        shop = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.explosion); //add items
        explosion = LoadToBitmapArray(bi, 8);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.boom); //add items
        boom = LoadToBitmapArray(bi, 1);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.ice); //add items
        ice = LoadToBitmapArray(bi, 1);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.fire_slash); //add items
        fireSlash = LoadToBitmapArray(bi, 7);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.firespin); //add items
        fireSpin = LoadToBitmapArray(bi, 8);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.nebula); //add items
        nebula = LoadToBitmapArray(bi, 8);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.boost); //add items
        boost = LoadToBitmapArray(bi, 10);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.protection); //add items
        protection = LoadToBitmapArray(bi, 8);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.hit); //add items
        hit = LoadToBitmapArray(bi, 6);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.pumpkin); //add items
        pumpkin = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.mage); //add items
        mage = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.skeleton_soldier); //add items
        skeleton_soldier = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.portal); //add items
        portal = LoadToBitmapArray(bi, 6);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.ball); //add items
        ball = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.pink_goblin); //add items
        pinkGoblin = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.goblin_king); //add items
        goblinKing = LoadToBitmapArray(bi, 4);

        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.ghost); //add items
        ghost = LoadToBitmapArray(bi, 4);

        //load pots
        bi = BitmapFactory.decodeResource(handler.getGame().getResources(), R.drawable.pots); //add items
        pots = new Bitmap[12][4];
        for(int j = 0; j < 12; j++){
            pots[j] = LoadToBitmapArray(bi, 12, 4, j * 4);
        }
    }

    private Bitmap[] LoadToBitmapArray(Bitmap b, int height){
        int Index = 0;
        int scale = b.getHeight() / height;

        Bitmap[] a = new Bitmap[b.getHeight() / scale * b.getWidth() / scale];

        for(int y = 0; y < b.getHeight() / scale; y++) {
            for (int x = 0; x < b.getWidth() / scale; x++) {
                Bitmap bitmap = Bitmap.createBitmap(b, x * scale,y * scale, scale, scale);
                a[Index] = bitmap;
                Index++;
            }
        }

        return a;
    }

    private Bitmap[] LoadToBitmapArray(Bitmap b, int height, int images, int start){
        int Index = 0;
        int scale = b.getHeight() / height;

        Bitmap[] a = new Bitmap[images];

        int image = 0;
        for(int y = 0; y < b.getHeight() / scale; y++) {
            for (int x = 0; x < b.getWidth() / scale; x++) {
                if(image < start){
                    image++;
                    continue;
                }
                Bitmap bitmap = Bitmap.createBitmap(b, x * scale,y * scale, scale, scale);
                a[Index] = bitmap;
                Index++;

                if(Index >= images)
                    return a;
            }
        }

        return a;
    }

    private int LoadToBitmapArrayAtIndex(Bitmap b, int height, Bitmap[] array, int startIndex){
        int Index = startIndex;
        int scale = b.getHeight() / height;
        for(int y = 0; y < b.getHeight() / scale; y++) { //add player
            for (int x = 0; x < b.getWidth() / scale; x++) {
                Bitmap bitmap = Bitmap.createBitmap(b, x * scale,y * scale, scale, scale);
                array[Index] = bitmap;
                Index++;
            }
        }

        return Index - startIndex;
    }
}
