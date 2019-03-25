package com.csis3175.walmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175.walmarket.entity.Order;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.util.MessageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyOrdersFragment extends Fragment {
    MainActivity mainActivity;
    private List<Order> orders;

    private List<HashMap<String, String>> listMap = new ArrayList<>();

    private ListView list;
    private Context context;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_orders, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        initalizeList();
    }

    private void initalizeList() {
        try {
            for (Order order : orders) {
                HashMap<String, String> map = new HashMap<>();
                //map.put("img", Integer.toString(R.drawable.ic_map));
                map.put("orderId", order.getOrderId().toString());
                map.put("date", order.getDate());
                map.put("status", order.getStatus());
                map.put("total", order.getTotal().toString());

                listMap.add(map);
            }

            String[] from = {"orderId", "date", "status", "total"};
            int[] to = {R.id.order_id, R.id.order_date,R.id.order_status, R.id.order_total};
            SimpleAdapter adapter = new SimpleAdapter(mainActivity, listMap, R.layout.lst_my_orders, from, to);
            list = mainActivity.findViewById(R.id.lstOrderHistory);
            list.setAdapter(adapter);
            //list.setOnItemClickListener(listClickListener());
        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), mainActivity);
        }




    }


}
