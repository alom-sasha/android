package org.techtown.sashabottomnavigationview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.sashabottomnavigationview.Utils.BottomNavigationViewHelper;

public class MapActivity extends AppCompatActivity {//main activity

    private static final String TAG="MapActivity";
    private static final int ACTIVITY_NUM=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG,"onCreate : starting.");

        setupBottomNavigationView();
    }
    //BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG,"setupBottomNavigationView: setting up BottomNavigationView.");
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(MapActivity.this,bottomNavigationView);
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.not_move_activity,R.anim.not_move_activity);
        finish();
    }
}