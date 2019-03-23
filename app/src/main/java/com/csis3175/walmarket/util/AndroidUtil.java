package com.csis3175.walmarket.util;

import android.content.Context;
import android.location.LocationManager;

public class AndroidUtil {
    public static boolean isGpsEnabled(Context context){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        return gps_enabled;
    }
}
