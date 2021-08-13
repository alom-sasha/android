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
import android.widget.EditText;

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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;





public class MainActivity extends AppCompatActivity{
    private BottomNavigationView mBottomNV;
    public static Context context;

    private static final String TAG = "map";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private NaverMap mNaverMap;
    private FusedLocationSource mLocationSource;

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

        String key = "pk.eyJ1IjoiaGFpc2VvbmciLCJhIjoiY2tyZjgxN3Y3MHZxazJvdDc3aHY5d2VzbiJ9.OX59hcCGUPl-ipdu1nfzdQ";
        LatLng origin = new LatLng(37.516260,127.131193);
        LatLng destination = new LatLng(37.510166,127.132372);

        String result;


        try{
            MainActivity.HttpAsynTask task = new MainActivity.HttpAsynTask();

            result = task.execute("https://api.mapbox.com/directions/v5/mapbox/walking/"
                    +origin.longitude+","+origin.latitude+";"+destination.longitude+","+destination.latitude
                    +"?geometries=geojson&access_token="+key).get();

            Log.d("result sl ==> " , result);
        }catch(Exception e){
            result = "none";
        }
        String[] result_d = result.split(" ");
        ArrayList<LatLng> result_latlng = new ArrayList<>();
        Log.d("String result_d", result_d[5]);
        for(int i=0;i<result_d.length;i+=2){
            double lat = Double.parseDouble(result_d[i]);
            double lng = Double.parseDouble(result_d[i+1]);
            result_latlng.add(new LatLng(lat, lng));
        }

        Log.d("arraylist", result_latlng.toString());


    }

    public static class Path{
        private ArrayList<LatLng> coordinates = new ArrayList<LatLng>();

        public Path(ArrayList<LatLng> coordinates) {
            this.coordinates = coordinates;
        }

        public ArrayList<LatLng> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(ArrayList<LatLng> coordinates) {
            this.coordinates = coordinates;
        }

        @Override
        public String toString() {
//            return "Path{" +
//                    "coordinates=" + coordinates +
//                    '}';
            return coordinates + " ";
        }

    }

    private static class HttpAsynTask extends AsyncTask<String, Void, String> {
        OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            List<Path> pathList = new ArrayList<>();
            String strUrl = params[0];
            String result = null;


            try {
                Request request = new Request.Builder()
                        .url(strUrl)
                        .build();
                Response response = client.newCall(request).execute();

                JSONObject jsonObject = new JSONObject(response.body().string());
                JSONArray routes = jsonObject.getJSONArray("routes");

                for(int i=0; i<routes.length();i++){
                    JSONObject route = routes.getJSONObject(i);
                    JSONObject geometry = route.getJSONObject("geometry");
                    JSONArray coordinates = geometry.getJSONArray("coordinates");
                    ArrayList<LatLng> a = new ArrayList<LatLng>();

                    for(int j=0;j<coordinates.length();j++){
                        JSONArray latlng = coordinates.getJSONArray(j);
                        Double lat = latlng.getDouble(1);
                        //double lat = Double.valueOf(lat_.toString());
                        Log.d(TAG,lat.toString());
                        if( result == null){
                            result = lat.toString() + " ";
                        } else{
                            result += lat.toString() + " ";
                        }

                        Double lng = latlng.getDouble(0);
                        //double lng = Double.valueOf(lng_.toString());
                        result += lng.toString() + " ";
                        Log.d(TAG,lng.toString());
                        //LatLng l = new LatLng(lat,lng);
                        //a.add(l);
                        Log.d(TAG,latlng.toString());
                    }

                    //Path p = new Path(a);
                    /*
                    Path 생성
                    Path{coordinates=[lat/lng: (37.516245,127.131227), lat/lng: (37.515881,127.130975), lat/lng: (37.515959,127.130796), lat/lng: (37.512617,127.128531), lat/lng: (37.512094,127.128512), lat/lng: (37.511757,127.128627), lat/lng: (37.511299,127.129098), lat/lng: (37.511044,127.129711), lat/lng: (37.511038,127.129983), lat/lng: (37.510071,127.13231)]}
                    */
                    //Log.d(TAG,p.toString());
                    //result = p.toString();
                    //Log.d("result in ==>" , result);
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s!=null){
                Log.d(TAG, s);
            }
        }
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
            } else if (id == R.id.ic_alert) {
                fragment = new AlertPageFragment();
            } else if (id == R.id.ic_community) {
                fragment = new CommunityPageFragment();
            } else if (id == R.id.ic_calendar) {
                fragment = new CalendarPageFragment();
            } else {
                fragment = new SettingsPageFragment();
            }
            fragmentTransaction.add(R.id.content_layout, fragment, tag);
        } else {
            fragmentTransaction.show(fragment);
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    public FusedLocationSource getmLocationSource() {
        return mLocationSource;
    }

    public void setmLocationSource(FusedLocationSource mLocationSource) {
        this.mLocationSource = mLocationSource;
    }

    public NaverMap getmNaverMap() {
        return mNaverMap;
    }

    public void setmNaverMap(NaverMap mNaverMap) {
        this.mNaverMap = mNaverMap;
    }

    public static int getPermissionRequestCode() {
        return PERMISSION_REQUEST_CODE;
    }

    public static String[] getPERMISSIONS() {
        return PERMISSIONS;
    }
}