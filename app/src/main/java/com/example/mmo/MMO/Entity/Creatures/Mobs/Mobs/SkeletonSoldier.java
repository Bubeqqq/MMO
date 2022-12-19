package com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;

public class SkeletonSoldier extends Mob {

    public SkeletonSoldier(Handler handler, int width, int height, int x, int y, MobSpawner spawner) {
        super(handler, width, height, x, y, Assets.skeleton_soldier, spawner, SKELETONSOLDIER);

        attackDelay = 1500;
        damage = 150000;
        setHealth(20000);
        defense = 150000;
        range = 200;
        lvl = 84;
        addDrop(new Drop(0, 15, 1, 3));
        addDrop(new Drop(89, 30, 1, 0));
        addDrop(new Drop(94, 10, 1, 0));
        addDrop(new Drop(96, 75, 1, 0));
        addDrop(new Drop(98, 3, 1, 0));
    }
}
