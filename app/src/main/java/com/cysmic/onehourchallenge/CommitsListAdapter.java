package com.cysmic.onehourchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by carlos on 21/02/18.
 */

public class CommitsListAdapter extends RecyclerView.Adapter<CommitsListViewHolder> {
  private List<Commit> commits = null;

  public void addItems(List<Commit> commits) {
    this.commits = commits;
  }

  @Override
  public CommitsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_item, parent, false);
    return new CommitsListViewHolder(view);
  }

  @Override
  public void onBindViewHolder(CommitsListViewHolder holder, int position) {
    if (commits != null && position < commits.size()) {
      Commit commit = commits.get(position);
      holder.sha.setText(holder.sha.getResources().getString(R.string.commit_sha_format, commit.sha));
      holder.author.setText(commit.author);
      holder.message.setText(commit.message);
    }
  }

  @Override
  public int getItemCount() {
    return commits == null ? 0 : commits.size();
  }
}
