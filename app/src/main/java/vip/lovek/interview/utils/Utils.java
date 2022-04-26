package vip.lovek.interview.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-07 13:42
 */
public class Utils {

    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
