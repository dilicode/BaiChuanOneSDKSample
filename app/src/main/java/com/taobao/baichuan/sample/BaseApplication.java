package com.taobao.baichuan.sample;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ConfigManager;
import com.alibaba.sdk.android.Environment;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.login.LoginConfigs;
import com.alibaba.sdk.android.login.LoginService;
import com.alibaba.sdk.android.session.SessionListener;
import com.alibaba.sdk.android.session.model.Session;
import com.alibaba.sdk.android.trade.TradeConfigs;

/**
 * Created by LiDili on 16/6/28.
 */
public class BaseApplication extends Application {

    private static final String KEY_ENV_INDEX = "envIndex";

    private static final String SHARED_PRE = "_tae_sdk_demo";

    @Override
    public void onCreate() {
        super.onCreate();

        AlibabaSDK.asyncInit(this, new InitResultCallback() {

            @Override
            public void onSuccess() {
                Toast.makeText(BaseApplication.this, "初始化成功 ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String message) {
                Toast.makeText(BaseApplication.this, "初始化异常" + message, Toast.LENGTH_SHORT).show();
            }
        });

        AlibabaSDK.setProperty("ui", "backPressedAsHistoryBack", "true");

    }
}