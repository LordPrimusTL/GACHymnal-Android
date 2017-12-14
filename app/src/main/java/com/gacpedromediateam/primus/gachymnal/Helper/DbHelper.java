package com.gacpedromediateam.primus.gachymnal.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Primus on 7/9/2017.
 */

public  class DbHelper{

    public static final String DATABASE_NAME = "gac.db";
    public static final String TABLE_USER = "hymn_user";
    public static final String TABLE_MAIN_HYMN = "main_hymn";
    public static final String TABLE_APP_HYMN = "appendix_hymn";
    public static final String TABLE_MAIN_VERSE = "main_verse";
    public static final String TABLE_APP_VERSE = "appendix_verse";
    public static final String USERCOLID = "ID";
    public static final String USERCOLNAME = "Name";
    public static final String USERCOLUMNBRANCH = "Branch";
    public static final String USERCOLUMNUUID = "UUID";
    public static final String MHCOLHYMNID = "hymn_id";
    public static final String MHCOLENG = "english";
    public static final String MHCOLYOR = "yoruba";
    public static final String MHFAVE = "favorite";
    public static final String VHCOLHYMNID = "hymn_id";
    public static final String VHCOLVERSEID = "verse_id";
    public static final String VHCOLENG = "english";
    public static final String VHCOLYOR = "yoruba";
    private Context ourContext =null;
    private SqlHelper helper;
    private SQLiteDatabase db;
    String TAG = "DBHELPER";

    public DbHelper(Context context){
        ourContext = context;
    }

    public DbHelper open() throws SQLException{

        helper= new SqlHelper(ourContext);

        db = helper.getWritableDatabase();

        return this;
    }

    public void close()
    {
        db.close();
    }




    public class SqlHelper extends SQLiteOpenHelper {
        SQLiteDatabase db;
        //private final Context context;

        public SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `main_hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT, `favorite` INTEGER NOT NULL)";
            String CREATE_TABLE_APP_HYMN = "CREATE TABLE `appendix_hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT, `favorite` BOOLEAN NOT NULL )";
            String CREATE_TABLE_MAIN_VERSE = "CREATE TABLE `main_verse` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,`hymn_id` INTEGER NOT NULL,`verse_id` INTEGER NOT NULL,`english` TEXT,`yoruba` TEXT);";
            String CREATE_TABLE_APP_VERSE = "CREATE TABLE `appendix_verse` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,`hymn_id` INTEGER NOT NULL,`verse_id` INTEGER NOT NULL,`english` TEXT,`yoruba` TEXT);";
            db.execSQL(CREATE_TABLE_MAIN_HYMN);
            db.execSQL(CREATE_TABLE_APP_HYMN);
            db.execSQL(CREATE_TABLE_MAIN_VERSE);
            db.execSQL(CREATE_TABLE_APP_VERSE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
            db.execSQL("DROP TABLE IF EXISTS `appendix_hymn`");
            db.execSQL("DROP TABLE IF EXISTS `main_verse`");
            db.execSQL("DROP TABLE IF EXISTS `appendix_verse`");
            onCreate(db);
        }

        public void close()
        {
            db.close();
        }
    }

    public void Truncate()
    {
        db.execSQL("DELETE from `main_hymn`");
        Log.i("Main Hymn", "Truncated");
        db.execSQL("DELETE from `appendix_hymn`");
        Log.i("App Hymn", "Truncated");
        db.execSQL("DELETE from `main_verse`");
        Log.i("truncate App Verse", "Truncated");
        db.execSQL("DELETE from `appendix_verse`");
        Log.i("App Verse", "Truncated");
    }

    public boolean addMainHymnList(List<Hymn> hymns) {
        boolean check = true;
        ContentValues cv = new ContentValues();
        int i = 1;
        for (Hymn mh: hymns) {
            try{
                cv.put(MHCOLHYMNID, mh.hymn_id);
                cv.put(MHCOLENG, mh.english);
                cv.put(MHCOLYOR, mh.yoruba);
                cv.put(MHFAVE, 0);
                db.insert(TABLE_MAIN_HYMN, null, cv);
                Log.e(TAG, "addMainHymnList: added " + i++);

            }catch(Exception ex){
                Log.e(TAG, "addMainHymnList: " + ex );
                check = false;
            }
        }
        Log.e("Add Main Hymn", "Success ");
        return check;
    }
    public boolean addAppHymnList(List<Hymn> hymns) {
        boolean check = true;
        ContentValues cv = new ContentValues();
        int i = 1;
        for (Hymn mh: hymns) {
            try{
                cv.put(MHCOLHYMNID, mh.hymn_id);
                cv.put(MHCOLENG, mh.english);
                cv.put(MHCOLYOR, mh.yoruba);
                cv.put(MHFAVE, 0);
                db.insert(TABLE_APP_HYMN, null, cv);
                Log.e(TAG, "addAppHymnList: added " + i++ );

            }catch(Exception ex){
                Log.e(TAG, "addAppHymnList: " + ex );
                check = false;
            }
        }
        Log.e("Add App Hymn", "Success ");
        return check;
    }

    public boolean addMainVerse(List<verse> verse){
        boolean check = true;
        ContentValues cv = new ContentValues();
        int i = 1;
        for (verse vs: verse) {
            try{
                cv.put(VHCOLHYMNID, vs.hymn_id);
                cv.put(VHCOLVERSEID, vs.verse_id);
                cv.put(VHCOLENG, vs.english);
                cv.put(VHCOLYOR, vs.yoruba);
                db.insert(TABLE_MAIN_VERSE, null, cv);
                Log.e(TAG, "addMainVerseList: added" + i++);

            }catch(Exception ex){
                Log.e(TAG, "addMainHymnList: " + ex );
                check = false;
                break;
            }
        }
        Log.e("Add Main Hymn", "Success ");
        return check;
    }
    public boolean addAppVerse(List<verse> verse){
        boolean check = true;
        ContentValues cv = new ContentValues();
        int i = 1;
        for (verse vs: verse) {
            try{
                cv.put(VHCOLHYMNID, vs.hymn_id);
                cv.put(VHCOLVERSEID, vs.verse_id);
                cv.put(VHCOLENG, vs.english);
                cv.put(VHCOLYOR, vs.yoruba);
                db.insert(TABLE_APP_VERSE, null, cv);
                Log.e(TAG, "addAppVerseList: added"+ i++ );
            }catch(Exception ex){
                Log.e(TAG, "addAppHymnList: " + ex );
                check = false;
                break;
            }
        }
        Log.e("Add Main Hymn", "Success ");
        return check;
    }


    //GET Data From Database
    public Cursor GetAllMainHymnList(){
        Cursor res = db.rawQuery("select * from main_hymn", null);
        if (res == null)
        {
            return null;
        }
        return res;
    }
    public Cursor GetAllMainFavorite() {
        Cursor res = db.rawQuery("select * from main_hymn where favorite=1", null);
        if (res == null)
        {
            return null;
        }
        return res;
    }

    public Cursor GetAllAppendixHymnList() {
        Cursor res = db.rawQuery("select * from appendix_hymn", null);

        if (res == null)
        {
            return null;
        }
        return res;
    }
    public Cursor GetAllAppFavorite() {
        Cursor res = db.rawQuery("select * from appendix_hymn where favorite=1", null);
        if (res == null)
        {
            return null;
        }
        return res;
    }

    public Cursor GetVerse(String id, String HymnType) {

        Cursor res = null;

        if(Integer.parseInt(HymnType) == 0)
        {
            res = db.rawQuery("select * from main_verse where hymn_id = " + id + " ORDER BY verse_id ASC", null);
        }

        if(Integer.parseInt(HymnType) == 1)
        {
            res = db.rawQuery("select * from appendix_verse where hymn_id = " + id + " ORDER BY verse_id ASC", null);
        }

        if (res.getCount() == 0)
        {
            res = null;
        }
        return res;
    }

    public boolean setMainFavorite(int hymn_id, int fave){
        try{
            Log.e(TAG, "setMainFavorite: " + hymn_id + " + " + fave);
            db.execSQL("UPDATE "+ TABLE_MAIN_HYMN +" SET favorite= " + fave + " WHERE hymn_id = " + hymn_id);
            return true;
        }catch(Exception ex){
            Log.e(TAG, "setMainFavorite: Error" + ex);
            return false;
        }
    }
    public boolean setAppFavorite(int hymn_id, int fave){
        try{
            Log.e(TAG, "setappFavorite: " + hymn_id + " + " + fave);
            db.execSQL("UPDATE "+ TABLE_APP_HYMN +" SET favorite= " + fave + " WHERE hymn_id = " + hymn_id);
            return true;
        }catch(Exception ex){
            Log.e(TAG, "setMainFavorite: Error" + ex);
            return false;
        }
    }

}