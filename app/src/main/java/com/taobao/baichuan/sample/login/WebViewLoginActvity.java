package com.taobao.baichuan.sample.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ConfigManager;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.callback.CallbackContext;
import com.alibaba.sdk.android.login.WebViewService;
import com.alibaba.sdk.android.util.ResourceUtils;


public class WebViewLoginActvity extends Activity {

    //" tae_sdk_" + ConfigManager.SDK_INTERNAL_VERSION +
    private static final String UA_APPEND =  " AliApp(BC/" + ConfigManager.TAE_SDK_VERSION.toString() + ")";

    private WebView webView;

    private WebViewService webViewService;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(ResourceUtils.getRLayout(this, "com_taobao_tae_sdk_web_view_activity"), null);
        webView = new SimpleWebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webViewService = AlibabaSDK.getService(WebViewService.class);
        webViewService.bindWebView(webView,null);
//        webView.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//
//            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//                return;
//            }
//        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.addView(webView, params);
        setContentView(linearLayout);
        ImageView backButton = (ImageView) this.findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_back_button"));
        TextView titleText = (TextView) this.findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_title"));
        titleText.setText("myView");
        backButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!webView.canGoBack()) {
                    Intent intent = new Intent();
                    setResult(ResultCode.USER_CANCEL.code, intent);
                    WebViewLoginActvity.this.finish();
                } else {
                    webView.goBack();
                }
            }
        });

//        StringBuilder sb = new StringBuilder();
//        String ua = webView.getSettings().getUserAgentString();
//        if (ua != null) {
//            sb.append(ua);
//        }
//        sb.append(UA_APPEND);
//
//        CookieSyncManager.createInstance(this);
//        Log.d("demo", "CookieManager.getInstance().acceptCookie() = " + CookieManager.getInstance().acceptCookie());
//        Log.d("demo", "CookieManager.getInstance().allowFileSchemeCookies() = " + CookieManager.getInstance().allowFileSchemeCookies());
//        CookieManager.getInstance().setAcceptCookie(true);
//        webView.getSettings().setUserAgentString(sb.toString());
//        CookieManager.getInstance().removeAllCookie();
//        CookieSyncManager.getInstance().sync();
//        String url = "https://h5.m.taobao.com/awp/core/detail.htm?id=42966483399";
        String url = "https://h5.m.taobao.com";
        //CookieManagerWrapper.INSTANCE.refreshCookie(url);
//        String url = "https://h5.m.taobao.com/mlapp/mytaobao.html";
        webView.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 返回成功，淘宝将返回正确的信息，信息保存在intent中的result项中.

            Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_CANCELED) {
            // 用户主动取消操作.
        } else if (resultCode == -2) {
            // error,淘宝将返回错误码，同样解析intent中的result项，形式如下：
            Toast.makeText(this, data.getStringExtra("result"), Toast.LENGTH_SHORT).show();
        }

        CallbackContext.onActivityResult(requestCode, resultCode, data,webView);
//        webView.reload();
    }

    private static class SimpleWebView extends WebView {
        private String startUrl;
        private static final String UA_ALIAPP_APPEND = " AliApp(BC/" + ConfigManager.TAE_SDK_VERSION.toString() + ")";

        private static final String UA_TAESDK_APPEND = " tae_sdk_" + ConfigManager.SDK_INTERNAL_VERSION;

        public SimpleWebView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            CookieManager.getInstance().setAcceptCookie(true);
        }

        public SimpleWebView(Context context, AttributeSet attrs) {
            super(context, attrs);
            CookieManager.getInstance().setAcceptCookie(true);
        }

        public SimpleWebView(Context context) {
            super(context);
            CookieManager.getInstance().setAcceptCookie(true);
        }

//        @Override
//        public void loadUrl(String url) {
//            if (url != null) {
//                this.startUrl = normalizeURL(url);
//                if (this.startUrl != null) {
//                    super.loadUrl(this.startUrl);
//                }
//            }
//        }
//
//        private HashMap<String, String> contextParameters = new HashMap<String, String>();
//        protected String normalizeURL(String url) {
//            if (url == null) {
//                return null;
//            }
//            String normalizedUrl = url;
//            try {
//                UIBusRequest request = new UIBusRequest();
//                request.scenario = UIBusConstants.WEBVIEW_LOAD_URL;
//                request.url = url;
//                request.extraParams = new HashMap<String, Object>();
//                request.extraParams.put(UIBusConstants.CONTEXT_PARAMS, this.contextParameters);
//                normalizedUrl = UIBus.getDefault().doFilters(request);
//            } catch (Exception e) {
//                AliSDKLogger.e("ui", "fail to execute do filters for url " + url + ", the error message is " + e.getMessage(), e);
//            }
//            refreshCookies(normalizedUrl);
//            return normalizedUrl;
//        }
//
//        protected void refreshCookies(String url) {
//            if (WebViewUtils.isLoginDowngraded()) {
//                return;
//            }
//            try {
//                CookieManagerWrapper.INSTANCE.refreshCookie(url);
//            } catch (Exception e) {
//                AliSDKLogger.e("ui", "fail to refresh cookie", e);
//            }
//        }
    }



    @Override
    protected void onDestroy() {
        webViewService.releaseWebView(webView);
        super.onDestroy();
    }
}
