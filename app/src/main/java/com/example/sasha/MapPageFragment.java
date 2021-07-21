package com.example.sasha;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naver.maps.map.MapView;

public class MapPageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity=(MainActivity)getActivity();
        MapView mapView;
        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_map_page, container, false);

        mapView = rootView.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        Button btn_tmp = rootView.findViewById(R.id.btn_tmp);
        btn_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity.getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}