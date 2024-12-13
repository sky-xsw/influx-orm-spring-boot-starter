package com.sky.orm.influx.annotation;

import java.lang.annotation.*;

/**
 * @Description:
 * @author: sky
 * @date: 2024/1/5 14:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {

    String value() default "";
}
