package com.example.samuel.schedule.DataBaseTool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.samuel.schedule.ScheduleThum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samuel on 2015/9/16.
 */
public class DAO {
    private Database database;
    public DAO(Context context){
        database = new Database(context,"Schedule.db",null,1);
    }
    public boolean insert(ContentValues values){
        boolean flag = false;

        SQLiteDatabase db = null;

        try{
           db = database.getWritableDatabase();
            Long l =db.insert("note",null,values);
            if(l != null ){
                flag = (l>0?true:false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null )
            {
                db.close();
            }

        }
        return flag;
    }
    public boolean edit(ContentValues values,String[] where){
        boolean flag = false;
        SQLiteDatabase db = null;
        try{
            db = database.getWritableDatabase();
            int i = db.update("note", values, "id=?", where);
            flag = (i>0?true:false);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null )
            {
                db.close();
            }

        }
        return flag;
    }
    public Map<String,String> select(String selection,String[]selectionArgs){
        Map<String,String> map = new HashMap<String, String>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = database.getWritableDatabase();
            cursor = db.query(true,"note",null,selection,selectionArgs,null,null,null,null);
            int cols_num = cursor.getColumnCount();
            while (cursor.moveToNext()){
                for(int i=1;i<cols_num;i++){
                    String cols_name = cursor.getColumnName(i);
                    String cols_value = cursor.getString(cursor.getColumnIndex(cols_name));
                    if(cols_value == null){
                        cols_value = "";
                    }
                    map.put(cols_name,cols_value);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null )
            {
                db.close();
            }

        }


        return map;

    }
    public ArrayList<ScheduleThum> thumArrayList(String selection,String[]selectionArgs){
        ArrayList<ScheduleThum> thums = new ArrayList<ScheduleThum>();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = database.getWritableDatabase();
            cursor = db.query(false,"note",new String[]{"id","tag","title","time"},selection,selectionArgs,null,null,null,null);
            int cols_num = cursor.getColumnCount();
            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String tag = cursor.getString(1);
                String title = cursor.getString(2);
                String time = cursor.getString(3);
                thums.add(new ScheduleThum(id,tag,title,time));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(db != null )
            {
                db.close();
            }

        }


        return thums;
    }

}
