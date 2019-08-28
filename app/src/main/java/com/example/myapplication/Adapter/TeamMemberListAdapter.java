package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.DataModels.TeamMemberDataModel;
import com.example.myapplication.R;
import java.util.ArrayList;

public class TeamMemberListAdapter extends RecyclerView.Adapter<TeamMemberListAdapter.ViewHolder> {

  private ArrayList<TeamMemberDataModel> dataSet;
  private Context context;

  public TeamMemberListAdapter(ArrayList<TeamMemberDataModel> data, Context context) {
    this.dataSet = data;
    this.context = context;
  }


  @NonNull
  @Override
  public TeamMemberListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.team_member_card, parent, false);
    return new TeamMemberListAdapter.ViewHolder(view);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public void onBindViewHolder(@NonNull final TeamMemberListAdapter.ViewHolder holder, final int listPosition) {
    TeamMemberDataModel dataModel = dataSet.get(listPosition);
    holder.memberName.setText(
        String.format("%s %s", dataModel.getFirstName(), dataModel.getLastName()));
    holder.memberEmail.setText(dataModel.getEmail());
    holder.memberContact.setText(dataModel.getContact());
  }


  @Override
  public int getItemCount() {
    return dataSet.size();
  }


  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView memberName, memberEmail, memberContact;

    ViewHolder(View itemView) {
      super(itemView);
      memberName = itemView.findViewById(R.id.member_name);
      memberEmail = itemView.findViewById(R.id.member_email);
      memberContact = itemView.findViewById(R.id.member_contact);
    }

  }


}