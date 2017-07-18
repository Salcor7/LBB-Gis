package com.subayu.comarudin.lbb_gis;

import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CariLBB extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private SqliteHelper dbcenter;
    Cursor cursor;
    String[] daftar;
    RelativeLayout rr;
    private RecyclerView ls;
    LBBAdapter lbbAdapter;
    private SearchView ed;
    private String key;
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_lbb);
        setTitle(R.string.carilok);

        dbcenter = new SqliteHelper(this);

        rr = (RelativeLayout)findViewById(R.id.rr);
        ls = (RecyclerView) findViewById(R.id.listlbbc);
        ed = (SearchView) findViewById(R.id.editText);
        listLBB();
        setupSearchView();
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
        lbbAdapter = new LBBAdapter(CariLBB.this,data);
        ls.setAdapter(lbbAdapter);
        ls.setLayoutManager(new LinearLayoutManager(CariLBB.this));
    }
    private void setupSearchView() {
        ed.setIconifiedByDefault(false);
        ed.setOnQueryTextListener(this);
        ed.setSubmitButtonEnabled(true);
        ed.setQueryHint("Search Here");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        lbbAdapter.filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        lbbAdapter.filter(newText);
        return true;
    }
}
