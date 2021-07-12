package com.example.sasha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class report extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Button b = (Button)findViewById(R.id.button_report);
        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 오랫동안 눌렀을 때 이벤트가 발생됨
                Toast.makeText(getApplicationContext(),
                        "신고하려면 아래로 스와이프 해주세요.", Toast.LENGTH_SHORT).show();

                // 리턴값이 있다
                // 이메서드에서 이벤트에대한 처리를 끝냈음
                // 그래서 다른데서는 처리할 필요없음 true
                // 여기서 이벤트 처리를 못했을 경우는 false
                return true;
            }

        });



    }
}