package com.example.djsu;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


class mainkcalBackgroundTask extends AsyncTask<Void, Void, String> {
    String target;
    Context context;

    protected void onPreExecute() {
        //List.php은 파싱으로 가져올 웹페이지
        target = "http://enejd0613.dothome.co.kr/foodcalendarlist.php";
    }
    public mainkcalBackgroundTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(Void... voids) {

        try {
            URL url = new URL(target);//URL 객체 생성
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp + "\n");//stringBuilder에 넣어줌
            }

            //사용했던 것도 다 닫아줌
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();//trim은 앞뒤의 공백을 제거함

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, main_user.class);
        intent.putExtra("UserFood", result);
        context.startActivity(intent);

    }
}
