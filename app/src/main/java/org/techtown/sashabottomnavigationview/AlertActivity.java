package org.techtown.sashabottomnavigationview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.sashabottomnavigationview.Utils.BottomNavigationViewHelper;

public class AlertActivity extends AppCompatActivity {

    private static final String TAG="AlertActivity";
    private static final int ACTIVITY_NUM=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG, "onCreate: started.");

        setupBottomNavigationView();
    }

    //BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG,"setupBottomNavigationView: setting up BottomNavigationView.");
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(AlertActivity.this,bottomNavigationView);
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
