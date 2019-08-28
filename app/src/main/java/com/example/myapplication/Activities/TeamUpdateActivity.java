package com.example.myapplication.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.DocumentsAdapter;
import com.example.myapplication.Adapter.TeamMemberListAdapter;
import com.example.myapplication.DataModels.DocumentDataModel;
import com.example.myapplication.DataModels.TeamMemberDataModel;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TeamUpdateActivity extends AppCompatActivity {

  public static final String TEAM_ID = "team_id";
  public static final String TEAM_NAME = "team_name";
  public static final String TEAM_COLLEGE = "team_college";
  public static final String TEAM_SCORE = "team_score";
  public static final String REGISTRATION = "Registration";
  public static final String REGISTRATION_FEE = "Registration_Fee";
  public static final String VIRTUAL = "Virtual";
  public static final String PLACEMENT_DRIVE = "Placement_Drive";
  public static final String ROUND_1 = "Round1";
  public static final String ROUND_2 = "Round2";
  public static final String ROUND_3 = "Round3";
  public static final String ROUND_4 = "Round4";
  public static final String ROUND_5 = "Round5";
  public static final String ROUND_6 = "Round6";
  public static final String ROUND_7 = "Round7";
  public static final String ROUND_8 = "Round8";
  public static final String ROUND_9 = "Round9";
  public static final String ROUND_10 = "Round10";
  public static final String ROUND_11 = "Round11";
  public static final String ROUND_12 = "Round12";
  public static final String STATUS = "status";
  public static Map<String, String> documentNameColumns = new HashMap<>();

  static {
    documentNameColumns.put(REGISTRATION, "Registration");
    documentNameColumns.put(REGISTRATION_FEE, "Registration Fee");
    documentNameColumns.put(VIRTUAL, "Virtual Round");
    documentNameColumns.put(PLACEMENT_DRIVE, "Placement Drive");
    documentNameColumns.put(ROUND_1, "Technical Inspection");
    documentNameColumns.put(ROUND_2, "Weight and Egress Test");
    documentNameColumns.put(ROUND_3, "Design Round");
    documentNameColumns.put(ROUND_4, "Business and Marketing Plan");
    documentNameColumns.put(ROUND_5, "Vehicle Cost Round");
    documentNameColumns.put(ROUND_6, "Innovation Round");
    documentNameColumns.put(ROUND_7, "Build Quality Round");
    documentNameColumns.put(ROUND_8, "Brake and Acceleration Test");
    documentNameColumns.put(ROUND_9, "Auto Cross Check");
    documentNameColumns.put(ROUND_10, "Suspension and Traction Check");
    documentNameColumns.put(ROUND_11, "Challenging Round");
    documentNameColumns.put(ROUND_12, "Endurance Round");
  }

  private String team_id;
  private TeamMemberListAdapter memberListAdapter;
  private ArrayList<TeamMemberDataModel> memberDataModels;
  private ImageView scoreUpdateButton;
  private LinearLayout buttonLayout;
  private Button approveButton, declineButton;
  private String status;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_team_update);
    Intent intent = getIntent();
    team_id = intent.getStringExtra(TEAM_ID);
    final TextView teamNamne = findViewById(R.id.team_name);
    teamNamne.setText(intent.getStringExtra(TEAM_NAME));
    final TextView teamCollege = findViewById(R.id.team_college);
    teamCollege.setText(intent.getStringExtra(TEAM_COLLEGE));
    final EditText teamScore = findViewById(R.id.team_score);
    teamScore.setText(intent.getStringExtra(TEAM_SCORE));
    String score_heading = "Score:(out of 2000)";
    SpannableString spannableString = new SpannableString(score_heading);
    spannableString.setSpan(new RelativeSizeSpan(0.5f), 6, score_heading.length(), 0);
    TextView scoreHeading = findViewById(R.id.score_heading);
    scoreHeading.setText(spannableString);
    RecyclerView membersRecyclerView = findViewById(R.id.team_members_recyclerView);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,
        false);
    membersRecyclerView.setLayoutManager(layoutManager);
    memberDataModels = new ArrayList<>();
    memberListAdapter = new TeamMemberListAdapter(memberDataModels, this);
    membersRecyclerView.setAdapter(memberListAdapter);
    getAllMembers();
    scoreUpdateButton = findViewById(R.id.score_update_button);
    teamScore.setOnFocusChangeListener(new OnFocusChangeListener() {
      @Override
      public void onFocusChange(View view, boolean b) {
        if (b) {
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } else {
          hideKeyboard(TeamUpdateActivity.this);
        }
      }
    });
    teamScore.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
    teamScore.addTextChangedListener(new TextWatcher() {
      String lastString = "";

      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        lastString = charSequence.toString();
      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable editable) {
        if (editable.toString().length() > 0) {
          if (Integer.parseInt(editable.toString()) > 2000) {
            Toast.makeText(TeamUpdateActivity.this, "Max Score Limit 2000", Toast.LENGTH_SHORT)
                .show();
            teamScore.setText(lastString);
          }
        }
      }
    });
    scoreUpdateButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (teamScore.isEnabled()) {
          teamScore.setEnabled(false);
          Glide.with(TeamUpdateActivity.this).load(R.drawable.rolling_circle)
              .into(scoreUpdateButton);
          if (teamScore.getText().toString().length() > 0) {
            updateScore(teamScore.getText().toString(), scoreUpdateButton);
          }
          scoreUpdateButton.setImageDrawable(getDrawable(R.drawable.ic_edit_black_24dp));
        } else {
          teamScore.setEnabled(true);
          InputMethodManager imm = (InputMethodManager) getSystemService(
              Context.INPUT_METHOD_SERVICE);
          imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
          teamScore.requestFocus();
          scoreUpdateButton.setImageDrawable(getDrawable(R.drawable.ic_done_black_24dp));
        }
      }
    });
    getDocumentList();

    TextView documentColorCodes = findViewById(R.id.team_document_color_codes);
    String colorCodes = "■ Not Recieved ■ In Progress ■ Approved";
    SpannableString ss1 = new SpannableString(colorCodes);
    ss1.setSpan(new ForegroundColorSpan(
            ContextCompat.getColor(getApplicationContext(), R.color.red)),
        0, 1, 0);
    ss1.setSpan(new ForegroundColorSpan(
            ContextCompat.getColor(getApplicationContext(), R.color.goldenrod)),
        15, 16, 0);
    ss1.setSpan(new ForegroundColorSpan(
            ContextCompat.getColor(getApplicationContext(), R.color.lawn_green)),
        29, 30, 0);
    documentColorCodes.setText(ss1);

    ImageView backButton = findViewById(R.id.back_button);
    backButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });

    buttonLayout = findViewById(R.id.button_layout);
    approveButton = findViewById(R.id.approve_button);
    declineButton = findViewById(R.id.reject_button);
    status = intent.getStringExtra(STATUS);
    if (status.equals("PENDING")) {
      buttonLayout.setVisibility(View.VISIBLE);
      scoreUpdateButton.setEnabled(false);
      approveButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          status = "APPROVED";
          new AlertDialog.Builder(TeamUpdateActivity.this)
              .setTitle("Approve Team!")
              .setMessage(
                  "Are you sure want to approve team " + teamNamne.getText().toString() + "?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  updateStatus(team_id, status);
                }
              })
              .setNegativeButton("No", null)
              .show();

        }
      });

      declineButton.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          new AlertDialog.Builder(TeamUpdateActivity.this)
              .setTitle("Delete Team!")
              .setMessage(
                  "Are you sure want to delete team " + teamNamne.getText().toString() + "?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  deleteTeam(team_id);
                }
              })
              .setNegativeButton("No", null)
              .show();

        }
      });


    } else {
      buttonLayout.setVisibility(View.GONE);
    }
  }


  private void deleteTeam(final String team_id) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
        Endpoints.delete_team,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(TeamUpdateActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            if (!ServerResponse.equals("Updated")) {
              TeamUpdateActivity.this.finish();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(TEAM_ID, team_id);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void updateStatus(final String team_id, final String team_status) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
        Endpoints.update_team_status,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(TeamUpdateActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            if (ServerResponse.equals("Updated")) {
              buttonLayout.setVisibility(View.GONE);
              scoreUpdateButton.setEnabled(true);
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(TEAM_ID, team_id);
        params.put(STATUS, status);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void updateScore(final String score, final ImageView imageView) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST,
        Endpoints.update_team_score,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Toast.makeText(TeamUpdateActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            Glide.with(TeamUpdateActivity.this).load(R.drawable.ic_edit_black_24dp)
                .into(scoreUpdateButton);
            if (!ServerResponse.equals("Updated")) {
              Glide.with(TeamUpdateActivity.this).load(R.drawable.ic_done_black_24dp)
                  .into(scoreUpdateButton);
              imageView.setEnabled(true);
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            imageView.setEnabled(true);
            Glide.with(TeamUpdateActivity.this).load(R.drawable.ic_done_black_24dp)
                .into(scoreUpdateButton);
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(TEAM_ID, team_id);
        params.put(TEAM_SCORE, score);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void getAllMembers() {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.get_all_members,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Gson gson = new Gson();
            TeamMemberDataModel[] d = gson.fromJson(ServerResponse, TeamMemberDataModel[].class);
            memberDataModels.addAll(Arrays.asList(d));
            memberListAdapter.notifyDataSetChanged();
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(TEAM_ID, team_id);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  public static void hideKeyboard(Activity activity) {
    InputMethodManager imm = (InputMethodManager) activity
        .getSystemService(Activity.INPUT_METHOD_SERVICE);
    View view = activity.getCurrentFocus();
    if (view == null) {
      view = new View(activity);
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
  }


  private void getDocumentList() {
    Intent intent = getIntent();
    ArrayList<DocumentDataModel> documentDataModels = new ArrayList<>();
    documentDataModels.add(
        new DocumentDataModel(REGISTRATION, Integer.parseInt(intent.getStringExtra(REGISTRATION))));
    documentDataModels.add(
        new DocumentDataModel(REGISTRATION_FEE,
            Integer.parseInt(intent.getStringExtra(REGISTRATION_FEE))));
    documentDataModels.add(
        new DocumentDataModel(VIRTUAL, Integer.parseInt(intent.getStringExtra(VIRTUAL))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_1, Integer.parseInt(intent.getStringExtra(ROUND_1))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_2, Integer.parseInt(intent.getStringExtra(ROUND_2))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_3, Integer.parseInt(intent.getStringExtra(ROUND_3))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_4, Integer.parseInt(intent.getStringExtra(ROUND_4))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_5, Integer.parseInt(intent.getStringExtra(ROUND_5))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_6, Integer.parseInt(intent.getStringExtra(ROUND_6))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_7, Integer.parseInt(intent.getStringExtra(ROUND_7))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_8, Integer.parseInt(intent.getStringExtra(ROUND_8))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_9, Integer.parseInt(intent.getStringExtra(ROUND_9))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_10, Integer.parseInt(intent.getStringExtra(ROUND_10))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_11, Integer.parseInt(intent.getStringExtra(ROUND_11))));
    documentDataModels.add(
        new DocumentDataModel(ROUND_12, Integer.parseInt(intent.getStringExtra(ROUND_12))));
    documentDataModels.add(
        new DocumentDataModel(PLACEMENT_DRIVE,
            Integer.parseInt(intent.getStringExtra(PLACEMENT_DRIVE))));
    DocumentsAdapter adapter = new DocumentsAdapter(documentDataModels, this, team_id);
    RecyclerView recyclerView = findViewById(R.id.team_documents_recyclerView);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }


}
