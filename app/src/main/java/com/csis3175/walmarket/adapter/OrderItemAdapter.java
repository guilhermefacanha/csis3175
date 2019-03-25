package com.csis3175.walmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csis3175.walmarket.R;
import com.csis3175.walmarket.entity.OrderItem;
import com.csis3175.walmarket.util.ImageUtil;

import java.text.NumberFormat;
import java.util.List;

public class OrderItemAdapter extends BaseAdapter {

    private List<OrderItem> items;
    private final Context context;

    public OrderItemAdapter(List<OrderItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items != null ? items.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lst_order_item, null);
        }

        OrderItem item = items.get(position);

        ImageView img = view.findViewById(R.id.order_item_img);
        TextView txtDesc = view.findViewById(R.id.order_item_desc);
        TextView txtQty = view.findViewById(R.id.order_item_qty);
        TextView txtPrice = view.findViewById(R.id.order_item_price);

        img.setImageBitmap(ImageUtil.byteToBitmap(item.getItemImage()));
        txtDesc.setText(item.getItemDescription());
        txtQty.setText("x " + item.getQuantity() + "");
        txtPrice.setText(NumberFormat.getCurrencyInstance().format(item.getPrice()));

        return view;
    }
}
