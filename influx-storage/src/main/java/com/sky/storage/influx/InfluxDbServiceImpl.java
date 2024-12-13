package com.sky.storage.influx;

import org.influxdb.InfluxDB;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;
import org.influxdb.annotation.TimeColumn;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.InfluxDBResultMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InfluxDbServiceImpl<T> implements InfluxDbService<T> {

    private InfluxDB influxDB;

    private final static long NANO_MILLI_RATE = 1000000;

    public InfluxDbServiceImpl(InfluxDB influxDB) {
        this.influxDB = influxDB;
    }

    @Override
    public void batchPoints(List<T> points) {

        points.forEach(this::batchPoint);
    }

    @Override
    public void batchPoint(T point) {

        Class<?> aClass = point.getClass();
        AnnotationChecker.checkClassForAnnotation(aClass, Measurement.class);
        Field field = AnnotationChecker.checkFieldForAnnotation(aClass, TimeColumn.class);
        AnnotationChecker.checkFieldForAnnotation(aClass, Column.class);
        ReflectionUtils.makeAccessible(field);
        long time = (long) ReflectionUtils.getField(field, point);
        Point.Builder pointBuilder = Point.measurementByPOJO(aClass)
                .addFieldsFromPOJO(point);
        if (time != 0) {
            pointBuilder.time(time, field.getAnnotation(TimeColumn.class).timeUnit());
        } else {
            long milli = System.currentTimeMillis() * NANO_MILLI_RATE;
            long nano = System.nanoTime() % NANO_MILLI_RATE;
            pointBuilder.time(milli + nano, TimeUnit.NANOSECONDS);
        }
        Point p = pointBuilder.build();

        influxDB.write(queryDataBase(aClass), "autogen", p);
    }


    private String queryDataBase(Class<?> clazz) {
        Measurement annotation = clazz.getAnnotation(Measurement.class);
        return annotation.database();
    }

    @Override
    public List<T> query(String command, Class<T> clazz) {

        Query query = new Query(command, queryDataBase(clazz));

        return query(query, clazz);

    }

    @Override
    public List<T> query(Query query, Class<T> clazz) {
        QueryResult queryResult = influxDB.query(query);

        InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();

        return resultMapper.toPOJO(queryResult, clazz);
    }
}
