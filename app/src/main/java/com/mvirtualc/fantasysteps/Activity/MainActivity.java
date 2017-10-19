package com.mvirtualc.fantasysteps.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mvirtualc.fantasysteps.R;
import com.mvirtualc.fantasysteps.http.HttpTest;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends Activity {
    String APP_ID = "wx4a6dd69bc75de4fe";
    private IWXAPI api;
    private Button btn_WxLogin;
    private Button btn_WxPay;
    private Button btn_MapTest;
    private Button btn_MapDirection;
    private static final int BAIDU_READ_PHONE_STATE =100;
    public static String uuid = null;
    private HttpTest httpTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpTest = new HttpTest();
        btn_WxLogin = (Button)findViewById(R.id.btn_wxlogin);
        btn_WxPay = (Button)findViewById(R.id.btn_wxpay);
        btn_MapTest = (Button)findViewById(R.id.btn_mapTest);
        btn_MapDirection = (Button)findViewById(R.id.btn_mapDirection);
        api = WXAPIFactory.createWXAPI(this,APP_ID);
        api.registerApp(APP_ID);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);//自定义的code
        }


        btn_WxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!api.isWXAppInstalled()){
                    Toast.makeText(MainActivity.this,"未安装微信客户端，请先下载",Toast.LENGTH_SHORT).show();
                    return;
                }
                uuid = UUID.randomUUID().toString();
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = uuid;
                System.out.println("state: " + uuid);
                boolean b = api.sendReq(req);
                System.out.println(b);
            }
        });

        btn_WxPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (api.isWXAppInstalled()){
                    Toast.makeText(MainActivity.this,"未安装微信客户端，请先下载",Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String result = httpTest.TestWXPay();
                    JSONObject wxPay  = new JSONObject(result);
                    PayReq payRequest = new PayReq();
                    payRequest.appId = APP_ID;
                    payRequest.partnerId = wxPay.getString("partnerid");
                    payRequest.prepayId = wxPay.getString("prepayid");
                    payRequest.packageValue = "Sign=WXPay";
                    payRequest.nonceStr = wxPay.getString("noncestr");
                    payRequest.timeStamp = wxPay.getString("timestamp");
                    payRequest.sign = wxPay.getString("sign");
                    api.sendReq(payRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_MapTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MapTest.class);
                startActivity(intent);
            }
        });

        btn_MapDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,MapDirection.class);
                startActivity(intent);
            }
        });

    }
}
