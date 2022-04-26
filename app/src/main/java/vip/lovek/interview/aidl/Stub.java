package vip.lovek.interview.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-20 08:44
 */
public abstract class Stub extends Binder implements IPersonManager {

    public static final int TRANSACTION_ADD_PERSON = IBinder.FIRST_CALL_TRANSACTION;
    public static final int TRANSACTION_GET_PERSON = IBinder.LAST_CALL_TRANSACTION + 1;
    private static final String DESCRIPTOR = "vip.lovek.interview.aidl.IPersonManager";

    public Stub() {
        this.attachInterface(this, DESCRIPTOR);
    }

    public static IPersonManager asInterface(IBinder binder) {
        if (binder == null) {
            return null;
        }
        IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
        if (iInterface instanceof IPersonManager) {
            return (IPersonManager) iInterface;
        } else {
            return new Proxy(binder);
        }
    }

    // 运行在服务端线程中
    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        switch (code) {
            case INTERFACE_TRANSACTION:
                assert reply != null;
                reply.writeString(DESCRIPTOR);
                return true;
            case TRANSACTION_ADD_PERSON:
                data.enforceInterface(DESCRIPTOR);
                Person person = null;
                if (data.readInt() != 0) {
                    person = Person.CREATOR.createFromParcel(data);
                }
                this.addPerson(person);
                assert reply != null;
                reply.writeNoException();
                return true;
            case TRANSACTION_GET_PERSON:
                data.enforceInterface(DESCRIPTOR);
                List<Person> personList = this.getPersons();
                assert reply != null;
                reply.writeNoException();
                reply.writeTypedList(personList);
                return true;
        }
        return super.onTransact(code, data, reply, flags);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }
}
