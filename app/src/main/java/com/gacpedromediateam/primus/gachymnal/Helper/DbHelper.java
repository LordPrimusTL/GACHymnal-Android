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
    public static final String VHCOLHYMNID = "hymn_id";
    public static final String VHCOLVERSEID = "verse_id";
    public static final String VHCOLENG = "english";
    public static final String VHCOLYOR = "yoruba";
    private Context ourContext =null;
    private SqlHelper helper;
    private SQLiteDatabase db;

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
            String CREATE_TABLE_USER = "CREATE TABLE `hymn_user` ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `Name` TEXT, `Branch` TEXT, `UUID` TEXT )";
            String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `main_hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
            String CREATE_TABLE_APP_HYMN = "CREATE TABLE `appendix_hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
            String CREATE_TABLE_MAIN_VERSE = "CREATE TABLE `main_verse` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,`hymn_id` INTEGER NOT NULL,`verse_id` INTEGER NOT NULL,`english` TEXT,`yoruba` TEXT);";
            String CREATE_TABLE_APP_VERSE = "CREATE TABLE `appendix_verse` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,`hymn_id` INTEGER NOT NULL,`verse_id` INTEGER NOT NULL,`english` TEXT,`yoruba` TEXT);";
            String CREATE_TABLE_UPDATE = "CREATE TABLE `update_check` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `update_key` TEXT NOT NULL, `updated_at` TEXT NOT NULL )";
            db.execSQL(CREATE_TABLE_USER);
            db.execSQL(CREATE_TABLE_MAIN_HYMN);
            db.execSQL(CREATE_TABLE_APP_HYMN);
            db.execSQL(CREATE_TABLE_MAIN_VERSE);
            db.execSQL(CREATE_TABLE_APP_VERSE);
            db.execSQL(CREATE_TABLE_UPDATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS `hymn_user`");
            db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
            db.execSQL("DROP TABLE IF EXISTS `appendix_hymn`");
            db.execSQL("DROP TABLE IF EXISTS `main_verse`");
            db.execSQL("DROP TABLE IF EXISTS `appendix_verse`");
            db.execSQL("DROP TABLE IF EXISTS `update_check`");
            onCreate(db);
        }

        public void close()
        {
            db.close();
        }
    }


    public boolean AddUser(User user)
    {
        try{
            ContentValues cv = new ContentValues();
            cv.put(USERCOLNAME,user.Name);
            cv.put(USERCOLUMNBRANCH,user.Branch);
            cv.put(USERCOLUMNUUID,user.UUID);
            db.insert(TABLE_USER,null,cv);
            return true;
        }
        catch(Exception ex)
        {
            Log.v("add User",ex.toString());
            return false;
        }
    }

    public void Truncate()
    {
        db.execSQL("DELETE from `main_hymn`");
        Log.i("Main Hymn", "Truncated");
        db.execSQL("DELETE from `appendix_hymn`");
        Log.i("App hymn", "Truncated");
        db.execSQL("DELETE from `main_verse`");
        Log.i("truncate App Verse", "Truncated");
        db.execSQL("DELETE from `appendix_verse`");
        Log.i("App Verse", "Truncated");
    }

    public boolean AddMainHymn(JSONArray jsonArray) {
        ContentValues cv = new ContentValues();
        //String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
        //db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
        //db.execSQL(CREATE_TABLE_MAIN_HYMN);

        hymn mh;

        for(int i = 0; i < jsonArray.length(); i++)
        { JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                mh = new hymn(jsonObject.getInt("id"),jsonObject.getString("english"),jsonObject.getString("yoruba"));
                cv.put(MHCOLHYMNID, mh.hymn_id);
                cv.put(MHCOLENG, mh.english);
                cv.put(MHCOLYOR, mh.yoruba);
                db.insert(TABLE_MAIN_HYMN, null, cv);
                Log.i("Add Main Hymn", "Success " + mh.toString());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public boolean AddAppHymn(JSONArray jsonArray) {
        ContentValues cv = new ContentValues();
        //String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
        //db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
        //db.execSQL(CREATE_TABLE_MAIN_HYMN);
        hymn mh;

        for(int i = 0; i < jsonArray.length(); i++)
        { JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                mh = new hymn(jsonObject.getInt("id"),jsonObject.getString("english"),jsonObject.getString("yoruba"));
                cv.put(MHCOLHYMNID, mh.hymn_id);
                cv.put(MHCOLENG, mh.english);
                cv.put(MHCOLYOR, mh.yoruba);
                db.insert(TABLE_APP_HYMN, null, cv);
                Log.i("add App hymn", "Success" + mh.toString());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public boolean AddMainVerse(JSONArray jsonArray) {
        ContentValues cv = new ContentValues();
        //String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
        //db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
        //db.execSQL(CREATE_TABLE_MAIN_HYMN);

        verse vh;

        for(int i = 0; i < jsonArray.length(); i++)
        { JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                vh = new verse(jsonObject.getInt("hymn_id"),jsonObject.getInt("verse_id"),jsonObject.getString("english"),jsonObject.getString("yoruba"));
                cv.put(VHCOLHYMNID, vh.hymn_id);
                cv.put(VHCOLVERSEID, vh.verse_id);
                cv.put(VHCOLENG, vh.english);
                cv.put(VHCOLYOR, vh.yoruba);
                db.insert(TABLE_MAIN_VERSE, null, cv);
                Log.i("Add Main Verse", "Success" + vh.toString());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    public boolean AddAppVerse(JSONArray jsonArray) {
        ContentValues cv = new ContentValues();
        //String CREATE_TABLE_MAIN_HYMN = "CREATE TABLE `hymn` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `hymn_id` INTEGER NOT NULL, `english` TEXT, `yoruba` TEXT )";
        //db.execSQL("DROP TABLE IF EXISTS `main_hymn`");
        //db.execSQL(CREATE_TABLE_MAIN_HYMN);

        verse vh;

        for(int i = 0; i < jsonArray.length(); i++)
        { JSONObject jsonObject;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                vh = new verse(jsonObject.getInt("hymn_id"),jsonObject.getInt("verse_id"),jsonObject.getString("english"),jsonObject.getString("yoruba"));
                cv.put(VHCOLHYMNID, vh.hymn_id);
                cv.put(VHCOLVERSEID, vh.verse_id);
                cv.put(VHCOLENG, vh.english);
                cv.put(VHCOLYOR, vh.yoruba);
                db.insert(TABLE_APP_VERSE, null, cv);
                Log.i("add App Verse", "Success" + vh.toString());
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //GET Data From Database

    public Cursor SearchDB(String HymnNumber,Integer HymnType)
    {
        Cursor res = null;
        if(HymnType == 0) {
            res = db.rawQuery("select * from main_hymn  WHERE hymn_id LIKE '%" + HymnNumber + "%'",null);
        }

        if(HymnType == 1)
            res = db.rawQuery("select * from appendix_hymn  WHERE hymn_id LIKE '%" + HymnNumber + "%'",null);

        if (res == null)
        {
            return null;
        }
        return res;
    }

    public Cursor GetAllMainHymnList(){
        Cursor res = db.rawQuery("select * from main_hymn", null);
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

}