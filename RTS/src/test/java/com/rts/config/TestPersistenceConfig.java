package com.rts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

public class TestPersistenceConfig extends PersistenceConfig {

    @Override
    @Autowired
    @Bean
    public AnnotationSessionFactoryBean sessionFactory(DataSource ds) {
        AnnotationSessionFactoryBean sessionFactory = super.getBaseAnnotationSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("com.rts.persistence.model");
        Properties properties = sessionFactory.getHibernateProperties();
        properties.setProperty("hibernate.hbm2ddl.auto", e.getProperty("test.hibernate.hbm2ddl.auto"));
        properties.setProperty("hibernate.hbm2ddl.import_files", e.getProperty("test.hibernate.hbm2ddl.import_files"));
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }
}
