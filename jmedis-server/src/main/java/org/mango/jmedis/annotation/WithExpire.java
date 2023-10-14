package org.mango.jmedis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 伴随expire逻辑执行注解
 * @Date 2021-11-02 21:48
 * @Created by mango
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface WithExpire {

}
