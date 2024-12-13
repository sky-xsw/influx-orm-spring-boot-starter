package com.sky.orm.influx.service;


import org.influxdb.dto.BoundParameterQuery;

import java.util.Map;

public interface IParamResolve {

    String resolveSql(Map<Integer, String> paramMap, Object[] args, String value);

    void paramBinding(BoundParameterQuery.QueryBuilder query, Object[] args, Map<Integer,String> paramNames);

    boolean checkId(Object arg, Class<?> aClass);
}
