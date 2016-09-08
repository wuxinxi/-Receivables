package com.tangren.presenter;

import android.widget.EditText;

import com.tangren.szxb.AdminView;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class AdminCompl implements AdminPresenter {
    AdminView adminView;

    public AdminCompl(AdminView adminView) {
        this.adminView = adminView;

    }


    @Override
    public void outFinish() {
        adminView.onFinish();
    }

    @Override
    public void deterMine(EditText psw) {
        String password = psw.getText().toString();
        int code = 400;
        if (password.equals("123456789"))
            code = 100;
        else
            code = 400;

        adminView.onLogin(code);
    }
}
