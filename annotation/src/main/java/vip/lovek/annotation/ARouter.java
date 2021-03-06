package vip.lovek.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-31 17:34
 */
@Target(ElementType.TYPE) // 类上
@Retention(RetentionPolicy.SOURCE) // 编译期
public @interface ARouter {
    String path();

    String group() default "";
}
