package com.sky.storage.influx;

import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.influx.InfluxDbCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(InfluxDbExtensionProperties.class)
public class InfluxStorageAutoConfiguration {
    @Bean
    public InfluxDbCustomizer influxDbCustomizer(InfluxDbExtensionProperties influxDbExtensionProperties) {
        return new InfluxDbBatchCustomizer(influxDbExtensionProperties);
    }


    @Bean
    @ConditionalOnMissingBean
    public InfluxDbService<?> influxDbService(InfluxDB influxDB) {
        return new InfluxDbServiceImpl<>(influxDB);
    }



}
