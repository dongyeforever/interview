package vip.lovek.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author： yuzhirui@douban.com
 * date: 2022-04-25 00:20
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface AsmTimer {
}
