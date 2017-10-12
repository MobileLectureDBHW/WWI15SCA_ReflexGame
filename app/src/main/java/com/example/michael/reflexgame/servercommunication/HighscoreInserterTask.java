package com.example.michael.reflexgame.servercommunication;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michael on 12.10.17.
 */

public class HighscoreInserterTask extends AsyncTask<String, Void, String>{




    //Asynchrone Abarbeitung
    @Override
    protected String doInBackground(String... strings) {

        String player = strings[0];
        String level = strings[1];
        String points = strings[2];



        String result = "";

        //URL zusammenstellen
        String urlString = "http://space-labs.appspot.com/repo/465001/highscore.sjs";

        try {

            //JSONObject zusammenstellen
            JSONObject insertHS = new JSONObject();
            insertHS.put("Name", player);
            insertHS.put("Level", level);
            insertHS.put("Points", points);

            //Hier haben wir eine Connection
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("PUT");

            OutputStream os = connection.getOutputStream();
            os.write(insertHS.toString().getBytes());

            connection.connect();

            int responseCode = connection.getResponseCode();
            InputStream is = connection.getInputStream();

            result = convertStreamToString(is);

        }  catch(IOException e){
            System.err.println(e.toString());
        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return result;
    }

    //Rückmeldung
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    public static String convertStreamToString(InputStream is) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }


}
