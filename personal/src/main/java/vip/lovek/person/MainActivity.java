package vip.lovek.person;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import vip.lovek.annotation.ARouter;

@ARouter(path = "/person/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}