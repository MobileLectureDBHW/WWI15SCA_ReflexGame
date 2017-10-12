package com.example.michael.reflexgame;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.michael.reflexgame.model.HighscoreDBHelper;
import com.example.michael.reflexgame.servercommunication.HighscoreInserterTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    private static final String LEVEL = "LEVEL";
    private static final String PLAYER_NAME = "PLAYER_NAME" ;


    int countdown = 0;
    int points = 0;
    int activeButton = 0;
    int level = 0;
    String playername = "";

    ScheduledFuture task;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        level = sp.getInt(LEVEL,0);
        playername = sp.getString(PLAYER_NAME, "");

        countdown = 30;
        points = 0;

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(points + " Points");

        TextView countdownTV = (TextView) findViewById(R.id.countdown);
        countdownTV.setText(countdown + " Seconds Left");

        ScheduledExecutorService ste = Executors.newScheduledThreadPool(5);
        task = ste.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                countdown--;

                boolean determineButton = false;

                if(level == 2){
                    determineButton = true;
                } else if(level == 1 && countdown % 2 == 0){
                    determineButton = true;
                } else if(level == 0 && countdown % 3 == 0){
                    determineButton = true;
                }

                if(determineButton){
                    activeButton = new Double(Math.random() * 4).intValue();
                    System.out.println(activeButton);
                }

                if(countdown <= 0){
                    finish();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        TextView countdownTV = (TextView) findViewById(R.id.countdown);
                        countdownTV.setText(countdown + " Seconds Left");

                        Button btn1 = (Button) findViewById(R.id.button1);
                        Button btn2 = (Button) findViewById(R.id.button2);
                        Button btn3 = (Button) findViewById(R.id.button3);
                        Button btn4 = (Button) findViewById(R.id.button4);

                        btn1.setEnabled(false);
                        btn2.setEnabled(false);
                        btn3.setEnabled(false);
                        btn4.setEnabled(false);

                        if(activeButton == 0){
                            btn1.setEnabled(true);
                        } else if(activeButton == 1){
                            btn2.setEnabled(true);
                        } else if(activeButton == 2){
                            btn3.setEnabled(true);
                        } else if(activeButton == 3){
                            btn4.setEnabled(true);
                        }

                    }
                });


            }
        },0,1, TimeUnit.SECONDS);

    }

    public void buttonHit(View view){
        points += 100;

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(points + " Points");

    }

    @Override
    protected void onPause() {
        super.onPause();

        HighscoreDBHelper dbHelper = new HighscoreDBHelper(getApplicationContext());
        dbHelper.addHighscoreEntry(playername, level, points);

        HighscoreInserterTask inserterTask = new HighscoreInserterTask();
        inserterTask.execute(playername,level+"",points+"");

        task.cancel(true);
    }
}
