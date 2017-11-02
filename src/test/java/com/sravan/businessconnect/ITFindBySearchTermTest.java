package com.sravan.businessconnect;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.sravan.businessconnect.app.repository.TodoRepository;
import com.sravan.businessconnect.config.db.BussinessConnectDBConfig;
import com.sravan.businessconnect.config.db.Profiles;
import com.sravan.businessconnect.properties.BussinessConnectDbProperties;
import com.sravan.businessconnect.todo.service.CurrentTimeDateTimeService;
import com.sravan.businessconnect.todo.service.DateTimeService;
import com.sravan.businessconnect.todo.service.TodoSearchResultDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.INTEGRATION_TEST)
@ContextConfiguration(classes = {ITFindBySearchTermTest.EmployeeServiceImplTestContextConfiguration.class,BussinessConnectDBConfig.class})
/*@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})*/
@DatabaseSetup("todo-entries.xml")
public class ITFindBySearchTermTest {
	
	@TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
  
        @Bean
        public BussinessConnectDbProperties bcProperties() {
        	
        	BussinessConnectDbProperties bcProperties = new BussinessConnectDbProperties();
        	bcProperties.setDriver("com.mysql.jdbc.Driver");
        	bcProperties.setUrl("jdbc:mysql://localhost:3306/connectBussiness?useSSL=false");
        	bcProperties.setUsername("root");
        	bcProperties.setPassword("mysl");
        	bcProperties.setDialect("org.hibernate.dialect.MySQLDialect");
        	bcProperties.setHbm2ddl_auto("update");
        	bcProperties.setShow_sql("true");
        	bcProperties.setFormat_sql("true");
        	
            return bcProperties;
        }
        
        @Bean
        DateTimeService currentTimeDateTimeServiceTest() {
            return new CurrentTimeDateTimeService();
        }
    }
	
	@Autowired
    private TodoRepository repository;
     
    @Test
    public void findBySearchTerm_TitleOfFirstTodoEntryContainsGivenSearchTerm_ShouldReturnOneTodoEntry() {
        List<TodoSearchResultDTO> searchResults = repository.findBySearchTerm("abc");
        assertThat(searchResults).hasSize(13);
    }
     
    @Test
    public void findBySearchTerm_TitleOfFirstTodoEntryContainsGivenSearchTerm_ShouldReturnFirstTodoEntry() {
        List<TodoSearchResultDTO> searchResults = repository.findBySearchTerm("abc");
 
        TodoSearchResultDTO found = searchResults.get(0);
        assertThat(found.getId()).isEqualTo(2L);
    } 

}
