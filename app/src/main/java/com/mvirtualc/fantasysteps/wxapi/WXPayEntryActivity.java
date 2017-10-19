package com.mvirtualc.fantasysteps.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.widget.Toast;

import com.mvirtualc.fantasysteps.activity.MainActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    String APP_ID = "wx4a6dd69bc75de4fe";
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        if(baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (baseResp.errCode == 0)
                Toast.makeText(WXPayEntryActivity.this,"支付成功",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(WXPayEntryActivity.this,"支付失败",Toast.LENGTH_LONG).show();
            }
        }
        Intent intent = new Intent();
        intent.setClass(WXPayEntryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
