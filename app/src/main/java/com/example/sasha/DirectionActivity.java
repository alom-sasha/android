package com.example.sasha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.naver.maps.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

class ApiSearchLocal {
    private static String result;


    public static String getResult() {
        return result;
    }

    public static void setResult(String finding) {
        result = "";

        String clientId = "Ycu9uF6wApElpe1SNvhr"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "DzL7nW10TE"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(finding, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/local?display=5&query=" + text;    // json 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        result = responseBody;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

=======
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
*/
public class DirectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);
    }
}



        //String finding = "스시하쿠비";
        //ApiSearchLocal apiSearchLocal = new ApiSearchLocal();
        //apiSearchLocal.setResult(finding);
        //String res = apiSearchLocal.getResult();
        //Log.d("ds -> ",res);

        RelativeLayout recommend_origin = (RelativeLayout)findViewById(R.id.recommend_origin);
        RelativeLayout recommend_destination = (RelativeLayout)findViewById(R.id.recommend_destination);
        recommend_origin.setVisibility(View.INVISIBLE);
        recommend_destination.setVisibility(View.INVISIBLE);
        EditText edittext_origin = (EditText)findViewById(R.id.edittext_origin);
        EditText edittext_destination = (EditText)findViewById(R.id.edittext_destination);

        edittext_origin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommend_origin.setVisibility(View.VISIBLE);
                recommend_destination.setVisibility(View.INVISIBLE);

            }
        });

        edittext_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommend_origin.setVisibility(View.INVISIBLE);
                recommend_destination.setVisibility(View.VISIBLE);

            }
        });




        String result;

        String clientId = "Ycu9uF6wApElpe1SNvhr"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "DzL7nW10TE"; //애플리케이션 클라이언트 시크릿값"
        String finding = "잠실";


        try{
            DirectionActivity.HttpAsynTask task = new DirectionActivity.HttpAsynTask(this);
            result = (task.execute("https://openapi.naver.com/v1/search/local?display=5&clientId=Ycu9uF6wApElpe1SNvhr&clientSecret=DzL7nW10TE&query="+finding,"clientId",clientId,"clientSecret",clientSecret)
                    .get());

            Log.d("result sl ==> " , result);
        }catch(Exception e){
            result = "none";
        }

    }
}