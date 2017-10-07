package com.sravan.businessconnect.config.db;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.sravan.businessconnect.properties.BussinessConnectDbProperties;
import com.sravan.businessconnect.todo.service.AuditingDateTimeProvider;
import com.sravan.businessconnect.todo.service.CurrentTimeDateTimeService;
import com.sravan.businessconnect.todo.service.DateTimeService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


/*@EnableJpaAuditing annotation and set the name of the DateTimeProvider bean (dateTimeProvider) 
as the value of of its dataTimeProviderRef attribute.*/
@Configuration
@EnableJpaRepositories(basePackages={"com.sravan.businessconnect.app.repository"},
					   entityManagerFactoryRef = "bcEntityManagerFactory",
					   transactionManagerRef = "bcTransactionManager")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class BussinessConnectDBConfig {
	
	@Autowired
	private BussinessConnectDbProperties bcProperties;
	
	@Bean
	public DataSource bcDataSource(){
		
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(bcProperties.getDriver());
        dataSourceConfig.setJdbcUrl(bcProperties.getUrl());
        dataSourceConfig.setUsername(bcProperties.getUsername());
        dataSourceConfig.setPassword(bcProperties.getPassword());
		
        return new HikariDataSource(dataSourceConfig);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean bcEntityManagerFactory(){
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(bcDataSource());
		factory.setPackagesToScan("com.sravan.businessconnect.app.dao");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto",bcProperties.getHbm2ddl_auto());
		jpaProperties.put("show-sql",bcProperties.getShow_sql());
		jpaProperties.put("hibernate.dialect",bcProperties.getDialect());
		jpaProperties.put("format_sql",bcProperties.getFormat_sql());
		
		factory.setJpaProperties(jpaProperties);
		return factory;
	}
	
    @Bean
    JpaTransactionManager bcTransactionManager(EntityManagerFactory bcEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(bcEntityManagerFactory);
        return transactionManager;
    }
    

    /**
     * This below method for spring data auditing
     * @param dateTimeService
     * @return
     */
    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService){
    	return new AuditingDateTimeProvider(dateTimeService);
    }
    
    /*Annotate the method with the @Profile annotation and set its value to Profiles.
    APPLICATION. This ensures that this bean is created only when our application is started*/
    
    /**
     * This below method for spring data auditing
    */
    @Profile(Profiles.APPLICATION)
    @Bean
    DateTimeService currentTimeDateTimeService() {
        return new CurrentTimeDateTimeService();
    }
        

}
