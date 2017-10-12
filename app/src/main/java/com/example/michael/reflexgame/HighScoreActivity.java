package com.example.michael.reflexgame;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.michael.reflexgame.model.HighscoreDBHelper;
import com.example.michael.reflexgame.servercommunication.HighscoreLoaderTask;
import com.example.michael.reflexgame.servercommunication.PlayerRegistrationTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HighScoreActivity extends AppCompatActivity implements HighscoreLoaderTask.TaskFinishedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);
    }

    @Override
    protected void onResume() {
        super.onResume();

        HighscoreLoaderTask hsLoader = new HighscoreLoaderTask(this);
        hsLoader.execute();

      /*  HighscoreDBHelper db = new HighscoreDBHelper(getApplicationContext());
        String highscore = db.getHighscoreEntries();

        TextView highscoreTextView = (TextView) findViewById(R.id.highscoreList);
        highscoreTextView.setText(highscore);*/

    }

    @Override
    public void taskFinishedSucceesfully(String result) {

        try {
            JSONArray array = new JSONArray(result);
            String highscore = "";

            for(int i = 0; i < array.length(); i++){
                JSONObject entry = array.getJSONObject(i);

                String player = entry.getString("Name");
                String points = entry.getString("Points");

                highscore += player + ": " + points + "P\n";
            }

            final String highscoreString = highscore;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    TextView highscoreTextView = (TextView) findViewById(R.id.highscoreList);
                    highscoreTextView.setText(highscoreString);

                }
            });

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public void startGame(View view){
        Intent i = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(i);
    }


}
