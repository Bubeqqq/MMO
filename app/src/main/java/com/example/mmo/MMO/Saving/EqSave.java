package com.example.mmo.MMO.Saving;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.DynamicsProcessing;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.mmo.MMO.Containers.Container;
import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.EQ.EQ;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.util.ArrayList;

public class EqSave extends SQLiteOpenHelper {

    private static final String DB_NAME = "eq";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "items";

    //column

    private static final String ID_COL = "id";

    private static final String ITEMID_COL = "itemID";

    private static final String AMOUNT_COL = "amount";

    private static final String LVL_COL = "lvl";

    private static final String BONUS1ID_COL = "bonus1ID";
    private static final String BONUS1VALUE_COL = "bonus1Value";

    private static final String BONUS2ID_COL = "bonus2ID";
    private static final String BONUS2VALUE_COL = "bonus2Value";

    private static final String BONUS3ID_COL = "bonus3ID";
    private static final String BONUS3VALUE_COL = "bonus3Value";

    private Handler handler;

    public EqSave(Handler handler) {
        super(handler.getGame().getContext(), DB_NAME, null, DB_VERSION);
        this.handler = handler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ITEMID_COL + " INTEGER,"
                + AMOUNT_COL + " INTEGER,"
                + LVL_COL + " INTEGER,"
                + BONUS1ID_COL + " INTEGER,"
                + BONUS1VALUE_COL + " INTEGER,"
                + BONUS2ID_COL + " INTEGER,"
                + BONUS2VALUE_COL + " INTEGER,"
                + BONUS3ID_COL + " INTEGER,"
                + BONUS3VALUE_COL + " INTEGER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveEQ(EQ eq, Container skillBar){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_NAME);
        db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_NAME});

        ContainerItem[][] items = eq.getEq().getItems();

        for(int x = 0; x < eq.getEq().getWidth(); x++){
            for(int y = 0; y < eq.getEq().getHeight(); y++){
                addNewCourse(items[x][y]);
            }
        }

        items = eq.getEquipment().getItems();

        for(int y = 0; y < eq.getEquipment().getHeight(); y++){
            addNewCourse(items[0][y]);
        }

        for(int x = 0; x < skillBar.getWidth(); x++){
            addNewCourse(skillBar.getItems()[x][0]);
        }

        Log.println(Log.ASSERT, "Save", "EQSaved");
    }

    private void addNewCourse(ContainerItem item){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        if(item == null){
            values.put(ITEMID_COL, -1);
            db.insert(TABLE_NAME, null, values);
            db.close();
            return;
        }

        values.put(ITEMID_COL, item.getID());
        values.put(AMOUNT_COL, item.getAmount());
        values.put(LVL_COL, item.getLvl());

        if(item.getBonuses() != null){
            ItemBonus[] bonuses = item.getBonuses().toArray(new ItemBonus[0]);
            int size = bonuses.length;

            if(1 <= size){
                values.put(BONUS1ID_COL, bonuses[0].getID());
                values.put(BONUS1VALUE_COL, bonuses[0].getValue());
            }else {
                values.put(BONUS1ID_COL, -1);
                values.put(BONUS1VALUE_COL, -1);
            }

            if(2 <= size){
                values.put(BONUS2ID_COL, bonuses[1].getID());
                values.put(BONUS2VALUE_COL, bonuses[1].getValue());
            }else {
                values.put(BONUS2ID_COL, -1);
                values.put(BONUS2VALUE_COL, -1);
            }

            if(3 <= size){
                values.put(BONUS3ID_COL, bonuses[2].getID());
                values.put(BONUS3VALUE_COL, bonuses[2].getValue());
            }else {
                values.put(BONUS3ID_COL, -1);
                values.put(BONUS3VALUE_COL, -1);
            }
        }else {
            values.put(BONUS1ID_COL, -1);
            values.put(BONUS1VALUE_COL, -1);
            values.put(BONUS2ID_COL, -1);
            values.put(BONUS2VALUE_COL, -1);
            values.put(BONUS3ID_COL, -1);
            values.put(BONUS3VALUE_COL, -1);
        }

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    private void addItemAtPosition(int i, ContainerItem item){
        int index = 0;
        for(int x = 0; x < handler.getEq().getEq().getWidth(); x++){
            for(int y = 0; y < handler.getEq().getEq().getHeight(); y++){
                if(i == index){
                    handler.getEq().getEq().getItems()[x][y] = item;
                    return;
                }
                index++;
            }
        }

        for(int y = 0; y < handler.getEq().getEquipment().getHeight(); y++){
            if(index == i){
                handler.getEq().getEquipment().getItems()[0][y] = item;
                return;
            }
            index++;
        }

        for(int x = 0; x < handler.getSkillManager().getSkillBar().getWidth(); x++){
            if(i == index){
                handler.getSkillManager().getSkillBar().getItems()[x][0] = item;
                return;
            }
            index++;
        }
    }

    public void loadEQ() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        int index = 0;

        // moving cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {

                if(cursorCourses.getInt(1) == -1){
                    index++;
                    continue;
                }

                ArrayList<ItemBonus> bonuses = new ArrayList<>();

                for(int i = 0; i < 3; i++){
                    if(cursorCourses.getInt(i * 2 + 4) != -1){
                        bonuses.add(new ItemBonus(cursorCourses.getInt(i * 2 + 4), cursorCourses.getInt(i * 2 + 5)));
                    }
                }

                ContainerItem item = new ContainerItem(cursorCourses.getInt(1), handler, cursorCourses.getInt(2), cursorCourses.getInt(3), bonuses);

                addItemAtPosition(index, item);
                index++;
            } while (cursorCourses.moveToNext());
        }
        Log.println(Log.ASSERT, "Save", "EQ loaded");
        cursorCourses.close();
    }
}
