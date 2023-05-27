package com.example.practicafinalalgoritmia;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class AgafaHTMLTask extends AsyncTask<String, Void, String> {
    private AgafaHTMLListener listener;

    public interface AgafaHTMLListener {
        void onAgafaHTMLComplete(String result);
    }

    public AgafaHTMLTask(AgafaHTMLListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        String paraula = params[0];
        StringBuilder result = new StringBuilder();

        try {
            URL definicio = new URL("https://example.com/" + paraula);
            BufferedReader in = new BufferedReader(new InputStreamReader(definicio.openStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        if (listener != null) {
            listener.onAgafaHTMLComplete(result);
        }
    }
}

