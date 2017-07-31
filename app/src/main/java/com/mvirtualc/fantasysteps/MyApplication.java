package com.mvirtualc.fantasysteps;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by yuritian on 2017/7/31.
 */

public class MyApplication extends Application {
    public static IWXAPI api;
    String APP_ID = "wx4a6dd69bc75de4fe";
    @Override
    public void onCreate() {
        super.onCreate();
        registToWX();
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        api = WXAPIFactory.createWXAPI(this, APP_ID, false);
        // 将该app注册到微信
        api.registerApp(APP_ID);
    }
}
