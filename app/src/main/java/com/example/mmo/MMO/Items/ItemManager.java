package com.example.mmo.MMO.Items;

import android.graphics.Canvas;
import android.graphics.Point;

import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Items.Chestplate.BubaChestplatePlus;
import com.example.mmo.MMO.Items.Chestplate.LeatherChestplate;
import com.example.mmo.MMO.Items.Chests.WoodenChest;
import com.example.mmo.MMO.Items.Chests.WoodenChestOpened;
import com.example.mmo.MMO.Items.DraggableEvents.AddBonus;
import com.example.mmo.MMO.Items.DraggableEvents.ChangeBonus;
import com.example.mmo.MMO.Items.DraggableEvents.Key;
import com.example.mmo.MMO.Items.DraggableEvents.TransformBook;
import com.example.mmo.MMO.Items.DraggableEvents.UpgradeStone;
import com.example.mmo.MMO.Items.DraggableEvents.UpgradeStonePlus;
import com.example.mmo.MMO.Items.Helmet.BubaHelmet;
import com.example.mmo.MMO.Items.Necklace.BubaNecklace;
import com.example.mmo.MMO.Items.Necklace.JadeNecklace;
import com.example.mmo.MMO.Items.NoUse.Gold;
import com.example.mmo.MMO.Items.NoUse.SackOfGold;
import com.example.mmo.MMO.Items.NoUse.Silver;
import com.example.mmo.MMO.Items.Orbs.BubaOrb;
import com.example.mmo.MMO.Items.Orbs.CalciteOrb;
import com.example.mmo.MMO.Items.Shield.BubaShield;
import com.example.mmo.MMO.Items.Swords.AbsoluteSword;
import com.example.mmo.MMO.Items.Swords.BarbedSword;
import com.example.mmo.MMO.Items.Swords.BasicSword;
import com.example.mmo.MMO.Items.Swords.BigEnderSword;
import com.example.mmo.MMO.Items.Swords.BigGoldSword;
import com.example.mmo.MMO.Items.Swords.BigNecromantaSword;
import com.example.mmo.MMO.Items.Swords.BigSteelSword;
import com.example.mmo.MMO.Items.Swords.BloodSword;
import com.example.mmo.MMO.Items.Swords.BronzeKnife;
import com.example.mmo.MMO.Items.Swords.BronzeSword;
import com.example.mmo.MMO.Items.Swords.BronzeSwordPlus;
import com.example.mmo.MMO.Items.Swords.BubaSword;
import com.example.mmo.MMO.Items.Swords.ButterSword;
import com.example.mmo.MMO.Items.Swords.CrystalSharpBlade;
import com.example.mmo.MMO.Items.Swords.CrystalSharpBladePlus;
import com.example.mmo.MMO.Items.Swords.CrystalSword;
import com.example.mmo.MMO.Items.Swords.DarkKnife;
import com.example.mmo.MMO.Items.Swords.DarkSword;
import com.example.mmo.MMO.Items.Swords.DiamondKatana;
import com.example.mmo.MMO.Items.Swords.DragonSword;
import com.example.mmo.MMO.Items.Swords.EmeraldKatana;
import com.example.mmo.MMO.Items.Swords.EmeraldSword;
import com.example.mmo.MMO.Items.Swords.EnchantedGoldSword;
import com.example.mmo.MMO.Items.Swords.EnchantedGoldSwordPlus;
import com.example.mmo.MMO.Items.Swords.FireSword;
import com.example.mmo.MMO.Items.Swords.FireSwordPlus;
import com.example.mmo.MMO.Items.Swords.FireSwordPlusPlus;
import com.example.mmo.MMO.Items.Swords.FlameSword;
import com.example.mmo.MMO.Items.Swords.GodKatana;
import com.example.mmo.MMO.Items.Swords.GodSword;
import com.example.mmo.MMO.Items.Swords.GoldDragonSword;
import com.example.mmo.MMO.Items.Swords.GoldSword;
import com.example.mmo.MMO.Items.Swords.GoldSwordPlus;
import com.example.mmo.MMO.Items.Swords.GreenGoldSword;
import com.example.mmo.MMO.Items.Swords.GreenGoldSwordPlus;
import com.example.mmo.MMO.Items.Swords.HotSword;
import com.example.mmo.MMO.Items.Swords.IceSword;
import com.example.mmo.MMO.Items.Swords.Katana;
import com.example.mmo.MMO.Items.Swords.LightSword;
import com.example.mmo.MMO.Items.Swords.NecromancerSword;
import com.example.mmo.MMO.Items.Swords.OmegaIceSword;
import com.example.mmo.MMO.Items.Swords.OmegaLightSword;
import com.example.mmo.MMO.Items.Swords.OmegaTerraSword;
import com.example.mmo.MMO.Items.Swords.OmniSword;
import com.example.mmo.MMO.Items.Swords.PinkSword;
import com.example.mmo.MMO.Items.Swords.PurpleFlameSword;
import com.example.mmo.MMO.Items.Swords.PurpleSword;
import com.example.mmo.MMO.Items.Swords.RedCrystalSword;
import com.example.mmo.MMO.Items.Swords.SharpBlade;
import com.example.mmo.MMO.Items.Swords.SharpBladePlus;
import com.example.mmo.MMO.Items.Swords.SilverSword;
import com.example.mmo.MMO.Items.Swords.SlimeSword;
import com.example.mmo.MMO.Items.Swords.TerraSword;
import com.example.mmo.MMO.Items.Swords.ThickBlade;
import com.example.mmo.MMO.Items.Swords.ThickBladeBlue;
import com.example.mmo.MMO.Items.Swords.ThickBladeGreen;
import com.example.mmo.MMO.Items.Swords.ThickBladePlus;
import com.example.mmo.MMO.Items.Swords.WaterCrystalSword;
import com.example.mmo.MMO.Items.Swords.WaterCrystalSwordPlus;
import com.example.mmo.MMO.Items.Swords.WaterSword;
import com.example.mmo.MMO.Items.Swords.WeakOmniSword;
import com.example.mmo.MMO.Items.Swords.YellowFlameSword;
import com.example.mmo.MMO.Items.Swords.xxxxxxxx;
import com.example.mmo.MMO.Items.Usable.Items.CritDamageBerry;
import com.example.mmo.MMO.Items.Usable.Items.DefenceBerry;
import com.example.mmo.MMO.Items.Usable.Items.HealthBerry;
import com.example.mmo.MMO.Items.Usable.Items.LuckBerry;
import com.example.mmo.MMO.Items.Usable.Items.MobSpawn;
import com.example.mmo.MMO.Items.Usable.Items.SmallPotion;
import com.example.mmo.MMO.Items.Usable.Skills.BombItem;
import com.example.mmo.MMO.Items.Usable.Skills.DamageBoostItem;
import com.example.mmo.MMO.Items.Usable.Skills.DefenceBoostItem;
import com.example.mmo.MMO.Items.Usable.Skills.ExplosionItem;
import com.example.mmo.MMO.Items.Usable.Skills.FireSlashItem;
import com.example.mmo.MMO.Items.Usable.Skills.FireSpinItem;
import com.example.mmo.MMO.Items.Usable.Skills.IceBombItem;
import com.example.mmo.MMO.Items.Usable.Skills.NebulaItem;
import com.example.mmo.MMO.Items.Usable.Skills.SummonMageItem;
import com.example.mmo.MMO.Items.Usable.UsableDelayManager;
import com.example.mmo.MMO.Skills.Passive.DefenceBoost;
import com.example.mmo.MMO.Skills.Summon.SummonMage;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.util.ArrayList;

public class ItemManager {

    private final Item[] items;

    private final ArrayList<DroppedItem> droppedItems, loadedDroppedItems;

    private final Handler handler;

    private final UsableDelayManager usableDelayManager;

    private final Point p;

    public ItemManager(Handler handler){
        this.handler = handler;

        handler.setItemManager(this);

        usableDelayManager = new UsableDelayManager();

        items = new Item[256];

        droppedItems = new ArrayList<>();
        loadedDroppedItems = new ArrayList<>();

        p = new Point();
        handler.getGame().getMainActivity().getWindowManager().getDefaultDisplay().getSize(p);

        setUpItems();
    }

    public void tick(){
        usableDelayManager.tick();
    }

    public void render(Canvas canvas){
        ArrayList<DroppedItem> droppedItems = new ArrayList<>(this.loadedDroppedItems);
        for (DroppedItem i : droppedItems) {
            i.render(canvas);
        }
    }

    public void addDroppedItem(float x, float y, int amount, int ID, int lvl, ArrayList<ItemBonus> bonuses){
        droppedItems.add(new DroppedItem(ID, amount, x, y, lvl, bonuses, handler));
        setLoadedDroppedItems();
    }

    public void removeDroppedItem(DroppedItem i){
        droppedItems.remove(i);
        setLoadedDroppedItems();
    }

    public void setLoadedDroppedItems(){
        loadedDroppedItems.clear();

        for(DroppedItem d : droppedItems){
            if(d.getX() - handler.getCamera().getxOffset() + Container.slotSize < 0 || d.getX() - handler.getCamera().getxOffset() - Container.slotSize > p.x)
                continue;
            if(d.getY() - handler.getCamera().getyOffset() + Container.slotSize < 0 || d.getY() - handler.getCamera().getyOffset() - Container.slotSize > p.y)
                continue;

            loadedDroppedItems.add(d);
        }
    }

    //getters setters


    public ArrayList<DroppedItem> getDroppedItems() {
        return loadedDroppedItems;
    }

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int ID) {
        return items[ID];
    }

    public void clearDroppedItems(){
        droppedItems.clear();
        setLoadedDroppedItems();
    }

    //set up items

    private void setUpItems(){
        new BasicSword(handler);
        new BronzeSword(handler);
        new PurpleSword(handler);
        new DarkSword(handler);
        new GoldDragonSword(handler);
        new DragonSword(handler);
        new BarbedSword(handler);
        new BloodSword(handler);
        new BronzeSwordPlus(handler);
        new HotSword(handler);
        new IceSword(handler);
        new EmeraldSword(handler);
        new PinkSword(handler);
        new SlimeSword(handler);
        new SilverSword(handler);
        new GoldSword(handler);
        new WaterSword(handler);
        new CrystalSword(handler);
        new LightSword(handler);
        new ButterSword(handler);
        new FlameSword(handler);
        new YellowFlameSword(handler);
        new PurpleFlameSword(handler);
        new OmniSword(handler);
        new SharpBlade(handler);
        new SharpBladePlus(handler);
        new CrystalSharpBlade(handler);
        new CrystalSharpBladePlus(handler);
        new OmegaIceSword(handler);
        new OmegaLightSword(handler);
        new OmegaTerraSword(handler);
        new NecromancerSword(handler);
        new ThickBlade(handler);
        new ThickBladeBlue(handler);
        new ThickBladeGreen(handler);
        new ThickBladeGreen(handler);
        new ThickBladePlus(handler);
        new RedCrystalSword(handler);
        new GodSword(handler);
        new WeakOmniSword(handler);
        new TerraSword(handler);
        new xxxxxxxx(handler);
        new GoldSwordPlus(handler);
        new EnchantedGoldSword(handler);
        new EnchantedGoldSwordPlus(handler);
        new GreenGoldSword(handler);
        new GreenGoldSwordPlus(handler);
        new WaterCrystalSword(handler);
        new WaterCrystalSwordPlus(handler);
        new BigNecromantaSword(handler);
        new LightSword(handler);
        new Katana(handler);
        new DiamondKatana(handler);
        new EmeraldKatana(handler);
        new GodKatana(handler);
        new DarkKnife(handler);
        new BronzeKnife(handler);
        new AbsoluteSword(handler);
        new FireSword(handler);
        new FireSwordPlus(handler);
        new FireSwordPlusPlus(handler);
        new BigSteelSword(handler);
        new BigGoldSword(handler);
        new BigEnderSword(handler);
        new BubaSword(handler);
        new CalciteOrb(handler);
        new JadeNecklace(handler);
        new BubaNecklace(handler);
        new LeatherChestplate(handler);
        new BubaChestplatePlus(handler);
        new BubaOrb(handler);
        new BubaShield(handler);
        new BubaHelmet(handler);
        new ExplosionItem(handler);
        new BombItem(handler);
        new SmallPotion(handler);
        new IceBombItem(handler);
        new FireSpinItem(handler);
        new NebulaItem(handler);
        new DamageBoostItem(handler);
        new DefenceBoostItem(handler);
        new CritDamageBerry(handler);
        new Gold(handler);
        new Silver(handler);
        new SackOfGold(handler);
        new MobSpawn(handler);
        new UpgradeStone(handler);
        new AddBonus(handler);
        new ChangeBonus(handler);
        new FireSlashItem(handler);
        new LuckBerry(handler);
        new DefenceBerry(handler);
        new HealthBerry(handler);
        new SummonMageItem(handler);
        new UpgradeStonePlus(handler);
        new TransformBook(handler);
        new Key(handler);
        new WoodenChest(handler);
        new WoodenChestOpened(handler);
    }

    public UsableDelayManager getUsableDelayManager() {
        return usableDelayManager;
    }
}
