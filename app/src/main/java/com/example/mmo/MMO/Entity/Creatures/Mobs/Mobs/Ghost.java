package com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;

public class Ghost extends Mob {

    public Ghost(Handler handler, int width, int height, int x, int y, MobSpawner spawner) {
        super(handler, width, height, x, y, Assets.ghost, spawner, GHOST);

        attackDelay = 4000;
        damage = 400000;
        setHealth(300000);
        speed = 0.3f;
        defense = 0;
        range = 400;
        lvl = 99;
        addDrop(new Drop(0, 15, 1, 3));
        addDrop(new Drop(89, 30, 1, 0));
        addDrop(new Drop(94, 10, 1, 0));
        addDrop(new Drop(96, 75, 1, 0));
    }
}
