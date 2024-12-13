package com.sky.storage.influx;

import org.influxdb.dto.Query;

import java.util.List;

public interface InfluxDbService<T> {

    void batchPoints(List<T> points);

    void batchPoint(T point);

    List<T> query(String command, Class<T> clazz);

    List<T> query(Query query, Class<T> clazz);
}
