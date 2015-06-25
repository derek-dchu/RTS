package com.rts.config;

import com.rts.persistence.dao.*;
import com.rts.persistence.dao.impl.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.rts.persistence"})
public class PersistenceConfig {

    @Autowired
    protected Environment e;

    protected AnnotationSessionFactoryBean getBaseAnnotationSessionFactoryBean() {
        AnnotationSessionFactoryBean sessionFactory = new AnnotationSessionFactoryBean();
        Properties properties = new Properties() {
            {
                setProperty("hibernate.dialect", e.getProperty("hibernate.dialect"));
                setProperty("hibernate.show_sql", e.getProperty("hibernate.show_sql"));
                setProperty("hibernate.format_sql", e.getProperty("hibernate.format_sql"));
                setProperty("hibernate.cache.use_second_level_cache",
                        e.getProperty("hibernate.cache.use_second_level_cache"));
                setProperty("hibernate.cache.use_query_cache", e.getProperty("hibernate.cache.use_query_cache"));
                setProperty("hibernate.id.new_generator_mappings", e.getProperty("hibernate.id.new_generator_mappings"));
            }
        };
        sessionFactory.setHibernateProperties(properties);
        return sessionFactory;
    }

    @Autowired
    @Bean
    public AnnotationSessionFactoryBean sessionFactory(DataSource ds) {
        AnnotationSessionFactoryBean sessionFactory = getBaseAnnotationSessionFactoryBean();
        sessionFactory.setDataSource(ds);
        sessionFactory.setPackagesToScan("com.rts.persistence.model");
        return sessionFactory;
    }

    @Autowired
    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }

    @Autowired
    @Bean
    public UserDao userDao(SessionFactory sessionFactory) {
        return new UserDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean
    public TransactionDao transactionDao(SessionFactory sessionFactory) {
        return new TransactionDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean
    public TicketDao ticketDao(SessionFactory sessionFactory) {
        return new TicketDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean
    public CreditCardDao creditCardDao(SessionFactory sessionFactory) {
        return new CreditCardDaoImpl(sessionFactory);
    }

    @Autowired
    @Bean
    public ConfirmationCodeDao confirmationCodeDao(SessionFactory sessionFactory) {
        return new ConfirmationCodeDaoImpl(sessionFactory);
    }
}
