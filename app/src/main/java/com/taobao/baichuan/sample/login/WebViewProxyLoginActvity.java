package com.taobao.baichuan.sample.login;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ConfigManager;
import com.alibaba.sdk.android.callback.CallbackContext;
import com.alibaba.sdk.android.login.WebViewProxyService;
import com.alibaba.sdk.android.util.CommonUtils;
import com.alibaba.sdk.android.util.ResourceUtils;
import com.alibaba.sdk.android.webview.BaseWebViewClient;
import com.alibaba.sdk.android.webview.proxy.WebViewProxy;
import com.alibaba.sdk.android.webview.utils.WebViewUtils;

import java.util.HashMap;

public class WebViewProxyLoginActvity extends Activity {

    private static final String UA_ALIAPP_APPEND = " AliApp(BC/" + ConfigManager.TAE_SDK_VERSION.toString() + ")";

    private static final String UA_TAESDK_APPEND = " tae_sdk_" + ConfigManager.SDK_INTERNAL_VERSION;

    private String appCacheDir;

    private String orinUserAgent;

    private CustomWebView customWebView;
    private Context context;
    WebViewProxyService webViewProxyService;

    private HashMap<String, String> contextParameters = new HashMap<String, String>();
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @SuppressWarnings("static-access")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(ResourceUtils.getRLayout(this, "com_taobao_tae_sdk_web_view_activity"), null);
        customWebView = new CustomWebView(this);
        initSettings(customWebView,true);
        webViewProxyService = AlibabaSDK.getService(WebViewProxyService.class);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.addView(customWebView, params);
        setContentView(linearLayout);
        ImageView backButton = (ImageView) this.findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_back_button"));
        TextView titleText = (TextView) this.findViewById(ResourceUtils.getIdentifier(this, "id", "com_taobao_tae_sdk_web_view_title_bar_title"));
        titleText.setText("myView");


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
        String url = "http://www.hao123.com";
//        String url = "https://h5.m.taobao.com/awp/core/detail.htm?id=42966483399";
        //CookieManagerWrapper.INSTANCE.refreshCookie(url);
        customWebView.loadUrl(url);
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
        CallbackContext.onActivityResult(this,requestCode, resultCode, data, customWebView);
//        CallbackContext.onActivityResult(requestCode, resultCode, data);
//        webView.reload();
    }

    private  class CustomWebView extends WebView implements WebViewProxy {
//        public WebView webView;

        public CustomWebView(Context context) {
            super(context);
//            webView = new WebView(context);
            CookieManager.getInstance().setAcceptCookie(true);
            getSettings().setJavaScriptEnabled(true);
            setWebViewClient(new BaseWebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return webViewProxyService.shouldOverrideUrlLoading(customWebView, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
            });

        }

//        @Override
//        public void loadUrl(String url) {
//            String normalizedUrl = url;
//            try {
//                UIBusRequest request = new UIBusRequest();
//                request.scenario = UIBusConstants.WEBVIEW_LOAD_URL;
//                request.url = url;
//                request.extraParams = new HashMap<String, Object>();
//                request.extraParams.put(UIBusConstants.CONTEXT_PARAMS, contextParameters);
//                normalizedUrl = UIBus.getDefault().doFilters(request);
//            } catch (Exception e) {
//                AliSDKLogger.e("ui", "fail to execute do filters for url " + url + ", the error message is " + e.getMessage(), e);
//            }
//            if (WebViewUtils.isLoginDowngraded()) {
//                return;
//            }
//            try {
//                CookieManagerWrapperWithWebViewProxy.INSTANCE.refreshCookie(this,url);
//            } catch (Exception e) {
//                AliSDKLogger.e("ui", "fail to refresh cookie", e);
//            }
//            super.loadUrl(normalizedUrl);
//        }

        @Override
        public void reload() {
            super.reload();
        }

        @Override
        public String execJS(String jsStr) {
            return null;
        }

        @Override
        public void setCookie(String key, String value) {
            CookieManager.getInstance().setCookie(key,value);
        }

        @Override
        public String getCookie(String key) {
            return CookieManager.getInstance().getCookie(key);
        }


        @Override
        public void setUserAgent(String ua) {
            getSettings().setUserAgentString(ua);
        }

        @Override
        public String getUserAgent() {
            return getSettings().getUserAgentString();
        }

        @Override
        public String getUrl() {
            return super.getUrl();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initSettings(WebView webView, boolean updateUAA) {

        WebSettings webSettings = webView.getSettings();
        try {
            webSettings.setJavaScriptEnabled(true);
        } catch (Exception e) {
            //https://code.google.com/p/android/issues/detail?id=40944
            //in some android versions, setJavaScriptEnabled may throw NPE
            //ignore
        }
        webSettings.setSavePassword(false);
        /*******************************start*******************************/
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        /************添加上面两行是为了解决网页显示的时候超出屏幕的问题************/

        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setDomStorageEnabled(true);
        appCacheDir = webView.getContext().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        if (CommonUtils.isNetworkAvailable(webView.getContext())) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setBuiltInZoomControls(false);
        if (updateUAA) {
            StringBuilder sb = new StringBuilder();
            orinUserAgent = webSettings.getUserAgentString();
            if (orinUserAgent != null) {
                sb.append(orinUserAgent);
            }
            if (!WebViewUtils.isLoginDowngraded()) {
                sb.append(UA_TAESDK_APPEND);
            }
            sb.append(UA_ALIAPP_APPEND);
            webSettings.setUserAgentString(sb.toString());
        }

//        if (Build.VERSION.SDK_INT >= 21) {
//            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
//            int mixedContentMode = LoginContext.pluginConfigurations == null ? -1 : LoginContext.pluginConfigurations.getIntValue("mixedContentMode", -1);
//            if (mixedContentMode != -1) {
//                webSettings.setMixedContentMode(mixedContentMode);
//            }
//        }
    }
}
