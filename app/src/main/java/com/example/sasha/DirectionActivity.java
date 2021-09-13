package com.example.sasha;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/*
public class DirectionActivity extends AppCompatActivity {
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

//        edittext_origin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recommend_origin.setVisibility(View.VISIBLE);
//                recommend_destination.setVisibility(View.INVISIBLE);
//
//            }
//        });
//
//        edittext_destination.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recommend_origin.setVisibility(View.INVISIBLE);
//                recommend_destination.setVisibility(View.VISIBLE);
//
//            }
//        });

        edittext_origin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //텍스트가 변경 될때마다 Call back
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


        String result;

        String clientId = "Ycu9uF6wApElpe1SNvhr"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "DzL7nW10TE"; //애플리케이션 클라이언트 시크릿값"
        String finding = "잠실";
        try {
            finding = URLEncoder.encode(finding,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try{
            DirectionActivity.HttpAsynTask task = new DirectionActivity.HttpAsynTask(this);
            result = (task.execute("https://openapi.naver.com/v1/search/local.json?display=5&query="+finding)
                    .get());

            Log.d("result sl ==> " , result);
        }catch(Exception e){
            result = "none";
        }

    }


    private class HttpAsynTask extends AsyncTask<String, Void, String> {

        private static final String TAG = "InsertTask";

        public Context mContext;

        public HttpAsynTask(Context mContext) {
            super();
            Log.d("sd","sdfs");

            this.mContext = mContext;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "1");

            // POST 방식으로 데이터 전달시에는 데이터가 주소에 직접 입력되지 않습니다.
            String serverURL = (String) params[0];

            Log.d(TAG, "2");
            // TODO : 아래 형식처럼 원하는 key과 value를 계속 추가시킬수있다.
            // 1. PHP 파일을 실행시킬 수 있는 주소와 전송할 데이터를 준비합니다.
//            String key = (String) params[1];
//
//            String value = (String) params[2];
            Log.d(TAG, "3");

            //String key2 = (String) params[3];
            //String value2 = (String) params[4];
            Log.d(TAG, "4");

            // HTTP 메시지 본문에 포함되어 전송되기 때문에 따로 데이터를 준비해야 합니다.
            // 전송할 데이터는 “이름=값” 형식이며 여러 개를 보내야 할 경우에는 항목 사이에 &를 추가합니다.
            // 여기에 적어준 이름을 나중에 PHP에서 사용하여 값을 얻게 됩니다.

            // TODO : 위에 추가한 형식처럼 아래 postParameters에 key과 value를 계속 추가시키면 끝이다.
            // ex : String postParameters = "name=" + name + "&country=" + country;
            //String postParameters = key + "=" + value + "&" + key2 + "=" + value2;

            //Log.d(TAG, postParameters);

            try {
                // 2. HttpURLConnection 클래스를 사용하여 POST 방식으로 데이터를 전송합니다.
                URL url = new URL(serverURL); // 주소가 저장된 변수를 이곳에 입력합니다.

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                String clientId = "Ycu9uF6wApElpe1SNvhr"; //애플리케이션 클라이언트 아이디값"
                String clientSecret = "DzL7nW10TE"; //애플리케이션 클라이언트 시크릿값"


                httpURLConnection.setReadTimeout(5000); //5초안에 응답이 오지 않으면 예외가 발생합니다.

                httpURLConnection.setConnectTimeout(5000); //5초안에 연결이 안되면 예외가 발생합니다.

                httpURLConnection.setRequestMethod("POST"); //요청 방식을 POST로 합니다.
                httpURLConnection.setRequestProperty("clientId",clientId);
                httpURLConnection.setRequestProperty("clientSecret",clientSecret);
//
//                String query = "잠실";
//                query = URLEncoder.encode(query,"utf-8");
//
//                httpURLConnection.setRequestProperty("query",query);

                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                //outputStream.write(postParameters.getBytes("UTF-8")); //전송할 데이터가 저장된 변수를 이곳에 입력합니다.

                outputStream.flush();
                outputStream.close();


                // 응답을 읽습니다.

                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if (responseStatusCode == HttpURLConnection.HTTP_OK) {

                    // 정상적인 응답 데이터
                    inputStream = httpURLConnection.getInputStream();
                } else {

                    // 에러 발생

                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while (null != (line = bufferedReader.readLine())) {
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }

}