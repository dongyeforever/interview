package vip.lovek.interview.mvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import vip.lovek.interview.R;
import vip.lovek.interview.third_library.retrofit.RetrofitActivity;

public class MvvmActivity extends AppCompatActivity {
    private MyViewModel viewModel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm);
        textView = findViewById(R.id.tv);
        textView.postDelayed(() -> {
            viewModel.setName();
            viewModel.postName();
        }, 3000);


        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("TAG", "observe: " + s);
                textView.setText(s);
            }
        });
        startActivity(new Intent(this, RetrofitActivity.class));
    }
}