package com.subayu.comarudin.lbb_gis;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LokUser} interface
 * to handle interaction events.
 * Use the {@link LokUser#} factory method to
 * create an instance of this fragment.
 */
public class LokUser extends Fragment  implements OnMapReadyCallback{
    private GoogleMap mMap;
    private MapView mp;
    private SqliteHelper dbcenter;
    private Cursor cursor;
    String[] daftar;
    private List<DataLBB> data;
    private static final int REQUEST_INTERNET = 200;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        final View rt =inflater.inflate(R.layout.fragment_lok_user, container, false);
        return rt;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dbcenter = new SqliteHelper(getActivity());

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_INTERNET);
        }
        mMap.setMyLocationEnabled(true);

        listLBB();

        // Add a marker in Sydney and move the camera
        int p=0;
        while (p<data.size()){
            String s = data.get(p).nama;
            Double lt = Double.valueOf(data.get(p).latlbb);
            Double lng = Double.valueOf(data.get(p).longlbb);
            LatLng lbb = new LatLng(lt,lng);
            mMap.addMarker(new MarkerOptions().position(lbb).title(s));
            p=p+1;
        }

        LatLng sydney = new LatLng(-7.118736, 112.416550);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13));
    }
    public void listLBB(){
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM lbb", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();
        data=new ArrayList<>();

        int t = cursor.getCount();
        //Toast.makeText(getApplicationContext(),"Jmlah Data"+ String.valueOf(t),Toast.LENGTH_LONG).show();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            DataLBB lbb = new DataLBB();
            cursor.moveToPosition(cc);
            lbb.nama = cursor.getString(1).toString();
            lbb.alamat = cursor.getString(2).toString();
            lbb.notel = cursor.getString(3).toString();
            lbb.latlbb = cursor.getString(4).toString();
            lbb.longlbb = cursor.getString(5).toString();
            Log.d("data",lbb.longlbb);
            data.add(lbb);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_INTERNET) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //start audio recording or whatever you planned to do
            }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //Show an explanation to the user *asynchronously*
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_INTERNET);
                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }
}
