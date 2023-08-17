package com.Spike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.Spike.Order;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Order> {

    public OrderAdapter(Context context, List<Order> orders) {
        super(context, 0, orders);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.orderlistview, parent, false);
        }

        Order order = getItem(position);

        TextView orderIdTextView = convertView.findViewById(R.id.orderIdTextView);
        TextView carIdTextView = convertView.findViewById(R.id.carIdTextView);
        TextView stateTextView = convertView.findViewById(R.id.stateTextView);
        TextView applyKwhTextView = convertView.findViewById(R.id.applyKwhTextView);
        TextView chargeKwhTextView = convertView.findViewById(R.id.chargeKwhTextView);
        TextView chargePriceTextView = convertView.findViewById(R.id.chargePriceTextView);
        TextView chargeIdTextView = convertView.findViewById(R.id.chargeIdTextView);
        TextView createTimeTextView = convertView.findViewById(R.id.createTimeTextView);
        TextView dispatchTimeTextView = convertView.findViewById(R.id.dispatchTimeTextView);
        TextView startTimeTextView = convertView.findViewById(R.id.startTimeTextView);
        TextView finishTimeTextView = convertView.findViewById(R.id.finishTimeTextView);
        TextView feeTextView = convertView.findViewById(R.id.feeTextView);

        // 其他文本视图
        orderIdTextView.setText("订单号: " + order.getID());
        carIdTextView.setText("车辆ID: " + order.getCarID());
        stateTextView.setText("车辆状态: " + order.getState());
        applyKwhTextView.setText("申请电量: " + order.getApplyKwh());
        chargeKwhTextView.setText("已充电量: " + order.getChargeKwh());
        chargePriceTextView.setText("当前电量计费" + order.getChargePrice());
        chargeIdTextView.setText("分配的充电桩ID: " + order.getChargeID());
        createTimeTextView.setText("创建时间: " + order.getCreateTime());
        dispatchTimeTextView.setText("调度时间: " + order.getDispatchTime());
        startTimeTextView.setText("开始充电时间: " + order.getStartTime());
        finishTimeTextView.setText("结束充电时间: " + order.getFinishTime());
        feeTextView.setText("总计费: " + order.getFee());

        // 设置其他文本视图的数据
        return convertView;
    }
}
