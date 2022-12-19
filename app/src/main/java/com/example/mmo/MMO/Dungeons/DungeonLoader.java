package com.example.mmo.MMO.Dungeons;

import android.util.Log;

import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DungeonLoader {

    public static DungeonGenerator loadDungeon(String path, Handler handler) throws IOException{
        BufferedReader reader = null;
        //load dungeon file
        reader = new BufferedReader(new InputStreamReader(handler.getGame().getContext().getAssets().open(path)));

        DungeonGenerator generator = new DungeonGenerator(handler);

        generator.setName(reader.readLine());
        generator.setDescription(reader.readLine());

        String s = reader.readLine();

        if(!s.equalsIgnoreCase("-")){
            String[] sizes = s.split(" ");

            generator.setSize(Integer.parseInt(sizes[0]),
                    Integer.parseInt(sizes[1]),
                    Integer.parseInt(sizes[2]),
                    Integer.parseInt(sizes[3]),
                    Integer.parseInt(sizes[4]));
        }

        String m = reader.readLine();

        if(!m.equalsIgnoreCase("-")){
            String[] materials = m.split(" ");

            generator.setMaterial(Integer.parseInt(materials[0]),
                    Integer.parseInt(materials[1]),
                    Integer.parseInt(materials[2]),
                    Integer.parseInt(materials[3]),
                    Integer.parseInt(materials[4]),
                    Integer.parseInt(materials[5]),
                    Integer.parseInt(materials[6]),
                    Integer.parseInt(materials[7]),
                    Integer.parseInt(materials[8]),
                    Integer.parseInt(materials[9]));
        }

        generator.setTexture(Assets.items[Integer.parseInt(reader.readLine())]);

        String mo = reader.readLine();

        if(!mo.equalsIgnoreCase("-")){
            String[] mobs = mo.split(" ");

            for(String a : mobs){
                generator.addMob(Integer.parseInt(a));
            }
        }

        String i = reader.readLine();

        if(!i.equalsIgnoreCase("-")){
            String[] items = i.split(" ");

            TreasurePattern pattern = new TreasurePattern();

            for(int j = 0; j < items.length; j += 3){
                pattern.addItem(Integer.parseInt(items[j]), Integer.parseInt(items[1 + j]), Integer.parseInt(items[2 + j]));
            }

            generator.setTreasurePattern(pattern);
        }

        String p = reader.readLine();

        if(!p.equalsIgnoreCase("-")){
            String[] pots = p.split(" ");

            for(String a : pots){
                generator.addPot(Integer.parseInt(a));
            }
        }

        Mob mob = MobSpawner.getMob(Integer.parseInt(reader.readLine()), 0, 0, null);

        mob.setSpawnPortalTo(Integer.parseInt(reader.readLine()));

        generator.setBoss(mob);

        return generator;
    }
}
