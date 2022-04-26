package vip.lovek.interview.aidl;

import android.os.IInterface;

import java.util.List;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-20 00:30
 */
public interface IPersonManager extends IInterface {

   void addPerson(Person p);

   List<Person> getPersons();

}
