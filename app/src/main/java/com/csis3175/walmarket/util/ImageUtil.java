package com.csis3175.walmarket.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtil {

    public static byte[] getImage(String imageUrl) {
        try {
            OkHttpHandler handler = new OkHttpHandler();
            byte[] imageArray = handler.execute(imageUrl).get();
            return imageArray;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
        return stream.toByteArray();
    }

    public static String BitMapToString(byte[] image){
        String temp= Base64.encodeToString(image, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap byteToBitmap(byte[] image){
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap StringToBitMap(String encodedString){
        try {
            byte[] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            return null;
        }
    }
}
