package com.example.myapplication.Adapter;

import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_COLLEGE;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.AddMemberActivity;
import com.example.myapplication.Activities.UserPageActiivity;
import com.example.myapplication.DataModels.TeamMemberDataModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserTeamMembersAdapter extends
    RecyclerView.Adapter<UserTeamMembersAdapter.ViewHolder> {

  private List<TeamMemberDataModel> dataSet;
  private Context context;
  private List<String> memberRolesStrings;

  public UserTeamMembersAdapter(List<TeamMemberDataModel> data, Context context) {
    this.dataSet = data;
    this.context = context;
    memberRolesStrings = new ArrayList<String>();
    String[] roles = {"Leader", "Captain", "Vice Captain", "Driver 1", "Driver 2", "Accounts Head",
        "Marketing Head", "Additional Faculty Advisor", "Member"};
    memberRolesStrings.addAll(Arrays.asList(roles));
  }


  @NonNull
  @Override
  public UserTeamMembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.user_team_member_item, parent, false);
    return new UserTeamMembersAdapter.ViewHolder(view);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public void onBindViewHolder(@NonNull final UserTeamMembersAdapter.ViewHolder holder,
      final int listPosition) {
    final TeamMemberDataModel dataModel = dataSet.get(listPosition);
    holder.memberName.setText(
        String.format("%s %s", dataModel.getFirstName(), dataModel.getLastName()));
    holder.memberContact.setText(dataModel.getContact());
    holder.memberEmail.setText(dataModel.getEmail());
    holder.memberCollege.setText(dataModel.getCollegeName());
    if (!dataModel.getRole().equals("ADMIN")) {
      ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,
          R.layout.drop_down_item, memberRolesStrings.toArray(new String[0]));
      holder.memberRole.setAdapter(stringArrayAdapter);
      holder.memberRole.setSelection(memberRolesStrings.indexOf(dataModel.getRole()));
      holder.memberRole.setOnItemSelectedListener(new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          if (!adapterView.getSelectedItem().equals(dataModel.getRole())) {
            holder.updateButton.setVisibility(View.VISIBLE);
          } else {
            holder.updateButton.setVisibility(View.INVISIBLE);
          }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
      });
      holder.deleteButton.setVisibility(View.VISIBLE);
      holder.deleteButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          AlertDialog.Builder builder = new AlertDialog.Builder(context);

          builder.setTitle("Delete");
          builder.setMessage("Are you sure?");
          builder.setCancelable(true);

          builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
              // Do nothing but close the dialog
              deleteMember(dataModel.getID(), listPosition);
              dialog.dismiss();
            }
          });

          builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
              // Do nothing
              dialog.dismiss();
            }
          });

          AlertDialog alert = builder.create();

          alert.show();
        }
      });
      holder.memberRole.setEnabled(true);
      holder.editButton.setVisibility(View.VISIBLE);
    } else {
      holder.editButton.setVisibility(View.GONE);
      ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context,
          R.layout.drop_down_item, new String[]{"ADMIN"});
      holder.memberRole.setAdapter(stringArrayAdapter);
      holder.deleteButton.setVisibility(View.INVISIBLE);
      holder.memberRole.setEnabled(false);
    }

    holder.updateButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Glide.with(context).load(R.drawable.rolling_circle).into(holder.updateButton);
        dataModel.setRole(holder.memberRole.getSelectedItem().toString());
        updateRole(dataModel.getID(), holder.memberRole.getSelectedItem().toString(),
            holder.updateButton);
      }
    });

    holder.editButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String id = dataModel.getID();
        String first_name = dataModel.getFirstName();
        String last_name = dataModel.getLastName();
        String member_contact = dataModel.getContact();
        String member_role = dataModel.getRole();
        String team_college = dataModel.getCollegeName();
        Intent intent = new Intent(context, AddMemberActivity.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("member_id", id);
        intent.putExtra("member_contact", member_contact);
        intent.putExtra("member_role", member_role);
        intent.putExtra("member_email", dataModel.getEmail());
        intent.putExtra("Purpose", "update");
        intent.putExtra(TEAM_ID, dataModel.getTeamID());
        intent.putExtra(TEAM_COLLEGE, team_college);
        ((UserPageActiivity) context)
            .startActivityForResult(intent, UserPageActiivity.ADD_MEMBER_FORM);
      }
    });

  }


  private void deleteMember(final String id, final int position) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.delete_member,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(context, ServerResponse, Toast.LENGTH_SHORT).show();
            if (ServerResponse.equals("deleted")) {
              dataSet.remove(position);
              notifyDataSetChanged();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return params;
      }

    };
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }


  private void updateRole(final String id, final String role, final ImageView imageView) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
        Endpoints.update_member_role,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(context, ServerResponse, Toast.LENGTH_SHORT).show();
            if (ServerResponse.equals("Updated")) {
              Glide.with(context).load(R.drawable.ic_done_black_24dp).into(imageView);
              imageView.setVisibility(View.INVISIBLE);
            } else {
              Glide.with(context).load(R.drawable.ic_done_black_24dp).into(imageView);
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Glide.with(context).load(R.drawable.ic_done_black_24dp).into(imageView);
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("role", role);

        return params;
      }

    };
    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
  }


  @Override
  public int getItemCount() {
    return dataSet.size();
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    TextView memberName, memberCollege, memberEmail, memberContact;
    Spinner memberRole;
    ImageView updateButton;
    ImageView deleteButton;
    TextView editButton;

    ViewHolder(View itemView) {
      super(itemView);
      memberName = itemView.findViewById(R.id.member_name);
      memberRole = itemView.findViewById(R.id.member_role);
      updateButton = itemView.findViewById(R.id.updateRoleButton);
      memberCollege = itemView.findViewById(R.id.member_college);
      memberEmail = itemView.findViewById(R.id.member_email);
      memberContact = itemView.findViewById(R.id.member_contact);
      deleteButton = itemView.findViewById(R.id.deleteButton);
      editButton = itemView.findViewById(R.id.edit_button);
    }

  }


}