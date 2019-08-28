package com.example.myapplication;

import static com.example.myapplication.Activities.TeamUpdateActivity.TEAM_ID;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

class Notification{
    private int id;
    private String sub, msg;
    private int team_id;
    private String createdDateTime;
    private int sync;

    public Notification(int id, String sub, String msg, int team_id, String createdDateTime, int sync){
        this.id = id;
        this.sub = sub;
        this.msg = msg;
        this.team_id = team_id;
        this.createdDateTime = createdDateTime;
        this.sync = sync;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
}

public class NotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        try{
            BackgroundWorker worker = new BackgroundWorker(getApplicationContext());
            worker.execute("notifications","2");
            String result = worker.get();
            ArrayList<Notification> list = new ArrayList<>();
            for (String temp:result.split("<br/>")){
                String[] strings = temp.split("\\^");
                list.add(new Notification(Integer.parseInt(strings[0]),strings[1],strings[2],Integer.parseInt(strings[3]),strings[4],Integer.parseInt(strings[5])));
            }
            String[] sub = new String[list.size()];
            for (int i=0;i<list.size();i++)
                sub[i] = list.get(i).getSub();
            ListView listView = findViewById(android.R.id.list);
            listView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,sub));
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
