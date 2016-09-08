package com.tangren.utlis;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.tangren.db.OrderDBManager;
import com.tangren.font.Toast;
import com.tangren.httpclient.CallServer;
import com.tangren.httpclient.HttpListener;
import com.tangren.szxb.PayFinishActivity;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import activity.com.szxb.tangren.payment.R;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class Pay {
    private static Context context;

    private int polling_times = 0;// 轮询次数

    public int isPay = 0;// 是否已经完成支付

    private String result;

    private static String out_trade_no;

    private static String total_fee;


    private OrderDBManager orderDBManager;

    public Pay(Context context, String result) {
        this.context = context;
        this.result = result;
        orderDBManager = new OrderDBManager();
    }

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeToast(context, "支付成功!", Toast.LENGTH_LONG).show();
                    Utils.print(total_fee, total_fee, "0.00", out_trade_no);
                    Intent intent = new Intent(context, PayFinishActivity.class);
                    intent.putExtra("payType", msg.obj.toString());
                    intent.putExtra("cashier", Utils.fenToYuan(total_fee));
                    intent.putExtra("koujian", "0.00");
                    intent.putExtra("zhifu", Utils.fenToYuan(total_fee));
                    intent.putExtra("zhaoling", "0.00");
                    intent.putExtra("loushuihao", out_trade_no);
                    context.startActivity(intent);
                    break;
                default:

                    break;
            }
        }
    };

    /**
     * 主扫
     *
     * @param amoutOfMoney 金额
     * @param out_trade_no 订单号
     */
    public void mainSweptPay(String amoutOfMoney, String out_trade_no) {
        this.out_trade_no = out_trade_no;
        Request<String> request = NoHttp.createStringRequest(Config.payUrl, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.setDefineRequestBodyForXML(Parmas.getScanPay(amoutOfMoney, out_trade_no));
        CallServer.getHttpclient().add(context, context.getString(R.string.msg_pay), 1,
                request, mainSweptCallBack, false, true);
    }

    HttpListener<String> mainSweptCallBack = new HttpListener<String>() {
        @Override
        public void success(int what, Response<String> response) {
            Logger.d(response.get());
            Map<String, String> xml = XMlUtils.decodeXml(response.get());
            int status = Integer.valueOf(xml.get("status"));
            if (status == 400)
                Toast.makeToast(context, xml.get("message"), Toast.LENGTH_SHORT).show();
            else if (status == 0) {
                int result_code = Integer.valueOf(xml.get("result_code"));
                if (result_code == 0) {

                    Utils.Intent(context, "show", xml.get("code_url"));
                    Logger.d("支付地址：" + xml.get("code_url"));
                    ExecutorService executorService = Executors
                            .newSingleThreadExecutor();
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {

                            while (isPay == 0 && polling_times < 6) {

                                try {

                                    Thread.sleep(10 * 10 * 100);

                                    polling_times++;

                                    requestQuery(out_trade_no);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    executorService.shutdown();

                }
            }
        }

        @Override
        public void fail(int what, Response<String> response) {
            Logger.d("失败");
            Toast.makeToast(context, "网络请求超时或服务器异常！", Toast.LENGTH_SHORT).show();
        }
    };

    private int j = 0;

    //主扫轮训订单
    private void requestQuery(String out_trade_no) {
        Request<String> request = NoHttp.createStringRequest(Config.payUrl, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.setDefineRequestBodyForXML(Parmas.getQuery(out_trade_no));
        Response<String> response = NoHttp.startRequestSync(request);
        if (response.isSucceed()) {

            j++;
            System.out.println("j=" + j);
            Map<String, String> xml = XMlUtils.decodeXml(response.get());
            System.out.println(xml.toString());
            int status = Integer.valueOf(xml.get("status"));
            if (status == 0) {
                int result_code = Integer.valueOf(xml.get("result_code"));
                if (result_code == 0) {
                    if (xml.get("trade_state").equals("SUCCESS")) {
                        isPay = 1;

                        total_fee = xml.get("total_fee");
                        out_trade_no = xml.get("out_trade_no");
                        String transaction_id = xml.get("transaction_id");
                        String time_end = xml.get("time_end");

                        try {
                            orderDBManager.addRecord(out_trade_no, time_end, transaction_id, Utils.fenToYuan(total_fee), "微信支付");
                        } catch (Exception e) {
                            Logger.d("交易记录存储失败，失败详情：" + e.toString());
                        }

                        Message message = Message.obtain();
                        message.what = 0;
                        message.obj = "微信支付";
                        handler.sendMessage(message);

                    } else if (xml.get("trade_state").equals("USERPAYING")) {
                        isPay = 0;
                    } else if (xml.get("trade_state").equals("REVOKED")) {
                        isPay = 0;
                    }

                }
            }

            Logger.d(response.get());
        } else {

        }
    }

    /***********************************************************华丽丽的分割线：被扫*****************************************************************************/


    /**
     * 被扫
     *
     * @param amount       支付金额
     * @param out_trade_no 订单号
     * @param code         支付条码
     * @param body         商品名称
     */
    public void sweptPay(String amount, String out_trade_no, String code, String body) {
        this.out_trade_no = out_trade_no;
        Request<String> request = NoHttp.createStringRequest(Config.payUrl, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.setDefineRequestBodyForXML(Parmas.getswept_parmas(amount, out_trade_no, code, body));
        CallServer.getHttpclient().add(context, context.getString(R.string.msg_pay), 5,
                request, sweptCallBack, false, true);
    }


    HttpListener<String> sweptCallBack = new HttpListener<String>() {
        @Override
        public void success(int what, Response<String> response) {
            Map<String, String> xml = XMlUtils.decodeXml(response.get());
            System.out.println(xml.toString());
            String status = xml.get("status");

            // 当满足下面的条件时说明已经支付成功
            if (Integer.valueOf(status) == 0) {

                String resultCode = xml.get("result_code");

                if (Integer.valueOf(resultCode) == 0) {
                    total_fee = xml.get("total_fee");
                    out_trade_no = xml.get("out_trade_no");
                    String transaction_id = xml.get("transaction_id");
                    String time_end = xml.get("time_end");

                    try {
                        orderDBManager.addRecord(out_trade_no, time_end, transaction_id, Utils.fenToYuan(total_fee), "微信支付");
                    } catch (Exception e) {
                        Logger.d("交易记录存储失败，失败详情：" + e.toString());
                    }

                    Message message = Message.obtain();
                    message.what = 0;
                    message.obj = "微信支付";
                    handler.sendMessage(message);

                } else if (Integer.valueOf(resultCode) == 1) {
                    Toast.makeToast(context, "请在1分钟内输入密码,完成支付！",
                            Toast.LENGTH_LONG).show();
                    System.out.println(resultCode);
                    // 等待用户输入完密码，进行查询，每6S查询1次,6次之后还没有完成支付则以冲正处理

                    ExecutorService executorService = Executors
                            .newSingleThreadExecutor();
                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {

                            while (isPay == 0 && polling_times < 5) {

                                try {

                                    Thread.sleep(6 * 10 * 100);

                                    polling_times++;

                                    //查询订单
                                    requestQuery(out_trade_no);

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                    executorService.shutdown();

                }
            } else {
                // 支付失败则冲正
//                requestImpact();
                System.out.println(response.get());
                Toast.makeToast(context, "支付失败,正在发起冲正！", Toast.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        public void fail(int what, Response<String> response) {
            Toast.makeToast(context, "支付失败,正在发起冲正！", Toast.LENGTH_LONG)
                    .show();
        }
    };


}
