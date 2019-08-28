package com.example.myapplication.Adapter;

import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.myapplication.Activities.TeamUpdateActivity;
import com.example.myapplication.DataModels.DocumentDataModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DocumentsAdapter extends RecyclerView.Adapter<DocumentsAdapter.ViewHolder> {

  private ArrayList<DocumentDataModel> dataSet;
  private Context context;
  private Map<Integer, Integer> integerMap = new HashMap<>();
  private static final String COLUMN_NAME = "column_name";
  private String team_id;

  public DocumentsAdapter(ArrayList<DocumentDataModel> data, Context context, String team_id) {
    this.dataSet = data;
    this.context = context;
    integerMap.put(R.id.radio_button_red, 0);
    integerMap.put(R.id.radio_button_yellow, 1);
    integerMap.put(R.id.radio_button_green, 2);
    this.team_id = team_id;
  }


  @NonNull
  @Override
  public DocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
      int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.documents_item, parent, false);
    return new DocumentsAdapter.ViewHolder(view);
  }


  @Override
  public long getItemId(int position) {
    return position;
  }


  @Override
  public void onBindViewHolder(@NonNull final DocumentsAdapter.ViewHolder holder,
      final int listPosition) {
    final DocumentDataModel dataModel = dataSet.get(listPosition);

    holder.documentName.setText(TeamUpdateActivity.documentNameColumns.get(dataModel.getDocumentName()));
    switch (dataModel.getStatus()) {
      case 0:
        holder.radioGroup.check(R.id.radio_button_red);
        break;
      case 1:
        holder.radioGroup.check(R.id.radio_button_yellow);
        break;
      case 2:
        holder.radioGroup.check(R.id.radio_button_green);
        break;
    }

    if (integerMap.get(holder.radioGroup.getCheckedRadioButtonId()) == dataModel.getStatus()) {
      holder.updateButton.setEnabled(false);
    } else {
      holder.updateButton.setEnabled(true);
    }

    holder.radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (integerMap.get(radioGroup.getCheckedRadioButtonId()) == dataModel.getStatus()) {
          holder.updateButton.setEnabled(false);
        } else {
          holder.updateButton.setEnabled(true);
        }

      }
    });


    holder.updateButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        int status = integerMap.get(holder.radioGroup.getCheckedRadioButtonId());
        dataModel.setStatus(status);
        holder.updateButton.setEnabled(false);
        updateStatus(dataModel.getDocumentName(), String.valueOf(status), holder.updateButton);
        Glide.with(context).load(R.drawable.rolling_circle).into(holder.updateButton);
      }
    });

  }


  private void updateStatus(final String documentName, final String status, final ImageView imageView){
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
        Endpoints.update_team_document_status,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(context, ServerResponse, Toast.LENGTH_SHORT).show();
            Glide.with(context).load(R.drawable.ic_done_black_24dp).into(imageView);
            if(!ServerResponse.equals("Updated")){
              imageView.setEnabled(true);
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
            Glide.with(context).load(R.drawable.ic_done_black_24dp).into(imageView);
            imageView.setEnabled(true);
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(TEAM_ID, team_id);
        params.put(COLUMN_NAME, documentName);
        params.put("status", status);
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

    TextView documentName;
    RadioGroup radioGroup;
    ImageView updateButton;

    ViewHolder(View itemView) {
      super(itemView);
      documentName = itemView.findViewById(R.id.document_name);
      radioGroup = itemView.findViewById(R.id.radio_group);
      updateButton = itemView.findViewById(R.id.updateStatusButton);
    }

  }


}
