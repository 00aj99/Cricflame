package com.cricflame.cricflame.Adapter;

/**
 * Created by Android Developer on 3/23/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter extends SQLiteOpenHelper {
    private static final String DB_NAME="cricbuzz";
    private static final int DB_VERSION=1;
    public static final String TABLE_NAME="betfair";
    public static final String TABLE_POLL="poll";

    private static final String ID_COL="id";
    private static final String ID_POLL="id_poll";




    private static final String SELECTION_NAME="name";
    private static final String SELECTION_ID="select_id";
    private static final String MARKET_ID="market_id";
    private static final String POLL_ID="poll_id";




    public DBAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+ID_COL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+SELECTION_ID+" TEXT,"+MARKET_ID+" TEXT,"+SELECTION_NAME+" TEXT)";
        db.execSQL(query);
        String query1="CREATE TABLE "+TABLE_POLL+" ("+ID_POLL+" INTEGER PRIMARY KEY AUTOINCREMENT,"+POLL_ID+" TEXT)";
        db.execSQL(query1);
       }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create table again
        onCreate(db);
    }

    public void insertRecord(String id,String markrtId,String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        
        values.put(SELECTION_ID,id);
        values.put(MARKET_ID,markrtId);
        values.put(SELECTION_NAME,name);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public void insertPoll(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();


        values.put(POLL_ID,id);
        db.insert(TABLE_POLL,null,values);
        db.close();
    }



    public void updateRecord(String id, String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SELECTION_ID,id);
        values.put(SELECTION_NAME,name);


        db.update(TABLE_NAME,values,"id=?",new String[]{id});
        db.close();
    }

    public void deleteRecord(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{id});

        db.close();
    }
    public void clearTable()   {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, null,null);
    }
    public void clearPoll()   {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_POLL, null,null);
    }
}