package com.sravan.businessconnect.config.db;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.sravan.businessconnect.properties.BussinessConnectDbProperties;
import com.sravan.businessconnect.properties.SecurityDbProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages={"com.sravan.businessconnect.sec.repository"},
					   entityManagerFactoryRef = "secEntityManagerFactory",
					   transactionManagerRef = "secTransactionManager")
//@Profile({Profiles.APPLICATION})
public class SecurityConfig {
	
	@Autowired
	private SecurityDbProperties secProperties;
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource secDataSource(){
		
		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(secProperties.getDriver());
        dataSourceConfig.setJdbcUrl(secProperties.getUrl());
        dataSourceConfig.setUsername(secProperties.getUsername());
        dataSourceConfig.setPassword(secProperties.getPassword());
		
        return new HikariDataSource(dataSourceConfig);
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean secEntityManagerFactory(){
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(secDataSource());
		factory.setPackagesToScan("com.sravan.businessconnect.sec.dao");
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto",secProperties.getHbm2ddl_auto());
		jpaProperties.put("hibernate.show-sql",secProperties.getShow_sql());
		jpaProperties.put("hibernate.dialect",secProperties.getDialect());
		jpaProperties.put("hibernate.format_sql",secProperties.getFormat_sql());
		
		factory.setJpaProperties(jpaProperties);
		return factory;
	}
	
    @Bean
    JpaTransactionManager secTransactionManager(EntityManagerFactory secEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(secEntityManagerFactory);
        return transactionManager;
    }
    

}
