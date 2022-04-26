package vip.lovek.interview.third_library.retrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import vip.lovek.interview.utils.NetworkUtil;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-17 23:06
 */
public class BaseInterceptor implements Interceptor {

    private Context mContext;

    public BaseInterceptor(Context context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (NetworkUtil.isConnected(mContext)) {
            return chain.proceed(chain.request());
        } else {
            // 如果没有网络，则返回缓存未过期一个月的数据
            Request newRequest = chain.request().newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .removeHeader("Pragma")
//                    .header("Cache-Control", "only-if-cached, max-stale=" + 60)
                    .build();
            return chain.proceed(newRequest);
        }
    }
}