package com.example.nav_drawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME="Login.db";

    public DBHelper(@Nullable Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users(username TEXT primary key , password TEXT, fullname TEXT, gender TEXT, Country TEXT, Movies_watched TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int i, int i1){
        db.execSQL("drop table if exists users");
    }

    public Boolean insertRecord(String username,String password,String fullname,  String gender, String country, String movies_watched){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("gender", gender);
        contentValues.put("country", country);
        contentValues.put("movies_watched", movies_watched);

        long result=db.insert("users",null, contentValues );
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean updateRecord(String username,String movies_watched,String gender, String country){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("gender", gender);
        contentValues.put("country", country);
        contentValues.put("movies_watched", movies_watched);

        long result=db.update("users",contentValues,"username=?",new String[]{username} );
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getRecord(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor =db.rawQuery("select * from users where username=?", new String[]{username},null);
        return cursor;
    }
    public Boolean checkUsername(String username){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=?", new String[]{username});
        if (cursor.getCount()>0)
                return true;
        else
            return false;
    }
    public Boolean checkUsernamePassword(String username,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username=? and password=?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
