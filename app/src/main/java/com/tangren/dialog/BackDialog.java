package com.tangren.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import activity.com.szxb.tangren.payment.R;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class BackDialog extends Dialog {

    private Context context;

    private LayoutInflater inflater;

    private Button cancel, signOut;

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        public void canCel();

        public void singOut();
    }


    public BackDialog(Context context) {
        super(context,R.style.bottomDialogStyle);
        this.context = context;
        inflater = inflater.from(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.back_dialog_view, null);
        cancel = (Button) view.findViewById(R.id.quxiao);
        signOut = (Button) view.findViewById(R.id.out);
        cancel.setOnClickListener(new ClickListner());
        signOut.setOnClickListener(new ClickListner());
        setContentView(view);
    }

    public void setClickListener(ClickListenerInterface clickListener) {
        this.clickListenerInterface = clickListener;
    }

    private class ClickListner implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.quxiao:
                    clickListenerInterface.canCel();
                    break;
                case R.id.out:
                    clickListenerInterface.singOut();
                    break;
            }
        }
    }
}
