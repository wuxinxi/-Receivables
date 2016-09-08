package com.tangren.szxb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangren.font.Toast;
import com.tangren.httpclient.CallServer;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class PaymentActivity extends AppCompatActivity {


    @InjectView(R.id.num_money)
    EditText numMoney;
    @InjectView(R.id.crach_pay)
    Button crachPay;
    @InjectView(R.id.ali_pay)
    Button aliPay;
    @InjectView(R.id.qq_pay)
    Button qqPay;
    @InjectView(R.id.wx_pay)
    Button wxPay;
    @InjectView(R.id.move_pay)
    Button movePay;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("收银");
        numMoney.setText("0.00");
        numMoney.setSelection(4);
        index = numMoney.getSelectionEnd();

    }


    @OnClick({R.id.crach_pay, R.id.ali_pay, R.id.qq_pay, R.id.wx_pay, R.id.move_pay,
            R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_4, R.id.num_5, R.id.num_6,
            R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_0, R.id.num_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.crach_pay:
                intent(CashActivity.class, "现金支付");
                break;
            case R.id.ali_pay:
                intent(AlipayActivity.class, "支付宝支付");
                break;
            case R.id.qq_pay:
                intent(AlipayActivity.class, "QQ支付");
                break;
            case R.id.wx_pay:
                intent(AlipayActivity.class, "微信支付");
                break;
            case R.id.move_pay:
//                intent(AlipayActivity.class, "微信支付");
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

    private void input(String num) {
        String result = numMoney.getText().toString().trim() + num;
        result = result.replace(".", "");
        if (count < 3 && index == result.length() && result.startsWith("0")) {
            result = result.substring(1, result.length());
        }
        String a = String.valueOf(Long.valueOf(result.substring(0, result.length() - 2)));//整数部分
        StringBuilder sb = new StringBuilder(a);
        sb.append(".").append(result.subSequence(result.length() - 2, result.length()));//小数部分
        String tem = sb.toString();
        if (tem.length() > 10)
            return;
        numMoney.setText(tem);
        numMoney.setSelection(tem.length());
        count++;
    }

    private void delNum() {
        if (count != 0) {
            String result = numMoney.getText().toString().trim();

            result = result.replace(".", "");
            if (result.length() == 3) {
                result = "0".concat(result);
            }
            StringBuilder builder = new StringBuilder(result.substring(0, result.length() - 1));
            builder.insert(builder.toString().length() - 2, ".");

            numMoney.setText(builder.toString());
            numMoney.setSelection(builder.length());
            count--;
        } else {
            numMoney.setText("0.00");
            numMoney.setSelection(4);
        }
    }

    private void intent(Class activity, String payType) {
        if (numMoney.getText().toString().equals("0.00"))
            Toast.makeToast(this, "收款金额必须大于0", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(PaymentActivity.this, activity);
            intent.putExtra("payType", payType);
            intent.putExtra("amoutOfMoney", numMoney.getText().toString().trim());
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        numMoney.setText("0.00");
    }

    @Override
    protected void onResume() {
        super.onResume();
        numMoney.setText("0.00");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallServer.getHttpclient().cancelAll();
    }
}
