package com.example.myapplication;

import android.widget.ArrayAdapter;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;


import java.util.ArrayList;



public class ProfileActivity extends AppCompatActivity{
    ListView documentsSubmitted;
    ListView documentsNotSubmitted;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getScreenElements();
        documentsSubmitted();
        documentsNotSubmitted();

    }

    private void documentsSubmitted() {


        ArrayList<String> documentSubmittedList = new ArrayList<>();
        for (int i=0;i<6;i++){
            documentSubmittedList.add("Document "+(i+1));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,documentSubmittedList);
        documentsSubmitted.setAdapter(arrayAdapter);
    }

    private void documentsNotSubmitted() {


        ArrayList<String> documentNotSubmittedList = new ArrayList<>();
        for (int i=6;i<12;i++){
            documentNotSubmittedList.add("Document "+(i+1));
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,documentNotSubmittedList);
        documentsNotSubmitted.setAdapter(arrayAdapter);
    }


    private void getScreenElements(){
        documentsSubmitted=findViewById(R.id.documentsSubmitted);
        documentsNotSubmitted=findViewById(R.id.documentsNotSubmitted);
    }
}
