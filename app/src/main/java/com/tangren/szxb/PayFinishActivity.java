package com.tangren.szxb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangren.utlis.MyUtil;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/1 0001.
 */
public class PayFinishActivity extends AppCompatActivity {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.mtoolbar)
    Toolbar mtoolbar;
    @InjectView(R.id.cashier)
    TextView cashier;
    @InjectView(R.id.koujian)
    TextView koujian;
    @InjectView(R.id.zhifu)
    EditText zhifu;
    @InjectView(R.id.zhaoling)
    TextView zhaoling;
    @InjectView(R.id.out_activity)
    Button outActivity;
    @InjectView(R.id.print_invoice)
    Button printInvoice;

    private String loushuihao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("payType"));
        cashier.setText(intent.getStringExtra("cashier"));
        koujian.setText(intent.getStringExtra("koujian"));
        zhifu.setText(intent.getStringExtra("zhifu"));
        zhaoling.setText(intent.getStringExtra("zhaoling"));
        loushuihao = intent.getStringExtra("loushuihao");
    }

    @OnClick({R.id.out_activity, R.id.print_invoice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_activity:
                finish();
                break;
            case R.id.print_invoice:
                print(cashier.getText().toString(), zhifu.getText().toString(), zhaoling.getText().toString());
                break;
        }
    }

    private void print(final String amountMoney, final String shishou, final String zhaoling) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MyUtil().print("深圳市小兵智能科技有限公司", amountMoney, "0.00", shishou, zhaoling, "P" + loushuihao);
            }
        }).start();
    }


}
