package vip.lovek.interview.面试Java;

import android.os.Build;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-26 15:03
 */
public class Generic {

    abstract class SuperClass<T> {
        abstract T getObject();
    }


    public class Impl extends SuperClass<User> {

        @Override
        User getObject() {
            return new User();
        }

        public void printGenericClassInfo() {
            System.out.println(getObject());
            Type type = getClass().getGenericSuperclass();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                System.out.println(type.getTypeName());
                ParameterizedType parameterizedType = (ParameterizedType) type;
                System.out.println(parameterizedType.getTypeName()); //  vip.lovek.interview.面试Java.Generic$SuperClass<vip.lovek.interview.面试Java.User>
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (Type actualType : actualTypeArguments) {
                    System.out.println(actualType.getTypeName()); // vip.lovek.interview.面试Java.User
                }

                Type genericType = actualTypeArguments[0];
                System.out.println(genericType);
                Class<User> clazz = (Class<User>) genericType;
                System.out.println(clazz);
            }
        }
    }

    ///////////////////////////////////// 接口 ///////////////////////////////////////////////////

    interface ServiceA<T> {
    }

    interface ServiceB<T> {
    }

    public class Impl2 implements ServiceA<User>, ServiceB<User> {

        public void printGenericInterfaceInfo() {
            Type[] genericInterfaces = getClass().getGenericInterfaces();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (genericInterfaces.getClass().isAssignableFrom(ParameterizedType[].class)) {
                    for (Type genericInterface : genericInterfaces) {
                        System.out.println(genericInterface.getTypeName()); // vip.lovek.interview.面试Java.Generic$ServiceA
                        ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                        Type type = actualTypeArguments[0];
                        if (type instanceof Class) {
                            Class<?> clazz = (Class<?>) type;
                            System.out.println(clazz);
                        }
                    }
                }
            }
        }
    }
}

class User {
    String name;
    int age;

    public User() {
        this("张三", 20);
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}