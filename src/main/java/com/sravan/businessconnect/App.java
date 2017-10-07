package com.sravan.businessconnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sravan.businessconnect.config.db.Profiles;
import com.sravan.businessconnect.todo.service.CurrentTimeDateTimeService;
import com.sravan.businessconnect.todo.service.DateTimeService;

@ComponentScan(basePackages = "com.sravan")
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
								   HibernateJpaAutoConfiguration.class,
								   DataSourceTransactionManagerAutoConfiguration.class })
@EnableTransactionManagement
public class App extends SpringBootServletInitializer 
{
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
    

}
