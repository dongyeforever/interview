package vip.lovek.interview.third_library;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;

import okio.Buffer;
import okio.Okio;
import okio.Source;
import vip.lovek.annotation.ARouter;
import vip.lovek.interview.R;
import vip.lovek.interview.base.BaseActivity;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-12 16:16
 */
@ARouter(path = "/app/okio")
public class OkioActivity extends BaseActivity {
    TextView textView;
    private static final String TAG = "OkioActivity";
    Handler h = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_library);
        textView = findViewById(R.id.tv);
        textView.setText("世界和平");
        try {
            Source source = Okio.source(getAssets().open("a.txt"));
            Buffer buffer = new Buffer();
            source.read(buffer, 1024);
            Log.e("TAG", buffer.readUtf8());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Log.e(TAG, "addIdleHandler: width = " + textView.getWidth() + ", measureWidth = " + textView.getMeasuredWidth());
                return false;
            }
        });

        // 会被调用多次
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e(TAG, "getViewTreeObserver: width = " + textView.getWidth() + ", measureWidth = " + textView.getMeasuredWidth());
            }
        });


//        new Thread(() -> {
//            if (ActivityManager.currentActivityRef != null && ActivityManager.currentActivityRef.get() != null) {
//                Activity currentActivity = ActivityManager.currentActivityRef.get();
//                ActivityExt activityExt = new ActivityExt(currentActivity);
//
//                ViewGroup.LayoutParams params = textView.getLayoutParams();
//                TextView view = new TextView(this);
//                view.setText("我是addView");
//                view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                view.setTextColor(getResources().getColor(R.color.white));
//                activityExt.addContentView(view, params);
//                view.setText("ThreadName:" + Thread.currentThread().getName());
//            }
//        }).start();

//        try {
//            Sink sink = Okio.sink(new File("a.txt"));
//            BufferedSink bufferedSink = Okio.buffer(sink);
//            bufferedSink.writeUtf8("hello world");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        textView.post(() -> {
            Log.e(TAG, "post: width = " + textView.getWidth() + ", measureWidth = " + textView.getMeasuredWidth());
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: width = " + textView.getWidth() + ", measureWidth = " + textView.getMeasuredWidth());
    }


}
