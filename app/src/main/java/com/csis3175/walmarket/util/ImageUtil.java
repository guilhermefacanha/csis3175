package com.csis3175.walmarket.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageUtil {

    public static byte[] getImage(String imageUrl) {
        InputStream is = null;
        try {
            URL url = new URL(imageUrl);
            is = url.openStream();
            byte[] imageBytes = IOUtils.toByteArray(is);

            return imageBytes;
        } catch (IOException e) {
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
