package com.example.sasha.work;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sasha.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LogWorker extends Worker {

    public LogWorker(Context context, WorkerParameters params) {
        super(context, params);
        Log.d(TAG, "LogWorker: started.");
        this.context = context;
        if (context!=null){
            Log.d(TAG, "LogWorker: "+context.toString());
        }
    }

    Context context;
    private static final String TAG = "logWorker";
    private FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);

    @NonNull
    @Override
    public Result doWork() {
        //do the work here

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "doWork: permission does not granted.");
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Log.d(TAG, "logWorker_doWork_onSuccess: lat = "+location.getLatitude());
                    Log.d(TAG, "logWorker_doWork_onSuccess: lng = "+location.getLongitude());
                }
            }
        });

//        LocationManager locationManager=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//        String locationProvider=LocationManager.GPS_PROVIDER;
//        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "doWork: permission does not granted.");
//        }
//        Location lastKnownLocation =locationManager.getLastKnownLocation(locationProvider);
//        if (lastKnownLocation != null) {
//            double lng = lastKnownLocation.getLatitude();
//            double lat = lastKnownLocation.getLatitude();
//            Log.d("LogWorker", "longtitude=" + lng + ", latitude=" + lat);
//        }


        // Indicate whether the work finished successfully with the Result
        return Result.success();
    }
}
