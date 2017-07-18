package com.subayu.comarudin.lbb_gis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by black4v on 02/06/2017.
 */

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "datalbb.db";
    private static final int DATABASE_VERTSION = 1;

    public SqliteHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERTSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE lbb (no integer primary key, nama text not null, alamat text null, notel text null, latlbb text null, longlbb text null);";
        Log.d("Data","onCreate: "+sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}
