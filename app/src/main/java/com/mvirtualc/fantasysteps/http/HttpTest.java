package com.mvirtualc.fantasysteps.http;

import android.os.StrictMode;

import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yuritian on 2017/8/1.
 */

public class HttpTest {
    OkHttpClient okHttpClient = new OkHttpClient();
    String url = "http://192.168.1.102:8080/Test/User_wxLogin.action?code=";
    public String sendCode(String code) throws IOException{
        String result = "null";
        try {
            String LoginSevlet = url + code;
            URL url = new URL(LoginSevlet);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            urlConnection.setRequestMethod("GET");
            // 设置编码格式
            urlConnection.setRequestProperty("Charset", "UTF-8");
            // 设置容许输出
            urlConnection.setDoOutput(true);
            //把上面访问方式改为异步操作,就不会出现 android.os.NetworkOnMainThreadException异常
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if (urlConnection.getResponseCode() == 200){
                InputStream is = urlConnection.getInputStream();
                result = getStringFromInputStream(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String TestWXPay() throws IOException{
        String result = "null";
        try {
            String LoginSevlet = "http://192.168.1.104:8080/Test/User_wxPay.action";
            URL url = new URL(LoginSevlet);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            // 设置请求方式
            urlConnection.setRequestMethod("GET");
            // 设置编码格式
            urlConnection.setRequestProperty("Charset", "UTF-8");
            // 设置容许输出
            urlConnection.setDoOutput(true);
            //把上面访问方式改为异步操作,就不会出现 android.os.NetworkOnMainThreadException异常
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if (urlConnection.getResponseCode() == 200){
                InputStream is = urlConnection.getInputStream();
                result = getStringFromInputStream(is);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public static String getStringFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
        }
        String result = baos.toString();
        is.close();
        baos.close();
        return result;
    }
}
