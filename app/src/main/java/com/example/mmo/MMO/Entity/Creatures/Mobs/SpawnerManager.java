package com.example.mmo.MMO.Entity.Creatures.Mobs;

import com.example.mmo.MMO.Handler;

import java.util.ArrayList;

public class SpawnerManager {

    private ArrayList<MobSpawner> spawners;

    public SpawnerManager(){
        spawners = new ArrayList<>();
    }

    public void tick(){
        for(MobSpawner m : spawners){
            m.tick();
        }
    }

    public void addSpawner(MobSpawner spawner){
        spawners.add(spawner);
    }

    public ArrayList<MobSpawner> getSpawners() {
        return spawners;
    }
}
