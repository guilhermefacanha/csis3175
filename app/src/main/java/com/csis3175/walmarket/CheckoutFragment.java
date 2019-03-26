package com.csis3175.walmarket;

import android.os.Bundle;
import android.se.omapi.Session;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.csis3175.walmarket.database.CartDbHelper;
import com.csis3175.walmarket.database.CartItemDbHelper;
import com.csis3175.walmarket.database.OrderDbHelper;
import com.csis3175.walmarket.database.OrderItemDbHelper;
import com.csis3175.walmarket.database.UserDbHelper;
import com.csis3175.walmarket.entity.Cart;
import com.csis3175.walmarket.entity.CartItem;
import com.csis3175.walmarket.entity.Order;
import com.csis3175.walmarket.entity.OrderItem;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.entity.User;
import com.csis3175.walmarket.util.MessageUtil;
import com.csis3175.walmarket.util.SessionUtil;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class CheckoutFragment extends Fragment {

    private MainActivity mainActivity;

    TextView txtOrder, txtOrderTotal;
    Button btnPlaceOrder;
    RadioButton radioDelivery;

    CartDbHelper cartDbHelper;
    CartItemDbHelper cartItemDbHelper;
    OrderDbHelper orderDbHelper;
    OrderItemDbHelper orderItemDbHelper;
    UserDbHelper userDbHelper;

    Store store;
    User user;
    Cart cart;
    List<CartItem> items;

    NumberFormat format = NumberFormat.getCurrencyInstance();
    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

    private double totalItems = 0;
    private double discount = 0;
    private double delivery = 0;
    private double beforeTax;
    private double totalTax;
    private double tax = 0.12;
    private double total = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

        try {
            initializeVariables();
            updateOrderInfo();
        }catch (Exception e){
            FindStoreFragment fragment = new FindStoreFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.frameContent, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(fragment.getClass().getSimpleName());
            ft.commit();
        }
    }

    private void initializeVariables() {
        this.txtOrder = mainActivity.findViewById(R.id.txtOrder);
        this.txtOrderTotal = mainActivity.findViewById(R.id.txtOrderTotal);
        this.btnPlaceOrder = mainActivity.findViewById(R.id.btnPlaceOrder);

        this.btnPlaceOrder.setOnClickListener(btnPlaceOrderOnClick());

        this.radioDelivery = mainActivity.findViewById(R.id.radioDelivery);
        this.radioDelivery.setOnCheckedChangeListener(changeDeliveryOptions());
    }

    private CompoundButton.OnCheckedChangeListener changeDeliveryOptions() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateOrderInfo();
            }
        };
    }

    private void updateOrderInfo() {
        cartDbHelper = new CartDbHelper(mainActivity);
        cartItemDbHelper = new CartItemDbHelper(mainActivity);
        orderDbHelper = new OrderDbHelper(mainActivity);
        orderItemDbHelper = new OrderItemDbHelper(mainActivity);
        userDbHelper = new UserDbHelper(mainActivity);

        store = SessionUtil.getStore(mainActivity);
        user = SessionUtil.getUser(mainActivity);

        cart = cartDbHelper.getLastCartByUserStore(user.getUserId(), store.getStoreId());
        items = cartItemDbHelper.getCartItemsByCartId(cart.getCartId());

        updateTotals();

        StringBuffer orderInfo = new StringBuffer();
        orderInfo.append("Items:                     " + format.format(totalItems) + "\n"+"\n");
        orderInfo.append("Delivery:                " + format.format(delivery) +  "\n"+"\n");
        orderInfo.append("Discount:               " + format.format(discount) +  "\n"+"\n");
        orderInfo.append("Total before tax:  " + format.format(beforeTax) +  "\n"+"\n");
        orderInfo.append("Tax(12%):              " + format.format(totalTax) + "\n");
        txtOrder.setText(orderInfo.toString());

        txtOrderTotal.setText("Order Total: " + format.format(total));

    }

    private void updateTotals() {
        totalItems = 0;
        for (CartItem item : items) {
            totalItems += item.getTotal();
        }

        if (radioDelivery.isChecked())
            delivery = 5;
        else
            delivery = 0;

        beforeTax = totalItems + delivery;

        if (user.getApplyFriendlyDisc() > 0) {
            double rate = (double) user.getApplyFriendlyDisc();
            discount = (rate / 100) * beforeTax;
            beforeTax = beforeTax - discount;
        }

        totalTax = beforeTax * tax;
        total = beforeTax + totalTax;
    }


    private View.OnClickListener btnPlaceOrderOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String optDeli = radioDelivery.isChecked() ? "Delivery" : "Pick-up at Store";

                Order order = new Order();
                order.setDate(dateFormat.format(new Date()));
                order.setDeliverCharge(delivery);
                order.setDeliverPickupOpt(optDeli);
                order.setFriendDiscount(discount);
                order.setStoreId(store.getStoreId());
                order.setUserId(user.getUserId());
                order.setStatus("Completed");
                order.setTotal(total);

                Long orderid = orderDbHelper.addOrder(order);
                if (orderid > 0) {
                    try {
                        for (CartItem item : items) {
                            OrderItem orderItem = new OrderItem();
                            orderItem.setPrice(item.getPrice());
                            orderItem.setOrderId(orderid.intValue());
                            orderItem.setItemId(item.getItemId());
                            orderItem.setQuantity(item.getQuantity());

                            orderItemDbHelper.addItem(orderItem);
                        }

                        for(CartItem item : items)
                            cartItemDbHelper.deleteCartItem(item);

                        cartDbHelper.deleteCart(cart.getCartId());

                        User u = userDbHelper.getUserByEmail(user.getEmail());
                        u.setApplyFriendlyDisc(0);
                        userDbHelper.updateRecord(u);
                        SessionUtil.setUser(u,mainActivity);

                        OrderHistoryFragment fragment = new OrderHistoryFragment();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.frameContent, fragment);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(fragment.getClass().getSimpleName());
                        ft.commit();

                        MessageUtil.addMessage("Order placed successfully.",mainActivity);

                    } catch (Exception e) {
                        MessageUtil.addMessage("Unable to place order: " + e.getMessage(), mainActivity);
                        orderDbHelper.deleteOrder(orderid.intValue());
                    }
                }

            }
        };
    }

}
