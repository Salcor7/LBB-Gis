package com.subayu.comarudin.lbb_gis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);
        final FrameLayout flHolder = (FrameLayout)findViewById(R.id.about);

        AboutBuilder builder = AboutBuilder.with(getApplicationContext())
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .setPhoto(R.drawable.me)
                .setCover(R.mipmap.profile_cover)
                .setLinksAnimated(true)
                .setDividerDashGap(13)
                .setName("Chomaruddin Salam")
                .setSubTitle("111310324")
                .setBrief("Lahir di Lamongan pada 17 Mei 1989, berdomisili di Ds. Babat – Kab. Lamongan, tercatat sebagai mahasiswa aktif di Universitas Islam Lamongan , anak ke 2 dari 2 bersaudara pasangan Bpk. Wasito Adi Saputro dan Ibu Suhati, mempunyai hobi bermain futsal dan badminton, seorang yag humoris dan pekerja keras.")
                .setBrief("Menamatkan pendidikan Sekolah Dasar di SDN III Babat , Melanjutkan Sekolah Menengah Pertama di SMPN 1 Babat dan Sekolah Menengah Atas di SMAN 1 Babat.")
                .setLinksColumnsCount(4)
                .addMoreFromMeAction("Vansuita")
                .setVersionNameAsAppSubTitle();

        AboutView view = builder.build();

        flHolder.addView(view);
    }
}
