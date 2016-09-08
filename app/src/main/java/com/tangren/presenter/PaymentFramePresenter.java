package com.tangren.presenter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wizarpos.barcode.scanner.IScanEvent;
import com.wizarpos.barcode.scanner.ScannerRelativeLayout;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public interface PaymentFramePresenter {
    /**
     * @param context       上下文
     * @param amoutOfMoney  支付金额
     * @param out_trade_no  订单号
     * @param scannerView   摄像头
     * @param layoutImage
     * @param showImage     扫码区域
     * @param surfaceLayout
     * @param textLine1
     * @param textLine2
     * @param scanLisenter  回调事件
     */
    public void doMainSweep(Context context, String amoutOfMoney, String out_trade_no, ScannerRelativeLayout scannerView, LinearLayout layoutImage, ImageView showImage, FrameLayout surfaceLayout,
                            TextView textLine1, TextView textLine2, IScanEvent scanLisenter);

    public void doSwept(Context context, String amoutOfMoney, String out_trade_no, ScannerRelativeLayout scannerView, LinearLayout layoutImage, ImageView showImage, FrameLayout surfaceLayout,
                        TextView textLine1, TextView textLine2, IScanEvent scanLisenter);
}
