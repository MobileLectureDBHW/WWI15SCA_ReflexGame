package com.example.michael.reflexgame;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.michael.reflexgame.model.HighscoreDBHelper;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
    }

    @Override
    protected void onResume() {
        super.onResume();

        HighscoreDBHelper db = new HighscoreDBHelper(getApplicationContext());
        String highscore = db.getHighscoreEntries();

        TextView highscoreTextView = (TextView) findViewById(R.id.highscoreList);
        highscoreTextView.setText(highscore);

    }
}
