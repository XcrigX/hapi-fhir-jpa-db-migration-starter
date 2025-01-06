package com.github.xcrigx.springstub.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackageClasses = { com.github.xcrigx.springstub.rest.PackageScanDummy.class })
@Configuration
//@Import(WebMvcAutoConfiguration.class)
//@EnableConfigurationProperties(ApplicationProperties.class)
public class SpringConfig {

}
