package vip.lovek.interview.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * author： yuzhirui@douban.com
 * date: 2022-03-20 09:25
 */
public class PersonService extends Service {
    private static final String TAG = "PersonService";
    private List<Person> personList = new ArrayList();
    private IBinder binder = new Stub() {
        @Override
        public void addPerson(Person p) {
            Log.e(TAG, "addPerson: " + Thread.currentThread().getName());
            personList.add(p);
        }

        @Override
        public List<Person> getPersons() {
            Log.e(TAG, "getPersons: " + Thread.currentThread().getName());
            return personList;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: " + Thread.currentThread().getName());
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: " + Thread.currentThread().getName());
        return super.onStartCommand(intent, flags, startId);
    }
}
