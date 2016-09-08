package com.tangren.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tangren.font.Toast;
import com.tangren.presenter.PaymentFramePresenter;
import com.tangren.presenter.PaymentFramePresenterCompl;
import com.wizarpos.barcode.scanner.IScanEvent;
import com.wizarpos.barcode.scanner.ScannerRelativeLayout;

import activity.com.szxb.tangren.payment.R;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class PaymentFrameDialog extends Dialog implements PaymentView {

    private Context context;
    private PaymentFramePresenter paymentFramePresenter;
    private LayoutInflater inflater;
    private ImageView imageView;
    private IScanEvent scanLisenter;
    private TextView textLine1, textLine2;
    private static ScannerRelativeLayout scanView;// 扫描控件
    private SurfaceView scanShow;// 扫描区域
    private Button autoScan, passiveScan;
    private static LinearLayout imageLayout;
    private static FrameLayout surfaceLayout;

    public PaymentFrameDialog(Context context) {
        super(context, R.style.bottomDialogStyle);
        this.context = context;
        this.inflater = inflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = inflater.inflate(R.layout.dialog_view, null);
        imageLayout = (LinearLayout) view.findViewById(R.id.imageLayout); // 图片区域
        surfaceLayout = (FrameLayout) view.findViewById(R.id.surfaceViewLayout); // 扫描区域
        scanView = (ScannerRelativeLayout) view.findViewById(R.id.scanner);// 扫描控件
        autoScan = (Button) view.findViewById(R.id.autoScan);
        imageView = (ImageView) view.findViewById(R.id.imageShow);
        passiveScan = (Button) view.findViewById(R.id.passiveScan);
        textLine1 = (TextView) view.findViewById(R.id.textLineShow1);
        textLine2 = (TextView) view.findViewById(R.id.textLineShow2);
        autoScan.setOnClickListener(new MyButtonClick());
        passiveScan.setOnClickListener(new MyButtonClick());
        paymentFramePresenter = new PaymentFramePresenterCompl(this);
        setOnKeyListener(listener);
        setContentView(view);
        setWindoSize(0.8, 0.8);

    }

    private void setWindoSize(double width, double height) {
        Window window = getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics display = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (display.heightPixels * height);
        lp.width = (int) (display.widthPixels * width);
        window.setAttributes(lp);
    }

    @Override
    public void onResult(String response) {
        Toast.makeToast(context,response, android.widget.Toast.LENGTH_LONG).show();
    }



    public class MyButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.autoScan://主扫
//                    paymentFramePresenter.doMainSweep(context,scanView,imageLayout,imageView,surfaceLayout,textLine1,textLine2,scanLisenter);
                    break;
                case R.id.passiveScan://被扫
//                    paymentFramePresenter.doSwept(context,scanView,imageLayout,imageView,surfaceLayout,textLine1,textLine2,scanLisenter);
                    break;
            }
        }
    }

    private OnKeyListener listener=new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                scanView.stopScan();
            }
            return true;
        }
    };
}
