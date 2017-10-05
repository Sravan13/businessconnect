package com.sravan.businessconnect.todo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.sravan.businessconnect.app.dao.Todo;
import com.sravan.businessconnect.app.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;
import static com.sravan.businessconnect.todo.service.TodoSpecifications.titleOrDescriptionContainsIgnoreCase;

@Service("todoSearchService")
public class RepositoryTodoSearchService implements TodoSearchService {
	
	
	
	@Autowired
	TodoRepository todoRepository;

	//@Transactional(readOnly = true)
	@Override
	public List<TodoDTO> findBySearchTerm(String searchTerm) {
		System.out.println("todo service &&&&&&");
		List<Todo> searchResults = todoRepository.findAll(titleOrDescriptionContainsIgnoreCase(searchTerm), orderBy());
		
		return TodoMapper.mapEntitiesIntoDTOs(searchResults);
	}
	
	private Sort orderBy() {
	    return new Sort(Sort.Direction.DESC, "title", "description");
	}

}
