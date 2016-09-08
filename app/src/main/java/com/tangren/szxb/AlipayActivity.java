package com.tangren.szxb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.tangren.dialog.PaymentView;
import com.tangren.font.Toast;
import com.tangren.presenter.PaymentFramePresenter;
import com.tangren.presenter.PaymentFramePresenterCompl;
import com.tangren.utlis.CheckOrder;
import com.tangren.utlis.Pay;
import com.tangren.utlis.Utils;
import com.wizarpos.barcode.scanner.IScanEvent;
import com.wizarpos.barcode.scanner.ScannerRelativeLayout;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class AlipayActivity extends AppCompatActivity implements PaymentView {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.autoScan)
    Button autoScan;
    @InjectView(R.id.passiveScan)
    Button passiveScan;
    @InjectView(R.id.textLineShow1)
    TextView textLineShow1;
    @InjectView(R.id.textLineShow2)
    TextView textLineShow2;
    @InjectView(R.id.imageShow)
    ImageView imageShow;
    @InjectView(R.id.imageLayout)
    LinearLayout imageLayout;
    @InjectView(R.id.scanner)
    ScannerRelativeLayout scanner;
    @InjectView(R.id.surfaceViewLayout)
    FrameLayout surfaceViewLayout;
    @InjectView(R.id.msg)
    TextView msg;
    @InjectView(R.id.money)
    TextView money;
    @InjectView(R.id.koujian)
    TextView koujian;
    @InjectView(R.id.yingshou)
    TextView yingshou;
    @InjectView(R.id.out_activity)
    Button outActivity;
    @InjectView(R.id.print)
    Button print;
    @InjectView(R.id.check_dingdan)
    Button checkDingdan;

    private String payType;

    private LocalBroadcastManager manager;

    private BroadcastReceiver receiver;

    private PaymentFramePresenter paymentFramePresenter;
    private IScanEvent scanLisenter;
    private String amoutOfMoney;

    private String out_trade_no;//订单号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alipay_main);
        ButterKnife.inject(this);

        initView();
        showScanQrcode();
    }

    private void initView() {
        Intent intent = getIntent();
        payType = intent.getStringExtra("payType");
        amoutOfMoney = intent.getStringExtra("amoutOfMoney");
        textView.setText(payType);
        money.setText(amoutOfMoney);
        koujian.setText("0.00");
        yingshou.setText(amoutOfMoney);

        paymentFramePresenter = new PaymentFramePresenterCompl(this);
    }

    /**
     * 注册广播，接收广播
     *
     * @author TangRen
     * @time 2016-7-2
     */
    private void showScanQrcode() {
        manager = LocalBroadcastManager
                .getInstance(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("show");
        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    imageShow.setBackgroundResource(R.mipmap.bord);
                    imageShow.setImageBitmap(Utils.CreateCode(intent
                            .getStringExtra("msg")));
                } catch (WriterException e) {
                    e.printStackTrace();
                    Toast.makeToast(context, "生成二维码失败！", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        manager.registerReceiver(receiver, filter);

    }


    @OnClick({R.id.autoScan, R.id.passiveScan, R.id.out_activity, R.id.print, R.id.check_dingdan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.autoScan:
                out_trade_no = Utils.OrderNo();
                paymentFramePresenter.doMainSweep(this, amoutOfMoney, out_trade_no, scanner, imageLayout, imageShow,
                        surfaceViewLayout, textLineShow1, textLineShow2, scanLisenter);
                break;
            case R.id.passiveScan:
                out_trade_no = Utils.OrderNo();
                paymentFramePresenter.doSwept(this, amoutOfMoney, out_trade_no, scanner, imageLayout, imageShow,
                        surfaceViewLayout, textLineShow1, textLineShow2, scanLisenter);
                break;
            case R.id.out_activity:
                finish();
                break;
            case R.id.print:
                break;
            case R.id.check_dingdan:
                if (!out_trade_no.equals("")) {
                    CheckOrder order = new CheckOrder(this);
                    order.checkPay(out_trade_no);
                } else
                    Toast.makeToast(this, "暂无订单！", Toast.LENGTH_SHORT)
                            .show();

                break;
        }
    }

    @Override
    public void onResult(String response) {
        Pay pay = new Pay(this, "");
        pay.sweptPay(amoutOfMoney, out_trade_no, response, "未知商品");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scanner != null)
            scanner.stopScan();
        if (manager != null)
            manager.unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

