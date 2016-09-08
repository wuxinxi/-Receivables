package com.tangren.presenter;

import android.widget.TextView;

import com.tangren.szxb.LoginView;
import com.yolanda.nohttp.Logger;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class LoginCompl implements LoginPresenter {
    private LoginView view;

    public LoginCompl(LoginView view) {
        this.view = view;
    }

    @Override
    public void out() {
        view.onFinish();
    }

    @Override
    public void login(TextView user, TextView psw) {
        int code = 400;
        String name = user.getText().toString().trim();
        Logger.d(name);
        String password = psw.getText().toString().trim();
        Logger.d(password);
        if (name.equals("00") && password.equals("123456789"))
            code = 100;
        else
            code = 400;
        view.onLoginResult(code);

    }
}
