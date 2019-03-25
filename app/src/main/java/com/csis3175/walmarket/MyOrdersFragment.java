package com.csis3175.walmarket;

import android.content.Context;
import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175.walmarket.database.OrderDbHelper;
import com.csis3175.walmarket.entity.Order;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyOrdersFragment extends Fragment {
    MainActivity mainActivity;
    private List<Order> orders = new ArrayList<>();

    private List<HashMap<String, String>> listMap = new ArrayList<>();

    private ListView list;
    private Context context;
    private View view;

    private User user;

    private OrderDbHelper orderDbHelper;

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
        user = SessionUtil.getUser(mainActivity);
        orderDbHelper = new OrderDbHelper(mainActivity);

        orders = orderDbHelper.getOrdersByUser(user.getUserId());

        listMap = new ArrayList<>();
        initializeList();
    }

    private void initializeList() {
        try {
            for (Order order : orders) {
                HashMap<String, String> map = new HashMap<>();
                //map.put("img", Integer.toString(R.drawable.ic_map));
                map.put("orderId", order.getOrderId().toString());
                map.put("date", order.getDate());
                map.put("type", order.getDeliverPickupOpt());
                map.put("total", NumberFormat.getCurrencyInstance().format(order.getTotal()));

                listMap.add(map);
            }

            String[] from = {"orderId", "date", "type", "total"};
            int[] to = {R.id.order_id, R.id.order_date,R.id.order_type, R.id.order_total};
            SimpleAdapter adapter = new SimpleAdapter(mainActivity, listMap, R.layout.lst_order, from, to);
            list = mainActivity.findViewById(R.id.lstOrderHistory);
            list.setAdapter(adapter);
            list.setOnItemClickListener(listClickListener());
        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), mainActivity);
        }




    }

    private AdapterView.OnItemClickListener listClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = orders.get(position);
                OrderDetailFragment fragment = new OrderDetailFragment(order.getOrderId());
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frameContent, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(fragment.getClass().getSimpleName());
                ft.commit();
            }
        };
    }


}
