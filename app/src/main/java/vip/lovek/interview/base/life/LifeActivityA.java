package vip.lovek.interview.base.life;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import vip.lovek.interview.R;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-29 08:43
 */
public class LifeActivityA extends Activity implements View.OnClickListener {
    private static final String TAG = "LifeActivityA";
    private Button goActivityB;
    private Button goSingleTopActivityB;
    private Button goPlugin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
        goActivityB = findViewById(R.id.goActivityB);
        goSingleTopActivityB = findViewById(R.id.goSingleTopActivityB);
        goPlugin = findViewById(R.id.goPlugin);
        goActivityB.setOnClickListener(this);
        goSingleTopActivityB.setOnClickListener(this);
        goPlugin.setOnClickListener(this);

        Log.i(TAG, "onCreate()");
//      for (;;){
//         Log.i(TAG, "onCreate()");
//      }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState()");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goActivityB:
                startActivity(new Intent(this, LifeActivityB.class));
                break;
            case R.id.goPlugin:
                Intent intent = new Intent("android.intent.action.plugin_action");
                startActivity(intent);
                break;
            case R.id.goSingleTopActivityB:
                startActivity(new Intent(this, LifeActivitySingTopB.class));
                goSingleTopActivityB.postDelayed(() -> startActivity(new Intent(this, LifeActivitySingTopB.class)), 2000);
                break;
        }
    }

}
