package com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;

public class Mage extends Mob {

    public Mage(Handler handler, int width, int height, int x, int y, MobSpawner spawner) {
        super(handler, width, height, x, y, Assets.mage, spawner, MAGE);

        attackDelay = 3000;
        damage = 300000;
        setHealth(10000);
        defense = 3000;
        range = 500;
        lvl = 197;
        exp = 500;
        addDrop(new Drop(0, 15, 1, 3));
        addDrop(new Drop(89, 30, 1, 0));
        addDrop(new Drop(94, 10, 1, 0));
        addDrop(new Drop(96, 75, 1, 0));
    }
}
