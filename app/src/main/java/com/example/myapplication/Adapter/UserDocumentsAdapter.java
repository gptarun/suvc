package com.example.myapplication.Adapter;

import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.myapplication.Activities.UserPageActiivity;
import com.example.myapplication.DataModels.DocumentDataModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserDocumentsAdapter extends RecyclerView.Adapter<UserDocumentsAdapter.ViewHolder> {

  private ArrayList<DocumentDataModel> dataSet;
  private Context context;
  private Map<Integer, Integer> integerMap = new HashMap<>();
  private static final String COLUMN_NAME = "column_name";
  private String team_id;

  public UserDocumentsAdapter(ArrayList<DocumentDataModel> data, Context context, String team_id) {
    this.dataSet = data;
    this.context = context;
    integerMap.put(R.id.radio_button_red, 0);
    integerMap.put(R.id.radio_button_yellow, 1);
    integerMap.put(R.id.radio_button_green, 2);
    this.team_id = team_id;
  }


  @NonNull
  @Override
  public UserDocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.user_team_document_item, parent, false);
    return new UserDocumentsAdapter.ViewHolder(view);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public void onBindViewHolder(@NonNull final UserDocumentsAdapter.ViewHolder holder,
      final int listPosition) {
    final DocumentDataModel dataModel = dataSet.get(listPosition);

    holder.documentName.setText(UserPageActiivity.documentNameColumns.get(dataModel.getDocumentName()));
    switch (dataModel.getStatus()) {
      case 0:
        holder.radioButton.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.red)));
        break;
      case 1:
        holder.radioButton.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.goldenrod)));
        break;
      case 2:
        holder.radioButton.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.lawn_green)));
        break;
    }

  }



  @Override
  public int getItemCount() {
    return dataSet.size();
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    TextView documentName;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ImageView updateButton;

    ViewHolder(View itemView) {
      super(itemView);
      documentName = itemView.findViewById(R.id.document_name);
      radioGroup = itemView.findViewById(R.id.radio_group);
      radioButton = itemView.findViewById(R.id.radio_button);
      updateButton = itemView.findViewById(R.id.updateStatusButton);
    }

  }


}
