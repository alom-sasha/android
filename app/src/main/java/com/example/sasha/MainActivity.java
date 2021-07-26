package com.example.sasha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private BottomNavigationView mBottomNV;
    public static Context context;

    private static final String TAG = "map";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<LatLng> latlngs_light = new ArrayList<>();
    ArrayList<Marker> markers_light = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNV = findViewById(R.id.bottomNavViewBar);
        mBottomNV.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { //NavigationItemSelecte
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                BottomNavigate(menuItem.getItemId());
                return true;
            }
        });
        mBottomNV.setSelectedItemId(R.id.ic_map);

        context=this;



        // 지도
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

    }

    private void BottomNavigate(int id) {  //BottomNavigation 페이지 변경
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            if (id == R.id.ic_map) {
                fragment = new MapPageFragment();
            }
            else if (id == R.id.ic_alert){

                fragment = new AlertPageFragment();
            }
            else if (id == R.id.ic_community){

                fragment = new CommunityPageFragment();
            }
            else if (id == R.id.ic_calendar){

                fragment = new CalendarPageFragment();
            }
            else {
                fragment = new SettingsPageFragment();
            }

            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        }
        else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    @Override // 지도
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "OnMapReady");

        mNaverMap = naverMap;

        Log.d("start db" , "db start");
        db.collection("light")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                double latitude = (Double)cur.get("latitude");
                                double longitude = (Double)cur.get("longitude");

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                //marker.setMap(naverMap);
                                markers_light.add(marker);
                                latlngs_light.add(new LatLng(latitude, longitude));
                                //Log.d(document.getId(),(Double)cur.get("latitude") + "   " + (Double)cur.get("longitude"));

                                Log.d(TAG, document.getId() + " => " + latitude + "   "  + longitude+ " "+String.valueOf(latlngs_light.size()) );
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_light) {
                            marker.setMap(naverMap);
                        }
                    }
                });

        //iterator 사용




        //현재 위치 추적

        mNaverMap.setLocationSource(mLocationSource);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        //UI Setting
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        uiSettings.setIndoorLevelPickerEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
//        // 위치 변경 이벤트
//        mNaverMap.addOnLocationChangeListener(location ->
//                Toast.makeText(this,
//                        location.getLatitude() + ", " + location.getLongitude(),
//                        Toast.LENGTH_SHORT).show());

    }

    @Override // 지도
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }
}