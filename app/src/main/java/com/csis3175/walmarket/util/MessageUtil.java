package com.csis3175.walmarket.util;

import android.content.Context;
import android.widget.Toast;

public class MessageUtil {
    public static void addMessage(String msg, Context ctx){
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
