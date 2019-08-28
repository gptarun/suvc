package com.example.myapplication.Activities;

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
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_COLLEGE;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_NAME;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_SCORE;
import static com.example.myapplication.Activities.TeamUpdateActivity.VIRTUAL;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AboutUsActivity;
import com.example.myapplication.Adapter.DocumentsAdapter;
import com.example.myapplication.Adapter.UserDocumentsAdapter;
import com.example.myapplication.Adapter.UserTeamMembersAdapter;
import com.example.myapplication.DataModels.DocumentDataModel;
import com.example.myapplication.DataModels.DocumentStatusDataModel;
import com.example.myapplication.DataModels.TeamMemberDataModel;
import com.example.myapplication.DataModels.TeamsDataModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NotificationActivity;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.cachapa.expandablelayout.ExpandableLayout;

public class UserPageActiivity extends AppCompatActivity {
  private String username;
  private String team_id;
  TextView titleText, teamName, teamCollege, teamScore;
  RecyclerView membersRecyclerView, documentsRecyclerView;
  ExpandableLayout membersExpandable, documentsExpandable;
  LinearLayout membersToggle, documentsToggle;
  ImageView arrow1, arrow2;
  private List<TeamMemberDataModel> memberDataModels;
  private UserTeamMembersAdapter membersAdapter;
  public static final int ADD_MEMBER_FORM = 101;
  public static Map<String,String> documentNameColumns = new HashMap<>();
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


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_page_actiivity);
    username = getIntent().getStringExtra("username");
    arrow1 = findViewById(R.id.arrow_icon1);
    arrow2 = findViewById(R.id.arrow_icon2);
    membersToggle = findViewById(R.id.expandable_layout_toggle1);
    documentsToggle = findViewById(R.id.expandable_layout_toggle2);
    titleText = findViewById(R.id.title);
    teamName = findViewById(R.id.team_name);
    teamCollege = findViewById(R.id.team_college);
    teamScore = findViewById(R.id.team_score);
    membersRecyclerView = findViewById(R.id.team_members_recyclerView);
    documentsRecyclerView = findViewById(R.id.team_documents_recyclerView);
    membersExpandable = findViewById(R.id.members_expandable_layout);
    documentsExpandable = findViewById(R.id.documents_expandable_layout);
    titleText.setText(username);
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

    String score_heading = "Score:(out of 2000)";
    SpannableString spannableString = new SpannableString(score_heading);
    spannableString.setSpan(new RelativeSizeSpan(0.5f), 6, score_heading.length(), 0);
    TextView scoreHeading = findViewById(R.id.score_heading);
    scoreHeading.setText(spannableString);

    documentsToggle.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(documentsExpandable.isExpanded()){
          documentsExpandable.collapse();
          arrow2.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }else{
          documentsExpandable.expand();
          arrow2.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        }
      }
    });

    membersToggle.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if(membersExpandable.isExpanded()){
          membersExpandable.collapse();
          arrow1.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        }else{
          membersExpandable.expand();
          arrow1.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        }
      }
    });

    getTeamDetails();

    memberDataModels = new ArrayList<>();
    membersAdapter = new UserTeamMembersAdapter(memberDataModels, this);
    membersRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    membersRecyclerView.setAdapter(membersAdapter);

    ImageView notificationButton = findViewById(R.id.notification_button);
    notificationButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(UserPageActiivity.this, NotificationActivity.class));
      }
    });

    ImageView menuButton = findViewById(R.id.menu_button);
    menuButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(UserPageActiivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
              case R.id.about_us:
                startActivity(new Intent(UserPageActiivity.this, AboutUsActivity.class));
                break;
              case R.id.logout:
                logoutDialogBox();
                break;
            }
            return false;
          }
        });
        popupMenu.show();
      }
    });

  }


  private void logoutDialogBox() {
    new AlertDialog.Builder(this)
        .setTitle("Log Out!")
        .setMessage("Are you sure want to Log Out?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            Intent i = new Intent(UserPageActiivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
          }
        })
        .setNegativeButton("No", null)
        .show();
  }


  private void getTeamDetails(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.get_team_details,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Gson gson = new Gson();
            TeamsDataModel dataModel = gson.fromJson(ServerResponse, TeamsDataModel.class);
            team_id = dataModel.getID();
            teamName.setText(dataModel.getTeamName());
            teamCollege.setText(dataModel.getCollegeName());
            teamScore.setText(dataModel.getScore());
            getTeamMembers();
            getAllDocumentStatus();
            FloatingActionButton actionButton = findViewById(R.id.addMemberBtn);
            actionButton.setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View view) {
                if(membersAdapter.getItemCount()<40) {
                  Intent intent = new Intent(UserPageActiivity.this, AddMemberActivity.class);
                  intent.putExtra(TEAM_ID, team_id);
                  intent.putExtra(TEAM_COLLEGE, teamCollege.getText().toString());
                  intent.putExtra("Purpose", "add");
                  startActivityForResult(intent, ADD_MEMBER_FORM);
                }else{
                  Toast.makeText(UserPageActiivity.this, "Max. 40 per team", Toast.LENGTH_SHORT).show();
                }
              }
            });
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_name", username);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void getDocumentList(DocumentStatusDataModel dataModel) {
    ArrayList<DocumentDataModel> documentDataModels = new ArrayList<>();
    documentDataModels.add(
        new DocumentDataModel(REGISTRATION, Integer.parseInt(dataModel.getRegistration())));
    documentDataModels.add(
        new DocumentDataModel(REGISTRATION_FEE,
            Integer.parseInt(dataModel.getRegistrationFee())));
    documentDataModels.add(
        new DocumentDataModel(VIRTUAL, Integer.parseInt(dataModel.getVirtual())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_1, Integer.parseInt(dataModel.getRound1())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_2, Integer.parseInt(dataModel.getRound2())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_3, Integer.parseInt(dataModel.getRound3())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_4, Integer.parseInt(dataModel.getRound4())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_5, Integer.parseInt(dataModel.getRound5())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_6, Integer.parseInt(dataModel.getRound6())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_7, Integer.parseInt(dataModel.getRound7())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_8, Integer.parseInt(dataModel.getRound8())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_9, Integer.parseInt(dataModel.getRound9())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_10, Integer.parseInt(dataModel.getRound10())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_11, Integer.parseInt(dataModel.getRound11())));
    documentDataModels.add(
        new DocumentDataModel(ROUND_12, Integer.parseInt(dataModel.getRound12())));
    documentDataModels.add(
        new DocumentDataModel(PLACEMENT_DRIVE, Integer.parseInt(dataModel.getPlacementDrive())));
    UserDocumentsAdapter adapter = new UserDocumentsAdapter(documentDataModels, this, team_id);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    documentsRecyclerView.setLayoutManager(layoutManager);
    documentsRecyclerView.setAdapter(adapter);
  }


  private void getAllDocumentStatus(){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.get_team_document_status,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Gson gson = new Gson();
            DocumentStatusDataModel dataModel = gson.fromJson(ServerResponse, DocumentStatusDataModel.class);
            if (dataModel != null) {
              getDocumentList(dataModel);
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("team_id", team_id);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  private void getTeamMembers(){
    memberDataModels.clear();
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.get_all_members,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            Gson gson = new Gson();
            TeamMemberDataModel[] d = gson.fromJson(ServerResponse, TeamMemberDataModel[].class);
            memberDataModels.addAll(Arrays.asList(d));
            membersAdapter.notifyDataSetChanged();
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


  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==ADD_MEMBER_FORM && resultCode==RESULT_OK){
      getTeamMembers();
    }
  }


  @Override
  public void onBackPressed() {
    logoutDialogBox();
  }
}

