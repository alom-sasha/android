package com.example.sasha;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class SettingsPageFragment extends Fragment {

    ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView=(ViewGroup) inflater.inflate(R.layout.fragment_settings_page, container, false);
        scrollView=rootView.findViewById(R.id.scrollView);
        scrollView.setVerticalScrollBarEnabled(true);

        return rootView;
    }
}