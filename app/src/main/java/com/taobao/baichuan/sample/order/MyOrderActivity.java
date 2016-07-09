package com.taobao.baichuan.sample.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.MyCardCouponsPage;
import com.alibaba.sdk.android.trade.page.MyOrdersPage;
import com.taobao.baichuan.sample.R;
import com.taobao.baichuan.sample.StringUtils;


public class MyOrderActivity extends Activity {
	private TextView shop_param;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        
           }
    
       
       public void showMyOrder(View view) {
    	shop_param = (TextView) this.findViewById(R.id.orderInputData);
    	String param = shop_param.getText().toString();
    	int paramint;
    	if (StringUtils.isEmpty(param)) {
            paramint = 0;
        }
    	else
    		paramint= Integer.parseInt(param);
        
    	AlibabaSDK.getService(TradeService.class).show(new MyOrdersPage(paramint, false), null, this, null, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(MyOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(MyOrderActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyOrderActivity.this, "交易异常" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   
    public void showMyCardCouponse(View view) {
        AlibabaSDK.getService(TradeService.class).show(new MyCardCouponsPage(), null, this, null, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(MyOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(MyOrderActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyOrderActivity.this, "交易异常" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
