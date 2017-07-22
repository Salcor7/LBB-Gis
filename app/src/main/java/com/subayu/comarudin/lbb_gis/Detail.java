package com.subayu.comarudin.lbb_gis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Detail extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, View.OnClickListener{
    private SliderLayout mDemoSlider;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    CoordinatorLayout ln;
    TextView t1,t2,t3,t4,t5;
    CardView cardView;
    HashMap<String,String> file_maps = new HashMap<String, String>();
    String no,nama,lat,lng,awal;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SharedPreferences sharedPref = getSharedPreferences("SaveLok",Context.MODE_PRIVATE);
        ln = (CoordinatorLayout) findViewById(R.id.lndetail);
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        t1 = (TextView)findViewById(R.id.namalbb);
        t2 = (TextView)findViewById(R.id.alamatlbb);
        t3 = (TextView)findViewById(R.id.notellbb);
        t4 = (TextView)findViewById(R.id.namaguru);
        t5 = (TextView)findViewById(R.id.fasilitas);
        cardView= (CardView)findViewById(R.id.cv_main3);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        final Intent intent = getIntent();
        no = intent.getStringExtra("no");
        nama = intent.getStringExtra("nama");
        String alamat = intent.getStringExtra("alamat");
        String notel = intent.getStringExtra("notel");
        lat = intent.getStringExtra("lat");
        lng = intent.getStringExtra("lng");
        awal = sharedPref.getString(getString(R.string.latlong),"");

        cardView.setOnClickListener(this);

        t1.setText(nama);
        t2.setText(alamat);
        t3.setText(notel);

        Snackbar.make(ln,nama,Snackbar.LENGTH_SHORT).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(Detail.this,Upload.class);
                intent12.putExtra("nama",nama);
                startActivity(intent12);
            }
        });
        String asek = nama.replace(" ","%20");
        new AsyncFetch(asek).execute();
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
        readguru(path);
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
        readfassil(path);
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

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Detail.this);
        HttpURLConnection conn = null;
        URL url = null;
        String key;
        private AsyncFetch(String url){
            this.key = url;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                // Enter URL address where your php file resides
                url = new URL("https://smkmuhlmg.000webhostapp.com/upload/unduh.php?key="+key);
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                // setDoInput and setDoOutput to true as we send and recieve data
                conn.setDoOutput(true);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {
                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    // Pass data to onPostExecute method
                    return (result.toString());
                } else {
                  return("Connection error"+response_code+" "+HttpURLConnection.HTTP_OK);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            //this method will be running on UI thread
            pdLoading.dismiss();
            List<DataLBB> data=new ArrayList<>();
            pdLoading.dismiss();
            if(result.equals("no rows")) {
                readtxt(no);
                //Toast.makeText(getApplicationContext(), "No Results found for entered query", Toast.LENGTH_LONG).show();
            }else{
                try {
                    JSONArray jArray = new JSONArray(result);
                    // Extract data from json and store into ArrayList as class objects
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);
                        DataLBB dts = new DataLBB();
                        //dts.img = json_data.getString("");
                        dts.nama = json_data.getString("nama");
                        dts.file = json_data.getString("file");
                        file_maps.put(dts.nama+" S"+i, "https://smkmuhlmg.000webhostapp.com/upload/file/"+dts.file);
                        data.add(dts);
                    }
                    // Setup and Handover data to recyclerview
                    readtxt(no);
                } catch (JSONException e) {
                    // You to understand what actually error is and handle it appropriately
                    Log.d("Hasil",result);
                    Toast.makeText(Detail.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
}

