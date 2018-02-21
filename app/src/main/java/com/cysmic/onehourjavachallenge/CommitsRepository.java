package com.cysmic.onehourjavachallenge;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlos on 21/02/18.
 */

// Singleton
public enum CommitsRepository {
  INSTANCE;

  private List<Commit> commits;
  private Listener listener;
  private final FetchAsync fetchAsync = new FetchAsync();

  public void setListener(Listener listener) {
    this.listener = listener;
    notifyDataAvailable();
  }

  public interface Listener {
    void dataAvailable(List<Commit> commits);
  }

  private void notifyDataAvailable() {
    if (commits != null && commits.size() > 0) {
      if (listener != null) listener.dataAvailable(commits);
    }
    else {
      if (!fetchAsync.isFetching) fetchAsync.execute();
    }
  }

  private class FetchAsync extends AsyncTask<Void, Void, List<Commit>> {
    boolean isFetching = false;

    @Override
    protected void onPreExecute() {
      isFetching = true;
    }

    @Override
    protected List<Commit> doInBackground(Void... voids) {
      List<Commit> commits = null;

      try {
        URL url = new URL("https://api.github.com/repos/rails/rails/commits?page=1&per_page=30");
        try {
          URLConnection connection = url.openConnection();
          InputStreamReader reader = new InputStreamReader(connection.getInputStream());
          StringBuilder builder = new StringBuilder();
          char[] buffer = new char[1024];
          while (true) {
            int i = reader.read(buffer, 0, buffer.length);
            if (i == -1) break;
            builder.append(buffer, 0, i);
          }

          try {
            commits = new ArrayList<>();
            JSONArray ja = new JSONArray(builder.toString());
            for (int i=0; i<ja.length(); i++) {
              JSONObject jo = ja.getJSONObject(i);
              if (jo != null) {
                Commit commit = new Commit();
                commit.sha = jo.optString("sha");
                JSONObject jc = jo.optJSONObject("commit");
                if (jc != null) {
                  JSONObject author = jc.optJSONObject("author");
                  if (author != null) {
                    commit.author = author.optString("name");
                  }
                  commit.message = jc.optString("message");
                }
                commits.add(commit);
              }
            }
          }
          catch (JSONException e) {
            Log.e("CARLOS-ERR", e.getLocalizedMessage());
          }
        }
        catch (IOException e) {
          Log.e("CARLOS-ERR", e.getLocalizedMessage());
        }
      }
      catch (MalformedURLException e) {
        Log.e("CARLOS-ERR", e.getLocalizedMessage());
      }

      return commits;
    }

    @Override
    protected void onPostExecute(List<Commit> commits) {
      isFetching = false;
      CommitsRepository.this.commits = commits;
      notifyDataAvailable();
    }
  }
}
