package com.example.michael.reflexgame.model;

import android.provider.BaseColumns;

/**
 * Created by michael on 04.10.17.
 */

public class HighscoreDBContract {

    public static final String DATABASE_NAME = "ReflexDB";

    public class HighscoreDB implements BaseColumns {

        public static final String TABLE_NAME = "Highscore";
        public static final String COLUMN_PLAYERNAME = "PlayerName";
        public static final String COLUMN_LEVEL = "Level";
        public static final String COLUMN_SCORE = "Score";

    }











    public class PlayTimes implements  BaseColumns{

        public static final String TABLE_NAME = "Highscore";
        public static final String COLUMN_PLAYERNAME = "PlayerName";
        public static final String COLUMN_LEVEL = "Level";
        public static final String COLUMN_PLAYTIME = "Playtime";

    }

}
