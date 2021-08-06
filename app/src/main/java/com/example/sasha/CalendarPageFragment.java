package com.example.sasha;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sasha.decorator.SaturdayDecorator;
import com.example.sasha.decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


public class CalendarPageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar_page, container, false);

        MaterialCalendarView materialCalendarView=rootView.findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        return rootView;
    }
}