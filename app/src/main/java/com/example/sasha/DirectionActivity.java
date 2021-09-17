package com.example.sasha;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Request;
import okhttp3.Response;

import android.graphics.Picture;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naver.maps.map.NaverMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.function.LongFunction;

public class DirectionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        final Boolean[] isOriginSelected = {false};
        final Boolean isDestinationSelected = false;

         ArrayList<Point> recommend_origin_list = new ArrayList<Point>();

        final Point[] selected_orign = {new Point()};


        RelativeLayout recommend_origin = (RelativeLayout) findViewById(R.id.recommend_origin);
        RelativeLayout recommend_destination = (RelativeLayout) findViewById(R.id.recommend_destination);
        recommend_origin.setVisibility(View.INVISIBLE);
        recommend_destination.setVisibility(View.INVISIBLE);
        EditText edittext_origin = (EditText) findViewById(R.id.edittext_origin);
        EditText edittext_destination = (EditText) findViewById(R.id.edittext_destination);


        for(int i=0; i < 5; i++) { // 텍스트 초기화
            TextView text = findViewById(R.id.recommend_origin + (i + 1));
            text.setText("");
        }
        for(int i=0; i < 5; i++) { // 텍스트 초기화
            TextView text = findViewById(R.id.recommend_destination + (i + 1));
            text.setText("");
        }

        for (int i=0; i<5;i++){
            TextView text = findViewById(R.id.recommend_origin + (i + 1));
            int finalI = i;
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isOriginSelected[0] = true;
                    edittext_origin.setText(text.getText());
                    selected_orign[0] = recommend_origin_list.get(finalI);
                }
            });
        }

        edittext_origin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //텍스트가 변경 될때마다 Call back
                for(int i=0; i < 5; i++) { // 텍스트 초기화
                    TextView text = findViewById(R.id.recommend_origin + (i + 1));
                    text.setText("");
                }

                if(edittext_origin.getText().toString().length() != 0){
                    recommend_origin_list.clear();
                    String apiResult = searchAPI(edittext_origin.getText().toString());
                    Log.d("test444", apiResult);
                    JSONArray jsonArray;
                    try {
                        jsonArray = new JSONArray(apiResult);
                        for(int i=0; i < jsonArray.length(); i++){
                            TextView text = findViewById(R.id.recommend_origin+(i+1));
                            String str = jsonArray.opt(i).toString();
                            JSONObject jsonObject = new JSONObject(str);
                            str = jsonObject.getString("title").replace("<b>","");
                            str = str.replace("</b>","");
                            str = str.replace("amp;","");
                            Point p = new Point(jsonObject.getInt("mapx"),jsonObject.getInt("mapy"));
                            // recommend_origin_list.set(i, p); 이부분 오

                            text.setText(str);
                            Log.d("test",str);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recommend_origin.setVisibility(View.VISIBLE);
                recommend_destination.setVisibility(View.INVISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //텍스트 입력이 모두 끝났을때 Call back

            }

            @Override
            public void afterTextChanged(Editable s) {
                //텍스트가 입력하기 전에 Call back

            }
        });

        edittext_destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //텍스트가 변경 될때마다 Call back
                for(int i=0; i < 5; i++) { // 텍스트 초기화
                    TextView text = findViewById(R.id.recommend_destination + (i + 1));
                    text.setText("");
                }

                if(edittext_destination.getText().toString().length() != 0){
                    String apiResult = searchAPI(edittext_destination.getText().toString());
                    Log.d("test444", apiResult);
                    JSONArray jsonArray;
                    try {
                        jsonArray = new JSONArray(apiResult);
                        for(int i=0; i < jsonArray.length(); i++){
                            TextView text = findViewById(R.id.recommend_destination+(i+1));
                            String str = jsonArray.opt(i).toString();
                            JSONObject jsonObject = new JSONObject(str);
                            str = jsonObject.getString("title").replace("<b>","");
                            str = str.replace("</b>","");
                            str = str.replace("amp;","");


                            text.setText(str);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                recommend_origin.setVisibility(View.INVISIBLE);
                recommend_destination.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //텍스트 입력이 모두 끝났을때 Call back

            }

            @Override
            public void afterTextChanged(Editable s) {
                //텍스트가 입력하기 전에 Call back

            }
        });

        edittext_origin.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                recommend_origin.setVisibility(View.VISIBLE);
                recommend_destination.setVisibility(View.INVISIBLE);
            }
        });

        edittext_destination.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                recommend_origin.setVisibility(View.INVISIBLE);
                recommend_destination.setVisibility(View.VISIBLE);
            }
        });


    }


    public String searchAPI(String finding){
        NaverSearchTask naverSearchTask = new NaverSearchTask();
        naverSearchTask.setFinding(finding);

        String result;
        try {
            result = naverSearchTask.execute().get();
        } catch (ExecutionException e) {
            result =null;
            e.printStackTrace();
        } catch (InterruptedException e) {
            result =null;
            e.printStackTrace();
        }

        return result;
    }




    public class NaverSearchTask extends AsyncTask<String, Void, String> {
        String finding = "잠실";

        @Override
        protected String doInBackground(String... strings) {
            String result = SearchAPI.main(finding);
            Log.d("test", "result = " + result);

            String items;
            try {
                JSONObject jsonObject = new JSONObject(result);
                int display = jsonObject.getInt("display");
                items = jsonObject.getJSONArray("items").toString();
            } catch (JSONException e) {
                items = null;
                e.printStackTrace();
            }
            Log.d("test1",items);
            return items;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        public void setFinding(String finding) {
            this.finding = finding;
        }

    }
}

