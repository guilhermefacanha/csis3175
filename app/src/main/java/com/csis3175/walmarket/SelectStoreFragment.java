package com.csis3175.walmarket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SelectStoreFragment extends Fragment {

    MainActivity mainActivity;
    private Address address;
    private String postalCode;
    private List<Store> stores;

    private List<HashMap<String, String>> listMap = new ArrayList<>();

    private ListView list;
    private Context context;
    private View view;

    public SelectStoreFragment() {

    }

    @SuppressLint("ValidFragment")
    public SelectStoreFragment(List<Store> stores, String postalCode, Address address) {
        this.stores = stores;
        this.postalCode = postalCode;
        this.address = address;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select_store, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        initalizeList();
    }

    private void initalizeList() {
        try {
            for (Store store : stores) {
                HashMap<String, String> map = new HashMap<>();
                //map.put("img", Integer.toString(R.drawable.ic_map));
                map.put("name", store.getName());
                map.put("dist", store.getFormatedDistance());

                listMap.add(map);
            }

            String[] from = {"name", "dist"};
            int[] to = {R.id.store_name, R.id.store_distance};
            SimpleAdapter adapter = new SimpleAdapter(mainActivity, listMap, R.layout.lst_near_store, from, to);
            list = mainActivity.findViewById(R.id.lstNearStores);
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
                MessageUtil.addMessage("Selected store :" + position, mainActivity);
                SessionUtil.setStore(stores.get(position), mainActivity);
                mainActivity.updateStoreInfo();
            }
        };
    }


}
