package vip.lovek.interview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import vip.lovek.interview.R;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-08 11:35
 */
public class BitmapUtils {

    public static Bitmap getBitmap(Context context, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.avatar, options);
    }
}
