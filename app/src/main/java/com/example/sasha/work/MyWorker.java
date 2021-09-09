package com.example.sasha.work;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sasha.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;

public class MyWorker extends Worker {
    //https://stackoverflow.com/questions/53573436/how-can-location-updates-from-fusedlocationproviderclient-be-processed-with-work
    private static final String TAG = "MyWorker";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private Location mLocation;
    private FusedLocationProviderClient mFusedLocationClient;
    private Context mContext;
    private LocationCallback mLocationCallback;

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Done");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //debugging을 위한 알림 메세지 출력 코드입니다. debugging이 끝나서 주석 처리 하였습니다------------------------
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, "default");
//        builder.setSmallIcon(R.mipmap.ic_launcher);
        //------------------------------------------------------------------------------------------------------------


        try {
            mFusedLocationClient
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                Log.d(TAG, "Location - lat : " + mLocation.getLatitude());
                                Log.d(TAG, "Location - lng : " + mLocation.getLongitude());
                                Toast.makeText(mContext,"Location - lat : " + mLocation.getLatitude(),Toast.LENGTH_LONG).show();

                                //debugging을 위한 알림 메세지 출력 코드입니다. debugging이 끝나서 주석 처리 하였습니다------------------------
//                                builder.setContentTitle("current location");
//                                builder.setContentText("Location - lat : " + mLocation.getLatitude());
//                                NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                    notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
//                                }
//                                notificationManager.notify(1, builder.build());
                                //------------------------------------------------------------------------------------------------------------

                                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, null);
        } catch (SecurityException unlikely) {
            //Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
        return Result.success();
    }
}
