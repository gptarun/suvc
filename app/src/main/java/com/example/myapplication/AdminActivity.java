package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    ListView memberList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addMembers();
    }

    private void addMembers() {
        memberList = findViewById(R.id.memberList);
        ArrayList<String> memberArrayList = new ArrayList<>();
        for (int i=0;i<20;i++){
            memberArrayList.add("Team "+(i+1));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,memberArrayList);
        memberList.setAdapter(arrayAdapter);
    }

}
