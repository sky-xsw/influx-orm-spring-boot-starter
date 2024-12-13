package com.sky.orm.influx.service;

import com.sky.orm.influx.binding.InfluxClientMethod;

public interface IInfluxClientHandler {

    Object selectInflux(String oriSql, InfluxClientMethod influxClientMethod, Object[] args);

    Object insertInflux(Object[] args);

    Object updateInflux(Object[] args);
}
