package com.subayu.comarudin.lbb_gis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Detail extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener{
    private SliderLayout mDemoSlider;
    LinearLayout ln;
    TextView t1,t2,t3,t4,t5;
    CardView cardView;
    HashMap<String,String> file_maps = new HashMap<String, String>();
    String no,nama,lat,lng,awal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SharedPreferences sharedPref = getSharedPreferences("SaveLok",Context.MODE_PRIVATE);
        ln = (LinearLayout)findViewById(R.id.lndetail);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        t1 = (TextView)findViewById(R.id.namalbb);
        t2 = (TextView)findViewById(R.id.alamatlbb);
        t3 = (TextView)findViewById(R.id.notellbb);
        t4 = (TextView)findViewById(R.id.namaguru);
        t5 = (TextView)findViewById(R.id.fasilitas);
        cardView= (CardView)findViewById(R.id.cv_main3);

        Intent intent = getIntent();
        no = intent.getStringExtra("no");
        nama = intent.getStringExtra("nama");
        String alamat = intent.getStringExtra("alamat");
        String notel = intent.getStringExtra("notel");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        awal = sharedPref.getString(getString(R.string.latlong),"");

        cardView.setOnClickListener(this);

        readtxt(no);
        readguru(no);
        readfassil(no);

        t1.setText(nama);
        t2.setText(alamat);
        t3.setText(notel);

        Snackbar.make(ln,nama,Snackbar.LENGTH_SHORT).show();

    }
    void readtxt(String path){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path+"/img.txt")));
            String line;
            while ((line = reader.readLine())!=null) {
                    String[] RowData = line.split(",");
                    Log.d("Data : ",RowData[1]);
                    file_maps.put(RowData[0], RowData[1]);

            }
            for(String name : file_maps.keySet()){
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(file_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra",name);

                mDemoSlider.addSlider(textSliderView);
            }
            mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
            mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
            mDemoSlider.setDuration(4000);
            mDemoSlider.addOnPageChangeListener(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    void readguru(String path){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path+"/guru.txt")));
            String line;
            while ((line = reader.readLine())!=null) {
                //Log.e("code",line);
//                String[] RowData = line.split(",");
                t5.append(line+"\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    void readfassil(String path){
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(path+"/fasilitas.txt")));
            String line;
            while ((line = reader.readLine())!=null) {
                //Log.e("code",line);
//                String[] RowData = line.split(",");
                t4.append(line+"\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(),Rute.class);
        intent.putExtra("awal",awal);
        intent.putExtra("akhir",lat+","+lng);
        intent.putExtra("nama",nama);
        startActivity(intent);
    }
}
