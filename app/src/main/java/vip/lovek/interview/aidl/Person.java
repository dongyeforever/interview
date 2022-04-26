package vip.lovek.interview.aidl;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-20 00:27
 */
public class Person implements Parcelable {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Person() {

   }

   protected Person(Parcel in) {
      name = in.readString();
   }

   public static final Creator<Person> CREATOR = new Creator<Person>() {
      @Override
      public Person createFromParcel(Parcel in) {
         return new Person(in);
      }

      @Override
      public Person[] newArray(int size) {
         return new Person[size];
      }
   };

   @Override
   public int describeContents() {
      return 0;
   }

   @Override
   public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(name);
   }
}
