package com.example.mmo.MMO.World.Tiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

import java.io.IOException;

public class TileManager {

    private Tile[] tiles, objects;

    protected Handler handler;

    private int tileTexture = 176;

    public TileManager(Handler handler){
        this.handler = handler;

        handler.setTileManager(this);
        try {
            setTiles();
            new TileAtributes(tiles, objects);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTiles() throws IOException {
        tiles = new Tile[1024];
        objects = new Tile[1024];


        addTiles(Assets.tileset, true, 32);
        addTiles(Assets.tilesetObjects, false, 32);
    }

    private void addTiles(Bitmap b, boolean firstLayer, int height){
        int nextIndex = 0;
        tileTexture = b.getHeight() / height;
        for(int y = 0; y < b.getHeight() / tileTexture; y++){
            for(int x = 0; x < b.getWidth() / tileTexture; x++){
                Bitmap texture = Bitmap.createBitmap(b, x * tileTexture,y * tileTexture, tileTexture - 4, tileTexture - 4);
                new Tile(nextIndex, texture, handler, firstLayer);
                nextIndex++;
            }
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public Tile[] getSecondLayer(){
        return objects;
    }
}
