package com.example.mmo.MMO.Items.Usable.Items;

import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Mage;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Pumpkin;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.SkeletonSoldier;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Zombie;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Usable.Usable;

public class MobSpawn extends Usable {

    public MobSpawn(Handler handler) {
        super(87, handler);

        oneTimeUsable = true;
    }

    @Override
    public String getDescription(int lvl, int amount) {
        String s = "Spawn";

        switch (lvl){
            case 1 :{ s+="\nMage";
                    break;}
            case 2 :{ s+="\nSkeleton Soldier";
                    break;}
            case 3 :{ s+="\nPumpkin";
                    break;}
            default:{ s+="\nZombie";
                    break;}
        }

        return s;
    }

    @Override
    public String getName() {
        return "MobSpawn";
    }

    @Override
    public void use(int lvl) {
        int px = (int) handler.getEntityManager().getPlayer().getX();
        int py = (int) handler.getEntityManager().getPlayer().getY();
        switch (lvl){
            case 1 : {
                handler.getEntityManager().addEntity(new Mage(handler, 100, 100, px, py, null));
                break;
            }

            case 2 : {
                handler.getEntityManager().addEntity(new SkeletonSoldier(handler, 100, 100, px, py, null));
                break;
            }

            case 3 : {
                handler.getEntityManager().addEntity(new Pumpkin(handler, 100, 100, px, py, null));
                break;
            }

            default: handler.getEntityManager().addEntity(new Zombie(handler, 100, 100, px, py, null));
        }
    }

    @Override
    public boolean canUse() {
        return true;
    }
}
