package com.taobao.baichuan.sample.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.CartService;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.MyCartsPage;
import com.taobao.baichuan.sample.AbstractActivity;
import com.taobao.baichuan.sample.EnvData;
import com.taobao.baichuan.sample.R;
import com.taobao.baichuan.sample.StringUtils;


import java.util.regex.Pattern;

public class CartActivity extends AbstractActivity {

    int index;
    private RadioGroup style;
    int col = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        index = getIntent().getIntExtra("Index", 1);
        style = (RadioGroup) findViewById(R.id.type);
        style.setOnCheckedChangeListener(new OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tao_bao) {
                    col = 0;
                } else if (checkedId == R.id.tian_mao)
                    col = 2;
                else
                    col = 4;
            }
        });
    }

    public void showCart(View view) {
        AlibabaSDK.getService(TradeService.class).show(new MyCartsPage(), null, this, null, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(CartActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(CartActivity.this, "打开购物车失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "取消购物车失败" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addItem2Cart(View view) {
        String inputData = "533285630372";

        AlibabaSDK.getService(CartService.class).addItem2Cart(this, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(CartActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(CartActivity.this, "添加购物车失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "取消添加购物车" + code, Toast.LENGTH_SHORT).show();
                }
            }
        }, null, inputData, null);
    }


    public void addTaoKeItem2Cart(View view) {
        String[] str = EnvData.ItemID[index].split(",");
        TextView textView = (TextView) this.findViewById(R.id.cartInputData);
        String inputData = textView.getText().toString();
        String[] inputDatas = new String[4];
        if (StringUtils.isEmpty(inputData)) {
            inputDatas[0] = str[col];
            inputDatas[1] = (String) EnvData.Pid[index];
        } else {
            inputDatas = inputData.split(",");
            Pattern p = Pattern.compile("[0-9]*");
            if (inputDatas.length < 2 || !p.matcher(inputDatas[0]).matches() || !p.matcher(inputDatas[1]).matches()) {
                Toast.makeText(CartActivity.this, "input format err", Toast.LENGTH_LONG).show();
                return;
            }
        }
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = inputDatas[1];
        if (inputDatas.length > 2) {
            taokeParams.unionId = inputDatas[2];
        }
        if (inputDatas.length > 3) {
            taokeParams.subPid = inputDatas[3];
        }
        AlibabaSDK.getService(CartService.class).addTaoKeItem2Cart(this, new TradeProcessCallback() {
            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(CartActivity.this, "添加淘客商品到购物车成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(CartActivity.this, "添加淘客商品到购物车失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CartActivity.this, "添加淘客商品到购物车取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        }, null, inputDatas[0], null, taokeParams);
    }




}
