package vip.lovek.interview.third_library.butterknife;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import vip.lovek.annotation.BindView;


/**
 * author： yuzhirui@douban.com
 * date: 2022-02-14 00:08
 */
public class ButterKnife {

    public static void bind(Activity activity) {
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null) {
                field.setAccessible(true);
                try {
                    field.set(activity, activity.findViewById(bindView.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void bindV2(Activity activity) {
        try {
            Class<?> bindingClass = Class.forName(activity.getClass().getCanonicalName() + "Binding");
            Constructor<?> constructor = bindingClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |
                InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
