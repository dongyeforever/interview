package vip.lovek.interview.third_library.retrofit;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vip.lovek.interview.R;
import vip.lovek.interview.面试Java.Generic;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-06 18:18
 */
public class RetrofitActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        doHttpRequest();

        // 获取泛型类型
        Generic.Impl generic = new Generic().new Impl();
        generic.printGenericClassInfo();
        Generic.Impl2 generic2 = new Generic().new Impl2();
        generic2.printGenericInterfaceInfo();
    }

    private void doHttpRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService api = retrofit.create(GitHubService.class);
        api.listRepos("dongyeforever").enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.e(TAG, "success");
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
            }
        });
    }
}
