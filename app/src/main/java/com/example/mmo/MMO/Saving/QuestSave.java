package com.example.mmo.MMO.Saving;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mmo.MMO.Containers.ContainerItem;
import com.example.mmo.MMO.Handler;
import com.example.mmo.MMO.Quests.Quest;
import com.example.mmo.MMO.Quests.QuestLine;
import com.example.mmo.MMO.Statistics.ItemBonus;

import java.io.IOException;
import java.util.ArrayList;

public class QuestSave extends SQLiteOpenHelper {

    private static final String DB_NAME = "quests";

    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "questLines";

    //Columns

    private static final String QUESTLINE_ID = "id";

    private static final String TALKED = "talked";

    private static final String QUEST_ID = "quest";

    private static final String PROGRESS = "progress";

    //

    private Handler handler;

    private boolean created;

    public QuestSave(Handler handler){
        super(handler.getGame().getContext(), DB_NAME, null, DB_VERSION);

        this.handler = handler;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + QUESTLINE_ID + " INTEGER,"
                + TALKED + " INTEGER,"
                + QUEST_ID + " INTEGER,"
                + PROGRESS + " INTEGER)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveQuests(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from "+ TABLE_NAME);
            db.delete("SQLITE_SEQUENCE","NAME = ?",new String[]{TABLE_NAME});
        }catch (SQLException e){
            e.printStackTrace();
        }

        for(QuestLine questLine : handler.getQuestManager().getQuestsLines())
            addQuest(questLine);
    }

    private void addQuest(QuestLine quest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(QUESTLINE_ID, quest.getID());

        if(quest.isNpcShowedText())
            values.put(TALKED, 1);
        else
            values.put(TALKED, 0);

        values.put(QUEST_ID, quest.getIndex());

        if(quest.getCurrentQuest() != null)
            values.put(PROGRESS, quest.getCurrentQuest().getProgress());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void loadQuests(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // moving cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                for(QuestLine line : handler.getQuestManager().getQuestsLines()){
                    if(line.getID() == cursorCourses.getInt(0)){
                        line.setNpcShowedText(cursorCourses.getInt(1) == 1);
                        line.setIndex(cursorCourses.getInt(2));

                        try {
                            line.loadQuest(false, cursorCourses.getInt(1) == 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(line.getCurrentQuest() != null)
                            line.getCurrentQuest().setProgress(cursorCourses.getInt(3));
                    }
                }

            } while (cursorCourses.moveToNext());
        }
        Log.println(Log.ASSERT, "Save", "Quests loaded");
        cursorCourses.close();
    }
}
