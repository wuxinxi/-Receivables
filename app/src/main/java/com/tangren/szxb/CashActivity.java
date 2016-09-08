package com.tangren.szxb;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
 * Created by Administrator on 2016/8/31 0031.
 */
public class CashActivity extends AppCompatActivity implements TextWatcher {
    @InjectView(R.id.cashier)
    TextView cashier;
    @InjectView(R.id.koujian)
    TextView koujian;
    @InjectView(R.id.yingshou)
    EditText yingshou;
    @InjectView(R.id.shishou)
    EditText shishou;
    @InjectView(R.id.zhaoling)
    TextView zhaoling;
    @InjectView(R.id.out_activity)
    Button outActivity;
    @InjectView(R.id.pay)
    Button pay;
    @InjectView(R.id.num_7)
    Button num7;
    @InjectView(R.id.num_8)
    Button num8;
    @InjectView(R.id.num_9)
    Button num9;
    @InjectView(R.id.num_4)
    Button num4;
    @InjectView(R.id.num_5)
    Button num5;
    @InjectView(R.id.num_6)
    Button num6;
    @InjectView(R.id.num_1)
    Button num1;
    @InjectView(R.id.num_2)
    Button num2;
    @InjectView(R.id.num_3)
    Button num3;
    @InjectView(R.id.num_0)
    Button num0;
    @InjectView(R.id.num_del)
    Button numDel;

    private int count = 0;//标识输入字符数，用来判断长度是否超过0.00的长度
    private int index = 0; // 输入内容前的下标

    private String amountMoney;

    private LocalBroadcastManager manager;

    private BroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("现金支付");
        Intent intent = getIntent();
        amountMoney = intent.getStringExtra("amoutOfMoney");
        if (amountMoney.equals("") && amountMoney == null)
            return;
        else {
            cashier.setText(amountMoney);
            yingshou.setText(amountMoney);
        }
        shishou.addTextChangedListener(this);
    }


    @OnClick({R.id.out_activity, R.id.pay, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_4, R.id.num_5,
            R.id.num_6, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_0, R.id.num_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_activity:
                finish();
                break;
            case R.id.pay:
                Intent intent = new Intent(CashActivity.this, PayFinishActivity.class);
                intent.putExtra("payType","现金");
                intent.putExtra("cashier", amountMoney);
                intent.putExtra("koujian", "0.00");
                intent.putExtra("zhifu", shishou.getText().toString());
                intent.putExtra("zhaoling", zhaoling.getText().toString());
                startActivity(intent);
                print(amountMoney, shishou.getText().toString(), zhaoling.getText().toString());
                finish();
                break;
            case R.id.num_7:
                input("7");
                break;
            case R.id.num_8:
                input("8");
                break;
            case R.id.num_9:
                input("9");
                break;
            case R.id.num_4:
                input("4");
                break;
            case R.id.num_5:
                input("5");
                break;
            case R.id.num_6:
                input("6");
                break;
            case R.id.num_1:
                input("1");
                break;
            case R.id.num_2:
                input("2");
                break;
            case R.id.num_3:
                input("3");
                break;
            case R.id.num_0:
                input("0");
                break;
            case R.id.num_del:
                delNum();
                break;
        }
    }

    private void print(final String amountMoney, final String shishou, final String zhaoling) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new MyUtil().print("深圳市小兵智能科技有限公司", amountMoney, "0.00", shishou, zhaoling, "P2012345678");
            }
        }).start();
    }

    private void input(String num) {
        String result = shishou.getText().toString().trim() + num;
        result = result.replace(".", "");
        if (count < 3 && index == result.length() && result.startsWith("0")) {
            result = result.substring(1, result.length());
        }
        String a = String.valueOf(Long.valueOf(result.substring(0, result.length() - 2)));//整数部分
        StringBuilder sb = new StringBuilder(a);
        sb.append(".").append(result.subSequence(result.length() - 2, result.length()));//小数部分
        String tem = sb.toString();
        shishou.setText(tem);
        shishou.setSelection(tem.length());
        count++;
    }

    private void delNum() {
        if (count != 0) {
            String result = shishou.getText().toString().trim();

            result = result.replace(".", "");
            if (result.length() == 3) {
                result = "0".concat(result);
            }
            StringBuilder builder = new StringBuilder(result.substring(0, result.length() - 1));
            builder.insert(builder.toString().length() - 2, ".");

            shishou.setText(builder.toString());
            shishou.setSelection(builder.length());
            count--;
        } else {
            shishou.setText("0.00");
            shishou.setSelection(4);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //实时计算出找零
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        final String result = s.toString();
        final float f = Float.valueOf(result);
        final float receivable = Float.valueOf(amountMoney);//应收
        final float v = f - receivable;
        zhaoling.setText(String.valueOf((float) (Math.round(v * 100)) / 100));

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
