package com.example.databaseapplicationsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB_Connectivity extends SQLiteOpenHelper {
    public static final String dbName="Contacts";

    public DB_Connectivity(@Nullable Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q="create table users(id integer primary key autoincrement, name text,phone text,gender text)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists");
        onCreate(db);

    }

    public boolean insert_data(String name,String phone,String gender){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("name",name);
        c.put("phone",phone);
        c.put("gender",gender);
        long r=db.insert("users",null,c);
        if(r==-1) return false;
        else return true;

    }

    public boolean update_data(String name,String phone,String gender){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put("phone",phone);
        c.put("gender",gender);
        Cursor cursor=db.rawQuery("select * from users where name=?",new String[]{name});
        if(cursor.getCount()>0){
            long r=db.update("users",c,"name=?",new String[]{name});
            if(r==-1) return false;
            else return true;
        }
        else return false;
    }

    public boolean delete_data(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where name=?",new String[]{name});
        if(cursor.getCount()>0){
            long r=db.delete("users","name=?",new String[]{name});
            if(r==-1) return false;
            else return true;
        }
        else return false;
    }

    public Cursor getinfo(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users",null);
        return cursor;
    }


}
