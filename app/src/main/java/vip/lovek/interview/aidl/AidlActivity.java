package vip.lovek.interview.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.UUID;

import vip.lovek.annotation.BindView;
import vip.lovek.interview.R;
import vip.lovek.interview.base.BaseActivity;
import vip.lovek.interview.third_library.butterknife.ButterKnife;

/**
 * author： yuzhirui@douban.com
 * date: 2022-02-14 16:57
 */
@SuppressLint("NonConstantResourceId")
public class AidlActivity extends BaseActivity {
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.btn_load_plugin)
    Button btnLoad;
    @BindView(R.id.btn_load_online_plugin)
    Button btnLoadOnline;
    IPersonManager personInterface;

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("TAG", "client onServiceConnected: " + Thread.currentThread().getName());
            personInterface = Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("TAG", "client onServiceDisconnected: " + Thread.currentThread().getName());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        ButterKnife.bindV2(this);

        prepareAidl();

        textView.setText("hello");
        btnLoad.setOnClickListener(v -> addPerson());
        btnLoadOnline.setOnClickListener(v -> getRemotePersonList());

    }

    private void prepareAidl() {
        Intent intent = new Intent(this, PersonService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private void addPerson() {
        Person person = new Person();
        person.setName("张三丰" + UUID.randomUUID());
        try {
            personInterface.addPerson(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getRemotePersonList();
    }

    private void getRemotePersonList() {
        List<Person> personList = personInterface.getPersons();
        StringBuilder sb = new StringBuilder();
        for (Person person : personList) {
            sb.append(person.getName()).append("、");
        }
        textView.setText(sb.toString());
    }

}
