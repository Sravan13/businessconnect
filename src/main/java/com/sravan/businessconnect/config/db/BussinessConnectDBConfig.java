package com.sravan.businessconnect.config.db;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.sravan.businessconnect.properties.BussinessConnectDbProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages={"com.sravan.businessconnect.app.repository"},
					   entityManagerFactoryRef = "bcEntityManagerFactory",
					   transactionManagerRef = "bcTransactionManager")
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
        

}
