package com.rts.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.properties")
@Import({
        DataSourceConfig.class,
        PersistenceConfig.class,
        EmailConfig.class,
        ServiceConfig.class,
        SecurityConfig.class,
})
public class AppConfig {
}
