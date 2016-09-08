package com.tangren.szxb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tangren.font.Toast;
import com.tangren.presenter.LoginCompl;
import com.tangren.presenter.LoginPresenter;
import com.tangren.utlis.Utils;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class Login extends AppCompatActivity implements LoginView, View.OnLongClickListener {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.user)
    TextView user;
    @InjectView(R.id.psw)
    TextView psw;
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

    private boolean choseDefault = true;

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        textView.setText("安全登录");
        presenter = new LoginCompl(this);
        numDel.setOnLongClickListener(this);
    }

    @OnClick({R.id.out_activity, R.id.login, R.id.num_7, R.id.num_8, R.id.num_9, R.id.num_4,
            R.id.num_5, R.id.num_6, R.id.num_1, R.id.num_2, R.id.num_3, R.id.num_0, R.id.num_del, R.id.user, R.id.psw})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_activity:
                presenter.out();
                break;
            case R.id.login:
                presenter.login(user, psw);
                break;
            case R.id.num_7:
                if (choseDefault)
                    Utils.addNum("7", user);
                else
                    Utils.addNum("7", psw);
                break;
            case R.id.num_8:
                if (choseDefault)
                    Utils.addNum("8", user);
                else
                    Utils.addNum("8", psw);
                break;
            case R.id.num_9:
                if (choseDefault)
                    Utils.addNum("9", user);
                else
                    Utils.addNum("9", psw);
                break;
            case R.id.num_4:
                if (choseDefault)
                    Utils.addNum("4", user);
                else
                    Utils.addNum("4", psw);
                break;
            case R.id.num_5:
                if (choseDefault)
                    Utils.addNum("5", user);
                else
                    Utils.addNum("5", psw);
                break;
            case R.id.num_6:
                if (choseDefault)
                    Utils.addNum("6", user);
                else
                    Utils.addNum("6", psw);
                break;
            case R.id.num_1:
                if (choseDefault)
                    Utils.addNum("1", user);
                else
                    Utils.addNum("1", psw);
                break;
            case R.id.num_2:
                if (choseDefault)
                    Utils.addNum("2", user);
                else
                    Utils.addNum("2", psw);
                break;
            case R.id.num_3:
                if (choseDefault)
                    Utils.addNum("3", user);
                else
                    Utils.addNum("3", psw);
                break;
            case R.id.num_0:
                if (choseDefault)
                    Utils.addNum("0", user);
                else
                    Utils.addNum("0", psw);
                break;
            case R.id.num_del:
                if (choseDefault)
                    Utils.delNum(user);
                else
                    Utils.delNum(psw);
                break;
            case R.id.user:
                choseDefault = true;
                user.setBackgroundResource(R.drawable.login_edit_shape_click);
                psw.setBackgroundResource(R.drawable.login_edit_shape);

                break;
            case R.id.psw:
                choseDefault = false;
                user.setBackgroundResource(R.drawable.login_edit_shape);
                psw.setBackgroundResource(R.drawable.login_edit_shape_click);
                break;
        }
    }


    @Override
    public void onFinish() {
        finish();
    }

    @Override
    public void onLoginResult(int code) {
        if (code == 100) {
            startActivity(new Intent(Login.this, Main.class));
            finish();
        } else
            Toast.makeToast(this, "请检查账号密码！", Toast.LENGTH_SHORT).show();

    }

    /**
     * 回删按钮的长按事件
     *
     * @param v
     * @return
     */

    @Override
    public boolean onLongClick(View v) {
        if (choseDefault)
            user.setText("");
        else
            psw.setText("");
        return true;
    }
}
