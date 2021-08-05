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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
import com.naver.maps.map.overlay.OverlayImage;
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

    ArrayList<Map<String, Object>> light = new ArrayList<>();
    ArrayList<Marker> markers_light = new ArrayList<>();

    ArrayList<Map<String, Object>> alarmbell = new ArrayList<>();
    ArrayList<Marker> markers_alarmbell = new ArrayList<>();

    ArrayList<Map<String, Object>> cctv = new ArrayList<>();
    ArrayList<Marker> markers_cctv = new ArrayList<>();

    ArrayList<Map<String, Object>> policeoffice = new ArrayList<>();
    ArrayList<Marker> markers_policeoffice = new ArrayList<>();

    ArrayList<Map<String, Object>> safeguardhouse = new ArrayList<>();
    ArrayList<Marker> markers_safeguardhouse = new ArrayList<>();

    int check = 0, check1 = 0, check2 = 0, check3= 0, check4 = 0, check5 = 0;

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

        context = this;


        Button btn_light_onoff = (Button) findViewById(R.id.btn_light_onoff);
        btn_light_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {
                    for (Marker marker : markers_light) {
                        marker.setMap(null);
                        check = 1;
                    }
                } else {
                    for (Marker marker : markers_light) {
                        marker.setMap(mNaverMap);
                        check = 0;
                    }
                }
            }
        });

        Button btn_cctv_onoff = (Button) findViewById(R.id.btn_cctv_onoff);
        btn_cctv_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check1 == 0) {
                    for (Marker marker : markers_cctv) {
                        marker.setMap(null);
                        check1 = 1;
                    }
                } else {
                    for (Marker marker : markers_cctv) {
                        marker.setMap(mNaverMap);
                        check1 = 0;
                    }
                }
            }
        });

        Button btn_police_onoff = (Button) findViewById(R.id.btn_police_onoff);
        btn_police_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check2 == 0) {
                    for (Marker marker : markers_policeoffice) {
                        marker.setMap(null);
                        check2 = 1;
                    }
                } else {
                    for (Marker marker : markers_policeoffice) {
                        marker.setMap(mNaverMap);
                        check2 = 0;
                    }
                }
            }
        });

        Button btn_safeguard_onoff = (Button) findViewById(R.id.btn_safeguard_onoff);
        btn_safeguard_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check3 == 0) {
                    for (Marker marker : markers_safeguardhouse) {
                        marker.setMap(null);
                        check3 = 1;
                    }
                } else {
                    for (Marker marker : markers_safeguardhouse) {
                        marker.setMap(mNaverMap);
                        check3 = 0;
                    }
                }
            }
        });

        Button btn_alarmbell_onoff = (Button) findViewById(R.id.btn_alarmbell_onoff);
        btn_alarmbell_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check4 == 0) {
                    for (Marker marker : markers_alarmbell) {
                        marker.setMap(null);
                        check4 = 1;
                    }
                } else {
                    for (Marker marker : markers_alarmbell) {
                        marker.setMap(mNaverMap);
                        check4 = 0;
                    }
                }
            }
        });

        Button btn_mode_onoff = (Button) findViewById(R.id.btn_mode_onoff);
        btn_mode_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check5 == 0) {
                    mNaverMap.setMapType(NaverMap.MapType.Navi);
                    mNaverMap.setNightModeEnabled(true);
                    check5= 1;
                } else {
                    mNaverMap.setNightModeEnabled(false);
                    mNaverMap.setMapType(NaverMap.MapType.Basic);
                    check5= 0;
                }
            }
        });

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
        Button btn_light_onoff = (Button) findViewById(R.id.btn_light_onoff);
        Button btn_cctv_onoff = (Button) findViewById(R.id.btn_cctv_onoff);
        Button btn_police_onoff = (Button) findViewById(R.id.btn_police_onoff);
        Button btn_safeguard_onoff = (Button) findViewById(R.id.btn_safeguard_onoff);
        Button btn_alarmbell_onoff = (Button) findViewById(R.id.btn_alarmbell_onoff);

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
            } else if (id == R.id.ic_alert) {
                btn_light_onoff.setVisibility(View.INVISIBLE);
                btn_cctv_onoff.setVisibility(View.INVISIBLE);
                btn_police_onoff.setVisibility(View.INVISIBLE);
                btn_safeguard_onoff.setVisibility(View.INVISIBLE);
                btn_alarmbell_onoff.setVisibility(View.INVISIBLE);
                fragment = new AlertPageFragment();
            } else if (id == R.id.ic_community) {
                btn_light_onoff.setVisibility(View.INVISIBLE);
                btn_cctv_onoff.setVisibility(View.INVISIBLE);
                btn_police_onoff.setVisibility(View.INVISIBLE);
                btn_safeguard_onoff.setVisibility(View.INVISIBLE);
                btn_alarmbell_onoff.setVisibility(View.INVISIBLE);
                fragment = new CommunityPageFragment();
            } else if (id == R.id.ic_calendar) {
                btn_light_onoff.setVisibility(View.INVISIBLE);
                btn_cctv_onoff.setVisibility(View.INVISIBLE);
                btn_police_onoff.setVisibility(View.INVISIBLE);
                btn_safeguard_onoff.setVisibility(View.INVISIBLE);
                btn_alarmbell_onoff.setVisibility(View.INVISIBLE);
                fragment = new CalendarPageFragment();
            } else {
                btn_light_onoff.setVisibility(View.INVISIBLE);
                btn_cctv_onoff.setVisibility(View.INVISIBLE);
                btn_police_onoff.setVisibility(View.INVISIBLE);
                btn_safeguard_onoff.setVisibility(View.INVISIBLE);
                btn_alarmbell_onoff.setVisibility(View.INVISIBLE);
                fragment = new SettingsPageFragment();
            }
            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
            if (id == R.id.ic_map) {
                btn_light_onoff.setVisibility(View.VISIBLE);
                btn_cctv_onoff.setVisibility(View.VISIBLE);
                btn_police_onoff.setVisibility(View.VISIBLE);
                btn_safeguard_onoff.setVisibility(View.VISIBLE);
                btn_alarmbell_onoff.setVisibility(View.VISIBLE);
            } else{
                btn_light_onoff.setVisibility(View.INVISIBLE);
                btn_cctv_onoff.setVisibility(View.INVISIBLE);
                btn_police_onoff.setVisibility(View.INVISIBLE);
                btn_safeguard_onoff.setVisibility(View.INVISIBLE);
                btn_alarmbell_onoff.setVisibility(View.INVISIBLE);
            }
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    @Override // 지도
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "OnMapReady");
        mNaverMap = naverMap;
        //Log.d("start db", "db start");

        //light location                                 //Log.d(TAG, document.getId() + " => " + latitude + "   " + longitude + " " + String.valueOf(latlngs_light.size()));
        db.collection("light")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                light.add(cur);
                                double latitude = (Double) cur.get("latitude");
                                double longitude = (Double) cur.get("longitude");
                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_light_24));
                                marker.setIconTintColor(Color.MAGENTA);
                                marker.setAlpha(1);
                                marker.setMinZoom(15);
                                marker.setMaxZoom(21);
                                markers_light.add(marker);
                                latlngs_light.add(new LatLng(latitude, longitude));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        for (Marker marker : markers_light) {
                            marker.setMap(naverMap);
                        }
                    }
                });


        //cctv location
        db.collection("cctv")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                cctv.add(cur);
                                double latitude = (Double) cur.get("latitude");
                                double longitude = (Double) cur.get("longitude");

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.RED);
                                marker.setAlpha(1);
                                marker.setMinZoom(15);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_cctv_24));
                                markers_cctv.add(marker);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_cctv) {
                            marker.setMap(naverMap);
                        }
                    }
                });

        // alarmbell location
        db.collection("alarmbell")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                alarmbell.add(cur);
                                double latitude = (Double) cur.get("latitude");
                                double longitude = (Double) cur.get("longitude");

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.GREEN);
                                marker.setAlpha(1);
                                marker.setMinZoom(15);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_alarmbell_24));
                                markers_alarmbell.add(marker);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_alarmbell) {
                            marker.setMap(naverMap);
                        }
                    }
                });

        //policeoffice location
        db.collection("policeoffice")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                policeoffice.add(cur);
                                double longitude = (Double) cur.get("latitude");
                                double latitude = (Double) cur.get("longitude");

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.BLACK);
                                marker.setAlpha(1);
                                marker.setMinZoom(10);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_police_24));
                                markers_policeoffice.add(marker);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_policeoffice) {
                            marker.setMap(naverMap);
                        }
                    }
                });

        //safeguardhouse location
        db.collection("safeguardhouse")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> cur = new HashMap<>();
                                cur = document.getData();
                                safeguardhouse.add(cur);
                                double latitude = (Double) cur.get("latitude");
                                double longitude = (Double) cur.get("longitude");

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.GRAY);
                                marker.setAlpha(1);
                                marker.setMinZoom(15);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_safehouse_24));
                                markers_safeguardhouse.add(marker);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_safeguardhouse) {
                            marker.setMap(naverMap);
                        }
                    }
                });


        //현재 위치 추적
        mNaverMap.setLocationSource(mLocationSource);
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);

        //UI Setting
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        //uiSettings.setIndoorLevelPickerEnabled(true);
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