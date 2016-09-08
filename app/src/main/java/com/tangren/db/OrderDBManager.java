package com.tangren.db;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.tangren.model.bean.RecordBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class OrderDBManager {

    /**
     * 查询交易记录
     *
     * @return
     */
    public static List<RecordBean> query() {
        return new Select().from(RecordBean.class)
                .orderBy("_id desc")
                .execute();
    }

    /**
     * 保存交易信息
     *
     * @param dingdanhao    订单号
     * @param timeend       交易完成时间
     * @param wfdiangdanhao 威富通订单号
     * @param money         支付金额
     * @param paytype       支付类型
     */
    public static void addRecord(String dingdanhao, String timeend, String wfdiangdanhao, String money, String paytype) {
        RecordBean bean = new RecordBean();
        bean.outtradeno = dingdanhao;
        bean.timeend = timeend;
        bean.transactionid = wfdiangdanhao;
        bean.paytype = paytype;
        bean.money=money;
        bean.save();
    }

    /**
     * 当退款成功，则删除交易记录
     *
     * @param out_trade_no
     */
    public static void delete(String out_trade_no) {
        new Delete().from(RecordBean.class).where("outtradeno=?", out_trade_no).execute();
    }
}
