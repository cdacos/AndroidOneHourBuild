package com.cysmic.onehourchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CommitsRepository.Listener {
  CommitsListAdapter adapter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    adapter = new CommitsListAdapter();

    RecyclerView commitsList = findViewById(R.id.commits_list);
    commitsList.setLayoutManager(new LinearLayoutManager(this));
    commitsList.setAdapter(adapter);

    CommitsRepository.INSTANCE.setListener(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    CommitsRepository.INSTANCE.setListener(null);
  }

  @Override
  public void dataAvailable(List<Commit> commits) {
    adapter.addItems(commits);
    adapter.notifyDataSetChanged();
  }
}
