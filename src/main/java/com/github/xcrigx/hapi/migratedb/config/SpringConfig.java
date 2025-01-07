package com.github.xcrigx.hapi.migratedb.config;

import com.github.xcrigx.hapi.migratedb.rest.PackageScanDummy;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = { PackageScanDummy.class })
@Configuration
@SpringBootApplication(exclude = {ElasticsearchRestClientAutoConfiguration.class, ThymeleafAutoConfiguration.class,  QuartzAutoConfiguration.class })
//@Import(WebMvcAutoConfiguration.class)
//@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringConfig {

}
