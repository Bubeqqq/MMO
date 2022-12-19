package com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Drop;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;
import com.example.mmo.R;

public class Zombie extends Mob {
    public Zombie(Handler handler, int width, int height, int x, int y, MobSpawner spawner) {
        super(handler, width, height, x, y, Assets.zombie, spawner, ZOMBIE);

        attackDelay = 2000;
        damage = 100000;
        setHealth(3000000);
        speed = 0.5f;
        defense = 0;
        range = 200;
        lvl = 99;
        addDrop(new Drop(0, 15, 1, 3));
        addDrop(new Drop(89, 30, 1, 0));
        addDrop(new Drop(94, 10, 1, 0));
        addDrop(new Drop(96, 75, 1, 0));
    }
}
