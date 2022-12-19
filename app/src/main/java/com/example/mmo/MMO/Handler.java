package com.example.mmo.MMO;

import android.graphics.Point;

import com.example.mmo.MMO.Containers.ContainerManager;
import com.example.mmo.MMO.Camera.GameCamera;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Entity.Creatures.Mobs.SpawnerManager;
import com.example.mmo.MMO.Entity.EntityManager;
import com.example.mmo.MMO.GUI.Announcement;
import com.example.mmo.MMO.Input.InputManager;
import com.example.mmo.MMO.Items.ItemManager;
import com.example.mmo.MMO.Quests.QuestManager;
import com.example.mmo.MMO.Skills.SkillManager;
import com.example.mmo.MMO.Statistics.Statistics;
import com.example.mmo.MMO.TimeBonuses.TimeBonusesManager;
import com.example.mmo.MMO.World.Tiles.TileManager;
import com.example.mmo.MMO.World.World;
import com.example.mmo.MMO.World.WorldManager;

public class Handler {

    private Game game;

    private Point size;

    public Handler(Game game) {
        this.game = game;
        size = new Point();
        game.getMainActivity().getWindowManager().getDefaultDisplay().getSize(size);
    }

    public Game getGame() {
        return game;
    }

    private GameCamera camera;

    public GameCamera getCamera() {
        return camera;
    }

    public void setCamera(GameCamera camera) {
        this.camera = camera;
    }

    private TileManager tileManager;

    public TileManager getTileManager() {
        return tileManager;
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    private World world;

    public World getWorld() {
        return worldManager.getCurrentWorld();
    }

    private InputManager inputManager;

    public InputManager getInputManager() {
        return inputManager;
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private WorldManager worldManager;

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public void setWorldManager(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    private ItemManager itemManager;

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    private Statistics statistics;

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    private SkillManager skillManager;

    public SkillManager getSkillManager() {
        return skillManager;
    }

    public void setSkillManager(SkillManager skillManager) {
        this.skillManager = skillManager;
    }

    private ContainerManager containerManager;

    public ContainerManager getContainerManager() {
        return containerManager;
    }

    public void setContainerManager(ContainerManager containerManager) {
        this.containerManager = containerManager;
    }

    private EQ eq;

    public EQ getEq() {
        return eq;
    }

    public void setEq(EQ eq) {
        this.eq = eq;
    }

    private TimeBonusesManager timeBonusesManager;

    public TimeBonusesManager getTimeBonusesManager() {
        return timeBonusesManager;
    }

    public void setTimeBonusesManager(TimeBonusesManager timeBonusesManager) {
        this.timeBonusesManager = timeBonusesManager;
    }

    public Point getWindowSize() {
        return size;
    }

    private QuestManager questManager;

    public QuestManager getQuestManager() {
        return questManager;
    }

    public void setQuestManager(QuestManager questManager) {
        this.questManager = questManager;
    }

    private Announcement announcement;

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public void addAnnouncement(String s){
        announcement.addAnnouncement(s);
    }
}
