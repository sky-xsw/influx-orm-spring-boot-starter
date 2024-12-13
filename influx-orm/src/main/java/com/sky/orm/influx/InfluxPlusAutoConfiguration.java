package com.sky.orm.influx;

import com.sky.orm.influx.service.IInfluxClientHandler;
import com.sky.orm.influx.service.IParamResolve;
import com.sky.orm.influx.service.InfluxClientHandler;
import com.sky.orm.influx.service.ParamResolveService;
import com.sky.storage.influx.InfluxDbExtensionProperties;
import com.sky.storage.influx.InfluxDbService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(InfluxDbExtensionProperties.class)
public class InfluxPlusAutoConfiguration {

    @Bean
    public IParamResolve paramResolve() {
        return new ParamResolveService();
    }

    @Bean
    public IInfluxClientHandler influxClientHandler(InfluxDbService influxDbService, IParamResolve paramResolve) {
        return new InfluxClientHandler(influxDbService, paramResolve);
    }
}
