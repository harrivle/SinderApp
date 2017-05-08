package sinder.cse40333.sinderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by apple on 5/8/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "books.db";
    public static int DATABASE_VERSION = 1;
    public static String IMAGES_TABLE_NAME = "Images";
    public static String COL_IMAGE_ID = "_id";
    public static String IMAGE_NAME = "image_name";
    public static String TEAM_ID = "team_id";
    public static String IMAGE = "image";
    public static String COL_DATE = "date";
    public static String COL_URI = "uri";
    public SQLiteDatabase db;

    // constructor
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + IMAGES_TABLE_NAME + " ( " + COL_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TEAM_ID + " INTEGER, "
                + IMAGE + " BLOB, " +
                COL_DATE + " TEXT, "
                + COL_URI + " TEXT )");
        //create another table here for the images!!
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE if exists "  +  IMAGES_TABLE_NAME );
        onCreate(db);
    }

    public void insertData_images(String tblName, ContentValues contentValues) {
        SQLiteDatabase db = getWritableDatabase();

        long ret = db.insert(tblName, null, contentValues );

        if (ret > 0) {
            System.out.println("Successfully inserted");
        } else {
            System.out.println("Insert Unsuccessful");
        }

        db.close();
    }

    public void deleteData_images(int image_id) { //deletes one entry
        db = getWritableDatabase();
        db.delete(IMAGES_TABLE_NAME, " _id = ?", new String[]{Integer.toString(image_id)});
        db.close();
    }

    public Cursor getSelectEntries(String tblName, String[] columns, String where, String[] args, String orderBy) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(tblName, columns, where, args, null, null, orderBy);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
}
}
