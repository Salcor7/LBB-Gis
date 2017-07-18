package com.subayu.comarudin.lbb_gis;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;

public class Utama extends AppCompatActivity {
    private SqliteHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama);
        getSupportActionBar().setElevation(0);

        dbHelper = new SqliteHelper(this);
        datalbb();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Kategori"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ControlerPager adapter = new ControlerPager(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void datalbb(){
        addLBB("1","LKP LP3I Course Center Lamongan","Jl. Andansari 68 Lamongan","0322 - 325006","-7.119968","112.412805");
        addLBB("2","LBB Smart Student Centre","Jl. KH. Ahmad Dahlan 70 Lamongan","0322 - 6746447","-7.122254","112.416739");
        addLBB("3","LKP Bengkel Pintar","Jl. Sunan Giri 49 Lamongan","0322 - 322964","-7.111271","112.408663");
        addLBB("4","LKP Institute Of Management Information (MMI)","Jl. Mastrip No. 98 Made - Lamongan","0322 - 324950","-7.122265","112.389472");
        addLBB("5","LBB Rumah Belajar Genius Babat","Jl. Raya Plaosoan No. 21 Babat - Lamongan ","085643943663","-7.10021","112.18789");
        addLBB("6","LKP Media Cinta Ilmu","Jl. KH. Ahmad Dahlan Gg. Mawar No. 3 Tlogoanyar - Lamongan","085732343431","-7.123730","112.418568");
        addLBB("7","Sony Sugema College (SSC) Babat","Jl. Gotong Royong, Babat - Lamongan","0857-8527-9459","-7.103877","112.171586");
        addLBB("8","LBB Primagama Lamongan","Ruko LTC Blok B 9-10 Jl. Sunan Giri, Lamongan","0322 - 321206","-7.114910","112.407796");
        addLBB("9","LBB Primagama Babat","Jl. Gotong Royong, Babat - Lamongan","","-7.1051949","112.1709040");
        addLBB("10","Ganesha Operation (GO) Babat","Jl. Pantura 81 Bedahan Babat - Lamongan","","-7.082912","112.181046");
        addLBB("11","Ganesha Operation (GO) Lamongan","Jl. Kombes Pol. Moh Duryat Sidoharjo Lamongan","","-7.121181","112.418896");
        addLBB("12","Airlangga Bimbel","Sukomulyo Lamongan","","-7.120578","112.414301");
        addLBB("13","English Action (EA) Babat","Jl. Gotong Royong Babat - Lamongan","0856 - 4543 - 6963","-7.104063","112.171675");
        addLBB("14","Bimbel SSC Lamongan","Jl. Veteran No.64 Banjarmendalan Lamongan","","-7.118784","112.422317");
        addLBB("15","English Action (EA) Lamongan","Jl. Kusuma Bangsa No.40 Tumenggungan Lamongan","056 - 3166 - 295","-7.113951","112.406498");

    }
    public void addLBB(String no,String nama, String alamat, String notel, String latlbb, String longlbb){
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("insert into lbb(no, nama, alamat, notel, latlbb, longlbb) values('" + no + "','" + nama + "','" + alamat + "','" + notel + "','" + latlbb + "','" + longlbb + "')");
            finish();
        }catch (SQLiteException e){
            Log.d("Error Msk db", e.toString());
        }
    }
}
