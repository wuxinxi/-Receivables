package com.tangren.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tangren.model.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import activity.com.szxb.tangren.payment.R;

/**
 * Created by Administrator on 2016/9/2 0002.
 */
public class RecordAdapter extends BaseAdapter {

    private Context context;

    private List<RecordBean> recordBeans = new ArrayList<RecordBean>();

    private LayoutInflater inflater;

    public RecordAdapter(Context context, List<RecordBean> recordBeans) {
        this.context = context;
        this.recordBeans = recordBeans;
        inflater = inflater.from(context);
    }


    @Override
    public int getCount() {
        return recordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.record_item_view, null);
            holder.textView = (TextView) convertView.findViewById(R.id.id);
            holder.textView2 = (TextView) convertView.findViewById(R.id.dingdanhao);
            holder.textView3 = (TextView) convertView.findViewById(R.id.wfdingdanhao);
            holder.textView4 = (TextView) convertView.findViewById(R.id.timeed);
            holder.textView5 = (TextView) convertView.findViewById(R.id.money);
            holder.textView6 = (TextView) convertView.findViewById(R.id.paytype);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        RecordBean recordBean = (RecordBean) getItem(position);
        holder.textView.setText(recordBean.getId() + "");
        holder.textView2.setText(recordBean.getOuttradeno());
        holder.textView3.setText(recordBean.getTransactionid());
        holder.textView4.setText(recordBean.getTimeend());
        holder.textView5.setText(recordBean.getMoney()+ Html.fromHtml("<font color=\"#EA1F39\">￥</font>"));
        holder.textView6.setText(recordBean.getPaytype());

        return convertView;
    }


    class ViewHolder {
        TextView textView, textView2, textView3, textView4, textView5, textView6;
    }
}
