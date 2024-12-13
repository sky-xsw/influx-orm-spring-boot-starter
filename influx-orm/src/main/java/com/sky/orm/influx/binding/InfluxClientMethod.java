package com.sky.orm.influx.binding;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @Description:
 * @author: sky
 * @date: 2024/1/5 15:10
 */
public class InfluxClientMethod {

    private final Class<?> returnType;

    private final Annotation annotations;

    private final Map<Integer, String> paramNames;

    public InfluxClientMethod(Class<?> returnType, Annotation sql, Map<Integer, String> paramNames) {
        this.returnType = returnType;
        this.annotations = sql;
        this.paramNames = paramNames;
    }

    public Map<Integer, String> getParamNames() {
        return paramNames;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public Annotation getAnnotations() {
        return annotations;
    }
}
