package vip.lovek.interview.plugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import vip.lovek.interview.R;
import vip.lovek.interview.base.BaseActivity;
import vip.lovek.interview.third_library.OkioActivity;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-14 16:57
 */
public class HotFixActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_library);

        Log.e("TAG", "启动hot");
        findViewById(R.id.tv).postDelayed(() -> {
            Log.e("TAG", "post");
            Toast.makeText(HotFixActivity.this, "通知", Toast.LENGTH_LONG).show();
            Notification.Builder mBuilder = new Notification.Builder(HotFixActivity.this);
            mBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("简单通知Tittle")
                    .setContentText("点击可以打开Activity");

            Intent resultIntent = new Intent(this, OkioActivity.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(0, mBuilder.build());
        }, 20000);
    }
}
