package com.cricflame.cricflame.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends android.database.sqlite.SQLiteOpenHelper {

public static final String DATABASE_NAME = "Cricflame.db";

    public static final int DATABASE_VERSION = 1;
public DBHelper(android.content.Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}


public boolean insertField(String Fieldname, String FieldValue) {
    if ( getFieldValue(Fieldname) == null ) {

        SQLiteDatabase db = this.getWritableDatabase( );
        ContentValues contentValues = new ContentValues( );

        contentValues.put("fieldname", Fieldname);
        contentValues.put("fieldvalue", FieldValue);

        db.insert("data", null, contentValues);
    } else {
        updateField(Fieldname, FieldValue);
    }
    return true;
}


public boolean insertFielduser(String Fieldname, String FieldValue) {
    if ( getFieldValueUser(Fieldname) == null ) {

        SQLiteDatabase db = this.getWritableDatabase( );
        ContentValues contentValues = new ContentValues( );

        contentValues.put("fieldname", Fieldname);
        contentValues.put("fieldvalue", FieldValue);

        db.insert("userdata", null, contentValues);
    } else {
        updateFielduser(Fieldname, FieldValue);
    }
    return true;
}

public boolean updateField(String Fieldname, String FieldValue) {
    SQLiteDatabase db = this.getWritableDatabase( );
    ContentValues contentValues = new ContentValues( );
    contentValues.put("fieldname", Fieldname);
    contentValues.put("fieldvalue", FieldValue);
    int updated = db.update("data", contentValues, "fieldname = ? ", new String[]{Fieldname});
    System.out.println(updated + " " + getFieldValue("complainid"));
    return true;
}

public boolean updateFielduser(String Fieldname, String FieldValue) {
    SQLiteDatabase db = this.getWritableDatabase( );
    ContentValues contentValues = new ContentValues( );
    contentValues.put("fieldname", Fieldname);
    contentValues.put("fieldvalue", FieldValue);
    int updated = db.update("userdata", contentValues, "fieldname = ? ", new String[]{Fieldname});
    return true;
}

public String getFieldValue(String Fieldname) {
    SQLiteDatabase db = this.getReadableDatabase( );
    try {
        Cursor cursor = db.query("data", new String[]{"fieldname",
                        "fieldvalue"}, "fieldname" + "=?",
                new String[]{Fieldname}, null, null, null, null);
        if ( ( cursor != null ) && cursor.getCount( ) > 0 )
            cursor.moveToFirst( );
        else
            return null;

        return cursor.getString(1);
    } catch ( Exception e ) {
        return null;
    }
}

public String getFieldValueUser(String Fieldname) {
    Cursor cursor = null;
    try {
        SQLiteDatabase db = this.getReadableDatabase( );
       cursor = db.query("userdata", new String[]{"fieldname",
                        "fieldvalue"}, "fieldname" + "=?",
                new String[]{Fieldname}, null, null, null, null);
        if ( ( cursor != null ) && cursor.getCount( ) > 0 )
            cursor.moveToFirst( );
        else
            return null;

        return cursor.getString(1);
    } catch ( Exception e ) {
        return null;
    } finally {
        if (cursor != null) {
            cursor.close();
        }
    }
}


@Override
public void onCreate(SQLiteDatabase db) {
    db.execSQL("CREATE TABLE IF NOT EXISTS data (id integer primary key autoincrement, fieldname text, fieldvalue text)");
    //
//
    db.execSQL("CREATE TABLE IF NOT EXISTS permanentdata (id integer primary key autoincrement, fieldname text, fieldvalue text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS userdata (id integer primary key autoincrement, fieldname text, fieldvalue text)");
  /*  db.execSQL("CREATE TABLE IF NOT EXISTS cartitem (id integer primary key autoincrement, ProductId text, ProductName text, SellingPrice text, ProductPrice text, Quantity text, Image text, colorHeart text, Discount text, Profit text, Gold text, VendorId text, Maxorder text,Points text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS wishlist (id integer primary key autoincrement, ProductId text, ProductName text, SellingPrice text, ProductPrice text, Image text, colorHeart text, Discount text, Gold text, Points text, Profit text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS localwishlist (id integer primary key autoincrement, ProductId text, ProductName text, SellingPrice text, ProductPrice text, Image text, colorHeart text, Discount text, Gold text, Points text, Profit text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS recentsearch (id integer primary key autoincrement, RecentText text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS tempaddress (id integer primary key autoincrement, AddressId text ,Pincode text, State text, Dist text, Area text, Flat text, Land text, Mobile text, Name text)");
    db.execSQL("CREATE TABLE IF NOT EXISTS RecentItem (id integer primary key autoincrement, ProductId text, ProductName text, SellingPrice text, ProductPrice text, Image text, colorHeart text, Discount text, Profit text, Gold text)");*/
}

@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // TODO Auto-generated method stub
    onCreate(db);
}
}