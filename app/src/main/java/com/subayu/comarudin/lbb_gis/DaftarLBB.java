package com.subayu.comarudin.lbb_gis;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DaftarLBB extends AppCompatActivity {
    private SqliteHelper dbcenter;
    private Cursor cursor;
    String[] daftar;
    private RecyclerView ls;
    private LBBAdapter lbbAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_lbb);
        setTitle(R.string.lis);
        ls = (RecyclerView) findViewById(R.id.listLbb);
        dbcenter = new SqliteHelper(this);
        listLBB();
    }
    public void listLBB(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM lbb", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        List<DataLBB> data=new ArrayList<>();

        int t = cursor.getCount();
        //Toast.makeText(getApplicationContext(),"Jmlah Data"+ String.valueOf(t),Toast.LENGTH_LONG).show();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            DataLBB lbb = new DataLBB();
            cursor.moveToPosition(cc);
            lbb.no = cursor.getString(0).toString();
            lbb.nama = cursor.getString(1).toString();
            lbb.alamat = cursor.getString(2).toString();
            lbb.notel = cursor.getString(3).toString();
            lbb.latlbb = cursor.getString(4).toString();
            lbb.longlbb = cursor.getString(5).toString();
            Log.d("data",lbb.no);
            data.add(lbb);
        }
        lbbAdapter = new LBBAdapter(DaftarLBB.this,data);
        ls.setAdapter(lbbAdapter);
        ls.setLayoutManager(new LinearLayoutManager(DaftarLBB.this));
        }
    }

