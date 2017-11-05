package com.sravan.businessconnect;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.app.repository.TodoRepository;
import com.sravan.businessconnect.todo.service.RepositoryTodoSearchService;
import com.sravan.businessconnect.todo.service.TodoDTO;
import com.sravan.businessconnect.todo.service.TodoSpecifications;


@RunWith(SpringRunner.class)
public class TodoSearchServiceTest {
	
	@TestConfiguration
	static class TodoSearchServiceTestContextConfiguration {
		
		@Bean("testRepositoryBean")
		public RepositoryTodoSearchService getRepositoryTodoSearchService(){
			return new RepositoryTodoSearchService();
		}
		
	}
	
	@Autowired
	private RepositoryTodoSearchService todoService;
	
	@MockBean
	TodoRepository todoRepository;
	
	
	@Before
	public void setUp() {
		Todo todo = new Todo();
		todo.setId((long)1);//(long)i
		todo.setTitle("xyz");
		todo.setDescription("desc");
		todo.setModifiedByUser("user");
		todo.setVersion(1l);
		todo.setCreationTime(new Date());
		todo.setModificationTime(new Date());
		todo.setCreatedByUser("user");
		
		LinkedList<Todo> todos = new LinkedList<>();
		todos.add(todo);
		
		Specification<Todo> searchTermSample = TodoSpecifications.titleOrDescriptionContainsIgnoreCase(todo.getTitle());
		Sort sort = orderBy();
		Mockito.when(todoRepository.findAll(searchTermSample, sort)).thenReturn(todos);
	}
	
	@Test
	public void whenValidName_thenEmployeeShouldBeFound() {
	    String name = "xyz";
	    List<TodoDTO> todos = todoService.findBySearchTerm(name);
	     assertThat(todos.get(0).getTitle())
	      .isEqualTo(name);
	 }
	
	private Sort orderBy() {
	    return new Sort(Sort.Direction.DESC, "title", "description");
	}

}
