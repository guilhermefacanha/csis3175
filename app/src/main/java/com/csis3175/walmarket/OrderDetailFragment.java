package com.csis3175.walmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.csis3175.walmarket.adapter.OrderItemAdapter;
import com.csis3175.walmarket.database.OrderDbHelper;
import com.csis3175.walmarket.database.OrderItemDbHelper;
import com.csis3175.walmarket.database.StoreDbHelper;
import com.csis3175.walmarket.entity.Order;
import com.csis3175.walmarket.entity.OrderItem;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.util.ImageUtil;
import com.csis3175.walmarket.util.MessageUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrderDetailFragment extends Fragment {

    MainActivity mainActivity;

    StoreDbHelper storeDbHelper;
    OrderDbHelper orderDbHelper;
    OrderItemDbHelper orderItemDbHelper;

    private List<HashMap<String, String>> listMap = new ArrayList<>();
    private ListView list;
    private Order order = new Order();
    private Store store;
    private List<OrderItem> items;

    TextView txtDateType, txtTotal, txtTax;

    public OrderDetailFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public OrderDetailFragment(int orderId) {
        order.setOrderId(orderId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        initializeVariables();
        updateOrderInfo();
        initalizeList();
    }

    private void initializeVariables() {
        txtDateType = mainActivity.findViewById(R.id.txtOrderDetailDateType);
        txtTotal = mainActivity.findViewById(R.id.txtOrderDetailTotal);
        txtTax = mainActivity.findViewById(R.id.txtOrderDetailTax);

        orderDbHelper = new OrderDbHelper(mainActivity);
        orderItemDbHelper = new OrderItemDbHelper(mainActivity);
        storeDbHelper = new StoreDbHelper(mainActivity);

        order = orderDbHelper.getOrderById(order.getOrderId());
        store = storeDbHelper.getStoreById(order.getStoreId());
        order.setStoreDescription(store != null ? store.getName() : "");
    }

    private void updateOrderInfo() {
        StringBuffer orderInfo = new StringBuffer();
        orderInfo.append("Date: " + order.getDate() + "\n");
        orderInfo.append("Store: " + order.getStoreDescription() + "\n");
        orderInfo.append("Type: " + order.getDeliverPickupOpt() + "\n");
        txtDateType.setText(orderInfo.toString());

        StringBuffer orderTotalInfo = new StringBuffer();
        orderTotalInfo.append("Total: " + NumberFormat.getCurrencyInstance().format(order.getTotal()));
        txtTotal.setText(orderTotalInfo.toString());

        StringBuffer orderTotalTax = new StringBuffer();
        orderTotalTax.append("Discount: " + NumberFormat.getCurrencyInstance().format(order.getFriendDiscount())+ "\n");
        orderTotalTax.append("Delivery: " + NumberFormat.getCurrencyInstance().format(order.getDeliverCharge()));
        txtTax.setText(orderTotalTax.toString());
    }

    private void initalizeList() {
        try {
            listMap.clear();
            items = orderItemDbHelper.getOrderItems(order.getOrderId());
            OrderItemAdapter adapter = new OrderItemAdapter(items, mainActivity);
            list = mainActivity.findViewById(R.id.lstOrderItems);
            list.setAdapter(adapter);
        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), mainActivity);
        }
    }

}
