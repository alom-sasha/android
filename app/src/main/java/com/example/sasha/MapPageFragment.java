package com.example.sasha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ArrayListMultimap;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.sasha.MainActivity.context;

public class MapPageFragment extends Fragment  implements OnMapReadyCallback{

    private static final String TAG = "mapFragment";
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();


    private NaverMap mNaverMap;
    private FusedLocationSource mLocationSource;

    ArrayList<LatLng> latlngs_light = new ArrayList<>();

    ArrayList<Map<String, Object>> light = new ArrayList<>();
    ArrayList<Marker> markers_light = new ArrayList<>();

    ArrayList<Map<String, Object>> alarmbell = new ArrayList<>();
    ArrayList<Marker> markers_alarmbell = new ArrayList<>();

    ArrayList<Map<String, Object>> cctv = new ArrayList<>();
    ArrayList<Marker> markers_cctv = new ArrayList<>();

    ArrayList<Map<String, Object>> policeoffice = new ArrayList<>();
    ArrayList<Marker> markers_policeoffice = new ArrayList<>();
    ArrayList<InfoWindow> info_policeoffice = new ArrayList<>();

    ArrayList<Map<String, Object>> safeguardhouse = new ArrayList<>();
    ArrayList<Marker> markers_safeguardhouse = new ArrayList<>();
    ArrayList<InfoWindow> info_safeguardhouse = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getActivity();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_map_page, container, false);

        Button btn_light_onoff = (Button) rootView.findViewById(R.id.btn_light_onoff);
        btn_light_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check == 0) {
                    for (Marker marker : markers_light) {
                        marker.setMap(null);
                        mainActivity.check = 1;
                    }
                } else {
                    for (Marker marker : markers_light) {
                        marker.setMap(mNaverMap);
                        mainActivity.check = 0;
                    }
                }
            }
        });

        Button btn_cctv_onoff = (Button) rootView.findViewById(R.id.btn_cctv_onoff);
        btn_cctv_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check1 == 0) {
                    for (Marker marker : markers_cctv) {
                        marker.setMap(null);
                        mainActivity.check1 = 1;
                    }
                } else {
                    for (Marker marker : markers_cctv) {
                        marker.setMap(mNaverMap);
                        mainActivity.check1 = 0;
                    }
                }
            }
        });

        Button btn_police_onoff = (Button) rootView.findViewById(R.id.btn_police_onoff);
        btn_police_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check2 == 0) {
                    for (Marker marker : markers_policeoffice) {
                        marker.setMap(null);
                        mainActivity.check2 = 1;
                    }
                } else {
                    for (Marker marker : markers_policeoffice) {
                        marker.setMap(mNaverMap);
                        mainActivity.check2 = 0;
                    }
                }
            }
        });

        Button btn_safeguard_onoff = (Button) rootView.findViewById(R.id.btn_safeguard_onoff);
        btn_safeguard_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check3 == 0) {
                    for (Marker marker : markers_safeguardhouse) {
                        marker.setMap(null);
                        mainActivity.check3 = 1;
                    }
                } else {
                    for (Marker marker : markers_safeguardhouse) {
                        marker.setMap(mNaverMap);
                        mainActivity.check3 = 0;
                    }
                }
            }
        });

        Button btn_alarmbell_onoff = (Button) rootView.findViewById(R.id.btn_alarmbell_onoff);
        btn_alarmbell_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check4 == 0) {
                    for (Marker marker : markers_alarmbell) {
                        marker.setMap(null);
                        mainActivity.check4 = 1;
                    }
                } else {
                    for (Marker marker : markers_alarmbell) {
                        marker.setMap(mNaverMap);
                        mainActivity.check4 = 0;
                    }
                }
            }
        });

        Button btn_mode_onoff = (Button) rootView.findViewById(R.id.btn_mode_onoff);
        btn_mode_onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainActivity.check5 == 0) {
                    mNaverMap.setMapType(NaverMap.MapType.Navi);
                    mNaverMap.setNightModeEnabled(true);
                    mainActivity.check5= 1;
                } else {
                    mNaverMap.setNightModeEnabled(false);
                    mNaverMap.setMapType(NaverMap.MapType.Basic);
                    mainActivity.check5= 0;
                }
            }
        });

        Button btn_login=(Button)rootView.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainActivity.getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


        EditText edittext_destination = (EditText) rootView.findViewById(R.id.edittext_destination);
        edittext_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SetOriginDestinationFregment fragment = new SetOriginDestinationFregment();
            }
        });


        // 지도
        FragmentManager fm = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        mLocationSource=new FusedLocationSource(this, mainActivity.getPermissionRequestCode());

        return rootView;
    }

    @Override // 지도
    public void onMapReady(@NonNull NaverMap naverMap) {
        MainActivity mainActivity = (MainActivity) getActivity();
        Log.d(TAG, "OnMapReady");

        mNaverMap=naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        ActivityCompat.requestPermissions(mainActivity, mainActivity.getPERMISSIONS(), mainActivity.getPermissionRequestCode());
        Log.d(TAG, "onMapReady: "+
                mNaverMap.getContentBounds().toString());
//        mNaverMap.getLocationSource().activate(new LocationSource.OnLocationChangedListener() {
//            @Override
//            public void onLocationChanged(@Nullable Location location) {
//                Log.d(TAG, "onLocationChanged: "+
//                        location.toString());
//            }
//        });

        //light location  //Log.d(TAG, document.getId() + " => " + latitude + "   " + longitude + " " + String.valueOf(latlngs_light.size()));
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
                                double latitude = (Double) cur.get("latitude");
                                double longitude = (Double) cur.get("longitude");
                                String address = cur.get("adrress").toString();
                                String belong = cur.get("belong").toString();
                                String name = cur.get("name").toString();

                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.BLACK);
                                marker.setAlpha(1);
                                marker.setMinZoom(10);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_police_24));
                                marker.setAnchor(new PointF(1, 1));
                                markers_policeoffice.add(marker);

                                InfoWindow infoWindow = new InfoWindow();
                                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
                                    @NonNull
                                    @Override
                                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                        return "address : " + address + "\n" + "name : " + name + "\n" + "belong : " + belong;
                                    }
                                });
                                infoWindow.setPosition(new LatLng(latitude, longitude));
                                info_policeoffice.add(infoWindow);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_policeoffice) {
                            marker.setMap(naverMap);

                        }
                        for(InfoWindow info : info_policeoffice){
                            info.open(naverMap);
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
                                String address_lot = cur.get("adrress_lot").toString();
                                String address_street = cur.get("adrress_street").toString();
                                String phone_number = cur.get("phone_number").toString();
                                String name = cur.get("name").toString();


                                Marker marker = new Marker();
                                marker.setPosition(new LatLng(latitude, longitude));
                                marker.setIconTintColor(Color.GRAY);
                                marker.setAlpha(1);
                                marker.setMinZoom(15);
                                marker.setMaxZoom(21);
                                marker.setIcon(OverlayImage.fromResource(R.drawable.ic_map_safehouse_24));
                                markers_safeguardhouse.add(marker);

                                InfoWindow infoWindow = new InfoWindow();
                                infoWindow.setAdapter(new InfoWindow.DefaultTextAdapter(context) {
                                    @NonNull
                                    @Override
                                    public CharSequence getText(@NonNull InfoWindow infoWindow) {
                                        return "address_lot : " + address_lot + "\n" + "address_street : " + address_street + "\n"+ "name : " + name + "\n" + "phone_number : " + phone_number;
                                    }
                                });
                                infoWindow.setPosition(new LatLng(latitude, longitude));
                                //infoWindow.open(marker);
                                info_safeguardhouse.add(infoWindow);

                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        for (Marker marker : markers_safeguardhouse) {
                            marker.setMap(naverMap);
                        }
                        for(InfoWindow info : info_safeguardhouse){
                            info.open(naverMap);
                        }
                    }
                });

        PathOverlay path = new PathOverlay();
        path.setCoords(mainActivity.result_latlng);
        path.setMap(naverMap);
    }
    @Override // 지도
    //한슬님한테 한슬님 avd나 폰에서는 현위치 마커기능 잘 작동하는지 물어보기. 지금 onRequestPermissionsResult가 아예 호출되지 않는 것 같음.
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivity mainActivity = (MainActivity) getActivity();
        Log.d(TAG, "onRequestPermissionsResult: "+
                mainActivity.getPermissionRequestCode()+" , "+
                grantResults.toString());
        if (requestCode == mainActivity.getPermissionRequestCode()) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

//                mNaverMap.addOnLocationChangeListener(location ->
//                        Toast.makeText(mainActivity,
//                                location.getLatitude() + ", " + location.getLongitude(),
//                                Toast.LENGTH_LONG).show());
            }
        }
    }

}