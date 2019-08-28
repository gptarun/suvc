package com.example.myapplication.Adapter;

import static com.example.myapplication.Activities.TeamUpdateActivity.PLACEMENT_DRIVE;
import static com.example.myapplication.Activities.TeamUpdateActivity.REGISTRATION;
import static com.example.myapplication.Activities.TeamUpdateActivity.REGISTRATION_FEE;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_1;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_10;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_11;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_12;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_2;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_3;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_4;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_5;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_6;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_7;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_8;
import static com.example.myapplication.Activities.TeamUpdateActivity.ROUND_9;
import static com.example.myapplication.Activities.TeamUpdateActivity.STATUS;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_COLLEGE;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_NAME;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_SCORE;
import static com.example.myapplication.Activities.TeamUpdateActivity.VIRTUAL;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Activities.TeamUpdateActivity;
import com.example.myapplication.DataModels.TeamsDataModel;
import com.example.myapplication.R;
import java.util.ArrayList;

public class TeamsListAdapter extends RecyclerView.Adapter<TeamsListAdapter.ViewHolder> {

  private ArrayList<TeamsDataModel> dataSet;
  private Context context;

  public TeamsListAdapter(ArrayList<TeamsDataModel> data, Context context) {
    this.dataSet = data;
    this.context = context;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.team_card, parent, false);
    return new ViewHolder(view);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {
    final TeamsDataModel dataModel = dataSet.get(listPosition);
    holder.teamName.setText(dataModel.getTeamName());
    holder.teamCollege.setText(dataModel.getCollegeName());
    holder.teamScore.setText(String.format("%s/2000", dataModel.getScore()));
    if(dataModel.getStatus().equals("PENDING")){
      holder.statusIcon.setImageDrawable(context.getDrawable(R.drawable.ic_brightness_1_black_24dp));
    }else{
      holder.statusIcon.setImageDrawable(context.getDrawable(R.drawable.ic_check_circle_black_24dp));
    }
    holder.itemView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        ScaleAnimation scale = new ScaleAnimation(0.9f, 1, 0.9f, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(2500);
        scale.setInterpolator(new OvershootInterpolator());
        holder.itemView.startAnimation(scale);
        Intent intent = new Intent(context, TeamUpdateActivity.class);
        intent.putExtra(TEAM_ID, dataModel.getID());
        intent.putExtra(TEAM_NAME, dataModel.getTeamName());
        intent.putExtra(TEAM_COLLEGE, dataModel.getCollegeName());
        intent.putExtra(TEAM_SCORE, dataModel.getScore());
        intent.putExtra(REGISTRATION, dataModel.getRegistration());
        intent.putExtra(REGISTRATION_FEE, dataModel.getRegistrationFee());
        intent.putExtra(VIRTUAL, dataModel.getVirtual());
        intent.putExtra(PLACEMENT_DRIVE, dataModel.getPlacementDrive());
        intent.putExtra(ROUND_1, dataModel.getRound1());
        intent.putExtra(ROUND_2, dataModel.getRound2());
        intent.putExtra(ROUND_3, dataModel.getRound3());
        intent.putExtra(ROUND_4, dataModel.getRound4());
        intent.putExtra(ROUND_5, dataModel.getRound5());
        intent.putExtra(ROUND_6, dataModel.getRound6());
        intent.putExtra(ROUND_7, dataModel.getRound7());
        intent.putExtra(ROUND_8, dataModel.getRound8());
        intent.putExtra(ROUND_9, dataModel.getRound9());
        intent.putExtra(ROUND_10, dataModel.getRound10());
        intent.putExtra(ROUND_11, dataModel.getRound11());
        intent.putExtra(ROUND_12, dataModel.getRound12());
        intent.putExtra(STATUS, dataModel.getStatus());
        context.startActivity(intent);
      }
    });
  }


  @Override
  public int getItemCount() {
    return dataSet.size();
  }


  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView teamName, teamCollege, teamScore;
    ImageView statusIcon;
    RelativeLayout cardView;

    ViewHolder(View itemView) {
      super(itemView);
      cardView = itemView.findViewById(R.id.card);
      teamName = itemView.findViewById(R.id.team_name);
      teamCollege = itemView.findViewById(R.id.team_college);
      teamScore = itemView.findViewById(R.id.team_score);
      statusIcon = itemView.findViewById(R.id.status_icon);
    }

  }


}


