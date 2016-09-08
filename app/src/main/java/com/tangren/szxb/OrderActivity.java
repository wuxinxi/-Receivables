package com.tangren.szxb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tangren.adapter.RecordAdapter;
import com.tangren.db.OrderDBManager;
import com.tangren.font.Toast;
import com.tangren.httpclient.CallServer;
import com.tangren.httpclient.HttpListener;
import com.tangren.model.bean.RecordBean;
import com.tangren.utlis.Config;
import com.tangren.utlis.Parmas;
import com.tangren.utlis.XMlUtils;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import activity.com.szxb.tangren.payment.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class OrderActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.textView)
    TextView textView;

    private RecordAdapter mAdapter;

    private List<RecordBean> recordBeans = new ArrayList<RecordBean>();

    private OrderDBManager orderDBManager;

    private int custom_postion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_main);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
        textView.setText("查询|撤销");
        orderDBManager = new OrderDBManager();
        recordBeans = orderDBManager.query();
//        for (int i = 0; i < 10; i++) {
//            RecordBean bean = new RecordBean();
//            bean.setMoney("0.00" + 1);
//            bean.setOuttradeno("111" + 1);
//            bean.setPaytype("AAAAAAAAAAAAAAAAAA" + 1);
//            bean.setTimeend("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB" + 1);
//            bean.setTransactionid("C" + 1);
//            recordBeans.add(bean);
//        }

        mAdapter = new RecordAdapter(this, recordBeans);
        listView.setAdapter(mAdapter);
        listView.setOnItemLongClickListener(this);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        RecordBean bean = recordBeans.get(position);
        custom_postion = position;
        final String out_trade_no = bean.getOuttradeno();
        final String fee = bean.getMoney();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("退款")
                .setMessage("是否发起退款")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Request<String> request = NoHttp.createStringRequest(Config.payUrl, RequestMethod.POST);
                        request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);
                        request.setDefineRequestBodyForXML(Parmas.reFundArgs(out_trade_no, fee));
                        CallServer.getHttpclient().add(OrderActivity.this, "正在发起退款,请稍后……", 4, request, callBack, false, true);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        return true;
    }

    HttpListener<String> callBack = new HttpListener<String>() {
        @Override
        public void success(int what, Response<String> response) {
            Map<String, String> xml = XMlUtils.decodeXml(response.get());
            System.out.println(xml.toString());
            int status = Integer.valueOf(xml.get("status"));
            if (status == 0) {
                int result_code = Integer.valueOf(xml.get("result_code"));
                if (result_code == 0) {
                    orderDBManager.delete(xml.get("out_trade_no"));
                    recordBeans.remove(custom_postion);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeToast(OrderActivity.this, "申请退款成功,将在1-3工作日将退款退到您的账户！", Toast.LENGTH_LONG).show();
                } else if (result_code == 1) {
                    Toast.makeToast(OrderActivity.this, "退款金额大于支付金额或已退款！", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeToast(OrderActivity.this, "申请退款失败！", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void fail(int what, Response<String> response) {
            Toast.makeToast(OrderActivity.this, "申请退款失败！", Toast.LENGTH_LONG).show();
        }
    };
}
