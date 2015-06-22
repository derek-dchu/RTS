package com.mercury.rts.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.orm.hibernate3.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.activation.DataSource;
import java.lang.Exception;

import com.mercury.rts.persistence.dao.impl.*;

@Configuration
@PropertySource("/application.properties")
@EnableTransactionManagement
@ComponentScan
public class PersistenceConfiguration {

    @Bean
    public DataSource dataSource(Environment e) {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        return dsLookup.getDataSource(e.getProperty("dataSource"));
    }

    private LocalSessionFactoryBuilder getBaseSessionFactoryBuilder(DataSource ds, Environment e) {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(ds);
        builder.setProperty("hibernate.dialect", e.getProperty("hibernate.dialect"));
        builder.setProperty("hibernate.show_sql", e.getProperty("hibernate.show_sql"));
        builder.setProperty("hibernate.format_sql", e.getProperty("hibernate.format_sql"));
        builder.setProperty("hibernate.cache.use_second_level_cache",
                e.getProperty("hibernate.cache.use_second_level_cache"));
        builder.setProperty("hibernate.cache.use_query_cache", e.getProperty("hibernate.cache.use_query_cache"));
        builder.setProperty("hibernate.id.new_generator_mappings", e.getProperty("hibernate.id.new_generator_mappings"));
        return builder;
    }v

    @Autowired
    @Bean
    public SessionFactory appSessionFactory(DataSource ds) {
        LocalSessionFactoryBuilder baseBuilder = getBaseSessionFactoryBuilder(ds);
        baseBuilder.scanPackages("com.mercury.rts.presistence");
        return baseBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        return HibernateTransactionManager(sessionFactory);
    }

    @Autowired
    @Bean
    private UserDao userDao() {
        return new UserDaoImpl(SessionFactory appSessionFactory);
    }
}
