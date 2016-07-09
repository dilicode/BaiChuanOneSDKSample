package com.taobao.baichuan.sample;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.alibaba.sdk.android.trade.page.MyOrdersPage;
import com.alibaba.sdk.android.webview.UiSettings;
import com.taobao.baichuan.sample.cart.CartActivity;
import com.taobao.baichuan.sample.login.MemberActivity;
import com.taobao.baichuan.sample.oauth.TaobaoActivity;
import com.taobao.baichuan.sample.order.MyOrderActivity;
import com.taobao.baichuan.sample.trade.ItemActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiDili on 16/6/28.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Map<String, String> exParams = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.trade).setOnClickListener(this);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.cart).setOnClickListener(this);
        findViewById(R.id.order).setOnClickListener(this);
        findViewById(R.id.oauth).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.trade) {
            startActivity(new Intent(this, ItemActivity.class));
            return;
        }

        if (id == R.id.login) {
            startActivity(new Intent(this, MemberActivity.class));
            return;
        }

        if (id == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
            return;
        }

        if (id == R.id.order) {
            startActivity(new Intent(this, MyOrderActivity.class));
            return;
        }

        if (id == R.id.oauth) {
            startActivity(new Intent(this, TaobaoActivity.class));
        }
    }
}