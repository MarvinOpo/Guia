package com.example.jadjaluddin.guia.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stephanie Lyn on 10/6/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    Context context;
    final static String DBNAME = "db_guia";
    final static String FILTER= "tbl_filter";
    final static String SETTING = "tbl_setting";
    final static String BOOKING = "tbl_booking";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table "+FILTER+"(id integer primary key, gender varchar(6), " +
                "minPrice integer, maxPrice integer, interest varchar(20))";
        String sql1 = "Create table "+SETTING+"(id integer primary key, alert integer, " +
                "reminder integer, isTraveler integer)";
        String sql2 = "Create table "+BOOKING+"(id integer primary key autoincrement, travelerToken varchar(20), " +
                "guideToken varchar(20), tourDate long, status varchar(15))";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE "+FILTER+"IF EXISTS";
        String sql1 = "DROP TABLE "+SETTING+"IF EXISTS";
        String sql2 = "DROP TABLE "+BOOKING+"IF EXISTS";
        db.execSQL(sql);
        db.execSQL(sql1);
        db.execSQL(sql2);
        onCreate(db);
    }

    public void defaultSetting(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("alert", 0);
        cv.put("reminder", 0);
        cv.put("isTraveler", 0);
        db.insert(SETTING, null, cv);
        db.close();
    }

    public Cursor getSetting(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from "+SETTING;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void updSetting(int update, String column){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update "+SETTING+" set "+column+"="+update;
        db.execSQL(sql);
        db.close();
    }

    public void defaultFilter(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("gender", "BOTH");
        cv.put("minPrice", 100);
        cv.put("maxPrice", 1000);
        cv.put("interest", "13");
        db.insert(FILTER, null, cv);
        db.close();
    }

    public Cursor getFilter(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "Select * from "+FILTER;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void updFilterGender(String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update "+FILTER+" set gender='"+gender+"'";
        db.execSQL(sql);
        db.close();
    }

    public void updFilterPrice(int min, int max){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update "+FILTER+" set minPrice="+min+", maxPrice="+max;
        db.execSQL(sql);
        db.close();
    }

    public void updFilterInterest(String interest){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Update "+FILTER+" set interest="+interest;
        db.execSQL(sql);
        db.close();
    }
}
