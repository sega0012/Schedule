package com.example.samuel.schedule.DataBaseTool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samuel on 2015/9/4.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }
    public static final String CREATE_SCHEDULE = "create table if not exists note"
            + "(id integer primary key autoincrement,"
            + "token text,"
            + "time integer,"
            + "title text,"
            + "content text,"
            + "tag text,"
            + "status integer)";

    private Context mContext;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHEDULE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
