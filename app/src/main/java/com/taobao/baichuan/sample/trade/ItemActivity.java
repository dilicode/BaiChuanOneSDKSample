package com.taobao.baichuan.sample.trade;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.ResultCode;
import com.alibaba.sdk.android.trade.PromotionService;
import com.alibaba.sdk.android.trade.TradeConstants;
import com.alibaba.sdk.android.trade.TradeService;
import com.alibaba.sdk.android.trade.callback.TradeProcessCallback;
import com.alibaba.sdk.android.trade.model.TaokeParams;
import com.alibaba.sdk.android.trade.model.TradeResult;
import com.alibaba.sdk.android.trade.page.ItemDetailPage;
import com.alibaba.sdk.android.trade.page.PromotionsPage;
import com.alibaba.sdk.android.trade.page.ShopPage;
import com.alibaba.sdk.android.webview.UiSettings;
import com.taobao.baichuan.sample.EnvData;
import com.taobao.baichuan.sample.R;
import com.taobao.baichuan.sample.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ItemActivity extends Activity {

    private static final String[] itemIds = { "521480934728", "37196464781", "2100502166202", "2100502146518" };
    private int index;
    private TextView textView;
    private AutoCompleteTextView actv;
    private AutoCompleteTextView itemTypeTxt;

    private EditText urlEditText;

    private Map<String, String> exParams = new HashMap<String, String>();

    RadioGroup type;

    int col = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        index = 1;

        //urlEditText = (EditText)findViewById(R.id.eidtTextUrl);

        textView = (TextView) this.findViewById(R.id.itemInputData);

        actv = (AutoCompleteTextView) this.findViewById(R.id.itemInputData2);
        itemTypeTxt = (AutoCompleteTextView) this.findViewById(R.id.itemTypeTxt);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_list, itemIds);
        // 启用监听器
        actv.setAdapter(adapter);
        actv.setText(itemIds[0]);
        type = (RadioGroup) findViewById(R.id.type);

        type.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override

            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.tao) {

                    col = 0;

                } else if (checkedId == R.id.tian)

                    col = 2;

                else

                    col = 4;

            }

        });
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
    }

    public void showItemDetail(View view) {

        String[] str = EnvData.ItemID[index].split(",");
        String inputData = textView.getText().toString();
        String[] inputDatas = new String[2];
        if (StringUtils.isEmpty(inputData)) {
            inputDatas = EnvData.ItemID[index].split(",");
            inputDatas[0] = str[col];
            inputDatas[1] = str[col + 1];
        } else {
        	inputDatas = inputData.split(",");
			Pattern p = Pattern.compile("[0-9]*");
			if (inputDatas .length<1||!p.matcher(inputDatas[0]).matches()) {
				Toast.makeText(ItemActivity.this, "input format err", Toast.LENGTH_LONG).show();
				return;
			}
        }
        UiSettings taeWebViewUiSettings = new UiSettings();
        exParams.put(TradeConstants.ISV_CODE, "showItemDetailByOpenItemId2");
        AlibabaSDK.getService(TradeService.class).show(new ItemDetailPage("527721327887", exParams), null, this, taeWebViewUiSettings, new TradeProcessCallback()
        {

            @Override
            public void onPaySuccess(TradeResult tradeResult)
            {
                Toast.makeText(ItemActivity.this, "支付成功" + tradeResult.paySuccessOrders + " fail:" + tradeResult.payFailedOrders, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg)
            {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code)
                {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openTaokeDetail(View view) {
        String[] str = EnvData.ItemID[index].split(",");
        String inputData = textView.getText().toString();
        TaokeParams taokeParams = new TaokeParams();

        String[] inputDatas = new String[3];
        if (StringUtils.isEmpty(inputData)) {
            inputDatas[0] = str[col];
            inputDatas[1] = str[col + 1];
            inputDatas[2] = EnvData.Pid[index];
            taokeParams.pid = inputDatas[2];
        }

        else {
        	inputDatas = inputData.split(",");
			Pattern p = Pattern.compile("[0-9]*");
			if (inputDatas .length<2||!p.matcher(inputDatas[0]).matches()) {
				Toast.makeText(ItemActivity.this, "input format err", Toast.LENGTH_LONG).show();
				return;
			}
            taokeParams.pid = inputDatas[1];
        }


        exParams.put(TradeConstants.ISV_CODE, "showTaokeItemDetailByOpenItemId");
        AlibabaSDK.getService(TradeService.class).show(new ItemDetailPage(inputDatas[0], exParams),taokeParams,this,null, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(ItemActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showETicketDetail(View view) {
        AlibabaSDK.getService(PromotionService.class).showETicketDetail(this, 931159680463903l, new TradeProcessCallback()
        {

            @Override
            public void onPaySuccess(TradeResult tradeResult)
            {
                Toast.makeText(ItemActivity.this, "支付成功" + tradeResult.paySuccessOrders + " fail:" + tradeResult.payFailedOrders, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg)
            {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code)
                {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showPromotions(View view) {
        // AlibabaSDK.getService(ItemService.class).showPage(this, null, null,
        // "http://ff.win.daily.taobao.net/?des=promotions&cc=tae&itid=2000039659780");
        AlibabaSDK.getService(TradeService.class).show(new PromotionsPage("shop", "c测试账号0515"), null, this, null, new TradeProcessCallback()
        {

            @Override
            public void onPaySuccess(TradeResult tradeResult)
            {
                Toast.makeText(ItemActivity.this, "支付成功" + tradeResult.paySuccessOrders + " fail:" + tradeResult.payFailedOrders, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg)
            {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code)
                {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showItemDetailV2(View view) {
        String inputData = actv.getText().toString();

		Pattern p = Pattern.compile("[0-9]*");
		if (!p.matcher(inputData).matches()||StringUtils.isEmpty(inputData)) {
			Toast.makeText(ItemActivity.this, "input format err", Toast.LENGTH_LONG).show();
			return;
		}
        exParams.put(TradeConstants.ISV_CODE, "showItemDetailByItemId");
        AlibabaSDK.getService(TradeService.class).show(new ItemDetailPage(inputData, exParams),null,this, null,new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(ItemActivity.this, "支付成功" + tradeResult.paySuccessOrders + " fail:" + tradeResult.payFailedOrders, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        } );
        // }, null, 37196464781l, 1, null);
    }

    public void showShopPageByNative(View view) {

        TradeService tradeService = AlibabaSDK.getService(TradeService.class);
	    Map<String, String> exParams = new HashMap<String, String>();
	    exParams.put(TradeConstants.SHOP_DETAIL_VIEW_TYPE, TradeConstants.SHOP_NATIVE_VIEW);
        ShopPage shopPage = new ShopPage("73319344", exParams);
        TaokeParams taokeParams = new TaokeParams(); // 若非淘客taokeParams设置为null即可
        taokeParams.pid = "mm_35447008_0_0";
        tradeService.show(shopPage, taokeParams, (Activity) ItemActivity.this,
                null, new TradeProcessCallback() {

                    @Override
                    public void onFailure(int code, String msg) {
                            Toast.makeText(ItemActivity.this, "失败 " + code + msg,
                                    Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaySuccess(TradeResult tradeResult) {
                        Toast.makeText(ItemActivity.this, "成功", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

    }


	public void showShopPageByH5(View view){
		Map<String, String> exParams = new HashMap<String, String>();
		exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.SHOP_H5_VIEW);
		AlibabaSDK.getService(TradeService.class).show(new ShopPage("73319344",exParams),null,ItemActivity.this,null, new TradeProcessCallback() {

			@Override
			public void onPaySuccess(TradeResult tradeResult) {
				Toast.makeText(ItemActivity.this, "支付成功" + tradeResult.paySuccessOrders + "   " + tradeResult.payFailedOrders, Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onFailure(int code, String msg) {
				if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
					Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ItemActivity.this, "交易异常 code: " + code + " message" + msg, Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
    public void onItemPageTypeRadioButtonClicked(View view) {
        switch (view.getId()) {
        case R.id.defaultH5:
            exParams.remove(TradeConstants.ITEM_DETAIL_VIEW_TYPE);
            break;
        case R.id.taobaoH5:
            exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.TAOBAO_H5_VIEW);
            break;
        case R.id.shareH5:
            exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.BAICHUAN_H5_VIEW);
            break;
        case R.id.taobaoNative:
            exParams.put(TradeConstants.ITEM_DETAIL_VIEW_TYPE, TradeConstants.TAOBAO_NATIVE_VIEW);
            break;
        }
    }

    public void showTaokeItemDetailV2(View view) {
        String inputData = actv.getText().toString();
    	Pattern p = Pattern.compile("[0-9]*");
		if (!p.matcher(inputData).matches()||StringUtils.isEmpty(inputData)) {
			Toast.makeText(ItemActivity.this, "input format err", Toast.LENGTH_LONG).show();
			return;
		}
        TaokeParams taokeParams = new TaokeParams();
        taokeParams.pid = "mm_26632322_6858406_23810104";
        exParams.put(TradeConstants.ISV_CODE, "showTaokeItemDetailByItemId");
        AlibabaSDK.getService(TradeService.class).show(new ItemDetailPage(inputData,  null), taokeParams, this, null, new TradeProcessCallback() {

            @Override
            public void onPaySuccess(TradeResult tradeResult) {
                Toast.makeText(ItemActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    public void showByUrl(View v)
//    {
//        String url = StringUtils.isEmpty(urlEditText.getText().toString()) ? "http://h5.m.taobao.com/app/imagesearch/www/external/index.html?tfskey=TB2XuwBjVXXXXbrXXXXXXXXXXXX_!!0-imgsearch.jpg&bizcode=imgsearch&ttid=2014_0_23082328%40baichuan_android_2.2.0-SNAPSHOT "
//                :urlEditText.getText().toString();
//        Page page = new Page(url);
//        AlibabaSDK.getService(TradeService.class).show(page, null, this, null, new TradeProcessCallback()
//        {
//            @Override
//            public void onPaySuccess(TradeResult tradeResult)
//            {
//                Toast.makeText(ItemActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(int code, String msg)
//            {
//                if (code == ResultCode.QUERY_ORDER_RESULT_EXCEPTION.code) {
//                    Toast.makeText(ItemActivity.this, "确认交易订单失败", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(ItemActivity.this, "交易取消" + code, Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}