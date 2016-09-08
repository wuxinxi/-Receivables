package com.tangren.szxb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class SettingActivity extends AppCompatActivity {
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.setting)
    Button setting;
    @InjectView(R.id.safeManager)
    Button safeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settting_main);
        ButterKnife.inject(this);
        textView.setText("设置");
    }

    @OnClick({ R.id.setting, R.id.safeManager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                break;
            case R.id.safeManager:
                break;
        }
    }
}
