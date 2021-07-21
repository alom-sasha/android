package com.example.sasha.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.sasha.AlertActivity;
import com.example.sasha.CommunityActivity;
import com.example.sasha.LogActivity;
import com.example.sasha.MapActivity;
import com.example.sasha.R;
import com.example.sasha.SettingsActivity;

public class BottomNavigationViewHelper {
    private static final String TAG="BottomNavigationViewHelper";

    public static void disableShiftMode(BottomNavigationView view) {//아직 잘 모르겠습니다..ㅠㅠㅠ
    }

    public static void enableNavigation(final Context context, BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_map:
                        Intent intent1=new Intent(context, MapActivity.class);//ACTIVITY_NUM=0
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_alert:
                        Intent intent2=new Intent(context, AlertActivity.class);//ACTIVITY_NUM=1
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_community:
                        Intent intent3=new Intent(context, CommunityActivity.class);//ACTIVITY_NUM=2
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_calendar:
                        Intent intent4=new Intent(context, LogActivity.class);//ACTIVITY_NUM=3
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_settings:
                        Intent intent5=new Intent(context, SettingsActivity.class);//ACTIVITY_NUM=4
                        context.startActivity(intent5);
                        break;
                }
                return false;
            }
        });
    }
}



























