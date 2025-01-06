package com.github.xcrigx.hapi.migratedb.config;

import com.github.xcrigx.hapi.migratedb.rest.PackageScanDummy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = { PackageScanDummy.class })
@Configuration
//@Import(WebMvcAutoConfiguration.class)
//@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringConfig {

}
