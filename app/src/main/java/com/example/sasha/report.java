package com.example.sasha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class report extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Activity activity = this;

        final String phoneNumber = "010-0000-0000"; // 나중에 112 로 바꿔야함.
        final String message = "신고합니다 ~~~~"; // 현제 위치등 정보를 문자로 신고
        Button button_report = (Button)findViewById(R.id.button_report);
        button_report.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 오랫동안 눌렀을 때 이벤트가 발생됨
                Toast.makeText(getApplicationContext(),
                        "신고하려면 아래로 스와이프 해주세요.", Toast.LENGTH_SHORT).show();
                // 스와이프 하는 코드

                // 신고 문자 보내기
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.SEND_SMS},1);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(phoneNumber, null, message, null, null);


                return true;
            }

        });



    }
}