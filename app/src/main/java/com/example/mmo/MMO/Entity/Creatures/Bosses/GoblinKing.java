package com.example.mmo.MMO.Entity.Creatures.Bosses;

import android.graphics.Bitmap;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Mage;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.PinkGoblin;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Utils;
import com.example.mmo.MMO.World.Tiles.Tile;

public class GoblinKing extends Boss{

    public GoblinKing(Handler handler, int x, int y) {
        super(handler, (int) (Creature.DEFAULTWIDTH * 1.3f), (int) (Creature.DEFAULTHEIGHT * 1.3f), x, y, Assets.goblinKing, GOBLINKING);

        setHealth(20000000);
        attackDelay = 1000;
        damage = 150000;
        speed = 1.4f;
        range = Tile.TILEWIDTH * 4;
        lvl = 999;
        healthRegen = 13000;
        specialAttack = 10;
    }

    @Override
    protected void attackOne() {

    }

    @Override
    protected void attackTwo() {
        Mob m = new Mage(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, (int)x + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, (int)y + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, null);
        m.setAutoAgro(true);

        handler.getEntityManager().addEntity(m);
    }

    @Override
    protected void attackThree() {
        Mob m = new Mage(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, (int)x + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, (int)y + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, null);
        m.setAutoAgro(true);

        handler.getEntityManager().addEntity(m);

        m = new PinkGoblin(handler, Creature.DEFAULTWIDTH, Creature.DEFAULTHEIGHT, (int)x + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, (int)y + random.nextInt(Tile.TILEWIDTH * 2) - Tile.TILEWIDTH, null);
        m.setAutoAgro(true);

        handler.getEntityManager().addEntity(m);
    }

    @Override
    protected void defaultAttack() {
        if(Utils.getDistance((int)x, (int)y, (int) handler.getEntityManager().getPlayer().getX(), (int) handler.getEntityManager().getPlayer().getY()) <= range){
            handler.getEntityManager().getPlayer().hurt(damage, true, true);
        }
    }
}
