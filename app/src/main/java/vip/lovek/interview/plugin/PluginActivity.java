package vip.lovek.interview.plugin;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import vip.lovek.annotation.BindView;
import vip.lovek.interview.R;
import vip.lovek.interview.base.BaseActivity;
import vip.lovek.interview.third_library.butterknife.ButterKnife;
import vip.lovek.interview.utils.ThreadPoolHandler;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-14 16:57
 */
@SuppressLint("NonConstantResourceId")
public class PluginActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.btn_load_plugin)
    Button btnLoad;
    @BindView(R.id.btn_load_online_plugin)
    Button btnLoadOnline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        ButterKnife.bindV2(this);
        textView.setText("hello");
        btnLoad.setOnClickListener(v -> loadPluginMethod());
        btnLoadOnline.setOnClickListener(v -> startProxyActivity());

        MyHookHelper.inject(ClassLoaderHelper.getDexClassLoader());
        runOnUiThread(() -> {
            Toast.makeText(PluginActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
        });
    }

    private void startProxyActivity() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("vip.lovek.pluginapp", "vip.lovek.pluginapp.TestPluginActivity"));
        startActivity(intent);
    }

    private void loadPluginMethod() {
        ThreadPoolHandler.getInstance().run(() -> {
            String result = null;
            try {
                Class clazz = getClassLoader().loadClass("vip.lovek.pluginapp.PluginUtil");
                Constructor constructor = clazz.getDeclaredConstructors()[0];
                Object pluginUtil = constructor.newInstance();
                Method method = clazz.getDeclaredMethod("hello", String.class);
                result = (String) method.invoke(pluginUtil, "Rui");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }, s -> {
            textView.setText(s);
//            Intent intent = new Intent();
//            intent.setClassName(this, "vip.lovek.pluginapp.PluginMainActivity");
//            startActivity(intent);
        });
    }
}
