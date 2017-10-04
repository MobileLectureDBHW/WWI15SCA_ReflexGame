package com.example.michael.reflexgame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.michael.reflexgame.model.HighscoreDBHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StartActivity extends AppCompatActivity {

    private static final String LEVEL = "LEVEL";
    private static final String PLAYER_NAME = "PLAYER_NAME" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPlayerInfo();
    }

    public void loadPlayerInfo(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String playername = sp.getString(PLAYER_NAME, "");
        int level = sp.getInt(LEVEL, 0);

        EditText playerNameED = (EditText) findViewById(R.id.playername);
        playerNameED.setText(playername);

        SeekBar levelSB = (SeekBar) findViewById(R.id.level);
        levelSB.setProgress(level);
    }

    public void startGame(View view){

        EditText playerNameED = (EditText) findViewById(R.id.playername);
        String playername = playerNameED.getText().toString();

        SeekBar levelSB = (SeekBar) findViewById(R.id.level);
        int level = levelSB.getProgress();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(PLAYER_NAME, playername);
        editor.putInt(LEVEL, level);
        editor.commit();

        HighscoreDBHelper dbHelper = new HighscoreDBHelper(getApplicationContext());
        dbHelper.addHighscoreEntry(playername, level, (float)Math.random() * 1200);

        try {

            FileOutputStream os = openFileOutput(getFilesDir().getAbsolutePath() + File.separator + "REFLEX_CERTIFICATE.pdf", Context.MODE_PRIVATE);
            os.write("Test1234".getBytes());
            os.close();
        } catch(IOException e){
            System.err.println(e);
        }

        Intent i = new Intent(getApplicationContext(),HighScoreActivity.class);
        startActivity(i);
    }
}
