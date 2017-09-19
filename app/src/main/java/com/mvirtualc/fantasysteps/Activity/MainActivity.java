package com.mvirtualc.fantasysteps.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mvirtualc.fantasysteps.MyApplication;
import com.mvirtualc.fantasysteps.R;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.UUID;

public class MainActivity extends Activity {
    String APP_ID = "wx4a6dd69bc75de4fe";
    private IWXAPI api;
    private Button btn_WxLogin;
    public static String uuid = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_WxLogin = (Button)findViewById(R.id.btn_wxlogin);
        api = WXAPIFactory.createWXAPI(this,APP_ID);
        api.registerApp(APP_ID);
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
    }
}
