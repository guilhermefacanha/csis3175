package com.csis3175.walmarket.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.csis3175.walmarket.entity.User;

public class SessionUtil {

    public static void setUser(User user, Context ctx){

        SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("fname", user.getfName());
        editor.putString("lname", user.getlName());
        editor.putString("email", user.getEmail());
        editor.putString("address", user.getAddress());
        editor.putInt("apply", user.getApplyFriendlyDisc());
        editor.putInt("id", user.getUserId());
        editor.commit();
    }

    public static User getUser(Context ctx){
        User user = new User();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        user.setfName(sp.getString("fname",""));
        user.setlName(sp.getString("lname",""));
        user.setEmail(sp.getString("email",""));
        user.setAddress(sp.getString("address",""));
        user.setApplyFriendlyDisc(sp.getInt("apply",0));
        user.setUserId(sp.getInt("id",0));

        return user;
    }
}
