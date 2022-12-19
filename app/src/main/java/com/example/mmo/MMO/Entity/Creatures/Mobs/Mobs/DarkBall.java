package com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;

public class DarkBall extends Mob {

    public DarkBall(Handler handler, int width, int height, int x, int y, MobSpawner spawner) {
        super(handler, width, height, x, y, Assets.ball, spawner, DARKBALL);

        attackDelay = 1000;
        damage = 50000;
        setHealth(1000000);
        speed = 1f;
        defense = 25000;
        range = 200;
        lvl = 89;
        addDrop(new Drop(0, 15, 1, 3));
        addDrop(new Drop(89, 30, 1, 0));
        addDrop(new Drop(94, 10, 1, 0));
        addDrop(new Drop(96, 75, 1, 0));
    }
}
