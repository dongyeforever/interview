package vip.lovek.interview.third_library.butterknife;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vip.lovek.annotation.BindView;
import vip.lovek.interview.R;

/**
 * 自定义 butter knife
 * author： yuzhirui@douban.com
 * date: 2022-02-13 23:48
 */
public class ButterKnifeActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_library);
        ButterKnife.bindV2(this);
        textView.setText("my butter knife");
    }
}
