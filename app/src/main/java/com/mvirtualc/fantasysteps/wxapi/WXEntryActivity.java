package com.mvirtualc.fantasysteps.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.mvirtualc.fantasysteps.MyApplication;
import com.mvirtualc.fantasysteps.R;
import com.mvirtualc.fantasysteps.activity.MainActivity;
import com.mvirtualc.fantasysteps.http.HttpTest;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    String APP_ID = "wx4a6dd69bc75de4fe";
    private IWXAPI api;
    private HttpTest httpTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpTest = new HttpTest();
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.registerApp(APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp){
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                System.out.println("成功");
                SendAuth.Resp response = (SendAuth.Resp) baseResp;
                System.out.println(response.state);

                if (response.state == null
                        || !response.state.equals(MainActivity.uuid))
                    return;// 判断请求是否是我的应用的请求
                try {
                    httpTest.sendCode(response.code);
                    wxSendMegs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                System.out.println("quxiao");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                System.out.println("jujue");
                break;
            default:
                break;
        }
    }
    public void wxSendMegs(){
        WXTextObject textObject = new WXTextObject();
        textObject.text = "APP分享测试";
        WXMediaMessage msg = new WXMediaMessage();
        msg.description = "APP分享测试";
        msg.mediaObject = textObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

}
