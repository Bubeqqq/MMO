package com.example.mmo.MMO.States;

import android.graphics.Canvas;
import android.os.Build;
import android.telephony.mbms.MbmsErrors;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Containers.ContainerManager;
import com.example.mmo.MMO.Assets.Assets;
import com.example.mmo.MMO.Camera.GameCamera;
import com.example.mmo.MMO.Cheats.Cheats;
import com.example.mmo.MMO.Dungeons.DungeonGenerator;
import com.example.mmo.MMO.Dungeons.DungeonLoader;
import com.example.mmo.MMO.Dungeons.TreasurePattern;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Entity.Creatures.Bosses.Boss;
import com.example.mmo.MMO.Entity.Creatures.Bosses.GoblinKing;
import com.example.mmo.MMO.Entity.Creatures.Creature;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mob;
import com.example.mmo.MMO.Entity.Creatures.Mobs.MobSpawner;
import com.example.mmo.MMO.Entity.Creatures.Mobs.Mobs.Mage;
import com.example.mmo.MMO.Entity.Creatures.Mobs.SpawnerManager;
import com.example.mmo.MMO.Entity.Creatures.NPC.Blacksmith;
import com.example.mmo.MMO.Entity.Creatures.NPC.DungeonGuardian;
import com.example.mmo.MMO.Entity.Creatures.NPC.NPC;
import com.example.mmo.MMO.Entity.Creatures.NPC.Shop;
import com.example.mmo.MMO.Entity.Creatures.PlayerMobs.PlayerMob;
import com.example.mmo.MMO.Entity.EntityManager;
import com.example.mmo.MMO.GUI.Announcement;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Input.InputManager;
import com.example.mmo.MMO.Items.ItemManager;
import com.example.mmo.MMO.Quests.Quest;
import com.example.mmo.MMO.Quests.QuestEvent;
import com.example.mmo.MMO.Quests.QuestLine;
import com.example.mmo.MMO.Quests.QuestManager;
import com.example.mmo.MMO.Saving.SaveManager;
import com.example.mmo.MMO.Skills.SkillManager;
import com.example.mmo.MMO.Statistics.ItemBonus;
import com.example.mmo.MMO.Statistics.Statistics;
import com.example.mmo.MMO.TimeBonuses.TimeBonusesManager;
import com.example.mmo.MMO.World.Tiles.TileManager;
import com.example.mmo.MMO.World.WorldManager;

import java.io.IOException;
import java.util.ArrayList;

public class GameState extends State{

    private GameCamera camera;

    private InputManager inputManager;

    private EntityManager entityManager;

    private WorldManager worldManager;

    private EQ eq;

    private ContainerManager containerManager;

    private ItemManager itemManager;

    private SkillManager skillManager;

    private TimeBonusesManager timeBonusesManager;

    private Announcement announcement;

    private SaveManager saveManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GameState(Handler handler) {
        super(handler);

        TileManager tileManager = new TileManager(handler);

        containerManager = new ContainerManager(handler);
        timeBonusesManager = new TimeBonusesManager(handler);

        skillManager = new SkillManager(handler);

        inputManager = new InputManager(handler);

        itemManager = new ItemManager(handler);

        camera = new GameCamera(handler);
        handler.setCamera(camera);

        entityManager = new EntityManager(handler);

        worldManager = new WorldManager(handler);

        eq = new EQ(handler);

        announcement = new Announcement(handler);
        //announcement.start();

        QuestManager questManager = new QuestManager(handler);

        saveManager = new SaveManager(handler);
        saveManager.loadSave();

        eq.addMoney(1000);

        //saveManager.start();

        Cheats c = new Cheats(handler);

        Statistics s = new Statistics(handler);
        s.refresh();

        entityManager.setLoadedEntities();
    }

    @Override
    public void tick() {
        worldManager.tick();
        entityManager.tick();
        camera.centerOnEntity(entityManager.getPlayer());
        skillManager.tick();
        timeBonusesManager.tick();
        itemManager.tick();
        saveManager.tick();
    }

    @Override
    public void render(Canvas canvas) {
        worldManager.render(canvas);
        itemManager.render(canvas);
        entityManager.render(canvas);
        skillManager.render(canvas);
        inputManager.render(canvas);
        entityManager.postRender(canvas);
        eq.render(canvas);
        containerManager.render(canvas);
        announcement.render(canvas);
    }

    @Override
    public void Touch(MotionEvent event) {
        inputManager.Touch(event);
        entityManager.touch(event);
        containerManager.touch(event);
        skillManager.touch(event);
        eq.touch(event);
    }

    @Override
    public void stop(){
        //saveManager.stop();
        saveManager.addBackgroundSave();
        //announcement.stop();
    }
}
