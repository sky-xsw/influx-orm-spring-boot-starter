package com.sky.orm.influx.core;

import com.sky.orm.influx.annotation.Insert;
import com.sky.orm.influx.annotation.Select;
import com.sky.orm.influx.annotation.Update;
import com.sky.orm.influx.binding.InfluxClientMethod;
import com.sky.orm.influx.service.IInfluxClientHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Description:
 * @author: sky
 * @date: 2024/1/5 16:43
 */
public class InfluxClientInvocationHandler implements InvocationHandler {

    private final String influxClientName;

    private IInfluxClientHandler iInfluxClientHander;

    private final Map<Method, InfluxClientMethod> dispatch;

    public InfluxClientInvocationHandler(String influxClientName, IInfluxClientHandler iInfluxClientHander, Map<Method, InfluxClientMethod> dispatch) {
        this.influxClientName = influxClientName;
        this.iInfluxClientHander = iInfluxClientHander;
        this.dispatch = dispatch;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        InfluxClientMethod influxClientMethod = dispatch.get(method);

        Annotation annotations = influxClientMethod.getAnnotations();

        Class<? extends Annotation> annotationType = annotations.annotationType();

        if (Select.class.equals(annotationType)) {
            return iInfluxClientHander.selectInflux(((Select) annotations).value(), influxClientMethod, args);
        }

        if (Insert.class.equals(annotationType)) {
            return iInfluxClientHander.insertInflux(args);
        }

        if (Update.class.equals(annotationType)) {
            return iInfluxClientHander.updateInflux(args);
        }

        return method.invoke(proxy, args);
    }


}
