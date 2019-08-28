package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Utils.Endpoints;
import com.example.myapplication.Utils.TextUtil;
import com.example.myapplication.Utils.VolleySingleton;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.METValidator;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

  MaterialEditText teamName, teamCollege, userName, firstName, lastName, emailEt, contactNumber, passwordOne, passwordTwo;
  Button registerBtn;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);
    progressBar = findViewById(R.id.progressBar);
    teamName = findViewById(R.id.team_name);
    teamCollege = findViewById(R.id.team_college);
    userName = findViewById(R.id.user_name);
    firstName = findViewById(R.id.first_name);
    lastName = findViewById(R.id.last_name);
    emailEt = findViewById(R.id.email_address);
    contactNumber = findViewById(R.id.contact);
    passwordOne = findViewById(R.id.user_pass1);
    passwordTwo = findViewById(R.id.user_pass2);
    registerBtn = findViewById(R.id.register_btn);
    emailEt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        String result = s.toString().replaceAll(" ", "");
        if (!s.toString().equals(result)) {
          emailEt.setText(result);
          emailEt.setSelection(result.length());
          // alert the user
          showError("No spaces allowed in email");
        }
      }
    });

    userName.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void afterTextChanged(Editable s) {
        String result = s.toString().replaceAll(" ", "");
        if (!s.toString().equals(result)) {
          userName.setText(result);
          userName.setSelection(result.length());
          showError("No spaces allowed in username");
        }
      }
    });
    registerBtn.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isFormOk()) {
          String team_name = TextUtil.getText(teamName);
          String team_college = TextUtil.getText(teamCollege);
          String user_name = TextUtil.getText(userName);
          String first_name = TextUtil.getText(firstName);
          String last_name = TextUtil.getText(lastName);
          String email = TextUtil.getText(emailEt);
          String contact = TextUtil.getText(contactNumber);
          String password = TextUtil.getText(passwordOne);
          progressBar.setVisibility(View.VISIBLE);
          register(team_name, team_college, user_name, first_name, last_name, email, contact,
              password);
        }
      }
    });
  }


  private void register(final String team_name, final String team_college, final String user_name,
      final String first_name, final String last_name, final String email, final String contact,
      final String password) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, Endpoints.register_team,
        new Response.Listener<String>() {
          @Override
          public void onResponse(String ServerResponse) {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(RegisterActivity.this, ServerResponse, Toast.LENGTH_SHORT).show();
            Log.d("VOLLEY", ServerResponse);
            if (ServerResponse.equals("success")) {
              Toast.makeText(RegisterActivity.this, "Wait for the admin to approve your request",
                  Toast.LENGTH_SHORT).show();
              RegisterActivity.this.finish();
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
        params.put("team_name", team_name);
        params.put("team_college", team_college);
        params.put("user_name", user_name);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("contact", contact);
        params.put("email", email);
        params.put("password", password);
        return params;
      }

    };
    VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

  }


  private boolean isFormOk() {
    if (TextUtil.isEmpty(teamName)) {
      showError("Enter Team Name");
      return false;
    } else if (TextUtil.isEmpty(teamCollege)) {
      showError("Enter College Name");
      return false;
    } else if (TextUtil.isEmpty(userName)) {
      showError("Enter UserName");
      return false;
    } else if (TextUtil.isEmpty(firstName)) {
      showError("Enter First Name");
      return false;
    } else if (TextUtil.isEmpty(lastName)) {
      showError("Enter Last Name");
      return false;
    } else if (TextUtil.isEmpty(emailEt)) {
      showError("Enter Email Address");
      return false;
    } else if (TextUtil.isEmpty(contactNumber)) {
      showError("Enter Contact Number");
      return false;
    } else if (TextUtil.isEmpty(passwordOne)) {
      showError("Enter Password");
      return false;
    } else if (TextUtil.isEmpty(passwordTwo)) {
      showError("Confirm Password");
      return false;
    } else if (!TextUtil.isEqual(passwordOne, passwordTwo)) {
      showError("Passwords do not match");
      return false;
    }
    return true;
  }


  private void showError(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }


}