package com.example.myapplication.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.AboutUsActivity;
import com.example.myapplication.Adapter.TeamsListAdapter;
import com.example.myapplication.DataModels.TeamsDataModel;
import com.example.myapplication.MainActivity;
import com.example.myapplication.NotificationActivity;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class AdminPageActivity extends AppCompatActivity {

  ArrayList<TeamsDataModel> dataModels;
  TeamsListAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_page);
    dataModels = new ArrayList<>();
    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);
    adapter = new TeamsListAdapter(dataModels, this);
    recyclerView.setAdapter(adapter);
    getAllTeamsList();

    ImageView notificationButton = findViewById(R.id.notification_button);
    notificationButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(AdminPageActivity.this, NotificationActivity.class));
      }
    });

    ImageView menuButton = findViewById(R.id.menu_button);
    menuButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(AdminPageActivity.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
              case R.id.about_us:
                startActivity(new Intent(AdminPageActivity.this, AboutUsActivity.class));
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
            Intent i = new Intent(AdminPageActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
          }
        })
        .setNegativeButton("No", null)
        .show();
  }


  private void getAllTeamsList() {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.get_all_teams,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            dataModels.clear();
            Gson gson = new Gson();
            TeamsDataModel[] d = gson.fromJson(ServerResponse, TeamsDataModel[].class);
            dataModels.addAll(Arrays.asList(d));
            adapter.notifyDataSetChanged();
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
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


  @Override
  protected void onResume() {
    super.onResume();
    getAllTeamsList();
  }


  @Override
  public void onBackPressed() {
    logoutDialogBox();
  }


}
