package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BackgroundWorker worker = new BackgroundWorker(getApplicationContext());

        final MaterialButton[] buttons = new MaterialButton[200];

        final MaterialTextView scoreView = findViewById(R.id.home_scoreBoard);
        scoreView.setText(75+ "/2000");

        final ContentLoadingProgressBar progressBar = findViewById(R.id.contentLoadingProgressBar);
        progressBar.setProgress(75);

        LinearLayout linearLayout = findViewById(R.id.home_linearLayout);
        try {
            worker.execute("test");String res = worker.get();
            String[] strings = res.split(" ");
            for (int i=0;i<strings.length;i++){
                buttons[i] = new MaterialButton(this, null, R.attr.borderlessButtonStyle);
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                buttons[i].setStrokeWidth(2);
                buttons[i].setStrokeColorResource(R.color.black);
                buttons[i].setText(strings[i]);
                buttons[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(HomeActivity.this,ProfileActivity.class));
                    }
                });

                layout.addView(buttons[i]);

                linearLayout.addView(layout);
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            logoutDialogBox();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbar, menu);
        try {
            int count = Integer.parseInt(new BackgroundWorker(getApplicationContext()).execute("notificationcount","1").get());
            if (count>0)
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this,R.drawable.baseline_notifications_active_white_36));
            else
                menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.baseline_notifications_none_white_36));
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch (id){
            case R.id.appbar_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;

            case R.id.appbar_add:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.appbar_logout:
                logoutDialogBox();
                break;

            case R.id.appbar_aboutUs:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    private void logoutDialogBox(){
        new AlertDialog.Builder(this)
                .setTitle("Log Out!")
                .setMessage("Are you sure want to Log Out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

}
