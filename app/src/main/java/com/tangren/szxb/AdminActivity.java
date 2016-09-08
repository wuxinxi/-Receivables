package com.tangren.szxb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tangren.font.Toast;
import com.tangren.presenter.AdminCompl;
import com.tangren.presenter.AdminPresenter;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class AdminActivity extends AppCompatActivity implements AdminView, View.OnLongClickListener {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.psw)
    EditText psw;
    @InjectView(R.id.out_activity)
    Button outActivity;
    @InjectView(R.id.login)
    Button login;
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

    private AdminPresenter adminPresenter;

    private String activityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
        ButterKnife.inject(this);

        initView();
    }

    private void initView() {
        textView.setText("安全密码");
        Intent intent = getIntent();
        activityName = intent.getStringExtra("activityName");
        adminPresenter = new AdminCompl(this);
        numDel.setOnLongClickListener(this);
    }

    @OnClick({R.id.out_activity, R.id.login, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_0, R.id.num_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_activity:
                adminPresenter.outFinish();
                break;
            case R.id.login:
                adminPresenter.deterMine(psw);
                break;
            case R.id.num_7:
                addNum("7");
                break;
            case R.id.num_8:
                addNum("8");
                break;
            case R.id.num_9:
                addNum("9");
                break;
            case R.id.num_4:
                addNum("4");
                break;
            case R.id.num_5:
                addNum("5");
                break;
            case R.id.num_6:
                addNum("6");
                break;
            case R.id.num_1:
                addNum("1");
                break;
            case R.id.num_2:
                addNum("2");
                break;
            case R.id.num_3:
                addNum("3");
                break;
            case R.id.num_0:
                addNum("0");
                break;
            case R.id.num_del:
                delNum();
                break;
        }
    }

    private void addNum(String num) {
        String result = psw.getText().toString().trim() + num;
        psw.setText(result);
    }

    private void delNum() {
        String result = psw.getText().toString();
        if (result.equals(""))
            return;
        result = result.substring(0, result.length() - 1);
        psw.setText(result);
    }

    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void onLogin(int code) {
        if (code == 100) {

            if (activityName.equals("OrderActivity"))
                startActivity(new Intent(AdminActivity.this, OrderActivity.class));
            else if (activityName.equals("SettingActivity"))
                startActivity(new Intent(AdminActivity.this, SettingActivity.class));

            finish();
        } else
            Toast.makeToast(this, "密码错误，请重试！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View v) {
        psw.setText("");
        return true;
    }
}
