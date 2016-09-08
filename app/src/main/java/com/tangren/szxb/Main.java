package com.tangren.szxb;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tangren.dialog.BackDialog;
import com.tangren.httpclient.CallServer;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class Main extends AppCompatActivity {
    @InjectView(R.id.payment)
    Button payment;
    @InjectView(R.id.manager)
    Button manager;
    @InjectView(R.id.query)
    Button query;
    @InjectView(R.id.setting)
    Button setting;

    private BackDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        ButterKnife.inject(this);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("收款");


    }

    @OnClick({R.id.payment, R.id.manager, R.id.query, R.id.setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.payment:
                startActivity(new Intent(Main.this, PaymentActivity.class));
                break;
            case R.id.manager:
                break;
            case R.id.query:
                Intent(AdminActivity.class, "OrderActivity");
                break;
            case R.id.setting:
                Intent(AdminActivity.class, "SettingActivity");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallServer.getHttpclient().stopAll();
    }

    private void Intent(Class activity, String activityName) {
        Intent intent = new Intent(Main.this, activity);
        intent.putExtra("activityName", activityName);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            mDialog = new BackDialog(this);
            mDialog.setClickListener(new BackDialog.ClickListenerInterface() {
                @Override
                public void canCel() {
                    mDialog.dismiss();
                }

                @Override
                public void singOut() {
                    finish();
                }
            });
            mDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
