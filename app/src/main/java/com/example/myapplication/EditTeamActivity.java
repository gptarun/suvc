package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditTeamActivity extends AppCompatActivity {
    Button teamNameEditBtn;
    EditText et;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);
        getOnScreenComponents();

        teamNameEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditTeamActivity.this,HomeActivity.class);
                st=et.getText().toString();
                i.putExtra("Value",st);
                startActivity(i);
                finish();
            }
        });
    }

    void getOnScreenComponents(){
        teamNameEditBtn=findViewById(R.id.teamNameEditBtn);
        et= findViewById(R.id.editText);
    }
}
