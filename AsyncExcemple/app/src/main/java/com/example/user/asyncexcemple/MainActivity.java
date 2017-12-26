package com.example.user.asyncexcemple;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView mytest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytest = (TextView) findViewById(R.id.mytext);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GoodTask().execute(" http://...");
            }
        });
    }

    class GoodTask extends AsyncTask<String, Integer, String> {
        // <傳入參數, 處理中更新介面參數, 處理後傳出參數>
        private static final int TIME_OUT = 1000;

        String jsonString1 = "";

        @Override
        protected String doInBackground(String... countTo) {
            // TODO Auto-generated method stub
            // 再背景中處理的耗時工作
            try{
                HttpURLConnection conn = null;
                URL url = new URL(countTo[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                // 讀取資料
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                jsonString1 = reader.readLine();
                reader.close();

                if (Thread.interrupted()) {
                    throw new InterruptedException();

                }
                if (jsonString1.equals("")) {
                    Thread.sleep(1000);
                }
            }

            catch(Exception e)
            {
                e.printStackTrace();
                return "網路中斷"+e;
            }

            return jsonString1;
        }



        public void onPostExecute(String result )
        { super.onPreExecute();
            // 背景工作處理完"後"需作的事
            mytest.setText("JSON:\r\n"+ result);

        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
            // 背景工作處理"中"更新的事

        }

    }
}
