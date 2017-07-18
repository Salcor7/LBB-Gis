package com.subayu.comarudin.lbb_gis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuUtama} interface
 * to handle interaction events.
 * Use the {@link MenuUtama} factory method to
 * create an instance of this fragment.
 */
public class MenuUtama extends Fragment implements View.OnClickListener{
    LinearLayout ln1,ln2,ln3,ln4;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_utama, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ln1 = (LinearLayout)view.findViewById(R.id.menuklik1);
        ln2 = (LinearLayout)view.findViewById(R.id.menuklik2);
        ln3 = (LinearLayout)view.findViewById(R.id.menuklik3);
        ln4 = (LinearLayout)view.findViewById(R.id.menuklik4);

        ln1.setOnClickListener(this);
        ln2.setOnClickListener(this);
        ln3.setOnClickListener(this);
        ln4.setOnClickListener(this);

        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menuklik1 :
                Intent intent = new Intent(getActivity(),DaftarLBB.class);
                getActivity().startActivity(intent);
                break;
            case R.id.menuklik2 :
                Intent intent1 = new Intent(getActivity(),Lokasi.class);
                getActivity().startActivity(intent1);
                break;
            case R.id.menuklik3 :
                Intent intent3 = new Intent(getActivity(),CariLBB.class);
                getActivity().startActivity(intent3);
                break;
            case R.id.menuklik4 :
                Intent intent4 = new Intent(getActivity(),About.class);
                getActivity().startActivity(intent4);
                break;

        }
    }
}
