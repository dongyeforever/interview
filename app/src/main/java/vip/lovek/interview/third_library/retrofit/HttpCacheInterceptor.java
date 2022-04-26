package vip.lovek.interview.third_library.retrofit;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-17 23:04
 */
public class HttpCacheInterceptor implements Interceptor {

    private Context context;

    public HttpCacheInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=60")
                .build();
        return response;
    }
}
