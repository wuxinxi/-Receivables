package com.tangren.presenter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tangren.dialog.PaymentView;
import com.tangren.utlis.Pay;
import com.wizarpos.barcode.scanner.IScanEvent;
import com.wizarpos.barcode.scanner.ScannerRelativeLayout;
import com.wizarpos.barcode.scanner.ScannerResult;

import activity.com.szxb.tangren.payment.R;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class PaymentFramePresenterCompl implements PaymentFramePresenter {

    private PaymentView view;

    private ScannerRelativeLayout scannerView;

    private ImageView imageView;

    public PaymentFramePresenterCompl(PaymentView view) {
        this.view = view;
    }


    @Override
    public void doMainSweep(Context context, String amoutOfMoney,String out_trade_no, ScannerRelativeLayout scannerView, LinearLayout layoutImage, ImageView showImage,
                            FrameLayout surfaceLayout, TextView textLine1, TextView textLine2, IScanEvent scanLisenter) {
        this.scannerView = scannerView;
        this.imageView = imageView;
        scannerView.stopScan();
        layoutImage.setVisibility(View.VISIBLE);
        surfaceLayout.setVisibility(View.INVISIBLE);
        textLine1.setBackgroundColor(context.getResources().getColor(
                R.color.blueColor));
        textLine2.setBackgroundColor(context.getResources().getColor(
                R.color.whiteColor));
        Pay pay = new Pay(context, "");
        pay.mainSweptPay(amoutOfMoney,out_trade_no);

//        try {
//            showImage.setImageBitmap(Utils.CreateCode("wwwwwww"));
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void doSwept(Context context, String amoutOfMoney,String out_trade_no, ScannerRelativeLayout scannerView, LinearLayout layoutImage, ImageView showImage,
                        FrameLayout surfaceLayout, TextView textLine1, TextView textLine2, IScanEvent scanLisenter) {
        this.scannerView = scannerView;
        this.imageView = imageView;
        scannerView.onResume();
        scannerView.startScan();
        layoutImage.setVisibility(View.INVISIBLE);
        surfaceLayout.setVisibility(View.VISIBLE);
        textLine1.setBackgroundColor(context.getResources().getColor(
                R.color.whiteColor));
        textLine2.setBackgroundColor(context.getResources().getColor(
                R.color.blueColor));
        scanLisenter = new ScanSuccesListener();
        scannerView.setScanSuccessListener(scanLisenter);

    }

    //监听扫描成功后的结果处理
    private class ScanSuccesListener extends IScanEvent {

        @Override
        public void scanCompleted(ScannerResult scannerResult) {
            scannerView.stopScan();
            String result = scannerResult.getResult();
            view.onResult(result);
        }
    }


}
