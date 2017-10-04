package com.example.michael.reflexgame.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HighscoreDBHelper extends SQLiteOpenHelper {


    public HighscoreDBHelper(Context context) {
        super(context, HighscoreDBContract.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createStmt = "";
        createStmt += "CREATE TABLE " + HighscoreDBContract.HighscoreDB.TABLE_NAME + "(";
        createStmt += " " + HighscoreDBContract.HighscoreDB._ID + " INTEGER PRIMARY KEY, ";
        createStmt += " " + HighscoreDBContract.HighscoreDB.COLUMN_PLAYERNAME + " NVARCHAR(100), ";
        createStmt += " " + HighscoreDBContract.HighscoreDB.COLUMN_LEVEL + " INTEGER, ";
        createStmt += " " + HighscoreDBContract.HighscoreDB.COLUMN_SCORE + " DECIMAL(15,3) ";
        createStmt += " ) ";

        sqLiteDatabase.execSQL(createStmt);

    }

    public void addHighscoreEntry(String playername, int level, float score){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HighscoreDBContract.HighscoreDB.COLUMN_PLAYERNAME, playername);
        values.put(HighscoreDBContract.HighscoreDB.COLUMN_LEVEL, level);
        values.put(HighscoreDBContract.HighscoreDB.COLUMN_SCORE, score);

        db.insert(HighscoreDBContract.HighscoreDB.TABLE_NAME, null, values);

    }

    public String getHighscoreEntries(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.query(
                HighscoreDBContract.HighscoreDB.TABLE_NAME,
                new String[]{
                        HighscoreDBContract.HighscoreDB.COLUMN_PLAYERNAME,
                        HighscoreDBContract.HighscoreDB.COLUMN_LEVEL,
                        HighscoreDBContract.HighscoreDB.COLUMN_SCORE
                },
                null, //where
                null, //where args
                null, //group
                null, //having
                HighscoreDBContract.HighscoreDB.COLUMN_SCORE + " DESC"
        );

        String highscoreAsText = "";

        while(c.moveToNext()){
            String player = c.getString(0);
            int level = c.getInt(1);
            float score = c.getFloat(2);

            highscoreAsText += player + "(" + level + ") " + score + "p \n";
        }

        return highscoreAsText;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionFrom, int versionTo) {

    }
}
