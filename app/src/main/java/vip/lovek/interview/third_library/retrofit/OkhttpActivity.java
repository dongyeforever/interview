package vip.lovek.interview.third_library.retrofit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import vip.lovek.interview.R;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-06 18:18
 */
public class OkhttpActivity extends AppCompatActivity {
    private TextView tv;

    private static final String TAG = "RetrofitActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        tv = findViewById(R.id.tv);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(new File(this.getCacheDir(), "okhttp"), 100 * 1024 * 1024))
                .addInterceptor(new BaseInterceptor(this.getApplicationContext()))
                .addNetworkInterceptor(new HttpCacheInterceptor(this.getApplicationContext()))
                .addNetworkInterceptor(logging)
                .build();

//        String url = "https://movie.douban.com/j/search_subjects?type=movie&tag=%E7%83%AD%E9%97%A8&page_limit=50&page_start=0";
        String url = "https://api.apiopen.top/getJoke?page=1&count=2&type=video";
        okHttpClient.newCall(new Request.Builder()
                .url(url)
                .addHeader("apikey", "xxxxxxxx")
//                .addHeader("Cache-Control", "only-if-cached, max-stale=" + 30 * 24 * 60 * 60)
                .build())
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NonNull okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                        String result = response.body().string();
//                        Log.e(TAG, "success: " + result);
                        runOnUiThread(() -> {
                            tv.setText(result);
                        });
                    }
                });

    }
}
