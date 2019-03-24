package com.csis3175.walmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.csis3175.walmarket.CartFragment;
import com.csis3175.walmarket.R;
import com.csis3175.walmarket.entity.CartItem;
import com.csis3175.walmarket.util.ImageUtil;
import com.csis3175.walmarket.util.MessageUtil;

import org.w3c.dom.Text;

import java.util.List;

public class CartItemListAdapter extends BaseAdapter {

    private final Context context;
    private final List<CartItem> items;
    private final CartFragment parent;

    public CartItemListAdapter(List<CartItem> items, Context context, CartFragment cartFragment) {
        this.context = context;
        this.items = items;
        this.parent = cartFragment;
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return items == null ? null : items.get(position);
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
            view = inflater.inflate(R.layout.lst_cart, null);
        }

        CartItem item = items.get(position);
        ImageButton btnRemove = view.findViewById(R.id.btnRemoveItemCart);
        ImageButton btnAdd = view.findViewById(R.id.btnAddItemCart);
        ImageView btnDel = view.findViewById(R.id.btnDeleteItemCart);
        TextView txtDesc = view.findViewById(R.id.cart_item_desc);
        TextView txtQty = view.findViewById(R.id.cart_item_qty);
        TextView txtTotal = view.findViewById(R.id.cart_item_total);
        ImageView image = view.findViewById(R.id.cart_item_img);

        txtDesc.setText(item.getItemDescription());
        txtQty.setText("QTY\n" + item.getQuantity());
        txtTotal.setText("Subtotal\n" + item.getTotalStr());
        image.setImageBitmap(ImageUtil.byteToBitmap(item.getItemImage()));

        btnRemove.setOnClickListener(removeItemCart(item));
        btnAdd.setOnClickListener(addItemCart(item));
        btnDel.setOnClickListener(deleteItemCart(item));
        return view;
    }

    private View.OnClickListener addItemCart(final CartItem item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtil.addMessage("Add one unit of item " + item.getItemDescription(), context);
            }
        };
    }

    private View.OnClickListener removeItemCart(final CartItem item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtil.addMessage("Remove one unit of item " + item.getItemDescription(), context);
            }
        };
    }

    private View.OnClickListener deleteItemCart(final CartItem item) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageUtil.addMessage("Delete the item " + item.getItemDescription(), context);
                parent.updateCart();
            }
        };
    }
}
