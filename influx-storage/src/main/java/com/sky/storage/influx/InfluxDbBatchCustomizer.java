package com.sky.storage.influx;

import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.influx.InfluxDbCustomizer;

import java.util.concurrent.TimeUnit;

public class InfluxDbBatchCustomizer implements InfluxDbCustomizer {


    private InfluxDbExtensionProperties properties;

    public InfluxDbBatchCustomizer(InfluxDbExtensionProperties properties) {
        this.properties = properties;
    }

    @Override
    public void customize(InfluxDB influxDb) {

        influxDb.setConsistency(InfluxDB.ConsistencyLevel.ANY);
        influxDb.enableBatch(properties.getActions(), properties.getFlushDuration(), TimeUnit.MILLISECONDS);
        influxDb.setRetentionPolicy("autogen");
    }
}
