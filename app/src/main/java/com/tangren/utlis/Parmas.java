package com.tangren.utlis;


import com.yolanda.nohttp.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Parmas {

    /**
     * @author TangRen
     * @param args
     * @time 2016-7-8
     */
    private static String out_trade_no;// 商户订单号

    //被扫
    public static String getswept_parmas(String amount, String out_trade_no, String pay_code,String body) {
        String xmlString;
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("auth_code", pay_code);
        map.put("body", body);
        map.put("charset", "UTF-8");
        map.put("mch_create_ip", Utils.localIp());
        map.put("nonce_str", Utils.Random(15));
        map.put("mch_id", Config.mch_id);
        map.put("total_fee",Utils.yuanToFen(amount));
        map.put("sign_agentno", Config.sign_agentno);
        map.put("service", "unified.trade.micropay");
        map.put("out_trade_no", out_trade_no);

        String sign = SignUtils.createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        xmlString = XMlUtils.changeMapToXml(map);
        Logger.d(xmlString);
        return xmlString;
    }

    //主扫:订单
    public static String getScanPay(String amoutOfMoney, String out_trade_no) {
        String xmlString;
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("body", "未知商品");
        map.put("charset", "UTF-8");
        map.put("mch_create_ip", Utils.localIp());

        map.put("nonce_str", Utils.Random(15));
        map.put("mch_id", Config.mch_id);
        map.put("notify_url", Config.notify_url);

        map.put("total_fee", Utils.yuanToFen(amoutOfMoney));
        map.put("service", "pay.weixin.native");
        map.put("out_trade_no", out_trade_no);
        map.put("sign_agentno", Config.sign_agentno);

        String sign = SignUtils.createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        xmlString = XMlUtils.changeMapToXml(map);
        Logger.d(xmlString);
        return xmlString;
    }

    //主扫订单轮训
    public static String getQuery(String trade_no) {
        StringBuffer xml = new StringBuffer();
        String xmlString = "";
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("charset", "UTF-8");

        map.put("mch_id", Config.mch_id);
        map.put("service", "unified.trade.query");
        map.put("nonce_str", Utils.Random(15));
        map.put("out_trade_no", trade_no);
        map.put("sign_agentno", Config.sign_agentno);

        String sign = SignUtils.createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        xmlString = XMlUtils.changeMapToXml(map);
        Logger.d(xmlString);
        return xmlString;
    }

    /**
     * 退款
     *
     * @param out_trade_no
     * @return
     */
    public static String reFundArgs(String out_trade_no, String fee) {
        StringBuffer xml = new StringBuffer();
        String xmlString = "";

        xml.append("</xml>");

        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("charset", "UTF-8");
        map.put("mch_id", Config.mch_id);
        map.put("service", "unified.trade.refund");
        map.put("nonce_str", Utils.Random(15));
        map.put("out_trade_no", out_trade_no);
        map.put("out_refund_no", Utils.OrderNo());
        map.put("refund_fee", Utils.yuanToFen(fee));
        map.put("total_fee", Utils.yuanToFen(fee));
        map.put("total_fee", Utils.yuanToFen(fee));
        map.put("op_user_id", Config.mch_id);
        map.put("sign_agentno", Config.sign_agentno);
        String sign = SignUtils.createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        // 转换成xml格式
        xmlString = XMlUtils.changeMapToXml(map);

        System.out.println("sign签名:" + sign);
        System.out.println("xml:" + xmlString);

        return xmlString;
    }

    public static String ali() {
        String xmlString;
        out_trade_no = Utils.OrderNo();
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("service", "pay.alipay.native");
        map.put("mch_id", Config.mch_id);
        map.put("out_trade_no", out_trade_no);
        map.put("body", "智能POS");
        map.put("total_fee", "1");
        map.put("mch_create_ip", Utils.localIp());
        map.put("notify_url", "http://www.baidu.com");
        map.put("nonce_str", Utils.Random(15));

        String sign = createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        xmlString = XMlUtils.changeMapToXml(map);
        Logger.d(xmlString);
        return xmlString;
    }

    public static String queryAli() {
        String xmlString;
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("service", "unified.trade.query");
        map.put("mch_id", Config.mch_id);
        map.put("out_trade_no", out_trade_no);
        map.put("nonce_str", Utils.Random(15));

        String sign = createSign("UTF-8", map);
        map.put("sign", sign);
        xmlString = XMlUtils.changeMapToXml(map);

        Logger.d(xmlString);
        return xmlString;
    }

    public static String tencent() {
        String xmlString;
        out_trade_no = Utils.OrderNo();
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("service", "pay.qq.jspay");
        map.put("mch_id", "7551000001");
        map.put("out_trade_no", out_trade_no);
        map.put("body", "智能POS");
        map.put("total_fee", "1");
        map.put("mch_create_ip", Utils.localIp());
        map.put("notify_url", "http://www.baidu.com");
        map.put("nonce_str", Utils.Random(15));

        String sign = createSign("UTF-8", map);

        map.put("sign", sign);
        Logger.d("sign签名：" + sign);

        xmlString = XMlUtils.changeMapToXml(map);
        Logger.d(xmlString);
        return xmlString;
    }

    public static String queryTen() {
        String xmlString;
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("service", "unified.trade.query");
        map.put("mch_id", "7551000001");
        map.put("out_trade_no", out_trade_no);
        map.put("nonce_str", Utils.Random(15));

        String sign = createSign("UTF-8", map);
        map.put("sign", sign);
        xmlString = XMlUtils.changeMapToXml(map);

        Logger.d(xmlString);
        return xmlString;
    }

    public static String createSign(String characterEncoding,
                                    SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Config.key);
        String sign = MD5.MD5Encode(sb.toString(), characterEncoding)
                .toUpperCase();
        System.out.println("签名：" + sign);
        return sign;
    }

    public static String downloadBill() {
        String xmlString = null;
        SortedMap<Object, Object> map = new TreeMap<Object, Object>();
        map.put("service", "pay.bill.agent");
        map.put("bill_date", "20160820");
        map.put("bill_type", "ALL");
        map.put("mch_id", Config.sign_agentno);
        map.put("nonce_str", Utils.Random(15));

        String sing = createSign("UTF-8", map);
        map.put("sign", sing);
        xmlString = XMlUtils.changeMapToXml(map);
        System.out.println(xmlString);
        return xmlString;
    }
}
