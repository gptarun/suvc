package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DBName = "suvc.db";
    private static final String Table = "Notifications";
    private static final String id = "ID";
    private static final String subject = "Subject";
    private static final String Message = "Message";
    private static final String Team_ID = "Team_ID";
    private static final String CreatedDateTime = "CreatedDateTime";
    private static final String Sync = "sync";

    public DBhelper(Context context){
        super(context, DBName, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+Table+" (id Integer Primary Key not null, Subject varchar(100) not null, message varchar(2000) not null, CreatedDateTime timestamp not null, sync Tinyint(1) default 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertEntry(int id, String subject, String message, int Team_ID, String createdDateTime, int sync){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("subject",subject);
        cv.put("message",message);
        cv.put("Team_ID",Team_ID);
        cv.put("createdDateTime", createdDateTime);
        cv.put("sync",sync);
        db.insert(Table, null, cv);
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+Table+" order by CreatedDateTime desc",null);
        return cursor;
    }

    public int numRows(){
        SQLiteDatabase db = getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, Table);
    }

    public ArrayList<Notification> getNotifications(int TeamID){
        ArrayList<Notification> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+Table+" where "+Team_ID+"="+TeamID,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            list.add(new Notification(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getInt(5)));
        }
        return list;
    }
}
