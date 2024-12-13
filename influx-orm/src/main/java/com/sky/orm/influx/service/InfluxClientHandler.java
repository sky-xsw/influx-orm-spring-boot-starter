package com.sky.orm.influx.service;

import com.sky.orm.influx.annotation.Insert;
import com.sky.orm.influx.annotation.Update;
import com.sky.orm.influx.binding.InfluxClientMethod;
import com.sky.storage.influx.InfluxDbService;
import org.influxdb.annotation.Measurement;
import org.influxdb.dto.BoundParameterQuery;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class InfluxClientHandler implements IInfluxClientHandler {

    private InfluxDbService influxDbService;

    private IParamResolve paramResolve;


    public InfluxClientHandler(InfluxDbService influxDbService, IParamResolve paramResolve) {
        this.influxDbService = influxDbService;
        this.paramResolve = paramResolve;
    }

    @Override
    public Object selectInflux(String oriSql, InfluxClientMethod influxClientMethod, Object[] args) {

        /**
         * 1.进行sql解析
         * 2.绑定参数
         * 3.添加database
         */

        Class<?> returnType = influxClientMethod.getReturnType();

        Map<Integer, String> paramNames = influxClientMethod.getParamNames();

        //#{}字符串替换形式
        String sql = paramResolve.resolveSql(paramNames, args, oriSql);

        BoundParameterQuery.QueryBuilder queryBuilder = BoundParameterQuery.QueryBuilder.newQuery(sql);

        queryBuilder.forDatabase(getDataBase(returnType));

        //${} 占位符形式
        paramResolve.paramBinding(queryBuilder, args, paramNames);

        return influxDbService.query(queryBuilder.create(), returnType);
    }

    private String getDataBase(Class<?> returnType) {
        Measurement annotation = returnType.getAnnotation(Measurement.class);
        return annotation.database();
    }


    @Override
    public Object insertInflux(Object[] args) {
        insertOrUpdate(args, Insert.class);

        throw new RuntimeException("Inserting uncertain data types");
    }

    @Override
    public Object updateInflux(Object[] args) {
        insertOrUpdate(args, Update.class);

        throw new RuntimeException("Operation type error");
    }

    private void insertOrUpdate(Object[] args, Class<?> updateClass) {
        Object arg = args[0];

        if (Objects.nonNull(arg) && paramResolve.checkId(arg, updateClass)) {
            if (List.class.isAssignableFrom(arg.getClass()) || arg.getClass().isArray()) {
                influxDbService.batchPoints(arg.getClass().isArray() ? Arrays.asList((Object[]) arg) : (List) arg);
            } else {
                influxDbService.batchPoint(arg);
            }
        }
    }
}
