package com.sky.storage.influx;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("spring.influx")
public class InfluxDbExtensionProperties {

    /**
     * The number of points triggered by batch operations. After reaching this quantity, batch write operations will be triggered。
     */
    private int actions = 2000;

    /**
     * The time interval triggered by batch operations (in milliseconds)
     */
    private int flushDuration = 200;


}
