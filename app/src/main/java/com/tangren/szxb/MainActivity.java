package com.tangren.szxb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.tangren.dialog.PaymentFrameDialog;
import com.tangren.dialog.WaitDialog;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class MainActivity extends AppCompatActivity {
    @InjectView(R.id.pay)
    Button pay;
    @InjectView(R.id.quert)
    Button quert;
    @InjectView(R.id.alipay)
    Button alipay;
    @InjectView(R.id.tenpay)
    Button tenpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

    }

    @OnClick({R.id.pay, R.id.quert, R.id.alipay, R.id.tenpay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pay:
              WaitDialog dialog2=new WaitDialog(this,null);
                dialog2.show();

                break;
            case R.id.quert:
                PaymentFrameDialog dialog=new PaymentFrameDialog(this);
                dialog.show();

                break;
            case R.id.alipay:

                break;
            case R.id.tenpay:

                break;
        }
    }
}
