package com.example.myapplication.Activities;

import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_COLLEGE;
import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import java.util.Map;

public class AddMemberActivity extends AppCompatActivity {

  MaterialEditText firstNameEt, lastNameEt, contactEt, emailEt;
  Spinner memberRole;
  String[] roles = {"Leader", "Captain", "Vice Captain", "Driver 1", "Driver 2", "Accounts Head",
      "Marketing Head", "Additional Faculty Advisor", "Member"};
  Button submitBtn;
  ProgressBar progressBar;
  private String team_id;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_member);
    ImageView closeIcon = findViewById(R.id.close_button);
    closeIcon.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        onBackPressed();
      }
    });
    final Intent intent = getIntent();
    team_id = intent.getStringExtra(TEAM_ID);
    memberRole = findViewById(R.id.role_spinner);
    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item, roles);
    memberRole.setAdapter(adapter);
    firstNameEt = findViewById(R.id.first_name);
    lastNameEt = findViewById(R.id.last_name);
    contactEt = findViewById(R.id.member_contact);
    emailEt = findViewById(R.id.member_email);
    submitBtn = findViewById(R.id.submitButton);
    progressBar = findViewById(R.id.main_progressBar);
    submitBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isForkOk()) {
          String first_name = firstNameEt.getText().toString();
          String last_name = lastNameEt.getText().toString();
          String member_contact = contactEt.getText().toString();
          String member_email = emailEt.getText().toString();
          String member_role = memberRole.getSelectedItem().toString();
          String team_college = intent.getStringExtra(TEAM_COLLEGE);
          addNewMember(first_name, last_name, member_contact, member_email, member_role, team_college);
          progressBar.setVisibility(View.VISIBLE);
        }
      }
    });

    if (intent.getStringExtra("Purpose").equals("update")){
      TextView heading = findViewById(R.id.heading);
      heading.setText("Update Form");
      final String first_name = intent.getStringExtra("first_name");
      final String last_name = intent.getStringExtra("last_name");
      final String member_contact = intent.getStringExtra("member_contact");
      final String member_role = intent.getStringExtra("member_role");
      final String team_college = intent.getStringExtra(TEAM_COLLEGE);
      final String member_email = intent.getStringExtra("member_email");
      final String member_id = intent.getStringExtra("member_id");
      firstNameEt.setText(first_name);
      lastNameEt.setText(last_name);
      contactEt.setText(member_contact);
      emailEt.setText(member_email);
      memberRole.setSelection(adapter.getPosition(member_role));
      submitBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          progressBar.setVisibility(View.VISIBLE);
          String first_name = firstNameEt.getText().toString();
          String last_name = lastNameEt.getText().toString();
          String member_contact = contactEt.getText().toString();
          String member_email = emailEt.getText().toString();
          String member_role = memberRole.getSelectedItem().toString();
          updateMember(first_name,last_name,member_contact,member_email, member_role, team_college, member_id);
        }
      });
    }
  }


  private void updateMember(final String first_name, final String last_name, final String member_contact,
      final String member_email, final String member_role, final String team_college, final String member_id) {

    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.update_member,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddMemberActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            if(ServerResponse.equals("Updated")){
              Intent intent = new Intent();
              intent.putExtra("msg", "success");
              setResult(RESULT_OK, intent);
              finish();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Team_ID", team_id);
        params.put("First_Name", first_name);
        params.put("Last_Name", last_name);
        params.put("Contact", member_contact);
        params.put("email", member_email);
        params.put("Role", member_role);
        params.put("id", member_id);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

  }


  private void addNewMember(final String first_name, final String last_name, final String member_contact,
      final String member_email, final String member_role, final String team_college) {

    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.add_new_member,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(AddMemberActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            if(ServerResponse.equals("added")){
              Intent intent = new Intent();
              intent.putExtra("msg", "success");
              setResult(RESULT_OK, intent);
              finish();
            }
          }
        },
        new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError volleyError) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT)
                .show();
            Log.d("VOLLEY", volleyError.toString());
          }
        }) {
      @Override
      protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Team_ID", team_id);
        params.put("team_college", team_college);
        params.put("First_Name", first_name);
        params.put("Last_Name", last_name);
        params.put("Contact", member_contact);
        params.put("email", member_email);
        params.put("Role", member_role);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

  }


  private boolean isForkOk() {
    if (firstNameEt.getText().toString().isEmpty()) {
      showError("First Name Is Empty");
      return false;
    } else if (lastNameEt.getText().toString().isEmpty()) {
      showError("Last Name Is Empty");
      return false;
    } else if (contactEt.getText().toString().isEmpty()) {
      showError("Contact is empty");
      return false;
    } else if (emailEt.getText().toString().isEmpty()) {
      showError("Email is empty");
      return false;
    }
    return true;
  }


  private void showError(String msg) {

  }


}
