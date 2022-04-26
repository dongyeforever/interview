package vip.lovek.interview.aidl;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-20 00:32
 */
public class Proxy implements IPersonManager {
    private IBinder binder;
    // binder id
    private static final String DESCRIPTOR = "vip.lovek.interview.aidl.IPersonManager";

    public Proxy(IBinder binder) {
        this.binder = binder;
    }

    @Override
    public void addPerson(Person p) {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            if (p != null) {
                data.writeInt(1);
                p.writeToParcel(data, 0);
            } else {
                data.writeInt(0);
            }
            binder.transact(Stub.TRANSACTION_ADD_PERSON, data, reply, 0);
            reply.readException();
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
    }

    @Override
    public List<Person> getPersons() {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        List<Person> persons = null;
        try {
            data.writeInterfaceToken(DESCRIPTOR);
            binder.transact(Stub.TRANSACTION_GET_PERSON, data, reply, 0);
            reply.readException();
            persons = reply.createTypedArrayList(Person.CREATOR);
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            data.recycle();
            reply.recycle();
        }
        return persons;
    }

    @Override
    public IBinder asBinder() {
        return binder;
    }
}
