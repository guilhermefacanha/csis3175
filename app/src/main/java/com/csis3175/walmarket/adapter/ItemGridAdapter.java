package com.csis3175.walmarket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csis3175.walmarket.R;
import com.csis3175.walmarket.entity.ItemStore;

import java.util.List;

public class ItemGridAdapter extends BaseAdapter {

    Context context;
    List<ItemStore> items;

    public ItemGridAdapter(Context context, List<ItemStore> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gridViewAndroid = new View(context);
        gridViewAndroid = inflater.inflate(R.layout.grid_item_store, null);
        TextView txtDesc = (TextView) gridViewAndroid.findViewById(R.id.grid_item_desc);
        TextView txtPrice = (TextView) gridViewAndroid.findViewById(R.id.grid_item_price);
        ImageView image = (ImageView) gridViewAndroid.findViewById(R.id.grid_item_image);
        txtDesc.setText(items.get(position).getItemDescription());
        txtPrice.setText(items.get(position).getFinalPrice());
        byte[] itemImage = items.get(position).getItemImage();
        image.setImageBitmap(BitmapFactory.decodeByteArray(itemImage, 0, itemImage.length));

        return gridViewAndroid;

    }
}
