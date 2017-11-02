package com.sravan.businessconnect;

// Reffernece : http://www.baeldung.com/spring-boot-testing

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.app.repository.TodoRepository;
import com.sravan.businessconnect.config.db.Profiles;
import com.sravan.businessconnect.todo.service.TodoSearchResultDTO;

/*
@RunWith(SpringRunner.class) is used to provide a bridge between Spring Boot test features and JUnit. Whenever we are using any Spring Boot testing features in out JUnit tests, this annotation will be required.

@DataJpaTest provides some standard setup needed for testing the persistence layer:

1.configuring H2, an in-memory database 
2.setting Hibernate, Spring Data, and the DataSource
3.performing an @EntityScan turning on SQL logging
*/

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(Profiles.INTEGRATION_TEST)
public class TodoRepositorySpringBootTest {
	
	/*
	 * To carry out some DB operation, we need some records already setup in our database. 
	 * To setup such data, we can use TestEntityManager. The TestEntityManager provided by 
	 * Spring Boot is an alternative to the standard JPA EntityManager that provides methods 
	 * commonly used when writing tests.
	 */
	
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private TodoRepository todoRepository;
    
    @Before
    public void initailizeEntity(){
    	for(int i=1;i<=10;i++){
    		Todo todo = new Todo();
    		todo.setId((long)i);//(long)i
    		todo.setTitle("xyz"+i);
    		todo.setDescription("desc"+i);
    		todo.setModifiedByUser("user"+i);
    		todo.setVersion(1l);
    		todo.setCreationTime(new Date());
    		todo.setModificationTime(new Date());
    		todo.setCreatedByUser("user"+i);
    		entityManager.merge(todo);
    	}
    		entityManager.flush();
    }
    
    @Test
    public void findBySearchTerm_TitleOfFirstTodoEntryContainsGivenSearchTerm_ShouldReturnOneTodoEntry() {

        List<TodoSearchResultDTO> searchResults = todoRepository.findBySearchTerm("xyz");
        assertThat(searchResults).hasSize(10);
    }
     
 

}
