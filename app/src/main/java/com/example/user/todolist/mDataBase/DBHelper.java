package com.example.user.todolist.mDataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 02.04.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, Constans.DB_NAME, null, Constans.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    try {
        db.execSQL(Constans.CREATE_TB);
        db.execSQL(Constans.INSERT_DATA_1);
        db.execSQL(Constans.INSERT_DATA_2);
        db.execSQL(Constans.INSERT_DATA_3);
        db.execSQL(Constans.INSERT_DATA_4);
        db.execSQL(Constans.INSERT_DATA_5);
        db.execSQL(Constans.INSERT_DATA_6);
    }
    catch (SQLException e){
        e.printStackTrace();
    }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Constans.DROP_TB);
        onCreate(db);
    }
}
