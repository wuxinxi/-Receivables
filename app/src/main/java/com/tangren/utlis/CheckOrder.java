package com.tangren.utlis;

import android.content.Context;

import com.tangren.httpclient.CallServer;
import com.tangren.httpclient.HttpListener;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class CheckOrder {

    private static Context context;

    public CheckOrder(Context context) {
        this.context = context;
    }

    public static void checkPay(String out_trade_no) {
        Request<String> request = NoHttp.createStringRequest(Config.payUrl, RequestMethod.POST);
        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
        request.setDefineRequestBodyForXML(Parmas.getQuery(out_trade_no));
        CallServer.getHttpclient().add(context, "正在查找订单，请稍后……", 3, request, callBack, false, true);
    }

    static HttpListener<String> callBack = new HttpListener<String>() {
        @Override
        public void success(int what, Response<String> response) {
            Logger.d(response.get());
            Map<String, String> xml = XMlUtils.decodeXml(response.get());
            int status = Integer.valueOf(xml.get("status"));
            if (status == 0) {
                int result_code = Integer.valueOf(xml.get("result_code"));
                if (result_code == 0) {
                    if (xml.get("trade_state").equals("SUCCESS")) {

                    }
                }
            }
        }

        @Override
        public void fail(int what, Response<String> response) {

        }
    };
}
