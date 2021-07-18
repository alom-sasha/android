package org.techtown.sashabottomnavigationview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.sashabottomnavigationview.Utils.BottomNavigationViewHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MapActivity extends AppCompatActivity {//main activity

    private static final String TAG="MapActivity";
    private static final int ACTIVITY_NUM=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Log.d(TAG,"onCreate : starting.");

        setupBottomNavigationView();
        getHashKey();
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

    //map api
    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}