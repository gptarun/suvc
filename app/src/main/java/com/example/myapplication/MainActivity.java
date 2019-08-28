package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import com.example.myapplication.Activities.AdminPageActivity;
import com.example.myapplication.Activities.UserPageActiivity;
import com.google.android.material.button.MaterialButton;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

  private MaterialButton loginbtn, registerBtn;
  private ProgressBar progressBar;
  private ConstraintLayout constraintLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressBar = findViewById(R.id.main_progressBar);
    progressBar.setVisibility(View.GONE);
    loginbtn = findViewById(R.id.main_loginBtn);
    loginbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        final String username = ((EditText) findViewById(R.id.main_username)).getText().toString();
        final String password = ((EditText) findViewById(R.id.main_password)).getText().toString();

        if (username.isEmpty() || username.equals("")) {
          Toast.makeText(getApplicationContext(), "Please enter the username",
              Toast.LENGTH_SHORT)
              .show();
        } else if (username.equals(" ")) {
          Toast.makeText(getApplicationContext(), "Invalid username!", Toast.LENGTH_SHORT)
              .show();
        } else if (password.isEmpty() || password.equals("")) {
          Toast
              .makeText(getApplicationContext(), "Please enter the password.",
                  Toast.LENGTH_SHORT)
              .show();
        } else {
          BackgroundWorker worker = new BackgroundWorker(getApplicationContext());
          BackgroundWorker worker1 = new BackgroundWorker(getApplicationContext());
          worker.execute("login", username, password);
          constraintLayout = findViewById(R.id.main_container);
          progressBar.setVisibility(View.VISIBLE);
          constraintLayout.setVisibility(View.GONE);
          try {
            if (worker.get().equalsIgnoreCase("success")) {
              worker1.execute("getData", username, password);
              progressBar.setVisibility(View.GONE);
              constraintLayout.setVisibility(View.VISIBLE);
              if (username.equals("admin1")) {
                startActivity(new Intent(MainActivity.this, AdminPageActivity.class));
                MainActivity.this.finish();
              } else {
                Intent intent = new Intent(MainActivity.this, UserPageActiivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                MainActivity.this.finish();
              }
            } else {
              progressBar.setVisibility(View.GONE);
              constraintLayout.setVisibility(View.VISIBLE);
              Toast
                  .makeText(MainActivity.this, worker.get()+"",
                      Toast.LENGTH_SHORT)
                  .show();
            }
          } catch (ExecutionException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
            e.printStackTrace();
          } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
          }
        }
      }
    });

    registerBtn = findViewById(R.id.main_registerBtn);
    registerBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        ImageView ivProfile = findViewById(R.id.imageView8);
        ActivityOptionsCompat options = ActivityOptionsCompat.
            makeSceneTransitionAnimation(MainActivity.this, (View) ivProfile, "logo");
        startActivity(intent, options.toBundle());
      }
    });
    TextView aboutText = findViewById(R.id.aboutBtn);
    aboutText.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
      }
    });
  }


  @Override
  protected void onStop() {
    ((EditText) findViewById(R.id.main_username)).setText("");
    ((EditText) findViewById(R.id.main_password)).setText("");
    super.onStop();
  }


}