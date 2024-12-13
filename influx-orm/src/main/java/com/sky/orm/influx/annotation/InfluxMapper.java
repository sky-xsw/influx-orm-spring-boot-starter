package com.sky.orm.influx.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @author: sky
 * @date: 2024/1/5 14:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface InfluxMapper {

    String name() default "";

    String group() default "";

    /**
     * @return whether to mark the feign proxy as a primary bean. Defaults to true.
     */
    boolean primary() default true;
}
