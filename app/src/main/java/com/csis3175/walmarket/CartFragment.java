package com.csis3175.walmarket;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.csis3175.walmarket.adapter.CartItemListAdapter;
import com.csis3175.walmarket.database.CartDbHelper;
import com.csis3175.walmarket.database.CartItemDbHelper;
import com.csis3175.walmarket.entity.Cart;
import com.csis3175.walmarket.entity.CartItem;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.ImageUtil;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartFragment extends Fragment {

    private MainActivity mainActivity;

    CartDbHelper cartDbHelper;
    CartItemDbHelper cartItemDbHelper;

    Store store;
    User user;
    Cart cart;
    List<CartItem> items;

    private ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        initializeVariables();
        initializeList();
    }

    private void initializeVariables() {
        cartDbHelper = new CartDbHelper(mainActivity);
        cartItemDbHelper = new CartItemDbHelper(mainActivity);

        store = SessionUtil.getStore(mainActivity);
        user = SessionUtil.getUser(mainActivity);

        cart = cartDbHelper.getLastCartByUserStore(user.getUserId(), store.getStoreId());
    }

    private void initializeList() {
        try {
            items = cartItemDbHelper.getCartItemsByCartId(cart.getCartId());
            CartItemListAdapter adapter = new CartItemListAdapter(items, mainActivity, this);
            list = mainActivity.findViewById(R.id.lstCartItem);
            list.setAdapter(adapter);
        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), mainActivity);
        }
    }

    public void updateCart(String msg) {
        initializeList();
        MessageUtil.addMessage(msg,mainActivity);
    }
}
