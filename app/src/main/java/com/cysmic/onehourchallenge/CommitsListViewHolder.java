package com.cysmic.onehourchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by carlos on 21/02/18.
 */

public class CommitsListViewHolder extends RecyclerView.ViewHolder {
  public TextView sha = null;
  public TextView author = null;
  public TextView message = null;

  public CommitsListViewHolder(View itemView) {
    super(itemView);
    sha = itemView.findViewById(R.id.sha);
    author = itemView.findViewById(R.id.author);
    message = itemView.findViewById(R.id.message);
  }
}
