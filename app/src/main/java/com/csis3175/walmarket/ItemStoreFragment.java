package com.csis3175.walmarket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.csis3175.walmarket.adapter.ItemGridAdapter;
import com.csis3175.walmarket.database.CartDbHelper;
import com.csis3175.walmarket.database.CartItemDbHelper;
import com.csis3175.walmarket.database.ItemStoreDbHelper;
import com.csis3175.walmarket.entity.Cart;
import com.csis3175.walmarket.entity.CartItem;
import com.csis3175.walmarket.entity.ItemStore;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;


public class ItemStoreFragment extends Fragment {

    MainActivity mainActivity;
    View view;

    GridView gridItems;
    EditText txtSearch;

    ItemStoreDbHelper itemStoreDbHelper;
    CartDbHelper cartDbHelper;
    CartItemDbHelper cartItemDbHelper;

    List<ItemStore> items;
    List<ItemStore> itemsDefault;
    Store store;
    User user;
    Cart cart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_item_store, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        itemStoreDbHelper = new ItemStoreDbHelper(mainActivity);
        cartDbHelper = new CartDbHelper(mainActivity);
        cartItemDbHelper = new CartItemDbHelper(mainActivity);

        gridItems = getActivity().findViewById(R.id.gridItems);
        txtSearch = getActivity().findViewById(R.id.txtSearchItem);
        txtSearch.addTextChangedListener(searchItem());

        store = SessionUtil.getStore(mainActivity);
        user = SessionUtil.getUser(mainActivity);

        itemsDefault = itemStoreDbHelper.getStoreItems(store.getStoreId());
        items = new ArrayList<>();
        items.addAll(itemsDefault);

        cart = cartDbHelper.getLastCartByUserStore(user.getUserId(), store.getStoreId());

        initGrid();
    }

    private TextWatcher searchItem() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String search = txtSearch.getText().toString();
                if (search.length() > 2) {
                    items = filterItems(search.toLowerCase());
                    initGrid();
                } else if (search.isEmpty()) {
                    items = new ArrayList<>();
                    items.addAll(itemsDefault);
                    initGrid();
                }

            }
        };
    }

    private List<ItemStore> filterItems(String search) {
        List<ItemStore> list = new ArrayList<>();
        for (ItemStore item : itemsDefault) {
            if (item.getItemDescription().toLowerCase().contains(search))
                list.add(item);
        }
        return list;
    }

    private void initGrid() {
        gridItems.setAdapter(new ItemGridAdapter(mainActivity, items));
        gridItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemStore selectedItem = items.get(position);
                if (cart == null)
                    createNewCart();

                CartItem ci = cartItemDbHelper.getCartItemById(cart.getCartId(), selectedItem.getItemId());
                if (ci != null) {
                    ci.setQuantity(ci.getQuantity() + 1);
                    cartItemDbHelper.updateCart(ci);
                } else {
                    ci = new CartItem();
                    ci.setCartId(cart.getCartId());
                    ci.setItemId(selectedItem.getItemId());
                    ci.setPrice(selectedItem.getFinalPriceDouble());
                    ci.setQuantity(1);
                    cartItemDbHelper.addCartItem(ci);
                }

                cart.setTotal(cart.getTotal() + ci.getPrice());
                cartDbHelper.updateCart(cart);

                MessageUtil.addMessage("Item " + selectedItem.getItemDescription() + " added to the Cart.", mainActivity);
            }
        });
    }

    private void createNewCart() {
        cart = new Cart();
        cart.setStoreId(store.getStoreId());
        cart.setUserId(user.getUserId());
        cart.setTotal(new Double(0));

        cartDbHelper.addCart(cart);

        cart = cartDbHelper.getLastCartByUserStore(user.getUserId(), store.getStoreId());

    }

}
