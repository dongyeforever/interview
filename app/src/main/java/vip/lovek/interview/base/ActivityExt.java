package vip.lovek.interview.base;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;


/**
 * author： yuzhirui@douban.com
 * date: 2022-02-17 16:28
 */
public class ActivityExt {
    private final Activity activity;
    private final ViewGroup contentParent;

    public ActivityExt(Activity activity) {
        this.activity = activity;
        contentParent = activity.findViewById(android.R.id.content);
    }

    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        contentParent.addView(view, layoutParams);
    }

    public void removeView(View view) {
        contentParent.removeView(view);
    }
}
