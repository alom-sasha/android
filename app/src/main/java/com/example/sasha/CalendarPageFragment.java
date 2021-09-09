package com.example.sasha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sasha.decorator.SaturdayDecorator;
import com.example.sasha.decorator.SundayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


public class CalendarPageFragment extends Fragment implements OnDateSelectedListener{

    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        activity=mainActivity;
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar_page, container, false);

        MaterialCalendarView materialCalendarView=rootView.findViewById(R.id.calendarView);
        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator());

        materialCalendarView.setOnDateChangedListener(this);

        return rootView;
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Toast.makeText(activity,String.valueOf(date),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(activity.getApplicationContext(), TraceActivity.class);
        startActivity(intent);
    }
}